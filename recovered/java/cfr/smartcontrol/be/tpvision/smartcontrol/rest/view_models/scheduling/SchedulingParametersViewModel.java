/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models.scheduling;

import be.tpvision.smartcontrol.rest.view_models.scheduling.PageViewModel;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SchedulingParametersViewModel {
    private PageViewModel page1;
    private PageViewModel page2;
    private PageViewModel page3;
    private PageViewModel page4;
    private PageViewModel page5;
    private PageViewModel page6;
    private PageViewModel page7;

    public PageViewModel getPage1() {
        return this.page1;
    }

    public void setPage1(PageViewModel page1) {
        this.page1 = page1;
    }

    public PageViewModel getPage2() {
        return this.page2;
    }

    public void setPage2(PageViewModel page2) {
        this.page2 = page2;
    }

    public PageViewModel getPage3() {
        return this.page3;
    }

    public void setPage3(PageViewModel page3) {
        this.page3 = page3;
    }

    public PageViewModel getPage4() {
        return this.page4;
    }

    public void setPage4(PageViewModel page4) {
        this.page4 = page4;
    }

    public PageViewModel getPage5() {
        return this.page5;
    }

    public void setPage5(PageViewModel page5) {
        this.page5 = page5;
    }

    public PageViewModel getPage6() {
        return this.page6;
    }

    public void setPage6(PageViewModel page6) {
        this.page6 = page6;
    }

    public PageViewModel getPage7() {
        return this.page7;
    }

    public void setPage7(PageViewModel page7) {
        this.page7 = page7;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof SchedulingParametersViewModel)) {
            return false;
        }
        SchedulingParametersViewModel that = (SchedulingParametersViewModel)object;
        return new EqualsBuilder().append(this.getPage1(), that.getPage1()).append(this.getPage2(), that.getPage2()).append(this.getPage3(), that.getPage3()).append(this.getPage4(), that.getPage4()).append(this.getPage5(), that.getPage5()).append(this.getPage6(), that.getPage6()).append(this.getPage7(), that.getPage7()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getPage1(), this.getPage2(), this.getPage3(), this.getPage4(), this.getPage5(), this.getPage6(), this.getPage7());
    }

    public String toString() {
        return new ToStringBuilder(this).append("page1", this.getPage1()).append("page2", this.getPage2()).append("page3", this.getPage3()).append("page4", this.getPage4()).append("page5", this.getPage5()).append("page6", this.getPage6()).append("page7", this.getPage7()).toString();
    }
}

