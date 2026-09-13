/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.ldap.filter.AndFilter
 *  org.springframework.ldap.filter.Filter
 *  org.springframework.ldap.filter.OrFilter
 */
package org.apereo.services.persondir.support.ldap;

import org.apereo.services.persondir.support.QueryType;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.filter.OrFilter;

class LogicalFilterWrapper
implements Filter {
    private final QueryType queryType;
    private final AndFilter andFilter;
    private final OrFilter orFilter;
    private final Filter delegateFilter;

    public LogicalFilterWrapper(QueryType queryType) {
        this.queryType = queryType;
        switch (this.queryType) {
            case OR: {
                this.andFilter = null;
                this.orFilter = new OrFilter();
                this.delegateFilter = this.orFilter;
                break;
            }
            default: {
                this.andFilter = new AndFilter();
                this.orFilter = null;
                this.delegateFilter = this.andFilter;
            }
        }
    }

    public void append(Filter query) {
        switch (this.queryType) {
            case OR: {
                this.orFilter.or(query);
                break;
            }
            default: {
                this.andFilter.and(query);
            }
        }
    }

    public String encode() {
        return this.delegateFilter.encode();
    }

    public StringBuffer encode(StringBuffer buf) {
        return this.delegateFilter.encode(buf);
    }

    public boolean equals(Object o) {
        return this.delegateFilter.equals(o);
    }

    public int hashCode() {
        return this.delegateFilter.hashCode();
    }

    public String toString() {
        return this.delegateFilter.toString();
    }
}

