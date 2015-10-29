import java.util.ArrayList;
import java.util.List;

public class Lexer {
    public List<Token> Parse(String input) throws SyntaxErrorException {
        ArrayList<Token> rt = new ArrayList<Token>();
        String tmp = "";
        int state = 0, ptr = 0;
        while (ptr < input.length()) {
            char curChar = input.charAt(ptr);
            char nextChar = '\0';
            if (ptr < input.length() - 1)
                nextChar = input.charAt(ptr + 1);
            if (state == 0) {
                if (Character.isDigit(curChar)) {
                    state = 1;
                } else if (curChar == ' ') {
                    ptr++;
                } else if (curChar == '+' || curChar == '-' || curChar == '*'
                        || curChar == '/' || curChar == '(' || curChar == ')'
                        || curChar == '=' || curChar == ';' || curChar == '!'
                        || curChar == '{' || curChar == '}' || curChar == '>'
                        || curChar == '<' || curChar == '&' || curChar == '|'
                        || curChar == '^' || curChar == '%') {
                    state = 2;
                } else if (Character.isAlphabetic(curChar) || curChar == '_') {
                    state = 3;
                } else {
                    throw new SyntaxErrorException(rt.size() - 1);
                }
            } else if (state == 1) {
                if (Character.isDigit(curChar)) {
                    tmp += curChar;
                    ptr++;
                } else {
                    rt.add(new Token(tmp, Token.TokenType.Number, ptr));
                    tmp = "";
                    state = 0;
                }
            } else if (state == 2) {

                if (curChar == '+') {
                    rt.add(new Token(curChar + "", Token.TokenType.Add, ptr));
                } else if (curChar == '-') {
                    rt.add(new Token(curChar + "", Token.TokenType.Sub, ptr));
                } else if (curChar == '*') {
                    rt.add(new Token(curChar + "", Token.TokenType.Mul, ptr));
                } else if (curChar == '/') {
                    rt.add(new Token(curChar + "", Token.TokenType.Div, ptr));
                } else if (curChar == '(') {
                    rt.add(new Token(curChar + "", Token.TokenType.LParent, ptr));
                } else if (curChar == ')') {
                    rt.add(new Token(curChar + "", Token.TokenType.RParent, ptr));
                } else if (curChar == '!') {
                    if (nextChar == '=') {
                        rt.add(new Token("!=", Token.TokenType.NotEqual, ptr));
                        ptr++;
                    } else {
                        rt.add(new Token(curChar + "", Token.TokenType.Not, ptr));
                    }
                } else if (curChar == '=') {
                    if (nextChar == '=') {
                        rt.add(new Token("==", Token.TokenType.CompareEqual,
                                ptr));
                        ptr++;
                    } else {
                        rt.add(new Token(curChar + "", Token.TokenType.Assign,
                                ptr));
                    }
                } else if (curChar == '>') {
                    if (nextChar == '=') {
                        rt.add(new Token(">=", Token.TokenType.GreaterEqual,
                                ptr));
                        ptr++;
                    } else {
                        rt.add(new Token(curChar + "", Token.TokenType.Greater,
                                ptr));
                    }
                } else if (curChar == '<') {
                    if (nextChar == '=') {
                        rt.add(new Token("<=", Token.TokenType.LessEqual, ptr));
                        ptr++;
                    } else {
                        rt.add(new Token(curChar + "", Token.TokenType.Less,
                                ptr));
                    }
                } else if (curChar == '&') {
                    if (nextChar == '&') {
                        rt.add(new Token("&&", Token.TokenType.And, ptr));
                        ptr++;
                    } else {
                        rt.add(new Token("&", Token.TokenType.BitAnd, ptr));
                    }

                } else if (curChar == '|') {
                    if (nextChar == '|') {
                        rt.add(new Token("||", Token.TokenType.Or, ptr));
                        ptr++;
                    } else {
                        rt.add(new Token("|", Token.TokenType.BitOr, ptr));
                    }

                } else if (curChar == '^') {
                    rt.add(new Token(curChar + "", Token.TokenType.BitXor, ptr));
                } else if (curChar == '%') {
                    rt.add(new Token(curChar + "", Token.TokenType.Mod, ptr));
                }else if (curChar == ';') {
                    rt.add(new Token(curChar + "", Token.TokenType.Semicolon,
                            ptr));
                } else if (curChar == '{') {
                    rt.add(new Token(curChar + "", Token.TokenType.LBrace, ptr));
                } else if (curChar == '}') {
                    rt.add(new Token(curChar + "", Token.TokenType.RBrace, ptr));
                } else {
                    throw new SyntaxErrorException(rt.size() - 1);
                }
                ptr++;
                state = 0;
            } else {
                if (Character.isAlphabetic(curChar) || curChar == '_'
                        || Character.isDigit(curChar)) {

                    tmp += input.charAt(ptr);
                    ptr++;

                } else {
                    Token t = new Token(tmp, Token.TokenType.Identifier, ptr);
                    rt.add(t);
                    tmp = "";
                    state = 0;
                }

            }
        }
        if (state == 1) {
            rt.add(new Token(tmp, Token.TokenType.Number, ptr));
        } else if (state == 3) {
            rt.add(new Token(tmp, Token.TokenType.Identifier, ptr));
        }

        return rt;
    }

    public static void main(String[] args) {
        Lexer testLexer = new Lexer();
        Parser parser = new Parser();
        VirtualMachine vm = new VirtualMachine();
        try {
            String input = "x = 0;sum = 0; do {if(x%2 == 0) {sum = sum + x; x = x + 1; continue;} x = x + 1;}while(x < 10);";
            List<Token> tokens = testLexer.Parse(input);
            ProgramNode syntaxTree = parser.parseProgram(tokens);
            CodeWriter cw = new CodeWriter();
            syntaxTree.compile(cw);
            int i = 0;
            for (Instruction ist : cw.getCode().instructionList) {
                System.out.println(i + ": " + ist.instructName + " "
                        + ist.argument);
                i++;
            }

            vm.execute(cw.getCode());
            // System.out.print(parser.parseProgram(tokens).children.get(0).varName.toString());
             for (Token t : tokens) {
             System.out.println(t.content);
             }
        } catch (SyntaxErrorException e) {
            System.out.println("syntax error at " + Integer.toString(e.pos));
        } catch (RuntimeException e) {
            System.out.println("Runtime error: " + e.description);
        }
    }
}
