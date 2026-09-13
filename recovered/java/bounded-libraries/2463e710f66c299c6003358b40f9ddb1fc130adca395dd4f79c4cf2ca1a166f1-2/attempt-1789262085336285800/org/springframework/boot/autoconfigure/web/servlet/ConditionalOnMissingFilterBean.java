/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.Filter
 *  org.springframework.boot.web.servlet.FilterRegistrationBean
 *  org.springframework.core.annotation.AliasFor
 */
package org.springframework.boot.autoconfigure.web.servlet;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.servlet.Filter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.core.annotation.AliasFor;

@Target(value={ElementType.TYPE, ElementType.METHOD})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
@ConditionalOnMissingBean(parameterizedContainer={FilterRegistrationBean.class})
public @interface ConditionalOnMissingFilterBean {
    @AliasFor(annotation=ConditionalOnMissingBean.class)
    public Class<? extends Filter>[] value() default {};
}

