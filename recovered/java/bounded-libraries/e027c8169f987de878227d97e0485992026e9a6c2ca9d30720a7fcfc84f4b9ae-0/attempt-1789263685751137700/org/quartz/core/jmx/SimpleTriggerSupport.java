/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.core.jmx;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import javax.management.openmbean.TabularData;
import javax.management.openmbean.TabularDataSupport;
import javax.management.openmbean.TabularType;
import org.quartz.SimpleTrigger;
import org.quartz.core.jmx.JobDataMapSupport;
import org.quartz.core.jmx.TriggerSupport;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.quartz.spi.MutableTrigger;
import org.quartz.spi.OperableTrigger;

public class SimpleTriggerSupport {
    private static final String COMPOSITE_TYPE_NAME = "SimpleTrigger";
    private static final String COMPOSITE_TYPE_DESCRIPTION = "SimpleTrigger Details";
    private static final String[] ITEM_NAMES = new String[]{"repeatCount", "repeatInterval", "timesTriggered"};
    private static final String[] ITEM_DESCRIPTIONS = new String[]{"repeatCount", "repeatInterval", "timesTriggered"};
    private static final OpenType[] ITEM_TYPES = new OpenType[]{SimpleType.INTEGER, SimpleType.LONG, SimpleType.INTEGER};
    private static final CompositeType COMPOSITE_TYPE;
    private static final String TABULAR_TYPE_NAME = "SimpleTrigger collection";
    private static final String TABULAR_TYPE_DESCRIPTION = "SimpleTrigger collection";
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

    public static CompositeData toCompositeData(SimpleTrigger trigger) {
        try {
            return new CompositeDataSupport(COMPOSITE_TYPE, ITEM_NAMES, new Object[]{trigger.getRepeatCount(), trigger.getRepeatInterval(), trigger.getTimesTriggered(), trigger.getKey().getName(), trigger.getKey().getGroup(), trigger.getJobKey().getName(), trigger.getJobKey().getGroup(), trigger.getDescription(), JobDataMapSupport.toTabularData(trigger.getJobDataMap()), trigger.getCalendarName(), ((OperableTrigger)((Object)trigger)).getFireInstanceId(), trigger.getMisfireInstruction(), trigger.getPriority(), trigger.getStartTime(), trigger.getEndTime(), trigger.getNextFireTime(), trigger.getPreviousFireTime(), trigger.getFinalFireTime()});
        }
        catch (OpenDataException e) {
            throw new RuntimeException(e);
        }
    }

    public static TabularData toTabularData(List<? extends SimpleTrigger> triggers) {
        TabularDataSupport tData = new TabularDataSupport(TABULAR_TYPE);
        if (triggers != null) {
            ArrayList<CompositeData> list = new ArrayList<CompositeData>();
            for (SimpleTrigger simpleTrigger : triggers) {
                list.add(SimpleTriggerSupport.toCompositeData(simpleTrigger));
            }
            tData.putAll(list.toArray(new CompositeData[list.size()]));
        }
        return tData;
    }

    public static OperableTrigger newTrigger(CompositeData cData) throws ParseException {
        SimpleTriggerImpl result = new SimpleTriggerImpl();
        result.setRepeatCount((Integer)cData.get("repeatCount"));
        result.setRepeatInterval((Long)cData.get("repeatInterval"));
        result.setTimesTriggered((Integer)cData.get("timesTriggered"));
        TriggerSupport.initializeTrigger((MutableTrigger)result, cData);
        return result;
    }

    public static OperableTrigger newTrigger(Map<String, Object> attrMap) throws ParseException {
        SimpleTriggerImpl result = new SimpleTriggerImpl();
        if (attrMap.containsKey("repeatCount")) {
            result.setRepeatCount((Integer)attrMap.get("repeatCount"));
        }
        if (attrMap.containsKey("repeatInterval")) {
            result.setRepeatInterval((Long)attrMap.get("repeatInterval"));
        }
        if (attrMap.containsKey("timesTriggered")) {
            result.setTimesTriggered((Integer)attrMap.get("timesTriggered"));
        }
        TriggerSupport.initializeTrigger((MutableTrigger)result, attrMap);
        return result;
    }

    static {
        try {
            COMPOSITE_TYPE = new CompositeType(COMPOSITE_TYPE_NAME, COMPOSITE_TYPE_DESCRIPTION, SimpleTriggerSupport.getItemNames(), SimpleTriggerSupport.getItemDescriptions(), SimpleTriggerSupport.getItemTypes());
            TABULAR_TYPE = new TabularType("SimpleTrigger collection", "SimpleTrigger collection", COMPOSITE_TYPE, SimpleTriggerSupport.getItemNames());
        }
        catch (OpenDataException e) {
            throw new RuntimeException(e);
        }
    }
}

