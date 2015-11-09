import java.util.HashMap;
import java.util.List;

public class Parser {

    int ptr = 0;
    HashMap<Token.TokenType, Integer> priorityTable = new HashMap<Token.TokenType, Integer>();
    List<Token> tokens;

    public Parser() {
        priorityTable.put(Token.TokenType.Mul, 1);
        priorityTable.put(Token.TokenType.Div, 1);
        priorityTable.put(Token.TokenType.Mod, 1);
        priorityTable.put(Token.TokenType.Add, 2);
        priorityTable.put(Token.TokenType.Sub, 2);
        priorityTable.put(Token.TokenType.CompareEqual, 3);
        priorityTable.put(Token.TokenType.GreaterEqual, 3);
        priorityTable.put(Token.TokenType.Greater, 3);
        priorityTable.put(Token.TokenType.LessEqual, 3);
        priorityTable.put(Token.TokenType.Less, 3);
        priorityTable.put(Token.TokenType.NotEqual, 3);
        priorityTable.put(Token.TokenType.BitAnd, 4);
        priorityTable.put(Token.TokenType.BitOr, 5);
        priorityTable.put(Token.TokenType.BitXor, 5);
        priorityTable.put(Token.TokenType.And, 6);
        priorityTable.put(Token.TokenType.Or, 7);
        priorityTable.put(Token.TokenType.Assign, 8);
    }

    private Token.TokenType lookUpNextType() {
        if (ptr >= tokens.size()) {
            return Token.TokenType.EndofFile;
        } else
            return tokens.get(ptr).type;
    }

    private String lookUpNextContent() {
        if (ptr >= tokens.size()) {
            return "";
        } else
            return tokens.get(ptr).content;
    }

    private String readNext(Token.TokenType tp) throws SyntaxErrorException {
        if (ptr >= tokens.size() || tokens.get(ptr).type != tp) {
            throw new SyntaxErrorException(ptr);
        } else {
            String tmp = tokens.get(ptr).content;
            ptr++;
            return tmp;
        }
    }

    private String readNext(String expectedStr) throws SyntaxErrorException {
        if (ptr >= tokens.size()
                || !tokens.get(ptr).content.equals(expectedStr)) {
            throw new SyntaxErrorException(ptr);
        } else {
            String tmp = tokens.get(ptr).content;
            ptr++;
            return tmp;
        }
    }

    private BinaryNode.Operator tokenTypeToOperator(Token.TokenType tp)
            throws SyntaxErrorException {
        switch (tp) {
        case Add:
            return BinaryNode.Operator.Add;
        case Sub:
            return BinaryNode.Operator.Sub;
        case Mul:
            return BinaryNode.Operator.Mul;
        case Div:
            return BinaryNode.Operator.Div;
        case CompareEqual:
            return BinaryNode.Operator.CompareEqual;
        case GreaterEqual:
            return BinaryNode.Operator.GreaterEqual;
        case LessEqual:
            return BinaryNode.Operator.LessEqual;
        case NotEqual:
            return BinaryNode.Operator.NotEqual;
        case Greater:
            return BinaryNode.Operator.Greater;
        case Less:
            return BinaryNode.Operator.Less;
        case BitAnd:
            return BinaryNode.Operator.BitAnd;
        case BitOr:
            return BinaryNode.Operator.BitOr;
        case BitXor:
            return BinaryNode.Operator.BitXor;
        case And:
            return BinaryNode.Operator.And;
        case Or:
            return BinaryNode.Operator.Or;
        case Mod:
            return BinaryNode.Operator.Mod;
        case Assign:
            return BinaryNode.Operator.Assign;
        default:
            throw new SyntaxErrorException(0);
        }

    }

    public ExpressionNode parseExpress0() throws SyntaxErrorException {
        if (ptr >= tokens.size()) {
            throw new SyntaxErrorException(0);
        }
        if (lookUpNextType() == Token.TokenType.Sub) {
            UnaryNode n = new UnaryNode();
            readNext(Token.TokenType.Sub);
            n.node = parseExpress0();

            return n;
        } else if (tokens.get(ptr).type == Token.TokenType.Identifier) {
            String functionName = readNext(Token.TokenType.Identifier);
            if (lookUpNextType() == Token.TokenType.LParent) {
                readNext(Token.TokenType.LParent);
                ExpressionNode argVal = parseExpression();
                ExpressionNode n = new InovocationNode(functionName, argVal);
                readNext(Token.TokenType.RParent);
                return n;
            } else {
                VariableNode n = new VariableNode();
                n.varName = functionName;
                return n;
            }

        } else if (lookUpNextType() == Token.TokenType.String) {
            String content = readNext(Token.TokenType.String);
            StringNode strNode = new StringNode();
            strNode.content = content;
            return strNode;
        } else if (lookUpNextType() == Token.TokenType.Number) {

            ExpressionNode n = new NumberNode(
                    Float.parseFloat(readNext(Token.TokenType.Number)), ptr);
            return n;
        } else if (lookUpNextType() == Token.TokenType.LParent) {

            readNext(Token.TokenType.LParent);
            ExpressionNode n = parseExpression();
            readNext(Token.TokenType.RParent);
            return n;
        }
        return null;
    }

