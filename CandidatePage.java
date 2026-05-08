import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class CandidatePage extends JFrame {

    int userId;
    String type;
    JComboBox<String> candidateBox;
    int[] candidateIds;

    public CandidatePage(int userId, String type) {
        this.userId = userId;
        this.type = type;

        setTitle(type + " Voting");
        setSize(350, 250);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel(type + " Candidates", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));

        candidateBox = new JComboBox<>();
        loadCandidates();

        JButton voteBtn = new JButton("Vote");

        voteBtn.addActionListener(e -> vote());

        panel.add(label);
        panel.add(candidateBox);
        panel.add(voteBtn);

        add(panel);
        setVisible(true);
    }

    // 🔥 Load candidates based on MLA / MP
    void loadCandidates() {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT id, name FROM candidates WHERE type=?")) {

            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();

            candidateIds = new int[10];
            int i = 0;

            while (rs.next()) {
                candidateBox.addItem(rs.getString("name"));
                candidateIds[i++] = rs.getInt("id");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    // 🔥 Count total votes correctly
    int getTotalVotes(Connection con) throws Exception {
        PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM votes");
        ResultSet rs = ps.executeQuery();

        if (rs.next()) return rs.getInt(1);
        return 0;
    }

    void vote() {
        try (Connection con = DBConnection.getConnection()) {

            // ❗ Ensure candidate selected
            if (candidateBox.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Please select a candidate!");
                return;
            }

            // 🔥 Check vote limit BEFORE voting
            if (getTotalVotes(con) >= 10) {
                JOptionPane.showMessageDialog(this, "Voting Closed!");
                new ResultPage();
                dispose();
                return;
            }

            // 🔒 Check if already voted for this type
            PreparedStatement check = con.prepareStatement(
                    "SELECT voted_mla, voted_mp FROM users WHERE id=?"
            );
            check.setInt(1, userId);
            ResultSet rs = check.executeQuery();

            if (rs.next()) {
                if (type.equals("MLA") && rs.getBoolean("voted_mla")) {
                    JOptionPane.showMessageDialog(this, "You already voted for MLA!");
                    new LoginPage();
                    dispose();
                    return;
                }
                if (type.equals("MP") && rs.getBoolean("voted_mp")) {
                    JOptionPane.showMessageDialog(this, "You already voted for MP!");
                    new LoginPage();
                    dispose();
                    return;
                }
            }

            int index = candidateBox.getSelectedIndex();
            int candidateId = candidateIds[index];

            // ✅ Insert vote
            PreparedStatement ps1 = con.prepareStatement(
                    "INSERT INTO votes(user_id, candidate_id, type) VALUES (?, ?, ?)"
            );
            ps1.setInt(1, userId);
            ps1.setInt(2, candidateId);
            ps1.setString(3, type);
            ps1.executeUpdate();

            // ✅ Update candidate votes
            PreparedStatement ps2 = con.prepareStatement(
                    "UPDATE candidates SET votes = votes + 1 WHERE id=?"
            );
            ps2.setInt(1, candidateId);
            ps2.executeUpdate();

            // ✅ Mark user voted
            if (type.equals("MLA")) {
                PreparedStatement ps3 = con.prepareStatement(
                        "UPDATE users SET voted_mla = TRUE WHERE id=?"
                );
                ps3.setInt(1, userId);
                ps3.executeUpdate();
            } else {
                PreparedStatement ps3 = con.prepareStatement(
                        "UPDATE users SET voted_mp = TRUE WHERE id=?"
                );
                ps3.setInt(1, userId);
                ps3.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Vote Successful!");

            // 🔥 Check AFTER voting
            if (getTotalVotes(con) >= 10) {
                JOptionPane.showMessageDialog(this, "Voting Completed!");
                new ResultPage();
            } else {
                new LoginPage();
            }

            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}