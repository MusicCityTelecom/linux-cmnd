/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.springframework.core.log.LogMessage
 */
package org.springframework.boot.context.config;

import org.apache.commons.logging.Log;
import org.springframework.boot.context.config.ConfigDataNotFoundException;
import org.springframework.core.log.LogMessage;

public enum ConfigDataNotFoundAction {
    FAIL{

        @Override
        void handle(Log logger, ConfigDataNotFoundException ex) {
            throw ex;
        }
    }
    ,
    IGNORE{

        @Override
        void handle(Log logger, ConfigDataNotFoundException ex) {
            logger.trace((Object)LogMessage.format((String)"Ignoring missing config data %s", (Object)ex.getReferenceDescription()));
        }
    };


    abstract void handle(Log var1, ConfigDataNotFoundException var2);
}

