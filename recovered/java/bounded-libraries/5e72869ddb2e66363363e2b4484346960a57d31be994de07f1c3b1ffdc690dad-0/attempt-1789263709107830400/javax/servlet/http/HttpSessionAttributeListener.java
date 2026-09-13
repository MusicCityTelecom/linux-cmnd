/*
 * Decompiled with CFR 0.152.
 */
package javax.servlet.http;

import java.util.EventListener;
import javax.servlet.http.HttpSessionBindingEvent;

public interface HttpSessionAttributeListener
extends EventListener {
    default public void attributeAdded(HttpSessionBindingEvent event) {
    }

    default public void attributeRemoved(HttpSessionBindingEvent event) {
    }

    default public void attributeReplaced(HttpSessionBindingEvent event) {
    }
}

