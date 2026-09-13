/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.ldap.core.ContextExecutor
 *  org.springframework.ldap.core.LdapOperations
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.ldap;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.ldap.core.ContextExecutor;
import org.springframework.ldap.core.LdapOperations;
import org.springframework.util.Assert;

public class LdapHealthIndicator
extends AbstractHealthIndicator {
    private static final ContextExecutor<String> versionContextExecutor = new VersionContextExecutor();
    private final LdapOperations ldapOperations;

    public LdapHealthIndicator(LdapOperations ldapOperations) {
        super("LDAP health check failed");
        Assert.notNull((Object)ldapOperations, (String)"LdapOperations must not be null");
        this.ldapOperations = ldapOperations;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        String version = (String)this.ldapOperations.executeReadOnly(versionContextExecutor);
        builder.up().withDetail("version", version);
    }

    private static class VersionContextExecutor
    implements ContextExecutor<String> {
        private VersionContextExecutor() {
        }

        public String executeWithContext(DirContext ctx) throws NamingException {
            Object version = ctx.getEnvironment().get("java.naming.ldap.version");
            if (version != null) {
                return (String)version;
            }
            return null;
        }
    }
}

