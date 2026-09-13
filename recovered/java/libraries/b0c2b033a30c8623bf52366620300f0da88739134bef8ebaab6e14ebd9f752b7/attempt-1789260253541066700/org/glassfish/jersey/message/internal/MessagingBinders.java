/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import java.security.AccessController;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import javax.inject.Singleton;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.internal.ServiceFinderBinder;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.internal.inject.ClassBinding;
import org.glassfish.jersey.internal.inject.InstanceBinding;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.internal.util.Tokenizer;
import org.glassfish.jersey.message.internal.BasicTypesMessageProvider;
import org.glassfish.jersey.message.internal.ByteArrayProvider;
import org.glassfish.jersey.message.internal.CacheControlProvider;
import org.glassfish.jersey.message.internal.CookieProvider;
import org.glassfish.jersey.message.internal.DataSourceProvider;
import org.glassfish.jersey.message.internal.DateProvider;
import org.glassfish.jersey.message.internal.EntityTagProvider;
import org.glassfish.jersey.message.internal.FileProvider;
import org.glassfish.jersey.message.internal.FormMultivaluedMapProvider;
import org.glassfish.jersey.message.internal.FormProvider;
import org.glassfish.jersey.message.internal.InputStreamProvider;
import org.glassfish.jersey.message.internal.LinkProvider;
import org.glassfish.jersey.message.internal.LocaleProvider;
import org.glassfish.jersey.message.internal.MediaTypeProvider;
import org.glassfish.jersey.message.internal.NewCookieProvider;
import org.glassfish.jersey.message.internal.ReaderProvider;
import org.glassfish.jersey.message.internal.RenderedImageProvider;
import org.glassfish.jersey.message.internal.SourceProvider;
import org.glassfish.jersey.message.internal.StreamingOutputProvider;
import org.glassfish.jersey.message.internal.StringHeaderProvider;
import org.glassfish.jersey.message.internal.StringMessageProvider;
import org.glassfish.jersey.message.internal.UriProvider;
import org.glassfish.jersey.spi.HeaderDelegateProvider;

public final class MessagingBinders {
    private static final Logger LOGGER = Logger.getLogger(MessagingBinders.class.getName());

    private MessagingBinders() {
    }

    private static final class EnabledProvidersBinder {
        private static final String ALL = "ALL";
        private HashSet<Provider> enabledProviders = new HashSet();

        private EnabledProvidersBinder() {
            for (Provider provider : Provider.values()) {
                this.enabledProviders.add(provider);
            }
        }

        private void markDisabled(String properties) {
            String[] tokens = Tokenizer.tokenize(properties);
            for (int tokenIndex = 0; tokenIndex != tokens.length; ++tokenIndex) {
                String token = tokens[tokenIndex].toUpperCase(Locale.ROOT);
                if (ALL.equals(token)) {
                    this.enabledProviders.clear();
                    return;
                }
                Iterator<Provider> iterator = this.enabledProviders.iterator();
                while (iterator.hasNext()) {
                    Provider provider = iterator.next();
                    if (!provider.name().equals(token)) continue;
                    iterator.remove();
                }
            }
        }

        private void bindToBinder(AbstractBinder binder) {
            ProviderBinder providerBinder = null;
            for (Provider provider : this.enabledProviders) {
                if (EnabledProvidersBinder.isClass(provider.className)) {
                    switch (provider) {
                        case DOMSOURCE: {
                            providerBinder = new DomSourceBinder();
                            break;
                        }
                        case RENDEREDIMAGE: {
                            providerBinder = new RenderedImageBinder();
                            break;
                        }
                        case SAXSOURCE: {
                            providerBinder = new SaxSourceBinder();
                            break;
                        }
                        case SOURCE: {
                            providerBinder = new SourceBinder();
                            break;
                        }
                        case STREAMSOURCE: {
                            providerBinder = new StreamSourceBinder();
                        }
                    }
                    providerBinder.bind(binder, provider);
                    continue;
                }
                switch (provider) {
                    case DOMSOURCE: 
                    case SAXSOURCE: 
                    case STREAMSOURCE: {
                        LOGGER.warning(LocalizationMessages.DEPENDENT_CLASS_OF_DEFAULT_PROVIDER_NOT_FOUND(provider.className, "MessageBodyReader<" + provider.className + ">"));
                        break;
                    }
                    case RENDEREDIMAGE: 
                    case SOURCE: {
                        LOGGER.warning(LocalizationMessages.DEPENDENT_CLASS_OF_DEFAULT_PROVIDER_NOT_FOUND(provider.className, "MessageBodyWriter<" + provider.className + ">"));
                    }
                }
            }
        }

        private static boolean isClass(String className) {
            return null != AccessController.doPrivileged(ReflectionHelper.classForNamePA(className));
        }

        private static class StreamSourceBinder
        implements ProviderBinder {
            private StreamSourceBinder() {
            }

            @Override
            public void bind(AbstractBinder binder, Provider provider) {
                ((ClassBinding)binder.bind(SourceProvider.StreamSourceReader.class).to(MessageBodyReader.class)).in(Singleton.class);
            }
        }

