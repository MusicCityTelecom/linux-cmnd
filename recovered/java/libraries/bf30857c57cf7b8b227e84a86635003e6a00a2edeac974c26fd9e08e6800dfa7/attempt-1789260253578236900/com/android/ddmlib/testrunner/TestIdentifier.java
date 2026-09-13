/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib.testrunner;

public class TestIdentifier {
    private final String mClassName;
    private final String mTestName;

    public TestIdentifier(String className, String testName) {
        if (className == null || testName == null) {
            throw new IllegalArgumentException("className and testName must be non-null");
        }
        this.mClassName = className;
        this.mTestName = testName;
    }

    public String getClassName() {
        return this.mClassName;
    }

    public String getTestName() {
        return this.mTestName;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.mClassName == null ? 0 : this.mClassName.hashCode());
        result = 31 * result + (this.mTestName == null ? 0 : this.mTestName.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        TestIdentifier other = (TestIdentifier)obj;
        if (this.mClassName == null ? other.mClassName != null : !this.mClassName.equals(other.mClassName)) {
            return false;
        }
        return !(this.mTestName == null ? other.mTestName != null : !this.mTestName.equals(other.mTestName));
    }

    public String toString() {
        return String.format("%s#%s", this.getClassName(), this.getTestName());
    }
}

