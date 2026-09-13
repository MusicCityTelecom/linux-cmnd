/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.boot.ansi;

import java.awt.Color;
import java.awt.color.ColorSpace;
import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.boot.ansi.Ansi8BitColor;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiElement;
import org.springframework.util.Assert;

public final class AnsiColors {
    private static final Map<AnsiElement, LabColor> ANSI_COLOR_MAP;
    private static final int[] ANSI_8BIT_COLOR_CODE_LOOKUP;
    private final Map<AnsiElement, LabColor> lookup;

    public AnsiColors(BitDepth bitDepth) {
        this.lookup = this.getLookup(bitDepth);
    }

    private Map<AnsiElement, LabColor> getLookup(BitDepth bitDepth) {
        if (bitDepth == BitDepth.EIGHT) {
            LinkedHashMap<Ansi8BitColor, LabColor> lookup = new LinkedHashMap<Ansi8BitColor, LabColor>();
            for (int i = 0; i < ANSI_8BIT_COLOR_CODE_LOOKUP.length; ++i) {
                lookup.put(Ansi8BitColor.foreground(i), new LabColor(ANSI_8BIT_COLOR_CODE_LOOKUP[i]));
            }
            return Collections.unmodifiableMap(lookup);
        }
        return ANSI_COLOR_MAP;
    }

    public AnsiElement findClosest(Color color) {
        return this.findClosest(new LabColor(color));
    }

    private AnsiElement findClosest(LabColor color) {
        AnsiElement closest = null;
        double closestDistance = 3.4028234663852886E38;
        for (Map.Entry<AnsiElement, LabColor> entry : this.lookup.entrySet()) {
            double candidateDistance = color.getDistance(entry.getValue());
            if (closest != null && !(candidateDistance < closestDistance)) continue;
            closestDistance = candidateDistance;
            closest = entry.getKey();
        }
        return closest;
    }

    static {
        EnumMap<AnsiColor, LabColor> colorMap = new EnumMap<AnsiColor, LabColor>(AnsiColor.class);
        colorMap.put(AnsiColor.BLACK, new LabColor(0));
        colorMap.put(AnsiColor.RED, new LabColor(0xAA0000));
        colorMap.put(AnsiColor.GREEN, new LabColor(43520));
        colorMap.put(AnsiColor.YELLOW, new LabColor(0xAA5500));
        colorMap.put(AnsiColor.BLUE, new LabColor(170));
        colorMap.put(AnsiColor.MAGENTA, new LabColor(0xAA00AA));
        colorMap.put(AnsiColor.CYAN, new LabColor(43690));
        colorMap.put(AnsiColor.WHITE, new LabColor(0xAAAAAA));
        colorMap.put(AnsiColor.BRIGHT_BLACK, new LabColor(0x555555));
        colorMap.put(AnsiColor.BRIGHT_RED, new LabColor(0xFF5555));
        colorMap.put(AnsiColor.BRIGHT_GREEN, new LabColor(0x55FF00));
        colorMap.put(AnsiColor.BRIGHT_YELLOW, new LabColor(0xFFFF55));
        colorMap.put(AnsiColor.BRIGHT_BLUE, new LabColor(0x5555FF));
        colorMap.put(AnsiColor.BRIGHT_MAGENTA, new LabColor(0xFF55FF));
        colorMap.put(AnsiColor.BRIGHT_CYAN, new LabColor(0x55FFFF));
        colorMap.put(AnsiColor.BRIGHT_WHITE, new LabColor(0xFFFFFF));
        ANSI_COLOR_MAP = Collections.unmodifiableMap(colorMap);
        ANSI_8BIT_COLOR_CODE_LOOKUP = new int[]{0, 0x800000, 32768, 0x808000, 128, 0x800080, 32896, 0xC0C0C0, 0x808080, 0xFF0000, 65280, 0xFFFF00, 255, 0xFF00FF, 65535, 0xFFFFFF, 0, 95, 135, 175, 215, 255, 24320, 24415, 24455, 24495, 24535, 24575, 34560, 34655, 34695, 34735, 34775, 34815, 44800, 44895, 44935, 44975, 45015, 45055, 55040, 55135, 55175, 55215, 55255, 55295, 65280, 65375, 65415, 65455, 65495, 65535, 0x5F0000, 0x5F005F, 6226055, 6226095, 6226135, 0x5F00FF, 0x5F5F00, 0x5F5F5F, 6250375, 0x5F5FAF, 6250455, 0x5F5FFF, 6260480, 6260575, 6260615, 6260655, 6260695, 6260735, 6270720, 0x5FAF5F, 6270855, 0x5FAFAF, 6270935, 0x5FAFFF, 6280960, 6281055, 6281095, 6281135, 6281175, 6281215, 0x5FFF00, 0x5FFF5F, 6291335, 0x5FFFAF, 6291415, 0x5FFFFF, 0x870000, 8847455, 0x870087, 8847535, 8847575, 8847615, 8871680, 8871775, 8871815, 8871855, 8871895, 8871935, 0x878700, 8882015, 0x878787, 8882095, 0x8787D7, 0x8787FF, 8892160, 8892255, 8892295, 8892335, 8892375, 8892415, 8902400, 8902495, 0x87D787, 8902575, 0x87D7D7, 8902655, 8912640, 8912735, 0x87FF87, 8912815, 8912855, 0x87FFFF, 0xAF0000, 11468895, 11468935, 0xAF00AF, 11469015, 0xAF00FF, 11493120, 0xAF5F5F, 11493255, 0xAF5FAF, 11493335, 0xAF5FFF, 11503360, 11503455, 11503495, 11503535, 11503575, 11503615, 0xAFAF00, 0xAFAF5F, 11513735, 0xAFAFAF, 11513815, 0xAFAFFF, 11523840, 11523935, 11523975, 11524015, 11524055, 11524095, 0xAFFF00, 0xAFFF5F, 11534215, 0xAFFFAF, 11534295, 0xAFFFFF, 0xD70000, 14090335, 14090375, 14090415, 0xD700D7, 14090495, 14114560, 14114655, 14114695, 14114735, 14114775, 14114815, 14124800, 14124895, 0xD78787, 14124975, 0xD787D7, 14125055, 14135040, 14135135, 14135175, 14135215, 14135255, 14135295, 0xD7D700, 14145375, 0xD7D787, 14145455, 0xD7D7D7, 0xD7D7FF, 14155520, 14155615, 14155655, 14155695, 0xD7FFD7, 0xD7FFFF, 0xFF0000, 0xFF005F, 16711815, 0xFF00AF, 16711895, 0xFF00FF, 0xFF5F00, 0xFF5F5F, 16736135, 0xFF5FAF, 16736215, 0xFF5FFF, 16746240, 16746335, 0xFF8787, 16746415, 16746455, 0xFF87FF, 0xFFAF00, 0xFFAF5F, 16756615, 0xFFAFAF, 16756695, 0xFFAFFF, 16766720, 16766815, 16766855, 16766895, 0xFFD7D7, 0xFFD7FF, 0xFFFF00, 0xFFFF5F, 0xFFFF87, 0xFFFFAF, 0xFFFFD7, 0xFFFFFF, 526344, 0x121212, 0x1C1C1C, 0x262626, 0x303030, 0x3A3A3A, 0x444444, 0x4E4E4E, 0x585858, 0x626262, 0x6C6C6C, 0x767676, 0x808080, 0x8A8A8A, 0x949494, 0x9E9E9E, 0xA8A8A8, 0xB2B2B2, 0xBCBCBC, 0xC6C6C6, 0xD0D0D0, 0xDADADA, 0xE4E4E4, 0xEEEEEE};
    }

