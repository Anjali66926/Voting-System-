import java.awt.*;
import javax.swing.*;

public class VotingPage extends JFrame {

    int userId;
    JComboBox<String> typeBox;

    public VotingPage(int userId) {
        this.userId = userId;

        setTitle("Select Election Type");
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,1,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel label = new JLabel("Select Election Type", JLabel.CENTER);

        typeBox = new JComboBox<>(new String[]{"MLA","MP"});

        JButton nextBtn = new JButton("Next");

        nextBtn.addActionListener(e -> {
            String type = (String) typeBox.getSelectedItem();

            System.out.println("Selected Type: " + type); // DEBUG

            new CandidatePage(userId, type);
            dispose();
        });

        panel.add(label);
        panel.add(typeBox);
        panel.add(nextBtn);

        add(panel);
        setVisible(true);
    }
}