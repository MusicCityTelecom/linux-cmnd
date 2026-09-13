/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mongodb.reactivestreams.client.MongoClient
 *  org.springframework.beans.factory.FactoryBean
 *  org.springframework.core.annotation.Order
 *  org.springframework.data.mongodb.core.ReactiveMongoClientFactoryBean
 */
package org.springframework.boot.autoconfigure.data.mongo;

import com.mongodb.reactivestreams.client.MongoClient;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.autoconfigure.AbstractDependsOnBeanFactoryPostProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.ReactiveMongoClientFactoryBean;

@Order(value=0x7FFFFFFF)
public class ReactiveStreamsMongoClientDependsOnBeanFactoryPostProcessor
extends AbstractDependsOnBeanFactoryPostProcessor {
    public ReactiveStreamsMongoClientDependsOnBeanFactoryPostProcessor(Class<?> ... dependsOn) {
        super((Class<?>)MongoClient.class, (Class<? extends FactoryBean<?>>)ReactiveMongoClientFactoryBean.class, dependsOn);
    }
}

