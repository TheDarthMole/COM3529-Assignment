package uk.ac.shef.com3529.lectures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTriangle {

    @Test
    public void testInvalid() {
        assertEquals(Triangle.Type.INVALID, Triangle.classify(0,0,0));
    }

    @Test
    public void testScalene() {
        assertEquals(Triangle.Type.SCALENE, Triangle.classify(0,0,0));
    }
}
