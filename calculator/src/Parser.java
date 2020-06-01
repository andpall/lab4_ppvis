import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

public class Parser {
    private static Map<String, Integer> priority = new TreeMap<>();
    private static boolean isInit = false;

    public static Node ParseExpression(String s) {
        ArrayList<Pair<String, String>> tokens = Tokenizer.tokenize(s);
        init();
        ArrayList<Pair<String, String>> expression = toReversePolishNotation(tokens);
        return calculate(expression);
    }

    private static ArrayList<Pair<String, String>> toReversePolishNotation(ArrayList<Pair<String, String>> tokens) {
        ArrayList<Pair<String, String>> expression = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        for (Pair<String, String> pair : tokens) {
            String value = pair.getValue(),
                    key = pair.getKey();
            if (value.equals("number")) {
                expression.add(pair);
            } else {
                if (key.equals("(")) {
                    stack.push(key);
                } else if (key.equals(")")) {
                    while (!stack.peek().equals("(")) {
                        String operation = stack.peek();
                        expression.add(new Pair<>(operation, operationType(operation)));
                        stack.pop();
                    }
                    stack.pop();
                } else {
                    int keyPriority = priority.get(key);
                    if (stack.empty() || priority.get(stack.peek()).compareTo(keyPriority) < 0) {
                        stack.push(key);
                    } else {
                        while (!stack.isEmpty() && priority.get(stack.peek()).compareTo(keyPriority) >= 0) {
                            String operation = stack.peek();
                            expression.add(new Pair<>(operation, operationType(operation)));
                            stack.pop();
                        }
                        stack.push(key);
                    }
                }
            }
        }
        while (!stack.isEmpty()) {
            String operation = stack.peek();
            expression.add(new Pair<>(operation, operationType(operation)));
            stack.pop();
        }
        return expression;
    }

    private static Node calculate(ArrayList<Pair<String, String>> expression) {
        Stack<Node> stack = new Stack<>();
        for (Pair<String, String> pair : expression) {
            if (pair.getValue().equals("number")) {
                stack.push(new ConstantNode(Double.parseDouble(pair.getKey())));
            } else if (pair.getValue().equals("binary_operation")) {
                Node second = stack.peek();
                stack.pop();
                Node first = stack.peek();
                stack.pop();
                switch (pair.getKey()) {
                    case "+":
                        stack.push(new BinaryOperationNode('+',first,second));
                        break;
                    case "-":
                        stack.push(new BinaryOperationNode('-',first,second));
                        break;
                    case "*":
                        stack.push(new BinaryOperationNode('*',first,second));
                        break;
                    case "/":
                        stack.push(new BinaryOperationNode('/',first,second));
                        break;
                    case "%":
                        stack.push(new BinaryOperationNode('%',first,second));
                        break;
                }
            } else {
                Node first = stack.peek();
                stack.pop();
                switch (pair.getKey()) {
                    case "sin":
                        stack.push(new UnaryOperationNode("sin",first));
                        break;
                    case "cos":
                        stack.push(new UnaryOperationNode("cos",first));
                        break;
                    case "tg":
                        stack.push(new UnaryOperationNode("tg",first));
                        break;
                    case "ctg":
                        stack.push(new UnaryOperationNode("ctg",first));
                        break;
                    case "sqrt":
                        stack.push(new UnaryOperationNode("sqrt",first));
                        break;
                }
            }
        }
        return stack.peek();
    }

    private static void init() {
        if (!isInit) {
            isInit = true;
            priority.put("*", 3);
            priority.put("/", 3);
            priority.put("+", 2);
            priority.put("-", 2);
            priority.put("%", 2);
            priority.put("(", 1);
            priority.put("sin", 4);
            priority.put("cos", 4);
            priority.put("tg", 4);
            priority.put("ctg", 4);
            priority.put("sqrt", 4);
        }
    }

    private static String operationType(String operation) {
        if (operation.equals("+") || operation.equals("-") || operation.equals("*") ||
                operation.equals("/") || operation.equals("%")) {
            return "binary_operation";
        } else {
            return "unary_operation";
        }
    }
}
