/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.repository.converters.input_source.SourceLabelConverter;
import be.tpvision.smartcontrol.repository.converters.input_source.SourceTypeConverter;
import be.tpvision.smartcontrol.util.Convertible;
import be.tpvision.smartcontrol.util.Convertibles;
import be.tpvision.smartcontrol.util.MessageUtilities;
import java.util.Objects;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Embeddable
public class InputSource
implements Convertibles {
    @Convert(converter=SourceTypeConverter.class)
    private SourceType sourceType;
    @Convert(converter=SourceLabelConverter.class)
    private SourceLabel sourceLabel;

    protected InputSource() {
        this(SourceType.HDMI_1, SourceLabel.ON);
    }

    public InputSource(SourceType sourceType, SourceLabel sourceLabel) {
        this.setSourceType(sourceType);
        this.setSourceLabel(sourceLabel);
    }

    public SourceType getSourceType() {
        return this.sourceType;
    }

    public void setSourceType(SourceType sourceType) {
        Objects.requireNonNull(sourceType, MessageUtilities.INPUT_SOURCE_SOURCE_TYPE_NOT_NULL_MESSAGE);
        this.sourceType = sourceType;
    }

    public SourceLabel getSourceLabel() {
        return this.sourceLabel;
    }

    public void setSourceLabel(SourceLabel sourceLabel) {
        Objects.requireNonNull(sourceLabel, MessageUtilities.INPUT_SOURCE_SOURCE_LABEL_NOT_NULL_MESSAGE);
        this.sourceLabel = sourceLabel;
    }

    @Override
    public byte[] convert() {
        byte sourceTypeByte = this.sourceType.convert();
        byte reservedByte = 0;
        byte sourceLabelByte = this.sourceLabel.convert();
        return new byte[]{sourceTypeByte, reservedByte, sourceLabelByte, reservedByte};
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof InputSource)) {
            return false;
        }
        InputSource that = (InputSource)object;
        return new EqualsBuilder().append(this.sourceType, that.sourceType).append(this.sourceLabel, that.sourceLabel).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.sourceType, this.sourceLabel);
    }

    public String toString() {
        return new ToStringBuilder(this).append("sourceType", this.sourceType).append("sourceLabel", this.sourceLabel).toString();
    }

    public static enum SourceLabel implements Convertible
    {
        OFF(0),
        ON(1);

        private byte data;

        private SourceLabel(byte data) {
            this.data = data;
        }

        public byte getData() {
            return this.data;
        }

        @Override
        public byte convert() {
            return this.data;
        }
    }

    public static enum SourceType implements Convertible
    {
        VIDEO(1),
        S_VIDEO(2),
        COMPONENT(3),
        CVI_2(4),
        VGA(5),
        HDMI_2(6),
        DISPLAY_PORT_2(7),
        USB_2(8),
        CARD_DVI_D(9),
        DISPLAY_PORT_1(10),
        CARD_OPS(11),
        USB_1(12),
        HDMI_1(13),
        DVI_D(14),
        HDMI_3(15),
        BROWSER(16),
        SMART_CMS(17),
        DIGITAL_MEDIA_SERVER(18),
        INTERNAL_STORAGE(19),
        RESERVED_1(20),
        RESERVED_2(21);

        private byte data;

        private SourceType(byte data) {
            this.data = data;
        }

        public byte getData() {
            return this.data;
        }

        @Override
        public byte convert() {
            return this.data;
        }
    }
}

