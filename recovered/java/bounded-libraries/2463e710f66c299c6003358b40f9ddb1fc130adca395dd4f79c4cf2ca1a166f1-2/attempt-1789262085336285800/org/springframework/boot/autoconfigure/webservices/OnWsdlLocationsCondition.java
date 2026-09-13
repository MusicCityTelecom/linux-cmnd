/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.autoconfigure.webservices;

import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.OnPropertyListCondition;

class OnWsdlLocationsCondition
extends OnPropertyListCondition {
    OnWsdlLocationsCondition() {
        super("spring.webservices.wsdl-locations", () -> ConditionMessage.forCondition("WSDL locations", new Object[0]));
    }
}

