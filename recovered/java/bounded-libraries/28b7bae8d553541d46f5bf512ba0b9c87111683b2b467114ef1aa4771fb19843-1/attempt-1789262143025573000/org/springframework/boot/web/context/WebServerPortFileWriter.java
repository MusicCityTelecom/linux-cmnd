/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationListener
 *  org.springframework.core.log.LogMessage
 *  org.springframework.util.Assert
 *  org.springframework.util.FileCopyUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.web.context;

import java.io.File;
import java.util.Locale;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.system.SystemProperties;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.log.LogMessage;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

public class WebServerPortFileWriter
implements ApplicationListener<WebServerInitializedEvent> {
    private static final String DEFAULT_FILE_NAME = "application.port";
    private static final String[] PROPERTY_VARIABLES = new String[]{"PORTFILE", "portfile"};
    private static final Log logger = LogFactory.getLog(WebServerPortFileWriter.class);
    private final File file;

    public WebServerPortFileWriter() {
        this(new File(DEFAULT_FILE_NAME));
    }

    public WebServerPortFileWriter(String filename) {
        this(new File(filename));
    }

    public WebServerPortFileWriter(File file) {
        Assert.notNull((Object)file, (String)"File must not be null");
        String override = SystemProperties.get(PROPERTY_VARIABLES);
        this.file = override != null ? new File(override) : file;
    }

    public void onApplicationEvent(WebServerInitializedEvent event) {
        File portFile = this.getPortFile(event.getApplicationContext());
        try {
            String port = String.valueOf(event.getWebServer().getPort());
            this.createParentDirectory(portFile);
            FileCopyUtils.copy((byte[])port.getBytes(), (File)portFile);
            portFile.deleteOnExit();
        }
        catch (Exception ex) {
            logger.warn((Object)LogMessage.format((String)"Cannot create port file %s", (Object)this.file));
        }
    }

    protected File getPortFile(ApplicationContext applicationContext) {
        String namespace = this.getServerNamespace(applicationContext);
        if (!StringUtils.hasLength((String)namespace)) {
            return this.file;
        }
        String name = this.file.getName();
        String extension = StringUtils.getFilenameExtension((String)this.file.getName());
        name = this.isUpperCase(name = name.substring(0, name.length() - extension.length() - 1)) ? name + "-" + namespace.toUpperCase(Locale.ENGLISH) : name + "-" + namespace.toLowerCase(Locale.ENGLISH);
        if (StringUtils.hasLength((String)extension)) {
            name = name + "." + extension;
        }
        return new File(this.file.getParentFile(), name);
    }

    private String getServerNamespace(ApplicationContext applicationContext) {
        if (applicationContext instanceof WebServerApplicationContext) {
            return ((WebServerApplicationContext)applicationContext).getServerNamespace();
        }
        return null;
    }

    private boolean isUpperCase(String name) {
        for (int i = 0; i < name.length(); ++i) {
            if (!Character.isLetter(name.charAt(i)) || Character.isUpperCase(name.charAt(i))) continue;
            return false;
        }
        return true;
    }

    private void createParentDirectory(File file) {
        File parent = file.getParentFile();
        if (parent != null) {
            parent.mkdirs();
        }
    }
}

