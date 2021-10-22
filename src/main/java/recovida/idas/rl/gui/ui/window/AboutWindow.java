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
    public static final String PROJECT_URL = "https://recovida.com.br/";

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

        JPanel bottomPanel = new JPanel();
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        JButton closeBtn = new JButton(
                MessageProvider.getMessage("menu.help.about.close"));
        closeBtn.addActionListener(e -> dispose());
        bottomPanel.add(closeBtn);

        JPanel mainPanel = new JPanel();
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        JPanel topPanel = new JPanel();
        mainPanel.add(topPanel);

        JPanel descriptionPanel = new JPanel();
        mainPanel.add(descriptionPanel);
        descriptionPanel
                .setLayout(new BoxLayout(descriptionPanel, BoxLayout.X_AXIS));

        Component horizontalStrut = Box.createHorizontalStrut(20);
        descriptionPanel.add(horizontalStrut);

        JLabel descriptionLabel = new JLabel("<html>"
                + MessageProvider.getMessage("menu.help.about.description")
                + "</html>");
        descriptionPanel.add(descriptionLabel);
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        Component hs1 = Box.createHorizontalStrut(20);
        descriptionPanel.add(hs1);

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
