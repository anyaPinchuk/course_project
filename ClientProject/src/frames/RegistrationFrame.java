package frames;

import client.ClientGuiController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ANYA on 06.10.2016.
 */
public class RegistrationFrame extends AbstractFrame {
    private JLabel labelInfoName;
    private JLabel labelInfoPass;
    private JTextField textFieldName;
    private JPasswordField textFieldPass;
    private JButton buttonRegistration;

    public RegistrationFrame(ClientGuiController controller) {
        super(controller);
    }

    @Override
    public void initializationWindow() {
        setLayout(new FlowLayout());
        setBackground(Color.DARK_GRAY);

        Font font = new Font("Verdana", Font.ITALIC | Font.BOLD, 13);

        labelInfoName = new JLabel("Введите логин");
        labelInfoName.setFont(font);
        labelInfoName.setForeground(Color.WHITE);
        add(labelInfoName);
        textFieldName = new JTextField(20);
        add(textFieldName);
        labelInfoPass = new JLabel("Введите пароль");
        labelInfoPass.setFont(font);
        labelInfoPass.setForeground(Color.WHITE);
        add(labelInfoPass);
        textFieldPass = new JPasswordField(20);
        add(textFieldPass);
        buttonRegistration = new JButton("Зарегистрироваться");
        add(buttonRegistration);
        buttonRegistration.addActionListener(e->{
            controller.registration(textFieldName.getText(), textFieldPass.getText());
        });
    }

    @Override
    public Dimension getDimension() {
        return new Dimension(300,200);
    }
}
