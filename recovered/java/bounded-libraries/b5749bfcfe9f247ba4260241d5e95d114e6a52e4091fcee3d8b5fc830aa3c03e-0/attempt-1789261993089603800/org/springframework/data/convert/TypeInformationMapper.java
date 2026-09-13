/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.data.convert;

import org.springframework.data.mapping.Alias;
import org.springframework.data.util.TypeInformation;
import org.springframework.lang.Nullable;

public interface TypeInformationMapper {
    @Nullable
    public TypeInformation<?> resolveTypeFrom(Alias var1);

    public Alias createAliasFor(TypeInformation<?> var1);
}

