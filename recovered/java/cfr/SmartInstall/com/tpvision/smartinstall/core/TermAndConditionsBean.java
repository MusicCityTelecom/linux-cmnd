/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.core;

import java.util.List;

public class TermAndConditionsBean {
    private String Svc;
    private String SvcVer;
    private int Cookie;
    private String CmdType;
    private String Fun;
    private CommandDetails CommandDetails;

    public String getSvc() {
        return this.Svc;
    }

    public void setSvc(String svc) {
        this.Svc = svc;
    }

    public String getSvcVer() {
        return this.SvcVer;
    }

    public void setSvcVer(String svcVer) {
        this.SvcVer = svcVer;
    }

    public int getCookie() {
        return this.Cookie;
    }

    public void setCookie(int cookie) {
        this.Cookie = cookie;
    }

    public String getCmdType() {
        return this.CmdType;
    }

    public void setCmdType(String cmdType) {
        this.CmdType = cmdType;
    }

    public String getFun() {
        return this.Fun;
    }

    public void setFun(String fun) {
        this.Fun = fun;
    }

    public CommandDetails getCommandDetails() {
        return this.CommandDetails;
    }

    public void setCommandDetails(CommandDetails commandDetails) {
        this.CommandDetails = commandDetails;
    }

    public static class CommandDetails {
        private ApplicationDetails ApplicationDetails;

        public ApplicationDetails getApplicationDetails() {
            return this.ApplicationDetails;
        }

        public void setApplicationDetails(ApplicationDetails applicationDetails) {
            this.ApplicationDetails = applicationDetails;
        }

        public static class ApplicationDetails {
            private String ApplicationName;
            private ApplicationAttributes ApplicationAttributes;

            public String getApplicationName() {
                return this.ApplicationName;
            }

            public void setApplicationName(String applicationName) {
                this.ApplicationName = applicationName;
            }

            public ApplicationAttributes getApplicationAttributes() {
                return this.ApplicationAttributes;
            }

            public void setApplicationAttributes(ApplicationAttributes applicationAttributes) {
                this.ApplicationAttributes = applicationAttributes;
            }

            public static class ApplicationAttributes {
                private List<TermsAndConditions> TermsAndConditions;

                public List<TermsAndConditions> getMessages() {
                    return this.TermsAndConditions;
                }

                public void setMessages(List<TermsAndConditions> messages) {
                    this.TermsAndConditions = messages;
                }

                public static class TermsAndConditions {
                    private String Language;
                    private String TermsAndConditionsTitle;
                    private String TermsAndConditionsBody;

                    public String getLanguage() {
                        return this.Language;
                    }

                    public void setLanguage(String language) {
                        this.Language = language;
                    }

                    public String getMessageTitle() {
                        return this.TermsAndConditionsTitle;
                    }

                    public void setMessageTitle(String messageTitle) {
                        this.TermsAndConditionsTitle = messageTitle;
                    }

                    public String getMessageBody() {
                        return this.TermsAndConditionsBody;
                    }

                    public void setMessageBody(String messageBody) {
                        this.TermsAndConditionsBody = messageBody;
                    }
                }
            }
        }
    }
}

