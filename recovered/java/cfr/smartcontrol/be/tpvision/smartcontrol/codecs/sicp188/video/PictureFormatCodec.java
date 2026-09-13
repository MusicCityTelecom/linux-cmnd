/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.codecs.sicp188.video;

import be.tpvision.smartcontrol.codecs.sicp.SingleValueCodec;
import be.tpvision.smartcontrol.domain.device_settings.video.PictureFormat;
import java.util.EnumMap;

public class PictureFormatCodec
extends SingleValueCodec<PictureFormat> {
    static final byte NORMAL_BYTE = 0;
    static final byte CUSTOM_BYTE = 1;
    static final byte REAL_BYTE = 2;
    static final byte FULL_BYTE = 3;
    static final byte _21_9_BYTE = 4;
    static final byte DYNAMIC_BYTE = 5;
    static final byte _16_9_BYTE = 6;
    private static PictureFormatCodec pictureFormatCodec;

    private PictureFormatCodec() {
        super(PictureFormat.class);
    }

    public static synchronized PictureFormatCodec getInstance() {
        if (pictureFormatCodec == null) {
            pictureFormatCodec = new PictureFormatCodec();
        }
        return pictureFormatCodec;
    }

    @Override
    protected void initializeDeviceSettings() {
        EnumMap<PictureFormat, Byte> domainPictureFormat = new EnumMap<PictureFormat, Byte>(PictureFormat.class);
        domainPictureFormat.put(PictureFormat.NORMAL, (byte)0);
        domainPictureFormat.put(PictureFormat.CUSTOM, (byte)1);
        domainPictureFormat.put(PictureFormat.REAL, (byte)2);
        domainPictureFormat.put(PictureFormat.FULL, (byte)3);
        domainPictureFormat.put(PictureFormat._21_9, (byte)4);
        domainPictureFormat.put(PictureFormat.DYNAMIC, (byte)5);
        domainPictureFormat.put(PictureFormat._16_9, (byte)6);
        super.setDeviceSettings(domainPictureFormat);
    }
}

