/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.BeanDefinitionStoreException
 *  org.springframework.beans.factory.NoSuchBeanDefinitionException
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationListener
 *  org.springframework.context.event.ApplicationContextEvent
 *  org.springframework.context.event.ContextClosedEvent
 *  org.springframework.context.event.ContextRefreshedEvent
 *  org.springframework.messaging.MessageHeaders
 *  org.springframework.util.IdGenerator
 *  org.springframework.util.ReflectionUtils
 */
package org.springframework.integration.config;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.messaging.MessageHeaders;
import org.springframework.util.IdGenerator;
import org.springframework.util.ReflectionUtils;

public final class IdGeneratorConfigurer
implements ApplicationListener<ApplicationContextEvent> {
    private static final Set<String> GENERATOR_CONTEXT_ID = new HashSet<String>();
    private static volatile IdGenerator theIdGenerator;
    private final Log logger = LogFactory.getLog(this.getClass());

    public synchronized void onApplicationEvent(ApplicationContextEvent event) {
        ApplicationContext context = event.getApplicationContext();
        if (event instanceof ContextRefreshedEvent) {
            boolean contextHasIdGenerator;
            boolean bl = contextHasIdGenerator = context.getBeanNamesForType(IdGenerator.class).length > 0;
            if (contextHasIdGenerator && this.setIdGenerator(context)) {
                GENERATOR_CONTEXT_ID.add(context.getId());
            }
        } else if (event instanceof ContextClosedEvent && GENERATOR_CONTEXT_ID.contains(context.getId())) {
            if (GENERATOR_CONTEXT_ID.size() == 1) {
                this.unsetIdGenerator();
            }
            GENERATOR_CONTEXT_ID.remove(context.getId());
        }
    }

    private boolean setIdGenerator(ApplicationContext context) {
        try {
            IdGenerator idGeneratorBean = (IdGenerator)context.getBean(IdGenerator.class);
            if (this.logger.isDebugEnabled()) {
                this.logger.debug((Object)("using custom MessageHeaders.IdGenerator [" + idGeneratorBean.getClass() + "]"));
            }
            Field idGeneratorField = ReflectionUtils.findField(MessageHeaders.class, (String)"idGenerator");
            ReflectionUtils.makeAccessible((Field)idGeneratorField);
            IdGenerator currentIdGenerator = (IdGenerator)ReflectionUtils.getField((Field)idGeneratorField, null);
            if (currentIdGenerator != null) {
                if (currentIdGenerator.equals(idGeneratorBean)) {
                    return false;
                }
                if (theIdGenerator.getClass() == idGeneratorBean.getClass()) {
                    if (this.logger.isWarnEnabled()) {
                        this.logger.warn((Object)("Another instance of " + idGeneratorBean.getClass() + " has already been established; ignoring"));
                    }
                    return true;
                }
                throw new BeanDefinitionStoreException("'MessageHeaders.idGenerator' has already been set and can not be set again");
            }
            if (this.logger.isInfoEnabled()) {
                this.logger.info((Object)("Message IDs will be generated using custom IdGenerator [" + idGeneratorBean.getClass() + "]"));
            }
            ReflectionUtils.setField((Field)idGeneratorField, null, (Object)idGeneratorBean);
            theIdGenerator = idGeneratorBean;
        }
        catch (NoSuchBeanDefinitionException e) {
            this.noSuchBean(context);
            return false;
        }
        catch (IllegalStateException e) {
            this.illegalState(e);
            return false;
        }
        return true;
    }

    private void noSuchBean(ApplicationContext context) {
        int idBeans = context.getBeansOfType(IdGenerator.class).size();
        if (idBeans > 1 && this.logger.isWarnEnabled()) {
            this.logger.warn((Object)("Found too many 'IdGenerator' beans (" + idBeans + ") Will use the existing UUID strategy."));
        } else if (this.logger.isDebugEnabled()) {
            this.logger.debug((Object)"Unable to locate MessageHeaders.IdGenerator. Will use the existing UUID strategy.");
        }
    }

    private void illegalState(IllegalStateException e) {
        if (this.logger.isWarnEnabled()) {
            this.logger.warn((Object)"Unexpected exception occurred while accessing idGenerator of MessageHeaders. Will use the existing UUID strategy.", (Throwable)e);
        }
    }

    private void unsetIdGenerator() {
        block2: {
            try {
                Field idGeneratorField = ReflectionUtils.findField(MessageHeaders.class, (String)"idGenerator");
                ReflectionUtils.makeAccessible((Field)idGeneratorField);
                idGeneratorField.set(null, null);
                theIdGenerator = null;
            }
            catch (Exception e) {
                if (!this.logger.isWarnEnabled()) break block2;
                this.logger.warn((Object)"Unexpected exception occurred while accessing idGenerator of MessageHeaders.", (Throwable)e);
            }
        }
    }
}

