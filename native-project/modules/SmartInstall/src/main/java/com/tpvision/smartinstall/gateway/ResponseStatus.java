package com.tpvision.smartinstall.gateway;

import com.google.gson.Gson;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.List;
import java.util.Locale;

public class ResponseStatus {
   String status;
   String since;
   List<ResponseStatus.CloneItem> CloneItems;
   List<ResponseStatus.StreamItem> Streams;
   ResponseStatus.ErrorInfo ErrorInfo;
   List<ResponseStatus.SystemWarningItem> systemWarning;

   public String getStatus() {
      return this.status;
   }

   public String getSince() {
      return this.since;
   }

   public List<ResponseStatus.CloneItem> getCloneItems() {
      return this.CloneItems;
   }

   public List<ResponseStatus.StreamItem> getStreams() {
      return this.Streams;
   }

   public String getErrorMessage() {
      StringBuilder sb = new StringBuilder();
      if (this.systemWarning != null && !this.systemWarning.isEmpty()) {
         sb.append(String.format(Locale.ENGLISH, "Freespace of device is low: %s", humanReadableByteCountBin(this.systemWarning.get(0).deviceFreespace)));
      }

      if (this.ErrorInfo == null) {
         return sb.toString();
      }

      if (this.ErrorInfo.clones != null) {
         for (ResponseStatus.ErrorInfoItem errorItem : this.ErrorInfo.clones) {
            sb.append(String.format(Locale.ENGLISH, "CloneItem:%s error:%d,message:%s", errorItem.name, errorItem.error, errorItem.message));
         }
      }

      if (this.ErrorInfo.streams != null) {
         for (ResponseStatus.ErrorInfoItem errorItem : this.ErrorInfo.streams) {
            if (errorItem.name != null) {
               sb.append(String.format(Locale.ENGLISH, "Stream:%s error:%d,message:%s", errorItem.name, errorItem.error, errorItem.message));
            } else {
               sb.append(
                  String.format(
                     Locale.ENGLISH, "Live Stream:%s:%s error:%d,message:%s", errorItem.MCastAddress, errorItem.Port, errorItem.error, errorItem.message
                  )
               );
            }
         }
      }

      if (this.ErrorInfo.playout != null) {
         ResponseStatus.ErrorInfoItem errorItem = this.ErrorInfo.playout;
         if (errorItem.error > 0) {
            sb.append(String.format(Locale.ENGLISH, "Playout:%s error:%d,message:%s", errorItem.method, errorItem.error, errorItem.message));
         }
      }

      if (this.ErrorInfo.filesystem != null) {
         ResponseStatus.ErrorInfoItem errorItem = this.ErrorInfo.filesystem;
         sb.append(String.format(Locale.ENGLISH, "FileSystem error:%d,message:%s", errorItem.error, errorItem.message));
      }

      return sb.toString();
   }

   public static ResponseStatus fromJson(String json) {
      return new Gson().fromJson(json, ResponseStatus.class);
   }

   public static String humanReadableByteCountBin(long bytes) {
      long absB = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
      if (absB < 1024L) {
         return bytes + " B";
      }

      long value = absB;
      CharacterIterator ci = new StringCharacterIterator("KMGTPE");

      for (int i = 40; i >= 0 && absB > 1152865209611504844L >> i; i -= 10) {
         value >>= 10;
         ci.next();
      }

      value *= Long.signum(bytes);
      return String.format("%.1f %ciB", value / 1024.0, ci.current());
   }

   public static class CloneItem {
      String name;
      String version;
      String code;
   }

   public static class ErrorInfo {
      List<ResponseStatus.ErrorInfoItem> clones;
      List<ResponseStatus.ErrorInfoItem> streams;
      ResponseStatus.ErrorInfoItem playout;
      ResponseStatus.ErrorInfoItem filesystem;
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

   public static class SystemWarningItem {
      long deviceFreespace;
   }
}
