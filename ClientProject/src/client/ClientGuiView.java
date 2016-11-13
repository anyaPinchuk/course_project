package client;

import frames.*;
import message.MessageType;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ANYA on 04.10.2016.
 */
public class ClientGuiView extends JFrame {

    private final ClientGuiController controller;
    private AbstractFrame frame = null;

    public ClientGuiView(ClientGuiController controller) {
        this.controller = controller;
        initView();
    }

    private void initView() {
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Зайди на Pinchik - купи билет на кинчик!");
        setSize(300, 200);
        setFrame(new ConnectionFrame(controller));

        setVisible(true);
    }

    public void showMessageInfo(String s){
        JOptionPane.showMessageDialog(this, s);
    }

    public void showInfoToUser(MessageType type, Object o){
        if(type!=null){
            frame.showInfo(type, o);
        }

    }
    public void showMessageError(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    protected void updateWindow(ClientGuiModel.ConnectionState nowConnectionState) {
        AbstractFrame frame;
        switch (nowConnectionState) {
            case TRY_CONNECTION:
                frame = new ConnectionFrame(controller);
                setSize(frame.getDimension());
                setFrame(frame);
                break;
            case AUTHORIZATION:
                frame = new AuthorizationFrame(controller);
                setSize(frame.getDimension());
                setFrame(frame);
                break;
            case REGISTRATION: {
                frame = new RegistrationFrame(controller);
                setSize(frame.getDimension());
                setFrame(frame);
                break;
            }
            case CONNECTED: {
                frame = new MainFrame(controller);
                setSize(frame.getDimension());
               setFrame(frame);
                break;
            }
            case PERSON: {
                frame = new PersonFrame(controller);
                System.out.println("person");
                setSize(frame.getDimension());
                setFrame(frame);
                break;
            }
            case CONNECTED_ADMIN:{
                frame = new MainFrameAdmin(controller);
                setSize(frame.getDimension());
                setFrame(frame);
                break;
            }
            case ADD_NEW_SESSION:{
                frame = new AddSessionFrame(controller);
                setSize(frame.getDimension());
                setFrame(frame);
                break;
            }
            case DELETE_THIS_SESSION:{
                frame = new DeleteSessionFrame(controller);
                setSize(frame.getDimension());
                setFrame(frame);
                break;
            }
        }
    }

    protected void setFrame(AbstractFrame frame) {
        setLocationRelativeTo(null);
        if (this.frame != null) {
            remove(this.frame);
        }
        this.frame = frame;
        add(frame);
        revalidate();
    }
}
