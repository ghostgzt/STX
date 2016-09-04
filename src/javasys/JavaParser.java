package javasys;

public class JavaParser implements JavaParserConstants, Runnable {

    public void run() {
        System.out.println("start parse...");
        try {
            CompilationUnit();
            System.out.println("Java Parser Version 1.1:  Java program parsed successfully.");
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            System.out.println("Java Parser Version 1.1:  Encountered errors during parse.");
        }

    }
    /** Generated Token Manager. */
    public JavaParserTokenManager token_source;
    JavaCharStream input;
    /** Current token. */
    public Token token;
    /** Next token. */
    public Token nextToken;
    private int jj_ntk;
    private Token jj_scanpos, jj_lastpos;
    private int jj_la;
    /** Whether we are looking ahead. */
    private boolean jj_lookingAhead = false;
    private boolean jj_semLA;

    /** Reinitialise. */
    public void ReInit(java.io.InputStream stream, String encoding) throws java.io.IOException {
        input = new JavaCharStream(stream, encoding, 1, 1);
        token_source = new JavaParserTokenManager(input);
        token = new Token();
        jj_ntk = -1;
    }

    public JavaParser(String fileName) {
        try {
            System.out.println(fileName);
            ReInit(new java.io.ByteArrayInputStream(io.FileSys.getdata(fileName, 0, 0)), "Utf-8");
        } catch (Exception e) {
        }
    }

