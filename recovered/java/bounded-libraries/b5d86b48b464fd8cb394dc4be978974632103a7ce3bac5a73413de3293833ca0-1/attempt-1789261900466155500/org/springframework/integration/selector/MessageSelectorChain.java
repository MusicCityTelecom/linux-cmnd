/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.selector;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.integration.core.MessageSelector;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

public class MessageSelectorChain
implements MessageSelector {
    private volatile VotingStrategy votingStrategy = VotingStrategy.ALL;
    private final List<MessageSelector> selectors = new CopyOnWriteArrayList<MessageSelector>();

    public void setVotingStrategy(VotingStrategy votingStrategy) {
        Assert.notNull((Object)((Object)votingStrategy), (String)"votingStrategy must not be null");
        this.votingStrategy = votingStrategy;
    }

    public void add(MessageSelector selector) {
        this.selectors.add(selector);
    }

    public void add(int index, MessageSelector selector) {
        this.selectors.add(index, selector);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setSelectors(List<MessageSelector> selectors) {
        Assert.notEmpty(selectors, (String)"selectors must not be empty");
        List<MessageSelector> list = this.selectors;
        synchronized (list) {
            this.selectors.clear();
            this.selectors.addAll(selectors);
        }
    }

    @Override
    public final boolean accept(Message<?> message) {
        int count = 0;
        int accepted = 0;
        for (MessageSelector next : this.selectors) {
            ++count;
            if (next.accept(message)) {
                if (this.votingStrategy.equals((Object)VotingStrategy.ANY)) {
                    return true;
                }
                ++accepted;
                continue;
            }
            if (!this.votingStrategy.equals((Object)VotingStrategy.ALL)) continue;
            return false;
        }
        return this.decide(accepted, count);
    }

    private boolean decide(int accepted, int total) {
        if (accepted == 0) {
            return false;
        }
        switch (this.votingStrategy) {
            case ANY: {
                return true;
            }
            case ALL: {
                return accepted == total;
            }
            case MAJORITY: {
                return 2 * accepted > total;
            }
            case MAJORITY_OR_TIE: {
                return 2 * accepted >= total;
            }
        }
        throw new IllegalArgumentException("unsupported voting strategy " + (Object)((Object)this.votingStrategy));
    }

    public static enum VotingStrategy {
        ALL,
        ANY,
        MAJORITY,
        MAJORITY_OR_TIE;

    }
}

