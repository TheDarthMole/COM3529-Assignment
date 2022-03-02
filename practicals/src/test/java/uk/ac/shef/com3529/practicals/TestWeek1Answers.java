package uk.ac.shef.com3529.practicals;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
//import static uk.ac.shef.com3529.practicals.Week1.*;
import static uk.ac.shef.com3529.practicals.Week1Fixed.*;

public class TestWeek1Answers {

    // findLast
    // --------
    // (a) Identify the fault: i > 0 instead of i >= 0 in the loop header
    // It iterates up to index 1, rather than index 0
    // [This test will fail (and pass when the fault is fixed, as in Week1Completed.java)]
    @Test
    public void testFindLastFails() {
        // anything where y is the first element in x and does not appear
        // later in the array
        int[] x = {1, 0};
        assertEquals(0, findLast(x, 1));
    }
    // (b) A JUnit test case that does not execute the fault
    @Test
    public void testFindLastDoesNotExecuteFault() {
        // anything that does not execute the loop test,
        // so a null x (throws a NullPointerException before loop is executed)
        assertThrows(NullPointerException.class, () -> {
            findLast(null, 1);
        });
    }
    // (c) A JUnit test case that executes the fault but does not infect the program state
    @Test
    public void testFindLastExecutesFaultDoesNotInfectState() {
        // anything where y is not equal to the element in x at index 0
        // but is equal to an element where the index > 1 (so that no infection occurs)
        // the fault (the loop test) is executed, but the state is not infected
        int[] x = {0, 1};
        assertEquals(1, findLast(x, 1));
    }
    // (d) Infection but no failure
    @Test
    public void testFindLastInfectionNoFailure() {
        // anything where y is not in x
        // (e) the state is infected because the first index of x not considered.
        // However, because y is not in x anyway, the method behaves correctly
        int[] x = {0, 1};
        assertEquals(-1, findLast(x, 2));
    }

    // countPositive
    // --------
    // (a) Fault: if (x[i] >= 0) no if (x[i] > 0), i.e. counts zero as positive
    // [This test will fail (and pass when the fault is fixed, as in Week1Completed.java)]
    @Test
    public void testCountPositiveFails() {
        // anything that has an element of x that is 0
        int[] x = {0};
        assertEquals(0, countPositive(x));
    }
    // (b) A JUnit test case that does not execute the fault
    @Test
    public void testCountPositiveDoesNotExecuteFault() {
        // Anything that does not enter the loop body, so an empty array
        int[] x = {};
        assertEquals(0, countPositive(x));
    }
    // (c) A JUnit test case that executes the fault but does not infect the program state
    @Test
    public void testCountPositiveExecutesFaultDoesNotInfectState() {
        // Anything that does enters the loop body, but is != 0
        int[] x = {-1, 1};
        assertEquals(1, countPositive(x));
    }
    // (d) Infection but no failure
    // Not possible – the state is infected when an element of x = 0
    // this always propagates to the output

    // lastZero
    // --------
    // (a) Fault: could be in two places
    // 1) counts from start of array, so returns the first zero, not the last OR
    // 2) the test for zero should be x[x.length-i-1] == 0 rather than x[i] == 0
    // The following tests are based on (1)
    // [This test will fail (and pass when the fault is fixed, as in Week1Completed.java)]
    @Test
    public void testLastZeroFails() {
        // anything that has more than one zero in x
        int[] x = {0, 0};
        assertEquals(1, lastZero(x));
    }
    // (b) A JUnit test case that does not execute the fault
    @Test
    public void testLastZeroDoesNotExecuteFault() {
        // Anything that does not begin iterating, so a null array
        assertThrows(NullPointerException.class, () -> {
            lastZero(null);
        });
    }
    // (c) A JUnit test case that executes the fault but does not infect the program state
    @Test
    public void testLastZeroExecutesFaultDoesNotInfectState() {
        // x with one element that is zero
        int[] x = {0};
        assertEquals(0, lastZero(x));
    }
    // (d) Infection but no failure
    @Test
    public void testLastZeroInfectionButNoFailure() {
        // x with more than one element, only one zero
        int[] x = {1, 0};
        assertEquals(1, lastZero(x));
    }

    // oddOrPos
    // --------
    // Fault: x[i] % 2 should be Math.abs(x[i] % 2) as it does not
    // count negative numbers as odd
    // [This test will fail (and pass when the fault is fixed, as in Week1Completed.java)]
    @Test
    public void testOddOrPosFails() {
        // anything that has an negative odd element
        int[] x = {-1};
        assertEquals(1, oddOrPos(x));
    }
    // (b) A JUnit test case that does not execute the fault
    @Test
    public void testOddOrPosDoesNotExecuteFault() {
        // Anything that does not begin iterating, so a null or zero-lengthed array
        assertThrows(NullPointerException.class, () -> {
            oddOrPos(null);
        });
    }
    // (c) A JUnit test case that executes the fault but does not infect the program state
    @Test
    public void testOddOrPosExecutesFaultDoesNotInfectState() {
        // x with any elements that are not negative and odd
        int[] x = {0};
        assertEquals(0, oddOrPos(x));
    }
    // (d) Infection but no failure
    // Not possible – the state is infected when an element of x is negative odd,
    // this always propagates to the output
}