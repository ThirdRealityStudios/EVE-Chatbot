package chat;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable
{
    private static final long serialVersionUID = 3L;

    private ArrayList<String> words;

    private String message;

    private int priority = 0;

    public Message(String message)
    {
        words = new ArrayList<String>();

        this.message = message;

        String[] splitSentence = message.split(" ");

        for(String word : splitSentence)
        {
            words.add(word);
        }
    }

    public ArrayList<String> getWords()
    {
        return words;
    }

    public int getPriority()
    {
        return priority;
    }

    public void decreasePriority()
    {
        if((priority - 1) > Integer.MIN_VALUE)
        {
            priority -= 2;
        }
    }

    public void increasePriority()
    {
        if(priority < Integer.MAX_VALUE)
        {
            priority++;
        }
    }

    @Override
    public String toString()
    {
        return message;
    }

    @Override
    public boolean equals(Object o)
    {
        return ((Message) o).toString().equals((toString()));
    }
}
