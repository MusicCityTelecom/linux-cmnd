/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.ibm.icu.lang.UCharacter
 *  com.ibm.icu.text.UnicodeSet
 *  com.ibm.icu.text.UnicodeSet$EntryRange
 *  com.ibm.icu.util.RangeValueIterator
 *  com.ibm.icu.util.RangeValueIterator$Element
 */
package groovyjarjarantlr4.v4.unicode;

import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.RangeValueIterator;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class UnicodeDataTemplateController {
    private static void addIntervalForCategory(Map<String, IntervalSet> categoryMap, String categoryName, int start, int finish) {
        IntervalSet intervalSet = categoryMap.get(categoryName);
        if (intervalSet == null) {
            intervalSet = new IntervalSet(new int[0]);
            categoryMap.put(categoryName, intervalSet);
        }
        intervalSet.add(start, finish);
    }

    private static void addPropertyAliases(Map<String, String> propertyAliases, String propertyName, int property) {
        int nameChoice = 1;
        while (true) {
            String alias;
            try {
                alias = UCharacter.getPropertyName((int)property, (int)nameChoice);
            }
            catch (IllegalArgumentException e) {
                break;
            }
            assert (alias != null);
            UnicodeDataTemplateController.addPropertyAlias(propertyAliases, alias, propertyName);
            ++nameChoice;
        }
    }

    private static void addPropertyAlias(Map<String, String> propertyAliases, String alias, String propertyName) {
        propertyAliases.put(alias, propertyName);
    }

    public static Map<String, Object> getProperties() {
        LinkedHashMap<String, IntervalSet> propertyCodePointRanges = new LinkedHashMap<String, IntervalSet>();
        UnicodeDataTemplateController.addUnicodeCategoryCodesToCodePointRanges(propertyCodePointRanges);
        UnicodeDataTemplateController.addUnicodeBinaryPropertyCodesToCodePointRanges(propertyCodePointRanges);
        UnicodeDataTemplateController.addUnicodeIntPropertyCodesToCodePointRanges(propertyCodePointRanges);
        UnicodeDataTemplateController.addTR35ExtendedPictographicPropertyCodesToCodePointRanges(propertyCodePointRanges);
        UnicodeDataTemplateController.addEmojiPresentationPropertyCodesToCodePointRanges(propertyCodePointRanges);
        LinkedHashMap<String, String> propertyAliases = new LinkedHashMap<String, String>();
        UnicodeDataTemplateController.addUnicodeCategoryCodesToNames(propertyAliases);
        UnicodeDataTemplateController.addUnicodeBinaryPropertyCodesToNames(propertyAliases);
        UnicodeDataTemplateController.addUnicodeScriptCodesToNames(propertyAliases);
        UnicodeDataTemplateController.addUnicodeBlocksToNames(propertyAliases);
        UnicodeDataTemplateController.addUnicodeIntPropertyCodesToNames(propertyAliases);
        propertyAliases.put("EP", "Extended_Pictographic");
        LinkedHashMap<String, Object> properties = new LinkedHashMap<String, Object>();
        properties.put("propertyCodePointRanges", propertyCodePointRanges);
        properties.put("propertyAliases", propertyAliases);
        return properties;
    }

    private static String getShortPropertyName(int property) {
        String propertyName = UCharacter.getPropertyName((int)property, (int)0);
        if (propertyName == null) {
            propertyName = UCharacter.getPropertyName((int)property, (int)1);
        }
        return propertyName;
    }

    private static void addUnicodeCategoryCodesToCodePointRanges(Map<String, IntervalSet> propertyCodePointRanges) {
        RangeValueIterator iter = UCharacter.getTypeIterator();
        RangeValueIterator.Element element = new RangeValueIterator.Element();
        while (iter.next(element)) {
            String categoryName = UCharacter.getPropertyValueName((int)8192, (int)(1 << element.value), (int)0);
            UnicodeDataTemplateController.addIntervalForCategory(propertyCodePointRanges, categoryName, element.start, element.limit - 1);
            String shortCategoryName = categoryName.substring(0, 1);
            UnicodeDataTemplateController.addIntervalForCategory(propertyCodePointRanges, shortCategoryName, element.start, element.limit - 1);
        }
    }

    private static void addUnicodeCategoryCodesToNames(Map<String, String> propertyAliases) {
        RangeValueIterator iter = UCharacter.getTypeIterator();
        RangeValueIterator.Element element = new RangeValueIterator.Element();
        block2: while (iter.next(element)) {
            int generalCategoryMask = 1 << element.value;
            String categoryName = UCharacter.getPropertyValueName((int)8192, (int)generalCategoryMask, (int)0);
            int nameChoice = 1;
            while (true) {
                String alias;
                try {
                    alias = UCharacter.getPropertyValueName((int)8192, (int)generalCategoryMask, (int)nameChoice);
                }
                catch (IllegalArgumentException e) {
                    continue block2;
                }
                assert (alias != null);
                UnicodeDataTemplateController.addPropertyAlias(propertyAliases, alias, categoryName);
                ++nameChoice;
            }
        }
        UnicodeDataTemplateController.addPropertyAlias(propertyAliases, "Control", "C");
        UnicodeDataTemplateController.addPropertyAlias(propertyAliases, "Letter", "L");
        UnicodeDataTemplateController.addPropertyAlias(propertyAliases, "Number", "N");
        UnicodeDataTemplateController.addPropertyAlias(propertyAliases, "Mark", "M");
        UnicodeDataTemplateController.addPropertyAlias(propertyAliases, "Punctuation", "P");
        UnicodeDataTemplateController.addPropertyAlias(propertyAliases, "Symbol", "S");
        UnicodeDataTemplateController.addPropertyAlias(propertyAliases, "Space", "Z");
    }

    private static void addUnicodeBinaryPropertyCodesToCodePointRanges(Map<String, IntervalSet> propertyCodePointRanges) {
        for (int property = 0; property < 61; ++property) {
            String propertyName = UnicodeDataTemplateController.getShortPropertyName(property);
            IntervalSet intervalSet = new IntervalSet(new int[0]);
            UnicodeSet unicodeSet = new UnicodeSet();
            unicodeSet.applyIntPropertyValue(property, 1);
            for (UnicodeSet.EntryRange range : unicodeSet.ranges()) {
                intervalSet.add(range.codepoint, range.codepointEnd);
            }
            propertyCodePointRanges.put(propertyName, intervalSet);
        }
    }

    private static void addUnicodeBinaryPropertyCodesToNames(Map<String, String> propertyAliases) {
        for (int property = 0; property < 61; ++property) {
            String propertyName = UnicodeDataTemplateController.getShortPropertyName(property);
            UnicodeDataTemplateController.addPropertyAliases(propertyAliases, propertyName, property);
        }
    }

    private static void addIntPropertyRanges(int property, String namePrefix, Map<String, IntervalSet> propertyCodePointRanges) {
        for (int propertyValue = UCharacter.getIntPropertyMinValue((int)property); propertyValue <= UCharacter.getIntPropertyMaxValue((int)property); ++propertyValue) {
            UnicodeSet set = new UnicodeSet();
            set.applyIntPropertyValue(property, propertyValue);
            String propertyName = namePrefix + UCharacter.getPropertyValueName((int)property, (int)propertyValue, (int)0);
            IntervalSet intervalSet = propertyCodePointRanges.get(propertyName);
            if (intervalSet == null) {
                intervalSet = new IntervalSet(new int[0]);
                propertyCodePointRanges.put(propertyName, intervalSet);
            }
            UnicodeDataTemplateController.addUnicodeSetToIntervalSet(set, intervalSet);
        }
    }

    private static void addUnicodeSetToIntervalSet(UnicodeSet unicodeSet, IntervalSet intervalSet) {
        for (UnicodeSet.EntryRange range : unicodeSet.ranges()) {
            intervalSet.add(range.codepoint, range.codepointEnd);
        }
    }

    private static void addUnicodeIntPropertyCodesToCodePointRanges(Map<String, IntervalSet> propertyCodePointRanges) {
        for (int property = 4096; property < 4118; ++property) {
            String propertyName = UnicodeDataTemplateController.getShortPropertyName(property);
            UnicodeDataTemplateController.addIntPropertyRanges(property, propertyName + "=", propertyCodePointRanges);
        }
    }

    private static void addTR35ExtendedPictographicPropertyCodesToCodePointRanges(Map<String, IntervalSet> propertyCodePointRanges) {
        IntervalSet set = new IntervalSet(new int[0]);
        set.add(128884, 128895);
        set.add(9984, 9985);
        set.add(9987, 9988);
        set.add(9998);
        set.add(10000, 10001);
        set.add(10085, 10087);
        set.add(127024, 127123);
        set.add(127124, 127135);
        set.add(127245, 127247);
        set.add(127279);
        set.add(127340, 127343);
        set.add(127405, 127461);
        set.add(127584, 127589);
        set.add(127491, 127503);
        set.add(127548, 127551);
        set.add(127561, 127567);
        set.add(127570, 127583);
        set.add(127590, 127743);
        set.add(128981, 129023);
        set.add(126976, 126979);
        set.add(126981, 127019);
        set.add(127020, 127023);
        set.add(127778, 127779);
        set.add(127892, 127893);
        set.add(127896);
        set.add(127900, 127901);
        set.add(127985, 127986);
        set.add(127990);
        set.add(128254);
        set.add(128318, 128328);
        set.add(128335);
        set.add(128360, 128366);
        set.add(128369, 128370);
        set.add(128379, 128390);
        set.add(128392, 128393);
        set.add(128398, 128399);
        set.add(128401, 128404);
        set.add(128407, 128419);
        set.add(128422, 128423);
        set.add(128425, 128432);
        set.add(128435, 128443);
        set.add(128445, 128449);
        set.add(128453, 128464);
        set.add(128468, 128475);
        set.add(128479, 128480);
        set.add(128482);
        set.add(128484, 128487);
        set.add(128489, 128494);
        set.add(128496, 128498);
        set.add(128500, 128505);
        set.add(9733);
        set.add(9735, 9741);
        set.add(9743, 9744);
        set.add(9746);
        set.add(9750, 9751);
        set.add(9753, 9756);
        set.add(9758, 9759);
        set.add(9761);
        set.add(9764, 9765);
        set.add(9767, 9769);
        set.add(9771, 9773);
        set.add(9776, 9783);
        set.add(9787, 9799);
        set.add(9812, 9823);
        set.add(9825, 9826);
        set.add(9828);
        set.add(9831);
        set.add(9833, 9850);
        set.add(9852, 9854);
        set.add(9856, 9873);
        set.add(9877);
        set.add(9880);
        set.add(9882);
        set.add(9885, 9887);
        set.add(9890, 9897);
        set.add(9900, 9903);
        set.add(9906, 9916);
        set.add(9919, 9923);
        set.add(9926, 9927);
        set.add(9929, 9933);
        set.add(9936);
        set.add(9938);
        set.add(9941, 9960);
        set.add(9963, 9967);
        set.add(9974);
        set.add(9979, 9980);
        set.add(9982, 9983);
        set.add(9096);
        set.add(129536, 131069);
        set.add(127136, 127150);
        set.add(127153, 127167);
        set.add(127169, 127183);
        set.add(127185, 127221);
        set.add(127151, 127152);
        set.add(127168);
        set.add(127184);
        set.add(127222, 127231);
        set.add(129036, 129039);
        set.add(129096, 129103);
        set.add(129114, 129119);
        set.add(129160, 129167);
        set.add(129198, 129279);
        set.add(129280, 129291);
        set.add(129311);
        set.add(129320, 129327);
        set.add(129329, 129330);
        set.add(129356);
        set.add(129375, 129387);
        set.add(129426, 129431);
        set.add(129488, 129510);
        set.add(129292, 129295);
        set.add(129343);
        set.add(129357, 129359);
        set.add(129388, 129407);
        set.add(129432, 129471);
        set.add(129473, 129487);
        set.add(129511, 129535);
        set.add(128710, 128714);
        set.add(128723, 128724);
        set.add(128742, 128744);
        set.add(128746);
        set.add(128753, 128754);
        set.add(128759, 128760);
        set.add(128725, 128735);
        set.add(128749, 128751);
        set.add(128761, 128767);
        propertyCodePointRanges.put("Extended_Pictographic", set);
        UnicodeSet emojiRKUnicodeSet = new UnicodeSet("[\\p{GCB=Regional_Indicator}\\*#0-9\\u00a9\\u00ae\\u2122\\u3030\\u303d]");
        IntervalSet emojiRKIntervalSet = new IntervalSet(new int[0]);
        UnicodeDataTemplateController.addUnicodeSetToIntervalSet(emojiRKUnicodeSet, emojiRKIntervalSet);
        propertyCodePointRanges.put("EmojiRK", emojiRKIntervalSet);
        UnicodeSet emojiNRKUnicodeSet = new UnicodeSet("[\\p{Emoji=Yes}]");
        emojiNRKUnicodeSet.removeAll(emojiRKUnicodeSet);
        IntervalSet emojiNRKIntervalSet = new IntervalSet(new int[0]);
        UnicodeDataTemplateController.addUnicodeSetToIntervalSet(emojiNRKUnicodeSet, emojiNRKIntervalSet);
        propertyCodePointRanges.put("EmojiNRK", emojiNRKIntervalSet);
    }

    private static void addEmojiPresentationPropertyCodesToCodePointRanges(Map<String, IntervalSet> propertyCodePointRanges) {
        UnicodeSet emojiDefaultUnicodeSet = new UnicodeSet("[[\\p{Emoji=Yes}]&[\\p{Emoji_Presentation=Yes}]]");
        IntervalSet emojiDefaultIntervalSet = new IntervalSet(new int[0]);
        UnicodeDataTemplateController.addUnicodeSetToIntervalSet(emojiDefaultUnicodeSet, emojiDefaultIntervalSet);
        propertyCodePointRanges.put("EmojiPresentation=EmojiDefault", emojiDefaultIntervalSet);
        UnicodeSet textDefaultUnicodeSet = new UnicodeSet("[[\\p{Emoji=Yes}]&[\\p{Emoji_Presentation=No}]]");
        IntervalSet textDefaultIntervalSet = new IntervalSet(new int[0]);
        UnicodeDataTemplateController.addUnicodeSetToIntervalSet(textDefaultUnicodeSet, textDefaultIntervalSet);
        propertyCodePointRanges.put("EmojiPresentation=TextDefault", textDefaultIntervalSet);
        UnicodeSet textUnicodeSet = new UnicodeSet("[\\p{Emoji=No}]");
        IntervalSet textIntervalSet = new IntervalSet(new int[0]);
        UnicodeDataTemplateController.addUnicodeSetToIntervalSet(textUnicodeSet, textIntervalSet);
        propertyCodePointRanges.put("EmojiPresentation=Text", textIntervalSet);
    }

    private static void addIntPropertyAliases(int property, String namePrefix, Map<String, String> propertyAliases) {
        String propertyName = UnicodeDataTemplateController.getShortPropertyName(property);
        block2: for (int propertyValue = UCharacter.getIntPropertyMinValue((int)property); propertyValue <= UCharacter.getIntPropertyMaxValue((int)property); ++propertyValue) {
            String aliasTarget = propertyName + "=" + UCharacter.getPropertyValueName((int)property, (int)propertyValue, (int)0);
            int nameChoice = 0;
            while (true) {
                String alias;
                try {
                    alias = namePrefix + UCharacter.getPropertyValueName((int)property, (int)propertyValue, (int)nameChoice);
                }
                catch (IllegalArgumentException e) {
                    continue block2;
                }
                assert (alias != null);
                UnicodeDataTemplateController.addPropertyAlias(propertyAliases, alias, aliasTarget);
                ++nameChoice;
            }
        }
    }

    private static void addUnicodeScriptCodesToNames(Map<String, String> propertyAliases) {
        UnicodeDataTemplateController.addIntPropertyAliases(4106, "", propertyAliases);
    }

    private static void addUnicodeBlocksToNames(Map<String, String> propertyAliases) {
        UnicodeDataTemplateController.addIntPropertyAliases(4097, "In", propertyAliases);
    }

    private static void addUnicodeIntPropertyCodesToNames(Map<String, String> propertyAliases) {
        block2: for (int property = 4096; property < 4118; ++property) {
            int nameChoice = 1;
            while (true) {
                String propertyNameAlias;
                try {
                    propertyNameAlias = UCharacter.getPropertyName((int)property, (int)nameChoice);
                }
                catch (IllegalArgumentException e) {
                    continue block2;
                }
                UnicodeDataTemplateController.addIntPropertyAliases(property, propertyNameAlias + "=", propertyAliases);
                ++nameChoice;
            }
        }
    }
}

