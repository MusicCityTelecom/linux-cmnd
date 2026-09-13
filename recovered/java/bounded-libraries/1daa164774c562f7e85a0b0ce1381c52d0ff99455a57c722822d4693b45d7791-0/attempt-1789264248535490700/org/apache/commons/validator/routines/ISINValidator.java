/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.validator.routines;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Locale;
import org.apache.commons.validator.routines.CodeValidator;
import org.apache.commons.validator.routines.checkdigit.ISINCheckDigit;

public class ISINValidator
implements Serializable {
    private static final long serialVersionUID = -5964391439144260936L;
    private static final String ISIN_REGEX = "([A-Z]{2}[A-Z0-9]{9}[0-9])";
    private static final CodeValidator VALIDATOR = new CodeValidator("([A-Z]{2}[A-Z0-9]{9}[0-9])", 12, ISINCheckDigit.ISIN_CHECK_DIGIT);
    private static final ISINValidator ISIN_VALIDATOR_FALSE = new ISINValidator(false);
    private static final ISINValidator ISIN_VALIDATOR_TRUE = new ISINValidator(true);
    private static final String[] CCODES = Locale.getISOCountries();
    private static final String[] SPECIALS = new String[]{"EZ", "XS"};
    private final boolean checkCountryCode;

    public static ISINValidator getInstance(boolean checkCountryCode) {
        return checkCountryCode ? ISIN_VALIDATOR_TRUE : ISIN_VALIDATOR_FALSE;
    }

    private ISINValidator(boolean checkCountryCode) {
        this.checkCountryCode = checkCountryCode;
    }

    public boolean isValid(String code) {
        boolean valid = VALIDATOR.isValid(code);
        if (valid && this.checkCountryCode) {
            return this.checkCode(code.substring(0, 2));
        }
        return valid;
    }

    public Object validate(String code) {
        Object validate = VALIDATOR.validate(code);
        if (validate != null && this.checkCountryCode) {
            return this.checkCode(code.substring(0, 2)) ? validate : null;
        }
        return validate;
    }

    private boolean checkCode(String code) {
        return Arrays.binarySearch(CCODES, code) >= 0 || Arrays.binarySearch(SPECIALS, code) >= 0;
    }

    static {
        Arrays.sort(CCODES);
        Arrays.sort(SPECIALS);
    }
}

