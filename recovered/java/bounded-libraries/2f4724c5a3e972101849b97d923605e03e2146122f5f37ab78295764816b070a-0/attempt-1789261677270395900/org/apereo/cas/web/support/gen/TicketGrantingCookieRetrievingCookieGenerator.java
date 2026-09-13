/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.web.cookie.CookieGenerationContext
 *  org.apereo.cas.web.cookie.CookieValueManager
 */
package org.apereo.cas.web.support.gen;

import org.apereo.cas.web.cookie.CookieGenerationContext;
import org.apereo.cas.web.cookie.CookieValueManager;
import org.apereo.cas.web.support.gen.CookieRetrievingCookieGenerator;

public class TicketGrantingCookieRetrievingCookieGenerator
extends CookieRetrievingCookieGenerator {
    private static final long serialVersionUID = -1239028220717183717L;

    public TicketGrantingCookieRetrievingCookieGenerator(CookieGenerationContext context, CookieValueManager casCookieValueManager) {
        super(context, casCookieValueManager);
    }
}

