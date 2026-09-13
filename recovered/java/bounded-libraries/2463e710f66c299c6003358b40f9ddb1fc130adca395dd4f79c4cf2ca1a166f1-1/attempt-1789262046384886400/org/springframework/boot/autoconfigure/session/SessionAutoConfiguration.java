/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.WebApplicationType
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.boot.web.server.Cookie$SameSite
 *  org.springframework.boot.web.servlet.server.Session$Cookie
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.ConfigurationCondition$ConfigurationPhase
 *  org.springframework.context.annotation.Import
 *  org.springframework.context.annotation.ImportSelector
 *  org.springframework.core.type.AnnotationMetadata
 *  org.springframework.security.web.authentication.RememberMeServices
 *  org.springframework.session.ReactiveSessionRepository
 *  org.springframework.session.Session
 *  org.springframework.session.SessionRepository
 *  org.springframework.session.security.web.authentication.SpringSessionRememberMeServices
 *  org.springframework.session.web.http.CookieHttpSessionIdResolver
 *  org.springframework.session.web.http.CookieSerializer
 *  org.springframework.session.web.http.DefaultCookieSerializer
 *  org.springframework.session.web.http.HttpSessionIdResolver
 */
package org.springframework.boot.autoconfigure.session;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.session.DefaultCookieSerializerCustomizer;
import org.springframework.boot.autoconfigure.session.NonUniqueSessionRepositoryException;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.boot.autoconfigure.session.SessionRepositoryFilterConfiguration;
import org.springframework.boot.autoconfigure.session.SessionRepositoryUnavailableException;
import org.springframework.boot.autoconfigure.session.SessionStoreMappings;
import org.springframework.boot.autoconfigure.session.StoreType;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.reactive.HttpHandlerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxProperties;
import org.springframework.boot.autoconfigure.web.reactive.WebSessionIdResolverAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.web.server.Cookie;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.session.ReactiveSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HttpSessionIdResolver;

@AutoConfiguration(after={DataSourceAutoConfiguration.class, HazelcastAutoConfiguration.class, JdbcTemplateAutoConfiguration.class, MongoDataAutoConfiguration.class, MongoReactiveDataAutoConfiguration.class, RedisAutoConfiguration.class, RedisReactiveAutoConfiguration.class, WebSessionIdResolverAutoConfiguration.class}, before={HttpHandlerAutoConfiguration.class, WebFluxAutoConfiguration.class})
@ConditionalOnClass(value={Session.class})
@ConditionalOnWebApplication
@EnableConfigurationProperties(value={ServerProperties.class, SessionProperties.class, WebFluxProperties.class})
public class SessionAutoConfiguration {

    static class ReactiveSessionRepositoryValidator
    extends AbstractSessionRepositoryValidator {
        ReactiveSessionRepositoryValidator(SessionProperties sessionProperties, ObjectProvider<ReactiveSessionRepository<?>> sessionRepositoryProvider) {
            super(sessionProperties, sessionRepositoryProvider);
        }
    }

    static class ServletSessionRepositoryValidator
    extends AbstractSessionRepositoryValidator {
        ServletSessionRepositoryValidator(SessionProperties sessionProperties, ObjectProvider<SessionRepository<?>> sessionRepositoryProvider) {
            super(sessionProperties, sessionRepositoryProvider);
        }
    }

    static abstract class AbstractSessionRepositoryValidator
    implements InitializingBean {
        private final SessionProperties sessionProperties;
        private final ObjectProvider<?> sessionRepositoryProvider;

        protected AbstractSessionRepositoryValidator(SessionProperties sessionProperties, ObjectProvider<?> sessionRepositoryProvider) {
            this.sessionProperties = sessionProperties;
            this.sessionRepositoryProvider = sessionRepositoryProvider;
        }

        public void afterPropertiesSet() {
            StoreType storeType = this.sessionProperties.getStoreType();
            if (storeType != StoreType.NONE && this.sessionRepositoryProvider.getIfAvailable() == null && storeType != null) {
                throw new SessionRepositoryUnavailableException("No session repository could be auto-configured, check your configuration (session store type is '" + storeType.name().toLowerCase(Locale.ENGLISH) + "')", storeType);
            }
        }
    }

    static class ReactiveSessionRepositoryImplementationValidator
    extends AbstractSessionRepositoryImplementationValidator {
        ReactiveSessionRepositoryImplementationValidator(ApplicationContext applicationContext, SessionProperties sessionProperties) {
            super(applicationContext, sessionProperties, Arrays.asList("org.springframework.session.data.redis.ReactiveRedisSessionRepository", "org.springframework.session.data.mongo.ReactiveMongoSessionRepository"));
        }
    }

    static class ServletSessionRepositoryImplementationValidator
    extends AbstractSessionRepositoryImplementationValidator {
        ServletSessionRepositoryImplementationValidator(ApplicationContext applicationContext, SessionProperties sessionProperties) {
            super(applicationContext, sessionProperties, Arrays.asList("org.springframework.session.hazelcast.HazelcastIndexedSessionRepository", "org.springframework.session.jdbc.JdbcIndexedSessionRepository", "org.springframework.session.data.mongo.MongoIndexedSessionRepository", "org.springframework.session.data.redis.RedisIndexedSessionRepository"));
        }
    }

    static abstract class AbstractSessionRepositoryImplementationValidator {
        private final List<String> candidates;
        private final ClassLoader classLoader;
        private final SessionProperties sessionProperties;

        AbstractSessionRepositoryImplementationValidator(ApplicationContext applicationContext, SessionProperties sessionProperties, List<String> candidates) {
            this.classLoader = applicationContext.getClassLoader();
            this.sessionProperties = sessionProperties;
            this.candidates = candidates;
            this.checkAvailableImplementations();
        }