    public static enum BitDepth {
        FOUR(4),
        EIGHT(8);

        private final int bits;

        private BitDepth(int bits) {
            this.bits = bits;
        }

        public static BitDepth of(int bits) {
            for (BitDepth candidate : BitDepth.values()) {
                if (candidate.bits != bits) continue;
                return candidate;
            }
            throw new IllegalArgumentException("Unsupported ANSI bit depth '" + bits + "'");
        }
    }

    private static final class LabColor {
        private static final ColorSpace XYZ_COLOR_SPACE = ColorSpace.getInstance(1001);
        private final double l;
        private final double a;
        private final double b;

        LabColor(Integer rgb) {
            this(rgb != null ? new Color(rgb) : null);
        }

        LabColor(Color color) {
            Assert.notNull((Object)color, (String)"Color must not be null");
            float[] lab = this.fromXyz(color.getColorComponents(XYZ_COLOR_SPACE, null));
            this.l = lab[0];
            this.a = lab[1];
            this.b = lab[2];
        }

        private float[] fromXyz(float[] xyz) {
            return this.fromXyz(xyz[0], xyz[1], xyz[2]);
        }

        private float[] fromXyz(float x, float y, float z) {
            double l = (this.f(y) - 16.0) * 116.0;
            double a = (this.f(x) - this.f(y)) * 500.0;
            double b = (this.f(y) - this.f(z)) * 200.0;
            return new float[]{(float)l, (float)a, (float)b};
        }

        private double f(double t) {
            return t > 0.008856451679035631 ? Math.cbrt(t) : 0.3333333333333333 * Math.pow(4.833333333333333, 2.0) * t + 0.13793103448275862;
        }

        double getDistance(LabColor other) {
            double c1 = Math.sqrt(this.a * this.a + this.b * this.b);
            double deltaC = c1 - Math.sqrt(other.a * other.a + other.b * other.b);
            double deltaA = this.a - other.a;
            double deltaB = this.b - other.b;
            double deltaH = Math.sqrt(Math.max(0.0, deltaA * deltaA + deltaB * deltaB - deltaC * deltaC));
            return Math.sqrt(Math.max(0.0, Math.pow((this.l - other.l) / 1.0, 2.0) + Math.pow(deltaC / (1.0 + 0.045 * c1), 2.0) + Math.pow(deltaH / (1.0 + 0.015 * c1), 2.0)));
        }
    }
}

