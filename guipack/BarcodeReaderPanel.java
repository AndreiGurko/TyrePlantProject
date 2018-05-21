package guipack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Класс BarcodeReaderPanel предназначен для отображения панели с необходимым функционалом согласно ТЗ.
 * Это оснавная рабочая панель оператора.
 */
public class BarcodeReaderPanel extends JPanel {
    //объект класса WorkingInformation предоставляет основной функционал записи данных введеные оператором в файл.
    private WorkingInformation workingInformation;
    private JLabel statusLabel = new JLabel("       Сканируйте штрихкод");
    private JLabel statusLabel2 = new JLabel("или введите серийный номер");
    private JTextField barcodeTextField = new JTextField(18);
    private JButton enterButton = new JButton("Ввод(Enter)");
    private JButton whithoutBarcode = new JButton("Без штрихкода");
    private JButton logOutButton = new JButton("Выйти");
    private JLabel dateTimeLabel = new JLabel();
    private JLabel totalCounterTires = new JLabel("Отгружено: 0");
    private JLabel lastOperation = new JLabel("Посл.действ-ие: ");
    private JLabel loginLabel = new JLabel();
    private JPanel statusLabelPanel = new JPanel();
    private JPanel buttonsActionsPanel = new JPanel();
    private JPanel additionalInformationPanel = new JPanel();
    private JPanel additionalInformationPanel2 = new JPanel();
    private Timer screenClock = null;

    public BarcodeReaderPanel(WorkingInformation workingInformation) {
        this.workingInformation = workingInformation;
        add(statusLabelPanel);
        statusLabelPanel.setLayout(new GridLayout(2, 1));
        statusLabelPanel.add(statusLabel);
        statusLabelPanel.add(statusLabel2);
        add(barcodeTextField);
        add(buttonsActionsPanel);
        buttonsActionsPanel.setLayout(new GridLayout(3, 1, 300, 20));
        buttonsActionsPanel.add(enterButton);
        enterButton.addActionListener(new EnterButtonListner());
        buttonsActionsPanel.add(whithoutBarcode);
        whithoutBarcode.addActionListener(new WhithoutBarcodeListner());
        buttonsActionsPanel.add(logOutButton);
        additionalInformationPanel.setPreferredSize(new Dimension(300, 40));
        add(additionalInformationPanel);
        additionalInformationPanel.add(dateTimeLabel);
        additionalInformationPanel.add(totalCounterTires);
        additionalInformationPanel.add(loginLabel);
        loginLabel.setText( "Табельный номер: ");
        add(additionalInformationPanel2);
        additionalInformationPanel2.add(lastOperation);
        screenClock = new Timer(1000, new Clock());
        screenClock.start();
    }

    public JButton getLogOutButton() {
        return logOutButton;
    }

//Внутренний класс, реализует логику валидации вводимых оператором штрихкодов или серийных номеров
    class EnterButtonListner implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (barcodeTextField.getText().length() > 10 || barcodeTextField.getText().length() < 3) {
                statusLabel2.setForeground(Color.RED);
                statusLabel2.setText("Неправильный формат штрихкода");
                barcodeTextField.setText("");
            } else {
                statusLabel2.setText("или введите серийный номер");
                statusLabel2.setForeground(Color.BLACK);
                combainInformationTyre();
                setTotalCounterTires();
                setLastOperation();
            }
        }
    }
//Метод combainInformationTyre() проверяет валидность штрихкодов или серийного номера. Если данные валидны,
//то осуществляет передачу данных для записи в файл
    public void combainInformationTyre() {
        //штрихкод на предприятии может начинаться только одной из 3х цифр: 7, 8 или 9
        if (((barcodeTextField.getText().charAt(0) == '7') || (barcodeTextField.getText().charAt(0) == '8') || (barcodeTextField.getText().charAt(0) == '9'))
                && barcodeTextField.getText().length() ==10) {
            workingInformation.whithoutBarcode = false;
        }
        else {
            workingInformation.whithoutBarcode = true;
        }
        StringBuilder barCode = new StringBuilder();
        barCode.append(barcodeTextField.getText());
        workingInformation.writeToFile(barCode.toString());
        barcodeTextField.setText("");
    }

    //Данный метод подсчитывает общее колличество отгруженных шин и отображает счетчик на панели
    public void setTotalCounterTires() {
        String totalCounter = String.valueOf(workingInformation.getCounterOfTyres());
        totalCounterTires.setText("Отгружено: " + totalCounter);
    }

    //Данный метод отображает на панели последнее действие оператора: (штрихкод, серийный номер или статус "Без штрихкода")
    //последней отгруженной шины
    public void setLastOperation() {
        String lastBarcode = workingInformation.getLastBarcode();
        lastOperation.setText("Посл.действ-ие: " + lastBarcode);
    }
 //Данный метод отображает на панели цифровые часы
    class Clock implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dateTimeLabel.setText(workingInformation.getCurrentTime());
        }
    }
//Данный класс реализует логику, согласно которой если нажата клавиши "whithoutBarcode" необходимо в файл записать "Без штрихкода" в поле записии маски штрихкода
    class WhithoutBarcodeListner implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            workingInformation.whithoutBarcode = false;
            workingInformation.writeToFile("Без штрихкода");
            setTotalCounterTires();
            setLastOperation();
        }
    }
//Данный метод обнуляет и сбрасывает информацию об отрузки шин в текущей сессии
    public void resetTotallabelCounter() {
        lastOperation.setText("Посл.действ-ие: ");
        totalCounterTires.setText("Отгружено: 0");
        statusLabel2.setForeground(Color.BLACK);
        statusLabel2.setText("или введите серийный номер");
    }

    public JLabel getLoginLabel() {
        return loginLabel;
    }
}