        private void checkAvailableImplementations() {
            ArrayList availableCandidates = new ArrayList();
            for (String candidate : this.candidates) {
                this.addCandidateIfAvailable(availableCandidates, candidate);
            }
            StoreType storeType = this.sessionProperties.getStoreType();
            if (availableCandidates.size() > 1 && storeType == null) {
                throw new NonUniqueSessionRepositoryException(availableCandidates);
            }
        }

        private void addCandidateIfAvailable(List<Class<?>> candidates, String type) {
            try {
                candidates.add(Class.forName(type, false, this.classLoader));
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
    }

    static class ServletSessionConfigurationImportSelector
    extends SessionConfigurationImportSelector {
        ServletSessionConfigurationImportSelector() {
        }

        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            return super.selectImports(WebApplicationType.SERVLET);
        }
    }

    static class ReactiveSessionConfigurationImportSelector
    extends SessionConfigurationImportSelector {
        ReactiveSessionConfigurationImportSelector() {
        }

        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            return super.selectImports(WebApplicationType.REACTIVE);
        }
    }

    static abstract class SessionConfigurationImportSelector
    implements ImportSelector {
        SessionConfigurationImportSelector() {
        }

        protected final String[] selectImports(WebApplicationType webApplicationType) {
            return (String[])Arrays.stream(StoreType.values()).map(type -> SessionStoreMappings.getConfigurationClass(webApplicationType, type)).toArray(String[]::new);
        }
    }

    static class DefaultCookieSerializerCondition
    extends AnyNestedCondition {
        DefaultCookieSerializerCondition() {
            super(ConfigurationCondition.ConfigurationPhase.REGISTER_BEAN);
        }

        @ConditionalOnBean(value={CookieHttpSessionIdResolver.class})
        @ConditionalOnMissingBean(value={CookieSerializer.class})
        static class CookieHttpSessionIdResolverAvailable {
            CookieHttpSessionIdResolverAvailable() {
            }
        }

        @ConditionalOnMissingBean(value={HttpSessionIdResolver.class, CookieSerializer.class})
        static class NoComponentsAvailable {
            NoComponentsAvailable() {
            }
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.REACTIVE)
    @Import(value={ReactiveSessionRepositoryValidator.class})
    static class ReactiveSessionConfiguration {
        ReactiveSessionConfiguration() {
        }

        @Configuration(proxyBeanMethods=false)
        @ConditionalOnMissingBean(value={ReactiveSessionRepository.class})
        @Import(value={ReactiveSessionRepositoryImplementationValidator.class, ReactiveSessionConfigurationImportSelector.class})
        static class ReactiveSessionRepositoryConfiguration {
            ReactiveSessionRepositoryConfiguration() {
            }
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.SERVLET)
    @Import(value={ServletSessionRepositoryValidator.class, SessionRepositoryFilterConfiguration.class})
    static class ServletSessionConfiguration {
        ServletSessionConfiguration() {
        }

        @Bean
        @Conditional(value={DefaultCookieSerializerCondition.class})
        DefaultCookieSerializer cookieSerializer(ServerProperties serverProperties, ObjectProvider<DefaultCookieSerializerCustomizer> cookieSerializerCustomizers) {
            Session.Cookie cookie = serverProperties.getServlet().getSession().getCookie();
            DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
            PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
            map.from(() -> ((Session.Cookie)cookie).getName()).to(arg_0 -> ((DefaultCookieSerializer)cookieSerializer).setCookieName(arg_0));
            map.from(() -> ((Session.Cookie)cookie).getDomain()).to(arg_0 -> ((DefaultCookieSerializer)cookieSerializer).setDomainName(arg_0));
            map.from(() -> ((Session.Cookie)cookie).getPath()).to(arg_0 -> ((DefaultCookieSerializer)cookieSerializer).setCookiePath(arg_0));
            map.from(() -> ((Session.Cookie)cookie).getHttpOnly()).to(arg_0 -> ((DefaultCookieSerializer)cookieSerializer).setUseHttpOnlyCookie(arg_0));
            map.from(() -> ((Session.Cookie)cookie).getSecure()).to(arg_0 -> ((DefaultCookieSerializer)cookieSerializer).setUseSecureCookie(arg_0));
            map.from(() -> ((Session.Cookie)cookie).getMaxAge()).asInt(Duration::getSeconds).to(arg_0 -> ((DefaultCookieSerializer)cookieSerializer).setCookieMaxAge(arg_0));
            map.from(() -> ((Session.Cookie)cookie).getSameSite()).as(Cookie.SameSite::attributeValue).to(arg_0 -> ((DefaultCookieSerializer)cookieSerializer).setSameSite(arg_0));
            cookieSerializerCustomizers.orderedStream().forEach(customizer -> customizer.customize(cookieSerializer));
            return cookieSerializer;
        }

        @Configuration(proxyBeanMethods=false)
        @ConditionalOnMissingBean(value={SessionRepository.class})
        @Import(value={ServletSessionRepositoryImplementationValidator.class, ServletSessionConfigurationImportSelector.class})
        static class ServletSessionRepositoryConfiguration {
            ServletSessionRepositoryConfiguration() {
            }
        }

        @Configuration(proxyBeanMethods=false)
        @ConditionalOnClass(value={RememberMeServices.class})
        static class RememberMeServicesConfiguration {
            RememberMeServicesConfiguration() {
            }

            @Bean
            DefaultCookieSerializerCustomizer rememberMeServicesCookieSerializerCustomizer() {
                return cookieSerializer -> cookieSerializer.setRememberMeRequestAttribute(SpringSessionRememberMeServices.REMEMBER_ME_LOGIN_ATTR);
            }
        }
    }
}

