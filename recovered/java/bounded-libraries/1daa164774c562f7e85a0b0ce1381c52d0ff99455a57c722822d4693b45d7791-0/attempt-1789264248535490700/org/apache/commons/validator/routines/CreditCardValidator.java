/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.validator.routines;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.validator.routines.CodeValidator;
import org.apache.commons.validator.routines.RegexValidator;
import org.apache.commons.validator.routines.checkdigit.CheckDigit;
import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;

public class CreditCardValidator
implements Serializable {
    private static final long serialVersionUID = 5955978921148959496L;
    private static final int MIN_CC_LENGTH = 12;
    private static final int MAX_CC_LENGTH = 19;
    public static final long NONE = 0L;
    public static final long AMEX = 1L;
    public static final long VISA = 2L;
    public static final long MASTERCARD = 4L;
    public static final long DISCOVER = 8L;
    public static final long DINERS = 16L;
    public static final long VPAY = 32L;
    @Deprecated
    public static final long MASTERCARD_PRE_OCT2016 = 64L;
    private final List<CodeValidator> cardTypes = new ArrayList<CodeValidator>();
    private static final CheckDigit LUHN_VALIDATOR = LuhnCheckDigit.LUHN_CHECK_DIGIT;
    public static final CodeValidator AMEX_VALIDATOR = new CodeValidator("^(3[47]\\d{13})$", LUHN_VALIDATOR);
    public static final CodeValidator DINERS_VALIDATOR = new CodeValidator("^(30[0-5]\\d{11}|3095\\d{10}|36\\d{12}|3[8-9]\\d{12})$", LUHN_VALIDATOR);
    private static final RegexValidator DISCOVER_REGEX = new RegexValidator(new String[]{"^(6011\\d{12,13})$", "^(64[4-9]\\d{13})$", "^(65\\d{14})$", "^(62[2-8]\\d{13})$"});
    public static final CodeValidator DISCOVER_VALIDATOR = new CodeValidator(DISCOVER_REGEX, LUHN_VALIDATOR);
    private static final RegexValidator MASTERCARD_REGEX = new RegexValidator(new String[]{"^(5[1-5]\\d{14})$", "^(2221\\d{12})$", "^(222[2-9]\\d{12})$", "^(22[3-9]\\d{13})$", "^(2[3-6]\\d{14})$", "^(27[01]\\d{13})$", "^(2720\\d{12})$"});
    public static final CodeValidator MASTERCARD_VALIDATOR = new CodeValidator(MASTERCARD_REGEX, LUHN_VALIDATOR);
    @Deprecated
    public static final CodeValidator MASTERCARD_VALIDATOR_PRE_OCT2016 = new CodeValidator("^(5[1-5]\\d{14})$", LUHN_VALIDATOR);
    public static final CodeValidator VISA_VALIDATOR = new CodeValidator("^(4)(\\d{12}|\\d{15})$", LUHN_VALIDATOR);
    public static final CodeValidator VPAY_VALIDATOR = new CodeValidator("^(4)(\\d{12,18})$", LUHN_VALIDATOR);

    public CreditCardValidator() {
        this(15L);
    }

    public CreditCardValidator(long options) {
        if (this.isOn(options, 2L)) {
            this.cardTypes.add(VISA_VALIDATOR);
        }
        if (this.isOn(options, 32L)) {
            this.cardTypes.add(VPAY_VALIDATOR);
        }
        if (this.isOn(options, 1L)) {
            this.cardTypes.add(AMEX_VALIDATOR);
        }
        if (this.isOn(options, 4L)) {
            this.cardTypes.add(MASTERCARD_VALIDATOR);
        }
        if (this.isOn(options, 64L)) {
            this.cardTypes.add(MASTERCARD_VALIDATOR_PRE_OCT2016);
        }
        if (this.isOn(options, 8L)) {
            this.cardTypes.add(DISCOVER_VALIDATOR);
        }
        if (this.isOn(options, 16L)) {
            this.cardTypes.add(DINERS_VALIDATOR);
        }
    }

    public CreditCardValidator(CodeValidator[] creditCardValidators) {
        if (creditCardValidators == null) {
            throw new IllegalArgumentException("Card validators are missing");
        }
        Collections.addAll(this.cardTypes, creditCardValidators);
    }

    public CreditCardValidator(CreditCardRange[] creditCardRanges) {
        if (creditCardRanges == null) {
            throw new IllegalArgumentException("Card ranges are missing");
        }
        Collections.addAll(this.cardTypes, CreditCardValidator.createRangeValidator(creditCardRanges, LUHN_VALIDATOR));
    }

    public CreditCardValidator(CodeValidator[] creditCardValidators, CreditCardRange[] creditCardRanges) {
        if (creditCardValidators == null) {
            throw new IllegalArgumentException("Card validators are missing");
        }
        if (creditCardRanges == null) {
            throw new IllegalArgumentException("Card ranges are missing");
        }
        Collections.addAll(this.cardTypes, creditCardValidators);
        Collections.addAll(this.cardTypes, CreditCardValidator.createRangeValidator(creditCardRanges, LUHN_VALIDATOR));
    }

    public static CreditCardValidator genericCreditCardValidator(int minLen, int maxLen) {
        return new CreditCardValidator(new CodeValidator[]{new CodeValidator("(\\d+)", minLen, maxLen, LUHN_VALIDATOR)});
    }

    public static CreditCardValidator genericCreditCardValidator(int length) {
        return CreditCardValidator.genericCreditCardValidator(length, length);
    }

    public static CreditCardValidator genericCreditCardValidator() {
        return CreditCardValidator.genericCreditCardValidator(12, 19);
    }

    public boolean isValid(String card) {
        if (card == null || card.length() == 0) {
            return false;
        }
        for (CodeValidator cardType : this.cardTypes) {
            if (!cardType.isValid(card)) continue;
            return true;
        }
        return false;
    }

    public Object validate(String card) {
        if (card == null || card.length() == 0) {
            return null;
        }
        Object result = null;
        for (CodeValidator cardType : this.cardTypes) {
            result = cardType.validate(card);
            if (result == null) continue;
            return result;
        }
        return null;
    }

    static boolean validLength(int valueLength, CreditCardRange range) {
        if (range.lengths != null) {
            for (int length : range.lengths) {
                if (valueLength != length) continue;
                return true;
            }
            return false;
        }
        return valueLength >= range.minLen && valueLength <= range.maxLen;
    }

    static CodeValidator createRangeValidator(final CreditCardRange[] creditCardRanges, CheckDigit digitCheck) {
        return new CodeValidator(new RegexValidator("(\\d+)"){
            private static final long serialVersionUID = 1L;
            private CreditCardRange[] ccr;
            {
                super(x0);
                this.ccr = (CreditCardRange[])creditCardRanges.clone();
            }

            @Override
            public String validate(String value) {
                if (super.match(value) != null) {
                    int length = value.length();
                    for (CreditCardRange range : this.ccr) {
                        if (!CreditCardValidator.validLength(length, range) || !(range.high == null ? value.startsWith(range.low) : range.low.compareTo(value) <= 0 && range.high.compareTo(value.substring(0, range.high.length())) >= 0)) continue;
                        return value;
                    }
                }
                return null;
            }

            @Override
            public boolean isValid(String value) {
                return this.validate(value) != null;
            }

            @Override
            public String[] match(String value) {
                return new String[]{this.validate(value)};
            }
        }, digitCheck);
    }

    private boolean isOn(long options, long flag) {
        return (options & flag) > 0L;
    }

    public static class CreditCardRange {
        final String low;
        final String high;
        final int minLen;
        final int maxLen;
        final int[] lengths;

        public CreditCardRange(String low, String high, int minLen, int maxLen) {
            this.low = low;
            this.high = high;
            this.minLen = minLen;
            this.maxLen = maxLen;
            this.lengths = null;
        }

        public CreditCardRange(String low, String high, int[] lengths) {
            this.low = low;
            this.high = high;
            this.minLen = -1;
            this.maxLen = -1;
            this.lengths = (int[])lengths.clone();
        }
    }
}

