/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.device.activitymanager;

import com.android.tools.build.bundletool.device.activitymanager.ConfigStringIterator;
import com.android.tools.build.bundletool.device.activitymanager.ResourceConfigParser;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import java.util.Iterator;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class LocaleParser<T>
implements ResourceConfigParser.ConfigQualifierParser<T> {
    private static final String LANGUAGE_PATTERN = "(?!car)[A-Za-z]{2,3}";
    private static final String BCP47_PREFIX = "b+";
    private static final String REGION_CONTINUATION_PATTERN = "r[A-Z]{2}($|,.+)";
    private static final Pattern LANGUAGE_REGION_PATTERN = Pattern.compile("(?<language>[A-Za-z]{2,3})(-r(?<region>[A-Za-z]{2}))?");
    private static final Splitter BCP47_SPLITTER = Splitter.on('+');
    private static final Splitter COMMA_SPLITTER = Splitter.on(',');
    private static final Joiner HYPHEN_JOINER = Joiner.on('-');
    private static final Joiner PLUS_JOINER = Joiner.on('+');

    LocaleParser() {
    }

    @Override
    public boolean parse(ConfigStringIterator iterator2, ResourceConfigParser.ResourceConfigHandler<T> handler) {
        iterator2.savePosition();
        try {
            this.parseInternal(iterator2.getValue(), handler, iterator2);
            return true;
        }
        catch (ParseException parseException) {
            iterator2.restorePosition();
            return false;
        }
    }

    private void parseInternal(String nextPart, ResourceConfigParser.ResourceConfigHandler<T> handler, Iterator<String> iterator2) throws ParseException {
        Iterable<String> locales = COMMA_SPLITTER.split(this.getAllLocaleSegments(nextPart, iterator2));
        for (String locale : locales) {
            if (locale.startsWith(BCP47_PREFIX)) {
                this.initFromBcp47Tag(locale.substring(BCP47_PREFIX.length()), handler);
                continue;
            }
            Matcher languageRegionMatcher = LANGUAGE_REGION_PATTERN.matcher(locale);
            if (!languageRegionMatcher.matches()) {
                throw new ParseException(String.format("Unexpected language/region locale string '%s'.", locale));
            }
            String language = languageRegionMatcher.group("language");
            String region = languageRegionMatcher.group("region");
            Preconditions.checkNotNull(language);
            if (region != null) {
                handler.onLocale(language + "-" + region);
                continue;
            }
            handler.onLocale(language);
        }
    }

    private String getAllLocaleSegments(String nextPart, Iterator<String> iterator2) {
        ImmutableList.Builder localeParts = ImmutableList.builder();
        if (nextPart.contains(",") || nextPart.matches(LANGUAGE_PATTERN) || nextPart.startsWith(BCP47_PREFIX)) {
            localeParts.add(nextPart);
            while (iterator2.hasNext()) {
                nextPart = iterator2.next();
                if (!nextPart.matches(REGION_CONTINUATION_PATTERN) && !nextPart.contains(",")) continue;
                localeParts.add(nextPart);
            }
        }
        return HYPHEN_JOINER.join(localeParts.build());
    }

    private void initFromBcp47Tag(String bcp47Tag, ResourceConfigParser.ResourceConfigHandler<T> handler) throws ParseException {
        ImmutableList<String> subtags = ImmutableList.copyOf(BCP47_SPLITTER.split(bcp47Tag));
        String language = (String)subtags.get(0);
        Optional<String> region = this.extractRegion(subtags);
        if (region.isPresent()) {
            handler.onLocale(language + "-" + region.get());
        } else {
            handler.onLocale(language);
        }
    }

    private Optional<String> extractRegion(ImmutableList<String> bcp47SubTags) throws ParseException {
        if (bcp47SubTags.size() == 1) {
            return Optional.empty();
        }
        if (bcp47SubTags.size() == 2) {
            String secondSubTag = (String)bcp47SubTags.get(1);
            switch (secondSubTag.length()) {
                case 2: 
                case 3: {
                    return Optional.of(secondSubTag);
                }
                case 4: 
                case 5: 
                case 6: 
                case 7: 
                case 8: {
                    return Optional.empty();
                }
            }
        } else if (bcp47SubTags.size() == 3) {
            String secondSubTag = (String)bcp47SubTags.get(1);
            if (secondSubTag.length() == 2 || secondSubTag.length() == 3) {
                return Optional.of(secondSubTag);
            }
            String thirdSubTag = (String)bcp47SubTags.get(2);
            if (thirdSubTag.length() == 2 || thirdSubTag.length() == 3) {
                return Optional.of(thirdSubTag);
            }
            if (thirdSubTag.length() >= 4) {
                return Optional.empty();
            }
        } else if (bcp47SubTags.size() == 4) {
            return Optional.of(bcp47SubTags.get(2));
        }
        throw new ParseException(String.format("Encountered unsupported or invalid BCP-47 string '%s'.", PLUS_JOINER.join(bcp47SubTags)));
    }

    private static class ParseException
    extends Exception {
        public ParseException(String cause) {
            super(cause);
        }
    }
}