    /*
     * Program structuring syntax follows.
     */
    final public void CompilationUnit() throws ParseException {
        if (jj_2_1(2147483647)) {
            PackageDeclaration();
        }
        label_1:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case IMPORT:
                    ;
                    break;
                default:
                    break label_1;
            }
            ImportDeclaration();
        }
        label_2:
        while (true) {
            TypeDeclaration();
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case ABSTRACT:
                case CLASS:
                case ENUM:
                case FINAL:
                case INTERFACE:
                case NATIVE:
                case PRIVATE:
                case PROTECTED:
                case PUBLIC:
                case STATIC:
                case STRICTFP:
                case SYNCHRONIZED:
                case TRANSIENT:
                case VOLATILE:
                case SEMICOLON:
                case AT:
                    ;
                    break;
                default:
                    break label_2;
            }
        }
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case 127:
                jj_consume_token(127);
                break;
            default:
                ;
        }
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case STUFF_TO_IGNORE:
                jj_consume_token(STUFF_TO_IGNORE);
                break;
            default:
                ;
        }
        jj_consume_token(0);
    }

    final public void PackageDeclaration() throws ParseException {
        Modifiers();
        jj_consume_token(PACKAGE);
        Name();
        jj_consume_token(SEMICOLON);
    }

    final public void ImportDeclaration() throws ParseException {
        jj_consume_token(IMPORT);
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case STATIC:
                jj_consume_token(STATIC);
                break;
            default:
                ;
        }
        Name();
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case DOT:
                jj_consume_token(DOT);
                jj_consume_token(STAR);
                break;
            default:
                ;
        }
        jj_consume_token(SEMICOLON);
    }

    /*
     * Modifiers. We match all modifiers in a single rule to reduce the chances of
     * syntax errors for simple modifier mistakes. It will also enable us to give
     * better error messages.
     */
    final public int Modifiers() throws ParseException {
        int modifiers = 0;
        label_3:
        while (true) {
            if (!jj_2_2(2)) {
                break label_3;
            }
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case PUBLIC:
                    jj_consume_token(PUBLIC);
                    modifiers |= ModifierSet.PUBLIC;
                    break;
                case STATIC:
                    jj_consume_token(STATIC);
                    modifiers |= ModifierSet.STATIC;
                    break;
                case PROTECTED:
                    jj_consume_token(PROTECTED);
                    modifiers |= ModifierSet.PROTECTED;
                    break;
                case PRIVATE:
                    jj_consume_token(PRIVATE);
                    modifiers |= ModifierSet.PRIVATE;
                    break;
                case FINAL:
                    jj_consume_token(FINAL);
                    modifiers |= ModifierSet.FINAL;
                    break;
                case ABSTRACT:
                    jj_consume_token(ABSTRACT);
                    modifiers |= ModifierSet.ABSTRACT;
                    break;
                case SYNCHRONIZED:
                    jj_consume_token(SYNCHRONIZED);
                    modifiers |= ModifierSet.SYNCHRONIZED;
                    break;
                case NATIVE:
                    jj_consume_token(NATIVE);
                    modifiers |= ModifierSet.NATIVE;
                    break;
                case TRANSIENT:
                    jj_consume_token(TRANSIENT);
                    modifiers |= ModifierSet.TRANSIENT;
                    break;
                case VOLATILE:
                    jj_consume_token(VOLATILE);
                    modifiers |= ModifierSet.VOLATILE;
                    break;
                case STRICTFP:
                    jj_consume_token(STRICTFP);
                    modifiers |= ModifierSet.STRICTFP;
                    break;
                case AT:
                    Annotation();
                    break;
                default:
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
        {
            if (true) {
                return modifiers;
            }
        }
        throw new Error("Missing return statement in function");
    }

    /*
     * Declaration syntax follows.
     */
    final public void TypeDeclaration() throws ParseException {
        int modifiers;
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case SEMICOLON:
                jj_consume_token(SEMICOLON);
                break;
            case ABSTRACT:
            case CLASS:
            case ENUM:
            case FINAL:
            case INTERFACE:
            case NATIVE:
            case PRIVATE:
            case PROTECTED:
            case PUBLIC:
            case STATIC:
            case STRICTFP:
            case SYNCHRONIZED:
            case TRANSIENT:
            case VOLATILE:
            case AT:
                modifiers = Modifiers();
                switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                    case CLASS:
                    case INTERFACE:
                        ClassOrInterfaceDeclaration(modifiers);
                        break;
                    case ENUM:
                        EnumDeclaration(modifiers);
                        break;
                    case AT:
                        AnnotationTypeDeclaration(modifiers);
                        break;
                    default:
                        jj_consume_token(-1);
                        throw new ParseException();
                }
                break;
            default:
                jj_consume_token(-1);
                throw new ParseException();
        }
    }

    final public void ClassOrInterfaceDeclaration(int modifiers) throws ParseException {
        boolean isInterface = false;
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case CLASS:
                jj_consume_token(CLASS);
                break;
            case INTERFACE:
                jj_consume_token(INTERFACE);
                isInterface = true;
                break;
            default:
                jj_consume_token(-1);
                throw new ParseException();
        }
        jj_consume_token(IDENTIFIER);
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case LT:
                TypeParameters();
                break;
            default:
                ;
        }
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case EXTENDS:
                ExtendsList(isInterface);
                break;
            default:
                ;
        }
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case IMPLEMENTS:
                ImplementsList(isInterface);
                break;
            default:
                ;
        }
        ClassOrInterfaceBody(isInterface);
    }

    final public void ExtendsList(boolean isInterface) throws ParseException {
        boolean extendsMoreThanOne = false;
        jj_consume_token(EXTENDS);
        ClassOrInterfaceType();
        label_4:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case COMMA:
                    ;
                    break;
                default:
                    break label_4;
            }
            jj_consume_token(COMMA);
            ClassOrInterfaceType();
            extendsMoreThanOne = true;
        }
        if (extendsMoreThanOne && !isInterface) {
            if (true) {
                throw new ParseException("A class cannot extend more than one other class");
            }
        }
    }

    final public void ImplementsList(boolean isInterface) throws ParseException {
        jj_consume_token(IMPLEMENTS);
        ClassOrInterfaceType();
        label_5:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case COMMA:
                    ;
                    break;
                default:
                    break label_5;
            }
            jj_consume_token(COMMA);
            ClassOrInterfaceType();
        }
        if (isInterface) {
            if (true) {
                throw new ParseException("An interface cannot implement other interfaces");
            }
        }
    }

    final public void EnumDeclaration(int modifiers) throws ParseException {
        jj_consume_token(ENUM);
        jj_consume_token(IDENTIFIER);
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case IMPLEMENTS:
                ImplementsList(false);
                break;
            default:
                ;
        }
        EnumBody();
    }

    final public void EnumBody() throws ParseException {
        jj_consume_token(LBRACE);
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case ABSTRACT:
            case FINAL:
            case NATIVE:
            case PRIVATE:
            case PROTECTED:
            case PUBLIC:
            case STATIC:
            case STRICTFP:
            case SYNCHRONIZED:
            case TRANSIENT:
            case VOLATILE:
            case IDENTIFIER:
            case AT:
                EnumConstant();
                label_6:
                while (true) {
                    if (!jj_2_3(2)) {

                        break label_6;
                    }
                    jj_consume_token(COMMA);
                    EnumConstant();
                }
                break;
            default:
                ;
        }
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case COMMA:
                jj_consume_token(COMMA);
                break;
            default:
                ;
        }
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case SEMICOLON:
                jj_consume_token(SEMICOLON);
                label_7:
                while (true) {
                    switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                        case ABSTRACT:
                        case BOOLEAN:
                        case BYTE:
                        case CHAR:
                        case CLASS:
                        case DOUBLE:
                        case ENUM:
                        case FINAL:
                        case FLOAT:
                        case INT:
                        case INTERFACE:
                        case LONG:
                        case NATIVE:
                        case PRIVATE:
                        case PROTECTED:
                        case PUBLIC:
                        case SHORT:
                        case STATIC:
                        case STRICTFP:
                        case SYNCHRONIZED:
                        case TRANSIENT:
                        case VOID:
                        case VOLATILE:
                        case IDENTIFIER:
                        case LBRACE:
                        case SEMICOLON:
                        case AT:
                        case LT:
                            ;
                            break;
                        default:
                            break label_7;
                    }
                    ClassOrInterfaceBodyDeclaration(false);
                }
                break;
            default:
                ;
        }
        jj_consume_token(RBRACE);
    }

    final public void EnumConstant() throws ParseException {
        Modifiers();
        jj_consume_token(IDENTIFIER);
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case LPAREN:
                Arguments();
                break;
            default:
                ;
        }
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case LBRACE:
                ClassOrInterfaceBody(false);
                break;
            default:
                ;
        }
    }

    final public void TypeParameters() throws ParseException {
        jj_consume_token(LT);
        TypeParameter();
        label_8:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case COMMA:
                    ;
                    break;
                default:
                    break label_8;
            }
            jj_consume_token(COMMA);
            TypeParameter();
        }
        jj_consume_token(GT);
    }

    final public void TypeParameter() throws ParseException {
        jj_consume_token(IDENTIFIER);
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case EXTENDS:
                TypeBound();
                break;
            default:
                ;
        }
    }

    final public void TypeBound() throws ParseException {
        jj_consume_token(EXTENDS);
        ClassOrInterfaceType();
        label_9:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case BIT_AND:
                    ;
                    break;
                default:
                    break label_9;
            }
            jj_consume_token(BIT_AND);
            ClassOrInterfaceType();
        }
    }

    final public void ClassOrInterfaceBody(boolean isInterface) throws ParseException {
        jj_consume_token(LBRACE);
        label_10:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case ABSTRACT:
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case CLASS:
                case DOUBLE:
                case ENUM:
                case FINAL:
                case FLOAT:
                case INT:
                case INTERFACE:
                case LONG:
                case NATIVE:
                case PRIVATE:
                case PROTECTED:
                case PUBLIC:
                case SHORT:
                case STATIC:
                case STRICTFP:
                case SYNCHRONIZED:
                case TRANSIENT:
                case VOID:
                case VOLATILE:
                case IDENTIFIER:
                case LBRACE:
                case SEMICOLON:
                case AT:
                case LT:
                    ;
                    break;
                default:
                    break label_10;
            }
            ClassOrInterfaceBodyDeclaration(isInterface);
        }
        jj_consume_token(RBRACE);
    }

    final public void ClassOrInterfaceBodyDeclaration(boolean isInterface) throws ParseException {
        boolean isNestedInterface = false;
        int modifiers;
        if (jj_2_6(2)) {
            Initializer();
            if (isInterface) {
                if (true) {
                    throw new ParseException("An interface cannot have initializers");
                }
            }
        } else {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case ABSTRACT:
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case CLASS:
                case DOUBLE:
                case ENUM:
                case FINAL:
                case FLOAT:
                case INT:
                case INTERFACE:
                case LONG:
                case NATIVE:
                case PRIVATE:
                case PROTECTED:
                case PUBLIC:
                case SHORT:
                case STATIC:
                case STRICTFP:
                case SYNCHRONIZED:
                case TRANSIENT:
                case VOID:
                case VOLATILE:
                case IDENTIFIER:
                case AT:
                case LT:
                    modifiers = Modifiers();
                    switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                        case CLASS:
                        case INTERFACE:
                            ClassOrInterfaceDeclaration(modifiers);
                            break;
                        case ENUM:
                            EnumDeclaration(modifiers);
                            break;
                        default:
                            if (jj_2_4(2147483647)) {
                                ConstructorDeclaration();
                            } else if (jj_2_5(2147483647)) {
                                FieldDeclaration(modifiers);
                            } else {
                                switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                                    case BOOLEAN:
                                    case BYTE:
                                    case CHAR:
                                    case DOUBLE:
                                    case FLOAT:
                                    case INT:
                                    case LONG:
                                    case SHORT:
                                    case VOID:
                                    case IDENTIFIER:
                                    case LT:
                                        MethodDeclaration(modifiers);
                                        break;
                                    case AT:
                                        AnnotationTypeDeclaration(modifiers);
                                        break;
                                    default:
                                        jj_consume_token(-1);
                                        throw new ParseException();
                                }
                            }
                    }
                    break;
                case SEMICOLON:
                    jj_consume_token(SEMICOLON);
                    break;
                default:
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
    }

    final public void FieldDeclaration(int modifiers) throws ParseException {
        Type();
        VariableDeclarator();
        label_11:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case COMMA:
                    ;
                    break;
                default:
                    break label_11;
            }
            jj_consume_token(COMMA);
            VariableDeclarator();
        }
        jj_consume_token(SEMICOLON);
    }

    final public void VariableDeclarator() throws ParseException {
        VariableDeclaratorId();
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case ASSIGN:
                jj_consume_token(ASSIGN);
                VariableInitializer();
                break;
            default:
                ;
        }
    }

    final public void VariableDeclaratorId() throws ParseException {
        jj_consume_token(IDENTIFIER);
        label_12:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case LBRACKET:
                    ;
                    break;
                default:
                    break label_12;
            }
            jj_consume_token(LBRACKET);
            jj_consume_token(RBRACKET);
        }
    }

    final public void VariableInitializer() throws ParseException {
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case LBRACE:
                ArrayInitializer();
                break;
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case DOUBLE:
            case FALSE:
            case FLOAT:
            case INT:
            case LONG:
            case NEW:
            case NULL:
            case SHORT:
            case SUPER:
            case THIS:
            case TRUE:
            case VOID:
            case INTEGER_LITERAL:
            case FLOATING_POINT_LITERAL:
            case CHARACTER_LITERAL:
            case STRING_LITERAL:
            case IDENTIFIER:
            case LPAREN:
            case BANG:
            case TILDE:
            case INCR:
            case DECR:
            case PLUS:
            case MINUS:
                Expression();
                break;
            default:
                jj_consume_token(-1);
                throw new ParseException();
        }
    }

    final public void ArrayInitializer() throws ParseException {
        jj_consume_token(LBRACE);
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case DOUBLE:
            case FALSE:
            case FLOAT:
            case INT:
            case LONG:
            case NEW:
            case NULL:
            case SHORT:
            case SUPER:
            case THIS:
            case TRUE:
            case VOID:
            case INTEGER_LITERAL:
            case FLOATING_POINT_LITERAL:
            case CHARACTER_LITERAL:
            case STRING_LITERAL:
            case IDENTIFIER:
            case LPAREN:
            case LBRACE:
            case BANG:
            case TILDE:
            case INCR:
            case DECR:
            case PLUS:
            case MINUS:
                VariableInitializer();
                label_13:
                while (true) {
                    if (!jj_2_7(2)) {

                        break label_13;
                    }
                    jj_consume_token(COMMA);
                    VariableInitializer();
                }
                break;
            default:
                ;
        }
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case COMMA:
                jj_consume_token(COMMA);
                break;
            default:
                ;
        }
        jj_consume_token(RBRACE);
    }

    final public void MethodDeclaration(int modifiers) throws ParseException {
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case LT:
                TypeParameters();
                break;
            default:
                ;
        }
        ResultType();
        MethodDeclarator();
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case THROWS:
                jj_consume_token(THROWS);
                NameList();
                break;
            default:
                ;
        }
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case LBRACE:
                Block();
                break;
            case SEMICOLON:
                jj_consume_token(SEMICOLON);
                break;
            default:
                jj_consume_token(-1);
                throw new ParseException();
        }
    }

    final public void MethodDeclarator() throws ParseException {
        jj_consume_token(IDENTIFIER);
        FormalParameters();
        label_14:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case LBRACKET:
                    ;
                    break;
                default:
                    break label_14;
            }
            jj_consume_token(LBRACKET);
            jj_consume_token(RBRACKET);
        }
    }

    final public void FormalParameters() throws ParseException {
        jj_consume_token(LPAREN);
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case ABSTRACT:
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case DOUBLE:
            case FINAL:
            case FLOAT:
            case INT:
            case LONG:
            case NATIVE:
            case PRIVATE:
            case PROTECTED:
            case PUBLIC:
            case SHORT:
            case STATIC:
            case STRICTFP:
            case SYNCHRONIZED:
            case TRANSIENT:
            case VOLATILE:
            case IDENTIFIER:
            case AT:
                FormalParameter();
                label_15:
                while (true) {
                    switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                        case COMMA:
                            ;
                            break;
                        default:
                            break label_15;
                    }
                    jj_consume_token(COMMA);
                    FormalParameter();
                }
                break;
            default:
                ;
        }
        jj_consume_token(RPAREN);
    }

    final public void FormalParameter() throws ParseException {
        Modifiers();
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case FINAL:
            case AT:
                switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                    case FINAL:
                        jj_consume_token(FINAL);
                        break;
                    case AT:
                        Annotation();
                        break;
                    default:
                        jj_consume_token(-1);
                        throw new ParseException();
                }
                break;
            default:
                ;
        }
        Type();
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case ELLIPSIS:
                jj_consume_token(ELLIPSIS);
                break;
            default:
                ;
        }
        VariableDeclaratorId();
    }

    final public void ConstructorDeclaration() throws ParseException {
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case LT:
                TypeParameters();
                break;
            default:
                ;
        }
        jj_consume_token(IDENTIFIER);
        FormalParameters();
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case THROWS:
                jj_consume_token(THROWS);
                NameList();
                break;
            default:
                ;
        }
        jj_consume_token(LBRACE);
        if (jj_2_8(2147483647)) {
            ExplicitConstructorInvocation();
        }
        label_16:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case ABSTRACT:
                case ASSERT:
                case BOOLEAN:
                case BREAK:
                case BYTE:
                case CHAR:
                case CLASS:
                case CONTINUE:
                case DO:
                case DOUBLE:
                case FALSE:
                case FINAL:
                case FLOAT:
                case FOR:
                case IF:
                case INT:
                case INTERFACE:
                case LONG:
                case NATIVE:
                case NEW:
                case NULL:
                case PRIVATE:
                case PROTECTED:
                case PUBLIC:
                case RETURN:
                case SHORT:
                case STATIC:
                case STRICTFP:
                case SUPER:
                case SWITCH:
                case SYNCHRONIZED:
                case THIS:
                case THROW:
                case TRANSIENT:
                case TRUE:
                case TRY:
                case VOID:
                case VOLATILE:
                case WHILE:
                case INTEGER_LITERAL:
                case FLOATING_POINT_LITERAL:
                case CHARACTER_LITERAL:
                case STRING_LITERAL:
                case IDENTIFIER:
                case LPAREN:
                case LBRACE:
                case SEMICOLON:
                case AT:
                case INCR:
                case DECR:
                    ;
                    break;
                default:
                    break label_16;
            }
            BlockStatement();
        }
        jj_consume_token(RBRACE);
    }

    final public void ExplicitConstructorInvocation() throws ParseException {
        label_17:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case IDENTIFIER:
                    ;
                    break;
                default:
                    break label_17;
            }
            jj_consume_token(IDENTIFIER);
            jj_consume_token(DOT);
        }
        if (jj_2_9(2)) {
            jj_consume_token(THIS);
            jj_consume_token(DOT);
        }
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case LT:
                TypeArguments();
                break;
            default:
                ;
        }
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case THIS:
                jj_consume_token(THIS);
                break;
            case SUPER:
                jj_consume_token(SUPER);
                break;
            default:
                jj_consume_token(-1);
                throw new ParseException();
        }
        Arguments();
        jj_consume_token(SEMICOLON);
    }

    final public void Initializer() throws ParseException {
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case STATIC:
                jj_consume_token(STATIC);
                break;
            default:
                ;
        }
        Block();
    }

    /*
     * Type, name and expression syntax follows.
     */
    final public void Type() throws ParseException {
        if (jj_2_10(2)) {
            ReferenceType();
        } else {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case DOUBLE:
                case FLOAT:
                case INT:
                case LONG:
                case SHORT:
                    PrimitiveType();
                    break;
                default:
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
    }

    final public void ReferenceType() throws ParseException {
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case DOUBLE:
            case FLOAT:
            case INT:
            case LONG:
            case SHORT:
                PrimitiveType();
                label_18:
                while (true) {
                    jj_consume_token(LBRACKET);
                    jj_consume_token(RBRACKET);
                    if (!jj_2_11(2)) {

                        break label_18;
                    }
                }
                break;
            case IDENTIFIER:
                ClassOrInterfaceType();
                label_19:
                while (true) {
                    if (!jj_2_12(2)) {

                        break label_19;
                    }
                    jj_consume_token(LBRACKET);
                    jj_consume_token(RBRACKET);
                }
                break;
            default:
                jj_consume_token(-1);
                throw new ParseException();
        }
    }

    final public void ClassOrInterfaceType() throws ParseException {
        jj_consume_token(IDENTIFIER);
        if (jj_2_13(2)) {
            TypeArguments();
        }
        label_20:
        while (true) {
            if (!jj_2_14(2)) {

                break label_20;
            }
            jj_consume_token(DOT);
            jj_consume_token(IDENTIFIER);
            if (jj_2_15(2)) {
                TypeArguments();
            }
        }
    }

    final public void TypeArguments() throws ParseException {
        jj_consume_token(LT);
        TypeArgument();
        label_21:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case COMMA:
                    ;
                    break;
                default:
                    break label_21;
            }
            jj_consume_token(COMMA);
            TypeArgument();
        }
        jj_consume_token(GT);
    }

    final public void TypeArgument() throws ParseException {
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case DOUBLE:
            case FLOAT:
            case INT:
            case LONG:
            case SHORT:
            case IDENTIFIER:
                ReferenceType();
                break;
            case HOOK:
                jj_consume_token(HOOK);
                switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                    case EXTENDS:
                    case SUPER:
                        WildcardBounds();
                        break;
                    default:
                        ;
                }
                break;
            default:
                jj_consume_token(-1);
                throw new ParseException();
        }
    }

    final public void WildcardBounds() throws ParseException {
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case EXTENDS:
                jj_consume_token(EXTENDS);
                ReferenceType();
                break;
            case SUPER:
                jj_consume_token(SUPER);
                ReferenceType();
                break;
            default:
                jj_consume_token(-1);
                throw new ParseException();
        }
    }

    final public void PrimitiveType() throws ParseException {
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case BOOLEAN:
                jj_consume_token(BOOLEAN);
                break;
            case CHAR:
                jj_consume_token(CHAR);
                break;
            case BYTE:
                jj_consume_token(BYTE);
                break;
            case SHORT:
                jj_consume_token(SHORT);
                break;
            case INT:
                jj_consume_token(INT);
                break;
            case LONG:
                jj_consume_token(LONG);
                break;
            case FLOAT:
                jj_consume_token(FLOAT);
                break;
            case DOUBLE:
                jj_consume_token(DOUBLE);
                break;
            default:
                jj_consume_token(-1);
                throw new ParseException();
        }
    }

    final public void ResultType() throws ParseException {
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case VOID:
                jj_consume_token(VOID);
                break;
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case DOUBLE:
            case FLOAT:
            case INT:
            case LONG:
            case SHORT:
            case IDENTIFIER:
                Type();
                break;
            default:
                jj_consume_token(-1);
                throw new ParseException();
        }
    }

    final public void Name() throws ParseException {
        jj_consume_token(IDENTIFIER);
        label_22:
        while (true) {
            if (!jj_2_16(2)) {

                break label_22;
            }
            jj_consume_token(DOT);
            jj_consume_token(IDENTIFIER);
        }
    }

    final public void NameList() throws ParseException {
        Name();
        label_23:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case COMMA:
                    ;
                    break;
                default:
                    break label_23;
            }
            jj_consume_token(COMMA);
            Name();
        }
    }

    /*
     * Expression syntax follows.
     */
    final public void Expression() throws ParseException {
        ConditionalExpression();
        if (jj_2_17(2)) {
            AssignmentOperator();
            Expression();
        }
    }

    final public void AssignmentOperator() throws ParseException {
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case ASSIGN:
                jj_consume_token(ASSIGN);
                break;
            case STARASSIGN:
                jj_consume_token(STARASSIGN);
                break;
            case SLASHASSIGN:
                jj_consume_token(SLASHASSIGN);
                break;
            case REMASSIGN:
                jj_consume_token(REMASSIGN);
                break;
            case PLUSASSIGN:
                jj_consume_token(PLUSASSIGN);
                break;
            case MINUSASSIGN:
                jj_consume_token(MINUSASSIGN);
                break;
            case LSHIFTASSIGN:
                jj_consume_token(LSHIFTASSIGN);
                break;
            case RSIGNEDSHIFTASSIGN:
                jj_consume_token(RSIGNEDSHIFTASSIGN);
                break;
            case RUNSIGNEDSHIFTASSIGN:
                jj_consume_token(RUNSIGNEDSHIFTASSIGN);
                break;
            case ANDASSIGN:
                jj_consume_token(ANDASSIGN);
                break;
            case XORASSIGN:
                jj_consume_token(XORASSIGN);
                break;
            case ORASSIGN:
                jj_consume_token(ORASSIGN);
                break;
            default:
                jj_consume_token(-1);
                throw new ParseException();
        }
    }

    final public void ConditionalExpression() throws ParseException {
        ConditionalOrExpression();
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case HOOK:
                jj_consume_token(HOOK);
                Expression();
                jj_consume_token(COLON);
                Expression();
                break;
            default:
                ;
        }
    }

    final public void ConditionalOrExpression() throws ParseException {
        ConditionalAndExpression();
        label_24:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case SC_OR:
                    ;
                    break;
                default:
                    break label_24;
            }
            jj_consume_token(SC_OR);
            ConditionalAndExpression();
        }
    }

    final public void ConditionalAndExpression() throws ParseException {
        InclusiveOrExpression();
        label_25:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case SC_AND:
                    ;
                    break;
                default:
                    break label_25;
            }
            jj_consume_token(SC_AND);
            InclusiveOrExpression();
        }
    }

    final public void InclusiveOrExpression() throws ParseException {
        ExclusiveOrExpression();
        label_26:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case BIT_OR:
                    ;
                    break;
                default:
                    break label_26;
            }
            jj_consume_token(BIT_OR);
            ExclusiveOrExpression();
        }
    }

    final public void ExclusiveOrExpression() throws ParseException {
        AndExpression();
        label_27:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case XOR:
                    ;
                    break;
                default:
                    break label_27;
            }
            jj_consume_token(XOR);
            AndExpression();
        }
    }

    final public void AndExpression() throws ParseException {
        EqualityExpression();
        label_28:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case BIT_AND:
                    ;
                    break;
                default:
                    break label_28;
            }
            jj_consume_token(BIT_AND);
            EqualityExpression();
        }
    }

    final public void EqualityExpression() throws ParseException {
        InstanceOfExpression();
        label_29:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case EQ:
                case NE:
                    ;
                    break;
                default:
                    break label_29;
            }
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case EQ:
                    jj_consume_token(EQ);
                    break;
                case NE:
                    jj_consume_token(NE);
                    break;
                default:
                    jj_consume_token(-1);
                    throw new ParseException();
            }
            InstanceOfExpression();
        }
    }

    final public void InstanceOfExpression() throws ParseException {
        RelationalExpression();
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case INSTANCEOF:
                jj_consume_token(INSTANCEOF);
                Type();
                break;
            default:
                ;
        }
    }

    final public void RelationalExpression() throws ParseException {
        ShiftExpression();
        label_30:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case LT:
                case LE:
                case GE:
                case GT:
                    ;
                    break;
                default:
                    break label_30;
            }
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case LT:
                    jj_consume_token(LT);
                    break;
                case GT:
                    jj_consume_token(GT);
                    break;
                case LE:
                    jj_consume_token(LE);
                    break;
                case GE:
                    jj_consume_token(GE);
                    break;
                default:
                    jj_consume_token(-1);
                    throw new ParseException();
            }
            ShiftExpression();
        }
    }

    final public void ShiftExpression() throws ParseException {
        AdditiveExpression();
        label_31:
        while (true) {
            if (!jj_2_18(1)) {

                break label_31;
            }
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case LSHIFT:
                    jj_consume_token(LSHIFT);
                    break;
                default:
                    if (jj_2_19(1)) {
                        RSIGNEDSHIFT();
                    } else if (jj_2_20(1)) {
                        RUNSIGNEDSHIFT();
                    } else {
                        jj_consume_token(-1);
                        throw new ParseException();
                    }
            }
            AdditiveExpression();
        }
    }

    final public void AdditiveExpression() throws ParseException {
        MultiplicativeExpression();
        label_32:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case PLUS:
                case MINUS:
                    ;
                    break;
                default:
                    break label_32;
            }
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case PLUS:
                    jj_consume_token(PLUS);
                    break;
                case MINUS:
                    jj_consume_token(MINUS);
                    break;
                default:
                    jj_consume_token(-1);
                    throw new ParseException();
            }
            MultiplicativeExpression();
        }
    }

    final public void MultiplicativeExpression() throws ParseException {
        UnaryExpression();
        label_33:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case STAR:
                case SLASH:
                case REM:
                    ;
                    break;
                default:
                    break label_33;
            }
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case STAR:
                    jj_consume_token(STAR);
                    break;
                case SLASH:
                    jj_consume_token(SLASH);
                    break;
                case REM:
                    jj_consume_token(REM);
                    break;
                default:
                    jj_consume_token(-1);
                    throw new ParseException();
            }
            UnaryExpression();
        }
    }

    final public void UnaryExpression() throws ParseException {
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case PLUS:
            case MINUS:
                switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                    case PLUS:
                        jj_consume_token(PLUS);
                        break;
                    case MINUS:
                        jj_consume_token(MINUS);
                        break;
                    default:
                        jj_consume_token(-1);
                        throw new ParseException();
                }
                UnaryExpression();
                break;
            case INCR:
                PreIncrementExpression();
                break;
            case DECR:
                PreDecrementExpression();
                break;
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case DOUBLE:
            case FALSE:
            case FLOAT:
            case INT:
            case LONG:
            case NEW:
            case NULL:
            case SHORT:
            case SUPER:
            case THIS:
            case TRUE:
            case VOID:
            case INTEGER_LITERAL:
            case FLOATING_POINT_LITERAL:
            case CHARACTER_LITERAL:
            case STRING_LITERAL:
            case IDENTIFIER:
            case LPAREN:
            case BANG:
            case TILDE:
                UnaryExpressionNotPlusMinus();
                break;
            default:
                jj_consume_token(-1);
                throw new ParseException();
        }
    }

    final public void PreIncrementExpression() throws ParseException {
        jj_consume_token(INCR);
        PrimaryExpression();
    }

    final public void PreDecrementExpression() throws ParseException {
        jj_consume_token(DECR);
        PrimaryExpression();
    }

    final public void UnaryExpressionNotPlusMinus() throws ParseException {
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case BANG:
            case TILDE:
                switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                    case TILDE:
                        jj_consume_token(TILDE);
                        break;
                    case BANG:
                        jj_consume_token(BANG);
                        break;
                    default:
                        jj_consume_token(-1);
                        throw new ParseException();
                }
                UnaryExpression();
                break;
            default:
                if (jj_2_21(2147483647)) {
                    CastExpression();
                } else {
                    switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                        case BOOLEAN:
                        case BYTE:
                        case CHAR:
                        case DOUBLE:
                        case FALSE:
                        case FLOAT:
                        case INT:
                        case LONG:
                        case NEW:
                        case NULL:
                        case SHORT:
                        case SUPER:
                        case THIS:
                        case TRUE:
                        case VOID:
                        case INTEGER_LITERAL:
                        case FLOATING_POINT_LITERAL:
                        case CHARACTER_LITERAL:
                        case STRING_LITERAL:
                        case IDENTIFIER:
                        case LPAREN:
                            PostfixExpression();
                            break;
                        default:
                            jj_consume_token(-1);
                            throw new ParseException();
                    }
                }
        }
    }

