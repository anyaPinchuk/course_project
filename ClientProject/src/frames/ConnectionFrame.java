package frames;

import client.ClientGuiController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ANYA on 04.10.2016.
 */
public class ConnectionFrame extends AbstractFrame{
    public ConnectionFrame(ClientGuiController controller) {
        super(controller);
    }
    private JLabel labelInfoIp;
    private JLabel labelInfoPort;
    private JTextField textFieldIp;
    private JTextField textFieldPort;
    private JButton buttonTryConnection;

    private String ip;
    private int port;

    @Override
    protected void initializationWindow() {
        setLayout(new FlowLayout());
        setBackground(Color.PINK);

        Font font = new Font("Comic Sans", Font.BOLD, 15);

        labelInfoIp = new JLabel("Введите IP-адрес сервера");
        labelInfoIp.setFont(font);
        labelInfoIp.setForeground(Color.WHITE);
        add(labelInfoIp);
        textFieldIp = new JTextField(20);
        textFieldIp.setText("127.0.0.1");
        add(textFieldIp);

        labelInfoPort = new JLabel("Введите порт сервера");
        labelInfoPort.setFont(font);
        labelInfoPort.setForeground(Color.WHITE);
        add(labelInfoPort);
        textFieldPort = new JTextField(20);

        textFieldPort.setText("1313");
        add(textFieldPort);

        buttonTryConnection = new JButton("Подключиться");
        add(buttonTryConnection);

        buttonTryConnection.addActionListener(e -> {
            try {
                ip = textFieldIp.getText();
                port = Integer.parseInt(textFieldPort.getText());
                controller.tryConnection(ip, port);
            } catch (NumberFormatException ignore) {
                JOptionPane.showMessageDialog(this, "Порт сломался", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    @Override
    public Dimension getDimension() {
        return new Dimension(300,200);
    }
}
