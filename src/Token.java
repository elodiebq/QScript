
public class Token {
    public String content;
    TokenType type;
    int pos;

    public Token(String content, TokenType type, int pos) {
        this.content = content;
        this.type = type;
        this.pos = pos;
    }

    public enum TokenType {
        Number,Comma,String, Add, Sub, Mul, Div, Mod, LBrace, RBrace, LBracket, RBracket, LParent, RParent, Assign, Semicolon, CompareEqual, GreaterEqual, LessEqual, NotEqual, Greater, Less, BitAnd, BitOr, BitXor, And, Or, Not, Identifier, EndofFile
    }
}
