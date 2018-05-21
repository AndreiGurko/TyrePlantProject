package guipack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Класс ContentPane контролирует размещение и взаимодействие панелей: панели для входа, панели главного меню и панели для огтрузки шин.
 */

public class ContentPane extends JPanel {
    private LoginPanel loginPanel = new LoginPanel();
    private WorkingInformation workingInformation = new WorkingInformation();
    private BarcodeReaderPanel barcodeReaderPanel = new BarcodeReaderPanel(workingInformation);
    private MainMenuPanel mainMenuPanel = new MainMenuPanel(workingInformation, barcodeReaderPanel);

    public ContentPane() {
        setLayout(new BorderLayout());
        add(loginPanel);
        loginPanel.getEnterButton().addActionListener(new EnterButtonController());
        mainMenuPanel.getEnterLoginButton().addActionListener(new EnterLogOutButtonController());
        mainMenuPanel.getShipmentOfTires().addActionListener(new ShipmentOfTyresButtonController());
        barcodeReaderPanel.getLogOutButton().addActionListener(new LogOutButtonController());
    }

    //При успешной валидации оператора, данный класс отображает панель главного меню
    class EnterButtonController implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (loginPanel.loginValidator()) {
                workingInformation.setOperatorID(loginPanel.getUserLogin());
                barcodeReaderPanel.getLoginLabel().setText("Табельный номер: " + workingInformation.getOperatorID());
                loginPanel.getTextFieldRegistration().setText("");
                loginPanel.setVisible(false);
                remove(loginPanel);
                add(mainMenuPanel);
                mainMenuPanel.setVisible(true);
                repaint();
            }
        }
    }

    //При нажатии на клавишу "Ввод табельного" на панели главного меню, данный класс отображает панель для входа
    class EnterLogOutButtonController implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            add(loginPanel);
            loginPanel.setVisible(true);
            remove(mainMenuPanel);
            repaint();
        }
    }

//При нажатии на клавишу "Отгрузка шин" на панели главного меню, данный класс отображает панель для отгрузки шин
    class ShipmentOfTyresButtonController implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mainMenuPanel.setVisible(false);
            add(barcodeReaderPanel);
            barcodeReaderPanel.setVisible(true);
            remove(mainMenuPanel);
            repaint();
        }
    }

//При нажатии на клавишу "Выйти" на панели отгрузки шин, данный класс отображает панель главного меню
    class LogOutButtonController implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            barcodeReaderPanel.setVisible(false);
            add(mainMenuPanel);
            mainMenuPanel.setVisible(true);
            repaint();
        }
    }
}
