
public class SyntaxErrorException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -3466151346451368332L;

    public int pos;
    public SyntaxErrorException(int pos){
        this.pos = pos;
    }
}
