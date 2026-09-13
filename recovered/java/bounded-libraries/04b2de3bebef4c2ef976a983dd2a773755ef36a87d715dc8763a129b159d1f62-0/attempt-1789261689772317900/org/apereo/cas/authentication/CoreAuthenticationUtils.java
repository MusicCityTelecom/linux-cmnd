/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Splitter
 *  com.google.common.collect.ArrayListMultimap
 *  com.google.common.collect.Multimap
 *  groovy.lang.GroovyClassLoader
 *  lombok.Generated
 *  org.apache.commons.io.IOUtils
 *  org.apache.commons.lang3.ClassUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.tuple.Pair
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.AuthenticationPasswordPolicyHandlingStrategy
 *  org.apereo.cas.authentication.AuthenticationPolicy
 *  org.apereo.cas.authentication.Credential
 *  org.apereo.cas.authentication.PrincipalElectionStrategyConflictResolver
 *  org.apereo.cas.authentication.adaptive.intel.IPAddressIntelligenceService
 *  org.apereo.cas.authentication.handler.PrincipalNameTransformer
 *  org.apereo.cas.authentication.principal.PrincipalFactory
 *  org.apereo.cas.authentication.principal.PrincipalResolver
 *  org.apereo.cas.configuration.model.core.authentication.AdaptiveAuthenticationIPIntelligenceProperties
 *  org.apereo.cas.configuration.model.core.authentication.AdaptiveAuthenticationProperties
 *  org.apereo.cas.configuration.model.core.authentication.AuthenticationPolicyProperties
 *  org.apereo.cas.configuration.model.core.authentication.PasswordPolicyProperties
 *  org.apereo.cas.configuration.model.core.authentication.PasswordPolicyProperties$PasswordPolicyHandlingOptions
 *  org.apereo.cas.configuration.model.core.authentication.PersonDirectoryPrincipalResolverProperties
 *  org.apereo.cas.configuration.model.core.authentication.PrincipalAttributesCoreProperties$MergingStrategyTypes
 *  org.apereo.cas.configuration.support.Beans
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.model.TriStateBoolean
 *  org.apereo.cas.util.transforms.ChainingPrincipalNameTransformer
 *  org.apereo.cas.validation.Assertion
 *  org.apereo.services.persondir.IPersonAttributeDao
 *  org.apereo.services.persondir.support.merger.IAttributeMerger
 *  org.apereo.services.persondir.support.merger.MultivaluedAttributeMerger
 *  org.apereo.services.persondir.support.merger.NoncollidingAttributeAdder
 *  org.apereo.services.persondir.support.merger.ReplacingAttributeAdder
 *  org.apereo.services.persondir.support.merger.ReturnChangesAdditiveAttributeMerger
 *  org.apereo.services.persondir.support.merger.ReturnOriginalAdditiveAttributeMerger
 *  org.codehaus.groovy.control.CompilerConfiguration
 *  org.jooq.lambda.Unchecked
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.core.io.DefaultResourceLoader
 *  org.springframework.core.io.Resource
 *  org.springframework.util.StringUtils
 */
package org.apereo.cas.authentication;

