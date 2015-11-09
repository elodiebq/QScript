import java.util.ArrayList;
import java.util.List;

abstract public class Node {
    abstract void compile(CodeWriter cw) throws SyntaxErrorException;
}
abstract class StmtNode extends Node{
    
}
class AssignmentStmtNode extends StmtNode {
    String varName;
    ExpressionNode value;
    
    void compile(CodeWriter cw) throws SyntaxErrorException {
        this.value.compile(cw);
        cw.writeInstruction("store", varName);
    }
    
}

class ExpressionStmtNode extends StmtNode {
    ExpressionNode value;
    
    void compile(CodeWriter cw) throws SyntaxErrorException {
        this.value.compile(cw);
        cw.writeInstruction("pop");
    }
}

class IfStmtNode extends StmtNode {
    ExpressionNode condition;
    StmtNode tureStmt;
    StmtNode falseStmt;
//    public IfStmtNode(){
//        
//    }
    void compile(CodeWriter cw) throws SyntaxErrorException {
       condition.compile(cw);
       Instruction jumpFalse = new Instruction("jf","");
       cw.writeInstruction(jumpFalse);
       tureStmt.compile(cw);
       Instruction jmp = new Instruction("jmp","");
       cw.writeInstruction(jmp);
       jumpFalse.argument = Integer.toString(cw.getCurrentLineIndex());
       if(falseStmt != null) {
           falseStmt.compile(cw);
       }
       jmp.argument = Integer.toString(cw.getCurrentLineIndex());
        
    }
    
}

class WhileStmtNode extends StmtNode {

    ExpressionNode condition;
    StmtNode stmt;
    void compile(CodeWriter cw) throws SyntaxErrorException {
        int indexCheck = cw.getCurrentLineIndex();
        condition.compile(cw);
        Instruction jumpFalse = new Instruction("jf","");
        ArrayList<Instruction> breakList = new ArrayList<Instruction>();
        ArrayList<Instruction> continueList = new ArrayList<Instruction>();
        cw.breakLists.add(breakList);
        cw.continueLists.add(continueList);
        cw.writeInstruction(jumpFalse);
        stmt.compile(cw);
        int continueIndex = cw.getCurrentLineIndex();
        for (Instruction instr: continueList) {
            instr.argument = Integer.toString(continueIndex);
        }
        Instruction jmp = new Instruction("jmp","");
        cw.writeInstruction(jmp);
        jumpFalse.argument = Integer.toString(cw.getCurrentLineIndex());
        jmp.argument = Integer.toString(indexCheck);
        int breakIndex = cw.getCurrentLineIndex();
        for (Instruction instr: breakList) {
            instr.argument = Integer.toString(breakIndex);
        }
        cw.breakLists.remove(cw.breakLists.size() - 1);
        cw.continueLists.remove(cw.continueLists.size() - 1);
        
    }
    
}

class DoWhileNode extends StmtNode {
    StmtNode stmt;
    ExpressionNode condition;
   
    void compile(CodeWriter cw) throws SyntaxErrorException {
        int index = cw.getCurrentLineIndex();
        ArrayList<Instruction> breakList = new ArrayList<Instruction>();
        ArrayList<Instruction> continueList = new ArrayList<Instruction>();
        cw.breakLists.add(breakList);
        cw.continueLists.add(continueList);
        stmt.compile(cw);
        int continueIndex = cw.getCurrentLineIndex();
        condition.compile(cw);
        
        Instruction jmp = new Instruction("jt","");
        cw.writeInstruction(jmp);
        jmp.argument = Integer.toString(index);
        for (Instruction instr: continueList) {
            instr.argument = Integer.toString(continueIndex);
        }

        int breakIndex = cw.getCurrentLineIndex();
        for (Instruction instr: breakList) {
            instr.argument = Integer.toString(breakIndex);
        }
        cw.breakLists.remove(cw.breakLists.size() - 1);
        cw.continueLists.remove(cw.continueLists.size() - 1);
        
        
    }
    
}

class BlockStmtNode extends StmtNode {
    List<StmtNode> statements = new ArrayList<StmtNode>();

   
    void compile(CodeWriter cw) throws SyntaxErrorException {
        for (StmtNode stmt : statements) {
            stmt.compile(cw);
        }
    }
}

class BreakStmtNode extends StmtNode {

    void compile(CodeWriter cw) throws SyntaxErrorException {
        if (cw.breakLists.size() == 0) {
            throw new SyntaxErrorException(0);
        }
        Instruction ist = new Instruction("jmp","");
        cw.writeInstruction(ist);
        cw.breakLists.get(cw.breakLists.size() - 1).add(ist);
    }
    
}

class ContinueStmtNode extends StmtNode {

    void compile(CodeWriter cw) throws SyntaxErrorException {
        if (cw.continueLists.size() == 0) {
            throw new SyntaxErrorException(0);
        }
        Instruction ist = new Instruction("jmp","");
        cw.writeInstruction(ist);
        cw.continueLists.get(cw.continueLists.size() - 1).add(ist);
    }
    
}

class ForStmtNode extends StmtNode {
    String varName;
    ExpressionNode expressionStart;
    ExpressionNode expressionEnd;
    StmtNode stmt;
   
    void compile(CodeWriter cw) throws SyntaxErrorException {
      
        expressionStart.compile(cw);
        cw.writeInstruction("store", varName);
        int indexCheck = cw.getCurrentLineIndex();
        cw.writeInstruction("load", varName);
        expressionEnd.compile(cw);
        cw.writeInstruction("le");
        Instruction jumpFalse = new Instruction("jf","");
        ArrayList<Instruction> breakList = new ArrayList<Instruction>();
        ArrayList<Instruction> continueList = new ArrayList<Instruction>();
        cw.breakLists.add(breakList);
        cw.continueLists.add(continueList);
        cw.writeInstruction(jumpFalse);
        stmt.compile(cw);
        int continueIndex = cw.getCurrentLineIndex();
        for (Instruction instr: continueList) {
            instr.argument = Integer.toString(continueIndex);
        }
        cw.writeInstruction("load", varName);
        cw.writeInstruction("push", "1");
        cw.writeInstruction("add");
        
        cw.writeInstruction("store", varName);
        cw.writeInstruction("jmp", Integer.toString(indexCheck)); 
        jumpFalse.argument = Integer.toString(cw.getCurrentLineIndex());
        int breakIndex = cw.getCurrentLineIndex();
        for (Instruction instr: breakList) {
            instr.argument = Integer.toString(breakIndex);
        }
        cw.breakLists.remove(cw.breakLists.size() - 1);
        cw.continueLists.remove(cw.continueLists.size() - 1);
    }
}

class ProgramNode extends Node {

    ArrayList<StmtNode> children = new ArrayList<StmtNode>();

    void compile(CodeWriter cw) throws SyntaxErrorException {
       for (StmtNode child: this.children) {
           child.compile(cw);
       }   
    }
}