    public ExpressionNode parseExpression() throws SyntaxErrorException {
        return parseExpression(8);
    }

    public ExpressionNode parseExpression(int level)
            throws SyntaxErrorException {
        if (level == 0) {
            return parseExpress0();
        }
        ExpressionNode n0 = parseExpression(level - 1);
        if (lookUpNextType() == Token.TokenType.Assign) {
            BinaryNode nn = new BinaryNode();
            nn.left = n0;
            Token.TokenType tp = lookUpNextType();
            nn.opt = tokenTypeToOperator(tp);
            readNext(tp);
            nn.right = parseExpression(level - 1);
            return nn;
        } else {
            while (ptr < tokens.size()
                    && priorityTable.containsKey(lookUpNextType())
                    && priorityTable.get(lookUpNextType()) == level) {
                Token.TokenType tp = lookUpNextType();
                readNext(tp);
                ExpressionNode n1 = parseExpression(level - 1);
                ExpressionNode nn;
                nn = new BinaryNode(tokenTypeToOperator(tp), n0, n1, ptr);
                n0 = nn;

            }
            return n0;
        }
    }

    public ExpressionStmtNode parseExpressStmt() throws SyntaxErrorException {
        ExpressionNode n = parseExpression();
        readNext(Token.TokenType.Semicolon);
        ExpressionStmtNode nn = new ExpressionStmtNode();
        nn.value = n;
        return nn;
    }

    public IfStmtNode parseIfStmt() throws SyntaxErrorException {
        IfStmtNode ifNode = new IfStmtNode();
        readNext("if");
        readNext(Token.TokenType.LParent);
        ifNode.condition = parseExpression();
        readNext(Token.TokenType.RParent);
        ifNode.tureStmt = parseStmt();
        if (lookUpNextContent().equals("else")) {
            readNext("else");
            ifNode.falseStmt = parseStmt();
        }
        return ifNode;
    }

    public WhileStmtNode parseWhileStmt() throws SyntaxErrorException {
        WhileStmtNode whileNode = new WhileStmtNode();
        readNext("while");
        readNext(Token.TokenType.LParent);
        whileNode.condition = parseExpression();
        readNext(Token.TokenType.RParent);
        whileNode.stmt = parseStmt();
        return whileNode;
    }

    public DoWhileNode parseDoWhileStmt() throws SyntaxErrorException {
        DoWhileNode doWhileNode = new DoWhileNode();
        readNext("do");
        doWhileNode.stmt = parseStmt();
        readNext("while");
        readNext(Token.TokenType.LParent);
        doWhileNode.condition = parseExpression();
        readNext(Token.TokenType.RParent);
        readNext(";");
        return doWhileNode;
    }

    public ForStmtNode parseForStmt() throws SyntaxErrorException {
        ForStmtNode forNode = new ForStmtNode();
        readNext("for");
        readNext(Token.TokenType.LParent);
        forNode.varName = readNext(Token.TokenType.Identifier);
        readNext(Token.TokenType.Assign);
        forNode.expressionStart = parseExpression();
        readNext("to");
        forNode.expressionEnd = parseExpression();
        readNext(Token.TokenType.RParent);
        forNode.stmt = parseStmt();
        return forNode;
    }

    public BlockStmtNode parseBlockStmt() throws SyntaxErrorException {
        BlockStmtNode bn = new BlockStmtNode();
        readNext(Token.TokenType.LBrace);
        while (lookUpNextType() != Token.TokenType.RBrace) {
            bn.statements.add(parseStmt());
        }
        readNext(Token.TokenType.RBrace);
        return bn;

    }

    public BreakStmtNode parseBreakStmt() throws SyntaxErrorException {
        readNext("break");
        readNext(";");
        return new BreakStmtNode();
    }

    public ContinueStmtNode parseContinueStmt() throws SyntaxErrorException {
        readNext("continue");
        readNext(";");
        return new ContinueStmtNode();
    }

    public StmtNode parseStmt() throws SyntaxErrorException {
        if (lookUpNextContent().equals("{")) {
            return parseBlockStmt();
        } else if (lookUpNextContent().equals("if")) {
            return parseIfStmt();
        } else if (lookUpNextContent().equals("for")) {
            return parseForStmt();
        } else if (lookUpNextContent().equals("break")) {
            return parseBreakStmt();
        } else if (lookUpNextContent().equals("continue")) {
            return parseContinueStmt();
        } else if (lookUpNextContent().equals("while")) {
            return parseWhileStmt();
        } else if (lookUpNextContent().equals("do")) {
            return parseDoWhileStmt();
        } else
            return parseExpressStmt();
    }

    public ProgramNode parseProgram(List<Token> tokens)
            throws SyntaxErrorException {
        this.tokens = tokens;
        ProgramNode pn = new ProgramNode();
        while (lookUpNextType() != Token.TokenType.EndofFile) {
            pn.children.add(parseStmt());
        }
        return pn;
    }

}
