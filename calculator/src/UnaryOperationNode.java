public class UnaryOperationNode extends Node {
    private String operation;
    private Node child;

    public UnaryOperationNode(String operation, Node child) {
        this.operation = operation;
        this.child = child;
    }

    @Override
    public double evaluate() {
        switch (operation) {
            case "-":
                return -child.evaluate();
            case "sin":
                return Math.sin(child.evaluate());
            case "cos":
                return Math.cos(child.evaluate());
            case "tg":
                return Math.tan(child.evaluate());
            case "ctg":
                return 1 / Math.tan(child.evaluate());
            case "sqrt":
                return Math.sqrt(child.evaluate());
            default:
                throw new RuntimeException();
        }
    }

    @Override
    public String toString() {
        return operation;
    }

    public String getOperation() {
        return operation;
    }

    public Node getChild() {
        return child;
    }
}
