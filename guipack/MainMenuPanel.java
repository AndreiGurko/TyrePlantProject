package guipack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Клсс MainMenuPanel представляет панель меню на которой находятся 4 кнопки.
 * После регистрации оператору отображается данное меню для следующих действий.
 * Всего доступно 4 действия:
 * 1. Отгрузка шин (отобразится панель предназначенная для отгрузки шин)
 * 2. Передача данных (согласно ТЗ данная кнопка должна присутствовать, но никаких дейсвтий пока не выполнять)
 * 3. Очистка файла (удаляет данные об отгруженных бракованных шин, которые сохраняются в файле созданном автоматически
 *    в текущей сесси оператора в директории "C:\ScrapTires"
 * 4. Ввод табельного (выход оператора из текущей сессии. Переход на панель входа)
 *
 */

public class MainMenuPanel extends JPanel {

    private JPanel panelShipmentOfTiresButton = new JPanel();
    private JPanel panelSendDataButton = new JPanel();
    private JPanel panelClearDataButton = new JPanel();
    private JPanel panelEnterLoginButton = new JPanel();

    private JButton shipmentOfTires = new JButton("Отгрузка шин");
    private JButton sendData = new JButton("Передача данных");
    private JButton clearData = new JButton("Очистка файла");
    private JButton enterLogin = new JButton("Ввод табельного");
    private WorkingInformation workingInformation;
    private BarcodeReaderPanel barcodeReaderPanel;

    public MainMenuPanel(WorkingInformation workingInformation, BarcodeReaderPanel barcodeReaderPanel) {
        this.workingInformation = workingInformation;
        this.barcodeReaderPanel = barcodeReaderPanel;
        setLayout(new GridLayout(4, 1));
        add(panelShipmentOfTiresButton);
        add(panelSendDataButton);
        add(panelClearDataButton);
        add(panelEnterLoginButton);

        panelShipmentOfTiresButton.add(shipmentOfTires);
        shipmentOfTires.setPreferredSize(new Dimension(150, 30));
        shipmentOfTires.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        panelSendDataButton.add(sendData);
        sendData.setPreferredSize(new Dimension(150, 30));
        panelClearDataButton.add(clearData);
        clearData.setPreferredSize(new Dimension(150, 30));
        clearData.addActionListener(new ClearDataButtonListner());
        panelEnterLoginButton.add(enterLogin);
        enterLogin.setPreferredSize(new Dimension(150, 30));
        setVisible(true);
    }

    public JButton getEnterLoginButton() {
        return enterLogin;
    }

    public JButton getShipmentOfTires() {
        return shipmentOfTires;
    }

   //Внутренний класс реализующий логику удаления файла при нажатии кнопки "Очистка файла"
    class ClearDataButtonListner implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //если файла нет - вывод окна информирующее об этом
            if (workingInformation.getPathToFile() == null) {
                JOptionPane.showMessageDialog(panelClearDataButton, "Файл отсутствует!", null, JOptionPane.INFORMATION_MESSAGE);
            } else {
                //если файл существует, запросить два раза (согласно ТЗ) о подтверждении об удалении
                int selection = JOptionPane.showConfirmDialog(panelClearDataButton, "Вы собираетесь удалить файл!",
                        "Запрос на удаление файла", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (selection == JOptionPane.OK_OPTION) {
                    int additionalSelection = JOptionPane.showConfirmDialog(panelClearDataButton, "Подтвердить удаление?",
                            null, JOptionPane.OK_CANCEL_OPTION);
                    if (additionalSelection == JOptionPane.OK_OPTION) {
                        workingInformation.removePathToFile();
                        JOptionPane.showMessageDialog(panelClearDataButton, "Файл удален!", null, JOptionPane.INFORMATION_MESSAGE);
                        barcodeReaderPanel.resetTotallabelCounter();

                    }
                }
            }

        }
    }
}

