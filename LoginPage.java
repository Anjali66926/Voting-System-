import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginPage extends JFrame {

    JTextField userField;
    JPasswordField passField;

    public LoginPage() {
        setTitle("Voting System - Login");
        setSize(350, 250);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 30, 60));
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("LOGIN", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.WHITE);

        userField = new JTextField();
        userField.setBorder(BorderFactory.createTitledBorder("Username"));

        passField = new JPasswordField();
        passField.setBorder(BorderFactory.createTitledBorder("Password"));

        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(0, 153, 255));
        loginBtn.setForeground(Color.WHITE);

        loginBtn.addActionListener(e -> login());

        panel.add(title);
        panel.add(userField);
        panel.add(passField);
        panel.add(loginBtn);

        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    void login() {
        String username = userField.getText();
        String password = new String(passField.getPassword());

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT * FROM users WHERE username=? AND password=?")) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("id");

                JOptionPane.showMessageDialog(this, "Welcome " + username);

                new VotingPage(userId);
                dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Invalid Login");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}