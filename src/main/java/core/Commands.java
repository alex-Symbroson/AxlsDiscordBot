package core;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;

public class Commands extends ListenerAdapter {

    public class Links {
        static final String
            nobot = "https://media.discordapp.net/attachments/327718257270194180/564498209825751040/Screenshot_from_2019-04-07_19-13-44.png",
            cookie = "https://previews.123rf.com/images/memoangeles/memoangeles1506/memoangeles150600002/40818650-chocolate-chip-cookie-vektor-clipart-illustration-mit-einfachen-farbverl%C3%A4ufen-alle-in-einer-einzigen-s.jpg";
    }

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        super.onPrivateMessageReceived(event);

        System.out.println(
            event.getAuthor().getAsTag() + " (" +
                event.getAuthor().getId() + "): \"" +
                event.getMessage().getContentRaw() + "\""
        );
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        super.onGuildMessageReceived(event);

        System.out.println(
            event.getGuild().getName() + ": " +
                event.getAuthor().getAsTag() + " (" +
                event.getAuthor().getId() + "): \"" +
                event.getMessage().getContentRaw() + "\""
        );

        String msg = event.getMessage().getContentRaw();

        if (!msg.startsWith(AxlsBot.prefix)) return;

        String[] args = msg.substring(1).split("\\s+");
        System.out.println("Command: " + Arrays.toString(args));
        String cmd = args[0];
        args = Arrays.copyOfRange(args, 1, args.length);

        switch (cmd) {
            case "clear": {
                event.getChannel().deleteMessages(event.getChannel().getHistory().retrievePast(100).complete()).queue();
            }
            break;
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        super.onMessageReceived(event);

        String msg = event.getMessage().getContentRaw();

        if (!msg.startsWith(AxlsBot.prefix)) return;

        String[] args = msg.substring(1).split("\\s+");
        System.out.println("Command: " + Arrays.toString(args));
        String cmd = args[0];
        args = Arrays.copyOfRange(args, 1, args.length);

        switch (cmd) {

            case "info": {
                EmbedBuilder info = new EmbedBuilder();
                info.setTitle("📺 AxlsBot");
                info.setDescription("Completely useless information about a useless bot called 'AxlsBot'.");
                info.setColor(0xf45642);
                info.setAuthor("Symbroson");

                if (event.isFromType(ChannelType.TEXT))
                    info.setFooter("Created by Axl", event.getMember().getUser().getAvatarUrl());

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(info.build()).queue();
                info.clear();
            }
            break;

            case "help": {
                event.getChannel().sendMessage(
                    "`AxlsBot Command list:\n" +
                        "    info:    knows everything\n" +
                        "    help:    shows this help\n" +
                        "    dice:    rolls a dice\n" +
                        "    emo:     applies a regional flair to your message\n" +
                        "    funfact: tells you something you've always wanted to know\n" +
                        "    cookie:  in case you're hungry\n" +
                        "    imnobot: proves that AxlsBot is no robot\n" +
                        "    spam:    dont!\n\n" +
                        "Thanks for using AxlsBot`"
                ).queue();
            }
            break;

            case "dice": {
                int roll = new Random().nextInt(6) + 1;
                event.getChannel().sendMessage(event.getAuthor().getName() + "'s roll: " + roll).queue();
            }
            break;

            case "funfact": {
                StringBuilder result = new StringBuilder();

                try {
                    URL url = new URL("http://www.randomfunfacts.com");

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String line;
                    while ((line = rd.readLine()) != null) result.append(line);
                    rd.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                event.getChannel().sendMessage(result.toString().split("i>")[1].replace("</", "")).queue();
            }
            break;

            case "cookie": {
                EmbedBuilder info = new EmbedBuilder();
                info.setImage(Links.cookie);
                event.getChannel().sendMessage(info.build()).queue();
                info.clear();
            }
            break;

            case "imnobot": {
                EmbedBuilder info = new EmbedBuilder();
                info.setImage(Links.nobot);
                event.getChannel().sendMessage(info.build()).queue();
                info.clear();
            }
            break;

            case "spam": {
                for (int i = 0; i < 200; i++) {
                    event.getChannel().sendMessage("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.").queue();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;

            case util.SECRETS.CHACTIVITY: {
                if (args.length > 1)
                    core.AxlsBot.jda.getPresence().setActivity(Activity.playing(String.join(" ", args)));
            }

            case "emo": {
                final String[] nums = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
                StringBuilder text = new StringBuilder();

                for (char c : String.join(" ", args).toLowerCase().toCharArray()) {
                    if (Character.isDigit(c)) text.append(":").append(nums[c - '0']).append(":");
                    else if (Character.isLetter(c)) text.append(":regional_indicator_").append(c).append(":");
                    else if (c == ' ') text.append("       ");
                    else if (c == '\t') text.append("                            ");
                    else if (c == '?') text.append(":grey_question:");
                    else if (c == '!') text.append(":grey_exclamation:");
                    else if (c == '‽') text.append(":interrobang:");
                    else text.append(c);
                }

                if (event.isFromType(ChannelType.TEXT))
                    event.getMessage().delete().queue();

                event.getChannel().sendMessage(
                    event.getAuthor().getAsTag() + ": " +
                        text.toString().replace(":grey_question::grey_exclamation:", ":interrobang:")).queue();
            }
            break;

            case "chess": {
                new Games.Chess(event).start();
            }
        }
    }
}
