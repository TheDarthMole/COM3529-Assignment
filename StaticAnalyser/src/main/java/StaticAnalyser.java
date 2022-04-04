import javax.script.ScriptException;
import java.util.HashMap;

public class StaticAnalyser {

    public static void main(String[] args) throws ScriptException {

        test1();

        test2();
    }

    public static void test1() throws ScriptException {
        // Setup values
        HashMap<String, Integer> values = new HashMap<>();
        values.put("year1", 2020);
        values.put("year2", 2021);
        values.put("month1", 1);
        values.put("month2", 3);
        values.put("day1", 5);
        values.put("day2", 15);

        // year2 < year1
        Condition c1 = new Condition("year2 < year1", values);
        Predicate p1 = new Predicate(c1);

        // year2 == year1 && month2 < month1
        Condition c2 = new Condition("year2 == year1", values);
        Predicate p2 = new Predicate(c2);
        Condition c3 = new Condition("month2 < month1", values);
        Predicate p3 = new Predicate(c3);
        // Join the predicates together
        Predicate p7 = new Predicate(p2, "&&", p3);

        // year2 == year1 && month2 == month1 && day2 < day1
        Condition c4 = new Condition("year2 == year1", values);
        Predicate p4 = new Predicate(c4);
        Condition c5 = new Condition("month2 == month1", values);
        Predicate p5 = new Predicate(c5);
        Condition c6 = new Condition("day2 < day1", values);
        Predicate p6 = new Predicate(c6);
        // Join the predicates together
        Predicate p8 = new Predicate(p4, "&&", p5);
        Predicate p9 = new Predicate(p8, "&&", p6);

        // Join them all together
        Predicate p10 = new Predicate(p1, "||", p7);
        // Create the almighty root node, p11
        Predicate p11 = new Predicate(p10, "||", p9);

        // Evaluate the whole thing!
        System.out.println(Predicate.evalTree(p11));
        System.out.println(p11);

        p11.correlatedMCDC();
        p11.restrictedMCDC();

    }

    public static void test2() throws ScriptException {
        // Setup values
        HashMap<String, Integer> values = new HashMap<>();
        values.put("side1", 5);
        values.put("side2", 2);
        values.put("side3", 3);

//        // Set up the conditions and disjuncts
//        (c1 && (c2 || c3)) && (c4 || c5)
        Condition c1 = new Condition("side1 + side2 > side3", values);
        Predicate p1 = new Predicate(c1);
        Condition c2 = new Condition("side1 == side2", values);
        Predicate p2 = new Predicate(c2);
        Condition c3 = new Condition("side2 == side3", values);
        Predicate p3 = new Predicate(c3);
        Condition c4 = new Condition("side1 != side2", values);
        Predicate p4 = new Predicate(c4);
        Condition c5 = new Condition("side2 != side3", values);
        Predicate p5 = new Predicate(c5);

        Predicate p6 = new Predicate(p2, "||", p3);
        Predicate p7 = new Predicate(p4, "||", p5);
        Predicate p8 = new Predicate(p1, "&&", p6);
        Predicate p9 = new Predicate(p8, "&&", p7);

//        System.out.println(Predicate.evalTree(p9));
//        System.out.println(p9);
        p9.restrictedMCDC();
        p9.correlatedMCDC();

    }


}

