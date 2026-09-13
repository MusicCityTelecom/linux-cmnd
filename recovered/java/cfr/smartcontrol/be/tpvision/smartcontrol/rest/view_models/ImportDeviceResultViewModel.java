/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models;

import be.tpvision.smartcontrol.rest.view_models.ImportResultViewModel;
import be.tpvision.smartcontrol.rest.view_models.device.ImportExportDeviceViewModel;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ImportDeviceResultViewModel
implements ImportResultViewModel {
    private ImportExportDeviceViewModel importExportDeviceViewModel;
    private String result;
    private String message;

    public ImportDeviceResultViewModel() {
    }

    public ImportDeviceResultViewModel(ImportExportDeviceViewModel importExportDeviceViewModel, String result, String message) {
        this.importExportDeviceViewModel = importExportDeviceViewModel;
        this.result = result;
        this.message = message;
    }

    public ImportExportDeviceViewModel getImportExportDeviceViewModel() {
        return this.importExportDeviceViewModel;
    }

    public void setImportExportDeviceViewModel(ImportExportDeviceViewModel importExportDeviceViewModel) {
        this.importExportDeviceViewModel = importExportDeviceViewModel;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ImportDeviceResultViewModel)) {
            return false;
        }
        ImportDeviceResultViewModel that = (ImportDeviceResultViewModel)object;
        return new EqualsBuilder().append(this.getImportExportDeviceViewModel(), that.getImportExportDeviceViewModel()).append(this.getResult(), that.getResult()).append(this.getMessage(), that.getMessage()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getImportExportDeviceViewModel(), this.getResult(), this.getMessage());
    }

    public String toString() {
        return new ToStringBuilder(this).append("importExportDeviceViewModel", this.getImportExportDeviceViewModel()).append("result", this.getResult()).append("message", this.getMessage()).toString();
    }
}

