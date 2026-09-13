/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.exceptions;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.FormatMethod;
import com.google.errorprone.annotations.FormatString;

public class DeviceNotFoundException
extends RuntimeException {
    @FormatMethod
    public DeviceNotFoundException(@FormatString String message, Object ... args) {
        super(String.format(Preconditions.checkNotNull(message), args));
    }

    public static class TooManyDevicesMatchedException
    extends DeviceNotFoundException {
        private final int matchedNumber;

        public int getMatchedNumber() {
            return this.matchedNumber;
        }

        public TooManyDevicesMatchedException(int matchedNumber) {
            super("Unable to find one device matching the given criteria. Matched %d devices.", matchedNumber);
            this.matchedNumber = matchedNumber;
        }
    }
}

