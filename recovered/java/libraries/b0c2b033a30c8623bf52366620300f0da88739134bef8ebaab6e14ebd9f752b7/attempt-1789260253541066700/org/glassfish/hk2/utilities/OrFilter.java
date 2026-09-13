/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities;

import java.util.ArrayList;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.IndexedFilter;
import org.glassfish.hk2.utilities.general.GeneralUtilities;

public class OrFilter
implements Filter {
    private final ArrayList<Filter> allFilters;

    public OrFilter(Filter ... filters) {
        this.allFilters = new ArrayList(filters.length);
        for (Filter f : filters) {
            if (f == null) continue;
            this.allFilters.add(f);
        }
    }

    @Override
    public boolean matches(Descriptor d) {
        for (Filter filter : this.allFilters) {
            String contract;
            IndexedFilter iFilter;
            String name;
            if (filter instanceof IndexedFilter && ((name = (iFilter = (IndexedFilter)filter).getName()) != null && !GeneralUtilities.safeEquals(name, d.getName()) || (contract = iFilter.getAdvertisedContract()) != null && !d.getAdvertisedContracts().contains(contract)) || !filter.matches(d)) continue;
            return true;
        }
        return false;
    }
}

