/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.util.AsciiArtUtils
 *  org.apereo.cas.util.DateTimeUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.boot.context.event.ApplicationReadyEvent
 */
package org.apereo.cas.web;

import java.time.Instant;
import lombok.Generated;
import org.apereo.cas.util.AsciiArtUtils;
import org.apereo.cas.util.DateTimeUtils;
import org.apereo.cas.web.CasWebApplicationReadyListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;

public class CasWebApplicationReady
implements CasWebApplicationReadyListener {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CasWebApplicationReady.class);

    @Override
    public void handleApplicationReadyEvent(ApplicationReadyEvent event) {
        AsciiArtUtils.printAsciiArtReady((Logger)LOGGER, (String)"");
        LOGGER.info("Ready to process requests @ [{}]", (Object)DateTimeUtils.zonedDateTimeOf((Instant)Instant.ofEpochMilli(event.getTimestamp())));
    }
}

