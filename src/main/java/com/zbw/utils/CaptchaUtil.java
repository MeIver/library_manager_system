package com.zbw.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class CaptchaUtil {

    private static final int WIDTH = 120;
    private static final int HEIGHT = 40;
    private static final int CODE_LENGTH = 4;
    private static final String CODE_CHARS = "23456789ABCDEFGHJKMNPQRSTUVWXYZ";

    public static BufferedImage generateCaptchaImage(String captchaCode) {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        Random random = new Random();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        Font font = new Font("Arial", Font.BOLD, 28);
        g.setFont(font);

        for (int i = 0; i < CODE_LENGTH; i++) {
            g.setColor(new Color(random.nextInt(150), random.nextInt(150), random.nextInt(150)));
            g.drawString(String.valueOf(captchaCode.charAt(i)), 20 + i * 25, 30);
        }

        for (int i = 0; i < 10; i++) {
            g.setColor(new Color(random.nextInt(200), random.nextInt(200), random.nextInt(200)));
            g.drawLine(random.nextInt(WIDTH), random.nextInt(HEIGHT), 
                       random.nextInt(WIDTH), random.nextInt(HEIGHT));
        }

        g.dispose();
        return image;
    }

    public static String generateRandomCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(CODE_CHARS.charAt(random.nextInt(CODE_CHARS.length())));
        }
        return sb.toString();
    }
}
