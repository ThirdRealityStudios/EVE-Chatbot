import chat.Entry;
import chat.Message;

import java.io.Serializable;
import java.util.ArrayList;

public class Bot implements Serializable
{
    private static final long serialVersionUID = 2L;

    private String name;
    private float matchQuote;

    private ArrayList<Entry> dictionary;

    public Bot(String name, float matchQuote)
    {
        this.name = name;
        this.matchQuote = matchQuote;

        dictionary = new ArrayList<Entry>();
    }

    public String getName()
    {
        return name;
    }

    public float getMatchQuote()
    {
        return matchQuote;
    }

    public void processChat(ArrayList<Entry> conversation)
    {
        int chatSize = conversation.size();

        Entry chatEntry = conversation.get(chatSize - 1);

        if(!dictionary.contains(chatEntry))
        {
            dictionary.add(chatEntry);
        }

        if(chatSize > 1)
        {
            Entry lastChatEntry = conversation.get(chatSize - 2);

            lastChatEntry = dictionary.get(dictionary.indexOf(lastChatEntry));

            if(!lastChatEntry.getOutputs().contains(chatEntry.getEntryName()))
            {
                lastChatEntry.link(chatEntry.getEntryName());
            }
        }
    }

    private int countWords(ArrayList<String> compared, ArrayList<String> in)
    {
        int matches = 0;

        for(String text : compared)
        {
            if(in.contains(text))
            {
                matches++;
            }
        }

        return matches;
    }

    private float getMatchQuote(Entry dictionaryEntry, Entry chatEntry)
    {
        float matchQuote = (float) countWords(chatEntry.getEntryName().getWords(), dictionaryEntry.getEntryName().getWords()) / (float) dictionaryEntry.getEntryName().getWords().size();

        return matchQuote;
    }

    public Message answer(Entry chatEntry)
    {
        for(Entry entry : dictionary)
        {
            if(getMatchQuote(entry, chatEntry) >= matchQuote)
            {
                boolean hasAnswers = entry.getOutputs().size() > 0;

                if(hasAnswers)
                {
                    int randomIndex = (int) (Math.random() * entry.getOutputs().size());

                    return entry.getOutputs().get(randomIndex);
                }
                else
                {
                    return new Message("I can't help you with this..");
                }
            }
        }

        return null;
    }

    public ArrayList<Entry> getDictionary()
    {
        return dictionary;
    }
}
