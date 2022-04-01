import org.junit.Test;

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


    private HashMap<Boolean[], Boolean> calculateNextEvaluatedExpression(int i, HashMap<Boolean[], Boolean> current, Boolean[] inputs) {
//        TestCase(this)
        return current;
    }

    public HashMap<Boolean[], Boolean> RestrictedMCDC() throws ScriptException {
        // Get unique conditions, and generate the combination of inputs
        Condition[] uniqConditions = this.getUniqConditions();
        Boolean[][] inputs = TestCase.generateComboInputs(uniqConditions.length);
        HashMap<Boolean[], Boolean> evaluatedExpressions = new HashMap<>();
        int counter = 0;
        TestCase testCase = new TestCase(this, uniqConditions[counter].varIndex);
        evaluatedExpressions.put(inputs[0], testCase.evaluateTestCase(inputs[0]));
        Boolean output;
        boolean hasFalse, hasTrue;
        HashMap<Boolean[], Boolean> restrictedMCDCSet = new HashMap<>();

        // For each major, check to see if there is a condition that satisfies the positive and negative output
        for (int i = 0; i < uniqConditions.length; i++) {
            hasTrue = false;
            hasFalse = false;

            // loop over the existing values that we have evaluated to find the true and false output
            for (Boolean[] key : evaluatedExpressions.keySet()) {

                // If we already have both the cases we need, we can quit out of the for loop
                if (hasTrue && hasFalse)
                    break;

                if (key[i].equals(true)) {
                    if (evaluatedExpressions.get(key).equals(true)) {
                        // If it hasn't already been put in the set for this instance, put it in
                        if (hasTrue == false)
                            restrictedMCDCSet.put(key, evaluatedExpressions.get(key));
                        hasTrue = true;
                    } else {
                        // If it hasn't already been put in the set for this instance, put it in
                        if (hasFalse == false)
                            restrictedMCDCSet.put(key, evaluatedExpressions.get(key));
                        hasFalse = true;
                    }
                }

            }


            // While we don't have both true and false outputs, try to find new ones by generating them
            while ((!(hasTrue && hasFalse)) && counter < inputs.length - 1) {

                // counter is the index of the expression to evaluate
                counter++;

                // Create the test case
                testCase = new TestCase(this, uniqConditions[i].varIndex);
                // Add the evaluated value to the expressions hashmap
                output = testCase.evaluateTestCase(inputs[counter]);
                evaluatedExpressions.put(inputs[counter], output);

                if (!hasFalse && output.equals(false)) {
                    restrictedMCDCSet.put(inputs[counter], false);
                    hasFalse = true;
                } else if (!hasTrue && output.equals(true)) {
                    restrictedMCDCSet.put(inputs[counter], true);
                    hasTrue = true;
                }
            }
        }

        System.out.println(this);

        for (Boolean[] key : restrictedMCDCSet.keySet()) {
            System.out.println("Key: " + Arrays.toString(key) + " output : " + restrictedMCDCSet.get(key));
        }
        return restrictedMCDCSet;
    }

    public Condition[] getUniqConditions() {
        Condition[] conditions = this.getConditions(new Condition[]{});
        ArrayList<Condition> uniqConditions = new ArrayList<>();
        uniqConditions.add(conditions[0]);
        boolean isUniq;
        int counter = 0;

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
//        Condition[] retValue = (Condition[]) uniqConditions.toArray();
        for (int i = 0; i < uniqConditions.size(); i++) {
            retValue[i] = uniqConditions.get(i);
        }
        return retValue;
    }

    public Condition[] getConditions(Condition[] inputArray) {
        int n = inputArray.length;

        if (this.left == null && this.right == null) {
            Condition[] retValue = new Condition[n + 1];
            for (int i = 0; i < n; i++)
                retValue[i] = inputArray[i];
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

    public String toString() {
        // The case for a condition
        if (this.left == null && this.right == null)
            return this.cond.toString();

        // The case for !
        if (this.right == null)
            return "(" + this.data + " " + this.left + ")";

        // The case for || or &&
        return "(" + this.left.toString() + ") " + this.data + " (" + this.right.toString() + ")";
    }


}
