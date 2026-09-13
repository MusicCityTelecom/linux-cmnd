/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  org.apache.commons.io.FilenameUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.services.persondir.IPersonAttributeDaoFilter
 *  org.apereo.services.persondir.IPersonAttributes
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.services.persondir.support;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apereo.services.persondir.IPersonAttributeDaoFilter;
import org.apereo.services.persondir.IPersonAttributes;
import org.apereo.services.persondir.support.AbstractDefaultAttributePersonAttributeDao;
import org.apereo.services.persondir.support.CaseInsensitiveNamedPersonImpl;
import org.apereo.services.persondir.support.IUsernameAttributeProvider;
import org.apereo.services.persondir.support.MultivaluedPersonAttributeUtils;
import org.apereo.services.persondir.support.NamedPersonImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class ScriptEnginePersonAttributeDao
extends AbstractDefaultAttributePersonAttributeDao {
    private static final Logger logger = LoggerFactory.getLogger(ScriptEnginePersonAttributeDao.class);
    private String scriptFile;
    private SCRIPT_TYPE scriptType;
    private String engineName;
    private boolean caseInsensitiveUsername = false;

    public String getScriptFile() {
        return this.scriptFile;
    }

    public void setScriptFile(String scriptFile) {
        this.scriptFile = scriptFile;
        this.scriptType = this.determineScriptType(scriptFile);
        if (this.scriptType != SCRIPT_TYPE.CONTENTS) {
            this.engineName = ScriptEnginePersonAttributeDao.getScriptEngineName(scriptFile);
        }
    }

    public String getEngineName() {
        return this.engineName;
    }

    protected SCRIPT_TYPE getScriptType() {
        return this.scriptType;
    }

    public void setEngineName(String engineName) {
        this.engineName = engineName;
        ScriptEngine engine = new ScriptEngineManager().getEngineByName(engineName);
        if (engine == null) {
            logger.warn("Specified engineName {} is not available in classpath.", (Object)engineName);
        }
    }

    public boolean isCaseInsensitiveUsername() {
        return this.caseInsensitiveUsername;
    }

    public void setCaseInsensitiveUsername(boolean caseInsensitiveUsername) {
        this.caseInsensitiveUsername = caseInsensitiveUsername;
    }

    public ScriptEnginePersonAttributeDao() {
    }

    public ScriptEnginePersonAttributeDao(String scriptFile) {
        this(scriptFile, ScriptEnginePersonAttributeDao.getScriptEngineName(scriptFile));
    }

    public ScriptEnginePersonAttributeDao(String scriptFile, String engineName) {
        this.setScriptFile(scriptFile);
        this.setEngineName(engineName);
    }

    public ScriptEnginePersonAttributeDao(String scriptFile, String engineName, IUsernameAttributeProvider usernameAttributeProvider) {
        this(scriptFile, engineName);
        this.setUsernameAttributeProvider(usernameAttributeProvider);
    }

    @Override
    public IPersonAttributes getPerson(String uid, IPersonAttributeDaoFilter filter) {
        try {
            if (!this.isEnabled()) {
                return null;
            }
            if (uid == null) {
                logger.warn("Unable to get attributes from script {} because username is null", (Object)this.scriptFile);
                return null;
            }
            Map<String, Object> attributes = this.getScriptedAttributesFromFile(uid, Collections.EMPTY_MAP);
            if (this.caseInsensitiveUsername) {
                return new CaseInsensitiveNamedPersonImpl(uid, MultivaluedPersonAttributeUtils.toMultivaluedMap(attributes));
            }
            return new NamedPersonImpl(uid, MultivaluedPersonAttributeUtils.toMultivaluedMap(attributes));
        }
        catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public Set<IPersonAttributes> getPeopleWithMultivaluedAttributes(Map<String, List<Object>> query, IPersonAttributeDaoFilter filter) {
        try {
            if (!this.isEnabled()) {
                return null;
            }
            String uid = this.getUsernameAttributeProvider().getUsernameFromQuery(query);
            if (uid == null) {
                logger.warn("Unable to find username in map {} using attribute {}", query, (Object)this.getUsernameAttributeProvider().getUsernameAttribute());
                return null;
            }
            LinkedHashSet<IPersonAttributes> people = new LinkedHashSet<IPersonAttributes>();
            Map<String, Object> attributes = this.getScriptedAttributesFromFile(uid, query);
            NamedPersonImpl person = this.caseInsensitiveUsername ? new CaseInsensitiveNamedPersonImpl(uid, MultivaluedPersonAttributeUtils.toMultivaluedMap(attributes)) : new NamedPersonImpl(uid, MultivaluedPersonAttributeUtils.toMultivaluedMap(attributes));
            people.add(person);
            return people;
        }
        catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    @JsonIgnore
    public Set<String> getPossibleUserAttributeNames(IPersonAttributeDaoFilter filter) {
        return Collections.EMPTY_SET;
    }

    @JsonIgnore
    public Set<String> getAvailableQueryAttributes(IPersonAttributeDaoFilter filter) {
        return Collections.EMPTY_SET;
    }

    private Map<String, Object> getScriptedAttributesFromFile(String uid, Map<String, List<Object>> queryAttributes) throws Exception {
        if (StringUtils.isBlank((CharSequence)this.scriptFile)) {
            logger.warn("Script file or contents not set.");
            return new HashMap<String, Object>();
        }
        if (StringUtils.isBlank((CharSequence)this.engineName)) {
            if (this.scriptType == SCRIPT_TYPE.CONTENTS) {
                logger.warn("Engine name not specified, not running script.");
            } else {
                logger.warn("Unable to determine engineName for script: {}, not running script", (Object)this.scriptFile);
            }
            return new HashMap<String, Object>();
        }
        ScriptEngine engine = new ScriptEngineManager().getEngineByName(this.engineName);
        if (engine == null) {
            logger.warn("Script engine is not available in classpath for [{}]", (Object)this.engineName);
            return new HashMap<String, Object>();
        }
        logger.debug("Created script engine instance for [{}]", (Object)this.engineName);
        Object[] args = new Object[]{uid, logger, queryAttributes};
        switch (this.scriptType) {
            case RESOURCE: {
                try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(this.scriptFile);){
                    if (in == null || !in.markSupported() || in.available() <= 0) break;
                    logger.debug("Loading script [{}] from classloader as a stream", (Object)this.scriptFile);
                    engine.eval(new InputStreamReader(in));
                    break;
                }
            }
            case FILE: {
                File theScriptFile = new File(this.scriptFile);
                logger.debug("Loading script from [{}]", (Object)theScriptFile);
                engine.eval(new FileReader(theScriptFile));
                break;
            }
            case CONTENTS: {
                logger.debug("Evaluating script contents [\n{}\n]", (Object)this.scriptFile);
                engine.eval(new StringReader(this.scriptFile));
                break;
            }
            default: {
                throw new IllegalStateException("Unsupported script type: " + this.scriptType);
            }
        }
        logger.debug("Executing script's run method, with parameters [{}]", args);
        Invocable invocable = (Invocable)((Object)engine);
        Map personAttributesMap = (Map)invocable.invokeFunction("run", args);
        logger.debug("Final set of attributes determined by the script are [{}]", (Object)personAttributesMap);
        return personAttributesMap;
    }

    private SCRIPT_TYPE determineScriptType(String fileName) {
        File f = new File(fileName);
        if (f.exists() && f.isFile()) {
            return SCRIPT_TYPE.FILE;
        }
        InputStream in = ScriptEnginePersonAttributeDao.class.getClassLoader().getResourceAsStream(fileName);
        try {
            if (in != null && in.markSupported() && in.available() > 0) {
                return SCRIPT_TYPE.RESOURCE;
            }
        }
        catch (IOException e) {
            logger.warn("Error checking if stream exists: {}", (Object)e.getMessage(), (Object)e);
            return SCRIPT_TYPE.CONTENTS;
        }
        return SCRIPT_TYPE.CONTENTS;
    }

    public static String getScriptEngineName(String filename) {
        String extension = FilenameUtils.getExtension((String)filename);
        if (StringUtils.isBlank((CharSequence)extension)) {
            logger.warn("Can't determine engine name based on filename without extension {}", (Object)filename);
            return null;
        }
        ScriptEngineManager manager = new ScriptEngineManager();
        List<ScriptEngineFactory> engines = manager.getEngineFactories();
        for (ScriptEngineFactory engineFactory : engines) {
            List<String> extensions = engineFactory.getExtensions();
            for (String supportedExt : extensions) {
                if (!extension.equals(supportedExt)) continue;
                return engineFactory.getNames().get(0);
            }
        }
        logger.warn("Can't determine engine name based on filename and available script engines {}", (Object)filename);
        return null;
    }

    public static enum SCRIPT_TYPE {
        RESOURCE,
        FILE,
        CONTENTS;

    }
}