        private static class SourceBinder
        implements ProviderBinder {
            private SourceBinder() {
            }

            @Override
            public void bind(AbstractBinder binder, Provider provider) {
                ((ClassBinding)binder.bind(SourceProvider.SourceWriter.class).to(MessageBodyWriter.class)).in(Singleton.class);
            }
        }

        private static class SaxSourceBinder
        implements ProviderBinder {
            private SaxSourceBinder() {
            }

            @Override
            public void bind(AbstractBinder binder, Provider provider) {
                ((ClassBinding)binder.bind(SourceProvider.SaxSourceReader.class).to(MessageBodyReader.class)).in(Singleton.class);
            }
        }

        private static class RenderedImageBinder
        implements ProviderBinder {
            private RenderedImageBinder() {
            }

            @Override
            public void bind(AbstractBinder binder, Provider provider) {
                ((ClassBinding)((ClassBinding)binder.bind(RenderedImageProvider.class).to(MessageBodyReader.class)).to(MessageBodyWriter.class)).in(Singleton.class);
            }
        }

        private static class DomSourceBinder
        implements ProviderBinder {
            private DomSourceBinder() {
            }

            @Override
            public void bind(AbstractBinder binder, Provider provider) {
                ((ClassBinding)binder.bind(SourceProvider.DomSourceReader.class).to(MessageBodyReader.class)).in(Singleton.class);
            }
        }

        private static interface ProviderBinder {
            public void bind(AbstractBinder var1, Provider var2);
        }

        private static enum Provider {
            DOMSOURCE("javax.xml.transform.dom.DOMSource"),
            RENDEREDIMAGE("java.awt.image.RenderedImage"),
            SAXSOURCE("javax.xml.transform.sax.SAXSource"),
            SOURCE("javax.xml.transform.Source"),
            STREAMSOURCE("javax.xml.transform.stream.StreamSource");

            private String className;

            private Provider(String className) {
                this.className = className;
            }
        }
    }

    public static class HeaderDelegateProviders
    extends AbstractBinder {
        private final Set<HeaderDelegateProvider> providers;

        public HeaderDelegateProviders() {
            HashSet<HeaderDelegateProvider> providers = new HashSet<HeaderDelegateProvider>();
            providers.add(new CacheControlProvider());
            providers.add(new CookieProvider());
            providers.add(new DateProvider());
            providers.add(new EntityTagProvider());
            providers.add(new LinkProvider());
            providers.add(new LocaleProvider());
            providers.add(new MediaTypeProvider());
            providers.add(new NewCookieProvider());
            providers.add(new StringHeaderProvider());
            providers.add(new UriProvider());
            this.providers = providers;
        }

        @Override
        protected void configure() {
            this.providers.forEach(provider -> {
                InstanceBinding cfr_ignored_0 = (InstanceBinding)this.bind(provider).to(HeaderDelegateProvider.class);
            });
        }

        public Set<HeaderDelegateProvider> getHeaderDelegateProviders() {
            return this.providers;
        }
    }

    public static class MessageBodyProviders
    extends AbstractBinder {
        private final Map<String, Object> applicationProperties;
        private final RuntimeType runtimeType;

        public MessageBodyProviders(Map<String, Object> applicationProperties, RuntimeType runtimeType) {
            this.applicationProperties = applicationProperties;
            this.runtimeType = runtimeType;
        }

        @Override
        protected void configure() {
            this.bindSingletonWorker(ByteArrayProvider.class);
            this.bindSingletonWorker(DataSourceProvider.class);
            this.bindSingletonWorker(FileProvider.class);
            this.bindSingletonWorker(FormMultivaluedMapProvider.class);
            this.bindSingletonWorker(FormProvider.class);
            this.bindSingletonWorker(InputStreamProvider.class);
            this.bindSingletonWorker(BasicTypesMessageProvider.class);
            this.bindSingletonWorker(ReaderProvider.class);
            this.bindSingletonWorker(StringMessageProvider.class);
            ((ClassBinding)this.bind(StreamingOutputProvider.class).to(MessageBodyWriter.class)).in(Singleton.class);
            EnabledProvidersBinder enabledProvidersBinder = new EnabledProvidersBinder();
            if (this.applicationProperties != null && this.applicationProperties.get("jersey.config.disableDefaultProvider") != null) {
                enabledProvidersBinder.markDisabled(String.valueOf(this.applicationProperties.get("jersey.config.disableDefaultProvider")));
            }
            enabledProvidersBinder.bindToBinder(this);
            this.install(new ServiceFinderBinder<HeaderDelegateProvider>(HeaderDelegateProvider.class, this.applicationProperties, this.runtimeType));
        }

        private <T extends MessageBodyReader & MessageBodyWriter> void bindSingletonWorker(Class<T> worker) {
            ((ClassBinding)((ClassBinding)this.bind(worker).to(MessageBodyReader.class)).to(MessageBodyWriter.class)).in(Singleton.class);
        }
    }
}

