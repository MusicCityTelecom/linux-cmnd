package be.tpvision.smartcontrol.service;

import org.json.JSONObject;

public interface MetricsService {
   void writeMetricsLog(JSONObject metricDetails);
}
