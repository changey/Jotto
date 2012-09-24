package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class JottoModelTest {
    @Test
    public void testMakeGuess() {
        JottoModel testing = new JottoModel(16952); //cargo
        int[] result = testing.makeGuess("crazy");
        assertEquals(3, result[0]);
        assertEquals(1, result[1]);
        
        result =testing.makeGuess("hello");
        assertEquals(1, result[0]);
        assertEquals(1, result[1]);
        
        result =testing.makeGuess("giraffe");
        assertEquals(null, result);
        
        result =testing.makeGuess("elephant");
        assertEquals(null, result);
        
        result =testing.makeGuess("large");
        assertEquals(3, result[0]);
        assertEquals(3, result[1]);
    }
}
