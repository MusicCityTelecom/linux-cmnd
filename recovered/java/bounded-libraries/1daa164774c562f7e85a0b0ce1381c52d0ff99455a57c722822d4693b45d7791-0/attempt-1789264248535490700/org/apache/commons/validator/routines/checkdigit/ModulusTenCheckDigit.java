/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.validator.routines.checkdigit;

import java.util.Arrays;
import org.apache.commons.validator.routines.checkdigit.CheckDigitException;
import org.apache.commons.validator.routines.checkdigit.ModulusCheckDigit;

public final class ModulusTenCheckDigit
extends ModulusCheckDigit {
    private static final long serialVersionUID = -3752929983453368497L;
    private final int[] postitionWeight;
    private final boolean useRightPos;
    private final boolean sumWeightedDigits;

    public ModulusTenCheckDigit(int[] postitionWeight) {
        this(postitionWeight, false, false);
    }

    public ModulusTenCheckDigit(int[] postitionWeight, boolean useRightPos) {
        this(postitionWeight, useRightPos, false);
    }

    public ModulusTenCheckDigit(int[] postitionWeight, boolean useRightPos, boolean sumWeightedDigits) {
        super(10);
        this.postitionWeight = Arrays.copyOf(postitionWeight, postitionWeight.length);
        this.useRightPos = useRightPos;
        this.sumWeightedDigits = sumWeightedDigits;
    }

    @Override
    public boolean isValid(String code) {
        if (code == null || code.length() == 0) {
            return false;
        }
        if (!Character.isDigit(code.charAt(code.length() - 1))) {
            return false;
        }
        return super.isValid(code);
    }

    @Override
    protected int toInt(char character, int leftPos, int rightPos) throws CheckDigitException {
        int num = Character.getNumericValue(character);
        if (num < 0) {
            throw new CheckDigitException("Invalid Character[" + leftPos + "] = '" + character + "'");
        }
        return num;
    }

    @Override
    protected int weightedValue(int charValue, int leftPos, int rightPos) {
        int pos = this.useRightPos ? rightPos : leftPos;
        int weight = this.postitionWeight[(pos - 1) % this.postitionWeight.length];
        int weightedValue = charValue * weight;
        if (this.sumWeightedDigits) {
            weightedValue = ModulusCheckDigit.sumDigits(weightedValue);
        }
        return weightedValue;
    }

    public String toString() {
        return this.getClass().getSimpleName() + "[postitionWeight=" + Arrays.toString(this.postitionWeight) + ", useRightPos=" + this.useRightPos + ", sumWeightedDigits=" + this.sumWeightedDigits + "]";
    }
}

