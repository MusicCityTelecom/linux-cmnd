/*
 * Decompiled with CFR 0.152.
 */
package org.quartz;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.PersistJobDataAfterExecution;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public interface StatefulJob
extends Job {
}

