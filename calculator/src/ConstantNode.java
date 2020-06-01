public class ConstantNode extends Node {
    private double constant;

    public ConstantNode(double constant) {
        this.constant = constant;
    }

    @Override
    public double evaluate() {
        return constant;
    }

    @Override
    public String toString() {
        return Double.toString(constant);
    }

    public double getConstant() {
        return constant;
    }
}
