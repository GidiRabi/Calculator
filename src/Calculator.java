import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Calculator {

    private static Calculator instance;
    private CalculatorGUI calculatorGUI; // CalculatorGUI instance
    public static String text = "";
    public static String Ans = "0";

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


    //gets the String from displayed text and computes the final answer
    public int getAns(String currentText) {
        char opertaions[]; //array of operations from currentText

        return 0; // Placeholder
    }

    public String lastAns(){
        return Ans;
    }

    public String calculate() {
        if(!isValid(text)){
            return "Math Error , [AC] : Cancel";
        }
        ArrayList<String> elements = parseExpression(text);
        ArrayList<String> simplified = simplifyParanthesis(elements);
        printElements(simplified);
        return text;
    }

    //function that takes everything between 2 paranthesis and simplifies it to a number, if its sin( or cos( or tan( or sqrt(
    //then it will only take the number inside the paranthesis and apply the function to it and keep the sin( or cos( or tan( or sqrt(
    //the expression is valid so we dont need to check for errors
    //the function will do for example from (,12,+,4,*,6,) to (,12,+,24,) to (,36,)

    public static ArrayList<String> simplifyParanthesis(ArrayList<String> elements) {
        ArrayList<String> simplified = new ArrayList<>();
        int i = 0;
        while (i < elements.size()) {
            if (elements.get(i).equals("(")) {
                int j = i + 1;
                int count = 1;
                while (count != 0 && j < elements.size()) {
                    if (elements.get(j).equals("(")) {
                        count++;
                    } else if (elements.get(j).equals(")")) {
                        count--;
                    }
                    j++;
                }
                if (count != 0) {
                    throw new IllegalArgumentException("Unbalanced parentheses in the expression.");
                }
                ArrayList<String> subExpression = new ArrayList<>(elements.subList(i + 1, j - 1));
                ArrayList<String> simplifiedSubExpression = simplifyParanthesis(subExpression);
                double result = evaluate(simplifiedSubExpression);
                simplified.add(Double.toString(result));
                i = j;
            } else if (elements.get(i).equals("sin(") || elements.get(i).equals("cos(") || elements.get(i).equals("tan(") || elements.get(i).equals("sqrt(")) {
                simplified.add(elements.get(i));
                i++;
                simplified.add(elements.get(i));
                i++;
            } else {
                simplified.add(elements.get(i));
                i++;
            }
        }
        return simplified;
    }

    public static double evaluate(ArrayList<String> expression) {
        if (expression.isEmpty()) {
            throw new IllegalArgumentException("Expression cannot be empty.");
        }

        Stack<Double> stack = new Stack<>();

        for (String token : expression) {
            switch (token) {
                case "+":
                    if (stack.size() < 2) {
                        throw new IllegalArgumentException("Invalid expression.");
                    }
                    stack.push(stack.pop() + stack.pop());
                    break;
                case "-":
                    if (stack.size() < 2) {
                        throw new IllegalArgumentException("Invalid expression.");
                    }
                    double subtractor = stack.pop();
                    stack.push(stack.pop() - subtractor);
                    break;
                case "×":
                    if (stack.size() < 2) {
                        throw new IllegalArgumentException("Invalid expression.");
                    }
                    stack.push(stack.pop() * stack.pop());
                    break;
                case "/":
                    if (stack.size() < 2) {
                        throw new IllegalArgumentException("Invalid expression.");
                    }
                    double divisor = stack.pop();
                    if (divisor == 0) {
                        throw new ArithmeticException("Cannot divide by zero.");
                    }
                    stack.push(stack.pop() / divisor);
                    break;
                case "sin(":
                    if (stack.isEmpty()) {
                        throw new IllegalArgumentException("Invalid expression.");
                    }
                    stack.push(Math.sin(stack.pop()));
                    break;
                case "cos(":
                    if (stack.isEmpty()) {
                        throw new IllegalArgumentException("Invalid expression.");
                    }
                    stack.push(Math.cos(stack.pop()));
                    break;
                case "tan(":
                    if (stack.isEmpty()) {
                        throw new IllegalArgumentException("Invalid expression.");
                    }
                    stack.push(Math.tan(stack.pop()));
                    break;
                case "sqrt(":
                    if (stack.isEmpty()) {
                        throw new IllegalArgumentException("Invalid expression.");
                    }
                    stack.push(Math.sqrt(stack.pop()));
                    break;
                case "ln(":
                    if (stack.isEmpty()) {
                        throw new IllegalArgumentException("Invalid expression.");
                    }
                    //push ln to the stack
                    stack.push(Math.log(stack.pop()));
                    break;
                case "log₁₀(":
                    if (stack.isEmpty()) {
                        throw new IllegalArgumentException("Invalid expression.");
                    }
                    //push log₁₀ to the stack
                    stack.push(Math.log10(stack.pop()));
                    break;
                default:
                    stack.push(Double.parseDouble(token));
                    break;
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid expression.");
        }

        return stack.pop();
    }

//    public static ArrayList<String> simplifyParanthesis(ArrayList<String> elements) {
//        ArrayList<String> simplified = new ArrayList<>();
//        int i = 0;
//        while (i < elements.size()) {
//            if (elements.get(i).equals("(")) {
//                int j = i + 1;
//                int count = 1;
//                while (count != 0) {
//                    if (elements.get(j).equals("(")) {
//                        count++;
//                    } else if (elements.get(j).equals(")")) {
//                        count--;
//                    }
//                    j++;
//                }
//                ArrayList<String> subExpression = new ArrayList<>(elements.subList(i + 1, j - 1));
//                ArrayList<String> simplifiedSubExpression = simplifyParanthesis(subExpression);
//                double result = evaluate(simplifiedSubExpression);
//                simplified.add(Double.toString(result));
//                i = j;
//            } else if (elements.get(i).equals("sin(") || elements.get(i).equals("cos(") || elements.get(i).equals("tan(") || elements.get(i).equals("sqrt(")) {
//                simplified.add(elements.get(i));
//                i++;
//                simplified.add(elements.get(i));
//                i++;
//            } else {
//                simplified.add(elements.get(i));
//                i++;
//            }
//        }
//        return simplified;
//    }
//
//
//    public static double evaluate(ArrayList<String> expression) {
//    Stack<Double> stack = new Stack<>();
//
//    for (String token : expression) {
//        switch (token) {
//            case "+":
//                stack.push(stack.pop() + stack.pop());
//                break;
//            case "-":
//                double subtractor = stack.pop();
//                stack.push(stack.pop() - subtractor);
//                break;
//            case "*":
//                stack.push(stack.pop() * stack.pop());
//                break;
//            case "/":
//                double divisor = stack.pop();
//                stack.push(stack.pop() / divisor);
//                break;
//            case "sin(":
//                stack.push(Math.sin(stack.pop()));
//                break;
//            case "cos(":
//                stack.push(Math.cos(stack.pop()));
//                break;
//            case "tan(":
//                stack.push(Math.tan(stack.pop()));
//                break;
//            case "sqrt(":
//                stack.push(Math.sqrt(stack.pop()));
//                break;
//            default:
//                stack.push(Double.parseDouble(token));
//                break;
//        }
//    }
//
//    return stack.pop();
//}

    public static ArrayList<String> parseExpression(String text) {
        ArrayList<String> elements = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d+\\.?\\d*|[-+×/^()]|\\b(sin|cos|tan|sqrt)\\(");
        Matcher matcher = pattern.matcher(text);

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

    public boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '\u00D7' || c == '/' || c == '^' || c == '.';
    }
    //function isValid to check if the expression in the calculation is valid
    //cant be more than 1 operator in a row, a number cant have 2 dots, every open parenthesis should have a close parenthesis
    public boolean isValid(String currentText){
        Stack<Character> stack = new Stack<Character>();

        for(int i = 0; i< currentText.length() ; i++){
            char c = currentText.charAt(i);
            if(c == '('){
                if(i != currentText.length()-1){
                    if( currentText.charAt(i+1) == ')' || isOperator(currentText.charAt(i+1)) ){
                        return false;
                    }
                }
                stack.push(c);
            }
            else if(c == ')'){
                if(i != currentText.length()-1){
                    if( currentText.charAt(i+1) >= '0' && currentText.charAt(i+1) <= '9' ){
                        return false;
                    }
                }
                if(stack.isEmpty()){
                    return false;
                }
                stack.pop();
            }
            else if(isOperator(c)){
                if(i == 0 || i == currentText.length()-1){
                    return false;
                }
                if(isOperator(currentText.charAt(i-1)) || isOperator(currentText.charAt(i+1))){
                    return false;
                }
            }
            else if(c == 's' || c == 'c' || c == 't'){
                if(i + 3 < currentText.length() && isOperator(currentText.charAt(i+3))){
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    //function to convert int to string

    public String toString(double number) {
        return Double.toString(number);
    }

    public int toInt(String number) {
        return Integer.parseInt(number);
    }
}
