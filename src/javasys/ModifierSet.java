package javasys;

public final class ModifierSet {

    /* Definitions of the bits in the modifiers field.  */
    public static final int PUBLIC = 1;
    public static final int PROTECTED = 2;
    public static final int PRIVATE = 4;
    public static final int ABSTRACT = 8;
    public static final int STATIC = 16;
    public static final int FINAL = 32;
    public static final int SYNCHRONIZED = 64;
    public static final int NATIVE = 128;
    public static final int TRANSIENT = 256;
    public static final int VOLATILE = 512;
    public static final int STRICTFP = 4096;

    /** A set of accessors that indicate whether the specified modifier
    is in the set. */
    public boolean isPublic(int modifiers) {
        return (modifiers & PUBLIC) != 0;
    }

    public boolean isProtected(int modifiers) {
        return (modifiers & PROTECTED) != 0;
    }

    public boolean isPrivate(int modifiers) {
        return (modifiers & PRIVATE) != 0;
    }

    public boolean isStatic(int modifiers) {
        return (modifiers & STATIC) != 0;
    }

    public boolean isAbstract(int modifiers) {
        return (modifiers & ABSTRACT) != 0;
    }

    public boolean isFinal(int modifiers) {
        return (modifiers & FINAL) != 0;
    }

    public boolean isNative(int modifiers) {
        return (modifiers & NATIVE) != 0;
    }

    public boolean isStrictfp(int modifiers) {
        return (modifiers & STRICTFP) != 0;
    }

    public boolean isSynchronized(int modifiers) {
        return (modifiers & SYNCHRONIZED) != 0;
    }

    public boolean isTransient(int modifiers) {
        return (modifiers & TRANSIENT) != 0;
    }

    public boolean isVolatile(int modifiers) {
        return (modifiers & VOLATILE) != 0;
    }

    /**
     * Removes the given modifier.
     */
    static int removeModifier(int modifiers, int mod) {
        return modifiers & ~mod;
    }
}
