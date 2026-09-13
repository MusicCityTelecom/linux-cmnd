/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.reactivestreams.Publisher
 *  org.springframework.context.ApplicationEvent
 *  org.springframework.context.ApplicationEventPublisher
 *  org.springframework.context.ApplicationEventPublisherAware
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.support.PathMatchingResourcePatternResolver
 *  org.springframework.core.io.support.ResourcePatternResolver
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.ReflectionUtils
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 */
package org.springframework.data.repository.init;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.reactivestreams.Publisher;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.core.CrudMethods;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.init.RepositoriesPopulatedEvent;
import org.springframework.data.repository.init.RepositoryPopulator;
import org.springframework.data.repository.init.ResourceReader;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.data.repository.util.ReactiveWrapperConverters;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ResourceReaderRepositoryPopulator
implements RepositoryPopulator,
ApplicationEventPublisherAware {
    private static final Log logger = LogFactory.getLog(ResourceReaderRepositoryPopulator.class);
    private final ResourceReader reader;
    @Nullable
    private final ClassLoader classLoader;
    private final ResourcePatternResolver resolver;
    @Nullable
    private ApplicationEventPublisher publisher;
    private Collection<Resource> resources = Collections.emptySet();

    public ResourceReaderRepositoryPopulator(ResourceReader reader) {
        this(reader, null);
    }

    public ResourceReaderRepositoryPopulator(ResourceReader reader, @Nullable ClassLoader classLoader) {
        Assert.notNull((Object)reader, (String)"Reader must not be null");
        this.reader = reader;
        this.classLoader = classLoader;
        this.resolver = classLoader == null ? new PathMatchingResourcePatternResolver() : new PathMatchingResourcePatternResolver(classLoader);
    }

    public void setResourceLocation(String location) throws IOException {
        Assert.hasText((String)location, (String)"Location must not be null");
        this.setResources(this.resolver.getResources(location));
    }

    public void setResources(Resource ... resources) {
        this.resources = Arrays.asList(resources);
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void populate(Repositories repositories) {
        Assert.notNull((Object)repositories, (String)"Repositories must not be null");
        AggregatePersisterFactory persisterFactory = new AggregatePersisterFactory(repositories);
        for (Resource resource : this.resources) {
            logger.info((Object)String.format("Reading resource: %s", resource));
            Object result = this.readObjectFrom(resource);
            if (result instanceof Collection) {
                for (Object element : (Collection)result) {
                    if (element != null) {
                        this.persist(element, persisterFactory);
                        continue;
                    }
                    logger.info((Object)"Skipping null element found in unmarshal result");
                }
                continue;
            }
            this.persist(result, persisterFactory);
        }
        if (this.publisher != null) {
            this.publisher.publishEvent((ApplicationEvent)new RepositoriesPopulatedEvent(this, repositories));
        }
    }

    private Object readObjectFrom(Resource resource) {
        try {
            return this.reader.readFrom(resource, this.classLoader);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void persist(Object object, AggregatePersisterFactory persisterFactory) {
        AggregatePersister persister = persisterFactory.getPersisterFor(object.getClass());
        logger.debug((Object)String.format("Persisting %s using repository %s", object, persister));
        persister.save(object);
    }

    private static class ReactiveCrudRepositoryPersister
    implements AggregatePersister {
        private final ReactiveCrudRepository<Object, Object> repository;

        public ReactiveCrudRepositoryPersister(Object repository) {
            Assert.isInstanceOf(ReactiveCrudRepository.class, (Object)repository);
            this.repository = (ReactiveCrudRepository)repository;
        }

        @Override
        public void save(Object object) {
            this.repository.save(object).block();
        }

        public String toString() {
            return this.repository.toString();
        }
    }

    private static class CrudRepositoryPersister
    implements AggregatePersister {
        private final CrudRepository<Object, Object> repository;

        public CrudRepositoryPersister(Object repository) {
            Assert.isInstanceOf(CrudRepository.class, (Object)repository);
            this.repository = (CrudRepository)repository;
        }

        @Override
        public void save(Object object) {
            this.repository.save(object);
        }

        public String toString() {
            return this.repository.toString();
        }
    }

    private static class ReflectiveReactivePersister
    extends ReflectivePersister {
        public ReflectiveReactivePersister(RepositoryMetadata metadata, Object repository) {
            super(metadata, repository);
        }

        @Override
        public void save(Object object) {
            Object wrapper = this.doPersist(object);
            Publisher publisher = ReactiveWrapperConverters.toWrapper(wrapper, Publisher.class);
            if (!(publisher instanceof Mono)) {
                publisher = Flux.from((Publisher)publisher).collectList();
            }
            Mono.from((Publisher)publisher).block();
        }
    }

    private static class ReflectivePersister
    implements AggregatePersister {
        private final CrudMethods methods;
        private final Object repository;

        public ReflectivePersister(RepositoryMetadata metadata, Object repository) {
            this.methods = metadata.getCrudMethods();
            this.repository = repository;
        }

        @Override
        public void save(Object object) {
            this.doPersist(object);
        }

        Object doPersist(Object object) {
            Method method = this.methods.getSaveMethod().orElseThrow(() -> new IllegalStateException("Repository doesn't have a save-method declared"));
            return ReflectionUtils.invokeMethod((Method)method, (Object)this.repository, (Object[])new Object[]{object});
        }

        public String toString() {
            return this.repository.toString();
        }
    }

    static interface AggregatePersister {
        public void save(Object var1);
    }

    static class AggregatePersisterFactory {
        private final Map<Class<?>, AggregatePersister> persisters = new HashMap();
        private final Repositories repositories;

        public AggregatePersisterFactory(Repositories repositories) {
            this.repositories = repositories;
        }

        public AggregatePersister getPersisterFor(Class<?> domainType) {
            return this.persisters.computeIfAbsent(domainType, this::createPersisterFor);
        }

        private AggregatePersister createPersisterFor(Class<?> domainType) {
            RepositoryInformation repositoryInformation = this.repositories.getRequiredRepositoryInformation(domainType);
            Object repository = this.repositories.getRepositoryFor(domainType).orElseThrow(() -> new IllegalStateException(String.format("No repository found for domain type: %s", domainType)));
            if (repositoryInformation.isReactiveRepository()) {
                return repository instanceof ReactiveCrudRepository ? new ReactiveCrudRepositoryPersister(repository) : new ReflectiveReactivePersister(repositoryInformation, repository);
            }
            if (repository instanceof CrudRepository) {
                return new CrudRepositoryPersister(repository);
            }
            return new ReflectivePersister(repositoryInformation, repository);
        }
    }
}

