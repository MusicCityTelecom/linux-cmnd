/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.services.persondir.IPersonAttributeDao
 */
package org.apereo.services.persondir.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apereo.services.persondir.IPersonAttributeDao;

public class PatternHelper {
    public static Pattern compilePattern(String queryString) {
        String quotedQueryPart;
        String queryPart;
        StringBuilder queryBuilder = new StringBuilder();
        Matcher queryMatcher = IPersonAttributeDao.WILDCARD_PATTERN.matcher(queryString);
        if (!queryMatcher.find()) {
            return Pattern.compile(Pattern.quote(queryString));
        }
        int start = queryMatcher.start();
        int previousEnd = -1;
        if (start > 0) {
            queryPart = queryString.substring(0, start);
            quotedQueryPart = Pattern.quote(queryPart);
            queryBuilder.append(quotedQueryPart);
        }
        queryBuilder.append(".*");
        do {
            start = queryMatcher.start();
            if (previousEnd != -1) {
                queryPart = queryString.substring(previousEnd, start);
                quotedQueryPart = Pattern.quote(queryPart);
                queryBuilder.append(quotedQueryPart);
                queryBuilder.append(".*");
            }
            previousEnd = queryMatcher.end();
        } while (queryMatcher.find());
        if (previousEnd < queryString.length()) {
            queryPart = queryString.substring(previousEnd);
            quotedQueryPart = Pattern.quote(queryPart);
            queryBuilder.append(quotedQueryPart);
        }
        return Pattern.compile(queryBuilder.toString());
    }
}

