import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.security.SecureRandom;

public class PasswordGeneratorApp extends JFrame {

    private JTextField lengthField;
    private JCheckBox upperCaseCheck, lowerCaseCheck, numbersCheck, symbolsCheck;
    private JTextArea passwordArea;
    private JButton generateButton, copyButton;

    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_=+[]{};:,.<>?";

    public PasswordGeneratorApp() {
        setTitle("Secure Password Generator");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 248, 255));

        JLabel title = new JLabel("Secure Password Generator", SwingConstants.CENTER);
        title.setFont(new Font("Verdana", Font.BOLD, 20));
        title.setBounds(40, 20, 350, 40);
        add(title);

        JLabel lengthLabel = new JLabel("Password Length:");
        lengthLabel.setBounds(70, 80, 150, 25);
        add(lengthLabel);

        lengthField = new JTextField("12");
        lengthField.setBounds(220, 80, 50, 25);
        add(lengthField);

        upperCaseCheck = new JCheckBox("Uppercase (A-Z)", true);
        upperCaseCheck.setBounds(70, 120, 150, 25);
        add(upperCaseCheck);

        lowerCaseCheck = new JCheckBox("Lowercase (a-z)", true);
        lowerCaseCheck.setBounds(220, 120, 150, 25);
        add(lowerCaseCheck);

        numbersCheck = new JCheckBox("Numbers (0-9)", true);
        numbersCheck.setBounds(70, 150, 150, 25);
        add(numbersCheck);

        symbolsCheck = new JCheckBox("Symbols (!@#$...)", true);
        symbolsCheck.setBounds(220, 150, 150, 25);
        add(symbolsCheck);

        passwordArea = new JTextArea();
        passwordArea.setEditable(false);
        passwordArea.setFont(new Font("Consolas", Font.BOLD, 18));
        passwordArea.setLineWrap(true);
        passwordArea.setBounds(70, 190, 300, 50);
        add(passwordArea);

        generateButton = new JButton("Generate Password");
        generateButton.setBounds(70, 260, 160, 30);
        add(generateButton);

        copyButton = new JButton("Copy");
        copyButton.setBounds(250, 260, 120, 30);
        add(copyButton);

        generateButton.addActionListener(e -> {
            try {
                int length = Integer.parseInt(lengthField.getText());
                passwordArea.setText(generatePassword(length));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Enter a valid password length!");
            }
        });

        copyButton.addActionListener(e -> {
            String password = passwordArea.getText();
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No password to copy!");
                return;
            }
            StringSelection selection = new StringSelection(password);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, null);
            JOptionPane.showMessageDialog(this, "Password copied to clipboard!");
        });

        setVisible(true);
    }
 
    private String generatePassword(int length) {
        String chars = "";
        if (upperCaseCheck.isSelected()) chars += UPPERCASE;
        if (lowerCaseCheck.isSelected()) chars += LOWERCASE;
        if (numbersCheck.isSelected()) chars += NUMBERS;
        if (symbolsCheck.isSelected()) chars += SYMBOLS;

        if (chars.isEmpty()) {
            return "Select at least one option!";
        }

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            password.append(chars.charAt(index));
        }
        return password.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PasswordGeneratorApp::new);
    }
}
