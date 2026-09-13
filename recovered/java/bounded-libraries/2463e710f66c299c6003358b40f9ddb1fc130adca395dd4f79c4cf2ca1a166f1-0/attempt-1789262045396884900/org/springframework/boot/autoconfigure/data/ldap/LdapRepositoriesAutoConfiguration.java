/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.Import
 *  org.springframework.data.ldap.repository.LdapRepository
 *  org.springframework.data.ldap.repository.support.LdapRepositoryFactoryBean
 */
package org.springframework.boot.autoconfigure.data.ldap;

import javax.naming.ldap.LdapContext;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.ldap.LdapRepositoriesRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.data.ldap.repository.support.LdapRepositoryFactoryBean;

@AutoConfiguration
@ConditionalOnClass(value={LdapContext.class, LdapRepository.class})
@ConditionalOnProperty(prefix="spring.data.ldap.repositories", name={"enabled"}, havingValue="true", matchIfMissing=true)
@ConditionalOnMissingBean(value={LdapRepositoryFactoryBean.class})
@Import(value={LdapRepositoriesRegistrar.class})
public class LdapRepositoriesAutoConfiguration {
}

