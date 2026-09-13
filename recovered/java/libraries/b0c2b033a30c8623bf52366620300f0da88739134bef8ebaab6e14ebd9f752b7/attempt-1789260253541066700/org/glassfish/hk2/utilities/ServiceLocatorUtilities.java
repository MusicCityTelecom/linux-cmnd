/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Singleton;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.AnnotationLiteral;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.DuplicateServiceException;
import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.FactoryDescriptors;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.Immediate;
import org.glassfish.hk2.api.ImmediateController;
import org.glassfish.hk2.api.IndexedFilter;
import org.glassfish.hk2.api.InheritableThread;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.PerLookup;
import org.glassfish.hk2.api.PerThread;
import org.glassfish.hk2.api.Populator;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.ServiceLocatorFactory;
import org.glassfish.hk2.internal.ImmediateHelper;
import org.glassfish.hk2.internal.InheritableThreadContext;
import org.glassfish.hk2.internal.PerThreadContext;
import org.glassfish.hk2.utilities.AbstractActiveDescriptor;
import org.glassfish.hk2.utilities.Binder;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.glassfish.hk2.utilities.DescriptorImpl;
import org.glassfish.hk2.utilities.FactoryDescriptorsImpl;
import org.glassfish.hk2.utilities.GreedyResolver;
import org.glassfish.hk2.utilities.ImmediateContext;
import org.glassfish.hk2.utilities.RethrowErrorService;

public abstract class ServiceLocatorUtilities {
    private static final String DEFAULT_LOCATOR_NAME = "default";
    private static final Singleton SINGLETON = new SingletonImpl();
    private static final PerLookup PER_LOOKUP = new PerLookupImpl();
    private static final PerThread PER_THREAD = new PerThreadImpl();
    private static final InheritableThread INHERITABLE_THREAD = new InheritableThreadImpl();
    private static final Immediate IMMEDIATE = new ImmediateImpl();

    public static void enablePerThreadScope(ServiceLocator locator) {
        block2: {
            try {
                ServiceLocatorUtilities.addClasses(locator, true, PerThreadContext.class);
            }
            catch (MultiException me) {
                if (ServiceLocatorUtilities.isDupException(me)) break block2;
                throw me;
            }
        }
    }

    public static void enableInheritableThreadScope(ServiceLocator locator) {
        block2: {
            try {
                ServiceLocatorUtilities.addClasses(locator, true, InheritableThreadContext.class);
            }
            catch (MultiException me) {
                if (ServiceLocatorUtilities.isDupException(me)) break block2;
                throw me;
            }
        }
    }

    public static void enableImmediateScope(ServiceLocator locator) {
        ImmediateController controller = ServiceLocatorUtilities.enableImmediateScopeSuspended(locator);
        controller.setImmediateState(ImmediateController.ImmediateServiceState.RUNNING);
    }

    public static ImmediateController enableImmediateScopeSuspended(ServiceLocator locator) {
        block2: {
            try {
                ServiceLocatorUtilities.addClasses(locator, true, ImmediateContext.class, ImmediateHelper.class);
            }
            catch (MultiException me) {
                if (ServiceLocatorUtilities.isDupException(me)) break block2;
                throw me;
            }
        }
        return locator.getService(ImmediateController.class, new Annotation[0]);
    }

    public static void bind(ServiceLocator locator, Binder ... binders) {
        DynamicConfigurationService dcs = locator.getService(DynamicConfigurationService.class, new Annotation[0]);
        DynamicConfiguration config = dcs.createDynamicConfiguration();
        for (Binder binder : binders) {
            binder.bind(config);
        }
        config.commit();
    }

    public static ServiceLocator bind(String name, Binder ... binders) {
        ServiceLocatorFactory factory = ServiceLocatorFactory.getInstance();
        ServiceLocator locator = factory.create(name);
        ServiceLocatorUtilities.bind(locator, binders);
        return locator;
    }

    public static ServiceLocator bind(Binder ... binders) {
        return ServiceLocatorUtilities.bind(DEFAULT_LOCATOR_NAME, binders);
    }

