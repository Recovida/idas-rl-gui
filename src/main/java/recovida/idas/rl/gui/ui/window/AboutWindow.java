package recovida.idas.rl.gui.ui.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.text.MessageFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import recovida.idas.rl.gui.MainWindow;
import recovida.idas.rl.gui.lang.MessageProvider;

/**
 * Windows that shows basic information about the program.
 */
public class AboutWindow extends JDialog {

    private static final long serialVersionUID = -9167482401993772672L;

    /**
     * Creates the dialogue.
     *
     * @param parent the parent frame
     */
    public AboutWindow(Frame parent) {
        super(parent);
        setLocationByPlatform(true);
        setMinimumSize(new Dimension(400, 400));
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
        pack();
    }

}
