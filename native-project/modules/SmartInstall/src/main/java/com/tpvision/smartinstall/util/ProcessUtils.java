package com.tpvision.smartinstall.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.exec.environment.EnvironmentUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ProcessUtils {
   private static final Logger LOG = LoggerFactory.getLogger(ProcessUtils.class);
   private static final String TASKLIST = "tasklist";
   private static final String KILL = "taskkill /f /im ";

   private ProcessUtils() {
   }

   public static boolean execCommondWithReturn(String workspace, String command, ProcessUtils.ProcessOutput processOutput) {
      LOG.info("Running command:{} ,workspace={}", command, workspace);
      if (processOutput != null) {
         String tmpfilePath = FileUtils.getTempDirectoryPath();
         String fullBatFileName = tmpfilePath + UUID.randomUUID().toString() + ".bat";

         try {
            FileUtils.writeStringToFile(new File(fullBatFileName), "@echo off && chcp 65001 && " + command, StandardCharsets.UTF_8);
            command = fullBatFileName;
         } catch (IOException e) {
            LOG.error(e.getMessage(), e);
         }
      }

      CommandLine cmdLine = CommandLine.parse(command);
      DefaultExecutor executor = new DefaultExecutor();
      int[] values = new int[]{0, 1};
      executor.setExitValues(values);
      File workdir = new File(workspace);
      executor.setWorkingDirectory(workdir);
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
      executor.setStreamHandler(streamHandler);

      try {
         Map<String, String> mapEnv = EnvironmentUtils.getProcEnvironment();
         int exitValue = executor.execute(cmdLine, mapEnv);
         if (processOutput != null) {
            processOutput.setValue(new String(outputStream.toByteArray(), StandardCharsets.UTF_8));
            LOG.info("output={}", outputStream);
         }

         LOG.info("result:res={}", exitValue);
         if (exitValue == 0) {
            return true;
         }
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
      }

      return false;
   }

   public static boolean execCommond(String workspace, String command) {
      return execCommondWithReturn(workspace, command, null);
   }

   public static boolean startProcess(String line, String workspace) {
      LOG.info("start process:{}", line);
      Thread thread = new Thread(new ProcessUtils.ProccessStartRunnable(line, workspace));
      thread.setDaemon(true);
      thread.start();
      return true;
   }

   public static boolean isProcessRunning(String serviceName) {
      Process p = null;

      try {
         if (Utils.getOSName().equalsIgnoreCase("windows")) {
            p = Runtime.getRuntime().exec("tasklist");
         } else if (Utils.getOSName().equalsIgnoreCase("linux")) {
            return true;
         }
      } catch (IOException e1) {
         LOG.error(e1.getMessage(), e1);
         return false;
      }

      if (null != p) {
         String line;
         try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            while ((line = reader.readLine()) != null) {
               LOG.debug(line);
               if (line.toLowerCase().indexOf(serviceName.toLowerCase()) > -1) {
                  return true;
               }
            }
         } catch (Exception e) {
            LOG.error(e.getMessage(), e);
         }
      }

      return false;
   }

   public static boolean startService(String serviceName) {
      LOG.info("start service {}", serviceName);
      String startCommand = "net start ";
      if (Utils.getOSName().equalsIgnoreCase("linux")) {
         return true;
      }

      if (Utils.getOSName().equalsIgnoreCase("windows")) {
         startCommand = startCommand + serviceName;
      }

      try {
         Runtime.getRuntime().exec(startCommand);
         return true;
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
         return false;
      }
   }

   public static boolean stopService(String serviceName) {
      LOG.info("stop service {}", serviceName);
      String stopCommand = "net stop ";
      if (Utils.getOSName().equalsIgnoreCase("linux")) {
         return true;
      }

      if (Utils.getOSName().equalsIgnoreCase("windows")) {
         stopCommand = stopCommand + serviceName;
      }

      try {
         Runtime.getRuntime().exec(stopCommand);
         return true;
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
         return false;
      }
   }

   public static boolean killProcess(String processName) {
      LOG.info("kill process {}", processName);
      String killCommand = "";
      if (Utils.getOSName().equalsIgnoreCase("linux")) {
         return true;
      }

      if (Utils.getOSName().equalsIgnoreCase("windows")) {
         killCommand = "taskkill /f /im " + processName;
      }

      try {
         Runtime.getRuntime().exec(killCommand);
         return true;
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
         return false;
      }
   }

   private static class ProccessStartRunnable extends TpvRunableTask {
      private String line;
      private String workspace;

      public ProccessStartRunnable(String line, String workspace) {
         this.line = line;
         this.workspace = workspace;
      }

      @Override
      public void execute() {
         ProcessUtils.execCommond(this.workspace, this.line);
      }
   }

   public static class ProcessOutput {
      String value;

      public String getValue() {
         return this.value;
      }

      public void setValue(String value) {
         this.value = value;
      }
   }
}
