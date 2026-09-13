/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.dao.MessageRepository;
import com.tpvision.smartinstall.dao.core.Message;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageManager {
    @Autowired
    private MessageRepository messageRepository;

    public List<Message> findMessageByGuestIdsAndIsSend(String guestIds, String isSent) {
        return this.messageRepository.findByGuestIdsAndIsSent(guestIds, isSent);
    }

    public List<Message> findAccessTimeSendMessageByGuestIds(String guestIds) {
        return this.messageRepository.findAccessTimeSendByGuestIds(guestIds);
    }

    public List<Message> findMessageByGuestIds(String guestIds) {
        return this.messageRepository.findByGuestIds(guestIds);
    }

    public List<Message> loadAll() {
        return this.messageRepository.findAll();
    }

    public Message loadByKey(String id) {
        return this.messageRepository.findById(id).orElse(null);
    }

    public Message loadByMsgId(String msgId) {
        return this.messageRepository.findByMsgId(Integer.parseInt(msgId));
    }

    public void deleteByKey(String id) {
        this.messageRepository.deleteById(id);
    }

    public void deleteByRoomId(String roomId) {
        this.messageRepository.deleteByGuestIds(roomId);
    }

    public Message save(Message message) {
        return this.messageRepository.save(message);
    }

    public Message createMessage() {
        Message msg = new Message();
        msg.setIsSent("N");
        msg.setStatus("New");
        msg.setId(UUID.randomUUID().toString());
        return msg;
    }
}

