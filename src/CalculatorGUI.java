import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorGUI extends JFrame {
    private JTextField displayField;

    public CalculatorGUI() {
        super("Scientific Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

//        // Display Field
//        displayField = new JTextField();
//        displayField.setEditable(false);
//        displayField.setHorizontalAlignment(JTextField.RIGHT);
//        add(displayField, BorderLayout.NORTH);

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
                "(", ")", "^2", "ln", "log",
                "sin(", "cos(", "tan(", "sqrt(", "^",
                "7", "8", "9", "DEL", "AC",
                "4", "5", "6", "*", "/",
                "1", "2", "3", "+", "-",
                "0", ".", "*10^x", "Ans", "="
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
        setSize(300, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Action Listener for buttons
    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {


            if (((JButton) e.getSource()).getText().equals("=")) {
                String currentText = displayField.getText();
                Calculator calculator = new Calculator(currentText);
                displayField.setText(calculator.calculate());
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
                currentText = currentText.substring(0, currentText.length() - 1);
                displayField.setText(currentText);
                return;
            }


            String buttonLabel = ((JButton) e.getSource()).getText();
            String currentText = displayField.getText();

            // Append clicked button label to the display field
            displayField.setText(currentText + buttonLabel);

            currentText = displayField.getText();

        }


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculatorGUI());
    }
}
