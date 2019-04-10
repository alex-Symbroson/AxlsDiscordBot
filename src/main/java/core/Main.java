package core;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.imageio.*;
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
        //jda.getPresence().setStatus(OnlineStatus.IDLE);
        jda.getPresence().setActivity(Activity.playing("nichts. Langweiler."));
        jda.getPresence().setStatus(OnlineStatus.ONLINE);
        jda.setAutoReconnect(true);
        jda.addEventListener(new Commands());
    }

    public static void saveCompressed(BufferedImage img, String fmt, File fil){

        try {
            ImageTypeSpecifier type = ImageTypeSpecifier.createFromRenderedImage(img);
            ImageWriter writer = ImageIO.getImageWriters(type, fmt).next();
            ImageWriteParam param = writer.getDefaultWriteParam();

            if (param.canWriteCompressed()) {
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(0.0f);
            }

            writer.setOutput(ImageIO.createImageOutputStream(fil));
            writer.write(null, new IIOImage(img, null, null), param);
            writer.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
