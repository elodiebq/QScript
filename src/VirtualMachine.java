import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class VirtualMachine {

    public void execute(Code code) throws RuntimeException{
        int programCounter = 0;
        ArrayList<Float> stack = new ArrayList<Float>();
        HashMap<String, Float> heap = new HashMap<String, Float>();
        System.out.print("code size" + code.size());
        while (programCounter < code.size()) {
            Instruction instr = code.instructionList.get(programCounter);
            if (instr.instructName.equals("push")) {
                stack.add(Float.parseFloat(instr.argument));
            } else if (instr.instructName.equals("neg")) {
                stack.set(stack.size() - 1, -(stack.get(stack.size() - 1)));
            } else if (instr.instructName.equals("not")) {
                stack.set(stack.size() - 1, (stack.get(stack.size() - 1) == 0)?1.0f: 0.0f);
            } else if (instr.instructName.equals("add")) {
                stack.set(stack.size() - 2, stack.get(stack.size() - 2) + stack.get(stack.size() - 1));
                stack.remove(stack.size() - 1);
            } else if (instr.instructName.equals("sub")) {
                stack.set(stack.size() - 2, stack.get(stack.size() - 2) - stack.get(stack.size() - 1));
                stack.remove(stack.size() - 1);
            } else if (instr.instructName.equals("mul")) {
                stack.set(stack.size() - 2, stack.get(stack.size() - 2) * stack.get(stack.size() - 1));
                stack.remove(stack.size() - 1);
            } else if (instr.instructName.equals("div")) {
                stack.set(stack.size() - 2, stack.get(stack.size() - 2) / stack.get(stack.size() - 1));
                stack.remove(stack.size() - 1);
            } else if (instr.instructName.equals("mod")) {
                stack.set(stack.size() - 2, stack.get(stack.size() - 2) % stack.get(stack.size() - 1));
                stack.remove(stack.size() - 1);
            }else if (instr.instructName.equals("and")) {
                stack.set(stack.size() - 2, (stack.get(stack.size() - 2) != 0 && stack.get(stack.size() - 1) != 0)?1.0f:0.0f);
                stack.remove(stack.size() - 1);
            } else if (instr.instructName.equals("or")) {
                stack.set(stack.size() - 2, (stack.get(stack.size() - 2) != 0 || stack.get(stack.size() - 1) != 0)?1.0f:0.0f);
                stack.remove(stack.size() - 1);
            } else if (instr.instructName.equals("eql")) {
                stack.set(stack.size() - 2, ((float)stack.get(stack.size() - 2) == (float)stack.get(stack.size() - 1))?1.0f:0.0f);
                stack.remove(stack.size() - 1);
            } else if (instr.instructName.equals("gt")) {
                stack.set(stack.size() - 2, ((float)stack.get(stack.size() - 2) > (float)stack.get(stack.size() - 1))?1.0f:0.0f);
                stack.remove(stack.size() - 1);
            } else if (instr.instructName.equals("ge")) {
                stack.set(stack.size() - 2, ((float)stack.get(stack.size() - 2) >= (float)stack.get(stack.size() - 1))?1.0f:0.0f);
                stack.remove(stack.size() - 1);
            } else if (instr.instructName.equals("lt")) {
                stack.set(stack.size() - 2, ((float)stack.get(stack.size() - 2) < (float)stack.get(stack.size() - 1))?1.0f:0.0f);
                stack.remove(stack.size() - 1);
            } else if (instr.instructName.equals("le")) {
                stack.set(stack.size() - 2, ((float)stack.get(stack.size() - 2) <= (float)stack.get(stack.size() - 1))?1.0f:0.0f);
                stack.remove(stack.size() - 1);
            } else if (instr.instructName.equals("neq")) {
                stack.set(stack.size() - 2, ((float)stack.get(stack.size() - 2) != (float)stack.get(stack.size() - 1))?1.0f:0.0f);
                stack.remove(stack.size() - 1);
            } else if (instr.instructName.equals("band")) {
                stack.set(stack.size() - 2, (float)(((int)((float)stack.get(stack.size() - 2)) & (int)((float)stack.get(stack.size() - 1)))));
                stack.remove(stack.size() - 1);
            } else if (instr.instructName.equals("bor")) {
                stack.set(stack.size() - 2, (float)(((int)((float)stack.get(stack.size() - 2)) | (int)((float)stack.get(stack.size() - 1)))));
                stack.remove(stack.size() - 1);
            } else if (instr.instructName.equals("bxor")) {
                stack.set(stack.size() - 2, (float)(((int)((float)stack.get(stack.size() - 2)) ^ (int)((float)stack.get(stack.size() - 1)))));
                stack.remove(stack.size() - 1);
            } else if (instr.instructName.equals("call")) {
                String functionName = instr.argument;
                if (functionName.equals("sin")) {
                    stack.set(stack.size() - 1, (float)Math.sin(stack.get(stack.size() - 1))); 
                } else if (functionName.equals("cos")) {
                    stack.set(stack.size() - 1, (float)Math.cos(stack.get(stack.size() - 1))); 
                } else if (functionName.equals("log")) {
                    stack.set(stack.size() - 1, (float)Math.log(stack.get(stack.size() - 1))); 
                } else if (functionName.equals("sqrt")) {
                    stack.set(stack.size() - 1, (float)Math.sqrt(stack.get(stack.size() - 1))); 
                } else {
                    throw new RuntimeException("unknown function \"" +functionName +"\"");
                }
            } else if (instr.instructName.equals("load")) {
                String varName = instr.argument;
                if(heap.containsKey(varName))
                    stack.add(heap.get(varName));
                else{
                    throw new RuntimeException("undeclared variable \"" +varName +"\"");
                }
            } else if (instr.instructName.equals("store")) {
                heap.put(instr.argument, stack.get(stack.size() - 1));
                stack.remove(stack.size() - 1);
            } else if (instr.instructName.equals("jf")) {
                float top = stack.get(stack.size() - 1);
                stack.remove(stack.size() - 1);
                if (top == 0.0f) {
                    programCounter = Integer.parseInt(instr.argument) - 1;
                }
            } else if (instr.instructName.equals("jt")) {
                float top = stack.get(stack.size() - 1);
                stack.remove(stack.size() - 1);
                if (top != 0.0f) {
                    programCounter = Integer.parseInt(instr.argument) - 1;
                }
            } else if (instr.instructName.equals("jmp")) {
                programCounter = Integer.parseInt(instr.argument) - 1;
            }
            programCounter++;
        }
        if (heap.isEmpty()) {
            System.out.println(stack.get(stack.size() - 1));
        } else {
            for (Map.Entry<String, Float> map: heap.entrySet()) {
                System.out.println(map.getKey() + "= " + map.getValue());
            }
        }
    }
}
