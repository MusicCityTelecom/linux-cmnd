/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="EmployeeInfoType", propOrder={"value"})
public class EmployeeInfoType {
    @XmlValue
    protected String value;
    @XmlAttribute(name="EmployeeId")
    protected String employeeId;
    @XmlAttribute(name="EmployeeLevel")
    protected String employeeLevel;
    @XmlAttribute(name="EmployeeTitle")
    protected String employeeTitle;
    @XmlAttribute(name="EmployeeStatus")
    protected String employeeStatus;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(String value) {
        this.employeeId = value;
    }

    public String getEmployeeLevel() {
        return this.employeeLevel;
    }

    public void setEmployeeLevel(String value) {
        this.employeeLevel = value;
    }

    public String getEmployeeTitle() {
        return this.employeeTitle;
    }

    public void setEmployeeTitle(String value) {
        this.employeeTitle = value;
    }

    public String getEmployeeStatus() {
        return this.employeeStatus;
    }

    public void setEmployeeStatus(String value) {
        this.employeeStatus = value;
    }
}

