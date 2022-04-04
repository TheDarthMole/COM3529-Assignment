import java.util.HashMap;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class Condition {

    String expression;
    HashMap<String, Integer> varIndex;

    /**
     * Create a predicate
     *
     * @param expression The string expression
     * @param varIndex   The data values for the expression
     */
    Condition(String expression, HashMap<String, Integer> varIndex) {
        this.expression = expression;
        this.varIndex = varIndex;
    }

    /**
     * evaluate the expression
     *
     * @return returns true or false depending on the expression with the values substituted in
     * @throws ScriptException
     */
    public boolean evaluate(HashMap<String, Integer> values) throws ScriptException {
        // Set up the evaluation manager
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");

        String evalString = this.substitute(values);

        return (boolean) engine.eval(evalString);
    }

    public boolean evaluate() throws ScriptException {
        return this.evaluate(this.varIndex);
    }

    /**
     * Substitute the values from the condition into the equation
     *
     * @return The expression with the values substituted in
     */
    public String substitute() {
        return this.substitute(this.varIndex);
    }

    /**
     * Substitute the values into the equation
     * @param values The values to substitute
     * @return
     */
    public String substitute(HashMap<String, Integer> values) {
        String evalString = this.expression;

        for (String key : values.keySet())
            evalString = evalString.replace(key, String.valueOf(this.varIndex.get(key)));

        return evalString;
    }

    public String toString() {
        return this.expression;
    }

    /**
     * Find if two conditions are """EQUIVALENT"""
     * 1 == 1 and 1 != 1 are apparently equivalent, help us, life is a lie
     *
     * @param cond1 The first condition
     * @param cond2 The second condition
     * @return true if equivalent, false otherwise
     */
    public static boolean isEquivalent(Condition cond1, Condition cond2) {
        // We will be using the String .expression value, this will look something like the follows:
        // 1 == 1  // 1 == 2
        // 3 > 2   // 3 <= 5
        // 5 != 4

        // Calculate if the expression is equivalent

        if ((cond1.expression.contains("==") || cond1.expression.contains("!=")) &&
                (cond2.expression.contains("==") || cond2.expression.contains("!="))) {

            String[] cond1Split = cond1.getValuesFromEquation();
            String[] cond2Split = cond2.getValuesFromEquation();

            assert cond1Split != null;
            assert cond2Split != null;
            String cond1L = cond1Split[0].strip();
            String cond1R = cond1Split[1].strip();
            String cond2L = cond2Split[0].strip();
            String cond2R = cond2Split[1].strip();
            if ((cond1L.equals(cond2R) && cond1R.equals(cond2L)) ||
                    (cond1L.equals(cond2L) && cond1R.equals(cond2R)))
                return true;
            // Calculate if the expression is equivalent via not equals and equals
        }


        // Check A cond B is directly equivalent to A cond B, including structure
        for (String condition : new String[]{">", ">=", "<", "<="}) {
            if (cond1.expression.contains(condition) && cond2.expression.contains(condition)) {
                String[] cond1Split = cond1.getValuesFromEquation();
                String[] cond2Split = cond2.getValuesFromEquation();


                // Check equation1 left == equation2 left && equation1 right == equation2 right
                assert cond1Split != null;
                assert cond2Split != null;
                if ((cond1Split[0].strip().equals(cond2Split[0].strip()) &&
                        cond1Split[1].strip().equals(cond2Split[1].strip())))
                    return true;
            }
        }

        //
        if ((cond1.expression.contains(">") && cond2.expression.contains("<=")) ||
                (cond1.expression.contains("<") && cond2.expression.contains(">=")) ||
                (cond1.expression.contains(">=") && (cond2.expression.contains("<"))) ||
                (cond1.expression.contains("<=") && cond2.expression.contains(">"))) {
            String[] cond1Split = cond1.getValuesFromEquation();
            String[] cond2Split = cond2.getValuesFromEquation();
            assert cond1Split != null;
            assert cond2Split != null;
            if (cond1Split[0].equals(cond2Split[0]) && cond1Split[1].equals(cond2Split[1]))
                return true;

        }

        if ((cond1.expression.contains(">") && cond2.expression.contains("<=")) ||
                (cond1.expression.contains("<") && cond2.expression.contains(">=")) ||
                (cond1.expression.contains(">=") && (cond2.expression.contains("<"))) ||
                (cond1.expression.contains("<=") && cond2.expression.contains(">"))) {
            String[] cond1Split = cond1.getValuesFromEquation();
            String[] cond2Split = cond2.getValuesFromEquation();
            assert cond1Split != null;
            assert cond2Split != null;
            return cond1Split[0].equals(cond2Split[1]) && cond1Split[1].equals(cond2Split[0]);
        }
        return false;
    }

    /**
     * Grabs the values that are stored in .equation and return them as values in a string array
     *
     * @return An array of the variables from the equation, return[0] is the left, return[1] is the right
     */
    private String[] getValuesFromEquation() {
        String[] retValue;
        String[] operators = {"==", "!=", ">=", ">", "<=", "<"};
        for (String operator : operators) {
            if (this.expression.contains(operator)) {
                retValue = this.expression.split(operator);
                return retValue;
            }
        }
        return null;
    }


}