    public static <T> ActiveDescriptor<T> addOneConstant(ServiceLocator locator, Object constant) {
        if (locator == null || constant == null) {
            throw new IllegalArgumentException();
        }
        return ServiceLocatorUtilities.addOneDescriptor(locator, BuilderHelper.createConstantDescriptor(constant), false);
    }

    public static List<FactoryDescriptors> addFactoryConstants(ServiceLocator locator, Factory<?> ... constants) {
        if (locator == null) {
            throw new IllegalArgumentException();
        }
        DynamicConfigurationService dcs = locator.getService(DynamicConfigurationService.class, new Annotation[0]);
        DynamicConfiguration cd = dcs.createDynamicConfiguration();
        LinkedList<FactoryDescriptors> intermediateState = new LinkedList<FactoryDescriptors>();
        for (Factory<?> factoryConstant : constants) {
            if (factoryConstant == null) {
                throw new IllegalArgumentException("One of the factories in " + Arrays.toString(constants) + " is null");
            }
            FactoryDescriptors fds = cd.addActiveFactoryDescriptor(factoryConstant.getClass());
            intermediateState.add(fds);
        }
        cd = dcs.createDynamicConfiguration();
        LinkedList<FactoryDescriptors> retVal = new LinkedList<FactoryDescriptors>();
        int lcv = 0;
        for (FactoryDescriptors fds : intermediateState) {
            ActiveDescriptor provideMethod = (ActiveDescriptor)fds.getFactoryAsAFactory();
            Factory<?> constant = constants[lcv++];
            AbstractActiveDescriptor<Factory<?>> constantDescriptor = BuilderHelper.createConstantDescriptor(constant);
            DescriptorImpl addProvideMethod = new DescriptorImpl(provideMethod);
            FactoryDescriptorsImpl fdi = new FactoryDescriptorsImpl(constantDescriptor, addProvideMethod);
            retVal.add(cd.bind(fdi));
        }
        cd.commit();
        return retVal;
    }

    public static <T> ActiveDescriptor<T> addOneConstant(ServiceLocator locator, Object constant, String name, Type ... contracts) {
        if (locator == null || constant == null) {
            throw new IllegalArgumentException();
        }
        return ServiceLocatorUtilities.addOneDescriptor(locator, BuilderHelper.createConstantDescriptor(constant, name, contracts), false);
    }

    public static <T> ActiveDescriptor<T> addOneDescriptor(ServiceLocator locator, Descriptor descriptor) {
        return ServiceLocatorUtilities.addOneDescriptor(locator, descriptor, true);
    }

    public static <T> ActiveDescriptor<T> addOneDescriptor(ServiceLocator locator, Descriptor descriptor, boolean requiresDeepCopy) {
        ActiveDescriptor active;
        DynamicConfigurationService dcs = locator.getService(DynamicConfigurationService.class, new Annotation[0]);
        DynamicConfiguration config = dcs.createDynamicConfiguration();
        ActiveDescriptor retVal = descriptor instanceof ActiveDescriptor ? ((active = (ActiveDescriptor)descriptor).isReified() ? config.addActiveDescriptor(active, requiresDeepCopy) : config.bind(descriptor, requiresDeepCopy)) : config.bind(descriptor, requiresDeepCopy);
        config.commit();
        return retVal;
    }

    public static List<FactoryDescriptors> addFactoryDescriptors(ServiceLocator locator, FactoryDescriptors ... factories) {
        return ServiceLocatorUtilities.addFactoryDescriptors(locator, true, factories);
    }

    public static List<FactoryDescriptors> addFactoryDescriptors(ServiceLocator locator, boolean requiresDeepCopy, FactoryDescriptors ... factories) {
        if (factories == null || locator == null) {
            throw new IllegalArgumentException();
        }
        ArrayList<FactoryDescriptors> retVal = new ArrayList<FactoryDescriptors>(factories.length);
        DynamicConfigurationService dcs = locator.getService(DynamicConfigurationService.class, new Annotation[0]);
        DynamicConfiguration config = dcs.createDynamicConfiguration();
        for (FactoryDescriptors factory : factories) {
            FactoryDescriptors addMe = config.bind(factory, requiresDeepCopy);
            retVal.add(addMe);
        }
        config.commit();
        return retVal;
    }

