import java.util.ArrayList;

abstract public class ExpressionNode extends Node {

    int pos;

}
class ArrayExpressionNode extends ExpressionNode {
    ArrayList<ExpressionNode> elements = new ArrayList<ExpressionNode>();
    
    void compile(CodeWriter cw) throws SyntaxErrorException {
        
        for (int i = elements.size() - 1; i >= 0; i--) {
            elements.get(i).compile(cw);
        }
        cw.writeInstruction("createArr", Integer.toString(elements.size()));
    }
}

class IndexExpressionNode extends ExpressionNode {
    ExpressionNode object;
    ExpressionNode index;
    
    void compile(CodeWriter cw) throws SyntaxErrorException {
        object.compile(cw);
        index.compile(cw);
        cw.writeInstruction("loadIdx");
    }
}
class NumberNode extends ExpressionNode {
    float value;

    public NumberNode(float value, int pos) {
        this.value = value;
        this.pos = pos;
    }

    void compile(CodeWriter cw) {
        cw.writeInstruction("push", Float.toString(value));
    }
}

class StringNode extends ExpressionNode {

    String content;
    void compile(CodeWriter cw) throws SyntaxErrorException {
        cw.writeInstruction("pushstr", content);      
    }
    
}
class BinaryNode extends ExpressionNode {
    Operator opt;
    ExpressionNode left, right;

    public enum Operator {
        Add, Sub, Mul, Div,
        CompareEqual, GreaterEqual, LessEqual, NotEqual, Greater, Less,
        BitAnd, BitOr, BitXor, And, Or, Mod, Assign, Bracket
    }

    public BinaryNode() {
        this.opt = null;
        this.left = null;
        this.right = null;
        this.pos = -1;
    }

    public BinaryNode(Operator opt, ExpressionNode left, ExpressionNode right,
            int pos) {
        this.opt = opt;
        this.left = left;
        this.right = right;
        this.pos = pos;
    }

    void compile(CodeWriter cw) throws SyntaxErrorException {
        
        switch (this.opt) {
        case Add:
            this.left.compile(cw);
            this.right.compile(cw);
            cw.writeInstruction("add");
            break;
        case Sub:
            this.left.compile(cw);
            this.right.compile(cw);
            cw.writeInstruction("sub");
            break;
        case Mul:
            this.left.compile(cw);
            this.right.compile(cw);
            cw.writeInstruction("mul");
            break;
        case Div:
            this.left.compile(cw);
            this.right.compile(cw);
            cw.writeInstruction("div");
            break;
        case And:
            this.left.compile(cw);
            this.right.compile(cw);
            cw.writeInstruction("and");
            break;
        case BitAnd:
            this.left.compile(cw);
            this.right.compile(cw);
            cw.writeInstruction("band");
            break;
        case BitOr:
            this.left.compile(cw);
            this.right.compile(cw);
            cw.writeInstruction("bor");
            break;
        case BitXor:
            this.left.compile(cw);
            this.right.compile(cw);
            cw.writeInstruction("bxor");
            break;
        case CompareEqual:
            this.left.compile(cw);
            this.right.compile(cw);
            cw.writeInstruction("eql");
            break;
        case Greater:
            this.left.compile(cw);
            this.right.compile(cw);
            cw.writeInstruction("gt");
            break;
        case GreaterEqual:
            this.left.compile(cw);
            this.right.compile(cw);
            cw.writeInstruction("ge");
            break;
        case Less:
            this.left.compile(cw);
            this.right.compile(cw);
            cw.writeInstruction("lt");
            break;
        case LessEqual:
            this.left.compile(cw);
            this.right.compile(cw);
            cw.writeInstruction("le");
            break;
        case NotEqual:
            this.left.compile(cw);
            this.right.compile(cw);
            cw.writeInstruction("neq");
            break;
        case Or:
            this.left.compile(cw);
            this.right.compile(cw);
            cw.writeInstruction("or");
            break;
        case Mod:
            this.left.compile(cw);
            this.right.compile(cw);
            cw.writeInstruction("mod");
            break;  
        case Assign: 
            ExpressionNode en = this.left;
            if (en instanceof VariableNode) {
                this.right.compile(cw);
                VariableNode vn = (VariableNode) this.left; 
                cw.writeInstruction("store", vn.varName);
            } else if (en instanceof IndexExpressionNode) {
                IndexExpressionNode idxn = (IndexExpressionNode) this.left;
                this.right.compile(cw);
                idxn.index.compile(cw);
                idxn.object.compile(cw);
                
                
                cw.writeInstruction("storeIdx");
            } else {
                throw new SyntaxErrorException(0);    
            }
            break;
        default:
            break;

        }

    }

}

class InovocationNode extends ExpressionNode {
    String functionName;
    ExpressionNode argument;

    public InovocationNode(String fun, ExpressionNode arg) {
        this.functionName = fun;
        this.argument = arg;
    }

    void compile(CodeWriter cw) throws SyntaxErrorException {
        this.argument.compile(cw);
        cw.writeInstruction("call", this.functionName);
    }

}

class UnaryNode extends ExpressionNode {
    Operator opt;
    ExpressionNode node;

    public enum Operator {
        neg, not
    }

    @Override
    void compile(CodeWriter cw) throws SyntaxErrorException {
        this.node.compile(cw);
        if (opt == Operator.neg)
            cw.writeInstruction("neg");
        else
            cw.writeInstruction("not");
    }
}

class VariableNode extends ExpressionNode {

    String varName;

    void compile(CodeWriter cw) {
        cw.writeInstruction("load", varName);
    }

}