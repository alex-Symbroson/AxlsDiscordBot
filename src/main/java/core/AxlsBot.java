package core;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.imageio.ImageIO;
import javax.security.auth.login.LoginException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AxlsBot {
    public static JDA jda;
    public static String prefix = "!";

    // Main method
    public static void main(String[] args) throws LoginException {
        jda = new JDABuilder(AccountType.BOT).setToken(util.SECRETS.TOKEN).build();
        //jda.getPresence().setStatus(OnlineStatus.IDLE);
        jda.getPresence().setActivity(Activity.playing("nichts. Langweiler."));
        jda.getPresence().setStatus(OnlineStatus.ONLINE);

        jda.addEventListener(new Commands());
    }
}
