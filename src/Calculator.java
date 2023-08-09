
//Note: JFrame is a class made available by the swing package. So we need to import the swing package as well.
import javax.swing.*;
//The Font class is made available by Java’s Abstract Windows ToolKit (AWT) package
import java.awt.*;
//ActionListener interface is made available by Java’s AWT event package
import java.awt.event.*;

//create a calculator class that extends the JFrame
public class Calculator extends JFrame {

    // create the components included in the calculator
    // These components include the buttons that will send input to the calculator
    // and the text field to read the calculator output.
    // We will use JButton and JTextField, made available by the swing package
    // imported earlier
    JButton btnAdButton, btnSubtrButton, btnDiviButton, btnMulButton, btnClearButton, btnDeleteButton, btnEqualsButton,
            btnDotButton;
    JButton numButton[];
    JTextField output;
    // create String variables that will hold the previous input, the current input,
    // and the operator.
    String previous, current, operator;

    public void processOutputNumber() {
        if (current.length() > 0) {
            String integerPart = current.split("\\.")[0];
            String decimalPart = current.split("\\.")[1];
            if (decimalPart.equals("0")) {
                current = integerPart;
            }
        }
    }

    // creating the structure
    public void delete() {
        if (current.length() > 0) {
            current = current.substring(0, current.length() - 1);
        }
    }

    public void clear() {
        current = "";
        previous = "";
        operator = null;

    }

    public void updateOutput() {
        // This method updates the output display with the current operand’s value
        output.setText(current);

    }

    public void appendToOutput(String num) {
        // Prevents adding more than one dot on the output
        if (num.equals(".") && current.contains(".")) {
            return;
        }
        // This method adds any number that the user clicks to the output variable
        current += num;
    }

    public void selectOperator(String newOperator) {
        // if no number is entered yet but only operator
        if (current.isEmpty()) {
            operator = newOperator;
            return;
        }

        if (!previous.isEmpty()) {
            calculate();
        }

        operator = newOperator;
        previous = current;
        current = "";

    }

    public void calculate() {
        if (previous.length() < 1 || current.length() < 1) {
            return;
        }

        double result = 0.0;
        double num1 = Double.parseDouble(previous);
        double num2 = Double.parseDouble(current);
        switch (operator) {
            case "x":
                result = num1 * num2;
                break;

            case "+":
                result = num1 + num2;
                break;

            case "-":
                result = num1 - num2;
                break;

            case "/":
                result = num1 / num2;
                break;

            default:
                break;
        }

        current = String.valueOf(result);
        operator = null;
        previous = "";

    }

