package ch.epfl.cs108;

public final class Strings {
    private Strings() {}

    /**
     * Returns a string of length n composed only of the character c.
     *
     * @param n
     *            the length of the string to return (>= 0)
     * @param c
     *            the character composing the string
     * @return a string containing n copies of c
     */
    public static String nCopies(int n, char c) {
        if (! (n >= 0))
            throw new IllegalArgumentException();

        StringBuilder b = new StringBuilder();
        for (int i = 0; i < n; ++i)
            b.append(c);
        return b.toString();
    }

    /**
     * Returns the given string s reversed.
     *
     * @param s
     *            the string to reverse
     * @return the reverse of the given string
     */
    public static String reverse(String s) {
        return new StringBuilder(s).reverse().toString();
    }
}
