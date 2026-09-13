/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.transaction.Transaction
 *  org.springframework.context.annotation.Import
 */
package org.springframework.boot.autoconfigure.transaction.jta;

import javax.transaction.Transaction;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.jta.AtomikosJtaConfiguration;
import org.springframework.boot.autoconfigure.transaction.jta.JndiJtaConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration(before={XADataSourceAutoConfiguration.class, ActiveMQAutoConfiguration.class, ArtemisAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@ConditionalOnClass(value={Transaction.class})
@ConditionalOnProperty(prefix="spring.jta", value={"enabled"}, matchIfMissing=true)
@Import(value={JndiJtaConfiguration.class, AtomikosJtaConfiguration.class})
public class JtaAutoConfiguration {
}

