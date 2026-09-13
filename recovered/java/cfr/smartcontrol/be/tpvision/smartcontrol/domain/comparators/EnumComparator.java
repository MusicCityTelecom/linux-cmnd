/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.comparators;

import be.tpvision.smartcontrol.domain.comparators.DelegatingComparator;
import java.util.Comparator;

public class EnumComparator<E extends Enum>
extends DelegatingComparator<E> {
    public EnumComparator(Comparator<String> nameComparator) {
        super(Comparator.comparing(Enum::name, nameComparator));
    }
}

