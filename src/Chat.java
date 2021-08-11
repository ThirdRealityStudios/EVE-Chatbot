import chat.Entry;
import chat.Message;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Chat
{
    private ArrayList<Entry> conversation;

    private Scanner scanner;

    private Bot bot;

    public Chat()
    {
        conversation = new ArrayList<Entry>();

        scanner = new Scanner(System.in);

        bot = new Bot("EVE");

        System.out.println("[" + bot.getName() + "]: Conversation started!");

        load();

        //bot.setMinimumMatchQuote(0.85f);
    }

    private void write()
    {
        System.out.print("[You]: ");
        String text = scanner.nextLine();

        Entry entry = new Entry(text);

        switch(text)
        {
            case "/save":
            {
                save();

                System.out.println("EVE saved!");

                break;
            }
            case "/load":
            {
                load();

                System.out.println("EVE loaded!");

                break;
            }
            case "/exit":
            {
                save();

                System.out.println("EVE shut down!");

                System.exit(0);

                break;
            }
            case "/reset":
            {
                bot = new Bot(bot.getName());

                System.out.println("EVE reset!");

                break;
            }
            default:
            {
                conversation.add(entry);
            }
        }
    }

    public void chat()
    {
        while(true)
        {
            write();

            int chatSize = conversation.size();

            if(chatSize > 0)
            {
                bot.processChat(conversation);

                //printDictionary();

                Entry chatEntry = conversation.get(conversation.size() - 1);

                System.out.println("[" + bot.getName() + "]: " + bot.answer(chatEntry).toString());

                bot.printDictionary();
            }
        }
    }

    public void save()
    {
        try
        {
            FileOutputStream fileOutputStream = new FileOutputStream("chat.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(bot);
            objectOutputStream.flush();
            objectOutputStream.close();
        }
        catch(Exception e)
        {

        }
    }

    private void load()
    {
        try
        {
            FileInputStream fileInputStream = new FileInputStream("chat.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            Bot bot = (Bot) objectInputStream.readObject();
            this.bot = bot;

            objectInputStream.close();
        }
        catch(Exception e)
        {

        }

        System.out.println("[" + bot.getName() + "]: Minimum match quote = " + bot.getMatchQuote() * 100 + "%");
    }
}
