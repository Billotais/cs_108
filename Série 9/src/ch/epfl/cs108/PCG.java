package ch.epfl.cs108;

/**
 * Immutable PCG random number generator, toy variant: 16 bits of state, 8 bits
 * of output.
 * 
 * See http://www.pcg-random.org/
 */
public final class PCG {
    private final int state;
    
    /**
     * Create a new (immutable) PCG random number "generator" with the given
     * state (seed).
     * 
     * @param state
     *            the current state of the PCG generator.
     */
    public PCG(int state) {
        this.state = state & 0xFFFF;
    }

    /**
     * Compute the next state of the PCG generator.
     * 
     * @return the next state of the PCG generator.
     */
    public PCG next() {
        return new PCG((state * 12829) + 47989);
    }
    
    /**
     * Return the current value of the PCG generator, between 0 and 255
     * (included).
     * 
     * @return the current value of the PCG generator.
     */
    public int get() {
        return rotateRight8((((state >> 5) ^ state) >> 5) & 0xFF, state >> 13);
    }
    
    // Rotate the least-significant 8 bits of x by y positions on the right.
    private static int rotateRight8(int x, int y) {
        assert 0 <= x && x <= 0xFF;
        return (x >> y) | ((x << (8 - y)) & 0xFF);
    }
}
