package ch.epfl.cs108;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

public class BurrowsWheelerTransformTest {
    private static String toBeOrNotToBe =
            "To be, or not to be, that is the question";
    private static Pair<Integer, String> toBeOrNotToBeBWT =
            new Pair<>(11, "ootr,e,steenh  hbbuttt o Tti n oieao  s q");

    @Test(expected = IllegalArgumentException.class)
    public void forwardTransformOfEmptyStringFails() {
        BurrowsWheelerTransform.forward("");
    }

    @Test
    public void forwardTransformOfTrivialStringIsCorrect() {
        assertEquals(new Pair<>(0, "a"), BurrowsWheelerTransform.forward("a"));
    }

    @Test
    public void forwardTransformOfKnownStringIsCorrect() {
        assertEquals(toBeOrNotToBeBWT, BurrowsWheelerTransform.forward(toBeOrNotToBe));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void backwardTransformWithEmptyStringFails() {
        BurrowsWheelerTransform.backward(new Pair<>(0, ""));
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void backwardTransformWithNegativeIndexFails() {
        BurrowsWheelerTransform.backward(new Pair<>(-1, "test"));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void backwardTransformWithTooBigIndexFails() {
        BurrowsWheelerTransform.backward(new Pair<>(4, "test"));
    }

    @Test
    public void backwardTransformOfKnownPairIsCorrect() {
        assertEquals(toBeOrNotToBe, BurrowsWheelerTransform.backward(toBeOrNotToBeBWT));
    }

    @Test
    public void backwardTransformOfRandomStringIsInverseOfForwardTransform() {
        String alphabet = "abcdefghijklmnopqrstuvwxyz 0123456789,.;:!?";
        Random rng = new Random();
        for (int i = 0; i < 1000; ++i) {
            StringBuilder b = new StringBuilder();
            for (int l = 0; l < 1 + rng.nextInt(100); ++l)
                b.append(alphabet.charAt(rng.nextInt(alphabet.length())));
            String s = b.toString();
            assertEquals(s, BurrowsWheelerTransform.backward(BurrowsWheelerTransform.forward(s)));
        }
    }
}
