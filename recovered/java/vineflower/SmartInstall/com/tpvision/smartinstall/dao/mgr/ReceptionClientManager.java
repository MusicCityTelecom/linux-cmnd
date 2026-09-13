package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.dao.ReceptionClientRepository;
import com.tpvision.smartinstall.dao.core.ReceptionClient;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceptionClientManager {
   private static final Logger LOG = LoggerFactory.getLogger(ReceptionClientManager.class);
   @Autowired
   private ReceptionClientRepository receptionClientRepository;

   public ReceptionClient save(ReceptionClient bean) {
      this.receptionClientRepository.save(bean);
      return bean;
   }

   public ReceptionClient findByClientId(String clientId) {
      return this.receptionClientRepository.findByClientId(clientId);
   }

   public ReceptionClient findById(int id) {
      return this.receptionClientRepository.findById(id).orElse(null);
   }

   public List<ReceptionClient> findReceptionClientListByStatus(int status) {
      return this.receptionClientRepository.findByStatus(status);
   }
}
