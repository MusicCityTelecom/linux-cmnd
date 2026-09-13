/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.env.Environment
 *  org.springframework.core.io.Resource
 *  org.springframework.core.log.LogMessage
 *  org.springframework.util.Assert
 */
package org.springframework.boot;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.ansi.AnsiBackground;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiColors;
import org.springframework.boot.ansi.AnsiElement;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.log.LogMessage;
import org.springframework.util.Assert;

public class ImageBanner
implements Banner {
    private static final String PROPERTY_PREFIX = "spring.banner.image.";
    private static final Log logger = LogFactory.getLog(ImageBanner.class);
    private static final double[] RGB_WEIGHT = new double[]{0.2126, 0.7152, 0.0722};
    private final Resource image;

    public ImageBanner(Resource image) {
        Assert.notNull((Object)image, (String)"Image must not be null");
        Assert.isTrue((boolean)image.exists(), (String)"Image must exist");
        this.image = image;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        String headless = System.getProperty("java.awt.headless");
        try {
            System.setProperty("java.awt.headless", "true");
            this.printBanner(environment, out);
        }
        catch (Throwable ex) {
            logger.warn((Object)LogMessage.format((String)"Image banner not printable: %s (%s: '%s')", (Object)this.image, ex.getClass(), (Object)ex.getMessage()));
            logger.debug((Object)"Image banner printing failure", ex);
        }
        finally {
            if (headless == null) {
                System.clearProperty("java.awt.headless");
            } else {
                System.setProperty("java.awt.headless", headless);
            }
        }
    }

    private void printBanner(Environment environment, PrintStream out) throws IOException {
        int width = this.getProperty(environment, "width", Integer.class, 76);
        int height = this.getProperty(environment, "height", Integer.class, 0);
        int margin = this.getProperty(environment, "margin", Integer.class, 2);
        boolean invert = this.getProperty(environment, "invert", Boolean.class, false);
        AnsiColors.BitDepth bitDepth = this.getBitDepthProperty(environment);
        PixelMode pixelMode = this.getPixelModeProperty(environment);
        Frame[] frames = this.readFrames(width, height);
        for (int i = 0; i < frames.length; ++i) {
            if (i > 0) {
                this.resetCursor(frames[i - 1].getImage(), out);
            }
            this.printBanner(frames[i].getImage(), margin, invert, bitDepth, pixelMode, out);
            this.sleep(frames[i].getDelayTime());
        }
    }

    private AnsiColors.BitDepth getBitDepthProperty(Environment environment) {
        Integer bitDepth = this.getProperty(environment, "bitdepth", Integer.class, null);
        return bitDepth != null ? AnsiColors.BitDepth.of(bitDepth) : AnsiColors.BitDepth.FOUR;
    }

    private PixelMode getPixelModeProperty(Environment environment) {
        String pixelMode = this.getProperty(environment, "pixelmode", String.class, null);
        return pixelMode != null ? PixelMode.valueOf(pixelMode.trim().toUpperCase()) : PixelMode.TEXT;
    }

    private <T> T getProperty(Environment environment, String name, Class<T> targetType, T defaultValue) {
        return (T)environment.getProperty(PROPERTY_PREFIX + name, targetType, defaultValue);
    }

    /*
     * Exception decompiling
     */
    private Frame[] readFrames(int width, int height) throws IOException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Frame[] readFrames(int width, int height, ImageInputStream stream) throws IOException {
        Iterator<ImageReader> readers = ImageIO.getImageReaders(stream);
        Assert.state((boolean)readers.hasNext(), (String)"Unable to read image banner source");
        ImageReader reader = readers.next();
        try {
            ImageReadParam readParam = reader.getDefaultReadParam();
            reader.setInput(stream);
            int frameCount = reader.getNumImages(true);
            Frame[] frames = new Frame[frameCount];
            for (int i = 0; i < frameCount; ++i) {
                frames[i] = this.readFrame(width, height, reader, i, readParam);
            }
            Frame[] frameArray = frames;
            return frameArray;
        }
        finally {
            reader.dispose();
        }
    }

    private Frame readFrame(int width, int height, ImageReader reader, int imageIndex, ImageReadParam readParam) throws IOException {
        BufferedImage image = reader.read(imageIndex, readParam);
        BufferedImage resized = this.resizeImage(image, width, height);
        int delayTime = this.getDelayTime(reader, imageIndex);
        return new Frame(resized, delayTime);
    }

    private int getDelayTime(ImageReader reader, int imageIndex) throws IOException {
        IIOMetadata metadata = reader.getImageMetadata(imageIndex);
        IIOMetadataNode root = (IIOMetadataNode)metadata.getAsTree(metadata.getNativeMetadataFormatName());
        IIOMetadataNode extension = ImageBanner.findNode(root, "GraphicControlExtension");
        String attribute = extension != null ? extension.getAttribute("delayTime") : null;
        return attribute != null ? Integer.parseInt(attribute) * 10 : 0;
    }

    private static IIOMetadataNode findNode(IIOMetadataNode rootNode, String nodeName) {
        if (rootNode == null) {
            return null;
        }
        for (int i = 0; i < rootNode.getLength(); ++i) {
            if (!rootNode.item(i).getNodeName().equalsIgnoreCase(nodeName)) continue;
            return (IIOMetadataNode)rootNode.item(i);
        }
        return null;
    }

    private BufferedImage resizeImage(BufferedImage image, int width, int height) {
        if (width < 1) {
            width = 1;
        }
        if (height <= 0) {
            double aspectRatio = (double)width / (double)image.getWidth() * 0.5;
            height = (int)Math.ceil((double)image.getHeight() * aspectRatio);
        }
        BufferedImage resized = new BufferedImage(width, height, 1);
        Image scaled = image.getScaledInstance(width, height, 1);
        resized.getGraphics().drawImage(scaled, 0, 0, null);
        return resized;
    }

    private void resetCursor(BufferedImage image, PrintStream out) {
        int lines = image.getHeight() + 3;
        out.print("\u001b[" + lines + "A\r");
    }

    private void printBanner(BufferedImage image, int margin, boolean invert, AnsiColors.BitDepth bitDepth, PixelMode pixelMode, PrintStream out) {
        AnsiBackground background = invert ? AnsiBackground.BLACK : AnsiBackground.DEFAULT;
        out.print(AnsiOutput.encode(AnsiColor.DEFAULT));
        out.print(AnsiOutput.encode(background));
        out.println();
        out.println();
        AnsiElement lastColor = AnsiColor.DEFAULT;
        AnsiColors colors = new AnsiColors(bitDepth);
        for (int y = 0; y < image.getHeight(); ++y) {
            for (int i = 0; i < margin; ++i) {
                out.print(" ");
            }
            for (int x = 0; x < image.getWidth(); ++x) {
                Color color = new Color(image.getRGB(x, y), false);
                AnsiElement ansiColor = colors.findClosest(color);
                if (ansiColor != lastColor) {
                    out.print(AnsiOutput.encode(ansiColor));
                    lastColor = ansiColor;
                }
                out.print(this.getAsciiPixel(color, invert, pixelMode));
            }
            out.println();
        }
        out.print(AnsiOutput.encode(AnsiColor.DEFAULT));
        out.print(AnsiOutput.encode(AnsiBackground.DEFAULT));
        out.println();
    }

    private char getAsciiPixel(Color color, boolean dark, PixelMode pixelMode) {
        char[] pixels = pixelMode.getPixels();
        int increment = 10 / pixels.length * 10;
        int start = increment * pixels.length;
        double luminance = this.getLuminance(color, dark);
        for (int i = 0; i < pixels.length; ++i) {
            if (!(luminance >= (double)(start - i * increment))) continue;
            return pixels[i];
        }
        return pixels[pixels.length - 1];
    }

    private int getLuminance(Color color, boolean inverse) {
        double luminance = 0.0;
        luminance += this.getLuminance(color.getRed(), inverse, RGB_WEIGHT[0]);
        luminance += this.getLuminance(color.getGreen(), inverse, RGB_WEIGHT[1]);
        return (int)Math.ceil((luminance += this.getLuminance(color.getBlue(), inverse, RGB_WEIGHT[2])) / 255.0 * 100.0);
    }

    private double getLuminance(int component, boolean inverse, double weight) {
        return (double)(inverse ? 255 - component : component) * weight;
    }

    private void sleep(int delay) {
        try {
            Thread.sleep(delay);
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public static enum PixelMode {
        TEXT(' ', '.', '*', ':', 'o', '&', '8', '#', '@'),
        BLOCK(' ', '\u2591', '\u2592', '\u2593', '\u2588');

        private char[] pixels;

        private PixelMode(char ... pixels) {
            this.pixels = pixels;
        }

        char[] getPixels() {
            return this.pixels;
        }
    }

    private static class Frame {
        private final BufferedImage image;
        private final int delayTime;

        Frame(BufferedImage image, int delayTime) {
            this.image = image;
            this.delayTime = delayTime;
        }

        BufferedImage getImage() {
            return this.image;
        }

        int getDelayTime() {
            return this.delayTime;
        }
    }
}

