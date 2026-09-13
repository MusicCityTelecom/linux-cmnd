/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mongodb.client.MongoClient
 *  org.springframework.beans.factory.FactoryBean
 *  org.springframework.core.annotation.Order
 *  org.springframework.data.mongodb.core.MongoClientFactoryBean
 */
package org.springframework.boot.autoconfigure.data.mongo;

import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.autoconfigure.AbstractDependsOnBeanFactoryPostProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;

@Order(value=0x7FFFFFFF)
public class MongoClientDependsOnBeanFactoryPostProcessor
extends AbstractDependsOnBeanFactoryPostProcessor {
    public MongoClientDependsOnBeanFactoryPostProcessor(Class<?> ... dependsOn) {
        super((Class<?>)MongoClient.class, (Class<? extends FactoryBean<?>>)MongoClientFactoryBean.class, dependsOn);
    }
}

