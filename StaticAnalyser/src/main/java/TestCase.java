import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.HashMap;

public class TestCase {

    Predicate pred;
    HashMap<String, Integer> values;

    TestCase(Predicate pred, HashMap<String, Integer> values) {
        this.pred = pred;
        this.values = values;
    }

    /**
     * Evaluate the expression based on some inputs
     * @param inputs the inputs for the expression
     * @return The evaluated expression boolean
     * @throws ScriptException If there is an error with the input expression
     */
    public Boolean evaluateTestCase(Boolean[] inputs) throws ScriptException {
        String evalString = this.pred.toString();
        Condition[] uniqCond = this.pred.getUniqConditions();

        // Replace the conditions with their 'answers' to the conditions
        for (int i = 0; i < inputs.length; i++) {
            Condition[] duplicates = this.pred.getDuplicateExpressions(uniqCond[i]);
            // After getting the duplicates, we replace the duplicates and
            // their 'equivalent' values with the input values
            for (Condition cond : duplicates)
                evalString = evalString.replace(cond.toString().strip(), inputs[i].toString());
        }

        // Evaluate the expression using the javascript engine, ez
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");

        return (boolean) engine.eval(evalString);
    }

    /**
     * Generate a list of inputs for MCDC for a function
     * @param numInputs The number of inputs to enumerate
     * @return The full set of inputs of size numInputs,
     */
    public static Boolean[][] generateComboInputs(int numInputs) {
        int numGenerated = (int) Math.pow(2, numInputs) - 1;
        Boolean[][] retValue = new Boolean[numGenerated + 1][numInputs];

        String binaryString;
        Boolean[] testCase;
        for (int x = 0; x <= numGenerated; x++) {
            binaryString = Integer.toBinaryString(x);
            // pad the string to be the number of inputs long
            binaryString = "0".repeat(numInputs - binaryString.length()) + binaryString;

            testCase = new Boolean[numInputs];
            for (int i = 0; i < binaryString.length(); i++) {
                testCase[i] = binaryString.charAt(i) != '0';
            }
            retValue[x] = testCase;
        }
        return retValue;
    }


}
