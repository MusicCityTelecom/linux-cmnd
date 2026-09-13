/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip;

import com.android.tools.build.apkzlib.zip.VerifyLog;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;

final class VerifyLogs {
    private VerifyLogs() {
    }

    static VerifyLog devNull() {
        return new VerifyLog(){

            @Override
            public void log(String message) {
            }

            @Override
            public ImmutableList<String> getLogs() {
                return ImmutableList.of();
            }
        };
    }

    static VerifyLog unlimited() {
        return new VerifyLog(){
            private final List<String> messages = new ArrayList<String>();

            @Override
            public void log(String message) {
                this.messages.add(message);
            }

            @Override
            public ImmutableList<String> getLogs() {
                return ImmutableList.copyOf(this.messages);
            }
        };
    }
}

