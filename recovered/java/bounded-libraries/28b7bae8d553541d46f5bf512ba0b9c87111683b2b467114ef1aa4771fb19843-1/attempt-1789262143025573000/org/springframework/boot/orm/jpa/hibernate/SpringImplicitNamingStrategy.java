/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hibernate.boot.model.naming.Identifier
 *  org.hibernate.boot.model.naming.ImplicitJoinTableNameSource
 *  org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl
 */
package org.springframework.boot.orm.jpa.hibernate;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitJoinTableNameSource;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;

public class SpringImplicitNamingStrategy
extends ImplicitNamingStrategyJpaCompliantImpl {
    public Identifier determineJoinTableName(ImplicitJoinTableNameSource source) {
        String name = source.getOwningPhysicalTableName() + "_" + source.getAssociationOwningAttributePath().getProperty();
        return this.toIdentifier(name, source.getBuildingContext());
    }
}

