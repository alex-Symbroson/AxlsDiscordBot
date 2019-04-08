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

    public static class ChessFigs {
        static final int King = 0, Queen = 1, Bishop = 2, Knight = 3, Rook = 4, Pawn = 5;
        static final int WHITE = 0, BLACK = 1;
        static BufferedImage figs;

        static {
            try {
                figs = ImageIO.read(new File("chessfigs_600x200.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        static final int fw = figs.getWidth() / 6, fh = figs.getHeight() / 2;
    }

    public static class Figure {
        int figure, color, x, y;
        boolean alive;

        Figure(int figure, int color, int x, int y, boolean alive) {
            this.figure = figure;
            this.color = color;
            this.x = x;
            this.y = y;
            this.alive = alive;
        }
    }

    // Main method
    public static void main(String[] args) throws LoginException {
        jda = new JDABuilder(AccountType.BOT).setToken(util.SECRETS.TOKEN).build();
        //jda.getPresence().setStatus(OnlineStatus.IDLE);
        jda.getPresence().setActivity(Activity.playing("nichts. Langweiler."));
        jda.getPresence().setStatus(OnlineStatus.ONLINE);

        jda.addEventListener(new Commands());
    }

    public static BufferedImage getFigure(int figure, int color) {
        BufferedImage fig = ChessFigs.figs.getSubimage(figure * ChessFigs.fw, color * ChessFigs.fh, ChessFigs.fw, ChessFigs.fh);
        fig.getGraphics().setColor(new Color(0x0000000, false));
        return fig;
    }
}
