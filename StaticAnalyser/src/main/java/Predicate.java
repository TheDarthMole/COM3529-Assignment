import javax.script.ScriptException;
import java.util.*;

public class Predicate {
    String data;
    Condition cond;
    Predicate left;
    Predicate right;

    /**
     * Initialise a raw data point
     *
     * @param cond The current node data
     */
    Predicate(Condition cond) {
        this.data = null;
        this.cond = cond;
        this.left = null;
        this.right = null;
    }

    /**
     * Initialise a node tree
     *
     * @param left The left node
     * @param data The current node data
     */
    Predicate(Predicate left, String data) {
        this.data = data;
        this.cond = null;
        this.left = left;
        this.right = null;
    }

    /**
     * Initialise a node tree
     *
     * @param left The left node
     * @param data The current node data
     */
    Predicate(Condition left, String data) {
        this.data = data;
        this.cond = left;
        this.left = null;
        this.right = null;
    }

    /**
     * Initialise a node tree
     *
     * @param left  The left node
     * @param data  The current node data
     * @param right The right node
     */
    Predicate(Predicate left, String data, Predicate right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }

    /**
     * Recursively evaluate the node
     *
     * @param root The node to evaluate
     * @return The evaluated data
     */
    public static boolean evalTree(Predicate root) throws ScriptException {
        if (root == null)
            return false;

        if (root.cond != null)
            return root.cond.evaluate();

        boolean left = evalTree(root.left);
        boolean right = evalTree(root.right);

        switch (root.data) {
            case "&&":
                return left && right;
            case "||":
                return left || right;
            case "!":
                return !left;
            default:
                // Theoretically not accessible
                return root.cond.evaluate();
        }
    }

    /**
     * For one iteration of correlated MCDC, see if there is a pair that satisfies the condition for the major
     * @param index the major condition index
     * @param evaluatedExpressions The currently evaluated expressions
     * @return the two conditions that satisfy the correlated MCDC for a specific major, based on index
     */
    private HashMap<Boolean[], Boolean> findCorrelatedIteration(int index, HashMap<Boolean[], Boolean> evaluatedExpressions) {
        boolean lastValue, lastKey;

        HashMap<Boolean[], Boolean> mcdcSet = new HashMap<>();

        // loop over the existing values that we have evaluated to find the true and false output
        for (Boolean[] key : evaluatedExpressions.keySet()) {

            lastValue = evaluatedExpressions.get(key);
            lastKey = key[index];

            for (Boolean[] key1 : evaluatedExpressions.keySet()) {
                if (key1[index].equals(!lastKey) && evaluatedExpressions.get(key1).equals(!lastValue)) {
                    mcdcSet.put(key, lastValue);
                    mcdcSet.put(key1, evaluatedExpressions.get(key1));

                    return mcdcSet;
                }
            }
        }
        return null;
    }

    /**
     * Generate Correlated MCDC values for an expression
     * @return The Correlated MCDC values
     * @throws ScriptException If there is an error with the input expression
     */
    public HashMap<Boolean[], Boolean> correlatedMCDC() throws ScriptException {
        // Get unique conditions, and generate the combination of inputs
        Condition[] uniqConditions = this.getUniqConditions();
        Boolean[][] inputs = TestCase.generateComboInputs(uniqConditions.length);

        // Setup hashmaps for storing the sets of inputs to outputs
        HashMap<Boolean[], Boolean> evaluatedExpressions = new HashMap<>();
        HashMap<Boolean[], Boolean> correlatedMCDCSet = new HashMap<>();
        HashMap<Boolean[], Boolean> tempMCDCSet;

        // Setup stuff for evaluating predicates
        TestCase testCase = new TestCase(this, uniqConditions[0].varIndex);
        evaluatedExpressions.put(inputs[0], testCase.evaluateTestCase(inputs[0]));

        // For each major, check to see if there is a condition that satisfies the positive and negative output
        for (int i = 0; i < uniqConditions.length; i++) {
            // Check over already evaluated expressions to find an answer
            tempMCDCSet = findCorrelatedIteration(i, evaluatedExpressions);
            if (tempMCDCSet != null) {
                // entries were found, put them in the set
                for (Boolean[] key : tempMCDCSet.keySet())
                    correlatedMCDCSet.put(key, tempMCDCSet.get(key));
            } else {
                // If there was no answer in the already evaluated set, try to find one
                for (int j = evaluatedExpressions.size(); j < inputs.length; j++) {

                    // If the evaluated expression was found to have a partner that worked with mcdc then we're good, exit
                    if (tempMCDCSet != null) {
                        for (Boolean[] key : tempMCDCSet.keySet())
                            correlatedMCDCSet.put(key, tempMCDCSet.get(key));
                        break;
                    }

                    testCase = new TestCase(this, uniqConditions[i].varIndex);
                    evaluatedExpressions.put(inputs[j], testCase.evaluateTestCase(inputs[j]));
                    tempMCDCSet = findCorrelatedIteration(i, evaluatedExpressions);

                }
            }
        }

        // Display and output stuff
        System.out.println(this);
        System.out.println("Unique conditions, equivalences have been found");

        int counter = 0;
        for (Condition cond : uniqConditions) {
            counter++;
            System.out.println("Condition " + counter + ": (" + cond + ")");
        }

        System.out.println("Correlated MCDC output: ");
        for (Boolean[] key : correlatedMCDCSet.keySet())
            System.out.println("Key: " + Arrays.toString(key) + " output : " + correlatedMCDCSet.get(key));

        return correlatedMCDCSet;
    }

