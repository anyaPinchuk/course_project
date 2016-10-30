package frames;

import client.ClientGuiController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ANYA on 04.10.2016.
 */
public class PersonFrame extends AbstractFrame {
    private JButton buttonAdmin;
    private JButton buttonUser;

    public PersonFrame(ClientGuiController controller) {
        super(controller);
    }

    @Override
    protected void initializationWindow() {
        setLayout(new FlowLayout());
        setBackground(Color.PINK);

        buttonAdmin = new JButton("Admin");
        buttonUser = new JButton("User");
        add(buttonAdmin);
        add(buttonUser);
        buttonAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setAdmin(true);
            }
        });
        buttonUser.addActionListener(e->{
            controller.setAdmin(false);
            revalidate();
        });
    }

    @Override
    public Dimension getDimension() {
        return new Dimension(300,200);
    }
}
