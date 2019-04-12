package core;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.imageio.ImageIO;
import javax.security.auth.login.LoginException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class Main {
    public static JDA jda;
    public static String prefix = "axl";
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");

    // Main method
    public static void main(String[] args) throws LoginException {

        jda = new JDABuilder(AccountType.BOT).setToken(util.SECRETS.TOKEN).build();
        jda.getPresence().setActivity(Activity.playing("Nichts. Langweiler."));
        jda.getPresence().setStatus(OnlineStatus.ONLINE);
        jda.setAutoReconnect(true);
        jda.addEventListener(new Commands());
    }

    private static long tStart = 0, tStep = 0, tDiff = 0;

    public static void tic() {
        tStart = tStep = System.nanoTime();
    }

    public static void toc() {
        long t = System.nanoTime();
        tDiff = t - tStep;
        System.out.println(String.format("dtime: %dus", tDiff / 1000));
        tStep = t;
    }

    public static void saveCompressed(BufferedImage img, File fil) {

        try {
            String path = fil.getPath();
            ImageIO.write(img, "bmp", new File(path + ".bmp"));
            Process p = Runtime.getRuntime().exec("convert " + path + ".bmp " + path);
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
