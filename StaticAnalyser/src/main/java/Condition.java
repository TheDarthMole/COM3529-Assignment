import java.util.HashMap;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class Condition {

    String expression;
    HashMap<String, Integer> varIndex;

    /**
     * Create a predicate
     * @param expression The string expression
     * @param varIndex The data values for the expression
     */
    Condition(String expression, HashMap<String, Integer> varIndex) {
        this.expression = expression;
        this.varIndex = varIndex;
    }

    public boolean evaluate() throws ScriptException {
        // Set up the evaluation manager
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");

        String evalString = this.substitute();
        boolean retValue = (boolean) engine.eval(evalString);

        System.out.println(evalString);
        System.out.printf("%s : %b\n", evalString, retValue);

        return retValue;
    }

    public String substitute() {
        String evalString = this.expression;

        for (String key: this.varIndex.keySet())
            evalString = evalString.replaceAll(key, String.valueOf(this.varIndex.get(key)));

        return evalString;
    }

    public String toString() {
        return this.substitute();
    }

}
