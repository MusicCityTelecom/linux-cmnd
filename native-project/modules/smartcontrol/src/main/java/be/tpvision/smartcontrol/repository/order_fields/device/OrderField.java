package be.tpvision.smartcontrol.repository.order_fields.device;

import be.tpvision.smartcontrol.domain.Device;

public enum OrderField implements be.tpvision.smartcontrol.repository.order_fields.OrderField<Device> {
   ID,
   NAME,
   MODEL_NUMBER,
   IP_DESTINATION,
   TEMPERATURE,
   POWER_STATE,
   INPUT_SOURCE;
}
