import chat.Entry;
import chat.Message;

import java.io.Serializable;
import java.util.*;

public class Bot implements Serializable
{
    private static final long serialVersionUID = 3L;

    private String name;
    private float matchQuote;
    private int maxAnswers = 5;

    private ArrayList<Entry> dictionary;

    private Entry lastEntry, currentEntry;
    private Message lastAnswer, currentAnswer;

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

    // When the same entry is repeated (until the last answer), EVE will "think" the last answers have less importance.
    public void evaluateImportance()
    {
        if(lastEntry != null && lastAnswer != null)
        {
            if(!lastAnswer.equals(currentAnswer) && lastEntry.equals(currentEntry))
            {
                lastAnswer.decreasePriority();

                currentAnswer.increasePriority();

                Collections.sort(lastEntry.getOutputs());
            }
        }
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

    private ArrayList<Message> getSomeAnswers(Entry entry)
    {
        ArrayList<Message> foundAnswers = new ArrayList<Message>(maxAnswers);

        Iterator<Message> iterator = entry.getOutputs().iterator();

        for(int i = 0; i < maxAnswers && iterator.hasNext(); i++)
        {
            Message answer = iterator.next();

            foundAnswers.add(answer);
        }

        return foundAnswers;
    }

    private Message getRandomAnswer(ArrayList<Message> answers)
    {
        int randomIndex = (int) (Math.random() * answers.size());

        return answers.get(randomIndex);
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
                    Message foundAnswer = getRandomAnswer(getSomeAnswers(entry));

                    // Update the entry history.
                    lastEntry = currentEntry;
                    currentEntry = chatEntry;

                    // Update the answer history.
                    lastAnswer = currentAnswer;
                    currentAnswer = foundAnswer;

                    evaluateImportance();

                    return foundAnswer;
                }

                Message defaultAnswer = new Message("I can't help you with this..");

                // Update the entry history.
                lastEntry = currentEntry;
                currentEntry = chatEntry;

                // Update the answer history.
                lastAnswer = currentAnswer;
                currentAnswer = defaultAnswer;

                evaluateImportance();

                return defaultAnswer;
            }
        }

        evaluateImportance();

        return null;
    }

    public ArrayList<Entry> getDictionary()
    {
        return dictionary;
    }

    public void printDictionary()
    {
        for(int i = 0; i < getDictionary().size(); i++)
        {
            Entry dictEntry = dictionary.get(i);

            System.out.println(i + "." + dictEntry + ":");

            for(int e = 0; e < dictEntry.getOutputs().size(); e++)
            {
                Message linkedMessage = dictEntry.getOutputs().get(e);

                System.out.println("-> " + e + ". (" + linkedMessage.getPriority() + ") " + linkedMessage.toString());
            }
        }
    }
}
