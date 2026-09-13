/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.terracotta.toolkit.internal.ToolkitInternal
 */
package org.terracotta.quartz;

import org.terracotta.quartz.AbstractTerracottaJobStore;
import org.terracotta.quartz.PlainTerracottaJobStore;
import org.terracotta.quartz.TerracottaJobStoreExtensions;
import org.terracotta.toolkit.internal.ToolkitInternal;

public class TerracottaJobStore
extends AbstractTerracottaJobStore {
    @Override
    TerracottaJobStoreExtensions getRealStore(ToolkitInternal toolkit) {
        return new PlainTerracottaJobStore(toolkit);
    }
}

