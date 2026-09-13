/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.Destination
 *  javax.jms.Message
 *  javax.jms.Queue
 *  org.springframework.lang.Nullable
 */
package org.springframework.jms.core;

import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.Queue;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.BrowserCallback;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.jms.core.ProducerCallback;
import org.springframework.jms.core.SessionCallback;
import org.springframework.lang.Nullable;

public interface JmsOperations {
    @Nullable
    public <T> T execute(SessionCallback<T> var1) throws JmsException;

    @Nullable
    public <T> T execute(ProducerCallback<T> var1) throws JmsException;

    @Nullable
    public <T> T execute(Destination var1, ProducerCallback<T> var2) throws JmsException;

    @Nullable
    public <T> T execute(String var1, ProducerCallback<T> var2) throws JmsException;

    public void send(MessageCreator var1) throws JmsException;

    public void send(Destination var1, MessageCreator var2) throws JmsException;

    public void send(String var1, MessageCreator var2) throws JmsException;

    public void convertAndSend(Object var1) throws JmsException;

    public void convertAndSend(Destination var1, Object var2) throws JmsException;

    public void convertAndSend(String var1, Object var2) throws JmsException;

    public void convertAndSend(Object var1, MessagePostProcessor var2) throws JmsException;

    public void convertAndSend(Destination var1, Object var2, MessagePostProcessor var3) throws JmsException;

    public void convertAndSend(String var1, Object var2, MessagePostProcessor var3) throws JmsException;

    @Nullable
    public Message receive() throws JmsException;

    @Nullable
    public Message receive(Destination var1) throws JmsException;

    @Nullable
    public Message receive(String var1) throws JmsException;

    @Nullable
    public Message receiveSelected(String var1) throws JmsException;

    @Nullable
    public Message receiveSelected(Destination var1, String var2) throws JmsException;

    @Nullable
    public Message receiveSelected(String var1, String var2) throws JmsException;

    @Nullable
    public Object receiveAndConvert() throws JmsException;

    @Nullable
    public Object receiveAndConvert(Destination var1) throws JmsException;

    @Nullable
    public Object receiveAndConvert(String var1) throws JmsException;

    @Nullable
    public Object receiveSelectedAndConvert(String var1) throws JmsException;

    @Nullable
    public Object receiveSelectedAndConvert(Destination var1, String var2) throws JmsException;

    @Nullable
    public Object receiveSelectedAndConvert(String var1, String var2) throws JmsException;

    @Nullable
    public Message sendAndReceive(MessageCreator var1) throws JmsException;

    @Nullable
    public Message sendAndReceive(Destination var1, MessageCreator var2) throws JmsException;

    @Nullable
    public Message sendAndReceive(String var1, MessageCreator var2) throws JmsException;

    @Nullable
    public <T> T browse(BrowserCallback<T> var1) throws JmsException;

    @Nullable
    public <T> T browse(Queue var1, BrowserCallback<T> var2) throws JmsException;

    @Nullable
    public <T> T browse(String var1, BrowserCallback<T> var2) throws JmsException;

    @Nullable
    public <T> T browseSelected(String var1, BrowserCallback<T> var2) throws JmsException;

    @Nullable
    public <T> T browseSelected(Queue var1, String var2, BrowserCallback<T> var3) throws JmsException;

    @Nullable
    public <T> T browseSelected(String var1, String var2, BrowserCallback<T> var3) throws JmsException;
}

