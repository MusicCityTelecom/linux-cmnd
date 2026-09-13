package com.tpvision.smartinstall.schedule;

import com.tpvision.smartinstall.util.TpvTimerTask;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CmndMetricsLogCleanTask extends TpvTimerTask {
   private static final Logger LOG = LoggerFactory.getLogger(CmndMetricsLogCleanTask.class);
   private static final int PRESERVE_LOG_DAY_COUNT = 10;
   private static final Pattern METRICS_LOG_DATE_PATTERN = Pattern.compile("^metrics\\.([a-z]+)\\.(.*)\\.log$");
   private static final String CMS_METRICS_LOG_PATH = "C:\\Apache24\\htdocs\\SmartCMS\\sites\\default\\Metrics";

   @Override
   public void tryRun() {
      LOG.info("CmndMetricsLogCleanTask start..");
      Calendar cal = Calendar.getInstance();
      cal.add(5, -10);
      String maxPreserveDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
      LOG.info("remove all log file before <{}>", maxPreserveDate);
      List<File> checkFiles = new ArrayList<>();
      File metricsFolder = new File(CmndMetricsTask.METRICS_LOG_PATH);
      File[] metricsFiles = metricsFolder.listFiles();
      if (metricsFiles != null) {
         checkFiles.addAll(Arrays.asList(metricsFiles));
      }

      File cmsFolder = new File("C:\\Apache24\\htdocs\\SmartCMS\\sites\\default\\Metrics");
      File[] cmsFiles = cmsFolder.listFiles();
      if (cmsFiles != null) {
         checkFiles.addAll(Arrays.asList(cmsFiles));
      }

      LOG.info("need check metrics file count => <{}>", checkFiles.size());

      for (File file : checkFiles) {
         Matcher logDateMatcher = METRICS_LOG_DATE_PATTERN.matcher(file.getName());
         String fileDate = null;
         if (logDateMatcher.find()) {
            fileDate = logDateMatcher.group(2);
            if (maxPreserveDate.compareTo(fileDate) > 0) {
               LOG.info("remove expire log file <{}>", file.getAbsolutePath());
               FileUtils.deleteQuietly(file);
            }
         } else {
            LOG.warn("invalid metrics log file name:{}", file.getName());
         }
      }

      LOG.info("CmndMetricsLogCleanTask end");
   }
}
