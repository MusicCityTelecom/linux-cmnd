/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.device_settings.input_sources;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import be.tpvision.smartcontrol.messages.domain.device_settings.input_sources.input_source.SetSourceLabelMessages;
import be.tpvision.smartcontrol.messages.domain.device_settings.input_sources.input_source.SetSourceTypeMessages;
import be.tpvision.smartcontrol.repository.converters.input_source.SourceLabelConverter;
import be.tpvision.smartcontrol.repository.converters.input_source.SourceTypeConverter;
import be.tpvision.smartcontrol.repository.converters.input_source.TagConverter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

@Embeddable
public class InputSource
implements DeviceSetting {
    @Convert(converter=SourceTypeConverter.class)
    private SourceType sourceType;
    @Convert(converter=TagConverter.class)
    private Tag tag;
    @Convert(converter=SourceLabelConverter.class)
    private SourceLabel sourceLabel;

    protected InputSource() {
        this(SourceType.HDMI_1, SourceLabel.ON);
    }

    public InputSource(SourceType sourceType, SourceLabel sourceLabel) {
        this.setSourceType(sourceType);
        this.setSourceLabel(sourceLabel);
    }

    public InputSource(SourceType sourceType, Tag tag, SourceLabel sourceLabel) {
        this.setSourceType(sourceType);
        this.setTag(tag);
        this.setSourceLabel(sourceLabel);
    }

    public SourceType getSourceType() {
        return this.sourceType;
    }

    public void setSourceType(SourceType sourceType) {
        Assert.notNull((Object)sourceType, SetSourceTypeMessages.SOURCE_TYPE_CAN_NOT_BE_NULL);
        this.sourceType = sourceType;
    }

    public Tag getTag() {
        return this.tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag == null && this.isTagSourceType(this.sourceType) ? Tag.TAG_ONE : tag;
    }

    private boolean isTagSourceType(SourceType sourceType) {
        List<SourceType> tagSourceTypesList = Arrays.asList(SourceType.BROWSER, SourceType.MEDIA_PLAYER, SourceType.PDF_PLAYER);
        return tagSourceTypesList.contains((Object)sourceType);
    }

    public SourceLabel getSourceLabel() {
        return this.sourceLabel;
    }

    public void setSourceLabel(SourceLabel sourceLabel) {
        Assert.notNull((Object)sourceLabel, SetSourceLabelMessages.SOURCE_LABEL_CAN_NOT_BE_NULL);
        this.sourceLabel = sourceLabel;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof InputSource)) {
            return false;
        }
        InputSource that = (InputSource)object;
        return new EqualsBuilder().append((Object)this.getSourceType(), (Object)that.getSourceType()).append((Object)this.getTag(), (Object)that.getTag()).append((Object)this.getSourceLabel(), (Object)that.getSourceLabel()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.getSourceType(), this.getTag(), this.getSourceLabel()});
    }

    public String toString() {
        return new ToStringBuilder(this).append("sourceType", (Object)this.getSourceType()).append("tag", (Object)this.getTag()).append("sourceLabel", (Object)this.getSourceLabel()).toString();
    }

    public static enum SourceLabel {
        OFF,
        ON;

    }

    public static enum Tag {
        TAG_ONE,
        TAG_TWO,
        TAG_THREE,
        TAG_FOUR,
        TAG_FIVE,
        TAG_SIX,
        TAG_SEVEN,
        USB_AUTOPLAY;

    }

    public static enum SourceType {
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
        HOME_LAUNCHER,
        USB_TYPEC,
        KIOSK,
        SMART_INFO,
        TUNER,
        GOOGLE_CAST;

    }
}