    public Condition[] getDuplicateExpressions(Condition cond) {
        ArrayList<Condition> duplicates = new ArrayList<>();

        Condition[] conditions = this.getConditions(new Condition[]{});

        for (Condition possDup : conditions) {
            if (Condition.isEquivalent(possDup, cond))
                duplicates.add(possDup);
        }
        Condition[] retValue = new Condition[duplicates.size()];
        for (int i = 0; i < duplicates.size(); i++)
            retValue[i] = duplicates.get(i);

        return retValue;
    }

    /**
     * Evaluates an expression if it's not already been evaluated
     * @param testCase The test case to use for evaluation
     * @param input The expression inputs
     * @param evaluatedExpressions The currently evaluated expressions hashmap
     * @return The evaluated expression
     * @throws ScriptException If there is an error with the input expression
     */
    private boolean evaluatedOrEval(TestCase testCase, Boolean[] input, HashMap<Boolean[], Boolean> evaluatedExpressions) throws ScriptException {
        if (evaluatedExpressions.containsKey(input))
            return evaluatedExpressions.get(input);

        boolean output = testCase.evaluateTestCase(input);
        evaluatedExpressions.put(input, output);

        return output;
    }

    /**
     * Generate Restricted MCDC for an expression
     * @return The Restricted MCDC test cases
     * @throws ScriptException If there is an error with the input expression
     */
    public HashMap<Boolean[], Boolean> restrictedMCDC() throws ScriptException {
        Condition[] uniqConditions = this.getUniqConditions();
        Boolean[][] inputs = TestCase.generateComboInputs(uniqConditions.length);

        // Setup hashmaps for storing the sets of inputs to outputs
        HashMap<Boolean[], Boolean> evaluatedExpressions = new HashMap<>();
        HashMap<Boolean[], Boolean> restrictedMCDCSet = new HashMap<>();
        boolean tempOutput1, tempOutput2, found;
        Boolean[] majorFlippedInput;

        // For each major, check to see if there is a condition that satisfies the positive and negative output
        for (int i = 0; i < uniqConditions.length; i++) {
            found = false;
            for (Boolean[] input : inputs) {
                if (found)
                    break;

                // Setup stuff for evaluating predicates
                TestCase testCase = new TestCase(this, uniqConditions[0].varIndex);

                // toggle the major condition, keep the rest the same
                majorFlippedInput = input.clone();
                majorFlippedInput[i] = !majorFlippedInput[i];

                tempOutput1 = evaluatedOrEval(testCase, input, evaluatedExpressions);
                tempOutput2 = evaluatedOrEval(testCase, majorFlippedInput, evaluatedExpressions);

                if (tempOutput1 != tempOutput2) {
                    found = true;
                    restrictedMCDCSet.put(input, tempOutput1);
                    restrictedMCDCSet.put(majorFlippedInput, tempOutput2);
                }
            }
        }

        System.out.println(this);
        System.out.println("Unique conditions, equivalences have been found");

        int counter = 0;
        for (Condition cond : uniqConditions) {
            counter++;
            System.out.println("Condition " + counter + ": (" + cond + ")");
        }

        System.out.println("Restricted MCDC output: ");
        for (Boolean[] key : restrictedMCDCSet.keySet())
            System.out.println("Key: " + Arrays.toString(key) + " output : " + restrictedMCDCSet.get(key));

        return restrictedMCDCSet;
    }

    /**
     * Gets all of the unique conditions from the expression
     * Inverse expressions are considered equivalent
     * @return The list of unique conditions from the expression
     */
    public Condition[] getUniqConditions() {
        Condition[] conditions = this.getConditions(new Condition[]{});
        ArrayList<Condition> uniqConditions = new ArrayList<>();
        uniqConditions.add(conditions[0]);
        boolean isUniq;
        int counter;

        // Get the unique conditions
        for (Condition cond : conditions) {
            isUniq = true;
            counter = 0;

            for (Object conda : uniqConditions.toArray()) {
                counter++;

                if (Condition.isEquivalent((Condition) conda, cond))
                    isUniq = false;

                if (isUniq && counter == uniqConditions.size())
                    uniqConditions.add(cond);
            }
        }
        Condition[] retValue = new Condition[uniqConditions.size()];

        for (int i = 0; i < uniqConditions.size(); i++) {
            retValue[i] = uniqConditions.get(i);
        }
        return retValue;
    }

    /**
     * The complete list of conditions in the expression, a recursive function
     * @param inputArray The conditions from the function above
     * @return A full list of conditions for a predicate
     */
    public Condition[] getConditions(Condition[] inputArray) {
        int n = inputArray.length;

        if (this.left == null && this.right == null) {
            Condition[] retValue = new Condition[n + 1];
            System.arraycopy(inputArray, 0, retValue, 0, n);
            retValue[n] = this.cond;
            return retValue;
        }
        Condition[] tempLeft = new Condition[n + 1];
        boolean left = false;
        boolean right = false;
        if (this.left != null) {
            tempLeft = this.left.getConditions(inputArray);
            left = true;
        }

        Condition[] tempRight = new Condition[tempLeft.length + 1];

        if (this.right != null) {
            right = true;
            if (left)
                tempRight = this.right.getConditions(tempLeft);
            else
                tempRight = this.right.getConditions(inputArray);
        }

        if (right)
            return tempRight;
        else
            return tempLeft;
    }

    /**
     * Turn a predicate into a readable string
     * @return The predicate as a string
     */
    public String toString() {
        // The case for a condition
        if (this.left == null && this.right == null)
            return this.cond.toString();

        // The case for !
        if (this.right == null)
            return "(" + this.data + " " + this.left + ")";

        // The case for || or &&
        assert this.left != null;
        return "(" + this.left + ") " + this.data + " (" + this.right + ")";
    }


}
