//Yifan Wan
//10/30/2023
import java.util.Stack;

public class Parenthesization {
    public static void main(String[] args) {
        String[] tests = {"(", "}", "({})", ""};
        for (int i = 0; i < tests.length; i++) {
            System.out.println(tests[i]);
            boolean myBool = isReadable(tests[i]);
            if (myBool) {
                System.out.println(" is readable");
            } else {
                System.out.println(" is NOT readable");
            }
        }
    }

    public static boolean isReadable(String s) {
        Stack<Character> testingStack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char currentChar = s.charAt(i);

            if (currentChar == '(' || currentChar == '[' || currentChar == '{') {
                testingStack.push(currentChar);
            } else if (currentChar == ')' || currentChar == ']' || currentChar == '}') {
                if (testingStack.isEmpty()) {
                    return false;  // Unmatched closing parenthesis
                }
                char top = testingStack.pop();
                if (!isValidPair(top, currentChar)) {
                    return false;  // Mismatched opening and closing parenthesis
                }
            }
        }

        return testingStack.isEmpty();  // True if all parentheses are correctly matched and closed
    }

    private static boolean isValidPair(char open, char close) {
        return (open == '(' && close == ')') ||
               (open == '[' && close == ']') ||
               (open == '{' && close == '}');
    }
}
