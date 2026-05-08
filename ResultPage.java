import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class ResultPage extends JFrame {

    JTextArea area;

    public ResultPage() {
        setTitle("Election Results");
        setSize(450, 400);
        setLocationRelativeTo(null);

        area = new JTextArea();
        area.setFont(new Font("Arial", Font.BOLD, 14));
        area.setEditable(false);

        add(new JScrollPane(area));

        loadResults();

        setVisible(true);
    }

    void loadResults() {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT name, type, votes FROM candidates ORDER BY type, votes DESC");
             ResultSet rs = ps.executeQuery()) {

            StringBuilder sb = new StringBuilder();

            sb.append("🏆 BANGALORE ELECTION RESULTS 🏆\n\n");

            String currentType = "";

            while (rs.next()) {

                String type = rs.getString("type");
                String name = rs.getString("name");
                int votes = rs.getInt("votes");

                // 🔥 Handle null safely
                if (type == null) type = "UNKNOWN";

                // 🔥 Print heading when type changes
                if (!type.equals(currentType)) {
                    sb.append("\n=== ").append(type).append(" RESULTS ===\n");
                    currentType = type;
                }

                sb.append("• ")
                  .append(name)
                  .append(" → ")
                  .append(votes)
                  .append(" votes\n");
            }

            // 🔥 If no data
            if (sb.toString().trim().isEmpty()) {
                sb.append("No results available.");
            }

            area.setText(sb.toString());

        } catch (Exception e) {
            area.setText("Error: " + e.getMessage());
        }
    }
}