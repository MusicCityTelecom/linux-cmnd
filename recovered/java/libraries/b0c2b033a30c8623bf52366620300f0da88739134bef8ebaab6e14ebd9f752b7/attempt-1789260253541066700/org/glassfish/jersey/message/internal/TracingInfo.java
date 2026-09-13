/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import java.util.ArrayList;
import java.util.List;
import org.glassfish.jersey.message.internal.TracingLogger;

final class TracingInfo {
    private final List<Message> messageList = new ArrayList<Message>();

    TracingInfo() {
    }

    public static String formatDuration(long duration) {
        if (duration == 0L) {
            return " ----";
        }
        return String.format("%5.2f", (double)duration / 1000000.0);
    }

    public static String formatDuration(long fromTimestamp, long toTimestamp) {
        return TracingInfo.formatDuration(toTimestamp - fromTimestamp);
    }

    public static String formatPercent(long value, long top) {
        if (value == 0L) {
            return "  ----";
        }
        return String.format("%6.2f", 100.0 * (double)value / (double)top);
    }

    public String[] getMessages() {
        long fromTimestamp = this.messageList.get(0).getTimestamp() - this.messageList.get(0).getDuration();
        long toTimestamp = this.messageList.get(this.messageList.size() - 1).getTimestamp();
        String[] messages = new String[this.messageList.size()];
        for (int i = 0; i < messages.length; ++i) {
            Message message = this.messageList.get(i);
            StringBuilder textSB = new StringBuilder();
            textSB.append(String.format("%-11s ", message.getEvent().category()));
            textSB.append('[').append(TracingInfo.formatDuration(message.getDuration())).append(" / ").append(TracingInfo.formatDuration(fromTimestamp, message.getTimestamp())).append(" ms |").append(TracingInfo.formatPercent(message.getDuration(), toTimestamp - fromTimestamp)).append(" %] ");
            textSB.append(message.toString());
            messages[i] = textSB.toString();
        }
        return messages;
    }

    public void addMessage(Message message) {
        this.messageList.add(message);
    }

    public static class Message {
        private final TracingLogger.Event event;
        private final long duration;
        private final long timestamp;
        private final String text;

        public Message(TracingLogger.Event event, long duration, String[] args) {
            this.event = event;
            this.duration = duration;
            this.timestamp = System.nanoTime();
            if (event.messageFormat() != null) {
                this.text = String.format(event.messageFormat(), args);
            } else {
                StringBuilder textSB = new StringBuilder();
                for (String arg : args) {
                    textSB.append(arg).append(' ');
                }
                this.text = textSB.toString();
            }
        }

        private TracingLogger.Event getEvent() {
            return this.event;
        }

        private long getDuration() {
            return this.duration;
        }

        private long getTimestamp() {
            return this.timestamp;
        }

        public String toString() {
            return this.text;
        }
    }
}

