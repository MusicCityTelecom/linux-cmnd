/*
 * Decompiled with CFR 0.152.
 */
package com.terracotta.management.resource.services.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AttachmentUtils {
    private static final ThreadLocal<SimpleDateFormat> DATE_FORMATTER = new ThreadLocal<SimpleDateFormat>(){

        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMddHHmmss");
        }
    };

    public static String createTimestampedZipFilename(String prefix) {
        StringBuilder sb = new StringBuilder(prefix);
        sb.append("-");
        sb.append(DATE_FORMATTER.get().format(new Date()));
        sb.append(".zip");
        return sb.toString();
    }
}

