/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.service.exceptions;

import be.tpvision.smartcontrol.service.exceptions.AddGroupResult;

public class FailedToAddGroupException
extends RuntimeException {
    private static final long serialVersionUID = 6355830657316698924L;
    private AddGroupResult addGroupResult;

    public FailedToAddGroupException() {
        super("Failed to add group.");
    }

    public FailedToAddGroupException(String message) {
        super(message);
    }

    public FailedToAddGroupException(AddGroupResult addGroupResult) {
        this();
        this.addGroupResult = addGroupResult;
    }

    public AddGroupResult getAddGroupResult() {
        return this.addGroupResult;
    }
}

