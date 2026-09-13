package com.tpvision.smartinstall.core;

import java.util.List;

public class TermAndConditionsBean {
   private String Svc;
   private String SvcVer;
   private int Cookie;
   private String CmdType;
   private String Fun;
   private TermAndConditionsBean.CommandDetails CommandDetails;

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

   public TermAndConditionsBean.CommandDetails getCommandDetails() {
      return this.CommandDetails;
   }

   public void setCommandDetails(TermAndConditionsBean.CommandDetails commandDetails) {
      this.CommandDetails = commandDetails;
   }

   public static class CommandDetails {
      private TermAndConditionsBean.CommandDetails.ApplicationDetails ApplicationDetails;

      public TermAndConditionsBean.CommandDetails.ApplicationDetails getApplicationDetails() {
         return this.ApplicationDetails;
      }

      public void setApplicationDetails(TermAndConditionsBean.CommandDetails.ApplicationDetails applicationDetails) {
         this.ApplicationDetails = applicationDetails;
      }

      public static class ApplicationDetails {
         private String ApplicationName;
         private TermAndConditionsBean.CommandDetails.ApplicationDetails.ApplicationAttributes ApplicationAttributes;

         public String getApplicationName() {
            return this.ApplicationName;
         }

         public void setApplicationName(String applicationName) {
            this.ApplicationName = applicationName;
         }

         public TermAndConditionsBean.CommandDetails.ApplicationDetails.ApplicationAttributes getApplicationAttributes() {
            return this.ApplicationAttributes;
         }

         public void setApplicationAttributes(TermAndConditionsBean.CommandDetails.ApplicationDetails.ApplicationAttributes applicationAttributes) {
            this.ApplicationAttributes = applicationAttributes;
         }

         public static class ApplicationAttributes {
            private List<TermAndConditionsBean.CommandDetails.ApplicationDetails.ApplicationAttributes.TermsAndConditions> TermsAndConditions;

            public List<TermAndConditionsBean.CommandDetails.ApplicationDetails.ApplicationAttributes.TermsAndConditions> getMessages() {
               return this.TermsAndConditions;
            }

            public void setMessages(List<TermAndConditionsBean.CommandDetails.ApplicationDetails.ApplicationAttributes.TermsAndConditions> messages) {
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
