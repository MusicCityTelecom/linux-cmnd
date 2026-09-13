/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.misc;

import groovyjarjarantlr4.v4.runtime.misc.OrderedHashSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph<T> {
    protected Map<T, Node<T>> nodes = new LinkedHashMap<T, Node<T>>();

    public void addEdge(T a, T b) {
        Node<T> a_node = this.getNode(a);
        Node<T> b_node = this.getNode(b);
        a_node.addEdge(b_node);
    }

    public Node<T> getNode(T a) {
        Node<T> existing = this.nodes.get(a);
        if (existing != null) {
            return existing;
        }
        Node<T> n = new Node<T>(a);
        this.nodes.put(a, n);
        return n;
    }

    public List<T> sort() {
        OrderedHashSet<Node<T>> visited = new OrderedHashSet<Node<T>>();
        ArrayList sorted = new ArrayList();
        while (visited.size() < this.nodes.size()) {
            Node<T> tNode;
            Node<T> n = null;
            Iterator<Node<T>> i$ = this.nodes.values().iterator();
            while (i$.hasNext() && visited.contains(n = (tNode = i$.next()))) {
            }
            if (n == null) continue;
            this.DFS(n, visited, sorted);
        }
        return sorted;
    }

    public void DFS(Node<T> n, Set<Node<T>> visited, ArrayList<T> sorted) {
        if (visited.contains(n)) {
            return;
        }
        visited.add(n);
        if (n.edges != null) {
            for (Node target : n.edges) {
                this.DFS(target, visited, sorted);
            }
        }
        sorted.add(n.payload);
    }

    public static class Node<T> {
        public T payload;
        public List<Node<T>> edges = Collections.emptyList();

        public Node(T payload) {
            this.payload = payload;
        }

        public void addEdge(Node<T> n) {
            if (this.edges == Collections.EMPTY_LIST) {
                this.edges = new ArrayList<Node<T>>();
            }
            if (!this.edges.contains(n)) {
                this.edges.add(n);
            }
        }

        public String toString() {
            return this.payload.toString();
        }
    }
}