import com.google.common.base.Splitter;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import groovy.lang.GroovyClassLoader;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationPasswordPolicyHandlingStrategy;
import org.apereo.cas.authentication.AuthenticationPolicy;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.PrincipalElectionStrategyConflictResolver;
import org.apereo.cas.authentication.adaptive.intel.DefaultIPAddressIntelligenceService;
import org.apereo.cas.authentication.adaptive.intel.GroovyIPAddressIntelligenceService;
import org.apereo.cas.authentication.adaptive.intel.IPAddressIntelligenceService;
import org.apereo.cas.authentication.adaptive.intel.RestfulIPAddressIntelligenceService;
import org.apereo.cas.authentication.handler.PrincipalNameTransformer;
import org.apereo.cas.authentication.policy.AllAuthenticationHandlersSucceededAuthenticationPolicy;
import org.apereo.cas.authentication.policy.AllCredentialsValidatedAuthenticationPolicy;
import org.apereo.cas.authentication.policy.AtLeastOneCredentialValidatedAuthenticationPolicy;
import org.apereo.cas.authentication.policy.GroovyScriptAuthenticationPolicy;
import org.apereo.cas.authentication.policy.NotPreventedAuthenticationPolicy;
import org.apereo.cas.authentication.policy.RequiredAuthenticationHandlerAuthenticationPolicy;
import org.apereo.cas.authentication.policy.RestfulAuthenticationPolicy;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.authentication.principal.PrincipalNameTransformerUtils;
import org.apereo.cas.authentication.principal.PrincipalResolver;
import org.apereo.cas.authentication.principal.resolvers.PersonDirectoryPrincipalResolver;
import org.apereo.cas.authentication.principal.resolvers.PrincipalResolutionContext;
import org.apereo.cas.authentication.support.password.DefaultPasswordPolicyHandlingStrategy;
import org.apereo.cas.authentication.support.password.GroovyPasswordPolicyHandlingStrategy;
import org.apereo.cas.authentication.support.password.RejectResultCodePasswordPolicyHandlingStrategy;
import org.apereo.cas.configuration.model.core.authentication.AdaptiveAuthenticationIPIntelligenceProperties;
import org.apereo.cas.configuration.model.core.authentication.AdaptiveAuthenticationProperties;
import org.apereo.cas.configuration.model.core.authentication.AuthenticationPolicyProperties;
import org.apereo.cas.configuration.model.core.authentication.PasswordPolicyProperties;
import org.apereo.cas.configuration.model.core.authentication.PersonDirectoryPrincipalResolverProperties;
import org.apereo.cas.configuration.model.core.authentication.PrincipalAttributesCoreProperties;
import org.apereo.cas.configuration.support.Beans;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.model.TriStateBoolean;
import org.apereo.cas.util.transforms.ChainingPrincipalNameTransformer;
import org.apereo.cas.validation.Assertion;
import org.apereo.services.persondir.IPersonAttributeDao;
import org.apereo.services.persondir.support.merger.IAttributeMerger;
import org.apereo.services.persondir.support.merger.MultivaluedAttributeMerger;
import org.apereo.services.persondir.support.merger.NoncollidingAttributeAdder;
import org.apereo.services.persondir.support.merger.ReplacingAttributeAdder;
import org.apereo.services.persondir.support.merger.ReturnChangesAdditiveAttributeMerger;
import org.apereo.services.persondir.support.merger.ReturnOriginalAdditiveAttributeMerger;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.jooq.lambda.Unchecked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

