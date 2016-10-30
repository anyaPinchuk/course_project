package message;

import java.io.Serializable;

/**
 * Created by ANYA on 20.09.2016.
 */
public class Message implements Serializable
{
    private final MessageType type;
    private final Object data;

    public Message(MessageType type)
    {
        this.type = type;
        data = null;
    }

    public Message(MessageType type, Object data)
    {
        this.type = type;
        this.data = data;
    }

    public MessageType getType()
    {
        return type;
    }

    public Object getData()
    {
        return data;
    }
}
