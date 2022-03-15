public class Predicate {

    enum Operators {
        EQUALS,
        NOT_EQUAL,
        GREATER_THAN,
        LESS_THAN,
        LESS_THAN_EQUAL,
        GREATER_THAN_EQUAL,
        AND,
        OR,
        NOT,
        XOR
    }

    private double left;
    private String operator;
    private double right;

}
