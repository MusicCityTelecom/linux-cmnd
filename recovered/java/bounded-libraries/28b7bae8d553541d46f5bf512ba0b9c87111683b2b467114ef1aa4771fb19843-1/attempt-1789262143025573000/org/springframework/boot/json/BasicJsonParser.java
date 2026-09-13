/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.json;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.boot.json.AbstractJsonParser;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class BasicJsonParser
extends AbstractJsonParser {
    private static final int MAX_DEPTH = 1000;

    @Override
    public Map<String, Object> parseMap(String json) {
        return this.tryParse(() -> this.parseMap(json, jsonToParse -> this.parseMapInternal(0, (String)jsonToParse)), Exception.class);
    }

    @Override
    public List<Object> parseList(String json) {
        return this.tryParse(() -> this.parseList(json, jsonToParse -> this.parseListInternal(0, (String)jsonToParse)), Exception.class);
    }

    private List<Object> parseListInternal(int nesting, String json) {
        ArrayList<Object> list = new ArrayList<Object>();
        json = BasicJsonParser.trimLeadingCharacter(BasicJsonParser.trimTrailingCharacter(json, ']'), '[').trim();
        for (String value : this.tokenize(json)) {
            list.add(this.parseInternal(nesting + 1, value));
        }
        return list;
    }

    private Object parseInternal(int nesting, String json) {
        if (nesting > 1000) {
            throw new IllegalStateException("JSON is too deeply nested");
        }
        if (json.startsWith("[")) {
            return this.parseListInternal(nesting + 1, json);
        }
        if (json.startsWith("{")) {
            return this.parseMapInternal(nesting + 1, json);
        }
        if (json.startsWith("\"")) {
            return BasicJsonParser.trimTrailingCharacter(BasicJsonParser.trimLeadingCharacter(json, '\"'), '\"');
        }
        try {
            return Long.valueOf(json);
        }
        catch (NumberFormatException numberFormatException) {
            try {
                return Double.valueOf(json);
            }
            catch (NumberFormatException numberFormatException2) {
                return json;
            }
        }
    }

    private Map<String, Object> parseMapInternal(int nesting, String json) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        json = BasicJsonParser.trimLeadingCharacter(BasicJsonParser.trimTrailingCharacter(json, '}'), '{').trim();
        for (String pair : this.tokenize(json)) {
            String[] values = StringUtils.trimArrayElements((String[])StringUtils.split((String)pair, (String)":"));
            Assert.state((values[0].startsWith("\"") && values[0].endsWith("\"") ? 1 : 0) != 0, (String)"Expecting double-quotes around field names");
            String key = BasicJsonParser.trimLeadingCharacter(BasicJsonParser.trimTrailingCharacter(values[0], '\"'), '\"');
            Object value = this.parseInternal(nesting, values[1]);
            map.put(key, value);
        }
        return map;
    }

    private static String trimTrailingCharacter(String string, char c) {
        if (!string.isEmpty() && string.charAt(string.length() - 1) == c) {
            return string.substring(0, string.length() - 1);
        }
        return string;
    }

    private static String trimLeadingCharacter(String string, char c) {
        if (!string.isEmpty() && string.charAt(0) == c) {
            return string.substring(1);
        }
        return string;
    }

    private List<String> tokenize(String json) {
        ArrayList<String> list = new ArrayList<String>();
        int index = 0;
        int inObject = 0;
        int inList = 0;
        boolean inValue = false;
        boolean inEscape = false;
        StringBuilder build = new StringBuilder();
        while (index < json.length()) {
            char current = json.charAt(index);
            if (inEscape) {
                build.append(current);
                ++index;
                inEscape = false;
                continue;
            }
            if (current == '{') {
                ++inObject;
            }
            if (current == '}') {
                --inObject;
            }
            if (current == '[') {
                ++inList;
            }
            if (current == ']') {
                --inList;
            }
            if (current == '\"') {
                boolean bl = inValue = !inValue;
            }
            if (current == ',' && inObject == 0 && inList == 0 && !inValue) {
                list.add(build.toString());
                build.setLength(0);
            } else if (current == '\\') {
                inEscape = true;
            } else {
                build.append(current);
            }
            ++index;
        }
        if (build.length() > 0) {
            list.add(build.toString().trim());
        }
        return list;
    }
}

