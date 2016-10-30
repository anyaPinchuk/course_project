package frames;

import client.ClientGuiController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ANYA on 04.10.2016.
 */
public class AuthorizationFrame extends AbstractFrame {
    public AuthorizationFrame(ClientGuiController controller) {
        super(controller);
    }

    private JLabel labelInfoName;
    private JLabel labelInfoPass;
    private JTextField textFieldName;
    private JPasswordField textFieldPass;
    private JButton buttonAuthorization;
    private JButton registration;

    @Override
    protected void initializationWindow() {
        setLayout(new FlowLayout());
        setBackground(Color.PINK);

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
        registration = new JButton("Регистрация");
        if (!controller.getAdmin()){
            add(registration);
        }
        buttonAuthorization = new JButton("Вход");
        add(buttonAuthorization);
        registration.addActionListener(e->{
            controller.tryRegistration();
        });

        buttonAuthorization.addActionListener(e -> controller.authorization(textFieldName.getText(), textFieldPass.getText()));
    }

    @Override
    public Dimension getDimension() {
        return new Dimension(300,200);
    }
}
