import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ConditionTest {

    @Test
    void isEquivalent1() {
        HashMap<String, Integer> values = new HashMap<>();
        values.put("a", 1);
        values.put("b", 2);

        Condition p1 = new Condition("a >= b", values);
        Condition p2 = new Condition("a < b", values);

        Boolean result = Condition.isEquivalent(p1, p2);
        assertEquals(result, true);
    }

    @Test
    void isEquivalent2() {
        HashMap<String, Integer> values = new HashMap<>();
        values.put("a", 1);
        values.put("b", 2);

        Condition p1 = new Condition("a > b", values);
        Condition p2 = new Condition("a <= b", values);

        Boolean result = Condition.isEquivalent(p1, p2);
        assertEquals(result, true);
    }

    @Test
    void isEquivalent3() {
        HashMap<String, Integer> values = new HashMap<>();
        values.put("a", 1);
        values.put("b", 2);

        Condition p1 = new Condition("a == b", values);
        Condition p2 = new Condition("a == b", values);

        Boolean result = Condition.isEquivalent(p1, p2);
        assertEquals(result, true);
    }

    @Test
    void isEquivalent4() {
        HashMap<String, Integer> values = new HashMap<>();
        values.put("a", 1);
        values.put("b", 2);

        Condition p1 = new Condition("a != b", values);
        Condition p2 = new Condition("a == b", values);

        Boolean result = Condition.isEquivalent(p1, p2);
        assertEquals(result, true);
    }

    @Test
    void isEquivalent5() {
        HashMap<String, Integer> values = new HashMap<>();
        values.put("a", 1);
        values.put("b", 2);

        Condition p1 = new Condition("a > b", values);
        Condition p2 = new Condition("a == b", values);

        Boolean result = Condition.isEquivalent(p1, p2);
        assertEquals(result, false);
    }

    @Test
    void isEquivalent6() {
        HashMap<String, Integer> values = new HashMap<>();
        values.put("a", 1);
        values.put("b", 2);

        Condition p1 = new Condition("a > b", values);
        Condition p2 = new Condition("a < b", values);

        Boolean result = Condition.isEquivalent(p1, p2);
        assertEquals(result, false);
    }

    @Test
    void isEquivalent7() {
        HashMap<String, Integer> values = new HashMap<>();
        values.put("a", 1);
        values.put("b", 2);

        Condition p1 = new Condition("a < b", values);
        Condition p2 = new Condition("a < b", values);

        Boolean result = Condition.isEquivalent(p1, p2);
        assertEquals(result, true);
    }

}