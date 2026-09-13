/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models;

import be.tpvision.smartcontrol.rest.view_models.ImportDeviceResultViewModelList;
import be.tpvision.smartcontrol.rest.view_models.ImportResultViewModel;
import be.tpvision.smartcontrol.rest.view_models.group.ImportExportGroupViewModel;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ImportGroupResultViewModel
implements ImportResultViewModel {
    private ImportExportGroupViewModel importExportGroupViewModel;
    private ImportDeviceResultViewModelList importDeviceResultViewModelList;
    private String result;
    private String message;

    public ImportGroupResultViewModel() {
    }

    public ImportGroupResultViewModel(ImportExportGroupViewModel importExportGroupViewModel, ImportDeviceResultViewModelList importDeviceResultViewModelList, String result, String message) {
        this.importExportGroupViewModel = importExportGroupViewModel;
        this.importDeviceResultViewModelList = importDeviceResultViewModelList;
        this.result = result;
        this.message = message;
    }

    public ImportExportGroupViewModel getImportExportGroupViewModel() {
        return this.importExportGroupViewModel;
    }

    public void setImportExportGroupViewModel(ImportExportGroupViewModel importExportGroupViewModel) {
        this.importExportGroupViewModel = importExportGroupViewModel;
    }

    public ImportDeviceResultViewModelList getImportDeviceResultViewModelList() {
        return this.importDeviceResultViewModelList;
    }

    public void setImportDeviceResultViewModelList(ImportDeviceResultViewModelList importDeviceResultViewModelList) {
        this.importDeviceResultViewModelList = importDeviceResultViewModelList;
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
        if (!(object instanceof ImportGroupResultViewModel)) {
            return false;
        }
        ImportGroupResultViewModel that = (ImportGroupResultViewModel)object;
        return new EqualsBuilder().append(this.getImportExportGroupViewModel(), that.getImportExportGroupViewModel()).append(this.getImportDeviceResultViewModelList(), that.getImportDeviceResultViewModelList()).append(this.getResult(), that.getResult()).append(this.getMessage(), that.getMessage()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getImportExportGroupViewModel(), this.getImportDeviceResultViewModelList(), this.getResult(), this.getMessage());
    }

    public String toString() {
        return new ToStringBuilder(this).append("importExportGroupViewModel", this.getImportExportGroupViewModel()).append("importDeviceResultViewModelList", this.getImportDeviceResultViewModelList()).append("result", this.getResult()).append("message", this.getMessage()).toString();
    }
}

