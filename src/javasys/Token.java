package javasys;

public class Token {

    public int kind;
    public int beginLine;
    public int beginColumn;
    public int endLine;
    public int endColumn;

    public Token(int kind, String image) {
        this.kind = kind;
        this.image = image;
        System.out.println(image);
    }
    int realKind = JavaParserConstants.GT;
    /**
     * The string image of the token.
     */
    public String image;
    public Token next;
    /**
     * This field is used to access special tokens that occur prior to this
     * token, but after the immediately preceding regular (non-special) token.
     * If there are no such special tokens, this field is set to null.
     * When there are more than one such special token, this field refers
     * to the last of these special tokens, which in turn refers to the next
     * previous special token through its specialToken field, and so on
     * until the first special token (whose specialToken field is null).
     * The next fields of special tokens refer to other special tokens that
     * immediately follow it (without an intervening regular token).  If there
     * is no such token, this field is null.
     */
    public Token specialToken;

    public Token() {
        System.out.println("new null token");
    }
}

