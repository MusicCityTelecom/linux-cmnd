/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.util;

import java.io.IOException;
import java.io.InputStream;
import org.glassfish.jersey.internal.util.Closure;

public class Closing {
    private final InputStream in;

    public static Closing with(InputStream in) {
        return new Closing(in);
    }

    public Closing(InputStream in) {
        this.in = in;
    }

    public void invoke(Closure<InputStream> c) throws IOException {
        if (this.in == null) {
            return;
        }
        try {
            c.invoke(this.in);
        }
        finally {
            this.in.close();
        }
    }
}

