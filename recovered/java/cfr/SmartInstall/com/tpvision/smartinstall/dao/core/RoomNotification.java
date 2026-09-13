/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="room_notification")
public class RoomNotification {
    public static final int EVENT_LEVEL_INFO = 0;
    public static final int EVENT_LEVEL_ERROR_OR_WARNING = 1;
    public static final int READ_STATUS_UNREAD = 0;
    public static final int READ_STATUS_READED = 1;
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column(name="room")
    private String room;
    @Column(name="event_level")
    private int eventLevel;
    @Column(name="event_type")
    @Enumerated(value=EnumType.STRING)
    private EventType eventType;
    @Column(name="event_time")
    private String eventTime;
    @Column(name="message")
    private String message;
    @Column(name="read_time")
    private String readTime;
    @Column(name="read_status")
    private int readStatus;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoom() {
        return this.room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getEventTime() {
        return this.eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReadTime() {
        return this.readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }

    public int getReadStatus() {
        return this.readStatus;
    }

    public void setReadStatus(int readStatus) {
        this.readStatus = readStatus;
    }

    public EventType getEventType() {
        return this.eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public int getEventLevel() {
        return this.eventLevel;
    }

    public void setEventLevel(int eventLevel) {
        this.eventLevel = eventLevel;
    }

    public static enum EventType {
        ALARM_NOT_CONFIRM;

    }
}

