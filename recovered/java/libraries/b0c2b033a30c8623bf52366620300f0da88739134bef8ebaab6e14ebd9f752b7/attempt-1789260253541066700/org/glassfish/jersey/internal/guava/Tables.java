/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.guava;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import org.glassfish.jersey.internal.guava.AbstractTable;
import org.glassfish.jersey.internal.guava.Iterators;
import org.glassfish.jersey.internal.guava.Preconditions;
import org.glassfish.jersey.internal.guava.Table;

public final class Tables {
    private Tables() {
    }

    public static <R, C, V> Table.Cell<R, C, V> immutableCell(R rowKey, C columnKey, V value) {
        return new ImmutableCell<R, C, V>(rowKey, columnKey, value);
    }

    private static <R, C, V> Table<C, R, V> transpose(Table<R, C, V> table) {
        return table instanceof TransposeTable ? ((TransposeTable)table).original : new TransposeTable<C, R, V>(table);
    }

    static boolean equalsImpl(Table<?, ?, ?> table, Object obj) {
        if (obj == table) {
            return true;
        }
        if (obj instanceof Table) {
            Table that = (Table)obj;
            return table.cellSet().equals(that.cellSet());
        }
        return false;
    }

    private static class TransposeTable<C, R, V>
    extends AbstractTable<C, R, V> {
        private static final Function<Table.Cell<?, ?, ?>, Table.Cell<?, ?, ?>> TRANSPOSE_CELL = new Function<Table.Cell<?, ?, ?>, Table.Cell<?, ?, ?>>(){

            @Override
            public Table.Cell<?, ?, ?> apply(Table.Cell<?, ?, ?> cell) {
                return Tables.immutableCell(cell.getColumnKey(), cell.getRowKey(), cell.getValue());
            }
        };
        final Table<R, C, V> original;

        TransposeTable(Table<R, C, V> original) {
            this.original = Preconditions.checkNotNull(original);
        }

        @Override
        public void clear() {
            this.original.clear();
        }

        @Override
        public Map<C, V> column(R columnKey) {
            return this.original.row(columnKey);
        }

        @Override
        public Set<R> columnKeySet() {
            return this.original.rowKeySet();
        }

        @Override
        public Map<R, Map<C, V>> columnMap() {
            return this.original.rowMap();
        }

        @Override
        public boolean contains(Object rowKey, Object columnKey) {
            return this.original.contains(columnKey, rowKey);
        }

        @Override
        public boolean containsColumn(Object columnKey) {
            return this.original.containsRow(columnKey);
        }

        @Override
        public boolean containsRow(Object rowKey) {
            return this.original.containsColumn(rowKey);
        }

        @Override
        public boolean containsValue(Object value) {
            return this.original.containsValue(value);
        }

        @Override
        public V get(Object rowKey, Object columnKey) {
            return this.original.get(columnKey, rowKey);
        }

        @Override
        public V put(C rowKey, R columnKey, V value) {
            return this.original.put(columnKey, rowKey, value);
        }

        @Override
        public void putAll(Table<? extends C, ? extends R, ? extends V> table) {
            this.original.putAll(Tables.transpose(table));
        }

        @Override
        public V remove(Object rowKey, Object columnKey) {
            return this.original.remove(columnKey, rowKey);
        }

        @Override
        public Map<R, V> row(C rowKey) {
            return this.original.column(rowKey);
        }

        @Override
        public Set<C> rowKeySet() {
            return this.original.columnKeySet();
        }

        @Override
        public Map<C, Map<R, V>> rowMap() {
            return this.original.columnMap();
        }

        @Override
        public int size() {
            return this.original.size();
        }

        @Override
        Iterator<Table.Cell<C, R, V>> cellIterator() {
            return Iterators.transform(this.original.cellSet().iterator(), TRANSPOSE_CELL);
        }
    }

    static abstract class AbstractCell<R, C, V>
    implements Table.Cell<R, C, V> {
        AbstractCell() {
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof Table.Cell) {
                Table.Cell other = (Table.Cell)obj;
                return Objects.equals(this.getRowKey(), other.getRowKey()) && Objects.equals(this.getColumnKey(), other.getColumnKey()) && Objects.equals(this.getValue(), other.getValue());
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.getRowKey(), this.getColumnKey(), this.getValue());
        }

        public String toString() {
            return "(" + this.getRowKey() + "," + this.getColumnKey() + ")=" + this.getValue();
        }
    }

    static final class ImmutableCell<R, C, V>
    extends AbstractCell<R, C, V>
    implements Serializable {
        private static final long serialVersionUID = 0L;
        private final R rowKey;
        private final C columnKey;
        private final V value;

        ImmutableCell(R rowKey, C columnKey, V value) {
            this.rowKey = rowKey;
            this.columnKey = columnKey;
            this.value = value;
        }

        @Override
        public R getRowKey() {
            return this.rowKey;
        }

        @Override
        public C getColumnKey() {
            return this.columnKey;
        }

        @Override
        public V getValue() {
            return this.value;
        }
    }
}

