import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;


public class CalculatorGUI extends JFrame {
    private JTextField displayField;

    private boolean lastEquals = false;
    public CalculatorGUI() {
        super("Scientific Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Display Field
        displayField = new JTextField();
        displayField.setEditable(false);
        displayField.setHorizontalAlignment(JTextField.LEFT);
        displayField.setFont(new Font("Arial", Font.BOLD, 24)); // Setting font size
        displayField.setPreferredSize(new Dimension(300, 75)); // Setting preferred size
        add(displayField, BorderLayout.NORTH);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(6, 6, 8, 8));

        // Buttons
        String[] buttonLabels = {
                "(", ")", "x²", "ln(", "log₁₀(",
                "sin(", "cos(", "tan(", "sqrt(", "^",
                "7", "8", "9", "DEL", "AC",
                "4", "5", "6", "×", "/",
                "1", "2", "3", "+", "-",
                "0", ".", "×10ˣ", "Ans", "="
        };


        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new ButtonClickListener());

            // Customize button font
            Font buttonFont = new Font("Times New Roman", Font.BOLD, 16); // Example font size
            button.setFont(buttonFont);

            // Customize button size
            Dimension buttonSize = new Dimension(50, 50); // Example button size
            button.setPreferredSize(buttonSize);


            buttonsPanel.add(button);


        }


        add(buttonsPanel, BorderLayout.CENTER);

        // Set frame properties
        setSize(315, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    String getDisplayFieldText() {
        return displayField.getText();
    }

    // Action Listener for buttons
    private class ButtonClickListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            if(displayField.getText().equals("Math Error , [AC] : Cancel")){
                if(((JButton) e.getSource()).getText().equals("AC")){
                    displayField.setText("");
                }
                return;
            }
            if(lastEquals){
                displayField.setText("");
                lastEquals = false;
            }

            if (((JButton) e.getSource()).getText().equals("=")) {
                String currentText = displayField.getText();
                Calculator calculator = Calculator.getInstance(currentText);
//                Calculator calculator = new Calculator(currentText);
                displayField.setText(calculator.calculate());
                lastEquals = true;
                return;
            }


            //if AC clear the screen
            if (((JButton) e.getSource()).getText().equals("AC")) {
                displayField.setText("");
                return;

            }

            //if DEL clear the screen
            if (((JButton) e.getSource()).getText().equals("DEL")) {
                String currentText = displayField.getText();
                if(Objects.equals(currentText, "")){
                    return;
                }

                /* if the last chars are ln( or sin( or cos( or tan( or sqrt( or log10(...
                then delete the expression
                else delete 1 char
                */
                if (currentText.endsWith("sin(") || currentText.endsWith("cos(") || currentText.endsWith("tan(")
                         || currentText.endsWith("log(")) {
                    currentText = currentText.substring(0, currentText.length() - 4);
                } else if (currentText.endsWith("sqrt(")){
                    currentText = currentText.substring(0, currentText.length() - 5);
                } else if (currentText.endsWith("ln(") || currentText.endsWith("Ans") ) {
                    currentText = currentText.substring(0, currentText.length() - 3);
                } else{
                    currentText = currentText.substring(0, currentText.length() - 1);
                }
                displayField.setText(currentText);
                return;
            }

            //if x² is being pressed then add ^2 to the display,
            if (((JButton) e.getSource()).getText().equals("x²")) {
                String currentText = displayField.getText();
                displayField.setText(currentText + "^(2)");
                return;
            }
            //if ×10ˣ is pressed then add ×10^ to the display
            if (((JButton) e.getSource()).getText().equals("×10ˣ")) {
                String currentText = displayField.getText();
                displayField.setText(currentText + "×10^(");
                return;
            }
            //if log10 is pressed then add log to the display
            if (((JButton) e.getSource()).getText().equals("log₁₀(")) {
                String currentText = displayField.getText();
                displayField.setText(currentText + "log(");
                return;
            }

            String buttonLabel = ((JButton) e.getSource()).getText();
            String currentText = displayField.getText();

            // Append clicked button label to the display field
            displayField.setText(currentText + buttonLabel);

        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculatorGUI());
    }
}
