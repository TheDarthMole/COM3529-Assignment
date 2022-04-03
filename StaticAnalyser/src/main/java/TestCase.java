import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.HashMap;

public class TestCase {

    Predicate pred;
    HashMap<String, Integer> values;
    Boolean[] output;

    TestCase(Predicate pred, HashMap<String, Integer> values) {
        this.pred = pred;
        this.values = values;
    }

    public Boolean evaluateTestCase(Boolean[] inputs) throws ScriptException {
        String evalString = this.pred.toString();
        Condition[] uniqCond =  this.pred.getUniqConditions();


        // Replace the conditions with their 'answers' to the conditions
        for (int i = 0; i < inputs.length; i++) {
            evalString = evalString.replaceAll(uniqCond[i].toString(),inputs[i].toString());
        }

        // Evaluate the expression using the javascript engine, ez
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");

        boolean retValue = (boolean) engine.eval(evalString);

        // For testing, we can see the output of the eval engine
//        System.out.println("Input: " + evalString + " output: " + retValue);

        return retValue;
    }

    public HashMap<Boolean[], Boolean> getNextEvaluatedCondition(HashMap<Boolean[], Boolean> hashMap, int conditionLength, int evalNumber ) throws ScriptException {
        Boolean[][] comboInputs = generateComboInputs(conditionLength);
        hashMap.put(comboInputs[evalNumber], evaluateTestCase(comboInputs[evalNumber]));
        return hashMap;
    }

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
                if (binaryString.charAt(i) == '0')
                    testCase[i] = false;
                else
                    testCase[i] = true;
            }
            retValue[x] = testCase;
        }
        return retValue;
    }


}
