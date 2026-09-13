/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.japit.webservices;

import com.tpvision.smartinstall.japit.SecuredCmdControlManager;
import com.tpvision.smartinstall.japit.webservices.WebServiceCommandHandler;

public class SecuredCmdServiceHandler
extends WebServiceCommandHandler {
    @Override
    public String execute() {
        return SecuredCmdControlManager.parseReceiveSecuredCmdControl(true, this.commandDetails, this.device);
    }
}

