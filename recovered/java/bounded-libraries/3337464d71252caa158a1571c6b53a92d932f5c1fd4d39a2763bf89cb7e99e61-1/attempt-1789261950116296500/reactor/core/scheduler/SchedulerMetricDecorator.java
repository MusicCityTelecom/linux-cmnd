/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.MeterRegistry
 *  io.micrometer.core.instrument.Tags
 *  io.micrometer.core.instrument.binder.jvm.ExecutorServiceMetrics
 *  io.micrometer.core.instrument.search.Search
 */
package reactor.core.scheduler;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.jvm.ExecutorServiceMetrics;
import io.micrometer.core.instrument.search.Search;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import reactor.core.Disposable;
import reactor.core.Scannable;
import reactor.core.scheduler.DelegatingScheduledExecutorService;
import reactor.core.scheduler.Scheduler;
import reactor.util.Metrics;

final class SchedulerMetricDecorator
implements BiFunction<Scheduler, ScheduledExecutorService, ScheduledExecutorService>,
Disposable {
    static final String TAG_SCHEDULER_ID = "reactor.scheduler.id";
    static final String METRICS_DECORATOR_KEY = "reactor.metrics.decorator";
    final WeakHashMap<Scheduler, String> seenSchedulers = new WeakHashMap();
    final Map<String, AtomicInteger> schedulerDifferentiator = new HashMap<String, AtomicInteger>();
    final WeakHashMap<Scheduler, AtomicInteger> executorDifferentiator = new WeakHashMap();
    final MeterRegistry registry = Metrics.MicrometerConfiguration.getRegistry();

    SchedulerMetricDecorator() {
    }

    @Override
    public synchronized ScheduledExecutorService apply(Scheduler scheduler, final ScheduledExecutorService service) {
        String schedulerName = Scannable.from(scheduler).scanOrDefault(Scannable.Attr.NAME, scheduler.getClass().getName());
        String schedulerId = this.seenSchedulers.computeIfAbsent(scheduler, s -> {
            int schedulerDifferentiator = this.schedulerDifferentiator.computeIfAbsent(schedulerName, k -> new AtomicInteger(0)).getAndIncrement();
            return schedulerDifferentiator == 0 ? schedulerName : schedulerName + "#" + schedulerDifferentiator;
        });
        final String executorId = schedulerId + "-" + this.executorDifferentiator.computeIfAbsent(scheduler, key -> new AtomicInteger(0)).getAndIncrement();
        final Tags tags = Tags.of((String)TAG_SCHEDULER_ID, (String)schedulerId);
        class MetricsRemovingScheduledExecutorService
        extends DelegatingScheduledExecutorService {
            MetricsRemovingScheduledExecutorService() {
                super(ExecutorServiceMetrics.monitor((MeterRegistry)SchedulerMetricDecorator.this.registry, (ScheduledExecutorService)scheduledExecutorService, (String)string, (Iterable)tags2));
            }

            @Override
            public List<Runnable> shutdownNow() {
                this.removeMetrics();
                return super.shutdownNow();
            }

            @Override
            public void shutdown() {
                this.removeMetrics();
                super.shutdown();
            }

            void removeMetrics() {
                Search.in((MeterRegistry)SchedulerMetricDecorator.this.registry).tag("name", executorId).meters().forEach(arg_0 -> ((MeterRegistry)SchedulerMetricDecorator.this.registry).remove(arg_0));
            }
        }
        return new MetricsRemovingScheduledExecutorService();
    }

    @Override
    public void dispose() {
        Search.in((MeterRegistry)this.registry).tagKeys(new String[]{TAG_SCHEDULER_ID}).meters().forEach(arg_0 -> ((MeterRegistry)this.registry).remove(arg_0));
        this.seenSchedulers.clear();
        this.schedulerDifferentiator.clear();
        this.executorDifferentiator.clear();
    }
}

