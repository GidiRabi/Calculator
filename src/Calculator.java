import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Calculator {

    private static Calculator instance;
    private CalculatorGUI calculatorGUI; // CalculatorGUI instance
    public static String text = "";
    public static String Ans = "";
    public static int count = 1;

    /**
     * Private constructor to prevent instantiation from outside.
     * Initializes necessary variables.
     * @param txt The initial text for the calculator.
     */    private Calculator(String txt) {
        // Initialize any necessary variables here
        System.out.println("Calculator started!");
        text = txt;
    }

    /**
     * Public method to get the singleton instance of the calculator.
     * If the instance is null, a new instance is created.
     * @param s The expression to be set for the calculator.
     * @return The singleton instance of the calculator.
     */    public static Calculator getInstance(String s) {
        if (instance == null) {
            instance = new Calculator(text);
        }
        instance.setExpression(s);
        return instance;
    }

    /**
     * Getter for the text of the calculator.
     * @return The current text of the calculator.
     */
    private String getText() {
        return text;
    }

    /**
     * Getter for the last answer of the calculator.
     * @return The last answer of the calculator.
     */
    public String lastAns(){
        return Ans;
    }

    /**
     * This method calculates the result of the current expression.
     * It first replaces any "Ans" in the text with the last answer.
     * Then it checks if the expression is valid.
     * If it is, it parses the expression into elements, evaluates it, and sets the answer.
     * If the result is "Infinity", "NaN", or "-Infinity", it returns a math error.
     * @return The result of the calculation, or a math error message.
     */
    public String calculate() {
        replaceAnsInText();
        String tmp = text;
        if(!isValid(text)){
            return "Math Error , [AC] : Cancel";
        }

        ArrayList<String> elements = parseExpression(text);

        text = evaluate(elements);
        if(text.equals("Infinity") || text.equals("NaN") || text.equals("-Infinity")){
            return "Math Error , [AC] : Cancel";
        }
        if(!text.equals("")) {
            Ans = text;
            System.out.println("Equation No " + count++ +": " + tmp + " = " + Ans);

        }
        return text;
    }

    /**
     * This method replaces any "Ans" in the text with the last answer.
     */
    public void replaceAnsInText() {
        if (Ans != null) {
            text = text.replace("Ans", Ans);
        }
    }

    /**
     * This method checks if a given string is an operator.
     * @param s The string to check.
     * @return true if the string is an operator, false otherwise.
     */    public static boolean isOperator(String s) {
        return s.equals("+") || s.equals("-") || s.equals("×") || s.equals("/") || s.equals("^")
                || Objects.equals(s, "(") || Objects.equals(s, ")");
    }


    /**
     * This method checks if a given string is a function.
     * @param s The string to check.
     * @return true if the string is a function, false otherwise.
     */
    public static boolean isFunction(String s) {
        return s.equals("sin") || s.equals("cos") || s.equals("tan") || s.equals("sqrt") || s.equals("ln") || s.equals("log");
    }

    /**
     * This method evaluates a given mathematical expression.
     * It uses the shunting yard algorithm to convert the expression to Reverse Polish Notation (RPN),
     * and then evaluates the RPN expression.
     * @param expression The mathematical expression to evaluate.
     * @return The result of the evaluation.
     */
    public static String evaluate(ArrayList<String> expression) {
        if (expression.isEmpty()) {
            return "";
        }

        Stack<String> stack = new Stack<>();
        LinkedList<String> output = new LinkedList<>();

        for (String s : expression) {
            if (!isOperator(s) && !isFunction(s)) {
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
            } else if (isOperator(s) || isFunction(s)) {
                while (!stack.isEmpty() && (isOperator(stack.peek()) || isFunction(stack.peek())) && (precedence(s) <= precedence(stack.peek()))) {
                    output.add(stack.pop());
                }
                stack.push(s);
            }
        }

        while (!stack.isEmpty()) {
            output.add(stack.pop());
        }
        output.removeIf(s -> s.equals("(") || s.equals(")"));

        return calculateRPN(output);
    }

    /**
     * This method calculates the result of a given Reverse Polish Notation (RPN) expression.
     * @param rpn The RPN expression to calculate.
     * @return The result of the calculation.
     */
    public static String calculateRPN(LinkedList<String> rpn) {
        Stack<Double> stack = new Stack<>();

        System.out.println(rpn);

        while (!rpn.isEmpty()) {
            String token = rpn.poll();
            if (!isOperator(token) && !isFunction(token)) {
                stack.push(Double.parseDouble(token));
            } else {
                double a = stack.pop();
                double b;
                switch (token) {
                    case "+":
                        b = stack.pop();
                        stack.push(a + b);
                        break;
                    case "-":
                        b = stack.pop();
                        stack.push(b - a);
                        break;
                    case "×":
                        b = stack.pop();
                        stack.push(a * b);
                        break;
                    case "/":
                        b = stack.pop();
                        if (a == 0) {
                            return "Math Error , [AC] : Cancel";
                        }
                        stack.push(b / a);
                        break;
                    case "^":
                        b = stack.pop();
                        stack.push(Math.pow(b, a));
                        break;
                    case "sin":
                        stack.push(Math.sin(a));
                        break;
                    case "cos":
                        stack.push(Math.cos(a));
                        break;
                    case "tan":
                        stack.push(Math.tan(a));
                        break;
                    case "sqrt":
                        stack.push(Math.sqrt(a));
                        break;
                    case "ln":
                        stack.push(Math.log(a));
                        break;
                    case "log":
                        stack.push(Math.log10(a));
                        break;
                }
            }
        }

        double result = stack.pop();
        if (result % 1 == 0) {
            return String.valueOf(result).substring(0, String.valueOf(result).length() - 2);
        } else {
            return String.valueOf(result);
        }
    }

    /**
     * This method converts a Double to an int.
     * @param peek The Double to convert.
     * @return The int value of the Double.
     */
    private static int intValue(Double peek) {
        return (int)(double)peek;
    }

    /**
     * This method returns the precedence of a given operator.
     * @param operator The operator to check.
     * @return The precedence of the operator.
     */
    public static int precedence(String operator) {
        return switch (operator) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            case "sin", "cos", "tan", "sqrt", "log", "ln" -> 3;
            case "^" -> 4;
            default -> 0; // Default precedence for other operators or functions
        };
    }

    /**
     * This method parses a given mathematical expression into its constituent elements.
     * @param text The mathematical expression to parse.
     * @return An ArrayList of the elements of the expression.
     */
    public static ArrayList<String> parseExpression(String text) {
        ArrayList<String> elements = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d+\\.?\\d*|[-+×/^()]|\\b(sin|cos|tan|sqrt|ln|log)\\b|\\(");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            elements.add(matcher.group());
        }

        return elements;
    }

    /**
     * This method prints the elements of a given ArrayList.
     * @param elements The ArrayList to print.
     */    public static void printElements(ArrayList<String> elements) {
        System.out.println(text);
        for (String element : elements) {
            System.out.println(element);
        }
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
                if (i + 3 < currentText.length() && isFunction(currentText.substring(i, i + 2))) {
                    return false;
                }
            } else if (c == 'l') {
                if (currentText.charAt(i+1) == 'o') {
                    if (i + 2 < currentText.length() && isFunction(currentText.substring(i, i + 1))) {
                        return false;
                    } else if(currentText.charAt(i+1) == 'o'){
                        if (i + 1 < currentText.length() && isFunction(currentText.substring(i, i))) {
                            return false;
                        }
                    }
                }
            }
        }
            return stack.isEmpty();
    }


    /**
     * This method converts a double to a String.
     * @param number The double to convert.
     * @return The String representation of the double.
     */
    public String toString(double number) {
        return Double.toString(number);
    }
    /**
     * This method converts a String to an int.
     * @param number The String to convert.
     * @return The int value of the String.
     */
    public int toInt(String number) {
        return Integer.parseInt(number);
    }

    /**
     * This method sets the expression of the calculator.
     * @param s The expression to set.
     */
    public void setExpression(String s) {
        text = s;
    }
}
