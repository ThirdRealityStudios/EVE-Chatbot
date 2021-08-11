package chat;

import java.io.Serializable;
import java.util.ArrayList;

public class Entry implements Serializable
{
    private static final long serialVersionUID = 2L;

    private ArrayList<Message> outputs;

    private Message input;

    public Entry(String entry)
    {
        outputs = new ArrayList<Message>();

        this.input = new Message(entry);
    }

    public void link(Message msg)
    {
        outputs.add(msg);
    }

    public Message getEntryName()
    {
        return input;
    }

    public ArrayList<Message> getOutputs()
    {
        return outputs;
    }

    @Override
    public String toString()
    {
        return input.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        return ((Entry) o).toString().equals((toString()));
    }
}
