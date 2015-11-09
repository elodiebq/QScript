abstract public class ExpressionNode extends Node {

    int pos;

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
        BitAnd, BitOr, BitXor, And, Or, Mod
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
        this.left.compile(cw);
        this.right.compile(cw);
        switch (this.opt) {
        case Add:
            cw.writeInstruction("add");
            break;
        case Sub:
            cw.writeInstruction("sub");
            break;
        case Mul:
            cw.writeInstruction("mul");
            break;
        case Div:
            cw.writeInstruction("div");
            break;
        case And:
            cw.writeInstruction("and");
            break;
        case BitAnd:
            cw.writeInstruction("band");
            break;
        case BitOr:
            cw.writeInstruction("bor");
            break;
        case BitXor:
            cw.writeInstruction("bxor");
            break;
        case CompareEqual:
            cw.writeInstruction("eql");
            break;
        case Greater:
            cw.writeInstruction("gt");
            break;
        case GreaterEqual:
            cw.writeInstruction("ge");
            break;
        case Less:
            cw.writeInstruction("lt");
            break;
        case LessEqual:
            cw.writeInstruction("le");
            break;
        case NotEqual:
            cw.writeInstruction("neq");
            break;
        case Or:
            cw.writeInstruction("or");
            break;
        case Mod:
            cw.writeInstruction("mod");
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