/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.core.jmx;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import javax.management.openmbean.TabularData;
import javax.management.openmbean.TabularDataSupport;
import javax.management.openmbean.TabularType;
import org.quartz.CronTrigger;
import org.quartz.core.jmx.JobDataMapSupport;
import org.quartz.core.jmx.TriggerSupport;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.spi.MutableTrigger;
import org.quartz.spi.OperableTrigger;

public class CronTriggerSupport {
    private static final String COMPOSITE_TYPE_NAME = "CronTrigger";
    private static final String COMPOSITE_TYPE_DESCRIPTION = "CronTrigger Details";
    private static final String[] ITEM_NAMES = new String[]{"expression", "timeZone"};
    private static final String[] ITEM_DESCRIPTIONS = new String[]{"expression", "timeZone"};
    private static final OpenType[] ITEM_TYPES = new OpenType[]{SimpleType.STRING, SimpleType.STRING};
    private static final CompositeType COMPOSITE_TYPE;
    private static final String TABULAR_TYPE_NAME = "CronTrigger collection";
    private static final String TABULAR_TYPE_DESCRIPTION = "CronTrigger collection";
    private static final TabularType TABULAR_TYPE;

    public static String[] getItemNames() {
        ArrayList<String> l = new ArrayList<String>(Arrays.asList(ITEM_NAMES));
        l.addAll(Arrays.asList(TriggerSupport.getItemNames()));
        return l.toArray(new String[l.size()]);
    }

    public static String[] getItemDescriptions() {
        ArrayList<String> l = new ArrayList<String>(Arrays.asList(ITEM_DESCRIPTIONS));
        l.addAll(Arrays.asList(TriggerSupport.getItemDescriptions()));
        return l.toArray(new String[l.size()]);
    }

    public static OpenType[] getItemTypes() {
        ArrayList<OpenType> l = new ArrayList<OpenType>(Arrays.asList(ITEM_TYPES));
        l.addAll(Arrays.asList(TriggerSupport.getItemTypes()));
        return l.toArray(new OpenType[l.size()]);
    }

    public static CompositeData toCompositeData(CronTrigger trigger) {
        try {
            return new CompositeDataSupport(COMPOSITE_TYPE, ITEM_NAMES, new Object[]{trigger.getCronExpression(), trigger.getTimeZone(), trigger.getKey().getName(), trigger.getKey().getGroup(), trigger.getJobKey().getName(), trigger.getJobKey().getGroup(), trigger.getDescription(), JobDataMapSupport.toTabularData(trigger.getJobDataMap()), trigger.getCalendarName(), ((OperableTrigger)((Object)trigger)).getFireInstanceId(), trigger.getMisfireInstruction(), trigger.getPriority(), trigger.getStartTime(), trigger.getEndTime(), trigger.getNextFireTime(), trigger.getPreviousFireTime(), trigger.getFinalFireTime()});
        }
        catch (OpenDataException e) {
            throw new RuntimeException(e);
        }
    }

    public static TabularData toTabularData(List<? extends CronTrigger> triggers) {
        TabularDataSupport tData = new TabularDataSupport(TABULAR_TYPE);
        if (triggers != null) {
            ArrayList<CompositeData> list = new ArrayList<CompositeData>();
            for (CronTrigger cronTrigger : triggers) {
                list.add(CronTriggerSupport.toCompositeData(cronTrigger));
            }
            tData.putAll(list.toArray(new CompositeData[list.size()]));
        }
        return tData;
    }

    public static OperableTrigger newTrigger(CompositeData cData) throws ParseException {
        CronTriggerImpl result = new CronTriggerImpl();
        result.setCronExpression((String)cData.get("cronExpression"));
        if (cData.containsKey("timeZone")) {
            result.setTimeZone(TimeZone.getTimeZone((String)cData.get("timeZone")));
        }
        TriggerSupport.initializeTrigger((MutableTrigger)result, cData);
        return result;
    }

    public static OperableTrigger newTrigger(Map<String, Object> attrMap) throws ParseException {
        CronTriggerImpl result = new CronTriggerImpl();
        result.setCronExpression((String)attrMap.get("cronExpression"));
        if (attrMap.containsKey("timeZone")) {
            result.setTimeZone(TimeZone.getTimeZone((String)attrMap.get("timeZone")));
        }
        TriggerSupport.initializeTrigger((MutableTrigger)result, attrMap);
        return result;
    }

    static {
        try {
            COMPOSITE_TYPE = new CompositeType(COMPOSITE_TYPE_NAME, COMPOSITE_TYPE_DESCRIPTION, CronTriggerSupport.getItemNames(), CronTriggerSupport.getItemDescriptions(), CronTriggerSupport.getItemTypes());
            TABULAR_TYPE = new TabularType("CronTrigger collection", "CronTrigger collection", COMPOSITE_TYPE, CronTriggerSupport.getItemNames());
        }
        catch (OpenDataException e) {
            throw new RuntimeException(e);
        }
    }
}

