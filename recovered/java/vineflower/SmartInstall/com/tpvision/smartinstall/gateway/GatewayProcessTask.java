package com.tpvision.smartinstall.gateway;

import com.tpvision.smartinstall.util.ProcessUtils;
import com.tpvision.smartinstall.util.TpvTimerTask;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class GatewayProcessTask extends TpvTimerTask {
   private static final Logger LOG = LoggerFactory.getLogger(GatewayProcessTask.class);
   private final GatewayManager gatewayManager = GatewayManager.getInstance();

   @Override
   public void tryRun() {
      boolean isOldGateRunning = ProcessUtils.isProcessRunning("Gateway.exe");
      this.gatewayManager.setOldGatewayRunning(isOldGateRunning);
      if (isOldGateRunning) {
         this.gatewayManager.updateGatewayPlaystatus();
      }

      boolean isMGateRunning = this.gatewayManager.checkMGateRunning();
      if (isMGateRunning) {
         if (this.gatewayManager.isUsingOldGateway() && this.gatewayManager.isUsingLocalMGate()) {
            LOG.info("using oldgateway, stop mgate");
            this.gatewayManager.teardownMGate();
            isMGateRunning = false;
         }

         try {
            this.gatewayManager.requestStatus();
            this.gatewayManager.setMGateRunning(isMGateRunning);
         } catch (IOException e) {
            LOG.warn("request status failed");
            this.gatewayManager.setMGateRunning(false);
         }
      } else {
         this.gatewayManager.setMGateRunning(false);
         if (this.gatewayManager.isUsingLocalMGate() && !this.gatewayManager.isUsingOldGateway()) {
            LOG.info("using mgate, start mgate");
            this.gatewayManager.startupMGate();
         }
      }

      if (!isOldGateRunning && !isMGateRunning) {
         this.gatewayManager.resetStatus();
      }
   }
}
