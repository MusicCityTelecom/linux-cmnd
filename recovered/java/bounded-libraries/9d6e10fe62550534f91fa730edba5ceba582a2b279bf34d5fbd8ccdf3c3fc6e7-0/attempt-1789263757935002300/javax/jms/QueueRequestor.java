/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TemporaryQueue;

public class QueueRequestor {
    QueueSession session;
    Queue queue;
    TemporaryQueue tempQueue;
    QueueSender sender;
    QueueReceiver receiver;

    public QueueRequestor(QueueSession session, Queue queue) throws JMSException {
        this.session = session;
        this.queue = queue;
        this.tempQueue = session.createTemporaryQueue();
        this.sender = session.createSender(queue);
        this.receiver = session.createReceiver(this.tempQueue);
    }

    public Message request(Message message) throws JMSException {
        message.setJMSReplyTo(this.tempQueue);
        this.sender.send(message);
        return this.receiver.receive();
    }

    public void close() throws JMSException {
        this.session.close();
        this.tempQueue.delete();
    }
}

