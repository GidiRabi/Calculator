import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Calculator {

    private static Calculator instance;
    private CalculatorGUI calculatorGUI; // CalculatorGUI instance
    public static String text = "";
    public static String Ans = null;

    // Private constructor to prevent instantiation from outside
    Calculator(String txt) {
        // Initialize any necessary variables here
        text = txt;
    }

    // Public method to get the singleton instance
    public static Calculator getInstance() {
        if (instance == null) {
            instance = new Calculator(getText());
        }
        return instance;
    }

    private static String getText() {
        return text;
    }


    public String lastAns(){
        return Ans;
    }

    public String calculate() {
        if(!isValid(text)){
            return "Math Error , [AC] : Cancel";
        }
        ArrayList<String> elements = parseExpression(text);
        //printElements(elements);
        text = evaluate(elements);
        Ans = text;
        return text;
    }


    //using shunting yard algorithm to evaluate the expression
    public static String evaluate(ArrayList<String> expression) {
        if (expression.isEmpty()) {
            return "";
        }

        Stack<String> stack = new Stack<>();
        LinkedList<String> output = new LinkedList<>();

        for (String s : expression) {
            if (!isOperator(s)) {
                output.add(s);
            } else if (s.equals("(")) {
                stack.push(s);
            } else if (s.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    output.add(stack.pop());
                }
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else if (isOperator(s)) {
                while (!stack.isEmpty() && isOperator(stack.peek()) && (precedence(s) <= precedence(stack.peek()))) {
                    output.add(stack.pop());
                }
                stack.push(s);
            }
        }

        while (!stack.isEmpty()) {
            output.add(stack.pop());
        }

//        //if there is a division by 0 then return error
//        for (int i = output.size() - 1; i > 0 ; i--) {
//            if (output.get(i).equals("/")) {
//                if (output.get(i -1).equals("0")) {
//                    return "Math Error , [AC] : Cancel";
//                }
//            }
//        }

        Stack<Double> resultStack = new Stack<>();
        while (!output.isEmpty()) {
            String token = output.poll();
            if (!isOperator(token)) {
                resultStack.push(Double.parseDouble(token));
            } else {
                double a = resultStack.pop();
                double b;
                switch (token) {
                    case "+":
                        b = resultStack.pop();
                        resultStack.push(a + b);
                        break;
                    case "-":
                        b = resultStack.pop();
                        resultStack.push(b - a);
                        break;
                    case "×":
                        b = resultStack.pop();
                        resultStack.push(a * b);
                        break;
                    case "/":
                        b = resultStack.pop();
                        resultStack.push(b / a);
                        break;
                    case "^":
                        b = resultStack.pop();
                        resultStack.push(Math.pow(b, a));
                        break;
                    case "sin":
                        resultStack.push(Math.sin(a));
                        break;
                    case "cos":
                        resultStack.push(Math.cos(a));
                        break;
                    case "tan":
                        resultStack.push(Math.tan(a));
                        break;
                    case "sqrt":
                        resultStack.push(Math.sqrt(a));
                        break;
                    case "ln":
                        resultStack.push(Math.log(a));
                        break;
                    case "log":
                        resultStack.push(Math.log10(a));
                        break;
                }
            }


            for (int i = 0; i < stack.size(); i++) {
                String tmp = stack.get(i);
                if (tmp.equals("/")) {
                    if (i + 1 < stack.size()) {
                        String nextToken = stack.get(i + 1);
                        if (nextToken.equals("0") || nextToken.equals("sin 0") || nextToken.equals("cos π/2") || nextToken.equals("tan π/2") || nextToken.equals("ln 1") || nextToken.equals("log₁₀ 1")) {
                            return "Math Error , [AC] : Cancel";
                        }
                    }
                }
            }

        }

        //if the number is a form of x.0 then convert it to x else keep it as it is
        double result = resultStack.pop();
        if (result % 1 == 0) {
            return String.valueOf(result).substring(0, String.valueOf(result).length() - 2);
        } else {
            return String.valueOf(result);
        }
    }

    private static int intValue(Double peek) {
        return (int)(double)peek;
    }

    public static int precedence(String operator) {
        return switch (operator) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            case "sin", "cos", "tan", "sqrt", "log", "ln" -> 3;
            case "^" -> 4;
            default -> 0; // Default precedence for other operators or functions
        };
    }

    public static ArrayList<String> parseExpression(String text) {
        ArrayList<String> elements = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d+\\.?\\d*|[-+×/^()]|\\b(sin|cos|tan|sqrt|ln|log)\\b|\\(");        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            elements.add(matcher.group());
        }

        return elements;
    }

    //function to print the elements of the arraylist
    public static void printElements(ArrayList<String> elements) {
        System.out.println(text);
        for (String element : elements) {
            System.out.println(element);
        }
    }


    //function to check if the character is an operator
    public static boolean isOperator(String c) {
        return Objects.equals(c, "sin") || Objects.equals(c, "cos") || Objects.equals(c, "tan") ||
                Objects.equals(c, "sqrt") || Objects.equals(c, "log") || Objects.equals(c, "ln") ||
                 Objects.equals(c, "+") || Objects.equals(c, "-") || Objects.equals(c, "×") ||
                Objects.equals(c, "/") || Objects.equals(c, "^")
                || Objects.equals(c, "(") || Objects.equals(c, ")");
    }
    /**
     * This method checks if the given mathematical expression is valid.
     * It checks for the following conditions:
     * - If the expression contains "Ans" and Ans is null, it returns false.
     * - If there is an open parenthesis, it checks if the next character is a close parenthesis or an operator, in which case it returns false.
     * - If there is a close parenthesis, it checks if the next character is a number, in which case it returns false. It also checks if there is no corresponding open parenthesis, in which case it returns false.
     * - If there is an operator, it checks if it is at the start or end of the expression, in which case it returns false. It also checks if there is another operator immediately before or after it, in which case it returns false.
     * - If there is a 's', 'c', or 't', it checks if it is part of a valid operator (sin, cos, tan), in which case it returns false.
     * - If there is a 'l', it checks if it is part of a valid operator (log, ln), in which case it returns false.
     * - If all checks pass, it returns true if all open parentheses have corresponding close parentheses, and false otherwise.
     *
     * @param currentText The mathematical expression to check.
     * @return true if the expression is valid, false otherwise.
     */
    public boolean isValid(String currentText) {
        Stack<Character> stack = new Stack<Character>();
        //if text contains Ans and Ans == NULL then return false
        if (currentText.contains("Ans") && Ans == null) {
            return false;
        }
        for (int i = 0; i < currentText.length(); i++) {
            char c = currentText.charAt(i);
            if (c == '(') {
                if (i != currentText.length() - 1) {
                    if (currentText.charAt(i + 1) == ')' || isOperator(currentText.substring(i + 1, Math.min(i + 4, currentText.length())))) {
                        return false;
                    }
                }
                stack.push(c);
            } else if (c == ')') {
                if (i != currentText.length() - 1) {
                    if (currentText.charAt(i + 1) >= '0' && currentText.charAt(i + 1) <= '9') {
                        return false;
                    }
                }
                if (stack.isEmpty()) {
                    return false;
                }
                stack.pop();
            } else if (isOperator(String.valueOf(c))) {
                if (i == 0 || i == currentText.length() - 1) {
                    return false;
                }
                if (isOperator(currentText.substring(Math.max(0, i - 1), i + 1)) || isOperator(currentText.substring(i + 1, Math.min(i + 4, currentText.length())))) {
                    return false;
                }
            } else if (c == 's' || c == 'c' || c == 't') {
                if (i + 3 < currentText.length() && isOperator(currentText.substring(i, i + 2))) {
                    return false;
                }
            } else if (c == 'l') {
                if (currentText.charAt(i+1) == 'o') {
                    if (i + 2 < currentText.length() && isOperator(currentText.substring(i, i + 1))) {
                        return false;
                    } else if(currentText.charAt(i+1) == 'o'){
                        if (i + 1 < currentText.length() && isOperator(currentText.substring(i, i))) {
                            return false;
                        }
                    }
                }
            }
        }
            return stack.isEmpty();
    }



    public String toString(double number) {
        return Double.toString(number);
    }

    public int toInt(String number) {
        return Integer.parseInt(number);
    }
}
