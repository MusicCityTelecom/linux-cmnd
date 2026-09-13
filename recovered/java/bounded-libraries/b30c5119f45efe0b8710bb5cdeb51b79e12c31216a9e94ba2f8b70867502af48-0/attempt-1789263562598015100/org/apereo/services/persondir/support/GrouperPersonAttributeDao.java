/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  edu.internet2.middleware.grouperClient.api.GcGetGroups
 *  edu.internet2.middleware.grouperClient.ws.beans.WsGetGroupsResult
 *  edu.internet2.middleware.grouperClient.ws.beans.WsGroup
 *  org.apereo.services.persondir.IPersonAttributeDaoFilter
 *  org.apereo.services.persondir.IPersonAttributes
 */
package org.apereo.services.persondir.support;

import edu.internet2.middleware.grouperClient.api.GcGetGroups;
import edu.internet2.middleware.grouperClient.ws.beans.WsGetGroupsResult;
import edu.internet2.middleware.grouperClient.ws.beans.WsGroup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.apereo.services.persondir.IPersonAttributeDaoFilter;
import org.apereo.services.persondir.IPersonAttributes;
import org.apereo.services.persondir.support.BasePersonAttributeDao;
import org.apereo.services.persondir.support.IUsernameAttributeProvider;
import org.apereo.services.persondir.support.MultivaluedPersonAttributeUtils;
import org.apereo.services.persondir.support.NamedPersonImpl;
import org.apereo.services.persondir.support.SimpleUsernameAttributeProvider;

public class GrouperPersonAttributeDao
extends BasePersonAttributeDao {
    public static final String DEFAULT_GROUPER_ATTRIBUTES_KEY = "grouperGroups";
    private IUsernameAttributeProvider usernameAttributeProvider = new SimpleUsernameAttributeProvider();
    private Map<String, String> parameters = new LinkedHashMap<String, String>();
    private GrouperSubjectType subjectType = GrouperSubjectType.SUBJECT_ID;
    private GroupAttributeValueType groupAttributeValueType = GroupAttributeValueType.NAME;

    public GroupAttributeValueType getGroupAttributeValueType() {
        return this.groupAttributeValueType;
    }

    public void setGroupAttributeValueType(GroupAttributeValueType groupAttributeValueType) {
        this.groupAttributeValueType = groupAttributeValueType;
    }

    public IUsernameAttributeProvider getUsernameAttributeProvider() {
        return this.usernameAttributeProvider;
    }

    public void setUsernameAttributeProvider(IUsernameAttributeProvider usernameAttributeProvider) {
        this.usernameAttributeProvider = usernameAttributeProvider;
    }

    public GrouperSubjectType getSubjectType() {
        return this.subjectType;
    }

    public void setSubjectType(GrouperSubjectType subjectType) {
        this.subjectType = subjectType;
    }

    public Map<String, String> getParameters() {
        return this.parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public IPersonAttributes getPerson(String subjectId, IPersonAttributeDaoFilter filter) {
        if (!this.isEnabled()) {
            return null;
        }
        Objects.requireNonNull(subjectId, "username cannot be null");
        GcGetGroups groupsClient = this.getGroupsClient();
        switch (this.subjectType) {
            case SUBJECT_IDENTIFIER: {
                groupsClient.addSubjectIdentifier(subjectId);
                break;
            }
            case SUBJECT_ATTRIBUTE_NAME: {
                groupsClient.addSubjectAttributeName(subjectId);
                break;
            }
            default: {
                groupsClient.addSubjectId(subjectId);
            }
        }
        this.parameters.forEach((arg_0, arg_1) -> ((GcGetGroups)groupsClient).addParam(arg_0, arg_1));
        HashMap<String, List<Object>> grouperGroupsAsAttributesMap = new HashMap<String, List<Object>>(1);
        List<Object> groupsList = this.retrieveAttributesFromGrouper(groupsClient);
        grouperGroupsAsAttributesMap.put(DEFAULT_GROUPER_ATTRIBUTES_KEY, groupsList);
        return new NamedPersonImpl(subjectId, grouperGroupsAsAttributesMap);
    }

    public Set<String> getPossibleUserAttributeNames(IPersonAttributeDaoFilter filter) {
        return Collections.EMPTY_SET;
    }

    public Set<String> getAvailableQueryAttributes(IPersonAttributeDaoFilter filter) {
        return Collections.EMPTY_SET;
    }

    public Set<IPersonAttributes> getPeople(Map<String, Object> query, IPersonAttributeDaoFilter filter) {
        return this.getPeopleWithMultivaluedAttributes(MultivaluedPersonAttributeUtils.stuffAttributesIntoListValues(query, filter), filter);
    }

    public Set<IPersonAttributes> getPeopleWithMultivaluedAttributes(Map<String, List<Object>> query, IPersonAttributeDaoFilter filter) {
        LinkedHashSet<IPersonAttributes> people = new LinkedHashSet<IPersonAttributes>();
        String username = this.usernameAttributeProvider.getUsernameFromQuery(query);
        IPersonAttributes person = this.getPerson(username, filter);
        if (person != null) {
            people.add(person);
        }
        return people;
    }

    protected List<Object> retrieveAttributesFromGrouper(GcGetGroups groupsClient) {
        ArrayList<Object> groupsList = new ArrayList<Object>();
        try {
            for (WsGetGroupsResult groupsResult : groupsClient.execute().getResults()) {
                WsGroup[] wsGroups = groupsResult.getWsGroups();
                if (wsGroups == null) continue;
                block11: for (WsGroup group : wsGroups) {
                    switch (this.groupAttributeValueType) {
                        case NAME: {
                            groupsList.add(group.getName());
                            continue block11;
                        }
                        case EXTENSION: {
                            groupsList.add(group.getExtension());
                            continue block11;
                        }
                        case DISPLAY_EXTENSION: {
                            groupsList.add(group.getDisplayExtension());
                            continue block11;
                        }
                        case DISPLAY_NAME: {
                            groupsList.add(group.getDisplayName());
                            continue block11;
                        }
                        case UUID: {
                            groupsList.add(group.getUuid());
                            continue block11;
                        }
                        case ALTERNATE_NAME: {
                            groupsList.add(group.getAlternateName());
                        }
                    }
                }
            }
            return groupsList;
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), (Throwable)e);
            return groupsList;
        }
    }

    protected GcGetGroups getGroupsClient() {
        return new GcGetGroups();
    }

    public static enum GrouperSubjectType {
        SUBJECT_ID,
        SUBJECT_IDENTIFIER,
        SUBJECT_ATTRIBUTE_NAME;

    }

    public static enum GroupAttributeValueType {
        DISPLAY_EXTENSION,
        DISPLAY_NAME,
        UUID,
        ALTERNATE_NAME,
        NAME,
        EXTENSION;

    }
}

