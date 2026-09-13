/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.trace.http;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;

public class InMemoryHttpTraceRepository
implements HttpTraceRepository {
    private int capacity = 100;
    private boolean reverse = true;
    private final List<HttpTrace> traces = new LinkedList<HttpTrace>();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setReverse(boolean reverse) {
        List<HttpTrace> list = this.traces;
        synchronized (list) {
            this.reverse = reverse;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setCapacity(int capacity) {
        List<HttpTrace> list = this.traces;
        synchronized (list) {
            this.capacity = capacity;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<HttpTrace> findAll() {
        List<HttpTrace> list = this.traces;
        synchronized (list) {
            return Collections.unmodifiableList(new ArrayList<HttpTrace>(this.traces));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void add(HttpTrace trace) {
        List<HttpTrace> list = this.traces;
        synchronized (list) {
            while (this.traces.size() >= this.capacity) {
                this.traces.remove(this.reverse ? this.capacity - 1 : 0);
            }
            if (this.reverse) {
                this.traces.add(0, trace);
            } else {
                this.traces.add(trace);
            }
        }
    }
}

