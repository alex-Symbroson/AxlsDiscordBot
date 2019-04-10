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
import java.util.Date;
import java.util.Random;

public class Commands extends ListenerAdapter {

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        super.onPrivateMessageReceived(event);

        System.out.println(
            "\033[0;2m" + Main.dateFormat.format(new Date()) +
                "\033[1;30mPrivate\033[2;90m::\033[0;2m" +
                event.getAuthor().getAsTag() + "\033[2;90m(\033[3;2m" +
                event.getAuthor().getId() + "\033[2;90m)\033[0;37m: " +
                event.getMessage().getContentRaw()
        );

        String msg = event.getMessage().getContentRaw();

        String[] args = msg.split("\\s+");
        if (!args[0].equalsIgnoreCase(Main.prefix)) return;

        String cmd = args[1];
        args = Arrays.copyOfRange(args, 2, args.length);

        switch (cmd) {
            case "spam": {
                for (int i = 0; i < 10; i++) {
                    event.getChannel().sendMessage("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.").queue();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        super.onGuildMessageReceived(event);

        System.out.println(
            "\033[0;2m" + Main.dateFormat.format(new Date()) +
                event.getGuild().getName() + "\033[2;90m(\033[3;2m" +
                event.getGuild().getId() + "\033[2;90m)::\033[0;2m" +
                event.getChannel().getName() + "\033[2;90m(\033[3;2m" +
                event.getChannel().getId() + "\033[2;90m)::\033[0;2m" +
                event.getAuthor().getAsTag() + "\033[2;90m(\033[3;2m" +
                event.getAuthor().getId() + "\033[2;90m)\033[0;37m: " +
                event.getMessage().getContentRaw()
        );

        String msg = event.getMessage().getContentRaw();

        String[] args = msg.split("\\s+");
        if (!args[0].equalsIgnoreCase(Main.prefix)) return;

        String cmd = args[1];
        args = Arrays.copyOfRange(args, 2, args.length);

        switch (cmd) {
            case "clear": {
                event.getChannel().deleteMessages(event.getChannel().getHistory().retrievePast(100).complete()).queue();
            }
            break;

            case "spam": {
                if (!event.getAuthor().getName().equals("Symbroson")) {
                    event.getChannel().sendMessage("You suck " + event.getAuthor().getName() + "!").queue();
                    return;
                }

                for (int i = 0; i < 10; i++) {
                    event.getChannel().sendMessage("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.").queue();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        super.onMessageReceived(event);

        String msg = event.getMessage().getContentRaw();

        String[] args = msg.split("\\s+");
        if (!args[0].equalsIgnoreCase(Main.prefix)) return;
        System.out.println("Command: " + Arrays.toString(args));

        String cmd = args[1];
        args = Arrays.copyOfRange(args, 2, args.length);

        event.getChannel().sendTyping().complete();

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
                    "**AxlsBot Command list**\n" +
                        "*command prefix: " + Main.prefix + "*\n\n" +
                        "**info**: knows everything\n" +
                        "**help**: shows this help\n" +
                        "**dice**: rolls a dice\n" +
                        "**emo** *text*: applies a regional flair to your message\n" +
                        "**funfact**: tells you something you've always wanted to know\n" +
                        "**cookie**: in case you're hungry\n" +
                        "**chess**: shows a fancy chess game\n" +
                        "**imnobot**: proves that AxlsBot is no robot\n" +
                        "**spam**: dont!\n" +
                        "**encrypt** *__method__* *key* *text*: encrypt message using\n" +
                        "    ***caesar*** *char* *text*\n\n" +
                        "Thanks for using AxlsBot"
                ).queue();
            }
            break;

            case "roll":
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
                info.setImage("https://raw.githubusercontent.com/alex-Symbroson/AxlsDiscordBot/master/res/nobot.png");
                event.getChannel().sendMessage(info.build()).queue();
                info.clear();
            }
            break;

            case util.SECRETS.CHACTIVITY: {
                if (args.length > 1)
                    Main.jda.getPresence().setActivity(Activity.playing(String.join(" ", args)));
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
                Games.Chess.generatePlayground();
                Games.Chess game = new Games.Chess(event);
                game.start();
                game.stop();
            }
            break;

            case "encrypt": {
                final String dec = "abcdefghijklmnopqrstuvwxyz";

                switch (args[0].toLowerCase()) {
                    case "caesar": {
                        System.out.println(Arrays.toString(args));
                        int key = args[1].charAt(0);
                        if (Character.isLetter(key)) key = Character.toLowerCase(key) - 'a';
                        else if (Character.isDigit(key)) key -= '0';

                        StringBuilder enc = new StringBuilder();
                        for (char c : String.join(" ", Arrays.copyOfRange(args, 2, args.length)).toCharArray()) {
                            int p = dec.indexOf(Character.toLowerCase(c));
                            char nc = c;
                            if (p != -1) {
                                nc = dec.charAt((p + key + dec.length()) % dec.length());
                                if (Character.isUpperCase(c)) nc = Character.toUpperCase(nc);
                            }
                            enc.append(nc);
                        }
                        event.getChannel().sendMessage(enc).queue();
                    }
                    break;
                }
            }
            break;

            case "decrypt": {
                final String dec = "abcdefghijklmnopqrstuvwxyz";

                switch (args[0].toLowerCase()) {
                    case "caesar": {
                        System.out.println(Arrays.toString(args));
                        int key = args[1].charAt(0);
                        if (Character.isLetter(key)) key = Character.toLowerCase(key) - 'a';
                        else if (Character.isDigit(key)) key -= '0';

                        StringBuilder enc = new StringBuilder();
                        for (char c : String.join(" ", Arrays.copyOfRange(args, 2, args.length)).toCharArray()) {
                            int p = dec.indexOf(Character.toLowerCase(c));
                            char nc = c;
                            if (p != -1) {
                                nc = dec.charAt((p - key + dec.length()) % dec.length());
                                if (Character.isUpperCase(c)) nc = Character.toUpperCase(nc);
                            }
                            enc.append(nc);
                        }
                        event.getChannel().sendMessage(enc).queue();
                    }
                    break;
                }
            }
            break;

            // commands handled elsewhere
            case "clear":
                break;
            case "spam":
                break;

            default:
                event.getChannel().sendMessage("AxlsBot doesn't know '" + cmd + "'! He's too stupid for that.").queue();
        }
    }

    public class Links {
        static final String
            cookie = "https://previews.123rf.com/images/memoangeles/memoangeles1506/memoangeles150600002/40818650-chocolate-chip-cookie-vektor-clipart-illustration-mit-einfachen-farbverl%C3%A4ufen-alle-in-einer-einzigen-s.jpg";
    }
}
