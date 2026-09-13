/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.simpl;

import java.io.Serializable;
import java.util.Comparator;
import org.quartz.Trigger;
import org.quartz.simpl.TriggerWrapper;

class TriggerWrapperComparator
implements Comparator<TriggerWrapper>,
Serializable {
    private static final long serialVersionUID = 8809557142191514261L;
    Trigger.TriggerTimeComparator ttc = new Trigger.TriggerTimeComparator();

    TriggerWrapperComparator() {
    }

    @Override
    public int compare(TriggerWrapper trig1, TriggerWrapper trig2) {
        return this.ttc.compare(trig1.trigger, trig2.trigger);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof TriggerWrapperComparator;
    }

    public int hashCode() {
        return super.hashCode();
    }
}

