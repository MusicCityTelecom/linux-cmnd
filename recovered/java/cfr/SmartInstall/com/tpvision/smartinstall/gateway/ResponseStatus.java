/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.gateway;

import com.google.gson.Gson;
import java.text.StringCharacterIterator;
import java.util.List;
import java.util.Locale;

public class ResponseStatus {
    String status;
    String since;
    List<CloneItem> CloneItems;
    List<StreamItem> Streams;
    ErrorInfo ErrorInfo;
    List<SystemWarningItem> systemWarning;

    public String getStatus() {
        return this.status;
    }

    public String getSince() {
        return this.since;
    }

    public List<CloneItem> getCloneItems() {
        return this.CloneItems;
    }

    public List<StreamItem> getStreams() {
        return this.Streams;
    }

    public String getErrorMessage() {
        ErrorInfoItem errorItem;
        StringBuilder sb = new StringBuilder();
        if (this.systemWarning != null && !this.systemWarning.isEmpty()) {
            sb.append(String.format(Locale.ENGLISH, "Freespace of device is low: %s", ResponseStatus.humanReadableByteCountBin(this.systemWarning.get((int)0).deviceFreespace)));
        }
        if (this.ErrorInfo == null) {
            return sb.toString();
        }
        if (this.ErrorInfo.clones != null) {
            for (ErrorInfoItem errorItem2 : this.ErrorInfo.clones) {
                sb.append(String.format(Locale.ENGLISH, "CloneItem:%s error:%d,message:%s", errorItem2.name, errorItem2.error, errorItem2.message));
            }
        }
        if (this.ErrorInfo.streams != null) {
            for (ErrorInfoItem errorItem2 : this.ErrorInfo.streams) {
                if (errorItem2.name != null) {
                    sb.append(String.format(Locale.ENGLISH, "Stream:%s error:%d,message:%s", errorItem2.name, errorItem2.error, errorItem2.message));
                    continue;
                }
                sb.append(String.format(Locale.ENGLISH, "Live Stream:%s:%s error:%d,message:%s", errorItem2.MCastAddress, errorItem2.Port, errorItem2.error, errorItem2.message));
            }
        }
        if (this.ErrorInfo.playout != null) {
            errorItem = this.ErrorInfo.playout;
            if (errorItem.error > 0) {
                sb.append(String.format(Locale.ENGLISH, "Playout:%s error:%d,message:%s", errorItem.method, errorItem.error, errorItem.message));
            }
        }
        if (this.ErrorInfo.filesystem != null) {
            errorItem = this.ErrorInfo.filesystem;
            sb.append(String.format(Locale.ENGLISH, "FileSystem error:%d,message:%s", errorItem.error, errorItem.message));
        }
        return sb.toString();
    }

    public static ResponseStatus fromJson(String json) {
        return new Gson().fromJson(json, ResponseStatus.class);
    }

    public static String humanReadableByteCountBin(long bytes) {
        long absB;
        long l = absB = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
        if (absB < 1024L) {
            return bytes + " B";
        }
        long value = absB;
        StringCharacterIterator ci = new StringCharacterIterator("KMGTPE");
        for (int i = 40; i >= 0 && absB > 0xFFFCCCCCCCCCCCCL >> i; i -= 10) {
            value >>= 10;
            ci.next();
        }
        return String.format("%.1f %ciB", (double)(value *= (long)Long.signum(bytes)) / 1024.0, Character.valueOf(ci.current()));
    }

    public static class SystemWarningItem {
        long deviceFreespace;
    }

    public static class ErrorInfo {
        List<ErrorInfoItem> clones;
        List<ErrorInfoItem> streams;
        ErrorInfoItem playout;
        ErrorInfoItem filesystem;
    }

    public static class ErrorInfoItem {
        String file;
        String name;
        int error;
        String message;
        String method;
        String localNIC;
        String MCastAddress;
        String Port;
    }

    public static class StreamItem {
        int SID;
        String name;
        String version;
        String code;
        String localNIC;
        String MCastAddress;
        String MCastPort;
    }

    public static class CloneItem {
        String name;
        String version;
        String code;
    }
}

