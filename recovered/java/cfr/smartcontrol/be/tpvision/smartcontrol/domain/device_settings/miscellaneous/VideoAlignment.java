/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.device_settings.miscellaneous;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import be.tpvision.smartcontrol.messages.domain.device_settings.miscellaneous.video_alignment.ConstructorMessages;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

public class VideoAlignment
implements DeviceSetting {
    private Item item;

    public VideoAlignment(Item item) {
        Assert.notNull((Object)item, ConstructorMessages.ITEM_CAN_NOT_BE_NULL);
        this.item = item;
    }

    public Item getItem() {
        return this.item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof VideoAlignment)) {
            return false;
        }
        VideoAlignment that = (VideoAlignment)object;
        return new EqualsBuilder().append((Object)this.getItem(), (Object)that.getItem()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.getItem()});
    }

    public String toString() {
        return new ToStringBuilder(this).append("item", (Object)this.getItem()).toString();
    }

    public static enum Item {
        AUTO_ADJUST;

    }
}

