import java.util.Stack;

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

    // Binary operations
    public double add(double operand1, double operand2) {
        return operand1 + operand2;
    }

    public double subtract(double operand1, double operand2) {
        return operand1 - operand2;
    }

    public double multiply(double operand1, double operand2) {
        return operand1 * operand2;
    }

    public double divide(double operand1, double operand2) {
        return operand1 / operand2;
    }

    // Unary operations
    public double sin(double operand) {
        return Math.sin(operand);
    }

    public double cos(double operand) {
        return Math.cos(operand);
    }

    public double tan(double operand) {
        return Math.tan(operand);
    }

    public double sqrt(double operand) {
        return Math.sqrt(operand);
    }

    public String calculate() {
        if(!isValid(text)){
            return "Math Error , [AC] : Cancel";
        }

        simplify(text);
        Ans = text;
        return text; // Placeholder
    }

    //function that takes everything between 2 paranthesis and simplifies it to a number, even in sin/cos/tan/sqrt according to the operation
    //the approach is to find the last open parenthesis and the first close parenthesis and then simplify the expression between them
    //then continue to the one before that
    //a stack will keep track of the open parenthesis indexes so we can find the last one and continue to the next closing parenthesis
    // everytime updating the text to the simplified version such as "sin(30)" to "0.5", "123+(30/5)" to "123+6" ...
    public String simplify(String currentText) {
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < currentText.length(); i++) {
            char c = currentText.charAt(i);
            if (c == '(') {
                stack.push(i);
            } else if (c == ')') {
                int start = stack.pop();
                String simplified = simplifyExpression(currentText.substring(start + 1, i));
                currentText = currentText.substring(0, start) + simplified + currentText.substring(i + 1);
                i = start + simplified.length();
            }
        }
        return currentText;

    }

    //function to simplify the expression between 2 parenthesis
    public String simplifyExpression(String currentText) {
        if (currentText.contains("sin(")) {
            return toString(sin(toInt(simplify(currentText.substring(4, currentText.length() - 1)))));
        } else if (currentText.contains("cos(")) {
            return toString(cos(toInt(simplify(currentText.substring(4, currentText.length() - 1)))));
        } else if (currentText.contains("tan(")) {
            return toString(tan(toInt(simplify(currentText.substring(4, currentText.length() - 1)))));
        } else if (currentText.contains("sqrt(")) {
            return toString(sqrt(toInt(simplify(currentText.substring(5, currentText.length() - 1)))));
        } else {
            return toString(getAns(currentText));
        }
    }

    //function to check if the character is an operator

    public boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == '.';
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

        }
        return true;
    }

    //function to convert int to string

    public String toString(double number) {
        return Double.toString(number);
    }

    public int toInt(String number) {
        return Integer.parseInt(number);
    }
}
