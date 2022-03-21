import javax.script.ScriptException;

public class Disjunct {
    String data;
    Condition cond;
    Disjunct left;
    Disjunct right;

    /**
     * Initialise a raw data point
     * @param cond The current node data
     */
    Disjunct(Condition cond) {
        this.data = null;
        this.cond = cond;
        this.left = null;
        this.right = null;
    }

    /**
     * Initialise a node tree
     * @param left The left node
     * @param data The current node data
     */
    Disjunct(Disjunct left, String data) {
        this.data = data;
        this.cond = null;
        this.left = left;
        this.right = null;
    }

    /**
     * Initialise a node tree
     * @param left The left node
     * @param data The current node data
     */
    Disjunct(Condition left, String data) {
        this.data = data;
        this.cond = left;
        this.left = null;
        this.right = null;
    }

    /**
     * Initialise a node tree
     * @param left The left node
     * @param data The current node data
     * @param right The right node
     */
    Disjunct(Disjunct left, String data, Disjunct right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }

    /**
     * Recursively evaluate the node
     * @param root The node to evaluate
     * @return The evaluated data
     */
    public static boolean evalTree(Disjunct root) throws ScriptException {
        if (root == null)
            return false;

        if (root.cond != null)
            return root.cond.evaluate();

        boolean left = evalTree(root.left);
        boolean right = evalTree(root.right);

        switch(root.data) {
            case "&&":
                return left && right;
            case "||":
                return left || right;
            case "!":
                return ! left;
            default:
                // Theoretically not accessible
                return root.cond.evaluate();
        }
    }

    public String toString() {
        if (this.left == null && this.right == null)
                return "(" + this.cond.toString() + ") ";

        if (this.right == null)
            return this.left + this.data;

        return this.left.toString() + this.data + " " + this.right.toString();
    }

}
