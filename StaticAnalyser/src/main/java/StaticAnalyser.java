import javax.script.ScriptException;
import java.io.*;
import java.util.HashMap;

public class StaticAnalyser {

    public static void main(String[] args) throws ScriptException {
        // Setup values
        HashMap<String, Integer> values = new HashMap<String, Integer>();
        values.put("side1", 1);
        values.put("side2", 2);
        values.put("side3", 3);

        // Set up the conditions and disjuncts
        Condition c1 = new Condition("side1 + side2 > side3", values);
        Disjunct d1 = new Disjunct(c1);
        Condition c2 = new Condition("side1 == side2 || side2 == side3", values);
        Disjunct d2 = new Disjunct(c2);
        Condition c3 = new Condition("side1 != side2 || side2 != side3", values);
        Disjunct d3 = new Disjunct(c3);

        // Setup the main disjuncts
        Disjunct d4 = new Disjunct(d1, "&&", d2);
        Disjunct d5 = new Disjunct(d4, "&&", d3);

        StaticAnalyser analyser = new StaticAnalyser();
        System.out.println(Disjunct.evalTree(d5));
    }

}