    public static List<ActiveDescriptor<?>> addClasses(ServiceLocator locator, boolean idempotent, Class<?> ... toAdd) {
        DynamicConfigurationService dcs = locator.getService(DynamicConfigurationService.class, new Annotation[0]);
        DynamicConfiguration config = dcs.createDynamicConfiguration();
        LinkedList retVal = new LinkedList();
        for (Class<?> addMe : toAdd) {
            if (Factory.class.isAssignableFrom(addMe)) {
                FactoryDescriptors fds = config.addActiveFactoryDescriptor(addMe);
                if (idempotent) {
                    config.addIdempotentFilter(BuilderHelper.createDescriptorFilter(fds.getFactoryAsAService(), false));
                    config.addIdempotentFilter(BuilderHelper.createDescriptorFilter(fds.getFactoryAsAFactory(), false));
                }
                retVal.add((ActiveDescriptor)fds.getFactoryAsAService());
                retVal.add((ActiveDescriptor)fds.getFactoryAsAFactory());
                continue;
            }
            ActiveDescriptor<?> ad = config.addActiveDescriptor(addMe);
            if (idempotent) {
                config.addIdempotentFilter(BuilderHelper.createDescriptorFilter(ad, false));
            }
            retVal.add(ad);
        }
        config.commit();
        return retVal;
    }

    public static List<ActiveDescriptor<?>> addClasses(ServiceLocator locator, Class<?> ... toAdd) {
        return ServiceLocatorUtilities.addClasses(locator, false, toAdd);
    }

    static String getBestContract(Descriptor d) {
        String impl = d.getImplementation();
        Set<String> contracts = d.getAdvertisedContracts();
        if (contracts.contains(impl)) {
            return impl;
        }
        Iterator<String> iterator = contracts.iterator();
        if (iterator.hasNext()) {
            String candidate = iterator.next();
            return candidate;
        }
        return impl;
    }

    public static <T> ActiveDescriptor<T> findOneDescriptor(ServiceLocator locator, Descriptor descriptor) {
        ActiveDescriptor<?> retVal;
        if (locator == null || descriptor == null) {
            throw new IllegalArgumentException();
        }
        if (descriptor.getServiceId() != null && descriptor.getLocatorId() != null && (retVal = locator.getBestDescriptor(BuilderHelper.createSpecificDescriptorFilter(descriptor))) != null) {
            return retVal;
        }
        final DescriptorImpl di = descriptor instanceof DescriptorImpl ? (DescriptorImpl)descriptor : new DescriptorImpl(descriptor);
        final String contract = ServiceLocatorUtilities.getBestContract(descriptor);
        final String name = descriptor.getName();
        ActiveDescriptor<?> retVal2 = locator.getBestDescriptor(new IndexedFilter(){

            @Override
            public boolean matches(Descriptor d) {
                return di.equals(d);
            }

            @Override
            public String getAdvertisedContract() {
                return contract;
            }

            @Override
            public String getName() {
                return name;
            }
        });
        return retVal2;
    }

    public static void removeOneDescriptor(ServiceLocator locator, Descriptor descriptor) {
        ServiceLocatorUtilities.removeOneDescriptor(locator, descriptor, false);
    }

    public static void removeOneDescriptor(ServiceLocator locator, Descriptor descriptor, boolean includeAliasDescriptors) {
        List<ActiveDescriptor<?>> goingToDie;
        if (locator == null || descriptor == null) {
            throw new IllegalArgumentException();
        }
        DynamicConfigurationService dcs = locator.getService(DynamicConfigurationService.class, new Annotation[0]);
        DynamicConfiguration config = dcs.createDynamicConfiguration();
        if (descriptor.getLocatorId() != null && descriptor.getServiceId() != null) {
            List<ActiveDescriptor<?>> goingToDie2;
            IndexedFilter destructionFilter = BuilderHelper.createSpecificDescriptorFilter(descriptor);
            config.addUnbindFilter(destructionFilter);
            if (includeAliasDescriptors && !(goingToDie2 = locator.getDescriptors(destructionFilter)).isEmpty()) {
                AliasFilter af = new AliasFilter(goingToDie2);
                config.addUnbindFilter(af);
            }
            config.commit();
            return;
        }
        final DescriptorImpl di = descriptor instanceof DescriptorImpl ? (DescriptorImpl)descriptor : new DescriptorImpl(descriptor);
        Filter destructionFilter = new Filter(){

            @Override
            public boolean matches(Descriptor d) {
                return di.equals(d);
            }
        };
        config.addUnbindFilter(destructionFilter);
        if (includeAliasDescriptors && !(goingToDie = locator.getDescriptors(destructionFilter)).isEmpty()) {
            AliasFilter af = new AliasFilter(goingToDie);
            config.addUnbindFilter(af);
        }
        config.commit();
    }

