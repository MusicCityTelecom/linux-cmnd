/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.device_settings.miscellaneous;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.OtaImageType;

public class OtaUpdateGet
implements DeviceSetting {
    private OtaImageType imageType;
    private UpdateStatus status;

    public OtaUpdateGet(OtaImageType imageType, UpdateStatus status) {
        this.imageType = imageType;
        this.status = status;
    }

    public OtaImageType getImageType() {
        return this.imageType;
    }

    public void setImageType(OtaImageType imageType) {
        this.imageType = imageType;
    }

    public UpdateStatus getStatus() {
        return this.status;
    }

    public void setStatus(UpdateStatus status) {
        this.status = status;
    }

    public static enum UpdateStatus {
        CANT_UPDATE("Can\u2019t update"),
        ABLE_TO_UPDATE("Able to update");

        private String description;

        private UpdateStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}

