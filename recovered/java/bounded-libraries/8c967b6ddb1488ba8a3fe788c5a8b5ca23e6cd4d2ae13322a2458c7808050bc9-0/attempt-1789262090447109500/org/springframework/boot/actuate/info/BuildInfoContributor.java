/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.info.BuildProperties
 *  org.springframework.core.env.PropertiesPropertySource
 *  org.springframework.core.env.PropertySource
 */
package org.springframework.boot.actuate.info;

import java.util.Map;
import java.util.Properties;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoPropertiesInfoContributor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;

public class BuildInfoContributor
extends InfoPropertiesInfoContributor<BuildProperties> {
    public BuildInfoContributor(BuildProperties properties) {
        super(properties, InfoPropertiesInfoContributor.Mode.FULL);
    }

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("build", this.generateContent());
    }

    @Override
    protected PropertySource<?> toSimplePropertySource() {
        Properties props = new Properties();
        this.copyIfSet(props, "group");
        this.copyIfSet(props, "artifact");
        this.copyIfSet(props, "name");
        this.copyIfSet(props, "version");
        this.copyIfSet(props, "time");
        return new PropertiesPropertySource("build", props);
    }

    @Override
    protected void postProcessContent(Map<String, Object> content) {
        this.replaceValue(content, "time", ((BuildProperties)this.getProperties()).getTime());
    }
}

