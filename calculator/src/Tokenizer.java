import javafx.util.Pair;

import java.util.ArrayList;

public class Tokenizer {
    static ArrayList<Pair<String, String>> tokenize(String string) {
        ArrayList<Pair<String, String>> ans = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        String type;
        for (int i = 0; i < string.length(); ++i, buffer.delete(0, buffer.length())) {
            char c = string.charAt(i);
            if (c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')' || c=='%') {
                buffer.append(c);
                type = "operation";
            } else if (Character.isDigit(c)) {
                buffer.append(c);
                ++i;
                for (; i < string.length(); ++i) {
                    c = string.charAt(i);
                    if (Character.isDigit(c) || c == '.') {
                        buffer.append(c);
                    } else {
                        --i;
                        break;
                    }
                }
                type = "number";
            } else if (c == 's' || c == 'c') {
                buffer.append(c);
                ++i;
                c = string.charAt(i);
                buffer.append(c);
                ++i;
                c = string.charAt(i);
                buffer.append(c);
                if (c == 'r') {
                    ++i;
                    c = string.charAt(i);
                    buffer.append(c);
                }
                type = "operation";
            } else if (c == 't') {
                buffer.append(c);
                ++i;
                c = string.charAt(i);
                buffer.append(c);
                type = "operation";
            } else {
                throw new RuntimeException("Wrong input");
            }
            ans.add(new Pair<>(buffer.toString(), type));
            buffer.delete(0, buffer.length());
        }
        return ans;
    }
}
