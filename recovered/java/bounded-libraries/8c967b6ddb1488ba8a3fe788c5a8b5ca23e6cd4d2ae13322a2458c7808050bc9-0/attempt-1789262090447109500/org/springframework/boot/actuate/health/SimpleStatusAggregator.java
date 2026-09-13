/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.boot.actuate.health;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.actuate.health.StatusAggregator;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

public class SimpleStatusAggregator
implements StatusAggregator {
    private static final List<String> DEFAULT_ORDER;
    static final StatusAggregator INSTANCE;
    private final List<String> order;
    private final Comparator<Status> comparator = new StatusComparator();

    public SimpleStatusAggregator() {
        this.order = DEFAULT_ORDER;
    }

    public SimpleStatusAggregator(Status ... order) {
        this.order = ObjectUtils.isEmpty((Object[])order) ? DEFAULT_ORDER : SimpleStatusAggregator.getUniformCodes(Arrays.stream(order).map(Status::getCode));
    }

    public SimpleStatusAggregator(String ... order) {
        this.order = ObjectUtils.isEmpty((Object[])order) ? DEFAULT_ORDER : SimpleStatusAggregator.getUniformCodes(Arrays.stream(order));
    }

    public SimpleStatusAggregator(List<String> order) {
        this.order = CollectionUtils.isEmpty(order) ? DEFAULT_ORDER : SimpleStatusAggregator.getUniformCodes(order.stream());
    }

    @Override
    public Status getAggregateStatus(Set<Status> statuses) {
        return statuses.stream().filter(this::contains).min(this.comparator).orElse(Status.UNKNOWN);
    }

    private boolean contains(Status status) {
        return this.order.contains(SimpleStatusAggregator.getUniformCode(status.getCode()));
    }

    private static List<String> getUniformCodes(Stream<String> codes) {
        return codes.map(SimpleStatusAggregator::getUniformCode).collect(Collectors.toList());
    }

    private static String getUniformCode(String code) {
        if (code == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < code.length(); ++i) {
            char ch = code.charAt(i);
            if (!Character.isAlphabetic(ch) && !Character.isDigit(ch)) continue;
            builder.append(Character.toLowerCase(ch));
        }
        return builder.toString();
    }

    static {
        ArrayList<String> defaultOrder = new ArrayList<String>();
        defaultOrder.add(Status.DOWN.getCode());
        defaultOrder.add(Status.OUT_OF_SERVICE.getCode());
        defaultOrder.add(Status.UP.getCode());
        defaultOrder.add(Status.UNKNOWN.getCode());
        DEFAULT_ORDER = Collections.unmodifiableList(SimpleStatusAggregator.getUniformCodes(defaultOrder.stream()));
        INSTANCE = new SimpleStatusAggregator();
    }

    private class StatusComparator
    implements Comparator<Status> {
        private StatusComparator() {
        }

        @Override
        public int compare(Status s1, Status s2) {
            int i2;
            List order = SimpleStatusAggregator.this.order;
            int i1 = order.indexOf(SimpleStatusAggregator.getUniformCode(s1.getCode()));
            return i1 < (i2 = order.indexOf(SimpleStatusAggregator.getUniformCode(s2.getCode()))) ? -1 : (i1 != i2 ? 1 : s1.getCode().compareTo(s2.getCode()));
        }
    }
}

