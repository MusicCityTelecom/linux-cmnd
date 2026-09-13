/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.device_settings.video;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import be.tpvision.smartcontrol.messages.domain.device_settings.video.picture_in_picture_source.SetPictureInPictureSourceSourceTypeMessages;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

public class PictureInPictureSource
implements DeviceSetting {
    private SourceType pictureInPictureSourceSourceType;
    private InputSourceSourceType inputSourceSourceTypeQ2;
    private InputSourceSourceType inputSourceSourceTypeQ3;
    private InputSourceSourceType inputSourceSourceTypeQ4;

    protected PictureInPictureSource() {
    }

    public PictureInPictureSource(SourceType pictureInPictureSourceSourceType, InputSourceSourceType inputSourceSourceTypeQ2, InputSourceSourceType inputSourceSourceTypeQ3, InputSourceSourceType inputSourceSourceTypeQ4) {
        this.setPictureInPictureSourceSourceType(pictureInPictureSourceSourceType);
        this.setInputSourceSourceTypeQ2(inputSourceSourceTypeQ2);
        this.setInputSourceSourceTypeQ3(inputSourceSourceTypeQ3);
        this.setInputSourceSourceTypeQ4(inputSourceSourceTypeQ4);
    }

    public PictureInPictureSource(SourceType pictureInPictureSourceSourceType, InputSourceSourceType inputSourceSourceTypeQ2) {
        this(pictureInPictureSourceSourceType, inputSourceSourceTypeQ2, null, null);
    }

    public SourceType getPictureInPictureSourceSourceType() {
        return this.pictureInPictureSourceSourceType;
    }

    public void setPictureInPictureSourceSourceType(SourceType pictureInPictureSourceSourceType) {
        Assert.notNull((Object)pictureInPictureSourceSourceType, SetPictureInPictureSourceSourceTypeMessages.PICTURE_IN_PICTURE_SOURCE_SOURCE_TYPE_CAN_NOT_BE_NULL);
        this.pictureInPictureSourceSourceType = pictureInPictureSourceSourceType;
    }

    public InputSourceSourceType getInputSourceSourceTypeQ2() {
        return this.inputSourceSourceTypeQ2;
    }

    public void setInputSourceSourceTypeQ2(InputSourceSourceType inputSourceSourceTypeQ2) {
        this.inputSourceSourceTypeQ2 = inputSourceSourceTypeQ2;
    }

    public InputSourceSourceType getInputSourceSourceTypeQ3() {
        return this.inputSourceSourceTypeQ3;
    }

    public void setInputSourceSourceTypeQ3(InputSourceSourceType inputSourceSourceTypeQ3) {
        this.inputSourceSourceTypeQ3 = inputSourceSourceTypeQ3;
    }

    public InputSourceSourceType getInputSourceSourceTypeQ4() {
        return this.inputSourceSourceTypeQ4;
    }

    public void setInputSourceSourceTypeQ4(InputSourceSourceType inputSourceSourceTypeQ4) {
        this.inputSourceSourceTypeQ4 = inputSourceSourceTypeQ4;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof PictureInPictureSource)) {
            return false;
        }
        PictureInPictureSource that = (PictureInPictureSource)object;
        return new EqualsBuilder().append((Object)this.getPictureInPictureSourceSourceType(), (Object)that.getPictureInPictureSourceSourceType()).append((Object)this.getInputSourceSourceTypeQ2(), (Object)that.getInputSourceSourceTypeQ2()).append((Object)this.getInputSourceSourceTypeQ3(), (Object)that.getInputSourceSourceTypeQ3()).append((Object)this.getInputSourceSourceTypeQ4(), (Object)that.getInputSourceSourceTypeQ4()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.getPictureInPictureSourceSourceType(), this.getInputSourceSourceTypeQ2(), this.getInputSourceSourceTypeQ3(), this.getInputSourceSourceTypeQ4()});
    }

    public String toString() {
        return new ToStringBuilder(this).append("pictureInPictureSourceSourceType", (Object)this.getPictureInPictureSourceSourceType()).append("inputSourceSourceQ2", (Object)this.getInputSourceSourceTypeQ2()).append("inputSourceSourceQ3", (Object)this.getInputSourceSourceTypeQ3()).append("inputSourceSourceQ4", (Object)this.getInputSourceSourceTypeQ4()).toString();
    }

    public static enum InputSourceSourceType {
        VIDEO,
        S_VIDEO,
        COMPONENT,
        CVI_2,
        VGA,
        HDMI_2,
        DISPLAY_PORT_2,
        USB_2,
        CARD_DVI_D,
        DISPLAY_PORT_1,
        CARD_OPS,
        USB_1,
        HDMI_1,
        DVI_D,
        HDMI_3,
        BROWSER,
        SMART_CMS,
        DIGITAL_MEDIA_SERVER,
        INTERNAL_STORAGE,
        RESERVED_1,
        RESERVED_2,
        MEDIA_PLAYER,
        PDF_PLAYER,
        CUSTOM,
        HDMI_4,
        VGA_2,
        VGA_3,
        IWB,
        CMND_PLAY_WEB,
        USB_TYPEC,
        KIOSK,
        SMART_INFO,
        TUNER,
        GOOGLE_CAST;

    }

    public static enum SourceType {
        INPUT_SOURCE,
        SMART_CARD;

    }
}