public final class CoreAuthenticationUtils {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CoreAuthenticationUtils.class);

    public static Map<String, Object> convertAttributeValuesToObjects(Map<String, List<Object>> attributes) {
        Set<Map.Entry<String, List<Object>>> entries = attributes.entrySet();
        return entries.stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> {
            List value = (List)entry.getValue();
            return value.size() == 1 ? value.get(0) : value;
        }));
    }

    public static Map<String, List<Object>> convertAttributeValuesToMultiValuedObjects(Map<String, Object> attributes) {
        Set<Map.Entry<String, Object>> entries = attributes.entrySet();
        return entries.stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> {
            Object value = entry.getValue();
            return (List)CollectionUtils.toCollection(value, ArrayList.class);
        }));
    }

    public static IAttributeMerger getAttributeMerger(PrincipalAttributesCoreProperties.MergingStrategyTypes mergingPolicy) {
        switch (mergingPolicy) {
            case MULTIVALUED: {
                MultivaluedAttributeMerger merger = new MultivaluedAttributeMerger();
                merger.setDistinctValues(true);
                return merger;
            }
            case ADD: {
                return new NoncollidingAttributeAdder();
            }
            case SOURCE: {
                return new ReturnOriginalAdditiveAttributeMerger();
            }
            case DESTINATION: {
                return new ReturnChangesAdditiveAttributeMerger();
            }
        }
        return new ReplacingAttributeAdder();
    }

    public static boolean isRememberMeAuthentication(Authentication model, Assertion assertion) {
        Map authnAttributes = model.getAttributes();
        List authnMethod = (List)authnAttributes.get("org.apereo.cas.authentication.principal.REMEMBER_ME");
        return authnMethod != null && authnMethod.contains(Boolean.TRUE) && assertion.isFromNewLogin();
    }

    public static Boolean isRememberMeAuthentication(Authentication authentication) {
        if (authentication == null) {
            return Boolean.FALSE;
        }
        Map attributes = authentication.getAttributes();
        LOGGER.trace("Located authentication attributes [{}]", (Object)attributes);
        if (attributes.containsKey("org.apereo.cas.authentication.principal.REMEMBER_ME")) {
            List rememberMeValue = (List)attributes.get("org.apereo.cas.authentication.principal.REMEMBER_ME");
            LOGGER.debug("Located remember-me authentication attribute [{}]", (Object)rememberMeValue);
            return rememberMeValue.contains(Boolean.TRUE);
        }
        return Boolean.FALSE;
    }

    public static Map<String, List<Object>> mergeAttributes(Map<String, List<Object>> currentAttributes, Map<String, List<Object>> attributesToMerge, IAttributeMerger merger) {
        Map<String, ArrayList> toModify = currentAttributes.entrySet().stream().map(entry -> Pair.of((Object)((String)entry.getKey()), (Object)((ArrayList)CollectionUtils.toCollection(entry.getValue(), ArrayList.class)))).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        Map<String, ArrayList> toMerge = attributesToMerge.entrySet().stream().map(entry -> Pair.of((Object)((String)entry.getKey()), (Object)((ArrayList)CollectionUtils.toCollection(entry.getValue(), ArrayList.class)))).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        LOGGER.trace("Merging current attributes [{}] with [{}]", toModify, toMerge);
        Map results = merger.mergeAttributes(toModify, toMerge);
        LOGGER.debug("Merged attributes with the final result as [{}]", (Object)results);
        return results;
    }

    public static Map<String, List<Object>> mergeAttributes(Map<String, List<Object>> currentAttributes, Map<String, List<Object>> attributesToMerge) {
        MultivaluedAttributeMerger merger = new MultivaluedAttributeMerger();
        merger.setDistinctValues(true);
        return CoreAuthenticationUtils.mergeAttributes(currentAttributes, attributesToMerge, (IAttributeMerger)merger);
    }

    public static Map<String, Object> transformPrincipalAttributesListIntoMap(List<String> list) {
        Multimap<String, Object> map = CoreAuthenticationUtils.transformPrincipalAttributesListIntoMultiMap(list);
        return CollectionUtils.wrap(map);
    }

    public static Multimap<String, Object> transformPrincipalAttributesListIntoMultiMap(List<String> list) {
        ArrayListMultimap multimap = ArrayListMultimap.create();
        if (list.isEmpty()) {
            LOGGER.debug("No principal attributes are defined");
        } else {
            list.forEach(a -> {
                String attributeName = a.trim();
                if (attributeName.contains(":")) {
                    List attrCombo = Splitter.on((String)":").splitToList((CharSequence)attributeName);
                    String name = ((String)attrCombo.get(0)).trim();
                    String value = ((String)attrCombo.get(1)).trim();
                    LOGGER.debug("Mapped principal attribute name [{}] to [{}]", (Object)name, (Object)value);
                    multimap.put((Object)name, (Object)value);
                } else {
                    LOGGER.debug("Mapped principal attribute name [{}]", (Object)attributeName);
                    multimap.put((Object)attributeName, (Object)attributeName);
                }
            });
        }
        return multimap;
    }

    public static Predicate<Credential> newCredentialSelectionPredicate(String selectionCriteria) {
        try {
            if (org.apache.commons.lang3.StringUtils.isBlank((CharSequence)selectionCriteria)) {
                return credential -> true;
            }
            if (selectionCriteria.endsWith(".groovy")) {
                DefaultResourceLoader loader = new DefaultResourceLoader();
                Resource resource = loader.getResource(selectionCriteria);
                String script = IOUtils.toString((InputStream)resource.getInputStream(), (Charset)StandardCharsets.UTF_8);
                GroovyClassLoader classLoader = new GroovyClassLoader(Beans.class.getClassLoader(), new CompilerConfiguration(), true);
                Class clz = classLoader.parseClass(script);
                return (Predicate)clz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            }
            Class predicateClazz = ClassUtils.getClass((String)selectionCriteria);
            return (Predicate)predicateClazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (Exception e) {
            Predicate<String> predicate = Pattern.compile(selectionCriteria).asPredicate();
            return credential -> predicate.test(credential.getId());
        }
    }

    public static AuthenticationPasswordPolicyHandlingStrategy newPasswordPolicyHandlingStrategy(PasswordPolicyProperties properties, ApplicationContext applicationContext) {
        if (properties.getStrategy() == PasswordPolicyProperties.PasswordPolicyHandlingOptions.REJECT_RESULT_CODE) {
            LOGGER.debug("Created password policy handling strategy based on blocked authentication result codes");
            return new RejectResultCodePasswordPolicyHandlingStrategy();
        }
        Resource location = properties.getGroovy().getLocation();
        if (properties.getStrategy() == PasswordPolicyProperties.PasswordPolicyHandlingOptions.GROOVY && location != null) {
            LOGGER.debug("Created password policy handling strategy based on Groovy script [{}]", (Object)location);
            return new GroovyPasswordPolicyHandlingStrategy(location, applicationContext);
        }
        LOGGER.trace("Created default password policy handling strategy");
        return new DefaultPasswordPolicyHandlingStrategy();
    }

    public static PrincipalResolver newPersonDirectoryPrincipalResolver(PrincipalFactory principalFactory, IPersonAttributeDao attributeRepository, IAttributeMerger attributeMerger, PersonDirectoryPrincipalResolverProperties ... personDirectory) {
        return CoreAuthenticationUtils.newPersonDirectoryPrincipalResolver(principalFactory, attributeRepository, attributeMerger, PersonDirectoryPrincipalResolver.class, personDirectory);
    }

    public static <T extends PrincipalResolver> T newPersonDirectoryPrincipalResolver(PrincipalFactory principalFactory, IPersonAttributeDao attributeRepository, IAttributeMerger attributeMerger, Class<T> resolverClass, PersonDirectoryPrincipalResolverProperties ... personDirectory) {
        PrincipalResolutionContext context = CoreAuthenticationUtils.buildPrincipalResolutionContext(principalFactory, attributeRepository, attributeMerger, personDirectory);
        return (T)((PrincipalResolver)Unchecked.supplier(() -> {
            Constructor ctor = resolverClass.getDeclaredConstructor(PrincipalResolutionContext.class);
            return (PrincipalResolver)ctor.newInstance(context);
        }).get());
    }

    public static PrincipalResolutionContext buildPrincipalResolutionContext(PrincipalFactory principalFactory, IPersonAttributeDao attributeRepository, IAttributeMerger attributeMerger, PersonDirectoryPrincipalResolverProperties ... personDirectory) {
        List transformers = Arrays.stream(personDirectory).map(p -> PrincipalNameTransformerUtils.newPrincipalNameTransformer(p.getPrincipalTransformation())).collect(Collectors.toList());
        ChainingPrincipalNameTransformer transformer = new ChainingPrincipalNameTransformer(transformers);
        return ((PrincipalResolutionContext.PrincipalResolutionContextBuilder)((PrincipalResolutionContext.PrincipalResolutionContextBuilder)((PrincipalResolutionContext.PrincipalResolutionContextBuilder)((PrincipalResolutionContext.PrincipalResolutionContextBuilder)((PrincipalResolutionContext.PrincipalResolutionContextBuilder)((PrincipalResolutionContext.PrincipalResolutionContextBuilder)((PrincipalResolutionContext.PrincipalResolutionContextBuilder)((PrincipalResolutionContext.PrincipalResolutionContextBuilder)((PrincipalResolutionContext.PrincipalResolutionContextBuilder)PrincipalResolutionContext.builder().attributeRepository(attributeRepository)).attributeMerger(attributeMerger)).principalFactory(principalFactory)).returnNullIfNoAttributes(Arrays.stream(personDirectory).filter(p -> p.getReturnNull() != TriStateBoolean.UNDEFINED).map(p -> p.getReturnNull().toBoolean()).findFirst().orElse(Boolean.FALSE))).principalAttributeNames(Arrays.stream(personDirectory).map(PersonDirectoryPrincipalResolverProperties::getPrincipalAttribute).filter(org.apache.commons.lang3.StringUtils::isNotBlank).findFirst().orElse(""))).principalNameTransformer((PrincipalNameTransformer)transformer)).useCurrentPrincipalId(Arrays.stream(personDirectory).filter(p -> p.getUseExistingPrincipalId() != TriStateBoolean.UNDEFINED).map(p -> p.getUseExistingPrincipalId().toBoolean()).findFirst().orElse(Boolean.FALSE))).resolveAttributes(Arrays.stream(personDirectory).filter(p -> p.getAttributeResolutionEnabled() != TriStateBoolean.UNDEFINED).map(p -> p.getAttributeResolutionEnabled().toBoolean()).findFirst().orElse(Boolean.TRUE))).activeAttributeRepositoryIdentifiers(Arrays.stream(personDirectory).filter(p -> org.apache.commons.lang3.StringUtils.isNotBlank((CharSequence)p.getActiveAttributeRepositoryIds())).map(p -> StringUtils.commaDelimitedListToSet((String)p.getActiveAttributeRepositoryIds())).filter(p -> !p.isEmpty()).findFirst().orElse(Collections.EMPTY_SET))).build();
    }

    public static Collection<AuthenticationPolicy> newAuthenticationPolicy(AuthenticationPolicyProperties policyProps) {
        if (policyProps.getReq().isEnabled()) {
            LOGGER.trace("Activating authentication policy [{}]", (Object)RequiredAuthenticationHandlerAuthenticationPolicy.class.getSimpleName());
            Set requiredHandlerNames = StringUtils.commaDelimitedListToSet((String)policyProps.getReq().getHandlerName());
            RequiredAuthenticationHandlerAuthenticationPolicy policy = new RequiredAuthenticationHandlerAuthenticationPolicy(requiredHandlerNames, policyProps.getReq().isTryAll());
            return CollectionUtils.wrapList((Object[])new AuthenticationPolicy[]{policy});
        }
        if (policyProps.getAllHandlers().isEnabled()) {
            LOGGER.trace("Activating authentication policy [{}]", (Object)AllAuthenticationHandlersSucceededAuthenticationPolicy.class.getSimpleName());
            return CollectionUtils.wrapList((Object[])new AuthenticationPolicy[]{new AllAuthenticationHandlersSucceededAuthenticationPolicy()});
        }
        if (policyProps.getAll().isEnabled()) {
            LOGGER.trace("Activating authentication policy [{}]", (Object)AllCredentialsValidatedAuthenticationPolicy.class.getSimpleName());
            return CollectionUtils.wrapList((Object[])new AuthenticationPolicy[]{new AllCredentialsValidatedAuthenticationPolicy()});
        }
        if (policyProps.getNotPrevented().isEnabled()) {
            LOGGER.trace("Activating authentication policy [{}]", (Object)NotPreventedAuthenticationPolicy.class.getSimpleName());
            return CollectionUtils.wrapList((Object[])new AuthenticationPolicy[]{new NotPreventedAuthenticationPolicy()});
        }
        if (!policyProps.getGroovy().isEmpty()) {
            LOGGER.trace("Activating authentication policy [{}]", (Object)GroovyScriptAuthenticationPolicy.class.getSimpleName());
            return policyProps.getGroovy().stream().map(groovy -> new GroovyScriptAuthenticationPolicy(groovy.getScript())).collect(Collectors.toList());
        }
        if (!policyProps.getRest().isEmpty()) {
            LOGGER.trace("Activating authentication policy [{}]", (Object)RestfulAuthenticationPolicy.class.getSimpleName());
            return policyProps.getRest().stream().map(RestfulAuthenticationPolicy::new).collect(Collectors.toList());
        }
        if (policyProps.getAny().isEnabled()) {
            LOGGER.trace("Activating authentication policy [{}]", (Object)AtLeastOneCredentialValidatedAuthenticationPolicy.class.getSimpleName());
            return CollectionUtils.wrapList((Object[])new AuthenticationPolicy[]{new AtLeastOneCredentialValidatedAuthenticationPolicy(policyProps.getAny().isTryAll())});
        }
        return new ArrayList<AuthenticationPolicy>();
    }

    public static IPAddressIntelligenceService newIpAddressIntelligenceService(AdaptiveAuthenticationProperties adaptive) {
        AdaptiveAuthenticationIPIntelligenceProperties intel = adaptive.getIpIntel();
        if (org.apache.commons.lang3.StringUtils.isNotBlank((CharSequence)intel.getRest().getUrl())) {
            return new RestfulIPAddressIntelligenceService(adaptive);
        }
        if (intel.getGroovy().getLocation() != null) {
            return new GroovyIPAddressIntelligenceService(adaptive);
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank((CharSequence)intel.getBlackDot().getEmailAddress())) {
            return new RestfulIPAddressIntelligenceService(adaptive);
        }
        return new DefaultIPAddressIntelligenceService(adaptive);
    }

    public static PrincipalElectionStrategyConflictResolver newPrincipalElectionStrategyConflictResolver(PersonDirectoryPrincipalResolverProperties properties) {
        if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase((CharSequence)properties.getPrincipalResolutionConflictStrategy(), (CharSequence)"first")) {
            return PrincipalElectionStrategyConflictResolver.first();
        }
        return PrincipalElectionStrategyConflictResolver.last();
    }

    @Generated
    private CoreAuthenticationUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

