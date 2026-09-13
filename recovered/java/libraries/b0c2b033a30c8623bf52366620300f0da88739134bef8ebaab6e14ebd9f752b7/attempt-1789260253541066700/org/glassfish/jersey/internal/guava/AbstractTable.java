/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.guava;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.glassfish.jersey.internal.guava.Collections2;
import org.glassfish.jersey.internal.guava.Iterators;
import org.glassfish.jersey.internal.guava.Maps;
import org.glassfish.jersey.internal.guava.Table;
import org.glassfish.jersey.internal.guava.Tables;

abstract class AbstractTable<R, C, V>
implements Table<R, C, V> {
    private transient Set<Table.Cell<R, C, V>> cellSet;

    AbstractTable() {
    }

    @Override
    public boolean containsRow(Object rowKey) {
        return Maps.safeContainsKey(this.rowMap(), rowKey);
    }

    @Override
    public boolean containsColumn(Object columnKey) {
        return Maps.safeContainsKey(this.columnMap(), columnKey);
    }

    @Override
    public Set<R> rowKeySet() {
        return this.rowMap().keySet();
    }

    @Override
    public Set<C> columnKeySet() {
        return this.columnMap().keySet();
    }

    @Override
    public boolean containsValue(Object value) {
        for (Map row : this.rowMap().values()) {
            if (!row.containsValue(value)) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean contains(Object rowKey, Object columnKey) {
        Map row = Maps.safeGet(this.rowMap(), rowKey);
        return row != null && Maps.safeContainsKey(row, columnKey);
    }

    @Override
    public V get(Object rowKey, Object columnKey) {
        Map row = Maps.safeGet(this.rowMap(), rowKey);
        return row == null ? null : (V)Maps.safeGet(row, columnKey);
    }

    @Override
    public void clear() {
        Iterators.clear(this.cellSet().iterator());
    }

    @Override
    public V remove(Object rowKey, Object columnKey) {
        Map row = Maps.safeGet(this.rowMap(), rowKey);
        return row == null ? null : (V)Maps.safeRemove(row, columnKey);
    }

    @Override
    public V put(R rowKey, C columnKey, V value) {
        return this.row(rowKey).put(columnKey, value);
    }

    @Override
    public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
        for (Table.Cell<R, C, V> cell : table.cellSet()) {
            this.put(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
        }
    }

    @Override
    public Set<Table.Cell<R, C, V>> cellSet() {
        Set<Table.Cell<R, C, V>> result = this.cellSet;
        return result == null ? (this.cellSet = this.createCellSet()) : result;
    }

    private Set<Table.Cell<R, C, V>> createCellSet() {
        return new CellSet();
    }

    abstract Iterator<Table.Cell<R, C, V>> cellIterator();

    @Override
    public boolean equals(Object obj) {
        return Tables.equalsImpl(this, obj);
    }

    @Override
    public int hashCode() {
        return this.cellSet().hashCode();
    }

    public String toString() {
        return this.rowMap().toString();
    }

    private class CellSet
    extends AbstractSet<Table.Cell<R, C, V>> {
        private CellSet() {
        }

        @Override
        public boolean contains(Object o) {
            if (o instanceof Table.Cell) {
                Table.Cell cell = (Table.Cell)o;
                Map row = Maps.safeGet(AbstractTable.this.rowMap(), cell.getRowKey());
                return row != null && Collections2.safeContains(row.entrySet(), Maps.immutableEntry(cell.getColumnKey(), cell.getValue()));
            }
            return false;
        }

        @Override
        public boolean remove(Object o) {
            if (o instanceof Table.Cell) {
                Table.Cell cell = (Table.Cell)o;
                Map row = Maps.safeGet(AbstractTable.this.rowMap(), cell.getRowKey());
                return row != null && Collections2.safeRemove(row.entrySet(), Maps.immutableEntry(cell.getColumnKey(), cell.getValue()));
            }
            return false;
        }

        @Override
        public void clear() {
            AbstractTable.this.clear();
        }

        @Override
        public Iterator<Table.Cell<R, C, V>> iterator() {
            return AbstractTable.this.cellIterator();
        }

        @Override
        public int size() {
            return AbstractTable.this.size();
        }
    }
}

