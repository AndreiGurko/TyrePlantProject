package guipack;
import javax.swing.*;

/**
 * Вспомогательный класс для передачи задачи методу SwingUtiliteis.invokeLater()
 *
 */
public class SwingConsole {
    public static void run(final JFrame frameRegistration, final int width, final int height) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frameRegistration.setTitle("");
                frameRegistration.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frameRegistration.setSize(width, height);
                frameRegistration.setVisible(true);
            }
        });
    }
}
