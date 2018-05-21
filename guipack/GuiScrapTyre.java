package guipack;

import javax.swing.*;

import static guipack.SwingConsole.run;

/**
 * Класс GuiScrapTyre предназначен для запуска программы
 */

public class GuiScrapTyre extends JFrame {
    private ContentPane contantPane = new ContentPane();

    public GuiScrapTyre() {
        add(contantPane);
    }

    public static void main(String[] args) {
        run(new GuiScrapTyre(), 324, 324);
    }
}



