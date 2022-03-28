import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

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

    public Integer[] RestrictedMCDC() {
        Condition[] conditions = this.getConditions(new Condition[] {});
        ArrayList<Condition> uniqConditions = new ArrayList<>();
        uniqConditions.add(conditions[0]);

        // Get the unique conditions
        boolean isUniq;
        Condition temp;

        // TODO Fix this part, it broken

        for (Condition cond: conditions) {
            Iterator iterator = uniqConditions.iterator();
            isUniq = true;
            while (iterator.hasNext()) {
                temp = (Condition) iterator.next();
                System.out.println("Comparing " + temp + " to " + cond);
                if (Condition.isEquivalent(cond, temp)) {
                    isUniq = false;
                    break;
                }
                if (isUniq)
                    uniqConditions.add(cond);
            }
//            for (Condition uniqCond: ArrayList.asList(uniqConditions)) {
//
//                System.out.println("Compare " + cond.toString() + " to " + inArray.toString());
//
//                if ( ! Condition.isEquivalent(cond, inArray)) {
//                    System.out.println("True");
//                    uniqConditions.add(cond);
//                }
//            }
            System.out.println(uniqConditions);
        }
        System.out.println(uniqConditions.size());
        System.out.println(conditions.length);


        Integer[] retValue = {};
//        for (Predicate pred : )

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
            return "(" + this.cond.toString() + ") ";

        // The case for !
        if (this.right == null)
            return this.data + " " + this.left;

        // The case for || or &&
        return this.left.toString() + this.data + " " + this.right.toString();
    }


}
