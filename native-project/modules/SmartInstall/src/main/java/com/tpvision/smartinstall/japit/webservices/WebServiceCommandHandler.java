package com.tpvision.smartinstall.japit.webservices;

import com.tpvision.smartinstall.dao.core.Devices;
import org.json.JSONObject;

public abstract class WebServiceCommandHandler {
   protected JSONObject commandDetails;
   protected String tvUniqueId;
   protected String clientIp;
   protected String cmdType;
   protected String requestSchema;
   protected Devices device;

   public String getHandleFunction() {
      return this.getClass().getSimpleName().replace("Handler", "");
   }

   public abstract String execute();

   public JSONObject getCommandDetails() {
      return this.commandDetails;
   }

   public void setCommandDetails(JSONObject commandDetails) {
      this.commandDetails = commandDetails;
   }

   public String getTvUniqueId() {
      return this.tvUniqueId;
   }

   public void setTvUniqueId(String tvUniqueId) {
      this.tvUniqueId = tvUniqueId;
   }

   public String getClientIp() {
      return this.clientIp;
   }

   public void setClientIp(String clientIp) {
      this.clientIp = clientIp;
   }

   public String getCmdType() {
      return this.cmdType;
   }

   public void setCmdType(String cmdType) {
      this.cmdType = cmdType;
   }

   public String getRequestSchema() {
      return this.requestSchema;
   }

   public void setRequestSchema(String requestSchema) {
      this.requestSchema = requestSchema;
   }

   public Devices getDevice() {
      return this.device;
   }

   public void setDevice(Devices device) {
      this.device = device;
   }
}
