/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.BooleanUtils
 *  org.apache.commons.lang3.math.NumberUtils
 *  org.apereo.services.persondir.IPersonAttributeDao
 *  org.apereo.services.persondir.support.NamedStubPersonAttributeDao
 *  org.jooq.lambda.Unchecked
 *  org.springframework.beans.factory.FactoryBean
 *  org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean
 *  org.springframework.util.StringUtils
 */
package org.apereo.cas.configuration.support;

import java.io.File;
import java.time.Duration;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apereo.cas.configuration.model.core.authentication.AttributeRepositoryStates;
import org.apereo.cas.configuration.model.core.authentication.PrincipalAttributesProperties;
import org.apereo.cas.configuration.model.core.authentication.StubPrincipalAttributesProperties;
import org.apereo.cas.configuration.model.support.ConnectionPoolingProperties;
import org.apereo.services.persondir.IPersonAttributeDao;
import org.apereo.services.persondir.support.NamedStubPersonAttributeDao;
import org.jooq.lambda.Unchecked;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;
import org.springframework.util.StringUtils;

public final class Beans {
    public static FactoryBean<ExecutorService> newThreadPoolExecutorFactoryBean(ConnectionPoolingProperties config) {
        ThreadPoolExecutorFactoryBean bean = new ThreadPoolExecutorFactoryBean();
        bean.setMaxPoolSize(config.getMaxSize());
        bean.setCorePoolSize(config.getMinSize());
        bean.afterPropertiesSet();
        return bean;
    }

    public static IPersonAttributeDao newStubAttributeRepository(PrincipalAttributesProperties p) {
        NamedStubPersonAttributeDao dao = new NamedStubPersonAttributeDao();
        LinkedHashMap backingMap = new LinkedHashMap();
        StubPrincipalAttributesProperties stub = p.getStub();
        stub.getAttributes().forEach((key, value) -> {
            String[] vals = StringUtils.commaDelimitedListToStringArray((String)value);
            backingMap.put(key, Arrays.stream(vals).map(v -> {
                Boolean bool = BooleanUtils.toBooleanObject((String)v);
                if (bool != null) {
                    return bool;
                }
                return v;
            }).collect(Collectors.toList()));
        });
        dao.setBackingMap(backingMap);
        dao.setOrder(stub.getOrder());
        dao.setEnabled(stub.getState() != AttributeRepositoryStates.DISABLED);
        dao.putTag("state", (Object)(stub.getState() == AttributeRepositoryStates.ACTIVE ? 1 : 0));
        if (StringUtils.hasText((String)stub.getId())) {
            dao.setId(new String[]{stub.getId()});
        }
        return dao;
    }

    public static Duration newDuration(String value) {
        if (Beans.isNeverDurable(value)) {
            return Duration.ZERO;
        }
        if (Beans.isInfinitelyDurable(value)) {
            return Duration.ofDays(Integer.MAX_VALUE);
        }
        if (NumberUtils.isCreatable((String)value)) {
            return Duration.ofSeconds(Long.parseLong(value));
        }
        return Duration.parse(value);
    }

    public static boolean isInfinitelyDurable(String value) {
        return "-1".equalsIgnoreCase(value) || !StringUtils.hasText((String)value) || "INFINITE".equalsIgnoreCase(value);
    }

    public static boolean isNeverDurable(String value) {
        return "0".equalsIgnoreCase(value) || "NEVER".equalsIgnoreCase(value) || !StringUtils.hasText((String)value);
    }

    public static String getTempFilePath(String prefix, String suffix) {
        return (String)Unchecked.supplier(() -> File.createTempFile(prefix, suffix).getCanonicalPath()).get();
    }

    @Generated
    private Beans() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

