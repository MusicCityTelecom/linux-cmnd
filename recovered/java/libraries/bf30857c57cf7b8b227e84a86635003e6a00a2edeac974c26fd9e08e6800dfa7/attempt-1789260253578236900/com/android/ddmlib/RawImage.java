/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import java.nio.ByteBuffer;

public final class RawImage {
    public int version;
    public int bpp;
    public int colorSpace;
    public int size;
    public int width;
    public int height;
    public int red_offset;
    public int red_length;
    public int blue_offset;
    public int blue_length;
    public int green_offset;
    public int green_length;
    public int alpha_offset;
    public int alpha_length;
    public byte[] data;
    public static final int COLOR_SPACE_UNKNOWN = 0;
    public static final int COLOR_SPACE_SRGB = 1;
    public static final int COLOR_SPACE_DISPLAY_P3 = 2;

    public boolean readHeader(int version, ByteBuffer buf) {
        this.version = version;
        if (version == 16) {
            this.bpp = 16;
            this.size = buf.getInt();
            this.width = buf.getInt();
            this.height = buf.getInt();
            this.red_offset = 11;
            this.red_length = 5;
            this.green_offset = 5;
            this.green_length = 6;
            this.blue_offset = 0;
            this.blue_length = 5;
            this.alpha_offset = 0;
            this.alpha_length = 0;
        } else if (version == 1 || version == 2) {
            this.bpp = buf.getInt();
            if (version == 2) {
                this.colorSpace = buf.getInt();
            }
            this.size = buf.getInt();
            this.width = buf.getInt();
            this.height = buf.getInt();
            this.red_offset = buf.getInt();
            this.red_length = buf.getInt();
            this.blue_offset = buf.getInt();
            this.blue_length = buf.getInt();
            this.green_offset = buf.getInt();
            this.green_length = buf.getInt();
            this.alpha_offset = buf.getInt();
            this.alpha_length = buf.getInt();
        } else {
            return false;
        }
        return true;
    }

    public int getRedMask() {
        return this.getMask(this.red_length, this.red_offset);
    }

    public int getGreenMask() {
        return this.getMask(this.green_length, this.green_offset);
    }

    public int getBlueMask() {
        return this.getMask(this.blue_length, this.blue_offset);
    }

    public static int getHeaderSize(int version) {
        switch (version) {
            case 16: {
                return 3;
            }
            case 1: {
                return 12;
            }
            case 2: {
                return 13;
            }
        }
        return 0;
    }

    public RawImage getRotated() {
        RawImage rotated = new RawImage();
        rotated.version = this.version;
        rotated.bpp = this.bpp;
        rotated.colorSpace = this.colorSpace;
        rotated.size = this.size;
        rotated.red_offset = this.red_offset;
        rotated.red_length = this.red_length;
        rotated.blue_offset = this.blue_offset;
        rotated.blue_length = this.blue_length;
        rotated.green_offset = this.green_offset;
        rotated.green_length = this.green_length;
        rotated.alpha_offset = this.alpha_offset;
        rotated.alpha_length = this.alpha_length;
        rotated.width = this.height;
        rotated.height = this.width;
        int count = this.data.length;
        rotated.data = new byte[count];
        int byteCount = this.bpp >> 3;
        int w3 = this.width;
        int h2 = this.height;
        for (int y3 = 0; y3 < h2; ++y3) {
            for (int x3 = 0; x3 < w3; ++x3) {
                System.arraycopy(this.data, (y3 * w3 + x3) * byteCount, rotated.data, ((w3 - x3 - 1) * h2 + y3) * byteCount, byteCount);
            }
        }
        return rotated;
    }

    public int getARGB(int index) {
        int a2;
        int b2;
        int g2;
        int r3;
        if (this.bpp == 16) {
            int value = this.data[index] & 0xFF;
            r3 = ((value |= this.data[index + 1] << 8 & 0xFF00) >>> 11 & 0x1F) * 255 / 31;
            g2 = (value >>> 5 & 0x3F) * 255 / 63;
            b2 = (value & 0x1F) * 255 / 31;
            a2 = 255;
        } else if (this.bpp == 32) {
            int value = this.data[index] & 0xFF;
            value |= (this.data[index + 1] & 0xFF) << 8;
            value |= (this.data[index + 2] & 0xFF) << 16;
            r3 = ((value |= (this.data[index + 3] & 0xFF) << 24) >>> this.red_offset & RawImage.getMask(this.red_length)) << 8 - this.red_length;
            g2 = (value >>> this.green_offset & RawImage.getMask(this.green_length)) << 8 - this.green_length;
            b2 = (value >>> this.blue_offset & RawImage.getMask(this.blue_length)) << 8 - this.blue_length;
            a2 = (value >>> this.alpha_offset & RawImage.getMask(this.alpha_length)) << 8 - this.alpha_length;
        } else {
            throw new UnsupportedOperationException("RawImage.getARGB(int) only works in 16 and 32 bit mode.");
        }
        return a2 << 24 | r3 << 16 | g2 << 8 | b2;
    }

    private int getMask(int length, int offset) {
        int res = RawImage.getMask(length) << offset;
        if (this.bpp == 32) {
            return Integer.reverseBytes(res);
        }
        return res;
    }

    private static int getMask(int length) {
        return (1 << length) - 1;
    }
}

