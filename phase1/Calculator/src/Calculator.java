import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public final class Calculator {
	// see if the client has chosen an operator
	private boolean isChooseOperator = false;
	
	// declare and initialize the UI components
	private JFrame frame = new JFrame("Calculator");
	private JTextField leftOperand = new JTextField("12");
	private JTextField operator = new JTextField("");
	private JTextField rightOperand = new JTextField("2");
	private JTextField equalOperator = new JTextField("=");
	private JTextField result = new JTextField();
	private JButton addBtn = new JButton("+");
	private JButton subBtn = new JButton("-");
	private JButton mulBtn = new JButton("*");
	private JButton divBtn = new JButton("/");
	private JButton okBtn = new JButton("OK");
	private JComponent[] components = {leftOperand, operator, rightOperand, equalOperator, result, addBtn, subBtn, mulBtn, divBtn, okBtn};
	
    private Calculator() {
    	JPanel pan = new JPanel();
    	// set the layout with 2 rows and 5 cols,
        // 5 pixels hgap and vgap
    	pan.setLayout(new GridLayout(2, 5, 5, 5));
    	
    	// set to uneditable
    	leftOperand.setEditable(false);
    	operator.setEditable(false);
    	rightOperand.setEditable(false);
    	equalOperator.setEditable(false);
    	result.setEditable(false);
    	
    	// set the background color and the size of buttons
    	leftOperand.setBackground(Color.WHITE);
    	rightOperand.setBackground(Color.WHITE);
    	leftOperand.setPreferredSize(new Dimension(80, 80));
    	
    	// set the horizontal alignment of the text to center
    	leftOperand.setHorizontalAlignment(JTextField.CENTER);
    	operator.setHorizontalAlignment(JTextField.CENTER);
    	rightOperand.setHorizontalAlignment(JTextField.CENTER);
    	equalOperator.setHorizontalAlignment(JTextField.CENTER); 	
    	result.setHorizontalAlignment(JTextField.CENTER);
    	
    	// add the listeners
    	addBtn.addActionListener(new ButtonActionHandler("+"));
    	subBtn.addActionListener(new ButtonActionHandler("-"));
    	mulBtn.addActionListener(new ButtonActionHandler("*"));
    	divBtn.addActionListener(new ButtonActionHandler("/"));
    	okBtn.addActionListener(new ButtonActionHandler("="));
    	
    	// add the components to the panel
    	for (int i = 0; i < components.length; ++i ) {
    		pan.add(components[i]);
    	}

        // set the frame unresizeable
        frame.setResizable(false); 
        frame.getContentPane().add(pan, BorderLayout.CENTER);
        
        frame.pack();
        frame.setVisible(true);
        
        // if close the window, the program exit
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
    
    private class ButtonActionHandler implements ActionListener {
    	private String theOperator = "";
    	public ButtonActionHandler(String operator) {
    		theOperator = operator;
    	}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (!theOperator.equals("=")) {
				operator.setText(theOperator);
				isChooseOperator = true;
			}
			else if (isChooseOperator) {
				result.setText(calculateResult(operator.getText()));
            }
		}
		
		public String calculateResult(String op) {
			int myResult = 0;
			int left = 12;
			int right = 2;
			switch (op) {
				case "+":
					myResult = left + right; 
                    break;
				case "-":
					myResult = left - right; 
                    break;
				case "*":
					myResult = left * right; 
                    break;
				case "/":
					myResult = left / right; 
                    break;
				default:
					break;
			}
			return "" + myResult;
		}
    }
    
    public static void main(String[] args) {
    	new Calculator();
    }
}