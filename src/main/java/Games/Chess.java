package Games;

import core.Main;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.io.FileUtils;

import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Chess {

    // static values

    // constants
    private static final int WHITE = 0, BLACK = 10;
    private static final int WIDTH = 400, HEIGHT = 400, W = 8, H = 8;
    private static final int KING = 0, QUEEN = 1, BISHOP = 2, KNIGHT = 3, ROOK = 4, PAWN = 5;
    private static final File fileField = new File(String.format("res/field%dx%d.png", WIDTH, HEIGHT));

    // resources
    private static BufferedImage bufFigs, bufField;
    private static Map<Integer, BufferedImage> sprites = new HashMap<Integer, BufferedImage>() {};

    private final Figure[] figures;
    private final String gameID = UUID.randomUUID().toString();
    private final File fileGame = new File("data/" + gameID + "/game.png");


    static {
        try {
            bufFigs = ImageIO.read(new File("res/chessfigs_600x200.png"));
            if(!fileField.exists()) generatePlayground();
            bufField = ImageIO.read(fileField);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // non static values
    private BufferedImage buf;
    private Graphics2D img;
    private MessageChannel channel;

    // initialization code
    public Chess(MessageReceivedEvent event) {

        fileGame.getParentFile().mkdirs();
        channel = event.getChannel();

        // init drawing buffer
        buf = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        img = buf.createGraphics();

        // init figures
        figures = new Figure[]{
            new Figure(ROOK, WHITE, 0, 7, true),
            new Figure(KNIGHT, WHITE, 1, 7, true),
            new Figure(BISHOP, WHITE, 2, 7, true),
            new Figure(QUEEN, WHITE, 3, 7, true),
            new Figure(KING, WHITE, 4, 7, true),
            new Figure(BISHOP, WHITE, 5, 7, true),
            new Figure(KNIGHT, WHITE, 6, 7, true),
            new Figure(ROOK, WHITE, 7, 7, true),

            new Figure(PAWN, WHITE, 0, 6, true),
            new Figure(PAWN, WHITE, 1, 6, true),
            new Figure(PAWN, WHITE, 2, 6, true),
            new Figure(PAWN, WHITE, 3, 6, true),
            new Figure(PAWN, WHITE, 4, 6, true),
            new Figure(PAWN, WHITE, 5, 6, true),
            new Figure(PAWN, WHITE, 6, 6, true),
            new Figure(PAWN, WHITE, 7, 6, true),

            new Figure(ROOK, BLACK, 0, 0, true),
            new Figure(KNIGHT, BLACK, 1, 0, true),
            new Figure(BISHOP, BLACK, 2, 0, true),
            new Figure(QUEEN, BLACK, 3, 0, true),
            new Figure(KING, BLACK, 4, 0, true),
            new Figure(BISHOP, BLACK, 5, 0, true),
            new Figure(KNIGHT, BLACK, 6, 0, true),
            new Figure(ROOK, BLACK, 7, 0, true),

            new Figure(PAWN, BLACK, 0, 1, true),
            new Figure(PAWN, BLACK, 1, 1, true),
            new Figure(PAWN, BLACK, 2, 1, true),
            new Figure(PAWN, BLACK, 3, 1, true),
            new Figure(PAWN, BLACK, 4, 1, true),
            new Figure(PAWN, BLACK, 5, 1, true),
            new Figure(PAWN, BLACK, 6, 1, true),
            new Figure(PAWN, BLACK, 7, 1, true),
        };
    }

    // return sprite
    private static BufferedImage getFigure(int figure) {
        final int fw = bufFigs.getWidth() / 6, fh = bufFigs.getHeight() / 2;

        if (!sprites.containsKey(figure))
            sprites.put(figure, bufFigs.getSubimage((figure % 10) * fw, (figure / 10) * fh, fw, fh));
        return sprites.get(figure);
    }

    // generate new field
    public static void generatePlayground() {
        final int tw = WIDTH / W, th = HEIGHT / H;
        BufferedImage field = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D img = field.createGraphics();

        // chess field
        img.setColor(new Color(30, 30, 30));
        img.fillRect(0, 0, WIDTH, HEIGHT);
        img.setColor(Color.WHITE);

        for (int i = 0; i < W * H; i += 2)
            img.fillRoundRect(tw * ((i + i / H) % W), th * (i / H), tw, th, 10, 10);

        Main.saveCompressed(field, fileField);
    }


    private void render() {
        final int tw = WIDTH / W, th = HEIGHT / H;

        img.drawImage(bufField, 0, 0, WIDTH, HEIGHT, null);

        for (Figure f : figures)
            if (f.alive)
                img.drawImage(f.img, f.x * tw, f.y * th, tw, th, null);

        Main.saveCompressed(buf, fileGame);
    }

    public void start() {
        render();
        channel.sendFile(fileGame).queue();
    }

    public void stop() {
        try {
            FileUtils.deleteDirectory(new File("data/" + gameID));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onCommand(String[] command, MessageReceivedEvent event) {
        String cmd = command[0];
        String[] args = Arrays.copyOfRange(command, 1, command.length);
    }

    // figure container
    private static class Figure {
        BufferedImage img;
        boolean alive;
        int x, y;

        Figure(int figure, int color, int x, int y, boolean alive) {
            this.img = getFigure(color + figure);
            this.alive = alive;
            this.x = x;
            this.y = y;
        }
    }
}
