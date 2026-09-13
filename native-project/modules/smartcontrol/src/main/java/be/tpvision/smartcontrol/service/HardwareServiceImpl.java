package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Hardware;
import be.tpvision.smartcontrol.messages.services.hardware.AddHardwareMessages;
import be.tpvision.smartcontrol.messages.services.hardware.ConstructorMessages;
import be.tpvision.smartcontrol.messages.services.hardware.DeleteHardwareMessages;
import be.tpvision.smartcontrol.messages.services.hardware.GetHardwareMessages;
import be.tpvision.smartcontrol.messages.services.hardware.UpdateHardwareMessages;
import be.tpvision.smartcontrol.repository.HardwareRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class HardwareServiceImpl implements HardwareService {
   private final HardwareRepository hardwareRepository;

   @Autowired
   public HardwareServiceImpl(final HardwareRepository hardwareRepository) {
      Assert.notNull(hardwareRepository, ConstructorMessages.HARDWARE_REPOSITORY_CAN_NOT_BE_NULL);
      this.hardwareRepository = hardwareRepository;
   }

   @Override
   public Set<Hardware> getHardware() {
      List<Hardware> hardwareList = this.hardwareRepository.getAll();
      return new HashSet<>(hardwareList);
   }

   @Override
   public Hardware getHardware(final String hardwareKey) {
      Assert.notNull(hardwareKey, GetHardwareMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
      return this.hardwareRepository.getById(hardwareKey);
   }

   @Transactional
   @Override
   public void addHardware(final Hardware hardware) {
      Assert.notNull(hardware, AddHardwareMessages.HARDWARE_CAN_NOT_BE_NULL);
      this.hardwareRepository.persist(hardware);
   }

   @Transactional
   @Override
   public void updateHardware(final Hardware hardware) {
      Assert.notNull(hardware, UpdateHardwareMessages.HARDWARE_CAN_NOT_BE_NULL);
      this.hardwareRepository.merge(hardware);
   }

   @Transactional
   @Override
   public void deleteHardware(final Hardware hardware) {
      Assert.notNull(hardware, DeleteHardwareMessages.HARDWARE_CAN_NOT_BE_NULL);
      this.hardwareRepository.delete(hardware);
   }

   @Transactional
   @Override
   public void deleteHardware(final String hardwareKey) {
      Assert.notNull(hardwareKey, DeleteHardwareMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
      this.hardwareRepository.deleteById(hardwareKey);
   }
}