    // Creating event Handlers
    private class NumberBtnHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Note that we need to cast e.getSource() to a JButton to store it in a button
            // variable with a more meaningful name, which can later be used for comparison
            // inside the loop.
            JButton selectedButton = (JButton) e.getSource();
            for (JButton btn : numButton) {
                if (selectedButton == btn) {
                    appendToOutput(btn.getText());
                    updateOutput();
                }
            }

        }
    }

    /*
     * private class OperatorBtnHandler implements ActionListener {
     * 
     * @Override
     * public void actionPerformed(ActionEvent e) {
     * JButton selectedButton = (JButton) e.getSource();
     * if (selectedButton == btnMulButton) {
     * selectOperator(btnMulButton.getText());
     * } else if (selectedButton == btnAdButton) {
     * selectOperator(btnAdButton.getText());
     * } else if (selectedButton == btnSubtrButton) {
     * selectOperator(btnSubtrButton.getText());
     * } else if (selectedButton == btnDiviButton) {
     * selectOperator(btnDiviButton.getText());
     * }
     * updateOutput();
     * 
     * }
     * }
     */

    private class OperatorBtnHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton selectedButton = (JButton) e.getSource();
            if (selectedButton == btnMulButton) {
                selectOperator(btnMulButton.getText());
            } else if (selectedButton == btnAdButton) {
                selectOperator(btnAdButton.getText());
            } else if (selectedButton == btnSubtrButton) {
                selectOperator(btnSubtrButton.getText());
            } else if (selectedButton == btnDiviButton) {
                selectOperator(btnDiviButton.getText());
            }

            // Update the output field
            updateOutput();
        }
    }

    private class OtherBtnHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton selectedButton = (JButton) e.getSource();
            if (selectedButton == btnEqualsButton) {
                calculate();
            } else if (selectedButton == btnClearButton) {
                clear();
            } else if (selectedButton == btnDeleteButton) {
                delete();
            }

            // Update the output field
            updateOutput();
        }
    }

    /*
     * private class OtherBtnHandler implements ActionListener {
     * 
     * @Override
     * public void actionPerformed(ActionEvent e) {
     * JButton selectedButton = (JButton) e.getSource();
     * if (selectedButton == btnEqualsButton) {
     * calculate();
     * } else if (selectedButton == btnClearButton) {
     * clear();
     * } else if (selectedButton == btnDeleteButton) {
     * delete();
     * }
     * 
     * updateOutput();
     * 
     * }
     * }
     */

    // create the constructor to initialize the components and variables
    public Calculator() {
        // calculator’s title by passing the title into the superclass (JFrame)
        // constructor
        super("Box Calculator");
        // create a panel named mainPanel that will house all other components in the
        // calculator

        // Initializing the calculator operands
        current = "";
        previous = "";

        JPanel mainPanel = new JPanel();

        // create sub panel inside mail panel
        JPanel row1 = new JPanel();
        JPanel row2 = new JPanel();
        JPanel row3 = new JPanel();
        JPanel row4 = new JPanel();
        JPanel row5 = new JPanel();

        // initialize components

        output = new JTextField(16);
        btnAdButton = new JButton("+");
        btnSubtrButton = new JButton("-");
        btnMulButton = new JButton("x");
        btnDiviButton = new JButton("/");
        btnClearButton = new JButton("C");
        btnDotButton = new JButton(".");
        btnEqualsButton = new JButton("=");
        btnDeleteButton = new JButton("D");

        // Adding ActionListeners to the buttons
        // Instantiate action listener

        NumberBtnHandler numberBtnHandler = new NumberBtnHandler();
        OperatorBtnHandler operatorBtnHandler = new OperatorBtnHandler();
        OtherBtnHandler otherBtnHandler = new OtherBtnHandler();

        // Initial style and action listeners

        numButton = new JButton[11];
        numButton[10] = btnDotButton;
        for (int count = 0; count < numButton.length - 1; count++) {
            numButton[count] = new JButton(String.valueOf(count));
            numButton[count].setFont(new Font("Monospaced", Font.BOLD, 22));
        }

        // Style your Button
        btnAdButton.setFont(new Font("Monospaced", Font.BOLD, 22));
        btnSubtrButton.setFont(new Font("Monospaced", Font.BOLD, 22));
        btnMulButton.setFont(new Font("Monospaced", Font.BOLD, 22));
        btnDiviButton.setFont(new Font("Monospaced", Font.BOLD, 22));
        btnDotButton.setFont(new Font("Monospaced", Font.BOLD, 22));
        btnEqualsButton.setFont(new Font("Monospaced", Font.BOLD, 22));
        btnDeleteButton.setFont(new Font("Monospaced", Font.BOLD, 22));
        btnClearButton.setFont(new Font("Monospaced", Font.BOLD, 22));

        // Style the Output Display
        output.setMaximumSize(new Dimension(200, 40));
        output.setFont(new Font("Monospaced", Font.BOLD, 22));
        output.setDisabledTextColor(new Color(0, 0, 0));
        output.setMargin(new Insets(0, 5, 0, 0));
        output.setText("0");

        // add these action listeners to their respective buttons

        /*
         * for (int count = 0; count < numButton.length - 1; count++) {
         * numButton[count] = new JButton(String.valueOf(count));
         * numButton[count].setFont(new Font("Monospaced", Font.BOLD, 22));
         * numButton[count].addActionListener(numberBtnHandler);
         * }
         */
        for (int count = 0; count < numButton.length - 1; count++) {
            numButton[count] = new JButton(String.valueOf(count));
            numButton[count].setFont(new Font("Monospaced", Font.BOLD, 22));
        }
        numButton[0].addActionListener(numberBtnHandler);
        for (int count = 1; count < numButton.length - 1; count++) {
            numButton[count].addActionListener(numberBtnHandler);
        }

        // Add action listeners to other buttons
        btnDotButton.addActionListener(numberBtnHandler);
        btnDeleteButton.addActionListener(otherBtnHandler);
        btnClearButton.addActionListener(otherBtnHandler);
        btnEqualsButton.addActionListener(otherBtnHandler);

        // Add action listeners to operation buttons

        btnAdButton.addActionListener(operatorBtnHandler);
        btnSubtrButton.addActionListener(operatorBtnHandler);
        btnDiviButton.addActionListener(operatorBtnHandler);
        btnMulButton.addActionListener(operatorBtnHandler);

        // Notice that we created a horizontal glue and added it as the first component
        // in row1. This horizontal glue pushes the C and D buttons as far as possible
        // to the horizontal edge of the pane.
        row1.add(Box.createHorizontalGlue());
        row1.add(btnClearButton);
        row1.add(btnDeleteButton);
        row2.add(numButton[7]);
        row2.add(numButton[8]);
        row2.add(numButton[9]);
        row2.add(btnMulButton);
        row3.add(numButton[4]);
        row3.add(numButton[5]);
        row3.add(numButton[6]);
        row3.add(btnAdButton);
        row4.add(numButton[1]);
        row4.add(numButton[2]);
        row4.add(numButton[3]);
        row4.add(btnSubtrButton);
        row5.add(btnDotButton);
        row5.add(numButton[0]);
        row5.add(btnEqualsButton);
        row5.add(btnDiviButton);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(output);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(row1);
        mainPanel.add(row2);
        mainPanel.add(row3);
        mainPanel.add(row4);
        mainPanel.add(row5);

        // Set the layout of each row in the pane
        row1.setLayout(new BoxLayout(row1, BoxLayout.LINE_AXIS));
        row2.setLayout(new BoxLayout(row2, BoxLayout.LINE_AXIS));
        row3.setLayout(new BoxLayout(row3, BoxLayout.LINE_AXIS));
        row4.setLayout(new BoxLayout(row4, BoxLayout.LINE_AXIS));
        row5.setLayout(new BoxLayout(row5, BoxLayout.LINE_AXIS));

        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(205, 280);

    }

    // create the main method inside the calculator class that will invoke the class
    // once the program is executed.
    public static void main(String[] args) {
        new Calculator();

    }

}