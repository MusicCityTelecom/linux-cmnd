/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.device_settings.miscellaneous;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.OtaImageType;

public class OtaUpdateSet
implements DeviceSetting {
    private OtaImageType imageType;
    private Status status;

    public OtaUpdateSet(OtaImageType imageType, Status status) {
        this.imageType = imageType;
        this.status = status;
    }

    public OtaImageType getImageType() {
        return this.imageType;
    }

    public void setImageType(OtaImageType imageType) {
        this.imageType = imageType;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static enum Status {
        ON(1),
        OFF(2);

        private int index;

        private Status(int index) {
            this.index = index;
        }

        public int getIndex() {
            return this.index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
}

