import java.util.ArrayList;
import java.util.List;


public class Code {

    List<Instruction> instructionList = new ArrayList<Instruction>();
    int size() {
        return instructionList.size();
    }
    
    
}
class CodeWriter {
    ArrayList<ArrayList<Instruction>> breakLists = new ArrayList<ArrayList<Instruction>>();
    ArrayList<ArrayList<Instruction>> continueLists = new ArrayList<ArrayList<Instruction>>();
    Code currCode = new Code();
    public int getCurrentLineIndex() {
        return currCode.instructionList.size();
    }
    
    public void writeInstruction(Instruction instr) {
        currCode.instructionList.add(instr);
    }
    
    public void writeInstruction(String name) {
        Instruction instr = new Instruction();
        instr.instructName = name;
        writeInstruction(instr);
    }
    public void writeInstruction(String name, String arg) {
        writeInstruction(new Instruction(name, arg));
    }
    
    public Code getCode() {
        return currCode;
    }
    
}

class Instruction {
    String instructName;
    String argument;
    
    public Instruction(){
        
    }
    
    public Instruction(String name, String arg) {
        this.instructName = name;
        this.argument = arg;
    }
}
