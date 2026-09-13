/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.google.common.annotations.VisibleForTesting;
import java.security.KeyStore;
import java.util.function.Supplier;

public final class Password {
    private final Supplier<KeyStore.PasswordProtection> passwordSupplier;

    public Password(Supplier<KeyStore.PasswordProtection> passwordSupplier) {
        this.passwordSupplier = passwordSupplier;
    }

    @VisibleForTesting
    public static Password createForTest(String password) {
        return new Password(() -> new KeyStore.PasswordProtection(password.toCharArray()));
    }

    public final KeyStore.PasswordProtection getValue() {
        return this.passwordSupplier.get();
    }
}

