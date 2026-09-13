/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.transformer;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.integration.transformer.AbstractPayloadTransformer;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class SyslogToMapTransformer
extends AbstractPayloadTransformer<Object, Map<String, ?>> {
    public static final String FACILITY = "FACILITY";
    public static final String SEVERITY = "SEVERITY";
    public static final String TIMESTAMP = "TIMESTAMP";
    public static final String HOST = "HOST";
    public static final String TAG = "TAG";
    public static final String MESSAGE = "MESSAGE";
    public static final String UNDECODED = "UNDECODED";
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMM dd HH:mm:ss");
    private final Pattern pattern = Pattern.compile("<([^>]+)>(.{15}) ([^ ]+) ([a-zA-Z0-9]{0,32})(.*)", 32);

    @Override
    protected Map<String, ?> transformPayload(Object payload) {
        boolean isByteArray = payload instanceof byte[];
        boolean isString = payload instanceof String;
        Assert.isTrue((isByteArray || isString ? 1 : 0) != 0, (String)"payload must be String or byte[]");
        if (isByteArray) {
            return this.transform((byte[])payload);
        }
        return this.transform((String)payload);
    }

    @Override
    private Map<String, ?> transform(byte[] payloadBytes) {
        return this.transform(new String(payloadBytes, StandardCharsets.UTF_8));
    }

    @Override
    private Map<String, ?> transform(String payload) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        Matcher matcher = this.pattern.matcher(payload);
        if (matcher.matches()) {
            this.parseMatcherToMap(payload, matcher, map);
        } else {
            this.logger.debug(() -> "Could not decode: " + payload);
            map.put(UNDECODED, payload);
        }
        return map;
    }

    private void parseMatcherToMap(Object payload, Matcher matcher, Map<String, Object> map) {
        try {
            String facilityString = matcher.group(1);
            int facility = Integer.parseInt(facilityString);
            int severity = facility & 7;
            map.put(FACILITY, facility >>= 3);
            map.put(SEVERITY, severity);
            String timestamp = matcher.group(2);
            this.parseTimestampToMap(timestamp, map);
            map.put(HOST, matcher.group(3));
            String tag = matcher.group(4);
            if (StringUtils.hasLength((String)tag)) {
                map.put(TAG, tag);
            }
            map.put(MESSAGE, matcher.group(5));
        }
        catch (Exception ex) {
            this.logger.debug((Throwable)ex, () -> "Could not decode: " + payload);
            map.clear();
            map.put(UNDECODED, payload);
        }
    }

    private void parseTimestampToMap(String timestamp, Map<String, Object> map) {
        try {
            LocalDate localDate = this.dateTimeFormatter.parse((CharSequence)timestamp, LocalDate::from);
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(1);
            int month = calendar.get(2);
            calendar.setTime(Date.valueOf(localDate));
            if (month == 11 && calendar.get(2) == 0) {
                calendar.set(1, year + 1);
            } else if (month == 0 && calendar.get(2) == 1) {
                calendar.set(1, year - 1);
            } else {
                calendar.set(1, year);
            }
            map.put(TIMESTAMP, calendar.getTime());
        }
        catch (Exception e) {
            map.put(TIMESTAMP, timestamp);
        }
    }
}

