/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.androidapp;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class AndroidApplications {
    private String availablePackages;
    private List<AndroidApp> clonePackages;

    public String getAvailablePackages() {
        return this.availablePackages;
    }

    public void setAvailablePackages(String availablePackages) {
        this.availablePackages = availablePackages;
    }

    public List<AndroidApp> getClonePackages() {
        return this.clonePackages;
    }

    public void setClonePackages(List<AndroidApp> clonePackages) {
        this.clonePackages = clonePackages;
    }

    public static class AndroidApp {
        public static final String PACKAGE_TYPE_LOCAL = "LOCAL";
        public static final String PACKAGE_TYPE_PLAYSTORE = "PlayStore";
        private String packageURI;
        private String silentAction;
        private String packageType;
        private String packagePosition;
        private String packageShowHide;
        private String[] packageCountry;
        private String[] packageCategory;
        private String packageName;
        private PackageDetails packageDetails;
        private String applicationName;

        public String getPackageURI() {
            if (this.packageURI != null) {
                return this.packageURI;
            }
            if (this.packageDetails != null && !this.packageDetails.getBasePackages().isEmpty()) {
                return this.packageDetails.getBasePackages().get(0).getPackageURI();
            }
            return null;
        }

        public void setPackageURI(String packageURI) {
            this.packageURI = packageURI;
        }

        public String getSilentAction() {
            return this.silentAction;
        }

        public void setSilentAction(String silentAction) {
            this.silentAction = silentAction;
        }

        public String getPackageType() {
            return this.packageType;
        }

        public void setPackageType(String packageType) {
            this.packageType = packageType;
        }

        public String getPackagePosition() {
            return this.packagePosition;
        }

        public void setPackagePosition(String packagePosition) {
            this.packagePosition = packagePosition;
        }

        public String getPackageShowHide() {
            return this.packageShowHide;
        }

        public void setPackageShowHide(String packageShowHide) {
            this.packageShowHide = packageShowHide;
        }

        public String[] getPackageCountry() {
            return this.packageCountry;
        }

        public void setPackageCountry(String[] packageCountry) {
            this.packageCountry = packageCountry;
        }

        public String[] getPackageCategory() {
            return this.packageCategory;
        }

        public void setPackageCategory(String[] packageCategory) {
            this.packageCategory = packageCategory;
        }

        public String getPackageName() {
            if (this.packageName != null) {
                return this.packageName;
            }
            if (this.packageDetails != null && StringUtils.isNotBlank(this.packageDetails.getPackageName())) {
                return this.packageDetails.getPackageName();
            }
            return null;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getAllInfoString() {
            return this.packageURI + " " + String.join((CharSequence)",", this.getPackageCategory()) + " " + String.join((CharSequence)",", this.getPackageCountry());
        }

        public PackageDetails getPackageDetails() {
            return this.packageDetails;
        }

        public void setPackageDetails(PackageDetails packageDetails) {
            this.packageDetails = packageDetails;
        }

        public String getApplicationName() {
            return this.applicationName;
        }

        public void setApplicationName(String applicationName) {
            this.applicationName = applicationName;
        }

        public boolean containsCategory(String categoryName) {
            boolean isContains = false;
            for (String category : this.packageCategory) {
                if (!category.contains(categoryName)) continue;
                isContains = true;
                break;
            }
            return isContains;
        }
    }

    public static class PackageDetails {
        private String packageName;
        private List<BasePackage> basePackages = new ArrayList<BasePackage>();
        private List<SplitPackage> splitPackages = new ArrayList<SplitPackage>();

        public String getPackageName() {
            return this.packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public List<BasePackage> getBasePackages() {
            return this.basePackages;
        }

        public void setBasePackages(List<BasePackage> basePackages) {
            this.basePackages = basePackages;
        }

        public List<SplitPackage> getSplitPackages() {
            return this.splitPackages;
        }

        public void setSplitPackages(List<SplitPackage> splitPackages) {
            this.splitPackages = splitPackages;
        }

        public static class SplitPackage {
            private String packageURI;
            private String splitName;

            public String getSplitName() {
                return this.splitName;
            }

            public void setSplitName(String splitName) {
                this.splitName = splitName;
            }

            public String getPackageURI() {
                return this.packageURI;
            }

            public void setPackageURI(String packageURI) {
                this.packageURI = packageURI;
            }
        }

        public static class BasePackage {
            private String packageURI;

            public String getPackageURI() {
                return this.packageURI;
            }

            public void setPackageURI(String packageURI) {
                this.packageURI = packageURI;
            }
        }
    }
}

