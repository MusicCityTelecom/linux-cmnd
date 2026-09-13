/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.inject.hk2;

import java.io.PrintWriter;
import java.io.StringWriter;
import javax.inject.Singleton;
import org.glassfish.hk2.api.ErrorInformation;
import org.glassfish.hk2.api.ErrorService;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.inject.hk2.LocalizationMessages;
import org.glassfish.jersey.internal.Errors;

public final class JerseyErrorService
implements ErrorService {
    @Override
    public void onFailure(final ErrorInformation error) throws MultiException {
        String msg;
        switch (error.getErrorType()) {
            case FAILURE_TO_REIFY: {
                msg = LocalizationMessages.HK_2_REIFICATION_ERROR(error.getDescriptor().getImplementation(), this.printStackTrace(error.getAssociatedException()));
                break;
            }
            default: {
                msg = LocalizationMessages.HK_2_UNKNOWN_ERROR(this.printStackTrace(error.getAssociatedException()));
            }
        }
        try {
            Errors.warning(error.getInjectee(), msg);
        }
        catch (IllegalStateException ex) {
            Errors.process(new Runnable(){

                @Override
                public void run() {
                    Errors.warning(this, LocalizationMessages.HK_2_FAILURE_OUTSIDE_ERROR_SCOPE());
                    Errors.warning(error.getInjectee(), msg);
                }
            });
        }
    }

    private String printStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    public static final class Binder
    extends AbstractBinder {
        @Override
        protected void configure() {
            this.bind(JerseyErrorService.class).to(ErrorService.class).in(Singleton.class);
        }
    }
}

