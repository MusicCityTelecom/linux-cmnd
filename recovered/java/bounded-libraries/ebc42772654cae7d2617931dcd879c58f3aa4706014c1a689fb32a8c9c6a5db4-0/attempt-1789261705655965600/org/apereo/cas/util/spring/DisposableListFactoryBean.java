/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jooq.lambda.Unchecked
 *  org.springframework.beans.factory.DisposableBean
 *  org.springframework.beans.factory.config.ListFactoryBean
 */
package org.apereo.cas.util.spring;

import java.util.ArrayList;
import java.util.List;
import org.jooq.lambda.Unchecked;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.ListFactoryBean;

public class DisposableListFactoryBean
extends ListFactoryBean {
    public DisposableListFactoryBean() {
        this.setSourceList(new ArrayList());
    }

    protected void destroyInstance(List list) {
        if (list != null) {
            list.forEach(Unchecked.consumer(postProcessor -> ((DisposableBean)postProcessor).destroy()));
        }
    }
}

