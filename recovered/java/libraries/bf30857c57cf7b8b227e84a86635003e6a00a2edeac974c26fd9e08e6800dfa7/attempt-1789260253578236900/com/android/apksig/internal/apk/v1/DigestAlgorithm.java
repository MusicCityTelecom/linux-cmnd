/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.internal.apk.v1;

import java.util.Comparator;

public enum DigestAlgorithm {
    SHA1("SHA-1"),
    SHA256("SHA-256");

    private final String mJcaMessageDigestAlgorithm;
    public static Comparator<DigestAlgorithm> BY_STRENGTH_COMPARATOR;

    private DigestAlgorithm(String jcaMessageDigestAlgoritm) {
        this.mJcaMessageDigestAlgorithm = jcaMessageDigestAlgoritm;
    }

    String getJcaMessageDigestAlgorithm() {
        return this.mJcaMessageDigestAlgorithm;
    }

    static {
        BY_STRENGTH_COMPARATOR = new StrengthComparator();
    }

    private static class StrengthComparator
    implements Comparator<DigestAlgorithm> {
        private StrengthComparator() {
        }

        @Override
        public int compare(DigestAlgorithm a12, DigestAlgorithm a2) {
            switch (a12) {
                case SHA1: {
                    switch (a2) {
                        case SHA1: {
                            return 0;
                        }
                        case SHA256: {
                            return -1;
                        }
                    }
                    throw new RuntimeException("Unsupported algorithm: " + (Object)((Object)a2));
                }
                case SHA256: {
                    switch (a2) {
                        case SHA1: {
                            return 1;
                        }
                        case SHA256: {
                            return 0;
                        }
                    }
                    throw new RuntimeException("Unsupported algorithm: " + (Object)((Object)a2));
                }
            }
            throw new RuntimeException("Unsupported algorithm: " + (Object)((Object)a12));
        }
    }
}

