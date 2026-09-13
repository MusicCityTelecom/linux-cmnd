/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.aggregator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.integration.store.MessageGroup;
import org.springframework.messaging.Message;

public class DefaultAggregateHeadersFunction
implements Function<MessageGroup, Map<String, Object>> {
    private static final Log LOGGER = LogFactory.getLog(DefaultAggregateHeadersFunction.class);

    @Override
    public Map<String, Object> apply(MessageGroup messageGroup) {
        HashMap<String, Object> aggregatedHeaders = new HashMap<String, Object>();
        Set<String> conflictKeys = this.doAggregateHeaders(messageGroup, aggregatedHeaders);
        for (String keyToRemove : conflictKeys) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object)("Excluding header '" + keyToRemove + "' upon aggregation due to conflict(s) in MessageGroup with correlation key: " + messageGroup.getGroupId()));
            }
            aggregatedHeaders.remove(keyToRemove);
        }
        return aggregatedHeaders;
    }

    private Set<String> doAggregateHeaders(MessageGroup group, Map<String, Object> aggregatedHeaders) {
        HashSet<String> conflictKeys = new HashSet<String>();
        for (Message<?> message : group.getMessages()) {
            for (Map.Entry entry : message.getHeaders().entrySet()) {
                String key = (String)entry.getKey();
                if ("id".equals(key) || "timestamp".equals(key) || "sequenceSize".equals(key) || "sequenceNumber".equals(key)) continue;
                Object value = entry.getValue();
                if (!aggregatedHeaders.containsKey(key)) {
                    aggregatedHeaders.put(key, value);
                    continue;
                }
                if (Objects.equals(value, aggregatedHeaders.get(key))) continue;
                conflictKeys.add(key);
            }
        }
        return conflictKeys;
    }
}

