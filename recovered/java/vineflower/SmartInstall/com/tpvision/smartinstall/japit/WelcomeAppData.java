package com.tpvision.smartinstall.japit;

import com.tpvision.smartinstall.util.JAPITUtils;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.commons.io.FilenameUtils;

public class WelcomeAppData {
   String Svc = "OfflineServices";
   String SvcVer = "4.0";
   int Cookie = JAPITUtils.getJapitRandomCookieValue();
   String CmdType = "Change";
   String Fun = "WelcomeApp";
   public WelcomeAppData.CommandDetail CommandDetails = new WelcomeAppData.CommandDetail();

   public List<WelcomeAppData.MessageItemContent> getMessageList(WelcomeAppData.MessageType messageType) {
      if (this.CommandDetails.Messages == null) {
         this.CommandDetails.initMessages();
      }

      List<WelcomeAppData.MessageItemContent> messagelist = null;

      for (WelcomeAppData.MessageItem messageItem : this.CommandDetails.Messages) {
         if (messageItem.MessageType.equalsIgnoreCase(messageType.name())) {
            messagelist = messageItem.Messages;
            break;
         }
      }

      if (messagelist == null) {
         WelcomeAppData.MessageItem standardMesage = new WelcomeAppData.MessageItem();
         standardMesage.MessageType = messageType.name();
         this.CommandDetails.Messages.add(standardMesage);
         messagelist = standardMesage.Messages;
      }

      return messagelist;
   }

   public List<WelcomeAppData.ContentItem> getContentItemList() {
      return this.CommandDetails.Content;
   }

   public WelcomeAppData.ContentItem getContentItemByName(String name) {
      for (WelcomeAppData.ContentItem contentItem : this.CommandDetails.Content) {
         String content = FilenameUtils.getName(contentItem.Content);
         if (content.equalsIgnoreCase(name)) {
            return contentItem;
         }
      }

      return null;
   }

   public void updateMessages(WelcomeAppData.MessageItemContent messageItemContent) {
      List<WelcomeAppData.MessageItemContent> contents = this.getMessageList(WelcomeAppData.MessageType.Standard);
      boolean updated = false;

      for (WelcomeAppData.MessageItemContent content : contents) {
         if (content.Language != null && content.Language.equals(messageItemContent.Language)) {
            content.MessageBody = messageItemContent.MessageBody;
            content.MessageTitle = messageItemContent.MessageTitle;
            content.MessageSignature = messageItemContent.MessageSignature;
            updated = true;
            break;
         }
      }

      if (!updated) {
         contents.add(messageItemContent);
      }
   }

   public class CommandDetail {
      public List<WelcomeAppData.ContentItem> Content;
      public List<WelcomeAppData.MessageItem> Messages = new CopyOnWriteArrayList<>();
      public String ShowUniqueMessage;

      public CommandDetail() {
         this.Content = new CopyOnWriteArrayList<>();
         this.initMessages();
      }

      public void initMessages() {
         this.Messages = new CopyOnWriteArrayList<>();
         WelcomeAppData.MessageItem standardMesage = WelcomeAppData.this.new MessageItem();
         standardMesage.MessageType = WelcomeAppData.MessageType.Standard.name();
         this.Messages.add(standardMesage);
      }
   }

   public static class ContentItem {
      public String Content;
      public String Sequence;
      public String DisplayDuration;
   }

   public class MessageItem {
      public String MessageType;
      public List<WelcomeAppData.MessageItemContent> Messages = new CopyOnWriteArrayList<>();
   }

   public class MessageItemContent {
      public String Language;
      public String MessageTitle;
      public String MessageBody;
      public String MessageSignature;
   }

   public enum MessageType {
      Standard,
      Unique;
   }
}
