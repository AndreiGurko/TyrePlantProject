package guipack;

import javax.swing.*;
import java.awt.*;

/**
 * Класс LoginPanel представляет панель входа оператора,
 * а также осуществляет валидацию данных входа по табельному номеру
 */

public class LoginPanel extends JPanel {

    private JPanel panelLabelTextField = new JPanel();
    private JPanel panelTextFieldRegistration = new JPanel();
    private JPanel panelEnterButton = new JPanel();
    private JLabel labelTitle = new JLabel("Введите табельный номер");
    private JTextField textFieldRegistration = new JTextField(18);
    JButton enterButton = new JButton("Ввод(Enter)");

    public LoginPanel() {
        setLayout(new BorderLayout());
        add(panelLabelTextField, BorderLayout.NORTH);
        add(panelTextFieldRegistration, BorderLayout.CENTER);
        add(panelEnterButton, BorderLayout.SOUTH);

        panelLabelTextField.add(labelTitle);
        panelTextFieldRegistration.add(textFieldRegistration);
        panelEnterButton.add(enterButton);
    }

    public boolean loginValidator() {
        String login = textFieldRegistration.getText();
        if (login.length() < 3) {
            labelTitle.setForeground(Color.RED);
            labelTitle.setText("Недопустимый табельный номер. Повторите ввод");
            textFieldRegistration.setText("");
            return false;
        } else {
            labelTitle.setForeground(Color.BLACK);
            labelTitle.setText("Введите табельный номер");
            return true;
        }
    }

    public String getUserLogin() {
        return textFieldRegistration.getText();
    }

    public JTextField getTextFieldRegistration() {
        return textFieldRegistration;
    }

    public JButton getEnterButton() {
        return enterButton;
    }
}
