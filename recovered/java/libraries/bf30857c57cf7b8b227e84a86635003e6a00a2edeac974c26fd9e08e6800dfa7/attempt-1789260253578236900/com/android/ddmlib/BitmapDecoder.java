/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.google.common.collect.ImmutableMap;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Map;

public class BitmapDecoder {
    public static final String BITMAP_FQCN = "android.graphics.Bitmap";
    public static final String BITMAP_DRAWABLE_FQCN = "android.graphics.drawable.BitmapDrawable";
    protected static final Map<String, BitmapExtractor> SUPPORTED_FORMATS = ImmutableMap.of("\"ARGB_8888\"", new ARGB8888_BitmapExtractor(), "\"RGB_565\"", new RGB565_BitmapExtractor(), "\"ALPHA_8\"", new ALPHA8_BitmapExtractor());
    private static final int MAX_DIMENSION = 1024;

    public static BufferedImage getBitmap(BitmapDataProvider dataProvider) throws Exception {
        String config = dataProvider.getBitmapConfigName();
        if (config == null) {
            throw new RuntimeException("Unable to determine bitmap configuration");
        }
        BitmapExtractor bitmapExtractor = SUPPORTED_FORMATS.get(config);
        if (bitmapExtractor == null) {
            throw new RuntimeException("Unsupported bitmap configuration: " + config);
        }
        Dimension size = dataProvider.getDimension();
        if (size == null) {
            throw new RuntimeException("Unable to determine image dimensions.");
        }
        if (size.width > 1024 || size.height > 1024) {
            boolean couldDownsize = dataProvider.downsizeBitmap(size);
            if (!couldDownsize) {
                throw new RuntimeException("Unable to create scaled bitmap");
            }
            size = dataProvider.getDimension();
            if (size == null) {
                throw new RuntimeException("Unable to obtained scaled bitmap's dimensions");
            }
        }
        return bitmapExtractor.getImage(size.width, size.height, dataProvider.getPixelBytes(size));
    }

    private static class ALPHA8_BitmapExtractor
    implements BitmapExtractor {
        private ALPHA8_BitmapExtractor() {
        }

        @Override
        public BufferedImage getImage(int width, int height, byte[] rgb) {
            boolean bytesPerPixel = true;
            BufferedImage bufferedImage = new BufferedImage(width, height, 2);
            for (int y3 = 0; y3 < height; ++y3) {
                int stride = y3 * width;
                for (int x3 = 0; x3 < width; ++x3) {
                    int index = stride + x3;
                    byte value = rgb[index];
                    int rgba = value << 24 | 0xFF0000 | 0xFF00 | 0xFF;
                    bufferedImage.setRGB(x3, y3, rgba);
                }
            }
            return bufferedImage;
        }
    }

    private static class RGB565_BitmapExtractor
    implements BitmapExtractor {
        private RGB565_BitmapExtractor() {
        }

        @Override
        public BufferedImage getImage(int width, int height, byte[] rgb) {
            int bytesPerPixel = 2;
            BufferedImage bufferedImage = new BufferedImage(width, height, 2);
            for (int y3 = 0; y3 < height; ++y3) {
                int stride = y3 * width;
                for (int x3 = 0; x3 < width; ++x3) {
                    int index = (stride + x3) * bytesPerPixel;
                    int value = rgb[index] & 0xFF | rgb[index + 1] << 8 & 0xFF00;
                    int r3 = (value >>> 11 & 0x1F) * 255 / 31;
                    int g2 = (value >>> 5 & 0x3F) * 255 / 63;
                    int b2 = (value & 0x1F) * 255 / 31;
                    int a2 = 255;
                    int rgba = a2 << 24 | r3 << 16 | g2 << 8 | b2;
                    bufferedImage.setRGB(x3, y3, rgba);
                }
            }
            return bufferedImage;
        }
    }

    private static class ARGB8888_BitmapExtractor
    implements BitmapExtractor {
        private ARGB8888_BitmapExtractor() {
        }

        @Override
        public BufferedImage getImage(int width, int height, byte[] rgba) {
            BufferedImage bufferedImage = new BufferedImage(width, height, 2);
            for (int y3 = 0; y3 < height; ++y3) {
                int stride = y3 * width;
                for (int x3 = 0; x3 < width; ++x3) {
                    int i2 = (stride + x3) * 4;
                    long rgb = 0L;
                    rgb |= ((long)rgba[i2] & 0xFFL) << 16;
                    rgb |= ((long)rgba[i2 + 1] & 0xFFL) << 8;
                    rgb |= (long)rgba[i2 + 2] & 0xFFL;
                    bufferedImage.setRGB(x3, y3, (int)((rgb |= ((long)rgba[i2 + 3] & 0xFFL) << 24) & 0xFFFFFFFFL));
                }
            }
            return bufferedImage;
        }
    }

    private static interface BitmapExtractor {
        public BufferedImage getImage(int var1, int var2, byte[] var3);
    }

    public static interface BitmapDataProvider {
        public String getBitmapConfigName() throws Exception;

        public Dimension getDimension() throws Exception;

        public boolean downsizeBitmap(Dimension var1) throws Exception;

        public byte[] getPixelBytes(Dimension var1) throws Exception;
    }
}

