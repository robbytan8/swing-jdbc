package com.robby.swingapp;

import javax.swing.*;

/**
 * @author Robby Tan
 */
public class LoginForm {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnClear;
    private JPanel rootPanel;

    public LoginForm() {
        btnClear.addActionListener(e -> {
            txtUsername.setText("");
            txtPassword.setText("");
        });
        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText();
            String password = String.valueOf(txtPassword.getPassword());
            if (username.equals("robby.tan") && password.equals("12345")) {
                JOptionPane.showMessageDialog(rootPanel, "Authentication success", "Info", JOptionPane.INFORMATION_MESSAGE);
                MainForm mainForm = new MainForm();
                mainForm.createAndShowUI();
                SwingUtilities.getWindowAncestor(rootPanel).dispose();
            } else {
                JOptionPane.showMessageDialog(rootPanel, "Wrong username or password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        JFrame frame = new JFrame("Login Form");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new LoginForm().rootPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
