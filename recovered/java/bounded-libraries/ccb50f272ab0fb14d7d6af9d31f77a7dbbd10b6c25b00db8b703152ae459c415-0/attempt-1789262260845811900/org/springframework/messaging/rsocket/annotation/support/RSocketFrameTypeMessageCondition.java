/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.rsocket.frame.FrameType
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 */
package org.springframework.messaging.rsocket.annotation.support;

import io.rsocket.frame.FrameType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.AbstractMessageCondition;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

public class RSocketFrameTypeMessageCondition
extends AbstractMessageCondition<RSocketFrameTypeMessageCondition> {
    public static final String FRAME_TYPE_HEADER = "rsocketFrameType";
    public static final RSocketFrameTypeMessageCondition CONNECT_CONDITION = new RSocketFrameTypeMessageCondition(FrameType.SETUP, FrameType.METADATA_PUSH);
    public static final RSocketFrameTypeMessageCondition REQUEST_FNF_OR_RESPONSE_CONDITION = new RSocketFrameTypeMessageCondition(FrameType.REQUEST_FNF, FrameType.REQUEST_RESPONSE);
    public static final RSocketFrameTypeMessageCondition REQUEST_RESPONSE_CONDITION = new RSocketFrameTypeMessageCondition(FrameType.REQUEST_RESPONSE);
    public static final RSocketFrameTypeMessageCondition REQUEST_STREAM_CONDITION = new RSocketFrameTypeMessageCondition(FrameType.REQUEST_STREAM);
    public static final RSocketFrameTypeMessageCondition REQUEST_CHANNEL_CONDITION = new RSocketFrameTypeMessageCondition(FrameType.REQUEST_CHANNEL);
    public static final RSocketFrameTypeMessageCondition EMPTY_CONDITION = new RSocketFrameTypeMessageCondition();
    @Deprecated
    public static final RSocketFrameTypeMessageCondition REQUEST_CONDITION = new RSocketFrameTypeMessageCondition(FrameType.REQUEST_FNF, FrameType.REQUEST_RESPONSE, FrameType.REQUEST_STREAM, FrameType.REQUEST_CHANNEL);
    private static final Map<String, RSocketFrameTypeMessageCondition> frameTypeConditionCache = CollectionUtils.newHashMap((int)FrameType.values().length);
    private final Set<FrameType> frameTypes;

    public RSocketFrameTypeMessageCondition(FrameType ... frameType) {
        this(Arrays.asList(frameType));
    }

    public RSocketFrameTypeMessageCondition(Collection<FrameType> frameTypes) {
        Assert.notEmpty(frameTypes, (String)"`frameTypes` are required");
        this.frameTypes = Collections.unmodifiableSet(new LinkedHashSet<FrameType>(frameTypes));
    }

    private RSocketFrameTypeMessageCondition() {
        this.frameTypes = Collections.emptySet();
    }

    public Set<FrameType> getFrameTypes() {
        return this.frameTypes;
    }

    @Override
    protected Collection<?> getContent() {
        return this.frameTypes;
    }

    @Override
    protected String getToStringInfix() {
        return " || ";
    }

    @Nullable
    public static FrameType getFrameType(Message<?> message) {
        return (FrameType)message.getHeaders().get(FRAME_TYPE_HEADER);
    }

    @Override
    public RSocketFrameTypeMessageCondition combine(RSocketFrameTypeMessageCondition other) {
        if (this.frameTypes.equals(other.frameTypes)) {
            return other;
        }
        LinkedHashSet<FrameType> set = new LinkedHashSet<FrameType>(this.frameTypes);
        set.addAll(other.frameTypes);
        return new RSocketFrameTypeMessageCondition(set);
    }

    @Override
    public RSocketFrameTypeMessageCondition getMatchingCondition(Message<?> message) {
        FrameType actual = message.getHeaders().get(FRAME_TYPE_HEADER, FrameType.class);
        if (actual != null) {
            for (FrameType type : this.frameTypes) {
                if (actual != type) continue;
                return frameTypeConditionCache.get(type.name());
            }
        }
        return null;
    }

    @Override
    public int compareTo(RSocketFrameTypeMessageCondition other, Message<?> message) {
        return other.frameTypes.size() - this.frameTypes.size();
    }

    public static RSocketFrameTypeMessageCondition getCondition(int cardinalityIn, int cardinalityOut) {
        switch (cardinalityIn) {
            case 0: 
            case 1: {
                switch (cardinalityOut) {
                    case 0: {
                        return REQUEST_FNF_OR_RESPONSE_CONDITION;
                    }
                    case 1: {
                        return REQUEST_RESPONSE_CONDITION;
                    }
                    case 2: {
                        return REQUEST_STREAM_CONDITION;
                    }
                }
                throw new IllegalStateException("Invalid cardinality: " + cardinalityOut);
            }
            case 2: {
                return REQUEST_CHANNEL_CONDITION;
            }
        }
        throw new IllegalStateException("Invalid cardinality: " + cardinalityIn);
    }

    static {
        for (FrameType type : FrameType.values()) {
            frameTypeConditionCache.put(type.name(), new RSocketFrameTypeMessageCondition(type));
        }
    }
}

