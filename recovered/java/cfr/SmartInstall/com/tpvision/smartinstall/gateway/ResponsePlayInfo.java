/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.gateway;

import com.google.gson.Gson;
import com.tpvision.smartinstall.gateway.ResponseStatus;
import java.util.List;

public class ResponsePlayInfo {
    public String status;
    public ServiceData data;

    public static ResponsePlayInfo fromJson(String json) {
        return new Gson().fromJson(json, ResponsePlayInfo.class);
    }

    public static class DataService {
        List<ResponseStatus.CloneItem> CloneItems;
        long Freq;
        int ONID;
        int PMTPid;
        int SID;
        int TSID;
        String name;
    }

    public static class AVService {
        long Freq;
        int ONID;
        int PMTPid;
        int SID;
        int TSID;
        long bitrate;
        int Port;
        String Url;
        String code;
        String name;
        String version;

        public long getFreq() {
            return this.Freq;
        }

        public int getONID() {
            return this.ONID;
        }

        public int getPMTPid() {
            return this.PMTPid;
        }

        public int getSID() {
            return this.SID;
        }

        public int getTSID() {
            return this.TSID;
        }

        public long getBitrate() {
            return this.bitrate;
        }

        public String getCode() {
            return this.code;
        }

        public String getName() {
            return this.name;
        }

        public String getVersion() {
            return this.version;
        }

        public int getPort() {
            return this.Port;
        }

        public String getUrl() {
            return this.Url;
        }
    }

    public static class Services {
        List<AVService> AVServices;
        List<DataService> dataServices;
    }

    public static class ServiceData {
        public Services services;
    }
}

