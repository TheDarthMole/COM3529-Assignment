import java.util.HashMap;

public class TestCase {

    Predicate pred;
    Boolean[][] inputs;
    HashMap<String, Integer> values;
    Boolean output;

    TestCase(Predicate pred, Boolean[][] inputs, HashMap<String, Integer> values) {
        this.pred = pred;
        this.inputs = inputs;
        this.values = values;
//        this.output =
    }

    private Boolean[] evaluateTestCase(Boolean[] inputs) {
        Boolean[] retValue = new Boolean[inputs.length];
        // TODO substitue the values into the predicate in order to evaluate them.
//        for ()

        return retValue;
    }

}
