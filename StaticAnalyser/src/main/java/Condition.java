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

        String evalString = this.expression;


        for (String key: this.varIndex.keySet()) {
            evalString.replaceAll(key, String.valueOf(this.varIndex.get(key)));
        }

        return (boolean) engine.eval(evalString);
    }

}
