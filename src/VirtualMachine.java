import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VirtualMachine {

    public void execute(Code code) throws RuntimeException {
        int programCounter = 0;
        ArrayList<RuntimeObject> stack = new ArrayList<RuntimeObject>();
        HashMap<String, RuntimeObject> heap = new HashMap<String, RuntimeObject>();
        while (programCounter < code.size()) {
            Instruction instr = code.instructionList.get(programCounter);
            if (instr.instructName.equals("push")) {
                RuntimeObject ro = new RuntimeObject();
                ro.FloatValue = Float.parseFloat(instr.argument);
                ro.type = RuntimeObject.ContentType.Float;
                stack.add(ro);
            } else if (instr.instructName.equals("pushstr")) {
                RuntimeObject ro = new RuntimeObject();
                ro.StringValue = instr.argument;
                ro.type = RuntimeObject.ContentType.String;
                stack.add(ro);
            } else if (instr.instructName.equals("neg")) {

                if (stack.get(stack.size() - 1).type == RuntimeObject.ContentType.Float) {
                    RuntimeObject obj = new RuntimeObject();
                    obj.type = RuntimeObject.ContentType.Float;
                    obj.FloatValue = -stack.get(stack.size() - 1).FloatValue;
                    stack.remove(stack.size() - 1);
                    stack.add(obj);
                }

            } else if (instr.instructName.equals("not")) {
                if (stack.get(stack.size() - 1).type == RuntimeObject.ContentType.Float) {
                    if (stack.get(stack.size() - 1).FloatValue == 0) {
                        RuntimeObject obj = new RuntimeObject();
                        obj.type = RuntimeObject.ContentType.Float;
                        obj.FloatValue = 1.0f;
                        stack.remove(stack.size() - 1);
                        stack.add(obj);
                    } else {
                        RuntimeObject obj = new RuntimeObject();
                        obj.type = RuntimeObject.ContentType.Float;
                        obj.FloatValue = 0.0f;
                        stack.remove(stack.size() - 1);
                        stack.add(obj);
                    }
                } else
                    throw new RuntimeException("type mismatch");
            } else if (instr.instructName.equals("add")) {
                if (stack.get(stack.size() - 1).type == RuntimeObject.ContentType.Float) {
                    RuntimeObject obj = new RuntimeObject();
                    obj.type = RuntimeObject.ContentType.Float;
                    obj.FloatValue = stack.get(stack.size() - 2).FloatValue
                            + stack.get(stack.size() - 1).FloatValue;
                    stack.remove(stack.size() - 1);
                    stack.remove(stack.size() - 1);
                    stack.add(obj);
                } else {
                    RuntimeObject obj = new RuntimeObject();
                    obj.type = RuntimeObject.ContentType.String;
                    obj.StringValue = stack.get(stack.size() - 2).StringValue
                            + stack.get(stack.size() - 1).StringValue;
                    stack.remove(stack.size() - 1);
                    stack.remove(stack.size() - 1);
                    stack.add(obj);
                }
            } else if (instr.instructName.equals("sub")) {
                if (stack.get(stack.size() - 1).type == RuntimeObject.ContentType.Float) {
                    RuntimeObject obj = new RuntimeObject();
                    obj.type = RuntimeObject.ContentType.Float;
                    obj.FloatValue = stack.get(stack.size() - 2).FloatValue
                            - stack.get(stack.size() - 1).FloatValue;
                    stack.remove(stack.size() - 1);
                    stack.remove(stack.size() - 1);
                    stack.add(obj);
                } else {
                    throw new RuntimeException("type mismatch");
                }
            } else if (instr.instructName.equals("mul")) {
                if (stack.get(stack.size() - 1).type == RuntimeObject.ContentType.Float) {
                    RuntimeObject obj = new RuntimeObject();
                    obj.type = RuntimeObject.ContentType.Float;
                    obj.FloatValue = stack.get(stack.size() - 2).FloatValue
                            * stack.get(stack.size() - 1).FloatValue;
                    stack.remove(stack.size() - 1);
                    stack.remove(stack.size() - 1);
                    stack.add(obj);
                } else {
                    throw new RuntimeException("type mismatch");
                }
            } else if (instr.instructName.equals("div")) {
                if (stack.get(stack.size() - 1).type == RuntimeObject.ContentType.Float) {
                    RuntimeObject obj = new RuntimeObject();
                    obj.type = RuntimeObject.ContentType.Float;
                    obj.FloatValue = stack.get(stack.size() - 2).FloatValue
                            / stack.get(stack.size() - 1).FloatValue;
                    stack.remove(stack.size() - 1);
                    stack.remove(stack.size() - 1);
                    stack.add(obj);
                } else {
                    throw new RuntimeException("type mismatch");
                }
            } else if (instr.instructName.equals("mod")) {
                if (stack.get(stack.size() - 1).type == RuntimeObject.ContentType.Float) {
                    RuntimeObject obj = new RuntimeObject();
                    obj.type = RuntimeObject.ContentType.Float;
                    obj.FloatValue = stack.get(stack.size() - 2).FloatValue
                            % stack.get(stack.size() - 1).FloatValue;
                    stack.remove(stack.size() - 1);
                    stack.remove(stack.size() - 1);
                    stack.add(obj);
                } else {
                    throw new RuntimeException("type mismatch");
                }
            } else if (instr.instructName.equals("and")) {
                if (stack.get(stack.size() - 1).type == RuntimeObject.ContentType.Float) {
                    RuntimeObject obj = new RuntimeObject();
                    obj.type = RuntimeObject.ContentType.Float;
                    obj.FloatValue = (stack.get(stack.size() - 2).FloatValue != 0 && stack
                            .get(stack.size() - 1).FloatValue != 0) ? 1.0f
                            : 0.0f;
                    stack.remove(stack.size() - 1);
                    stack.remove(stack.size() - 1);
                    stack.add(obj);
                } else {
                    throw new RuntimeException("type mismatch");
                }

            } else if (instr.instructName.equals("or")) {
                if (stack.get(stack.size() - 1).type == RuntimeObject.ContentType.Float) {
                    RuntimeObject obj = new RuntimeObject();
                    obj.type = RuntimeObject.ContentType.Float;
                    obj.FloatValue = (stack.get(stack.size() - 2).FloatValue != 0 || stack
                            .get(stack.size() - 1).FloatValue != 0) ? 1.0f
                            : 0.0f;
                    stack.remove(stack.size() - 1);
                    stack.remove(stack.size() - 1);
                    stack.add(obj);
                } else {
                    throw new RuntimeException("type mismatch");
                }
            } else if (instr.instructName.equals("eql")) {
                if (stack.get(stack.size() - 1).type == RuntimeObject.ContentType.Float) {
                    RuntimeObject obj = new RuntimeObject();
                    obj.type = RuntimeObject.ContentType.Float;
                    obj.FloatValue = (stack.get(stack.size() - 2).FloatValue == stack
                            .get(stack.size() - 1).FloatValue) ? 1.0f : 0.0f;
                    stack.remove(stack.size() - 1);
                    stack.remove(stack.size() - 1);
                    stack.add(obj);
                } else {
                    throw new RuntimeException("type mismatch");
                }
            } else if (instr.instructName.equals("gt")) {
                if (stack.get(stack.size() - 1).type == RuntimeObject.ContentType.Float) {
                    RuntimeObject obj = new RuntimeObject();
                    obj.type = RuntimeObject.ContentType.Float;
                    obj.FloatValue = (stack.get(stack.size() - 2).FloatValue > stack
                            .get(stack.size() - 1).FloatValue) ? 1.0f : 0.0f;
                    stack.remove(stack.size() - 1);
                    stack.remove(stack.size() - 1);
                    stack.add(obj);
                } else {
                    throw new RuntimeException("type mismatch");
                }
            } else if (instr.instructName.equals("ge")) {
                if (stack.get(stack.size() - 1).type == RuntimeObject.ContentType.Float) {
                    RuntimeObject obj = new RuntimeObject();
                    obj.type = RuntimeObject.ContentType.Float;
                    obj.FloatValue = (stack.get(stack.size() - 2).FloatValue >= stack
                            .get(stack.size() - 1).FloatValue) ? 1.0f : 0.0f;
                    stack.remove(stack.size() - 1);
                    stack.remove(stack.size() - 1);
                    stack.add(obj);
                } else {
                    throw new RuntimeException("type mismatch");
                }
            } else if (instr.instructName.equals("lt")) {
                if (stack.get(stack.size() - 1).type == RuntimeObject.ContentType.Float) {
                    RuntimeObject obj = new RuntimeObject();
                    obj.type = RuntimeObject.ContentType.Float;
                    obj.FloatValue = (stack.get(stack.size() - 2).FloatValue < stack
                            .get(stack.size() - 1).FloatValue) ? 1.0f : 0.0f;
                    stack.remove(stack.size() - 1);
                    stack.remove(stack.size() - 1);
                    stack.add(obj);
                } else {
                    throw new RuntimeException("type mismatch");
                }
            } else if (instr.instructName.equals("le")) {
                if (stack.get(stack.size() - 1).type == RuntimeObject.ContentType.Float) {
                    RuntimeObject obj = new RuntimeObject();
                    obj.type = RuntimeObject.ContentType.Float;
                    obj.FloatValue = (stack.get(stack.size() - 2).FloatValue <= stack
                            .get(stack.size() - 1).FloatValue) ? 1.0f : 0.0f;
                    stack.remove(stack.size() - 1);
                    stack.remove(stack.size() - 1);
                    stack.add(obj);
                } else {
                    throw new RuntimeException("type mismatch");
                }
            } else if (instr.instructName.equals("neq")) {
                if (stack.get(stack.size() - 1).type == RuntimeObject.ContentType.Float) {
                    RuntimeObject obj = new RuntimeObject();
                    obj.type = RuntimeObject.ContentType.Float;
                    obj.FloatValue = (stack.get(stack.size() - 2).FloatValue != stack
                            .get(stack.size() - 1).FloatValue) ? 1.0f : 0.0f;
                    stack.remove(stack.size() - 1);
                    stack.remove(stack.size() - 1);
                    stack.add(obj);
                } else {
                    throw new RuntimeException("type mismatch");
                }
            } else if (instr.instructName.equals("band")) {
                if (stack.get(stack.size() - 1).type == RuntimeObject.ContentType.Float) {
                    RuntimeObject obj = new RuntimeObject();
                    obj.type = RuntimeObject.ContentType.Float;
                    obj.FloatValue = (float) (((int) ((float) stack.get(stack
                            .size() - 2).FloatValue) & (int) ((float) stack
                            .get(stack.size() - 1).FloatValue)));
                    stack.remove(stack.size() - 1);
                    stack.remove(stack.size() - 1);
                    stack.add(obj);
                } else {
                    throw new RuntimeException("type mismatch");
                }
            } else if (instr.instructName.equals("bor")) {
                if (stack.get(stack.size() - 1).type == RuntimeObject.ContentType.Float) {
                    RuntimeObject obj = new RuntimeObject();
                    obj.type = RuntimeObject.ContentType.Float;
                    obj.FloatValue = (float) (((int) ((float) stack.get(stack
                            .size() - 2).FloatValue) | (int) ((float) stack
                            .get(stack.size() - 1).FloatValue)));
                    stack.remove(stack.size() - 1);
                    stack.remove(stack.size() - 1);
                    stack.add(obj);
                } else {
                    throw new RuntimeException("type mismatch");
                }
            } else if (instr.instructName.equals("bxor")) {
                if (stack.get(stack.size() - 1).type == RuntimeObject.ContentType.Float) {
                    RuntimeObject obj = new RuntimeObject();
                    obj.type = RuntimeObject.ContentType.Float;
                    obj.FloatValue = (float) (((int) ((float) stack.get(stack
                            .size() - 2).FloatValue) ^ (int) ((float) stack
                            .get(stack.size() - 1).FloatValue)));
                    stack.remove(stack.size() - 1);
                    stack.remove(stack.size() - 1);
                    stack.add(obj);
                } else {
                    throw new RuntimeException("type mismatch");
                }
            } else if (instr.instructName.equals("call")) {
                String functionName = instr.argument;
                if (functionName.equals("sin")) {
                    if (stack.get(stack.size() - 1).type == RuntimeObject.ContentType.Float) {
                        RuntimeObject obj = new RuntimeObject();
                        obj.type = RuntimeObject.ContentType.Float;
                        obj.FloatValue = (float) Math.sin(stack.get(stack
                                .size() - 1).FloatValue);
                        stack.remove(stack.size() - 1);
                        stack.add(obj);
                    } else {
                        throw new RuntimeException("type mismatch");
                    }
                } else if (functionName.equals("print")) {
                    RuntimeObject argObj = stack.get(stack.size() - 1);
                    RuntimeObject obj = new RuntimeObject();
                    obj.type = RuntimeObject.ContentType.Float;
                    obj.FloatValue = 0.0f;
                    stack.remove(stack.size() - 1);
                    stack.add(obj);
                    System.out.print(argObj.toString());
                }  else if (functionName.equals("println")) {
                    RuntimeObject argObj = stack.get(stack.size() - 1);
                    RuntimeObject obj = new RuntimeObject();
                    obj.type = RuntimeObject.ContentType.Float;
                    obj.FloatValue = 0.0f;
                    stack.remove(stack.size() - 1);
                    stack.add(obj);
                    System.out.println(argObj.toString());
                } else if (functionName.equals("str")) {
                    RuntimeObject argObj = stack.get(stack.size() - 1);
                    if (argObj.type == RuntimeObject.ContentType.Float) {
                        RuntimeObject obj = new RuntimeObject();
                        obj.type = RuntimeObject.ContentType.String;
                        obj.StringValue = Float.toString(argObj.FloatValue);
                        stack.remove(stack.size() - 1);
                        stack.add(obj);
                    } else if (argObj.type == RuntimeObject.ContentType.String){
                        
                    } else {
                        throw new RuntimeException("type mismatch");
                    }
                } else if (functionName.equals("num")) {
                    RuntimeObject argObj = stack.get(stack.size() - 1);
                    if (argObj.type == RuntimeObject.ContentType.String) {
                        RuntimeObject obj = new RuntimeObject();
                        obj.type = RuntimeObject.ContentType.Float;
                        obj.FloatValue = Float.parseFloat(argObj.StringValue);
                        stack.remove(stack.size() - 1);
                        stack.add(obj);
                    } else if (argObj.type == RuntimeObject.ContentType.Float){
                        
                    } else {
                        throw new RuntimeException("type mismatch");
                    }
                } else if (functionName.equals("cos")) {
                    if (stack.get(stack.size() - 1).type == RuntimeObject.ContentType.Float) {
                        RuntimeObject obj = new RuntimeObject();
                        obj.type = RuntimeObject.ContentType.Float;
                        obj.FloatValue = (float) Math.cos(stack.get(stack
                                .size() - 1).FloatValue);
                        stack.remove(stack.size() - 1);
                        stack.add(obj);
                    } else {
                        throw new RuntimeException("type mismatch");
                    }
                } else if (functionName.equals("log")) {
                    if (stack.get(stack.size() - 1).type == RuntimeObject.ContentType.Float) {
                        RuntimeObject obj = new RuntimeObject();
                        obj.type = RuntimeObject.ContentType.Float;
                        obj.FloatValue = (float) Math.log(stack.get(stack
                                .size() - 1).FloatValue);
                        stack.remove(stack.size() - 1);
                        stack.add(obj);
                    } else {
                        throw new RuntimeException("type mismatch");
                    }
                } else if (functionName.equals("sqrt")) {
                    RuntimeObject topObj = stack.get(stack.size() - 1);
                    if (topObj.type == RuntimeObject.ContentType.Float) {
                        RuntimeObject obj = new RuntimeObject();
                        obj.type = RuntimeObject.ContentType.Float;
                        obj.FloatValue = (float) Math.sqrt(topObj.FloatValue);
                        stack.remove(stack.size() - 1);
                        stack.add(obj);
                    } else {
                        throw new RuntimeException("type mismatch");
                    }
                } else {
                    throw new RuntimeException("unknown function \""
                            + functionName + "\"");
                }
            } else if (instr.instructName.equals("load")) {
                String varName = instr.argument;
                if (heap.containsKey(varName))
                    stack.add(heap.get(varName));
                else {
                    throw new RuntimeException("undeclared variable \""
                            + varName + "\"");
                }
            } else if (instr.instructName.equals("store")) {
                heap.put(instr.argument, stack.get(stack.size() - 1));
                stack.remove(stack.size() - 1);
            } else if (instr.instructName.equals("jf")) {
                RuntimeObject topObj = stack.get(stack.size() - 1);
                if (topObj.type == RuntimeObject.ContentType.Float) {
                    float top = topObj.FloatValue;
                    stack.remove(stack.size() - 1);
                    if (top == 0.0f) {
                        programCounter = Integer.parseInt(instr.argument) - 1;
                    }
                } else {
                    throw new RuntimeException(
                            "condition must evaluate to a numeric");
                }
            } else if (instr.instructName.equals("jt")) {
                RuntimeObject topObj = stack.get(stack.size() - 1);
                if (topObj.type == RuntimeObject.ContentType.Float) {
                    float top = topObj.FloatValue;
                    stack.remove(stack.size() - 1);
                    if (top != 0.0f) {
                        programCounter = Integer.parseInt(instr.argument) - 1;
                    }
                } else {
                    throw new RuntimeException(
                            "condition must evaluate to a numeric");
                }
            } else if (instr.instructName.equals("jmp")) {
                programCounter = Integer.parseInt(instr.argument) - 1;
            }
            programCounter++;
        }
   
//        for (Map.Entry<String, RuntimeObject> map : heap.entrySet()) {
//            System.out.println(map.getKey() + "= "
//                    + map.getValue().toString());
//        }
   
    }
}

class RuntimeObject {
    String StringValue;
    float FloatValue;
    ContentType type;

    public String toString() {
        if (type == ContentType.String) {
            return this.StringValue;
        } else {
            return Float.toString(this.FloatValue);
        }
    }

    enum ContentType {
        String, Float,
    }
}
