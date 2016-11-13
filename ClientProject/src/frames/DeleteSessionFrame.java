package frames;

import client.ClientGuiController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ANYA on 08.11.2016.
 */
public class DeleteSessionFrame extends AbstractFrame {
    private JTextField sessionID;
    private JButton deleteButton;
    private String id;

    public DeleteSessionFrame(ClientGuiController controller) {
        super(controller);
    }

    @Override
    protected void initializationWindow() {
        setLayout(null);
        setBackground(Color.PINK);
        JLabel label = new JLabel("Введите ID удаляемого сеанса");
        label.setLayout(null);
        label.setBounds(50,20,200,20);
        add(label);
        sessionID = new JTextField(5);
        sessionID.setLayout(null);
        sessionID.setBounds(50,50,150,20);
        add(sessionID);
        deleteButton = new JButton("Удалить сеанс");
        deleteButton.setLayout(null);
        deleteButton.setBounds(60,100,150,40);
        add(deleteButton);
        deleteButton.addActionListener(e->{
            id = sessionID.getText();
            try{
                controller.deleteSession(Integer.parseInt(id));
            }
            catch (NumberFormatException t){
                JOptionPane.showMessageDialog(this,"Некорректный ID!");
            }
        });
    }

    @Override
    public Dimension getDimension() {
        return new Dimension(300,200);
    }
}
