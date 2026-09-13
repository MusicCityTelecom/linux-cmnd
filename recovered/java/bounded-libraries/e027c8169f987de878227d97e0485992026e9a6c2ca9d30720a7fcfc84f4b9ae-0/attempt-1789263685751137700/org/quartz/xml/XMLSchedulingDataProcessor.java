/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.xml.bind.DatatypeConverter
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.quartz.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import javax.xml.bind.DatatypeConverter;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.quartz.CalendarIntervalScheduleBuilder;
import org.quartz.CronScheduleBuilder;
import org.quartz.DateBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.JobPersistenceException;
import org.quartz.ObjectAlreadyExistsException;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.spi.ClassLoadHelper;
import org.quartz.spi.MutableTrigger;
import org.quartz.xml.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XMLSchedulingDataProcessor
implements ErrorHandler {
    public static final String QUARTZ_NS = "http://www.quartz-scheduler.org/xml/JobSchedulingData";
    public static final String QUARTZ_SCHEMA_WEB_URL = "http://www.quartz-scheduler.org/xml/job_scheduling_data_2_0.xsd";
    public static final String QUARTZ_XSD_PATH_IN_JAR = "org/quartz/xml/job_scheduling_data_2_0.xsd";
    public static final String QUARTZ_XML_DEFAULT_FILE_NAME = "quartz_data.xml";
    public static final String QUARTZ_SYSTEM_ID_JAR_PREFIX = "jar:";
    protected List<String> jobGroupsToDelete = new LinkedList<String>();
    protected List<String> triggerGroupsToDelete = new LinkedList<String>();
    protected List<JobKey> jobsToDelete = new LinkedList<JobKey>();
    protected List<TriggerKey> triggersToDelete = new LinkedList<TriggerKey>();
    protected List<JobDetail> loadedJobs = new LinkedList<JobDetail>();
    protected List<MutableTrigger> loadedTriggers = new LinkedList<MutableTrigger>();
    private boolean overWriteExistingData = true;
    private boolean ignoreDuplicates = false;
    protected Collection<Exception> validationExceptions = new ArrayList<Exception>();
    protected ClassLoadHelper classLoadHelper;
    protected List<String> jobGroupsToNeverDelete = new LinkedList<String>();
    protected List<String> triggerGroupsToNeverDelete = new LinkedList<String>();
    private DocumentBuilder docBuilder = null;
    private XPath xpath = null;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public XMLSchedulingDataProcessor(ClassLoadHelper clh) throws ParserConfigurationException {
        this.classLoadHelper = clh;
        this.initDocumentParser();
    }

    protected void initDocumentParser() throws ParserConfigurationException {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        docBuilderFactory.setNamespaceAware(true);
        docBuilderFactory.setValidating(true);
        docBuilderFactory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
        docBuilderFactory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource", this.resolveSchemaSource());
        docBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        docBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        docBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        docBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        docBuilderFactory.setXIncludeAware(false);
        docBuilderFactory.setExpandEntityReferences(false);
        this.docBuilder = docBuilderFactory.newDocumentBuilder();
        this.docBuilder.setErrorHandler(this);
        NamespaceContext nsContext = new NamespaceContext(){

            @Override
            public String getNamespaceURI(String prefix) {
                if (prefix == null) {
                    throw new IllegalArgumentException("Null prefix");
                }
                if ("xml".equals(prefix)) {
                    return "http://www.w3.org/XML/1998/namespace";
                }
                if ("xmlns".equals(prefix)) {
                    return "http://www.w3.org/2000/xmlns/";
                }
                if ("q".equals(prefix)) {
                    return XMLSchedulingDataProcessor.QUARTZ_NS;
                }
                return "";
            }

            public Iterator<?> getPrefixes(String namespaceURI) {
                throw new UnsupportedOperationException();
            }

            @Override
            public String getPrefix(String namespaceURI) {
                throw new UnsupportedOperationException();
            }
        };
        this.xpath = XPathFactory.newInstance().newXPath();
        this.xpath.setNamespaceContext(nsContext);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected Object resolveSchemaSource() {
        InputSource inputSource;
        InputStream is = null;
        try {
            is = this.classLoadHelper.getResourceAsStream(QUARTZ_XSD_PATH_IN_JAR);
        }
        finally {
            if (is == null) {
                this.log.info("Unable to load local schema packaged in quartz distribution jar. Utilizing schema online at http://www.quartz-scheduler.org/xml/job_scheduling_data_2_0.xsd");
                return QUARTZ_SCHEMA_WEB_URL;
            }
            inputSource = new InputSource(is);
            inputSource.setSystemId(QUARTZ_SCHEMA_WEB_URL);
            this.log.debug("Utilizing schema packaged in local quartz distribution jar.");
        }
        return inputSource;
    }

    public boolean isOverWriteExistingData() {
        return this.overWriteExistingData;
    }

    protected void setOverWriteExistingData(boolean overWriteExistingData) {
        this.overWriteExistingData = overWriteExistingData;
    }

    public boolean isIgnoreDuplicates() {
        return this.ignoreDuplicates;
    }

    public void setIgnoreDuplicates(boolean ignoreDuplicates) {
        this.ignoreDuplicates = ignoreDuplicates;
    }

    public void addJobGroupToNeverDelete(String group) {
        if (group != null) {
            this.jobGroupsToNeverDelete.add(group);
        }
    }

    public boolean removeJobGroupToNeverDelete(String group) {
        return group != null && this.jobGroupsToNeverDelete.remove(group);
    }

    public List<String> getJobGroupsToNeverDelete() {
        return Collections.unmodifiableList(this.jobGroupsToDelete);
    }

    public void addTriggerGroupToNeverDelete(String group) {
        if (group != null) {
            this.triggerGroupsToNeverDelete.add(group);
        }
    }

    public boolean removeTriggerGroupToNeverDelete(String group) {
        if (group != null) {
            return this.triggerGroupsToNeverDelete.remove(group);
        }
        return false;
    }

    public List<String> getTriggerGroupsToNeverDelete() {
        return Collections.unmodifiableList(this.triggerGroupsToDelete);
    }

    protected void processFile() throws Exception {
        this.processFile(QUARTZ_XML_DEFAULT_FILE_NAME);
    }

    protected void processFile(String fileName) throws Exception {
        this.processFile(fileName, this.getSystemIdForFileName(fileName));
    }

    protected String getSystemIdForFileName(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            try {
                new FileInputStream(file).close();
                return file.toURI().toString();
            }
            catch (IOException ignore) {
                return fileName;
            }
        }
        URL url = this.getURL(fileName);
        if (url == null) {
            return fileName;
        }
        try {
            url.openStream().close();
            return url.toString();
        }
        catch (IOException ignore) {
            return fileName;
        }
    }

    protected URL getURL(String fileName) {
        return this.classLoadHelper.getResource(fileName);
    }

    protected void prepForProcessing() {
        this.clearValidationExceptions();
        this.setOverWriteExistingData(true);
        this.setIgnoreDuplicates(false);
        this.jobGroupsToDelete.clear();
        this.jobsToDelete.clear();
        this.triggerGroupsToDelete.clear();
        this.triggersToDelete.clear();
        this.loadedJobs.clear();
        this.loadedTriggers.clear();
    }

    protected void processFile(String fileName, String systemId) throws ValidationException, ParserConfigurationException, SAXException, IOException, SchedulerException, ClassNotFoundException, ParseException, XPathException {
        this.prepForProcessing();
        this.log.info("Parsing XML file: " + fileName + " with systemId: " + systemId);
        InputSource is = new InputSource(this.getInputStream(fileName));
        is.setSystemId(systemId);
        this.process(is);
        this.maybeThrowValidationException();
    }

    public void processStreamAndScheduleJobs(InputStream stream, String systemId, Scheduler sched) throws ValidationException, ParserConfigurationException, SAXException, XPathException, IOException, SchedulerException, ClassNotFoundException, ParseException {
        this.prepForProcessing();
        this.log.info("Parsing XML from stream with systemId: " + systemId);
        InputSource is = new InputSource(stream);
        is.setSystemId(systemId);
        this.process(is);
        this.executePreProcessCommands(sched);
        this.scheduleJobs(sched);
        this.maybeThrowValidationException();
    }

    protected void process(InputSource is) throws SAXException, IOException, ParseException, XPathException, ClassNotFoundException {
        Document document = this.docBuilder.parse(is);
        NodeList deleteJobGroupNodes = (NodeList)this.xpath.evaluate("/q:job-scheduling-data/q:pre-processing-commands/q:delete-jobs-in-group", document, XPathConstants.NODESET);
        this.log.debug("Found " + deleteJobGroupNodes.getLength() + " delete job group commands.");
        for (int i = 0; i < deleteJobGroupNodes.getLength(); ++i) {
            Node node = deleteJobGroupNodes.item(i);
            String t = node.getTextContent();
            if (t == null || (t = t.trim()).length() == 0) continue;
            this.jobGroupsToDelete.add(t);
        }
        NodeList deleteTriggerGroupNodes = (NodeList)this.xpath.evaluate("/q:job-scheduling-data/q:pre-processing-commands/q:delete-triggers-in-group", document, XPathConstants.NODESET);
        this.log.debug("Found " + deleteTriggerGroupNodes.getLength() + " delete trigger group commands.");
        for (int i = 0; i < deleteTriggerGroupNodes.getLength(); ++i) {
            Node node = deleteTriggerGroupNodes.item(i);
            String t = node.getTextContent();
            if (t == null || (t = t.trim()).length() == 0) continue;
            this.triggerGroupsToDelete.add(t);
        }
        NodeList deleteJobNodes = (NodeList)this.xpath.evaluate("/q:job-scheduling-data/q:pre-processing-commands/q:delete-job", document, XPathConstants.NODESET);
        this.log.debug("Found " + deleteJobNodes.getLength() + " delete job commands.");
        for (int i = 0; i < deleteJobNodes.getLength(); ++i) {
            Node node = deleteJobNodes.item(i);
            String name = this.getTrimmedToNullString(this.xpath, "q:name", node);
            String group = this.getTrimmedToNullString(this.xpath, "q:group", node);
            if (name == null) {
                throw new ParseException("Encountered a 'delete-job' command without a name specified.", -1);
            }
            this.jobsToDelete.add(new JobKey(name, group));
        }
        NodeList deleteTriggerNodes = (NodeList)this.xpath.evaluate("/q:job-scheduling-data/q:pre-processing-commands/q:delete-trigger", document, XPathConstants.NODESET);
        this.log.debug("Found " + deleteTriggerNodes.getLength() + " delete trigger commands.");
        for (int i = 0; i < deleteTriggerNodes.getLength(); ++i) {
            Node node = deleteTriggerNodes.item(i);
            String name = this.getTrimmedToNullString(this.xpath, "q:name", node);
            String group = this.getTrimmedToNullString(this.xpath, "q:group", node);
            if (name == null) {
                throw new ParseException("Encountered a 'delete-trigger' command without a name specified.", -1);
            }
            this.triggersToDelete.add(new TriggerKey(name, group));
        }
        Boolean overWrite = this.getBoolean(this.xpath, "/q:job-scheduling-data/q:processing-directives/q:overwrite-existing-data", document);
        if (overWrite == null) {
            this.log.debug("Directive 'overwrite-existing-data' not specified, defaulting to " + this.isOverWriteExistingData());
        } else {
            this.log.debug("Directive 'overwrite-existing-data' specified as: " + overWrite);
            this.setOverWriteExistingData(overWrite);
        }
        Boolean ignoreDupes = this.getBoolean(this.xpath, "/q:job-scheduling-data/q:processing-directives/q:ignore-duplicates", document);
        if (ignoreDupes == null) {
            this.log.debug("Directive 'ignore-duplicates' not specified, defaulting to " + this.isIgnoreDuplicates());
        } else {
            this.log.debug("Directive 'ignore-duplicates' specified as: " + ignoreDupes);
            this.setIgnoreDuplicates(ignoreDupes);
        }
        NodeList jobNodes = (NodeList)this.xpath.evaluate("/q:job-scheduling-data/q:schedule/q:job", document, XPathConstants.NODESET);
        this.log.debug("Found " + jobNodes.getLength() + " job definitions.");
        for (int i = 0; i < jobNodes.getLength(); ++i) {
            Node jobDetailNode = jobNodes.item(i);
            String t = null;
            String jobName = this.getTrimmedToNullString(this.xpath, "q:name", jobDetailNode);
            String jobGroup = this.getTrimmedToNullString(this.xpath, "q:group", jobDetailNode);
            String jobDescription = this.getTrimmedToNullString(this.xpath, "q:description", jobDetailNode);
            String jobClassName = this.getTrimmedToNullString(this.xpath, "q:job-class", jobDetailNode);
            t = this.getTrimmedToNullString(this.xpath, "q:durability", jobDetailNode);
            boolean jobDurability = t != null && t.equals("true");
            t = this.getTrimmedToNullString(this.xpath, "q:recover", jobDetailNode);
            boolean jobRecoveryRequested = t != null && t.equals("true");
            Class<Job> jobClass = this.classLoadHelper.loadClass(jobClassName, Job.class);
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroup).withDescription(jobDescription).storeDurably(jobDurability).requestRecovery(jobRecoveryRequested).build();
            NodeList jobDataEntries = (NodeList)this.xpath.evaluate("q:job-data-map/q:entry", jobDetailNode, XPathConstants.NODESET);
            for (int k = 0; k < jobDataEntries.getLength(); ++k) {
                Node entryNode = jobDataEntries.item(k);
                String key = this.getTrimmedToNullString(this.xpath, "q:key", entryNode);
                String value = this.getTrimmedToNullString(this.xpath, "q:value", entryNode);
                jobDetail.getJobDataMap().put(key, value);
            }
            if (this.log.isDebugEnabled()) {
                this.log.debug("Parsed job definition: " + jobDetail);
            }
            this.addJobToSchedule(jobDetail);
        }
        NodeList triggerEntries = (NodeList)this.xpath.evaluate("/q:job-scheduling-data/q:schedule/q:trigger/*", document, XPathConstants.NODESET);
        this.log.debug("Found " + triggerEntries.getLength() + " trigger definitions.");
        for (int j = 0; j < triggerEntries.getLength(); ++j) {
            ScheduleBuilder sched;
            Node triggerNode = triggerEntries.item(j);
            String triggerName = this.getTrimmedToNullString(this.xpath, "q:name", triggerNode);
            String triggerGroup = this.getTrimmedToNullString(this.xpath, "q:group", triggerNode);
            String triggerDescription = this.getTrimmedToNullString(this.xpath, "q:description", triggerNode);
            String triggerMisfireInstructionConst = this.getTrimmedToNullString(this.xpath, "q:misfire-instruction", triggerNode);
            String triggerPriorityString = this.getTrimmedToNullString(this.xpath, "q:priority", triggerNode);
            String triggerCalendarRef = this.getTrimmedToNullString(this.xpath, "q:calendar-name", triggerNode);
            String triggerJobName = this.getTrimmedToNullString(this.xpath, "q:job-name", triggerNode);
            String triggerJobGroup = this.getTrimmedToNullString(this.xpath, "q:job-group", triggerNode);
            int triggerPriority = 5;
            if (triggerPriorityString != null) {
                triggerPriority = Integer.valueOf(triggerPriorityString);
            }
            String startTimeString = this.getTrimmedToNullString(this.xpath, "q:start-time", triggerNode);
            String startTimeFutureSecsString = this.getTrimmedToNullString(this.xpath, "q:start-time-seconds-in-future", triggerNode);
            String endTimeString = this.getTrimmedToNullString(this.xpath, "q:end-time", triggerNode);
            Date triggerStartTime = startTimeFutureSecsString != null ? new Date(System.currentTimeMillis() + Long.valueOf(startTimeFutureSecsString) * 1000L) : (startTimeString == null || startTimeString.length() == 0 ? new Date() : DatatypeConverter.parseDateTime((String)startTimeString).getTime());
            Date triggerEndTime = endTimeString == null || endTimeString.length() == 0 ? null : DatatypeConverter.parseDateTime((String)endTimeString).getTime();
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroup);
            if (triggerNode.getNodeName().equals("simple")) {
                String repeatCountString = this.getTrimmedToNullString(this.xpath, "q:repeat-count", triggerNode);
                String repeatIntervalString = this.getTrimmedToNullString(this.xpath, "q:repeat-interval", triggerNode);
                int repeatCount = repeatCountString == null ? 0 : Integer.parseInt(repeatCountString);
                long repeatInterval = repeatIntervalString == null ? 0L : Long.parseLong(repeatIntervalString);
                sched = SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(repeatInterval).withRepeatCount(repeatCount);
                if (triggerMisfireInstructionConst != null && triggerMisfireInstructionConst.length() != 0) {
                    if (triggerMisfireInstructionConst.equals("MISFIRE_INSTRUCTION_FIRE_NOW")) {
                        ((SimpleScheduleBuilder)sched).withMisfireHandlingInstructionFireNow();
                    } else if (triggerMisfireInstructionConst.equals("MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT")) {
                        ((SimpleScheduleBuilder)sched).withMisfireHandlingInstructionNextWithExistingCount();
                    } else if (triggerMisfireInstructionConst.equals("MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT")) {
                        ((SimpleScheduleBuilder)sched).withMisfireHandlingInstructionNextWithRemainingCount();
                    } else if (triggerMisfireInstructionConst.equals("MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT")) {
                        ((SimpleScheduleBuilder)sched).withMisfireHandlingInstructionNowWithExistingCount();
                    } else if (triggerMisfireInstructionConst.equals("MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_REMAINING_REPEAT_COUNT")) {
                        ((SimpleScheduleBuilder)sched).withMisfireHandlingInstructionNowWithRemainingCount();
                    } else if (!triggerMisfireInstructionConst.equals("MISFIRE_INSTRUCTION_SMART_POLICY")) {
                        throw new ParseException("Unexpected/Unhandlable Misfire Instruction encountered '" + triggerMisfireInstructionConst + "', for trigger: " + triggerKey, -1);
                    }
                }
            } else if (triggerNode.getNodeName().equals("cron")) {
                String cronExpression = this.getTrimmedToNullString(this.xpath, "q:cron-expression", triggerNode);
                String timezoneString = this.getTrimmedToNullString(this.xpath, "q:time-zone", triggerNode);
                TimeZone tz = timezoneString == null ? null : TimeZone.getTimeZone(timezoneString);
                sched = CronScheduleBuilder.cronSchedule(cronExpression).inTimeZone(tz);
                if (triggerMisfireInstructionConst != null && triggerMisfireInstructionConst.length() != 0) {
                    if (triggerMisfireInstructionConst.equals("MISFIRE_INSTRUCTION_DO_NOTHING")) {
                        ((CronScheduleBuilder)sched).withMisfireHandlingInstructionDoNothing();
                    } else if (triggerMisfireInstructionConst.equals("MISFIRE_INSTRUCTION_FIRE_ONCE_NOW")) {
                        ((CronScheduleBuilder)sched).withMisfireHandlingInstructionFireAndProceed();
                    } else if (!triggerMisfireInstructionConst.equals("MISFIRE_INSTRUCTION_SMART_POLICY")) {
                        throw new ParseException("Unexpected/Unhandlable Misfire Instruction encountered '" + triggerMisfireInstructionConst + "', for trigger: " + triggerKey, -1);
                    }
                }
            } else if (triggerNode.getNodeName().equals("calendar-interval")) {
                String repeatIntervalString = this.getTrimmedToNullString(this.xpath, "q:repeat-interval", triggerNode);
                String repeatUnitString = this.getTrimmedToNullString(this.xpath, "q:repeat-interval-unit", triggerNode);
                int repeatInterval = Integer.parseInt(repeatIntervalString);
                DateBuilder.IntervalUnit repeatUnit = DateBuilder.IntervalUnit.valueOf(repeatUnitString);
                sched = CalendarIntervalScheduleBuilder.calendarIntervalSchedule().withInterval(repeatInterval, repeatUnit);
                if (triggerMisfireInstructionConst != null && triggerMisfireInstructionConst.length() != 0) {
                    if (triggerMisfireInstructionConst.equals("MISFIRE_INSTRUCTION_DO_NOTHING")) {
                        ((CalendarIntervalScheduleBuilder)sched).withMisfireHandlingInstructionDoNothing();
                    } else if (triggerMisfireInstructionConst.equals("MISFIRE_INSTRUCTION_FIRE_ONCE_NOW")) {
                        ((CalendarIntervalScheduleBuilder)sched).withMisfireHandlingInstructionFireAndProceed();
                    } else if (!triggerMisfireInstructionConst.equals("MISFIRE_INSTRUCTION_SMART_POLICY")) {
                        throw new ParseException("Unexpected/Unhandlable Misfire Instruction encountered '" + triggerMisfireInstructionConst + "', for trigger: " + triggerKey, -1);
                    }
                }
            } else {
                throw new ParseException("Unknown trigger type: " + triggerNode.getNodeName(), -1);
            }
            MutableTrigger trigger = (MutableTrigger)TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroup).withDescription(triggerDescription).forJob(triggerJobName, triggerJobGroup).startAt(triggerStartTime).endAt(triggerEndTime).withPriority(triggerPriority).modifiedByCalendar(triggerCalendarRef).withSchedule(sched).build();
            NodeList jobDataEntries = (NodeList)this.xpath.evaluate("q:job-data-map/q:entry", triggerNode, XPathConstants.NODESET);
            for (int k = 0; k < jobDataEntries.getLength(); ++k) {
                Node entryNode = jobDataEntries.item(k);
                String key = this.getTrimmedToNullString(this.xpath, "q:key", entryNode);
                String value = this.getTrimmedToNullString(this.xpath, "q:value", entryNode);
                trigger.getJobDataMap().put(key, value);
            }
            if (this.log.isDebugEnabled()) {
                this.log.debug("Parsed trigger definition: " + trigger);
            }
            this.addTriggerToSchedule(trigger);
        }
    }

    protected String getTrimmedToNullString(XPath xpathToElement, String elementName, Node parentNode) throws XPathExpressionException {
        String str = (String)xpathToElement.evaluate(elementName, parentNode, XPathConstants.STRING);
        if (str != null) {
            str = str.trim();
        }
        if (str != null && str.length() == 0) {
            str = null;
        }
        return str;
    }

    protected Boolean getBoolean(XPath xpathToElement, String elementName, Document document) throws XPathExpressionException {
        Node directive = (Node)xpathToElement.evaluate(elementName, document, XPathConstants.NODE);
        if (directive == null || directive.getTextContent() == null) {
            return null;
        }
        String val = directive.getTextContent();
        if (val.equalsIgnoreCase("true") || val.equalsIgnoreCase("yes") || val.equalsIgnoreCase("y")) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public void processFileAndScheduleJobs(Scheduler sched, boolean overWriteExistingJobs) throws Exception {
        String fileName = QUARTZ_XML_DEFAULT_FILE_NAME;
        this.processFile(fileName, this.getSystemIdForFileName(fileName));
        this.setOverWriteExistingData(overWriteExistingJobs);
        this.executePreProcessCommands(sched);
        this.scheduleJobs(sched);
    }

    public void processFileAndScheduleJobs(String fileName, Scheduler sched) throws Exception {
        this.processFileAndScheduleJobs(fileName, this.getSystemIdForFileName(fileName), sched);
    }

    public void processFileAndScheduleJobs(String fileName, String systemId, Scheduler sched) throws Exception {
        this.processFile(fileName, systemId);
        this.executePreProcessCommands(sched);
        this.scheduleJobs(sched);
    }

    protected List<JobDetail> getLoadedJobs() {
        return Collections.unmodifiableList(this.loadedJobs);
    }

    protected List<MutableTrigger> getLoadedTriggers() {
        return Collections.unmodifiableList(this.loadedTriggers);
    }

    protected InputStream getInputStream(String fileName) {
        return this.classLoadHelper.getResourceAsStream(fileName);
    }

    protected void addJobToSchedule(JobDetail job) {
        this.loadedJobs.add(job);
    }

    protected void addTriggerToSchedule(MutableTrigger trigger) {
        this.loadedTriggers.add(trigger);
    }

    private Map<JobKey, List<MutableTrigger>> buildTriggersByFQJobNameMap(List<MutableTrigger> triggers) {
        HashMap<JobKey, List<MutableTrigger>> triggersByFQJobName = new HashMap<JobKey, List<MutableTrigger>>();
        for (MutableTrigger trigger : triggers) {
            LinkedList<MutableTrigger> triggersOfJob = (LinkedList<MutableTrigger>)triggersByFQJobName.get(trigger.getJobKey());
            if (triggersOfJob == null) {
                triggersOfJob = new LinkedList<MutableTrigger>();
                triggersByFQJobName.put(trigger.getJobKey(), triggersOfJob);
            }
            triggersOfJob.add(trigger);
        }
        return triggersByFQJobName;
    }

    protected void executePreProcessCommands(Scheduler scheduler) throws SchedulerException {
        for (String string : this.jobGroupsToDelete) {
            if (string.equals("*")) {
                this.log.info("Deleting all jobs in ALL groups.");
                for (String string2 : scheduler.getJobGroupNames()) {
                    if (this.jobGroupsToNeverDelete.contains(string2)) continue;
                    for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(string2))) {
                        scheduler.deleteJob(jobKey);
                    }
                }
                continue;
            }
            if (this.jobGroupsToNeverDelete.contains(string)) continue;
            this.log.info("Deleting all jobs in group: {}", (Object)string);
            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(string))) {
                scheduler.deleteJob(jobKey);
            }
        }
        for (String string : this.triggerGroupsToDelete) {
            if (string.equals("*")) {
                this.log.info("Deleting all triggers in ALL groups.");
                for (String string3 : scheduler.getTriggerGroupNames()) {
                    if (this.triggerGroupsToNeverDelete.contains(string3)) continue;
                    for (TriggerKey triggerKey : scheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(string3))) {
                        scheduler.unscheduleJob(triggerKey);
                    }
                }
                continue;
            }
            if (this.triggerGroupsToNeverDelete.contains(string)) continue;
            this.log.info("Deleting all triggers in group: {}", (Object)string);
            for (TriggerKey triggerKey : scheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(string))) {
                scheduler.unscheduleJob(triggerKey);
            }
        }
        for (JobKey jobKey : this.jobsToDelete) {
            if (this.jobGroupsToNeverDelete.contains(jobKey.getGroup())) continue;
            this.log.info("Deleting job: {}", (Object)jobKey);
            scheduler.deleteJob(jobKey);
        }
        for (TriggerKey triggerKey : this.triggersToDelete) {
            if (this.triggerGroupsToNeverDelete.contains(triggerKey.getGroup())) continue;
            this.log.info("Deleting trigger: {}", (Object)triggerKey);
            scheduler.unscheduleJob(triggerKey);
        }
    }

    protected void scheduleJobs(Scheduler sched) throws SchedulerException {
        LinkedList<JobDetail> jobs = new LinkedList<JobDetail>(this.getLoadedJobs());
        LinkedList<MutableTrigger> triggers = new LinkedList<MutableTrigger>(this.getLoadedTriggers());
        this.log.info("Adding " + jobs.size() + " jobs, " + triggers.size() + " triggers.");
        Map<JobKey, List<MutableTrigger>> triggersByFQJobName = this.buildTriggersByFQJobNameMap(triggers);
        Iterator itr = jobs.iterator();
        while (itr.hasNext()) {
            JobDetail detail = (JobDetail)itr.next();
            itr.remove();
            JobDetail dupeJ = null;
            try {
                dupeJ = sched.getJobDetail(detail.getKey());
            }
            catch (JobPersistenceException e) {
                if (e.getCause() instanceof ClassNotFoundException && this.isOverWriteExistingData()) {
                    this.log.info("Removing job: " + detail.getKey());
                    sched.deleteJob(detail.getKey());
                }
                throw e;
            }
            if (dupeJ != null) {
                if (!this.isOverWriteExistingData() && this.isIgnoreDuplicates()) {
                    this.log.info("Not overwriting existing job: " + dupeJ.getKey());
                    continue;
                }
                if (!this.isOverWriteExistingData() && !this.isIgnoreDuplicates()) {
                    throw new ObjectAlreadyExistsException(detail);
                }
            }
            if (dupeJ != null) {
                this.log.info("Replacing job: " + detail.getKey());
            } else {
                this.log.info("Adding job: " + detail.getKey());
            }
            List<MutableTrigger> triggersOfJob = triggersByFQJobName.get(detail.getKey());
            if (!(detail.isDurable() || triggersOfJob != null && triggersOfJob.size() != 0)) {
                if (dupeJ == null) {
                    throw new SchedulerException("A new job defined without any triggers must be durable: " + detail.getKey());
                }
                if (dupeJ.isDurable() && sched.getTriggersOfJob(detail.getKey()).size() == 0) {
                    throw new SchedulerException("Can't change existing durable job without triggers to non-durable: " + detail.getKey());
                }
            }
            if (dupeJ != null || detail.isDurable()) {
                if (triggersOfJob != null && triggersOfJob.size() > 0) {
                    sched.addJob(detail, true, true);
                    continue;
                }
                sched.addJob(detail, true, false);
                continue;
            }
            boolean addJobWithFirstSchedule = true;
            for (MutableTrigger trigger : triggersOfJob) {
                Trigger dupeT;
                triggers.remove(trigger);
                if (trigger.getStartTime() == null) {
                    trigger.setStartTime(new Date());
                }
                if ((dupeT = sched.getTrigger(trigger.getKey())) != null) {
                    if (this.isOverWriteExistingData()) {
                        if (this.log.isDebugEnabled()) {
                            this.log.debug("Rescheduling job: " + trigger.getJobKey() + " with updated trigger: " + trigger.getKey());
                        }
                    } else {
                        if (this.isIgnoreDuplicates()) {
                            this.log.info("Not overwriting existing trigger: " + dupeT.getKey());
                            continue;
                        }
                        throw new ObjectAlreadyExistsException(trigger);
                    }
                    if (!dupeT.getJobKey().equals(trigger.getJobKey())) {
                        this.log.warn("Possibly duplicately named ({}) triggers in jobs xml file! ", (Object)trigger.getKey());
                    }
                    sched.rescheduleJob(trigger.getKey(), trigger);
                    continue;
                }
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Scheduling job: " + trigger.getJobKey() + " with trigger: " + trigger.getKey());
                }
                try {
                    if (addJobWithFirstSchedule) {
                        sched.scheduleJob(detail, trigger);
                        addJobWithFirstSchedule = false;
                        continue;
                    }
                    sched.scheduleJob(trigger);
                }
                catch (ObjectAlreadyExistsException e) {
                    if (this.log.isDebugEnabled()) {
                        this.log.debug("Adding trigger: " + trigger.getKey() + " for job: " + detail.getKey() + " failed because the trigger already existed.  " + "This is likely due to a race condition between multiple instances " + "in the cluster.  Will try to reschedule instead.");
                    }
                    sched.rescheduleJob(trigger.getKey(), trigger);
                }
            }
        }
        for (MutableTrigger trigger : triggers) {
            Trigger dupeT;
            if (trigger.getStartTime() == null) {
                trigger.setStartTime(new Date());
            }
            if ((dupeT = sched.getTrigger(trigger.getKey())) != null) {
                if (this.isOverWriteExistingData()) {
                    if (this.log.isDebugEnabled()) {
                        this.log.debug("Rescheduling job: " + trigger.getJobKey() + " with updated trigger: " + trigger.getKey());
                    }
                } else {
                    if (this.isIgnoreDuplicates()) {
                        this.log.info("Not overwriting existing trigger: " + dupeT.getKey());
                        continue;
                    }
                    throw new ObjectAlreadyExistsException(trigger);
                }
                if (!dupeT.getJobKey().equals(trigger.getJobKey())) {
                    this.log.warn("Possibly duplicately named ({}) triggers in jobs xml file! ", (Object)trigger.getKey());
                }
                sched.rescheduleJob(trigger.getKey(), trigger);
                continue;
            }
            if (this.log.isDebugEnabled()) {
                this.log.debug("Scheduling job: " + trigger.getJobKey() + " with trigger: " + trigger.getKey());
            }
            try {
                sched.scheduleJob(trigger);
            }
            catch (ObjectAlreadyExistsException e) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Adding trigger: " + trigger.getKey() + " for job: " + trigger.getJobKey() + " failed because the trigger already existed.  " + "This is likely due to a race condition between multiple instances " + "in the cluster.  Will try to reschedule instead.");
                }
                sched.rescheduleJob(trigger.getKey(), trigger);
            }
        }
    }

    @Override
    public void warning(SAXParseException e) throws SAXException {
        this.addValidationException(e);
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        this.addValidationException(e);
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        this.addValidationException(e);
    }

    protected void addValidationException(SAXException e) {
        this.validationExceptions.add(e);
    }

    protected void clearValidationExceptions() {
        this.validationExceptions.clear();
    }

    protected void maybeThrowValidationException() throws ValidationException {
        if (this.validationExceptions.size() > 0) {
            throw new ValidationException("Encountered " + this.validationExceptions.size() + " validation exceptions.", this.validationExceptions);
        }
    }
}