// This production is to determine lookahead only.  The LOOKAHEAD specifications
// below are not used, but they are there just to indicate that we know about
// this.
    final public void CastLookahead() throws ParseException {
        if (jj_2_22(2)) {
            jj_consume_token(LPAREN);
            PrimitiveType();
        } else if (jj_2_23(2147483647)) {
            jj_consume_token(LPAREN);
            Type();
            jj_consume_token(LBRACKET);
            jj_consume_token(RBRACKET);
        } else {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case LPAREN:
                    jj_consume_token(LPAREN);
                    Type();
                    jj_consume_token(RPAREN);
                    switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                        case TILDE:
                            jj_consume_token(TILDE);
                            break;
                        case BANG:
                            jj_consume_token(BANG);
                            break;
                        case LPAREN:
                            jj_consume_token(LPAREN);
                            break;
                        case IDENTIFIER:
                            jj_consume_token(IDENTIFIER);
                            break;
                        case THIS:
                            jj_consume_token(THIS);
                            break;
                        case SUPER:
                            jj_consume_token(SUPER);
                            break;
                        case NEW:
                            jj_consume_token(NEW);
                            break;
                        case FALSE:
                        case NULL:
                        case TRUE:
                        case INTEGER_LITERAL:
                        case FLOATING_POINT_LITERAL:
                        case CHARACTER_LITERAL:
                        case STRING_LITERAL:
                            Literal();
                            break;
                        default:
                            jj_consume_token(-1);
                            throw new ParseException();
                    }
                    break;
                default:
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
    }

    final public void PostfixExpression() throws ParseException {
        PrimaryExpression();
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case INCR:
            case DECR:
                switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                    case INCR:
                        jj_consume_token(INCR);
                        break;
                    case DECR:
                        jj_consume_token(DECR);
                        break;
                    default:
                        jj_consume_token(-1);
                        throw new ParseException();
                }
                break;
            default:
                ;
        }
    }

    final public void CastExpression() throws ParseException {
        if (jj_2_24(2147483647)) {
            jj_consume_token(LPAREN);
            Type();
            jj_consume_token(RPAREN);
            UnaryExpression();
        } else {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case LPAREN:
                    jj_consume_token(LPAREN);
                    Type();
                    jj_consume_token(RPAREN);
                    UnaryExpressionNotPlusMinus();
                    break;
                default:
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
    }

    final public void PrimaryExpression() throws ParseException {
        PrimaryPrefix();
        label_34:
        while (true) {
            if (!jj_2_25(2)) {

                break label_34;
            }
            PrimarySuffix();
        }
    }

    final public void MemberSelector() throws ParseException {
        jj_consume_token(DOT);
        TypeArguments();
        jj_consume_token(IDENTIFIER);
    }

    final public void PrimaryPrefix() throws ParseException {
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case FALSE:
            case NULL:
            case TRUE:
            case INTEGER_LITERAL:
            case FLOATING_POINT_LITERAL:
            case CHARACTER_LITERAL:
            case STRING_LITERAL:
                Literal();
                break;
            default:
                if (jj_2_26(2147483647)) {
                    label_35:
                    while (true) {
                        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                            case IDENTIFIER:
                                ;
                                break;
                            default:
                                break label_35;
                        }
                        jj_consume_token(IDENTIFIER);
                        jj_consume_token(DOT);
                    }
                    jj_consume_token(THIS);
                } else {
                    switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                        case SUPER:
                            jj_consume_token(SUPER);
                            jj_consume_token(DOT);
                            jj_consume_token(IDENTIFIER);
                            break;
                        default:
                            if (jj_2_27(2147483647)) {
                                ClassOrInterfaceType();
                                jj_consume_token(DOT);
                                jj_consume_token(SUPER);
                                jj_consume_token(DOT);
                                jj_consume_token(IDENTIFIER);
                            } else {
                                switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                                    case LPAREN:
                                        jj_consume_token(LPAREN);
                                        Expression();
                                        jj_consume_token(RPAREN);
                                        break;
                                    case NEW:
                                        AllocationExpression();
                                        break;
                                    default:
                                        if (jj_2_28(2147483647)) {
                                            ResultType();
                                            jj_consume_token(DOT);
                                            jj_consume_token(CLASS);
                                        } else {
                                            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                                                case IDENTIFIER:
                                                    Name();
                                                    break;
                                                default:
                                                    jj_consume_token(-1);
                                                    throw new ParseException();
                                            }
                                        }
                                }
                            }
                    }
                }
        }
    }

    final public void PrimarySuffix() throws ParseException {
        if (jj_2_29(2147483647)) {
            jj_consume_token(DOT);
            jj_consume_token(SUPER);
        } else if (jj_2_30(2147483647)) {
            jj_consume_token(DOT);
            jj_consume_token(THIS);
        } else if (jj_2_31(2)) {
            jj_consume_token(DOT);
            AllocationExpression();
        } else if (jj_2_32(3)) {
            MemberSelector();
        } else {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case LBRACKET:
                    jj_consume_token(LBRACKET);
                    Expression();
                    jj_consume_token(RBRACKET);
                    break;
                case DOT:
                    jj_consume_token(DOT);
                    jj_consume_token(IDENTIFIER);
                    break;
                case LPAREN:
                    Arguments();
                    break;
                default:
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
    }

    final public void Literal() throws ParseException {
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case INTEGER_LITERAL:
                jj_consume_token(INTEGER_LITERAL);
                break;
            case FLOATING_POINT_LITERAL:
                jj_consume_token(FLOATING_POINT_LITERAL);
                break;
            case CHARACTER_LITERAL:
                jj_consume_token(CHARACTER_LITERAL);
                break;
            case STRING_LITERAL:
                jj_consume_token(STRING_LITERAL);
                break;
            case FALSE:
            case TRUE:
                BooleanLiteral();
                break;
            case NULL:
                NullLiteral();
                break;
            default:
                jj_consume_token(-1);
                throw new ParseException();
        }
    }

    final public void BooleanLiteral() throws ParseException {
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case TRUE:
                jj_consume_token(TRUE);
                break;
            case FALSE:
                jj_consume_token(FALSE);
                break;
            default:
                jj_consume_token(-1);
                throw new ParseException();
        }
    }

    final public void NullLiteral() throws ParseException {
        jj_consume_token(NULL);
    }

    final public void Arguments() throws ParseException {
        jj_consume_token(LPAREN);
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case DOUBLE:
            case FALSE:
            case FLOAT:
            case INT:
            case LONG:
            case NEW:
            case NULL:
            case SHORT:
            case SUPER:
            case THIS:
            case TRUE:
            case VOID:
            case INTEGER_LITERAL:
            case FLOATING_POINT_LITERAL:
            case CHARACTER_LITERAL:
            case STRING_LITERAL:
            case IDENTIFIER:
            case LPAREN:
            case BANG:
            case TILDE:
            case INCR:
            case DECR:
            case PLUS:
            case MINUS:
                ArgumentList();
                break;
            default:
                ;
        }
        jj_consume_token(RPAREN);
    }

    final public void ArgumentList() throws ParseException {
        Expression();
        label_36:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case COMMA:
                    ;
                    break;
                default:
                    break label_36;
            }
            jj_consume_token(COMMA);
            Expression();
        }
    }

    final public void AllocationExpression() throws ParseException {
        if (jj_2_33(2)) {
            jj_consume_token(NEW);
            PrimitiveType();
            ArrayDimsAndInits();
        } else {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case NEW:
                    jj_consume_token(NEW);
                    ClassOrInterfaceType();
                    switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                        case LT:
                            TypeArguments();
                            break;
                        default:
                            ;
                    }
                    switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                        case LBRACKET:
                            ArrayDimsAndInits();
                            break;
                        case LPAREN:
                            Arguments();
                            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                                case LBRACE:
                                    ClassOrInterfaceBody(false);
                                    break;
                                default:
                                    ;
                            }
                            break;
                        default:
                            jj_consume_token(-1);
                            throw new ParseException();
                    }
                    break;
                default:
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
    }

    /*
     * The third LOOKAHEAD specification below is to parse to PrimarySuffix
     * if there is an expression between the "[...]".
     */
    final public void ArrayDimsAndInits() throws ParseException {
        if (jj_2_36(2)) {
            label_37:
            while (true) {
                jj_consume_token(LBRACKET);
                Expression();
                jj_consume_token(RBRACKET);
                if (!jj_2_34(2)) {

                    break label_37;
                }
            }
            label_38:
            while (true) {
                if (!jj_2_35(2)) {

                    break label_38;
                }
                jj_consume_token(LBRACKET);
                jj_consume_token(RBRACKET);
            }
        } else {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case LBRACKET:
                    label_39:
                    while (true) {
                        jj_consume_token(LBRACKET);
                        jj_consume_token(RBRACKET);
                        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                            case LBRACKET:
                                ;
                                break;
                            default:
                                break label_39;
                        }
                    }
                    ArrayInitializer();
                    break;
                default:
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
    }

    /*
     * Statement syntax follows.
     */
    final public void Statement() throws ParseException {
        if (jj_2_37(2)) {
            LabeledStatement();
        } else {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case ASSERT:
                    AssertStatement();
                    break;
                case LBRACE:
                    Block();
                    break;
                case SEMICOLON:
                    EmptyStatement();
                    break;
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case DOUBLE:
                case FALSE:
                case FLOAT:
                case INT:
                case LONG:
                case NEW:
                case NULL:
                case SHORT:
                case SUPER:
                case THIS:
                case TRUE:
                case VOID:
                case INTEGER_LITERAL:
                case FLOATING_POINT_LITERAL:
                case CHARACTER_LITERAL:
                case STRING_LITERAL:
                case IDENTIFIER:
                case LPAREN:
                case INCR:
                case DECR:
                    StatementExpression();
                    jj_consume_token(SEMICOLON);
                    break;
                case SWITCH:
                    SwitchStatement();
                    break;
                case IF:
                    IfStatement();
                    break;
                case WHILE:
                    WhileStatement();
                    break;
                case DO:
                    DoStatement();
                    break;
                case FOR:
                    ForStatement();
                    break;
                case BREAK:
                    BreakStatement();
                    break;
                case CONTINUE:
                    ContinueStatement();
                    break;
                case RETURN:
                    ReturnStatement();
                    break;
                case THROW:
                    ThrowStatement();
                    break;
                case SYNCHRONIZED:
                    SynchronizedStatement();
                    break;
                case TRY:
                    TryStatement();
                    break;
                default:
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
    }

    final public void AssertStatement() throws ParseException {
        jj_consume_token(ASSERT);
        Expression();
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case COLON:
                jj_consume_token(COLON);
                Expression();
                break;
            default:
                ;
        }
        jj_consume_token(SEMICOLON);
    }

    final public void LabeledStatement() throws ParseException {
        jj_consume_token(IDENTIFIER);
        jj_consume_token(COLON);
        Statement();
    }

    final public void Block() throws ParseException {
        jj_consume_token(LBRACE);
        label_40:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case ABSTRACT:
                case ASSERT:
                case BOOLEAN:
                case BREAK:
                case BYTE:
                case CHAR:
                case CLASS:
                case CONTINUE:
                case DO:
                case DOUBLE:
                case FALSE:
                case FINAL:
                case FLOAT:
                case FOR:
                case IF:
                case INT:
                case INTERFACE:
                case LONG:
                case NATIVE:
                case NEW:
                case NULL:
                case PRIVATE:
                case PROTECTED:
                case PUBLIC:
                case RETURN:
                case SHORT:
                case STATIC:
                case STRICTFP:
                case SUPER:
                case SWITCH:
                case SYNCHRONIZED:
                case THIS:
                case THROW:
                case TRANSIENT:
                case TRUE:
                case TRY:
                case VOID:
                case VOLATILE:
                case WHILE:
                case INTEGER_LITERAL:
                case FLOATING_POINT_LITERAL:
                case CHARACTER_LITERAL:
                case STRING_LITERAL:
                case IDENTIFIER:
                case LPAREN:
                case LBRACE:
                case SEMICOLON:
                case AT:
                case INCR:
                case DECR:
                    ;
                    break;
                default:
                    break label_40;
            }
            BlockStatement();
        }
        jj_consume_token(RBRACE);
    }

    final public void BlockStatement() throws ParseException {
        if (jj_2_38(2147483647)) {
            LocalVariableDeclaration();
            jj_consume_token(SEMICOLON);
        } else {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case ASSERT:
                case BOOLEAN:
                case BREAK:
                case BYTE:
                case CHAR:
                case CONTINUE:
                case DO:
                case DOUBLE:
                case FALSE:
                case FLOAT:
                case FOR:
                case IF:
                case INT:
                case LONG:
                case NEW:
                case NULL:
                case RETURN:
                case SHORT:
                case SUPER:
                case SWITCH:
                case SYNCHRONIZED:
                case THIS:
                case THROW:
                case TRUE:
                case TRY:
                case VOID:
                case WHILE:
                case INTEGER_LITERAL:
                case FLOATING_POINT_LITERAL:
                case CHARACTER_LITERAL:
                case STRING_LITERAL:
                case IDENTIFIER:
                case LPAREN:
                case LBRACE:
                case SEMICOLON:
                case INCR:
                case DECR:
                    Statement();
                    break;
                case CLASS:
                case INTERFACE:
                    ClassOrInterfaceDeclaration(0);
                    break;
                default:
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
    }

    final public void LocalVariableDeclaration() throws ParseException {
        Modifiers();
        Type();
        VariableDeclarator();
        label_41:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case COMMA:
                    ;
                    break;
                default:
                    break label_41;
            }
            jj_consume_token(COMMA);
            VariableDeclarator();
        }
    }

    final public void EmptyStatement() throws ParseException {
        jj_consume_token(SEMICOLON);
    }

    final public void StatementExpression() throws ParseException {
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case INCR:
                PreIncrementExpression();
                break;
            case DECR:
                PreDecrementExpression();
                break;
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case DOUBLE:
            case FALSE:
            case FLOAT:
            case INT:
            case LONG:
            case NEW:
            case NULL:
            case SHORT:
            case SUPER:
            case THIS:
            case TRUE:
            case VOID:
            case INTEGER_LITERAL:
            case FLOATING_POINT_LITERAL:
            case CHARACTER_LITERAL:
            case STRING_LITERAL:
            case IDENTIFIER:
            case LPAREN:
                PrimaryExpression();
                switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                    case ASSIGN:
                    case INCR:
                    case DECR:
                    case PLUSASSIGN:
                    case MINUSASSIGN:
                    case STARASSIGN:
                    case SLASHASSIGN:
                    case ANDASSIGN:
                    case ORASSIGN:
                    case XORASSIGN:
                    case REMASSIGN:
                    case LSHIFTASSIGN:
                    case RSIGNEDSHIFTASSIGN:
                    case RUNSIGNEDSHIFTASSIGN:
                        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                            case INCR:
                                jj_consume_token(INCR);
                                break;
                            case DECR:
                                jj_consume_token(DECR);
                                break;
                            case ASSIGN:
                            case PLUSASSIGN:
                            case MINUSASSIGN:
                            case STARASSIGN:
                            case SLASHASSIGN:
                            case ANDASSIGN:
                            case ORASSIGN:
                            case XORASSIGN:
                            case REMASSIGN:
                            case LSHIFTASSIGN:
                            case RSIGNEDSHIFTASSIGN:
                            case RUNSIGNEDSHIFTASSIGN:
                                AssignmentOperator();
                                Expression();
                                break;
                            default:
                                jj_consume_token(-1);
                                throw new ParseException();
                        }
                        break;
                    default:
                        ;
                }
                break;
            default:
                jj_consume_token(-1);
                throw new ParseException();
        }
    }

    final public void SwitchStatement() throws ParseException {
        jj_consume_token(SWITCH);
        jj_consume_token(LPAREN);
        Expression();
        jj_consume_token(RPAREN);
        jj_consume_token(LBRACE);
        label_42:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case CASE:
                case _DEFAULT:
                    ;
                    break;
                default:
                    break label_42;
            }
            SwitchLabel();
            label_43:
            while (true) {
                switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                    case ABSTRACT:
                    case ASSERT:
                    case BOOLEAN:
                    case BREAK:
                    case BYTE:
                    case CHAR:
                    case CLASS:
                    case CONTINUE:
                    case DO:
                    case DOUBLE:
                    case FALSE:
                    case FINAL:
                    case FLOAT:
                    case FOR:
                    case IF:
                    case INT:
                    case INTERFACE:
                    case LONG:
                    case NATIVE:
                    case NEW:
                    case NULL:
                    case PRIVATE:
                    case PROTECTED:
                    case PUBLIC:
                    case RETURN:
                    case SHORT:
                    case STATIC:
                    case STRICTFP:
                    case SUPER:
                    case SWITCH:
                    case SYNCHRONIZED:
                    case THIS:
                    case THROW:
                    case TRANSIENT:
                    case TRUE:
                    case TRY:
                    case VOID:
                    case VOLATILE:
                    case WHILE:
                    case INTEGER_LITERAL:
                    case FLOATING_POINT_LITERAL:
                    case CHARACTER_LITERAL:
                    case STRING_LITERAL:
                    case IDENTIFIER:
                    case LPAREN:
                    case LBRACE:
                    case SEMICOLON:
                    case AT:
                    case INCR:
                    case DECR:
                        ;
                        break;
                    default:
                        break label_43;
                }
                BlockStatement();
            }
        }
        jj_consume_token(RBRACE);
    }

    final public void SwitchLabel() throws ParseException {
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case CASE:
                jj_consume_token(CASE);
                Expression();
                jj_consume_token(COLON);
                break;
            case _DEFAULT:
                jj_consume_token(_DEFAULT);
                jj_consume_token(COLON);
                break;
            default:
                jj_consume_token(-1);
                throw new ParseException();
        }
    }

    final public void IfStatement() throws ParseException {
        jj_consume_token(IF);
        jj_consume_token(LPAREN);
        Expression();
        jj_consume_token(RPAREN);
        Statement();
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case ELSE:
                jj_consume_token(ELSE);
                Statement();
                break;
            default:
                ;
        }
    }

    final public void WhileStatement() throws ParseException {
        jj_consume_token(WHILE);
        jj_consume_token(LPAREN);
        Expression();
        jj_consume_token(RPAREN);
        Statement();
    }

    final public void DoStatement() throws ParseException {
        jj_consume_token(DO);
        Statement();
        jj_consume_token(WHILE);
        jj_consume_token(LPAREN);
        Expression();
        jj_consume_token(RPAREN);
        jj_consume_token(SEMICOLON);
    }

    final public void ForStatement() throws ParseException {
        jj_consume_token(FOR);
        jj_consume_token(LPAREN);
        if (jj_2_39(2147483647)) {
            Modifiers();
            Type();
            jj_consume_token(IDENTIFIER);
            jj_consume_token(COLON);
            Expression();
        } else {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case ABSTRACT:
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case DOUBLE:
                case FALSE:
                case FINAL:
                case FLOAT:
                case INT:
                case LONG:
                case NATIVE:
                case NEW:
                case NULL:
                case PRIVATE:
                case PROTECTED:
                case PUBLIC:
                case SHORT:
                case STATIC:
                case STRICTFP:
                case SUPER:
                case SYNCHRONIZED:
                case THIS:
                case TRANSIENT:
                case TRUE:
                case VOID:
                case VOLATILE:
                case INTEGER_LITERAL:
                case FLOATING_POINT_LITERAL:
                case CHARACTER_LITERAL:
                case STRING_LITERAL:
                case IDENTIFIER:
                case LPAREN:
                case SEMICOLON:
                case AT:
                case INCR:
                case DECR:
                    switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                        case ABSTRACT:
                        case BOOLEAN:
                        case BYTE:
                        case CHAR:
                        case DOUBLE:
                        case FALSE:
                        case FINAL:
                        case FLOAT:
                        case INT:
                        case LONG:
                        case NATIVE:
                        case NEW:
                        case NULL:
                        case PRIVATE:
                        case PROTECTED:
                        case PUBLIC:
                        case SHORT:
                        case STATIC:
                        case STRICTFP:
                        case SUPER:
                        case SYNCHRONIZED:
                        case THIS:
                        case TRANSIENT:
                        case TRUE:
                        case VOID:
                        case VOLATILE:
                        case INTEGER_LITERAL:
                        case FLOATING_POINT_LITERAL:
                        case CHARACTER_LITERAL:
                        case STRING_LITERAL:
                        case IDENTIFIER:
                        case LPAREN:
                        case AT:
                        case INCR:
                        case DECR:
                            ForInit();
                            break;
                        default:
                            ;
                    }
                    jj_consume_token(SEMICOLON);
                    switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                        case BOOLEAN:
                        case BYTE:
                        case CHAR:
                        case DOUBLE:
                        case FALSE:
                        case FLOAT:
                        case INT:
                        case LONG:
                        case NEW:
                        case NULL:
                        case SHORT:
                        case SUPER:
                        case THIS:
                        case TRUE:
                        case VOID:
                        case INTEGER_LITERAL:
                        case FLOATING_POINT_LITERAL:
                        case CHARACTER_LITERAL:
                        case STRING_LITERAL:
                        case IDENTIFIER:
                        case LPAREN:
                        case BANG:
                        case TILDE:
                        case INCR:
                        case DECR:
                        case PLUS:
                        case MINUS:
                            Expression();
                            break;
                        default:
                            ;
                    }
                    jj_consume_token(SEMICOLON);
                    switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                        case BOOLEAN:
                        case BYTE:
                        case CHAR:
                        case DOUBLE:
                        case FALSE:
                        case FLOAT:
                        case INT:
                        case LONG:
                        case NEW:
                        case NULL:
                        case SHORT:
                        case SUPER:
                        case THIS:
                        case TRUE:
                        case VOID:
                        case INTEGER_LITERAL:
                        case FLOATING_POINT_LITERAL:
                        case CHARACTER_LITERAL:
                        case STRING_LITERAL:
                        case IDENTIFIER:
                        case LPAREN:
                        case INCR:
                        case DECR:
                            ForUpdate();
                            break;
                        default:
                            ;
                    }
                    break;
                default:
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
        jj_consume_token(RPAREN);
        Statement();
    }

    final public void ForInit() throws ParseException {
        if (jj_2_40(2147483647)) {
            LocalVariableDeclaration();
        } else {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case DOUBLE:
                case FALSE:
                case FLOAT:
                case INT:
                case LONG:
                case NEW:
                case NULL:
                case SHORT:
                case SUPER:
                case THIS:
                case TRUE:
                case VOID:
                case INTEGER_LITERAL:
                case FLOATING_POINT_LITERAL:
                case CHARACTER_LITERAL:
                case STRING_LITERAL:
                case IDENTIFIER:
                case LPAREN:
                case INCR:
                case DECR:
                    StatementExpressionList();
                    break;
                default:
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
    }

    final public void StatementExpressionList() throws ParseException {
        StatementExpression();
        label_44:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case COMMA:
                    ;
                    break;
                default:
                    break label_44;
            }
            jj_consume_token(COMMA);
            StatementExpression();
        }
    }

    final public void ForUpdate() throws ParseException {
        StatementExpressionList();
    }

    final public void BreakStatement() throws ParseException {
        jj_consume_token(BREAK);
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case IDENTIFIER:
                jj_consume_token(IDENTIFIER);
                break;
            default:
                ;
        }
        jj_consume_token(SEMICOLON);
    }

    final public void ContinueStatement() throws ParseException {
        jj_consume_token(CONTINUE);
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case IDENTIFIER:
                jj_consume_token(IDENTIFIER);
                break;
            default:
                ;
        }
        jj_consume_token(SEMICOLON);
    }

    final public void ReturnStatement() throws ParseException {
        jj_consume_token(RETURN);
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case DOUBLE:
            case FALSE:
            case FLOAT:
            case INT:
            case LONG:
            case NEW:
            case NULL:
            case SHORT:
            case SUPER:
            case THIS:
            case TRUE:
            case VOID:
            case INTEGER_LITERAL:
            case FLOATING_POINT_LITERAL:
            case CHARACTER_LITERAL:
            case STRING_LITERAL:
            case IDENTIFIER:
            case LPAREN:
            case BANG:
            case TILDE:
            case INCR:
            case DECR:
            case PLUS:
            case MINUS:
                Expression();
                break;
            default:
                ;
        }
        jj_consume_token(SEMICOLON);
    }

    final public void ThrowStatement() throws ParseException {
        jj_consume_token(THROW);
        Expression();
        jj_consume_token(SEMICOLON);
    }

    final public void SynchronizedStatement() throws ParseException {
        jj_consume_token(SYNCHRONIZED);
        jj_consume_token(LPAREN);
        Expression();
        jj_consume_token(RPAREN);
        Block();
    }

    final public void TryStatement() throws ParseException {
        jj_consume_token(TRY);
        Block();
        label_45:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case CATCH:
                    ;
                    break;
                default:
                    break label_45;
            }
            jj_consume_token(CATCH);
            jj_consume_token(LPAREN);
            FormalParameter();
            jj_consume_token(RPAREN);
            Block();
        }
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case FINALLY:
                jj_consume_token(FINALLY);
                Block();
                break;
            default:
                ;
        }
    }

    /* We use productions to match >>>, >> and > so that we can keep the
     * type declaration syntax with generics clean
     */
    final public void RUNSIGNEDSHIFT() throws ParseException {
        if (getToken(1).kind == GT && getToken(1).realKind == RUNSIGNEDSHIFT) {
        } else {
            jj_consume_token(-1);
            throw new ParseException();
        }
        jj_consume_token(GT);
        jj_consume_token(GT);
        jj_consume_token(GT);
    }

    final public void RSIGNEDSHIFT() throws ParseException {
        if (getToken(1).kind == GT && getToken(1).realKind == RSIGNEDSHIFT) {
        } else {
            jj_consume_token(-1);
            throw new ParseException();
        }
        jj_consume_token(GT);
        jj_consume_token(GT);
    }

    /* Annotation syntax follows. */
    final public void Annotation() throws ParseException {
        if (jj_2_41(2147483647)) {
            NormalAnnotation();
        } else if (jj_2_42(2147483647)) {
            SingleMemberAnnotation();
        } else {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case AT:
                    MarkerAnnotation();
                    break;
                default:
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
    }

    final public void NormalAnnotation() throws ParseException {
        jj_consume_token(AT);
        Name();
        jj_consume_token(LPAREN);
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case IDENTIFIER:
                MemberValuePairs();
                break;
            default:
                ;
        }
        jj_consume_token(RPAREN);
    }

    final public void MarkerAnnotation() throws ParseException {
        jj_consume_token(AT);
        Name();
    }

    final public void SingleMemberAnnotation() throws ParseException {
        jj_consume_token(AT);
        Name();
        jj_consume_token(LPAREN);
        MemberValue();
        jj_consume_token(RPAREN);
    }

    final public void MemberValuePairs() throws ParseException {
        MemberValuePair();
        label_46:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case COMMA:
                    ;
                    break;
                default:
                    break label_46;
            }
            jj_consume_token(COMMA);
            MemberValuePair();
        }
    }

    final public void MemberValuePair() throws ParseException {
        jj_consume_token(IDENTIFIER);
        jj_consume_token(ASSIGN);
        MemberValue();
    }

    final public void MemberValue() throws ParseException {
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case AT:
                Annotation();
                break;
            case LBRACE:
                MemberValueArrayInitializer();
                break;
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case DOUBLE:
            case FALSE:
            case FLOAT:
            case INT:
            case LONG:
            case NEW:
            case NULL:
            case SHORT:
            case SUPER:
            case THIS:
            case TRUE:
            case VOID:
            case INTEGER_LITERAL:
            case FLOATING_POINT_LITERAL:
            case CHARACTER_LITERAL:
            case STRING_LITERAL:
            case IDENTIFIER:
            case LPAREN:
            case BANG:
            case TILDE:
            case INCR:
            case DECR:
            case PLUS:
            case MINUS:
                ConditionalExpression();
                break;
            default:
                jj_consume_token(-1);
                throw new ParseException();
        }
    }

    final public void MemberValueArrayInitializer() throws ParseException {
        jj_consume_token(LBRACE);
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case DOUBLE:
            case FALSE:
            case FLOAT:
            case INT:
            case LONG:
            case NEW:
            case NULL:
            case SHORT:
            case SUPER:
            case THIS:
            case TRUE:
            case VOID:
            case INTEGER_LITERAL:
            case FLOATING_POINT_LITERAL:
            case CHARACTER_LITERAL:
            case STRING_LITERAL:
            case IDENTIFIER:
            case LPAREN:
            case LBRACE:
            case AT:
            case BANG:
            case TILDE:
            case INCR:
            case DECR:
            case PLUS:
            case MINUS:
                MemberValue();
                label_47:
                while (true) {
                    if (!jj_2_43(2)) {

                        break label_47;
                    }
                    jj_consume_token(COMMA);
                    MemberValue();
                }
                switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                    case COMMA:
                        jj_consume_token(COMMA);
                        break;
                    default:
                        ;
                }
                break;
            default:
                ;
        }
        jj_consume_token(RBRACE);
    }

    /* Annotation Types. */
    final public void AnnotationTypeDeclaration(int modifiers) throws ParseException {
        jj_consume_token(AT);
        jj_consume_token(INTERFACE);
        jj_consume_token(IDENTIFIER);
        AnnotationTypeBody();
    }

    final public void AnnotationTypeBody() throws ParseException {
        jj_consume_token(LBRACE);
        label_48:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case ABSTRACT:
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case CLASS:
                case DOUBLE:
                case ENUM:
                case FINAL:
                case FLOAT:
                case INT:
                case INTERFACE:
                case LONG:
                case NATIVE:
                case PRIVATE:
                case PROTECTED:
                case PUBLIC:
                case SHORT:
                case STATIC:
                case STRICTFP:
                case SYNCHRONIZED:
                case TRANSIENT:
                case VOLATILE:
                case IDENTIFIER:
                case SEMICOLON:
                case AT:
                    ;
                    break;
                default:
                    break label_48;
            }
            AnnotationTypeMemberDeclaration();
        }
        jj_consume_token(RBRACE);
    }

    final public void AnnotationTypeMemberDeclaration() throws ParseException {
        int modifiers;
        switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
            case ABSTRACT:
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case CLASS:
            case DOUBLE:
            case ENUM:
            case FINAL:
            case FLOAT:
            case INT:
            case INTERFACE:
            case LONG:
            case NATIVE:
            case PRIVATE:
            case PROTECTED:
            case PUBLIC:
            case SHORT:
            case STATIC:
            case STRICTFP:
            case SYNCHRONIZED:
            case TRANSIENT:
            case VOLATILE:
            case IDENTIFIER:
            case AT:
                modifiers = Modifiers();
                if (jj_2_44(2147483647)) {
                    Type();
                    jj_consume_token(IDENTIFIER);
                    jj_consume_token(LPAREN);
                    jj_consume_token(RPAREN);
                    switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                        case _DEFAULT:
                            DefaultValue();
                            break;
                        default:
                            ;
                    }
                    jj_consume_token(SEMICOLON);
                } else {
                    switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                        case CLASS:
                        case INTERFACE:
                            ClassOrInterfaceDeclaration(modifiers);
                            break;
                        case ENUM:
                            EnumDeclaration(modifiers);
                            break;
                        case AT:
                            AnnotationTypeDeclaration(modifiers);
                            break;
                        case BOOLEAN:
                        case BYTE:
                        case CHAR:
                        case DOUBLE:
                        case FLOAT:
                        case INT:
                        case LONG:
                        case SHORT:
                        case IDENTIFIER:
                            FieldDeclaration(modifiers);
                            break;
                        default:
                            jj_consume_token(-1);
                            throw new ParseException();
                    }
                }
                break;
            case SEMICOLON:
                jj_consume_token(SEMICOLON);
                break;
            default:
                jj_consume_token(-1);
                throw new ParseException();
        }
    }

    final public void DefaultValue() throws ParseException {
        jj_consume_token(_DEFAULT);
        MemberValue();
    }

    private boolean jj_2_1(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_1();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_2(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_2();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_3(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_3();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_4(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_4();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_5(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_5();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_6(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_6();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_7(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_7();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_8(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_8();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_9(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_9();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_10(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_10();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_11(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_11();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_12(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_12();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_13(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_13();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_14(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_14();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_15(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_15();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_16(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_16();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_17(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_17();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_18(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_18();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_19(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_19();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_20(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_20();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_21(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_21();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_22(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_22();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_23(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_23();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_24(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_24();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_25(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_25();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_26(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_26();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_27(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_27();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_28(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_28();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_29(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_29();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_30(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_30();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_31(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_31();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_32(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_32();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_33(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_33();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_34(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_34();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_35(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_35();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_36(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_36();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_37(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_37();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_38(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_38();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_39(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_39();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_40(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_40();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_41(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_41();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_42(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_42();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_43(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_43();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_2_44(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_44();
        } catch (Exception ls) {
            return true;
        }
    }

    private boolean jj_3R_151() {
        if (jj_3R_172()) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_202()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }

    private boolean jj_3R_143() {
        if (jj_3R_151()) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_199()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }

    private boolean jj_3R_135() {
        if (jj_3R_143()) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_194()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }

    private boolean jj_3R_125() {
        if (jj_3R_135()) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_189()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }

    private boolean jj_3R_100() {
        if (jj_3R_125()) {
            return true;
        }
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_171()) {
            jj_scanpos = xsp;
        }
        return false;
    }

    private boolean jj_3R_71() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(89)) {
            jj_scanpos = xsp;
            if (jj_scan_token(114)) {
                jj_scanpos = xsp;
                if (jj_scan_token(115)) {
                    jj_scanpos = xsp;
                    if (jj_scan_token(119)) {
                        jj_scanpos = xsp;
                        if (jj_scan_token(112)) {
                            jj_scanpos = xsp;
                            if (jj_scan_token(113)) {
                                jj_scanpos = xsp;
                                if (jj_scan_token(120)) {
                                    jj_scanpos = xsp;
                                    if (jj_scan_token(121)) {
                                        jj_scanpos = xsp;
                                        if (jj_scan_token(122)) {
                                            jj_scanpos = xsp;
                                            if (jj_scan_token(116)) {
                                                jj_scanpos = xsp;
                                                if (jj_scan_token(118)) {
                                                    jj_scanpos = xsp;
                                                    if (jj_scan_token(117)) {
                                                        return true;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean jj_3_17() {
        if (jj_3R_71()) {
            return true;
        }
        if (jj_3R_72()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_72() {
        if (jj_3R_100()) {
            return true;
        }
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3_17()) {
            jj_scanpos = xsp;
        }
        return false;
    }

    private boolean jj_3R_290() {
        if (jj_scan_token(COMMA)) {
            return true;
        }
        if (jj_3R_85()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_280() {
        if (jj_3R_85()) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_290()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }

    private boolean jj_3_16() {
        if (jj_scan_token(DOT)) {
            return true;
        }
        if (jj_scan_token(IDENTIFIER)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_85() {
        if (jj_scan_token(IDENTIFIER)) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3_16()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }

    private boolean jj_3R_110() {
        if (jj_3R_64()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_80() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(62)) {
            jj_scanpos = xsp;
            if (jj_3R_110()) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3_15() {
        if (jj_3R_70()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_76() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(14)) {
            jj_scanpos = xsp;
            if (jj_scan_token(19)) {
                jj_scanpos = xsp;
                if (jj_scan_token(16)) {
                    jj_scanpos = xsp;
                    if (jj_scan_token(50)) {
                        jj_scanpos = xsp;
                        if (jj_scan_token(39)) {
                            jj_scanpos = xsp;
                            if (jj_scan_token(41)) {
                                jj_scanpos = xsp;
                                if (jj_scan_token(32)) {
                                    jj_scanpos = xsp;
                                    if (jj_scan_token(25)) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean jj_3R_133() {
        if (jj_scan_token(COMMA)) {
            return true;
        }
        if (jj_3R_99()) {
            return true;
        }
        return false;
    }

    private boolean jj_3_12() {
        if (jj_scan_token(LBRACKET)) {
            return true;
        }
        if (jj_scan_token(RBRACKET)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_188() {
        if (jj_scan_token(SUPER)) {
            return true;
        }
        if (jj_3R_69()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_150() {
        if (jj_3R_170()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_170() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_187()) {
            jj_scanpos = xsp;
            if (jj_3R_188()) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_187() {
        if (jj_scan_token(EXTENDS)) {
            return true;
        }
        if (jj_3R_69()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_124() {
        if (jj_scan_token(HOOK)) {
            return true;
        }
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_150()) {
            jj_scanpos = xsp;
        }
        return false;
    }

    private boolean jj_3_13() {
        if (jj_3R_70()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_123() {
        if (jj_3R_69()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_99() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_123()) {
            jj_scanpos = xsp;
            if (jj_3R_124()) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3_11() {
        if (jj_scan_token(LBRACKET)) {
            return true;
        }
        if (jj_scan_token(RBRACKET)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_70() {
        if (jj_scan_token(LT)) {
            return true;
        }
        if (jj_3R_99()) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_133()) {
                jj_scanpos = xsp;
                break;
            }
        }
        if (jj_scan_token(GT)) {
            return true;
        }
        return false;
    }

    private boolean jj_3_14() {
        if (jj_scan_token(DOT)) {
            return true;
        }
        if (jj_scan_token(IDENTIFIER)) {
            return true;
        }
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3_15()) {
            jj_scanpos = xsp;
        }
        return false;
    }

    private boolean jj_3R_79() {
        if (jj_scan_token(IDENTIFIER)) {
            return true;
        }
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3_13()) {
            jj_scanpos = xsp;
        }
        while (true) {
            xsp = jj_scanpos;
            if (jj_3_14()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }

    private boolean jj_3R_98() {
        if (jj_3R_79()) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3_12()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }

    private boolean jj_3R_97() {
        if (jj_3R_76()) {
            return true;
        }
        Token xsp;
        if (jj_3_11()) {
            return true;
        }
        while (true) {
            xsp = jj_scanpos;
            if (jj_3_11()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }

    private boolean jj_3R_69() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_97()) {
            jj_scanpos = xsp;
            if (jj_3R_98()) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3_9() {
        if (jj_scan_token(THIS)) {
            return true;
        }
        if (jj_scan_token(DOT)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_265() {
        if (jj_scan_token(THROWS)) {
            return true;
        }
        if (jj_3R_280()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_90() {
        if (jj_3R_76()) {
            return true;
        }
        return false;
    }

    private boolean jj_3_10() {
        if (jj_3R_69()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_64() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3_10()) {
            jj_scanpos = xsp;
            if (jj_3R_90()) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_66() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(51)) {
            jj_scanpos = xsp;
        }
        if (jj_3R_91()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_298() {
        if (jj_3R_88()) {
            return true;
        }
        return false;
    }

    private boolean jj_3_8() {
        if (jj_3R_68()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_95() {
        if (jj_3R_70()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_94() {
        if (jj_scan_token(IDENTIFIER)) {
            return true;
        }
        if (jj_scan_token(DOT)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_68() {
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_94()) {
                jj_scanpos = xsp;
                break;
            }
        }
        xsp = jj_scanpos;
        if (jj_3_9()) {
            jj_scanpos = xsp;
        }
        xsp = jj_scanpos;
        if (jj_3R_95()) {
            jj_scanpos = xsp;
        }
        xsp = jj_scanpos;
        if (jj_scan_token(56)) {
            jj_scanpos = xsp;
            if (jj_scan_token(53)) {
                return true;
            }
        }
        if (jj_3R_96()) {
            return true;
        }
        if (jj_scan_token(SEMICOLON)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_283() {
        if (jj_scan_token(LBRACKET)) {
            return true;
        }
        if (jj_scan_token(RBRACKET)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_289() {
        if (jj_scan_token(COMMA)) {
            return true;
        }
        if (jj_3R_288()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_267() {
        if (jj_3R_132()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_304() {
        if (jj_3R_313()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_295() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(30)) {
            jj_scanpos = xsp;
            if (jj_3R_298()) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_266() {
        if (jj_3R_68()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_263() {
        if (jj_3R_89()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_254() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_263()) {
            jj_scanpos = xsp;
        }
        if (jj_scan_token(IDENTIFIER)) {
            return true;
        }
        if (jj_3R_264()) {
            return true;
        }
        xsp = jj_scanpos;
        if (jj_3R_265()) {
            jj_scanpos = xsp;
        }
        if (jj_scan_token(LBRACE)) {
            return true;
        }
        xsp = jj_scanpos;
        if (jj_3R_266()) {
            jj_scanpos = xsp;
        }
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_267()) {
                jj_scanpos = xsp;
                break;
            }
        }
        if (jj_scan_token(RBRACE)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_313() {
        if (jj_scan_token(_DEFAULT)) {
            return true;
        }
        if (jj_3R_87()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_288() {
        if (jj_3R_84()) {
            return true;
        }
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_295()) {
            jj_scanpos = xsp;
        }
        if (jj_3R_64()) {
            return true;
        }
        xsp = jj_scanpos;
        if (jj_scan_token(123)) {
            jj_scanpos = xsp;
        }
        if (jj_3R_281()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_272() {
        if (jj_scan_token(THROWS)) {
            return true;
        }
        if (jj_3R_280()) {
            return true;
        }
        return false;
    }

    private boolean jj_3_7() {
        if (jj_scan_token(COMMA)) {
            return true;
        }
        if (jj_3R_67()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_279() {
        if (jj_3R_288()) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_289()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }

    private boolean jj_3_44() {
        if (jj_3R_64()) {
            return true;
        }
        if (jj_scan_token(IDENTIFIER)) {
            return true;
        }
        if (jj_scan_token(LPAREN)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_303() {
        if (jj_3R_255()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_302() {
        if (jj_3R_257()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_264() {
        if (jj_scan_token(LPAREN)) {
            return true;
        }
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_279()) {
            jj_scanpos = xsp;
        }
        if (jj_scan_token(RPAREN)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_301() {
        if (jj_3R_253()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_300() {
        if (jj_3R_149()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_271() {
        if (jj_scan_token(IDENTIFIER)) {
            return true;
        }
        if (jj_3R_264()) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_283()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }

    private boolean jj_3R_299() {
        if (jj_3R_64()) {
            return true;
        }
        if (jj_scan_token(IDENTIFIER)) {
            return true;
        }
        if (jj_scan_token(LPAREN)) {
            return true;
        }
        if (jj_scan_token(RPAREN)) {
            return true;
        }
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_304()) {
            jj_scanpos = xsp;
        }
        if (jj_scan_token(SEMICOLON)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_273() {
        if (jj_3R_91()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_296() {
        if (jj_3R_84()) {
            return true;
        }
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_299()) {
            jj_scanpos = xsp;
            if (jj_3R_300()) {
                jj_scanpos = xsp;
                if (jj_3R_301()) {
                    jj_scanpos = xsp;
                    if (jj_3R_302()) {
                        jj_scanpos = xsp;
                        if (jj_3R_303()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean jj_3R_292() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_296()) {
            jj_scanpos = xsp;
            if (jj_scan_token(85)) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_284() {
        if (jj_3R_292()) {
            return true;
        }
        return false;
    }

    private boolean jj_3_43() {
        if (jj_scan_token(COMMA)) {
            return true;
        }
        if (jj_3R_87()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_270() {
        if (jj_3R_89()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_256() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_270()) {
            jj_scanpos = xsp;
        }
        if (jj_3R_80()) {
            return true;
        }
        if (jj_3R_271()) {
            return true;
        }
        xsp = jj_scanpos;
        if (jj_3R_272()) {
            jj_scanpos = xsp;
        }
        xsp = jj_scanpos;
        if (jj_3R_273()) {
            jj_scanpos = xsp;
            if (jj_scan_token(85)) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_243() {
        if (jj_3R_67()) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3_7()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }

    private boolean jj_3R_282() {
        if (jj_scan_token(ASSIGN)) {
            return true;
        }
        if (jj_3R_67()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_274() {
        if (jj_scan_token(LBRACE)) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_284()) {
                jj_scanpos = xsp;
                break;
            }
        }
        if (jj_scan_token(RBRACE)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_269() {
        if (jj_scan_token(COMMA)) {
            return true;
        }
        if (jj_3R_268()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_291() {
        if (jj_scan_token(LBRACKET)) {
            return true;
        }
        if (jj_scan_token(RBRACKET)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_121() {
        if (jj_scan_token(LBRACE)) {
            return true;
        }
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_243()) {
            jj_scanpos = xsp;
        }
        xsp = jj_scanpos;
        if (jj_scan_token(86)) {
            jj_scanpos = xsp;
        }
        if (jj_scan_token(RBRACE)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_257() {
        if (jj_scan_token(AT)) {
            return true;
        }
        if (jj_scan_token(INTERFACE)) {
            return true;
        }
        if (jj_scan_token(IDENTIFIER)) {
            return true;
        }
        if (jj_3R_274()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_65() {
        if (jj_scan_token(LBRACKET)) {
            return true;
        }
        if (jj_scan_token(RBRACKET)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_93() {
        if (jj_3R_72()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_173() {
        if (jj_3R_87()) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3_43()) {
                jj_scanpos = xsp;
                break;
            }
        }
        xsp = jj_scanpos;
        if (jj_scan_token(86)) {
            jj_scanpos = xsp;
        }
        return false;
    }

    private boolean jj_3R_92() {
        if (jj_3R_121()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_67() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_92()) {
            jj_scanpos = xsp;
            if (jj_3R_93()) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_127() {
        if (jj_scan_token(LBRACE)) {
            return true;
        }
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_173()) {
            jj_scanpos = xsp;
        }
        if (jj_scan_token(RBRACE)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_154() {
        if (jj_scan_token(COMMA)) {
            return true;
        }
        if (jj_3R_153()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_281() {
        if (jj_scan_token(IDENTIFIER)) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_291()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }

    private boolean jj_3R_114() {
        if (jj_3R_100()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_113() {
        if (jj_3R_127()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_268() {
        if (jj_3R_281()) {
            return true;
        }
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_282()) {
            jj_scanpos = xsp;
        }
        return false;
    }

    private boolean jj_3R_112() {
        if (jj_3R_88()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_87() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_112()) {
            jj_scanpos = xsp;
            if (jj_3R_113()) {
                jj_scanpos = xsp;
                if (jj_3R_114()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean jj_3R_153() {
        if (jj_scan_token(IDENTIFIER)) {
            return true;
        }
        if (jj_scan_token(ASSIGN)) {
            return true;
        }
        if (jj_3R_87()) {
            return true;
        }
        return false;
    }

    private boolean jj_3_5() {
        if (jj_3R_64()) {
            return true;
        }
        if (jj_scan_token(IDENTIFIER)) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_65()) {
                jj_scanpos = xsp;
                break;
            }
        }
        xsp = jj_scanpos;
        if (jj_scan_token(86)) {
            jj_scanpos = xsp;
            if (jj_scan_token(89)) {
                jj_scanpos = xsp;
                if (jj_scan_token(85)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean jj_3R_255() {
        if (jj_3R_64()) {
            return true;
        }
        if (jj_3R_268()) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_269()) {
                jj_scanpos = xsp;
                break;
            }
        }
        if (jj_scan_token(SEMICOLON)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_63() {
        if (jj_3R_89()) {
            return true;
        }
        return false;
    }

    private boolean jj_3_4() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_63()) {
            jj_scanpos = xsp;
        }
        if (jj_scan_token(IDENTIFIER)) {
            return true;
        }
        if (jj_scan_token(LPAREN)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_252() {
        if (jj_3R_257()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_145() {
        if (jj_3R_153()) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_154()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }

    private boolean jj_3R_137() {
        if (jj_3R_145()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_251() {
        if (jj_3R_256()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_86() {
        if (jj_scan_token(IDENTIFIER)) {
            return true;
        }
        if (jj_scan_token(ASSIGN)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_146() {
        if (jj_scan_token(BIT_AND)) {
            return true;
        }
        if (jj_3R_79()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_250() {
        if (jj_3R_255()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_129() {
        if (jj_scan_token(AT)) {
            return true;
        }
        if (jj_3R_85()) {
            return true;
        }
        if (jj_scan_token(LPAREN)) {
            return true;
        }
        if (jj_3R_87()) {
            return true;
        }
        if (jj_scan_token(RPAREN)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_249() {
        if (jj_3R_254()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_248() {
        if (jj_3R_253()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_247() {
        if (jj_3R_149()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_130() {
        if (jj_scan_token(AT)) {
            return true;
        }
        if (jj_3R_85()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_294() {
        if (jj_3R_242()) {
            return true;
        }
        return false;
    }

    private boolean jj_3_42() {
        if (jj_scan_token(AT)) {
            return true;
        }
        if (jj_3R_85()) {
            return true;
        }
        if (jj_scan_token(LPAREN)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_128() {
        if (jj_scan_token(AT)) {
            return true;
        }
        if (jj_3R_85()) {
            return true;
        }
        if (jj_scan_token(LPAREN)) {
            return true;
        }
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_137()) {
            jj_scanpos = xsp;
        }
        if (jj_scan_token(RPAREN)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_246() {
        if (jj_3R_84()) {
            return true;
        }
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_247()) {
            jj_scanpos = xsp;
            if (jj_3R_248()) {
                jj_scanpos = xsp;
                if (jj_3R_249()) {
                    jj_scanpos = xsp;
                    if (jj_3R_250()) {
                        jj_scanpos = xsp;
                        if (jj_3R_251()) {
                            jj_scanpos = xsp;
                            if (jj_3R_252()) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean jj_3_41() {
        if (jj_scan_token(AT)) {
            return true;
        }
        if (jj_3R_85()) {
            return true;
        }
        if (jj_scan_token(LPAREN)) {
            return true;
        }
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_86()) {
            jj_scanpos = xsp;
            if (jj_scan_token(80)) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_117() {
        if (jj_3R_130()) {
            return true;
        }
        return false;
    }

    private boolean jj_3_6() {
        if (jj_3R_66()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_245() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3_6()) {
            jj_scanpos = xsp;
            if (jj_3R_246()) {
                jj_scanpos = xsp;
                if (jj_scan_token(85)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean jj_3R_116() {
        if (jj_3R_129()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_244() {
        if (jj_3R_245()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_119() {
        if (jj_scan_token(COMMA)) {
            return true;
        }
        if (jj_3R_118()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_115() {
        if (jj_3R_128()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_101() {
        return false;
    }

    private boolean jj_3R_88() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_115()) {
            jj_scanpos = xsp;
            if (jj_3R_116()) {
                jj_scanpos = xsp;
                if (jj_3R_117()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean jj_3R_131() {
        if (jj_3R_138()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_293() {
        if (jj_3R_96()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_242() {
        if (jj_scan_token(LBRACE)) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_244()) {
                jj_scanpos = xsp;
                break;
            }
        }
        if (jj_scan_token(RBRACE)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_102() {
        return false;
    }

    private boolean jj_3R_138() {
        if (jj_scan_token(EXTENDS)) {
            return true;
        }
        if (jj_3R_79()) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_146()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }

    private boolean jj_3R_73() {
        jj_lookingAhead = true;
        jj_semLA = getToken(1).kind == GT && getToken(1).realKind == RSIGNEDSHIFT;
        jj_lookingAhead = false;
        if (!jj_semLA || jj_3R_101()) {
            return true;
        }
        if (jj_scan_token(GT)) {
            return true;
        }
        if (jj_scan_token(GT)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_118() {
        if (jj_scan_token(IDENTIFIER)) {
            return true;
        }
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_131()) {
            jj_scanpos = xsp;
        }
        return false;
    }

    private boolean jj_3_3() {
        if (jj_scan_token(COMMA)) {
            return true;
        }
        if (jj_3R_62()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_74() {
        jj_lookingAhead = true;
        jj_semLA = getToken(1).kind == GT && getToken(1).realKind == RUNSIGNEDSHIFT;
        jj_lookingAhead = false;
        if (!jj_semLA || jj_3R_102()) {
            return true;
        }
        if (jj_scan_token(GT)) {
            return true;
        }
        if (jj_scan_token(GT)) {
            return true;
        }
        if (jj_scan_token(GT)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_89() {
        if (jj_scan_token(LT)) {
            return true;
        }
        if (jj_3R_118()) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_119()) {
                jj_scanpos = xsp;
                break;
            }
        }
        if (jj_scan_token(GT)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_287() {
        if (jj_3R_245()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_312() {
        if (jj_scan_token(FINALLY)) {
            return true;
        }
        if (jj_3R_91()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_62() {
        if (jj_3R_84()) {
            return true;
        }
        if (jj_scan_token(IDENTIFIER)) {
            return true;
        }
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_293()) {
            jj_scanpos = xsp;
        }
        xsp = jj_scanpos;
        if (jj_3R_294()) {
            jj_scanpos = xsp;
        }
        return false;
    }

    private boolean jj_3R_311() {
        if (jj_scan_token(CATCH)) {
            return true;
        }
        if (jj_scan_token(LPAREN)) {
            return true;
        }
        if (jj_3R_288()) {
            return true;
        }
        if (jj_scan_token(RPAREN)) {
            return true;
        }
        if (jj_3R_91()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_278() {
        if (jj_scan_token(SEMICOLON)) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_287()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }

    private boolean jj_3R_186() {
        if (jj_scan_token(TRY)) {
            return true;
        }
        if (jj_3R_91()) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_311()) {
                jj_scanpos = xsp;
                break;
            }
        }
        xsp = jj_scanpos;
        if (jj_3R_312()) {
            jj_scanpos = xsp;
        }
        return false;
    }

    private boolean jj_3R_277() {
        if (jj_3R_62()) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3_3()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }

    private boolean jj_3R_262() {
        if (jj_scan_token(LBRACE)) {
            return true;
        }
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_277()) {
            jj_scanpos = xsp;
        }
        xsp = jj_scanpos;
        if (jj_scan_token(86)) {
            jj_scanpos = xsp;
        }
        xsp = jj_scanpos;
        if (jj_3R_278()) {
            jj_scanpos = xsp;
        }
        if (jj_scan_token(RBRACE)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_185() {
        if (jj_scan_token(SYNCHRONIZED)) {
            return true;
        }
        if (jj_scan_token(LPAREN)) {
            return true;
        }
        if (jj_3R_72()) {
            return true;
        }
        if (jj_scan_token(RPAREN)) {
            return true;
        }
        if (jj_3R_91()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_310() {
        if (jj_3R_72()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_261() {
        if (jj_3R_276()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_253() {
        if (jj_scan_token(ENUM)) {
            return true;
        }
        if (jj_scan_token(IDENTIFIER)) {
            return true;
        }
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_261()) {
            jj_scanpos = xsp;
        }
        if (jj_3R_262()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_184() {
        if (jj_scan_token(THROW)) {
            return true;
        }
        if (jj_3R_72()) {
            return true;
        }
        if (jj_scan_token(SEMICOLON)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_319() {
        if (jj_3R_324()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_286() {
        if (jj_scan_token(COMMA)) {
            return true;
        }
        if (jj_3R_79()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_328() {
        if (jj_scan_token(COMMA)) {
            return true;
        }
        if (jj_3R_175()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_183() {
        if (jj_scan_token(RETURN)) {
            return true;
        }
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_310()) {
            jj_scanpos = xsp;
        }
        if (jj_scan_token(SEMICOLON)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_276() {
        if (jj_scan_token(IMPLEMENTS)) {
            return true;
        }
        if (jj_3R_79()) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_286()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }

    private boolean jj_3R_182() {
        if (jj_scan_token(CONTINUE)) {
            return true;
        }
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(76)) {
            jj_scanpos = xsp;
        }
        if (jj_scan_token(SEMICOLON)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_285() {
        if (jj_scan_token(COMMA)) {
            return true;
        }
        if (jj_3R_79()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_181() {
        if (jj_scan_token(BREAK)) {
            return true;
        }
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(76)) {
            jj_scanpos = xsp;
        }
        if (jj_scan_token(SEMICOLON)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_275() {
        if (jj_scan_token(EXTENDS)) {
            return true;
        }
        if (jj_3R_79()) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_285()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }

    private boolean jj_3R_169() {
        if (jj_scan_token(INTERFACE)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_324() {
        if (jj_3R_327()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_318() {
        if (jj_3R_72()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_260() {
        if (jj_3R_276()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_259() {
        if (jj_3R_275()) {
            return true;
        }
        return false;
    }

    private boolean jj_3_40() {
        if (jj_3R_84()) {
            return true;
        }
        if (jj_3R_64()) {
            return true;
        }
        if (jj_scan_token(IDENTIFIER)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_258() {
        if (jj_3R_89()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_327() {
        if (jj_3R_175()) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_328()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }

    private boolean jj_3R_307() {
        if (jj_scan_token(ELSE)) {
            return true;
        }
        if (jj_3R_148()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_149() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(20)) {
            jj_scanpos = xsp;
            if (jj_3R_169()) {
                return true;
            }
        }
        if (jj_scan_token(IDENTIFIER)) {
            return true;
        }
        xsp = jj_scanpos;
        if (jj_3R_258()) {
            jj_scanpos = xsp;
        }
        xsp = jj_scanpos;
        if (jj_3R_259()) {
            jj_scanpos = xsp;
        }
        xsp = jj_scanpos;
        if (jj_3R_260()) {
            jj_scanpos = xsp;
        }
        if (jj_3R_242()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_326() {
        if (jj_3R_327()) {
            return true;
        }
        return false;
    }

    private boolean jj_3_39() {
        if (jj_3R_84()) {
            return true;
        }
        if (jj_3R_64()) {
            return true;
        }
        if (jj_scan_token(IDENTIFIER)) {
            return true;
        }
        if (jj_scan_token(COLON)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_325() {
        if (jj_3R_147()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_323() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_325()) {
            jj_scanpos = xsp;
            if (jj_3R_326()) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_317() {
        if (jj_3R_323()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_309() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_317()) {
            jj_scanpos = xsp;
        }
        if (jj_scan_token(SEMICOLON)) {
            return true;
        }
        xsp = jj_scanpos;
        if (jj_3R_318()) {
            jj_scanpos = xsp;
        }
        if (jj_scan_token(SEMICOLON)) {
            return true;
        }
        xsp = jj_scanpos;
        if (jj_3R_319()) {
            jj_scanpos = xsp;
        }
        return false;
    }

    private boolean jj_3R_308() {
        if (jj_3R_84()) {
            return true;
        }
        if (jj_3R_64()) {
            return true;
        }
        if (jj_scan_token(IDENTIFIER)) {
            return true;
        }
        if (jj_scan_token(COLON)) {
            return true;
        }
        if (jj_3R_72()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_180() {
        if (jj_scan_token(FOR)) {
            return true;
        }
        if (jj_scan_token(LPAREN)) {
            return true;
        }
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_308()) {
            jj_scanpos = xsp;
            if (jj_3R_309()) {
                return true;
            }
        }
        if (jj_scan_token(RPAREN)) {
            return true;
        }
        if (jj_3R_148()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_179() {
        if (jj_scan_token(DO)) {
            return true;
        }
        if (jj_3R_148()) {
            return true;
        }
        if (jj_scan_token(WHILE)) {
            return true;
        }
        if (jj_scan_token(LPAREN)) {
            return true;
        }
        if (jj_3R_72()) {
            return true;
        }
        if (jj_scan_token(RPAREN)) {
            return true;
        }
        if (jj_scan_token(SEMICOLON)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_178() {
        if (jj_scan_token(WHILE)) {
            return true;
        }
        if (jj_scan_token(LPAREN)) {
            return true;
        }
        if (jj_3R_72()) {
            return true;
        }
        if (jj_scan_token(RPAREN)) {
            return true;
        }
        if (jj_3R_148()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_61() {
        if (jj_3R_88()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_60() {
        if (jj_scan_token(STRICTFP)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_59() {
        if (jj_scan_token(VOLATILE)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_316() {
        if (jj_3R_132()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_177() {
        if (jj_scan_token(IF)) {
            return true;
        }
        if (jj_scan_token(LPAREN)) {
            return true;
        }
        if (jj_3R_72()) {
            return true;
        }
        if (jj_scan_token(RPAREN)) {
            return true;
        }
        if (jj_3R_148()) {
            return true;
        }
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_307()) {
            jj_scanpos = xsp;
        }
        return false;
    }

    private boolean jj_3R_58() {
        if (jj_scan_token(TRANSIENT)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_57() {
        if (jj_scan_token(NATIVE)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_56() {
        if (jj_scan_token(SYNCHRONIZED)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_55() {
        if (jj_scan_token(ABSTRACT)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_54() {
        if (jj_scan_token(FINAL)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_322() {
        if (jj_scan_token(_DEFAULT)) {
            return true;
        }
        if (jj_scan_token(COLON)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_53() {
        if (jj_scan_token(PRIVATE)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_321() {
        if (jj_scan_token(CASE)) {
            return true;
        }
        if (jj_3R_72()) {
            return true;
        }
        if (jj_scan_token(COLON)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_315() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_321()) {
            jj_scanpos = xsp;
            if (jj_3R_322()) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_297() {
        if (jj_scan_token(COMMA)) {
            return true;
        }
        if (jj_3R_268()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_52() {
        if (jj_scan_token(PROTECTED)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_51() {
        if (jj_scan_token(STATIC)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_306() {
        if (jj_3R_315()) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_316()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }

    private boolean jj_3R_50() {
        if (jj_scan_token(PUBLIC)) {
            return true;
        }
        return false;
    }

    private boolean jj_3_2() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_50()) {
            jj_scanpos = xsp;
            if (jj_3R_51()) {
                jj_scanpos = xsp;
                if (jj_3R_52()) {
                    jj_scanpos = xsp;
                    if (jj_3R_53()) {
                        jj_scanpos = xsp;
                        if (jj_3R_54()) {
                            jj_scanpos = xsp;
                            if (jj_3R_55()) {
                                jj_scanpos = xsp;
                                if (jj_3R_56()) {
                                    jj_scanpos = xsp;
                                    if (jj_3R_57()) {
                                        jj_scanpos = xsp;
                                        if (jj_3R_58()) {
                                            jj_scanpos = xsp;
                                            if (jj_3R_59()) {
                                                jj_scanpos = xsp;
                                                if (jj_3R_60()) {
                                                    jj_scanpos = xsp;
                                                    if (jj_3R_61()) {
                                                        return true;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean jj_3R_176() {
        if (jj_scan_token(SWITCH)) {
            return true;
        }
        if (jj_scan_token(LPAREN)) {
            return true;
        }
        if (jj_3R_72()) {
            return true;
        }
        if (jj_scan_token(RPAREN)) {
            return true;
        }
        if (jj_scan_token(LBRACE)) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_306()) {
                jj_scanpos = xsp;
                break;
            }
        }
        if (jj_scan_token(RBRACE)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_84() {
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3_2()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }

    private boolean jj_3R_320() {
        if (jj_3R_71()) {
            return true;
        }
        if (jj_3R_72()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_314() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(101)) {
            jj_scanpos = xsp;
            if (jj_scan_token(102)) {
                jj_scanpos = xsp;
                if (jj_3R_320()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean jj_3R_193() {
        if (jj_3R_198()) {
            return true;
        }
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_314()) {
            jj_scanpos = xsp;
        }
        return false;
    }

    private boolean jj_3R_192() {
        if (jj_3R_197()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_49() {
        if (jj_3R_88()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_175() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_191()) {
            jj_scanpos = xsp;
            if (jj_3R_192()) {
                jj_scanpos = xsp;
                if (jj_3R_193()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean jj_3R_191() {
        if (jj_3R_196()) {
            return true;
        }
        return false;
    }

    private boolean jj_3_1() {
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_49()) {
                jj_scanpos = xsp;
                break;
            }
        }
        if (jj_scan_token(PACKAGE)) {
            return true;
        }
        return false;
    }

    private boolean jj_3_38() {
        if (jj_3R_84()) {
            return true;
        }
        if (jj_3R_64()) {
            return true;
        }
        if (jj_scan_token(IDENTIFIER)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_147() {
        if (jj_3R_84()) {
            return true;
        }
        if (jj_3R_64()) {
            return true;
        }
        if (jj_3R_268()) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_297()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }

    private boolean jj_3R_305() {
        if (jj_scan_token(COLON)) {
            return true;
        }
        if (jj_3R_72()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_141() {
        if (jj_3R_149()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_140() {
        if (jj_3R_148()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_139() {
        if (jj_3R_147()) {
            return true;
        }
        if (jj_scan_token(SEMICOLON)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_132() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_139()) {
            jj_scanpos = xsp;
            if (jj_3R_140()) {
                jj_scanpos = xsp;
                if (jj_3R_141()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean jj_3R_120() {
        if (jj_3R_132()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_91() {
        if (jj_scan_token(LBRACE)) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_120()) {
                jj_scanpos = xsp;
                break;
            }
        }
        if (jj_scan_token(RBRACE)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_83() {
        if (jj_scan_token(IDENTIFIER)) {
            return true;
        }
        if (jj_scan_token(COLON)) {
            return true;
        }
        if (jj_3R_148()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_174() {
        if (jj_scan_token(ASSERT)) {
            return true;
        }
        if (jj_3R_72()) {
            return true;
        }
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_305()) {
            jj_scanpos = xsp;
        }
        if (jj_scan_token(SEMICOLON)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_168() {
        if (jj_3R_186()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_167() {
        if (jj_3R_185()) {
            return true;
        }
        return false;
    }

    private boolean jj_3_35() {
        if (jj_scan_token(LBRACKET)) {
            return true;
        }
        if (jj_scan_token(RBRACKET)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_166() {
        if (jj_3R_184()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_165() {
        if (jj_3R_183()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_164() {
        if (jj_3R_182()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_163() {
        if (jj_3R_181()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_162() {
        if (jj_3R_180()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_161() {
        if (jj_3R_179()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_160() {
        if (jj_3R_178()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_159() {
        if (jj_3R_177()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_158() {
        if (jj_3R_176()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_157() {
        if (jj_3R_175()) {
            return true;
        }
        if (jj_scan_token(SEMICOLON)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_156() {
        if (jj_3R_91()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_155() {
        if (jj_3R_174()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_236() {
        if (jj_3R_70()) {
            return true;
        }
        return false;
    }

    private boolean jj_3_37() {
        if (jj_3R_83()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_148() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3_37()) {
            jj_scanpos = xsp;
            if (jj_3R_155()) {
                jj_scanpos = xsp;
                if (jj_3R_156()) {
                    jj_scanpos = xsp;
                    if (jj_scan_token(85)) {
                        jj_scanpos = xsp;
                        if (jj_3R_157()) {
                            jj_scanpos = xsp;
                            if (jj_3R_158()) {
                                jj_scanpos = xsp;
                                if (jj_3R_159()) {
                                    jj_scanpos = xsp;
                                    if (jj_3R_160()) {
                                        jj_scanpos = xsp;
                                        if (jj_3R_161()) {
                                            jj_scanpos = xsp;
                                            if (jj_3R_162()) {
                                                jj_scanpos = xsp;
                                                if (jj_3R_163()) {
                                                    jj_scanpos = xsp;
                                                    if (jj_3R_164()) {
                                                        jj_scanpos = xsp;
                                                        if (jj_3R_165()) {
                                                            jj_scanpos = xsp;
                                                            if (jj_3R_166()) {
                                                                jj_scanpos = xsp;
                                                                if (jj_3R_167()) {
                                                                    jj_scanpos = xsp;
                                                                    if (jj_3R_168()) {
                                                                        return true;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean jj_3R_240() {
        if (jj_3R_242()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_241() {
        if (jj_scan_token(LBRACKET)) {
            return true;
        }
        if (jj_scan_token(RBRACKET)) {
            return true;
        }
        return false;
    }

    private boolean jj_3_34() {
        if (jj_scan_token(LBRACKET)) {
            return true;
        }
        if (jj_3R_72()) {
            return true;
        }
        if (jj_scan_token(RBRACKET)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_239() {
        Token xsp;
        if (jj_3R_241()) {
            return true;
        }
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_241()) {
                jj_scanpos = xsp;
                break;
            }
        }
        if (jj_3R_121()) {
            return true;
        }
        return false;
    }

    private boolean jj_3_36() {
        Token xsp;
        if (jj_3_34()) {
            return true;
        }
        while (true) {
            xsp = jj_scanpos;
            if (jj_3_34()) {
                jj_scanpos = xsp;
                break;
            }
        }
        while (true) {
            xsp = jj_scanpos;
            if (jj_3_35()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }

    private boolean jj_3R_235() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3_36()) {
            jj_scanpos = xsp;
            if (jj_3R_239()) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_238() {
        if (jj_3R_96()) {
            return true;
        }
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_240()) {
            jj_scanpos = xsp;
        }
        return false;
    }

    private boolean jj_3R_142() {
        if (jj_scan_token(COMMA)) {
            return true;
        }
        if (jj_3R_72()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_237() {
        if (jj_3R_235()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_111() {
        if (jj_scan_token(NEW)) {
            return true;
        }
        if (jj_3R_79()) {
            return true;
        }
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_236()) {
            jj_scanpos = xsp;
        }
        xsp = jj_scanpos;
        if (jj_3R_237()) {
            jj_scanpos = xsp;
            if (jj_3R_238()) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_81() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3_33()) {
            jj_scanpos = xsp;
            if (jj_3R_111()) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3_33() {
        if (jj_scan_token(NEW)) {
            return true;
        }
        if (jj_3R_76()) {
            return true;
        }
        if (jj_3R_235()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_134() {
        if (jj_3R_72()) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_142()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }

    private boolean jj_3R_122() {
        if (jj_3R_134()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_96() {
        if (jj_scan_token(LPAREN)) {
            return true;
        }
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_122()) {
            jj_scanpos = xsp;
        }
        if (jj_scan_token(RPAREN)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_152() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(60)) {
            jj_scanpos = xsp;
            if (jj_scan_token(29)) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_144() {
        if (jj_3R_152()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_136() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(65)) {
            jj_scanpos = xsp;
            if (jj_scan_token(69)) {
                jj_scanpos = xsp;
                if (jj_scan_token(74)) {
                    jj_scanpos = xsp;
                    if (jj_scan_token(75)) {
                        jj_scanpos = xsp;
                        if (jj_3R_144()) {
                            jj_scanpos = xsp;
                            if (jj_scan_token(44)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean jj_3R_126() {
        if (jj_3R_136()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_109() {
        if (jj_3R_96()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_108() {
        if (jj_scan_token(DOT)) {
            return true;
        }
        if (jj_scan_token(IDENTIFIER)) {
            return true;
        }
        return false;
    }

    private boolean jj_3_30() {
        if (jj_scan_token(DOT)) {
            return true;
        }
        if (jj_scan_token(THIS)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_107() {
        if (jj_scan_token(LBRACKET)) {
            return true;
        }
        if (jj_3R_72()) {
            return true;
        }
        if (jj_scan_token(RBRACKET)) {
            return true;
        }
        return false;
    }

    private boolean jj_3_29() {
        if (jj_scan_token(DOT)) {
            return true;
        }
        if (jj_scan_token(SUPER)) {
            return true;
        }
        if (jj_scan_token(DOT)) {
            return true;
        }
        return false;
    }

    private boolean jj_3_32() {
        if (jj_3R_82()) {
            return true;
        }
        return false;
    }

    private boolean jj_3_31() {
        if (jj_scan_token(DOT)) {
            return true;
        }
        if (jj_3R_81()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_106() {
        if (jj_scan_token(DOT)) {
            return true;
        }
        if (jj_scan_token(THIS)) {
            return true;
        }
        return false;
    }

    private boolean jj_3_28() {
        if (jj_3R_80()) {
            return true;
        }
        if (jj_scan_token(DOT)) {
            return true;
        }
        if (jj_scan_token(CLASS)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_105() {
        if (jj_scan_token(DOT)) {
            return true;
        }
        if (jj_scan_token(SUPER)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_77() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_105()) {
            jj_scanpos = xsp;
            if (jj_3R_106()) {
                jj_scanpos = xsp;
                if (jj_3_31()) {
                    jj_scanpos = xsp;
                    if (jj_3_32()) {
                        jj_scanpos = xsp;
                        if (jj_3R_107()) {
                            jj_scanpos = xsp;
                            if (jj_3R_108()) {
                                jj_scanpos = xsp;
                                if (jj_3R_109()) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean jj_3_27() {
        if (jj_3R_79()) {
            return true;
        }
        if (jj_scan_token(DOT)) {
            return true;
        }
        if (jj_scan_token(SUPER)) {
            return true;
        }
        if (jj_scan_token(DOT)) {
            return true;
        }
        if (jj_scan_token(IDENTIFIER)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_211() {
        if (jj_3R_85()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_210() {
        if (jj_3R_80()) {
            return true;
        }
        if (jj_scan_token(DOT)) {
            return true;
        }
        if (jj_scan_token(CLASS)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_209() {
        if (jj_3R_81()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_78() {
        if (jj_scan_token(IDENTIFIER)) {
            return true;
        }
        if (jj_scan_token(DOT)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_208() {
        if (jj_scan_token(LPAREN)) {
            return true;
        }
        if (jj_3R_72()) {
            return true;
        }
        if (jj_scan_token(RPAREN)) {
            return true;
        }
        return false;
    }

    private boolean jj_3_26() {
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_78()) {
                jj_scanpos = xsp;
                break;
            }
        }
        if (jj_scan_token(THIS)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_207() {
        if (jj_3R_79()) {
            return true;
        }
        if (jj_scan_token(DOT)) {
            return true;
        }
        if (jj_scan_token(SUPER)) {
            return true;
        }
        if (jj_scan_token(DOT)) {
            return true;
        }
        if (jj_scan_token(IDENTIFIER)) {
            return true;
        }
        return false;
    }

    private boolean jj_3_25() {
        if (jj_3R_77()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_206() {
        if (jj_scan_token(SUPER)) {
            return true;
        }
        if (jj_scan_token(DOT)) {
            return true;
        }
        if (jj_scan_token(IDENTIFIER)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_214() {
        if (jj_scan_token(IDENTIFIER)) {
            return true;
        }
        if (jj_scan_token(DOT)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_205() {
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_214()) {
                jj_scanpos = xsp;
                break;
            }
        }
        if (jj_scan_token(THIS)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_201() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_204()) {
            jj_scanpos = xsp;
            if (jj_3R_205()) {
                jj_scanpos = xsp;
                if (jj_3R_206()) {
                    jj_scanpos = xsp;
                    if (jj_3R_207()) {
                        jj_scanpos = xsp;
                        if (jj_3R_208()) {
                            jj_scanpos = xsp;
                            if (jj_3R_209()) {
                                jj_scanpos = xsp;
                                if (jj_3R_210()) {
                                    jj_scanpos = xsp;
                                    if (jj_3R_211()) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean jj_3R_204() {
        if (jj_3R_136()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_234() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(101)) {
            jj_scanpos = xsp;
            if (jj_scan_token(102)) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_82() {
        if (jj_scan_token(DOT)) {
            return true;
        }
        if (jj_3R_70()) {
            return true;
        }
        if (jj_scan_token(IDENTIFIER)) {
            return true;
        }
        return false;
    }

    private boolean jj_3_24() {
        if (jj_scan_token(LPAREN)) {
            return true;
        }
        if (jj_3R_76()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_198() {
        if (jj_3R_201()) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3_25()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }

    private boolean jj_3R_233() {
        if (jj_scan_token(LPAREN)) {
            return true;
        }
        if (jj_3R_64()) {
            return true;
        }
        if (jj_scan_token(RPAREN)) {
            return true;
        }
        if (jj_3R_224()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_230() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_232()) {
            jj_scanpos = xsp;
            if (jj_3R_233()) {
                return true;
            }
        }
        return false;
    }

    private boolean jj_3R_232() {
        if (jj_scan_token(LPAREN)) {
            return true;
        }
        if (jj_3R_64()) {
            return true;
        }
        if (jj_scan_token(RPAREN)) {
            return true;
        }
        if (jj_3R_218()) {
            return true;
        }
        return false;
    }

    private boolean jj_3_23() {
        if (jj_scan_token(LPAREN)) {
            return true;
        }
        if (jj_3R_64()) {
            return true;
        }
        if (jj_scan_token(LBRACKET)) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_231() {
        if (jj_3R_198()) {
            return true;
        }
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_234()) {
            jj_scanpos = xsp;
        }
        return false;
    }

    private boolean jj_3R_104() {
        if (jj_scan_token(LPAREN)) {
            return true;
        }
        if (jj_3R_64()) {
            return true;
        }
        if (jj_scan_token(RPAREN)) {
            return true;
        }
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(92)) {
            jj_scanpos = xsp;
            if (jj_scan_token(91)) {
                jj_scanpos = xsp;
                if (jj_scan_token(79)) {
                    jj_scanpos = xsp;
                    if (jj_scan_token(76)) {
                        jj_scanpos = xsp;
                        if (jj_scan_token(56)) {
                            jj_scanpos = xsp;
                            if (jj_scan_token(53)) {
                                jj_scanpos = xsp;
                                if (jj_scan_token(43)) {
                                    jj_scanpos = xsp;
                                    if (jj_3R_126()) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean jj_3R_103() {
        if (jj_scan_token(LPAREN)) {
            return true;
        }
        if (jj_3R_64()) {
            return true;
        }
        if (jj_scan_token(LBRACKET)) {
            return true;
        }
        if (jj_scan_token(RBRACKET)) {
            return true;
        }
        return false;
    }

    private boolean jj_3_22() {
        if (jj_scan_token(LPAREN)) {
            return true;
        }
        if (jj_3R_76()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_75() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3_22()) {
            jj_scanpos = xsp;
            if (jj_3R_103()) {
                jj_scanpos = xsp;
                if (jj_3R_104()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean jj_3_21() {
        if (jj_3R_75()) {
            return true;
        }
        return false;
    }

    private boolean jj_3_20() {
        if (jj_3R_74()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_228() {
        if (jj_3R_231()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_227() {
        if (jj_3R_230()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_224() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_226()) {
            jj_scanpos = xsp;
            if (jj_3R_227()) {
                jj_scanpos = xsp;
                if (jj_3R_228()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean jj_3R_226() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(92)) {
            jj_scanpos = xsp;
            if (jj_scan_token(91)) {
                return true;
            }
        }
        if (jj_3R_218()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_197() {
        if (jj_scan_token(DECR)) {
            return true;
        }
        if (jj_3R_198()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_225() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(103)) {
            jj_scanpos = xsp;
            if (jj_scan_token(104)) {
                return true;
            }
        }
        if (jj_3R_216()) {
            return true;
        }
        return false;
    }

    private boolean jj_3_19() {
        if (jj_3R_73()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_229() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(105)) {
            jj_scanpos = xsp;
            if (jj_scan_token(106)) {
                jj_scanpos = xsp;
                if (jj_scan_token(110)) {
                    return true;
                }
            }
        }
        if (jj_3R_218()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_196() {
        if (jj_scan_token(INCR)) {
            return true;
        }
        if (jj_3R_198()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_223() {
        if (jj_3R_224()) {
            return true;
        }
        return false;
    }

    private boolean jj_3_18() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(111)) {
            jj_scanpos = xsp;
            if (jj_3_19()) {
                jj_scanpos = xsp;
                if (jj_3_20()) {
                    return true;
                }
            }
        }
        if (jj_3R_213()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_222() {
        if (jj_3R_197()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_221() {
        if (jj_3R_196()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_218() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_220()) {
            jj_scanpos = xsp;
            if (jj_3R_221()) {
                jj_scanpos = xsp;
                if (jj_3R_222()) {
                    jj_scanpos = xsp;
                    if (jj_3R_223()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean jj_3R_220() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(103)) {
            jj_scanpos = xsp;
            if (jj_scan_token(104)) {
                return true;
            }
        }
        if (jj_3R_218()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_219() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(90)) {
            jj_scanpos = xsp;
            if (jj_scan_token(126)) {
                jj_scanpos = xsp;
                if (jj_scan_token(96)) {
                    jj_scanpos = xsp;
                    if (jj_scan_token(97)) {
                        return true;
                    }
                }
            }
        }
        if (jj_3R_203()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_217() {
        if (jj_scan_token(INSTANCEOF)) {
            return true;
        }
        if (jj_3R_64()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_216() {
        if (jj_3R_218()) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_229()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }

    private boolean jj_3R_215() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(95)) {
            jj_scanpos = xsp;
            if (jj_scan_token(98)) {
                return true;
            }
        }
        if (jj_3R_195()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_213() {
        if (jj_3R_216()) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_225()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }

    private boolean jj_3R_203() {
        if (jj_3R_213()) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3_18()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }

    private boolean jj_3R_212() {
        if (jj_scan_token(BIT_AND)) {
            return true;
        }
        if (jj_3R_190()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_200() {
        if (jj_3R_203()) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_219()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }

    private boolean jj_3R_199() {
        if (jj_scan_token(BIT_OR)) {
            return true;
        }
        if (jj_3R_151()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_195() {
        if (jj_3R_200()) {
            return true;
        }
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_217()) {
            jj_scanpos = xsp;
        }
        return false;
    }

    private boolean jj_3R_202() {
        if (jj_scan_token(XOR)) {
            return true;
        }
        if (jj_3R_172()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_194() {
        if (jj_scan_token(SC_AND)) {
            return true;
        }
        if (jj_3R_143()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_190() {
        if (jj_3R_195()) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_215()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }

    private boolean jj_3R_189() {
        if (jj_scan_token(SC_OR)) {
            return true;
        }
        if (jj_3R_135()) {
            return true;
        }
        return false;
    }

    private boolean jj_3R_172() {
        if (jj_3R_190()) {
            return true;
        }
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_212()) {
                jj_scanpos = xsp;
                break;
            }
        }
        return false;
    }

    private boolean jj_3R_171() {
        if (jj_scan_token(HOOK)) {
            return true;
        }
        if (jj_3R_72()) {
            return true;
        }
        if (jj_scan_token(COLON)) {
            return true;
        }
        if (jj_3R_72()) {
            return true;
        }
        return false;
    }

    private Token jj_consume_token(int kind) throws ParseException {
        Token oldToken;
        if ((oldToken = token).next != null) {
            token = token.next;
        } else {
            token = token.next = token_source.getNextToken();
        }
        jj_ntk = -1;
        if (token.kind == kind) {
            return token;
        }
        token = oldToken;
        throw generateParseException();
    }

    private boolean jj_scan_token(int kind) {
        if (jj_scanpos == jj_lastpos) {
            jj_la--;
            if (jj_scanpos.next == null) {
                jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
            } else {
                jj_lastpos = jj_scanpos = jj_scanpos.next;
            }
        } else {
            jj_scanpos = jj_scanpos.next;
        }
        if (jj_scanpos.kind != kind) {
            return true;
        }
        if (jj_la == 0 && jj_scanpos == jj_lastpos) {
            throw re;
        }
        return false;
    }
    RuntimeException re = new RuntimeException();

    /** Get the next Token. */
    final public Token getNextToken() {
        if (token.next != null) {
            token = token.next;
        } else {
            token = token.next = token_source.getNextToken();
        }
        jj_ntk = -1;
        return token;
    }

    /** Get the specific Token. */
    final public Token getToken(int index) {
        Token t = jj_lookingAhead ? jj_scanpos : token;
        for (int i = 0; i < index; i++) {
            if (t.next != null) {
                t = t.next;
            } else {
                t = t.next = token_source.getNextToken();
            }
        }
        return t;
    }

    private int jj_ntk() {
        if ((nextToken = token.next) == null) {
            return (jj_ntk = (token.next = token_source.getNextToken()).kind);
        } else {
            return (jj_ntk = nextToken.kind);
        }
    }

    /** Generate ParseException. */
    public ParseException generateParseException() {
        Token errortok = token.next;
        int line = errortok.beginLine, column = errortok.beginColumn;
        String mess = (errortok.kind == 0) ? tokenImage[0] : errortok.image;
        return new ParseException("Parse error at line " + line + ", column " + column + ".  Encountered: " + mess);
    }
}
