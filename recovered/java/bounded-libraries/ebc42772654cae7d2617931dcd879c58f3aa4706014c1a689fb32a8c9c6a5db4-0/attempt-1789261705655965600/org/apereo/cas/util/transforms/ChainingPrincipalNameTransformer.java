/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.handler.PrincipalNameTransformer
 */
package org.apereo.cas.util.transforms;

import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.authentication.handler.PrincipalNameTransformer;

public class ChainingPrincipalNameTransformer
implements PrincipalNameTransformer {
    private List<PrincipalNameTransformer> transformers = new ArrayList<PrincipalNameTransformer>(0);

    public String transform(String formUserId) {
        String idToTransform = formUserId;
        for (PrincipalNameTransformer t : this.transformers) {
            idToTransform = t.transform(idToTransform);
        }
        return idToTransform;
    }

    public void addTransformer(PrincipalNameTransformer transformer) {
        this.transformers.add(transformer);
    }

    @Generated
    public String toString() {
        return "ChainingPrincipalNameTransformer(transformers=" + this.transformers + ")";
    }

    @Generated
    public List<PrincipalNameTransformer> getTransformers() {
        return this.transformers;
    }

    @Generated
    public ChainingPrincipalNameTransformer(List<PrincipalNameTransformer> transformers) {
        this.transformers = transformers;
    }

    @Generated
    public ChainingPrincipalNameTransformer() {
    }
}

