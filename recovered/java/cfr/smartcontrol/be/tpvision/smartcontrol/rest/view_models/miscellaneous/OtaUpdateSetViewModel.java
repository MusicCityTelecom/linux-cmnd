/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models.miscellaneous;

public class OtaUpdateSetViewModel {
    private String imageType;
    private String status;

    protected OtaUpdateSetViewModel() {
    }

    public OtaUpdateSetViewModel(String imageType, String status) {
        this.imageType = imageType;
        this.status = status;
    }

    public String getImageType() {
        return this.imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

