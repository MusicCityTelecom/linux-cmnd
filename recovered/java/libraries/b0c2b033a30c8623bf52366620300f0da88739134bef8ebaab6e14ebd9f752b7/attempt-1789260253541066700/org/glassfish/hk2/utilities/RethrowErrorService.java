/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities;

import javax.inject.Singleton;
import org.glassfish.hk2.api.ErrorInformation;
import org.glassfish.hk2.api.ErrorService;
import org.glassfish.hk2.api.ErrorType;
import org.glassfish.hk2.api.MultiException;

@Singleton
public class RethrowErrorService
implements ErrorService {
    @Override
    public void onFailure(ErrorInformation errorInformation) throws MultiException {
        if (ErrorType.FAILURE_TO_REIFY.equals((Object)errorInformation.getErrorType())) {
            MultiException me = errorInformation.getAssociatedException();
            if (me == null) {
                return;
            }
            throw me;
        }
    }
}

