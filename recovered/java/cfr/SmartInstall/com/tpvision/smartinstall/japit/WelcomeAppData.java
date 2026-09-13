/*
 * Decompiled with CFR 0.152.
 */
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
    public CommandDetail CommandDetails = new CommandDetail();

    public List<MessageItemContent> getMessageList(MessageType messageType) {
        if (this.CommandDetails.Messages == null) {
            this.CommandDetails.initMessages();
        }
        List<MessageItemContent> messagelist = null;
        for (MessageItem messageItem : this.CommandDetails.Messages) {
            if (!messageItem.MessageType.equalsIgnoreCase(messageType.name())) continue;
            messagelist = messageItem.Messages;
            break;
        }
        if (messagelist == null) {
            MessageItem standardMesage = new MessageItem();
            standardMesage.MessageType = messageType.name();
            this.CommandDetails.Messages.add(standardMesage);
            messagelist = standardMesage.Messages;
        }
        return messagelist;
    }

    public List<ContentItem> getContentItemList() {
        return this.CommandDetails.Content;
    }

    public ContentItem getContentItemByName(String name) {
        for (ContentItem contentItem : this.CommandDetails.Content) {
            String content = FilenameUtils.getName(contentItem.Content);
            if (!content.equalsIgnoreCase(name)) continue;
            return contentItem;
        }
        return null;
    }

    public void updateMessages(MessageItemContent messageItemContent) {
        List<MessageItemContent> contents = this.getMessageList(MessageType.Standard);
        boolean updated = false;
        for (MessageItemContent content : contents) {
            if (content.Language == null || !content.Language.equals(messageItemContent.Language)) continue;
            content.MessageBody = messageItemContent.MessageBody;
            content.MessageTitle = messageItemContent.MessageTitle;
            content.MessageSignature = messageItemContent.MessageSignature;
            updated = true;
            break;
        }
        if (!updated) {
            contents.add(messageItemContent);
        }
    }

    public class MessageItemContent {
        public String Language;
        public String MessageTitle;
        public String MessageBody;
        public String MessageSignature;
    }

    public class MessageItem {
        public String MessageType;
        public List<MessageItemContent> Messages = new CopyOnWriteArrayList<MessageItemContent>();
    }

    public static class ContentItem {
        public String Content;
        public String Sequence;
        public String DisplayDuration;
    }

    public class CommandDetail {
        public List<ContentItem> Content;
        public List<MessageItem> Messages = new CopyOnWriteArrayList<MessageItem>();
        public String ShowUniqueMessage;

        public CommandDetail() {
            this.Content = new CopyOnWriteArrayList<ContentItem>();
            this.initMessages();
        }

        public void initMessages() {
            this.Messages = new CopyOnWriteArrayList<MessageItem>();
            MessageItem standardMesage = new MessageItem();
            standardMesage.MessageType = MessageType.Standard.name();
            this.Messages.add(standardMesage);
        }
    }

    public static enum MessageType {
        Standard,
        Unique;

    }
}

