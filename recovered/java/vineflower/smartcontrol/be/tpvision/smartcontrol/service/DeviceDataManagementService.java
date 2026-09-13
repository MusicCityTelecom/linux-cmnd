package be.tpvision.smartcontrol.service;

public interface DeviceDataManagementService {
   void fetchInfoData();

   void fetchOverviewData();

   boolean isFetchInfoDataRunning();

   boolean isFetchOverviewDataRunning();
}
