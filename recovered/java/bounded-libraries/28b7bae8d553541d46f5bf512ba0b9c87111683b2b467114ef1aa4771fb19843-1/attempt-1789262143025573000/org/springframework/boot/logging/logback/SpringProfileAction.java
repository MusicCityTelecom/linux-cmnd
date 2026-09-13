/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ch.qos.logback.core.joran.action.Action
 *  ch.qos.logback.core.joran.event.InPlayListener
 *  ch.qos.logback.core.joran.event.SaxEvent
 *  ch.qos.logback.core.joran.spi.ActionException
 *  ch.qos.logback.core.joran.spi.InterpretationContext
 *  ch.qos.logback.core.joran.spi.Interpreter
 *  ch.qos.logback.core.spi.PropertyContainer
 *  ch.qos.logback.core.util.OptionHelper
 *  org.springframework.core.env.Environment
 *  org.springframework.core.env.Profiles
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.logging.logback;

import ch.qos.logback.core.joran.action.Action;
import ch.qos.logback.core.joran.event.InPlayListener;
import ch.qos.logback.core.joran.event.SaxEvent;
import ch.qos.logback.core.joran.spi.ActionException;
import ch.qos.logback.core.joran.spi.InterpretationContext;
import ch.qos.logback.core.joran.spi.Interpreter;
import ch.qos.logback.core.spi.PropertyContainer;
import ch.qos.logback.core.util.OptionHelper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.xml.sax.Attributes;

class SpringProfileAction
extends Action
implements InPlayListener {
    private final Environment environment;
    private int depth = 0;
    private boolean acceptsProfile;
    private List<SaxEvent> events;

    SpringProfileAction(Environment environment) {
        this.environment = environment;
    }

    public void begin(InterpretationContext ic, String name, Attributes attributes) throws ActionException {
        ++this.depth;
        if (this.depth != 1) {
            return;
        }
        ic.pushObject((Object)this);
        this.acceptsProfile = this.acceptsProfiles(ic, attributes);
        this.events = new ArrayList<SaxEvent>();
        ic.addInPlayListener((InPlayListener)this);
    }

    private boolean acceptsProfiles(InterpretationContext ic, Attributes attributes) {
        if (this.environment == null) {
            return false;
        }
        String[] profileNames = StringUtils.trimArrayElements((String[])StringUtils.commaDelimitedListToStringArray((String)attributes.getValue("name")));
        if (profileNames.length == 0) {
            return false;
        }
        for (int i = 0; i < profileNames.length; ++i) {
            profileNames[i] = OptionHelper.substVars((String)profileNames[i], (PropertyContainer)ic, (PropertyContainer)this.context);
        }
        return this.environment.acceptsProfiles(Profiles.of((String[])profileNames));
    }

    public void end(InterpretationContext ic, String name) throws ActionException {
        --this.depth;
        if (this.depth != 0) {
            return;
        }
        ic.removeInPlayListener((InPlayListener)this);
        this.verifyAndPop(ic);
        if (this.acceptsProfile) {
            this.addEventsToPlayer(ic);
        }
    }

    private void verifyAndPop(InterpretationContext ic) {
        Object o = ic.peekObject();
        Assert.state((o != null ? 1 : 0) != 0, (String)"Unexpected null object on stack");
        Assert.isInstanceOf(SpringProfileAction.class, (Object)o, (String)"logback stack error");
        Assert.state((o == this ? 1 : 0) != 0, (String)"ProfileAction different than current one on stack");
        ic.popObject();
    }

    private void addEventsToPlayer(InterpretationContext ic) {
        Interpreter interpreter = ic.getJoranInterpreter();
        this.events.remove(0);
        this.events.remove(this.events.size() - 1);
        interpreter.getEventPlayer().addEventsDynamically(this.events, 1);
    }

    public void inPlay(SaxEvent event) {
        this.events.add(event);
    }
}

