package recovida.idas.rl.gui.ui.window;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.text.MessageFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import recovida.idas.rl.gui.MainWindow;
import recovida.idas.rl.gui.lang.MessageProvider;

/**
 * Windows that shows basic information about the program.
 */
public class AboutWindow extends JDialog {

    private static final long serialVersionUID = -9167482401993772672L;

    /**
     * Repository URL.
     */
    public static final String REPO_URL = "https://gitlab.com/recovida/idas-rl-gui";

    /**
     * Project URL.
     */
    public static final String PROJECT_URL = "";

    /**
     * Creates the dialogue.
     *
     * @param parent the parent frame
     */
    public AboutWindow(Frame parent) {
        super(parent);
        setLocationByPlatform(true);
        setMinimumSize(new Dimension(400, 400));
        setPreferredSize(new Dimension(400, 400));
        setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setModal(true);
        setTitle(MessageFormat.format(
                MessageProvider.getMessage("menu.help.about.about"),
                MainWindow.PROGRAM_NAME));

        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.SOUTH);

        JButton closeBtn = new JButton(
                MessageProvider.getMessage("menu.help.about.close"));
        closeBtn.addActionListener(e -> dispose());
        panel.add(closeBtn);

        JPanel mainPanel = new JPanel();
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        JPanel topPanel = new JPanel();
        mainPanel.add(topPanel);

        JPanel panel_1 = new JPanel();
        mainPanel.add(panel_1);
        panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

        Component horizontalStrut = Box.createHorizontalStrut(20);
        panel_1.add(horizontalStrut);

        JLabel descriptionLabel = new JLabel("<html>"
                + MessageProvider.getMessage("menu.help.about.description")
                + "</html>");
        panel_1.add(descriptionLabel);
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        Component horizontalStrut_1 = Box.createHorizontalStrut(20);
        panel_1.add(horizontalStrut_1);

        Component vg1 = Box.createVerticalGlue();
        mainPanel.add(vg1);

        JLabel repoLabel = new JLabel(String.format(
                "<html><a href=\"%s\">%s</a></html>", REPO_URL,
                MessageProvider.getMessage("menu.help.about.repository")));
        repoLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        repoLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        repoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        repoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        repoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(REPO_URL));
                } catch (Exception ex) {
                }
            }
        });
        mainPanel.add(repoLabel);

        Component vg2 = Box.createVerticalGlue();
        mainPanel.add(vg2);

        JLabel projectWebsiteLabel = new JLabel(String.format(
                "<html><a href=\"%s\">%s</a></html>", PROJECT_URL,
                MessageProvider.getMessage("menu.help.about.projectwebsite")));
        projectWebsiteLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        projectWebsiteLabel.setHorizontalAlignment(SwingConstants.CENTER);
        projectWebsiteLabel
                .setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        projectWebsiteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        if (!PROJECT_URL.isEmpty())
            projectWebsiteLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        Desktop.getDesktop().browse(new URI(PROJECT_URL));
                    } catch (Exception ex) {
                    }
                }
            });
        mainPanel.add(projectWebsiteLabel);

        Component verticalGlue = Box.createVerticalGlue();
        mainPanel.add(verticalGlue);

        pack();

        Font f = getFont();
        setFont(f.deriveFont(f.getSize2D() * 1.1f));
    }

}
