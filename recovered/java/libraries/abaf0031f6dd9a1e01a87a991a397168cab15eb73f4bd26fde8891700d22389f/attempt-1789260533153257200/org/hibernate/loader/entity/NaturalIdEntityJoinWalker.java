/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.loader.entity;

import java.util.ArrayList;
import java.util.Arrays;
import org.hibernate.LockOptions;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.LoadQueryInfluencers;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.internal.util.collections.ArrayHelper;
import org.hibernate.loader.entity.EntityJoinWalker;
import org.hibernate.persister.entity.Loadable;
import org.hibernate.persister.entity.OuterJoinLoadable;

public class NaturalIdEntityJoinWalker
extends EntityJoinWalker {
    private static String[] naturalIdColumns(Loadable persister, boolean[] valueNullness) {
        int i = 0;
        ArrayList<String> columns = new ArrayList<String>();
        for (int p : persister.getNaturalIdentifierProperties()) {
            if (valueNullness[i++]) continue;
            columns.addAll(Arrays.asList(persister.getPropertyColumnNames(p)));
        }
        return columns.toArray(ArrayHelper.EMPTY_STRING_ARRAY);
    }

    public NaturalIdEntityJoinWalker(OuterJoinLoadable persister, boolean[] valueNullness, int batchSize, LockOptions lockOptions, SessionFactoryImplementor factory, LoadQueryInfluencers loadQueryInfluencers) throws MappingException {
        super(persister, NaturalIdEntityJoinWalker.naturalIdColumns(persister, valueNullness), batchSize, lockOptions, factory, loadQueryInfluencers);
        StringBuilder sql = new StringBuilder(this.getSQLString());
        for (String nullCol : NaturalIdEntityJoinWalker.naturalIdColumns(this.getPersister(), ArrayHelper.negate(valueNullness))) {
            sql.append(" and ").append(this.getAlias()).append('.').append(nullCol).append(" is null");
        }
        this.setSql(sql.toString());
    }
}

