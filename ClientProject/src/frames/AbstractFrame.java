package frames;

import client.ClientGuiController;
import message.MessageType;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ANYA on 04.10.2016.
 */
public abstract class AbstractFrame extends JPanel implements ViewInfo{

    protected final ClientGuiController controller;

    protected AbstractFrame(ClientGuiController controller) {
        this.controller = controller;
        initializationWindow();
    }

    @Override
    public void showInfo(MessageType type, Object o) {

    }

    protected abstract void initializationWindow();
    public abstract Dimension getDimension();
}