    public static void removeFilter(ServiceLocator locator, Filter filter) {
        ServiceLocatorUtilities.removeFilter(locator, filter, false);
    }

    public static void removeFilter(ServiceLocator locator, Filter filter, boolean includeAliasDescriptors) {
        List<ActiveDescriptor<?>> goingToDie;
        if (locator == null || filter == null) {
            throw new IllegalArgumentException();
        }
        DynamicConfigurationService dcs = locator.getService(DynamicConfigurationService.class, new Annotation[0]);
        DynamicConfiguration config = dcs.createDynamicConfiguration();
        config.addUnbindFilter(filter);
        if (includeAliasDescriptors && !(goingToDie = locator.getDescriptors(filter)).isEmpty()) {
            AliasFilter af = new AliasFilter(goingToDie);
            config.addUnbindFilter(af);
        }
        config.commit();
    }

    public static <T> T getService(ServiceLocator locator, String className) {
        if (locator == null || className == null) {
            throw new IllegalArgumentException();
        }
        ActiveDescriptor<?> ad = locator.getBestDescriptor(BuilderHelper.createContractFilter(className));
        if (ad == null) {
            return null;
        }
        return (T)locator.getServiceHandle(ad).getService();
    }

    public static <T> T getService(ServiceLocator locator, Descriptor descriptor) {
        if (locator == null || descriptor == null) {
            throw new IllegalArgumentException();
        }
        Long locatorId = descriptor.getLocatorId();
        if (locatorId != null && locatorId.longValue() == locator.getLocatorId() && descriptor instanceof ActiveDescriptor) {
            return locator.getServiceHandle((ActiveDescriptor)descriptor).getService();
        }
        ActiveDescriptor<T> found = ServiceLocatorUtilities.findOneDescriptor(locator, descriptor);
        if (found == null) {
            return null;
        }
        return locator.getServiceHandle(found).getService();
    }

    public static DynamicConfiguration createDynamicConfiguration(ServiceLocator locator) throws IllegalStateException {
        if (locator == null) {
            throw new IllegalArgumentException();
        }
        DynamicConfigurationService dcs = locator.getService(DynamicConfigurationService.class, new Annotation[0]);
        if (dcs == null) {
            throw new IllegalStateException();
        }
        return dcs.createDynamicConfiguration();
    }

    public static <T> T findOrCreateService(ServiceLocator locator, Class<T> type, Annotation ... qualifiers) throws MultiException {
        if (locator == null || type == null) {
            throw new IllegalArgumentException();
        }
        ServiceHandle<T> retVal = locator.getServiceHandle(type, qualifiers);
        if (retVal == null) {
            return locator.createAndInitialize(type);
        }
        return retVal.getService();
    }

    public static String getOneMetadataField(Descriptor d, String field) {
        Map<String, List<String>> metadata = d.getMetadata();
        List<String> values = metadata.get(field);
        if (values == null || values.isEmpty()) {
            return null;
        }
        return values.get(0);
    }

    public static String getOneMetadataField(ServiceHandle<?> h, String field) {
        return ServiceLocatorUtilities.getOneMetadataField(h.getActiveDescriptor(), field);
    }

    public static ServiceLocator createAndPopulateServiceLocator(String name) throws MultiException {
        ServiceLocator retVal = ServiceLocatorFactory.getInstance().create(name);
        DynamicConfigurationService dcs = retVal.getService(DynamicConfigurationService.class, new Annotation[0]);
        Populator populator = dcs.getPopulator();
        try {
            populator.populate();
        }
        catch (IOException e) {
            throw new MultiException(e);
        }
        return retVal;
    }

