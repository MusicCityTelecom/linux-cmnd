/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;
import be.tpvision.smartcontrol.util.Convertibles;
import be.tpvision.smartcontrol.util.MessageUtilities;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class VideoAlignment
implements Convertibles {
    private Item item;

    public VideoAlignment(Item item) {
        Objects.requireNonNull(item, MessageUtilities.VIDEO_ALIGNMENT_ITEM_NOT_NULL_MESSAGE);
        this.item = item;
    }

    @Override
    public byte[] convert() {
        byte itemByte = this.item.convert();
        boolean reserved = false;
        return new byte[]{itemByte, 0};
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof VideoAlignment)) {
            return false;
        }
        VideoAlignment that = (VideoAlignment)object;
        return new EqualsBuilder().append(this.item, that.item).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.item);
    }

    public String toString() {
        return new ToStringBuilder(this).append("item", this.item).toString();
    }

    public static enum Item implements Convertible
    {
        AUTO_ADJUST(64);

        private byte data;

        private Item(byte data) {
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

