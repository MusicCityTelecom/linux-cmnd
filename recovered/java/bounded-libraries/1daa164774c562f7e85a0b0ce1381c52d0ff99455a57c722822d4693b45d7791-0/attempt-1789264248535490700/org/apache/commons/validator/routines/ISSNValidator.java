/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.validator.routines;

import java.io.Serializable;
import org.apache.commons.validator.routines.CodeValidator;
import org.apache.commons.validator.routines.checkdigit.CheckDigitException;
import org.apache.commons.validator.routines.checkdigit.EAN13CheckDigit;
import org.apache.commons.validator.routines.checkdigit.ISSNCheckDigit;

public class ISSNValidator
implements Serializable {
    private static final long serialVersionUID = 4319515687976420405L;
    private static final String ISSN_REGEX = "(?:ISSN )?(\\d{4})-(\\d{3}[0-9X])$";
    private static final int ISSN_LEN = 8;
    private static final String ISSN_PREFIX = "977";
    private static final String EAN_ISSN_REGEX = "^(977)(?:(\\d{10}))$";
    private static final int EAN_ISSN_LEN = 13;
    private static final CodeValidator VALIDATOR = new CodeValidator("(?:ISSN )?(\\d{4})-(\\d{3}[0-9X])$", 8, ISSNCheckDigit.ISSN_CHECK_DIGIT);
    private static final CodeValidator EAN_VALIDATOR = new CodeValidator("^(977)(?:(\\d{10}))$", 13, EAN13CheckDigit.EAN13_CHECK_DIGIT);
    private static final ISSNValidator ISSN_VALIDATOR = new ISSNValidator();

    public static ISSNValidator getInstance() {
        return ISSN_VALIDATOR;
    }

    public Object validateEan(String code) {
        return EAN_VALIDATOR.validate(code);
    }

    public boolean isValid(String code) {
        return VALIDATOR.isValid(code);
    }

    public Object validate(String code) {
        return VALIDATOR.validate(code);
    }

    public String convertToEAN13(String issn, String suffix) {
        if (suffix == null || !suffix.matches("\\d\\d")) {
            throw new IllegalArgumentException("Suffix must be two digits: '" + suffix + "'");
        }
        Object result = this.validate(issn);
        if (result == null) {
            return null;
        }
        String input = result.toString();
        String ean13 = ISSN_PREFIX + input.substring(0, input.length() - 1) + suffix;
        try {
            String checkDigit = EAN13CheckDigit.EAN13_CHECK_DIGIT.calculate(ean13);
            ean13 = ean13 + checkDigit;
            return ean13;
        }
        catch (CheckDigitException e) {
            throw new IllegalArgumentException("Check digit error for '" + ean13 + "' - " + e.getMessage());
        }
    }

    public String extractFromEAN13(String ean13) {
        String input = ean13.trim();
        if (input.length() != 13) {
            throw new IllegalArgumentException("Invalid length " + input.length() + " for '" + input + "'");
        }
        if (!input.startsWith(ISSN_PREFIX)) {
            throw new IllegalArgumentException("Prefix must be 977 to contain an ISSN: '" + ean13 + "'");
        }
        Object result = this.validateEan(input);
        if (result == null) {
            return null;
        }
        input = result.toString();
        try {
            String issnBase = input.substring(3, 10);
            String checkDigit = ISSNCheckDigit.ISSN_CHECK_DIGIT.calculate(issnBase);
            String issn = issnBase + checkDigit;
            return issn;
        }
        catch (CheckDigitException e) {
            throw new IllegalArgumentException("Check digit error for '" + ean13 + "' - " + e.getMessage());
        }
    }
}