    public static ServiceLocator createAndPopulateServiceLocator() {
        return ServiceLocatorUtilities.createAndPopulateServiceLocator(null);
    }

    public static void enableLookupExceptions(ServiceLocator locator) {
        block3: {
            if (locator == null) {
                throw new IllegalArgumentException();
            }
            try {
                ServiceLocatorUtilities.addClasses(locator, true, RethrowErrorService.class);
            }
            catch (MultiException me) {
                if (ServiceLocatorUtilities.isDupException(me)) break block3;
                throw me;
            }
        }
    }

    public static void enableGreedyResolution(ServiceLocator locator) {
        block3: {
            if (locator == null) {
                throw new IllegalArgumentException();
            }
            try {
                ServiceLocatorUtilities.addClasses(locator, true, GreedyResolver.class);
            }
            catch (MultiException me) {
                if (ServiceLocatorUtilities.isDupException(me)) break block3;
                throw me;
            }
        }
    }

    public static void enableTopicDistribution(ServiceLocator locator) {
        throw new AssertionError((Object)"ServiceLocatorUtilities.enableTopicDistribution method has been removed, use ExtrasUtilities.enableTopicDistribution");
    }

    public static void dumpAllDescriptors(ServiceLocator locator) {
        ServiceLocatorUtilities.dumpAllDescriptors(locator, System.err);
    }

    public static void dumpAllDescriptors(ServiceLocator locator, PrintStream output) {
        if (locator == null || output == null) {
            throw new IllegalArgumentException();
        }
        List<ActiveDescriptor<?>> all = locator.getDescriptors(BuilderHelper.allFilter());
        for (ActiveDescriptor<?> d : all) {
            output.println(d.toString());
        }
    }

    public static Singleton getSingletonAnnotation() {
        return SINGLETON;
    }

    public static PerLookup getPerLookupAnnotation() {
        return PER_LOOKUP;
    }

    public static PerThread getPerThreadAnnotation() {
        return PER_THREAD;
    }

    public static InheritableThread getInheritableThreadAnnotation() {
        return INHERITABLE_THREAD;
    }

    public static Immediate getImmediateAnnotation() {
        return IMMEDIATE;
    }

    private static boolean isDupException(MultiException me) {
        boolean atLeastOne = false;
        for (Throwable error : me.getErrors()) {
            atLeastOne = true;
            if (error instanceof DuplicateServiceException) continue;
            return false;
        }
        return atLeastOne;
    }

    private static class SingletonImpl
    extends AnnotationLiteral<Singleton>
    implements Singleton {
        private static final long serialVersionUID = -2425625604832777314L;

        private SingletonImpl() {
        }
    }

    private static class InheritableThreadImpl
    extends AnnotationLiteral<InheritableThread>
    implements InheritableThread {
        private static final long serialVersionUID = -3955786566272090916L;

        private InheritableThreadImpl() {
        }
    }

    private static class PerThreadImpl
    extends AnnotationLiteral<PerThread>
    implements PerThread {
        private static final long serialVersionUID = 521793185589873261L;

        private PerThreadImpl() {
        }
    }

    private static class PerLookupImpl
    extends AnnotationLiteral<PerLookup>
    implements PerLookup {
        private static final long serialVersionUID = 6554011929159736762L;

        private PerLookupImpl() {
        }
    }

    private static class ImmediateImpl
    extends AnnotationLiteral<Immediate>
    implements Immediate {
        private static final long serialVersionUID = -4189466670823669605L;

        private ImmediateImpl() {
        }
    }

    private static class AliasFilter
    implements Filter {
        private final Set<String> values = new HashSet<String>();

        private AliasFilter(List<ActiveDescriptor<?>> bases) {
            for (ActiveDescriptor<?> base : bases) {
                String val = base.getLocatorId() + "." + base.getServiceId();
                this.values.add(val);
            }
        }

        @Override
        public boolean matches(Descriptor d) {
            List<String> mAliasVals = d.getMetadata().get("__AliasOf");
            if (mAliasVals == null || mAliasVals.isEmpty()) {
                return false;
            }
            String aliasVal = mAliasVals.get(0);
            return this.values.contains(aliasVal);
        }
    }
}

