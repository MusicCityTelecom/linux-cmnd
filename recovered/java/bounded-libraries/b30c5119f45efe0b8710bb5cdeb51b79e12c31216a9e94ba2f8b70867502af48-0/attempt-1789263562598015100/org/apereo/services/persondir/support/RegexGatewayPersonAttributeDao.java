/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  org.apache.commons.lang3.Validate
 *  org.apereo.services.persondir.IPersonAttributeDao
 *  org.apereo.services.persondir.IPersonAttributeDaoFilter
 *  org.apereo.services.persondir.IPersonAttributes
 */
package org.apereo.services.persondir.support;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.Validate;
import org.apereo.services.persondir.IPersonAttributeDao;
import org.apereo.services.persondir.IPersonAttributeDaoFilter;
import org.apereo.services.persondir.IPersonAttributes;
import org.apereo.services.persondir.support.AbstractDefaultAttributePersonAttributeDao;
import org.apereo.services.persondir.support.SimpleUsernameAttributeProvider;
import org.apereo.services.persondir.util.CollectionsUtil;

public final class RegexGatewayPersonAttributeDao
extends AbstractDefaultAttributePersonAttributeDao {
    private boolean matchAllPatterns = false;
    private boolean matchAllValues = false;
    private Map<String, Pattern> patterns = null;
    private IPersonAttributeDao targetPersonAttributeDao = null;

    public RegexGatewayPersonAttributeDao() {
    }

    public RegexGatewayPersonAttributeDao(String attributeName, String pattern, IPersonAttributeDao enclosed) {
        SimpleUsernameAttributeProvider usernameAttributeProvider = new SimpleUsernameAttributeProvider(attributeName);
        this.setUsernameAttributeProvider(usernameAttributeProvider);
        this.setPatterns(Collections.singletonMap(attributeName, pattern));
        this.setTargetPersonAttributeDao(enclosed);
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Created RegexGatewayPersonAttributeDao with defaultAttributeName='" + attributeName + "' and patterns=" + this.patterns);
        }
    }

    public Map<String, String> getPatterns() {
        if (this.patterns == null) {
            return null;
        }
        LinkedHashMap<String, String> toReturn = new LinkedHashMap<String, String>(this.patterns.size());
        for (Map.Entry<String, Pattern> patternEntry : this.patterns.entrySet()) {
            String attribute = patternEntry.getKey();
            Pattern pattern = patternEntry.getValue();
            toReturn.put(attribute, pattern.pattern());
        }
        return toReturn;
    }

    public void setPatterns(Map<String, String> patterns) {
        Validate.notEmpty(patterns, (String)"patterns Map may not be null and must contain at least 1 mapping.", (Object[])new Object[0]);
        LinkedHashMap<String, Pattern> newPatterns = new LinkedHashMap<String, Pattern>(patterns.size());
        for (Map.Entry<String, String> patternEntry : patterns.entrySet()) {
            String attribute = patternEntry.getKey();
            String pattern = patternEntry.getValue();
            Validate.notNull((Object)pattern, (String)("pattern can not be null. attribute=" + attribute), (Object[])new Object[0]);
            Pattern compiledPattern = Pattern.compile(pattern);
            newPatterns.put(attribute, compiledPattern);
        }
        this.patterns = CollectionsUtil.safelyWrapAsUnmodifiableMap(newPatterns);
    }

    public IPersonAttributeDao getTargetPersonAttributeDao() {
        return this.targetPersonAttributeDao;
    }

    public void setTargetPersonAttributeDao(IPersonAttributeDao targetPersonAttributeDao) {
        Validate.notNull((Object)targetPersonAttributeDao, (String)"targetPersonAttributeDao may not be null", (Object[])new Object[0]);
        this.targetPersonAttributeDao = targetPersonAttributeDao;
    }

    public boolean isMatchAllPatterns() {
        return this.matchAllPatterns;
    }

    public void setMatchAllPatterns(boolean matchAllPatterns) {
        this.matchAllPatterns = matchAllPatterns;
    }

    public boolean isMatchAllValues() {
        return this.matchAllValues;
    }

    public void setMatchAllValues(boolean matchAllValues) {
        this.matchAllValues = matchAllValues;
    }

    public Set<IPersonAttributes> getPeopleWithMultivaluedAttributes(Map<String, List<Object>> seed, IPersonAttributeDaoFilter filter) {
        Validate.notNull(seed, (String)"Argument 'seed' cannot be null.", (Object[])new Object[0]);
        if (this.patterns == null || this.patterns.size() < 1) {
            throw new IllegalStateException("patterns Map may not be null and must contain at least 1 mapping.");
        }
        if (this.targetPersonAttributeDao == null) {
            throw new IllegalStateException("targetPersonAttributeDao may not be null");
        }
        boolean matchedPatterns = false;
        for (Map.Entry<String, Pattern> patternEntry : this.patterns.entrySet()) {
            String attributeName = patternEntry.getKey();
            List<Object> attributeValues = seed.get(attributeName);
            if (attributeValues == null) {
                if (!this.matchAllPatterns) continue;
                if (this.logger.isInfoEnabled()) {
                    this.logger.info("All patterns must match and attribute='" + attributeName + "' does not exist in the seed, returning null.");
                }
                return null;
            }
            Pattern compiledPattern = patternEntry.getValue();
            if (compiledPattern == null) {
                throw new IllegalStateException("Attribute '" + attributeName + "' has a null pattern");
            }
            boolean matchedValues = false;
            for (Object valueObj : attributeValues) {
                String value;
                try {
                    value = (String)valueObj;
                }
                catch (ClassCastException cce) {
                    IllegalArgumentException iae = new IllegalArgumentException("RegexGatewayPersonAttributeDao can only accept seeds who's values are String or List of String. Attribute '" + attributeName + "' has a non-String value.");
                    iae.initCause(cce);
                    throw iae;
                }
                Matcher valueMatcher = compiledPattern.matcher(value);
                matchedValues = valueMatcher.matches();
                if (matchedValues && !this.matchAllValues) {
                    if (!this.logger.isDebugEnabled()) break;
                    this.logger.debug("value='" + value + "' matched pattern='" + compiledPattern + "' and only one value match is needed, leaving value matching loop.");
                    break;
                }
                if (!matchedValues && this.matchAllValues) {
                    if (!this.logger.isDebugEnabled()) break;
                    this.logger.debug("value='" + value + "' did not match pattern='" + compiledPattern + "' and all values need to match, leaving value matching loop.");
                    break;
                }
                if (!this.logger.isDebugEnabled()) continue;
                if (matchedValues) {
                    this.logger.debug("value='" + value + "' matched pattern='" + compiledPattern + "' and all values need to match, continuing value matching loop.");
                    continue;
                }
                this.logger.debug("value='" + value + "' did not match pattern='" + compiledPattern + "' and only one value match is needed, continuing value matching loop.");
            }
            if ((matchedPatterns = matchedValues) && !this.matchAllPatterns) {
                if (!this.logger.isDebugEnabled()) break;
                this.logger.debug("pattern='" + compiledPattern + "' found a match and only one pattern match is needed, leaving pattern matching loop.");
                break;
            }
            if (!matchedPatterns && this.matchAllPatterns) {
                if (!this.logger.isDebugEnabled()) break;
                this.logger.debug("pattern='" + compiledPattern + "' did not find a match and all patterns need to match, leaving pattern matching loop.");
                break;
            }
            if (!this.logger.isDebugEnabled()) continue;
            if (matchedPatterns) {
                this.logger.debug("pattern='" + compiledPattern + "' found a match and all patterns need to match, continuing pattern matching loop.");
                continue;
            }
            this.logger.debug("pattern='" + compiledPattern + "' did not find a match and only one pattern match is needed, continuing pattern matching loop.");
        }
        if (matchedPatterns) {
            if (this.logger.isInfoEnabled()) {
                this.logger.info("Matching criteria '" + this.patterns + "' was met for query '" + seed + "', delegating call to the targetPersonAttributeDao='" + this.targetPersonAttributeDao + "'");
            }
            return this.targetPersonAttributeDao.getPeopleWithMultivaluedAttributes(seed, filter);
        }
        if (this.logger.isInfoEnabled()) {
            this.logger.info("Matching criteria '" + this.patterns + "' was not met for query '" + seed + "', return null");
        }
        return null;
    }

    @JsonIgnore
    public Set<String> getPossibleUserAttributeNames(IPersonAttributeDaoFilter filter) {
        return this.targetPersonAttributeDao.getPossibleUserAttributeNames(filter);
    }

    @JsonIgnore
    public Set<String> getAvailableQueryAttributes(IPersonAttributeDaoFilter filter) {
        return this.targetPersonAttributeDao.getAvailableQueryAttributes(filter);
    }
}

