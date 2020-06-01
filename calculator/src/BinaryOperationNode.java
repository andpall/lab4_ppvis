public class BinaryOperationNode extends Node {
    private char operation;
    private Node left, right;

    public BinaryOperationNode(char operation, Node left, Node right) {
        this.operation = operation;
        this.left = left;
        this.right = right;
    }

    @Override
    public double evaluate() {
        switch (operation) {
            case '+':
                return left.evaluate() + right.evaluate();
            case '-':
                return left.evaluate() - right.evaluate();
            case '*':
                return left.evaluate() * right.evaluate();
            case '/':
                return left.evaluate() / right.evaluate();
            case '%':
                return left.evaluate() % right.evaluate();
            default:
                throw new RuntimeException();
        }
    }

    @Override
    public String toString() {
        return Character.toString(operation);
    }

    public char getOperation() {
        return operation;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }
}
