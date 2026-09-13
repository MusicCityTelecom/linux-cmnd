package be.tpvision.smartcontrol.domain.comparators.device;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.comparators.EnumComparator;
import be.tpvision.smartcontrol.domain.comparators.InetAddressComparator;
import be.tpvision.smartcontrol.domain.comparators.InetSocketAddressComparator;
import be.tpvision.smartcontrol.domain.comparators.InputSourceComparator;
import be.tpvision.smartcontrol.domain.comparators.IpDestinationComparator;
import be.tpvision.smartcontrol.domain.comparators.StringWrapperComparator;
import be.tpvision.smartcontrol.domain.comparators.TemperatureSensorComparator;
import be.tpvision.smartcontrol.domain.device_settings.StringWrapper;
import be.tpvision.smartcontrol.domain.device_settings.general.PowerState;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.InputSource;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.TemperatureSensor;
import be.tpvision.smartcontrol.io.ip.IpDestination;
import be.tpvision.smartcontrol.messages.domain.comparators.device.device_comparator_factory_impl.GetComparatorMessages;
import be.tpvision.smartcontrol.repository.OrderDirection;
import be.tpvision.smartcontrol.repository.order_fields.OrderField;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class DeviceComparatorFactoryImpl implements DeviceComparatorFactory {
   private static final Map<OrderField<? super Device>, Map<OrderDirection, Comparator<? super Device>>> orderMap = new HashMap<>();

   private static Map<OrderDirection, Comparator<? super Device>> getDeviceIdComparators() {
      Map<OrderDirection, Comparator<? super Device>> deviceIdComparators = new EnumMap<>(OrderDirection.class);
      Comparator<Long> longAscendingComparator = Comparator.naturalOrder();
      Comparator<Long> longAscendingNullComparator = Comparator.nullsLast(longAscendingComparator);
      Comparator<Device> deviceIdAscendingComparator = new DeviceIdComparator(longAscendingNullComparator);
      Comparator<Device> deviceIdAscendingNullComparator = Comparator.nullsLast(deviceIdAscendingComparator);
      deviceIdComparators.put(OrderDirection.ASC, deviceIdAscendingNullComparator);
      Comparator<Long> longDescendingComparator = Comparator.reverseOrder();
      Comparator<Long> longDescendingNullComparator = Comparator.nullsLast(longDescendingComparator);
      Comparator<Device> deviceIdDescendingComparator = new DeviceIdComparator(longDescendingNullComparator);
      Comparator<Device> deviceIdDescendingNullComparator = Comparator.nullsLast(deviceIdDescendingComparator);
      deviceIdComparators.put(OrderDirection.DESC, deviceIdDescendingNullComparator);
      return deviceIdComparators;
   }

   private static Comparator<String> getStringAscendingNullComparator() {
      Comparator<String> stringAscendingComparator = Comparator.naturalOrder();
      return Comparator.nullsLast(stringAscendingComparator);
   }

   private static Comparator<String> getStringDescendingNullComparator() {
      Comparator<String> stringDescendingComparator = Comparator.reverseOrder();
      return Comparator.nullsLast(stringDescendingComparator);
   }

   private static Map<OrderDirection, Comparator<? super Device>> getDeviceNameComparators() {
      Map<OrderDirection, Comparator<? super Device>> deviceNameComparators = new EnumMap<>(OrderDirection.class);
      Comparator<String> stringAscendingNullComparator = getStringAscendingNullComparator();
      Comparator<Device> deviceNameAscendingComparator = new DeviceNameComparator(stringAscendingNullComparator);
      Comparator<Device> deviceNameAscendingNullComparator = Comparator.nullsLast(deviceNameAscendingComparator);
      deviceNameComparators.put(OrderDirection.ASC, deviceNameAscendingNullComparator);
      Comparator<String> stringDescendingNullComparator = getStringDescendingNullComparator();
      Comparator<Device> deviceNameDescendingComparator = new DeviceNameComparator(stringDescendingNullComparator);
      Comparator<Device> deviceNameDescendingNullComparator = Comparator.nullsLast(deviceNameDescendingComparator);
      deviceNameComparators.put(OrderDirection.DESC, deviceNameDescendingNullComparator);
      return deviceNameComparators;
   }

   private static Map<OrderDirection, Comparator<? super Device>> getDeviceModelNumberComparators() {
      Map<OrderDirection, Comparator<? super Device>> deviceModelNumberComparators = new EnumMap<>(OrderDirection.class);
      Comparator<String> stringAscendingNullComparator = getStringAscendingNullComparator();
      Comparator<StringWrapper> stringWrapperAscendingComparator = new StringWrapperComparator(stringAscendingNullComparator);
      Comparator<StringWrapper> stringWrapperAscendingNullComparator = Comparator.nullsLast(stringWrapperAscendingComparator);
      Comparator<Device> deviceModelNumberAscendingComparator = new DeviceModelNumberComparator(stringWrapperAscendingNullComparator);
      Comparator<Device> deviceModelNumberAscendingNullComparator = Comparator.nullsLast(deviceModelNumberAscendingComparator);
      deviceModelNumberComparators.put(OrderDirection.ASC, deviceModelNumberAscendingNullComparator);
      Comparator<String> stringDescendingNullComparator = getStringDescendingNullComparator();
      Comparator<StringWrapper> stringWrapperDescendingComparator = new StringWrapperComparator(stringDescendingNullComparator);
      Comparator<StringWrapper> stringWrapperDescendingNullComparator = Comparator.nullsLast(stringWrapperDescendingComparator);
      Comparator<Device> deviceModelNumberDescendingComparator = new DeviceModelNumberComparator(stringWrapperDescendingNullComparator);
      Comparator<Device> deviceModelNumberDescendingNullComparator = Comparator.nullsLast(deviceModelNumberDescendingComparator);
      deviceModelNumberComparators.put(OrderDirection.DESC, deviceModelNumberDescendingNullComparator);
      return deviceModelNumberComparators;
   }

   private static Map<OrderDirection, Comparator<? super Device>> getDeviceAddressComparators() {
      Map<OrderDirection, Comparator<? super Device>> deviceAddressComparators = new EnumMap<>(OrderDirection.class);
      Comparator<String> stringAscendingNullComparator = getStringAscendingNullComparator();
      Comparator<InetAddress> inetAddressAscendingComparator = new InetAddressComparator(stringAscendingNullComparator);
      Comparator<InetAddress> inetAddressAscendingNullComparator = Comparator.nullsLast(inetAddressAscendingComparator);
      Comparator<Integer> integerAscendingComparator = Comparator.naturalOrder();
      Comparator<InetSocketAddress> inetSocketAddressAscendingComparator = new InetSocketAddressComparator(
         inetAddressAscendingNullComparator, integerAscendingComparator
      );
      Comparator<InetSocketAddress> inetSocketAddressAscendingNullComparator = Comparator.nullsLast(inetSocketAddressAscendingComparator);
      Comparator<IpDestination> ipDestinationAscendingComparator = new IpDestinationComparator(
         inetSocketAddressAscendingNullComparator, integerAscendingComparator, integerAscendingComparator
      );
      Comparator<IpDestination> ipDestinationAscendingNullComparator = Comparator.nullsLast(ipDestinationAscendingComparator);
      Comparator<Device> deviceAddressAscendingComparator = new DeviceAddressComparator(ipDestinationAscendingNullComparator);
      Comparator<Device> deviceAddressAscendingNullComparator = Comparator.nullsLast(deviceAddressAscendingComparator);
      deviceAddressComparators.put(OrderDirection.ASC, deviceAddressAscendingNullComparator);
      Comparator<String> stringDescendingNullComparator = getStringDescendingNullComparator();
      Comparator<InetAddress> inetAddressDescendingComparator = new InetAddressComparator(stringDescendingNullComparator);
      Comparator<InetAddress> inetAddressDescendingNullComparator = Comparator.nullsLast(inetAddressDescendingComparator);
      Comparator<Integer> integerDescendingComparator = Comparator.reverseOrder();
      Comparator<InetSocketAddress> inetSocketAddressDescendingComparator = new InetSocketAddressComparator(
         inetAddressDescendingNullComparator, integerDescendingComparator
      );
      Comparator<InetSocketAddress> inetSocketAddressDescendingNullComparator = Comparator.nullsLast(inetSocketAddressDescendingComparator);
      Comparator<IpDestination> ipDestinationDescendingComparator = new IpDestinationComparator(
         inetSocketAddressDescendingNullComparator, integerDescendingComparator, integerDescendingComparator
      );
      Comparator<IpDestination> ipDestinationDescendingNullComparator = Comparator.nullsLast(ipDestinationDescendingComparator);
      Comparator<Device> deviceAddressDescendingComparator = new DeviceAddressComparator(ipDestinationDescendingNullComparator);
      Comparator<Device> deviceAddressDescendingNullComparator = Comparator.nullsLast(deviceAddressDescendingComparator);
      deviceAddressComparators.put(OrderDirection.DESC, deviceAddressDescendingNullComparator);
      return deviceAddressComparators;
   }

   private static Map<OrderDirection, Comparator<? super Device>> getDeviceTemperatureComparators() {
      Map<OrderDirection, Comparator<? super Device>> deviceTemperatureComparators = new EnumMap<>(OrderDirection.class);
      Comparator<TemperatureSensor> temperatureSensorAscendingComparator = new TemperatureSensorComparator();
      Comparator<TemperatureSensor> temperatureSensorAscendingNullComparator = Comparator.nullsLast(temperatureSensorAscendingComparator);
      Comparator<Device> deviceTemperatureAscendingComparator = new DeviceTemperatureComparator(temperatureSensorAscendingNullComparator);
      Comparator<Device> deviceTemperatureAscendingNullComparator = Comparator.nullsLast(deviceTemperatureAscendingComparator);
      deviceTemperatureComparators.put(OrderDirection.ASC, deviceTemperatureAscendingNullComparator);
      Comparator<TemperatureSensor> temperatureSensorDescendingComparator = new TemperatureSensorComparator().reversed();
      Comparator<TemperatureSensor> temperatureSensorDescendingNullComparator = Comparator.nullsLast(temperatureSensorDescendingComparator);
      Comparator<Device> deviceTemperatureDescendingComparator = new DeviceTemperatureComparator(temperatureSensorDescendingNullComparator);
      Comparator<Device> deviceTemperatureDescendingNullComparator = Comparator.nullsLast(deviceTemperatureDescendingComparator);
      deviceTemperatureComparators.put(OrderDirection.DESC, deviceTemperatureDescendingNullComparator);
      return deviceTemperatureComparators;
   }

   private static Map<OrderDirection, Comparator<? super Device>> getDevicePowerStateComparators() {
      Map<OrderDirection, Comparator<? super Device>> devicePowerStateComparators = new EnumMap<>(OrderDirection.class);
      Comparator<String> stringAscendingComparator = Comparator.naturalOrder();
      Comparator<PowerState> powerStateAscendingComparator = new EnumComparator<>(stringAscendingComparator);
      Comparator<PowerState> powerStateAscendingNullComparator = Comparator.nullsLast(powerStateAscendingComparator);
      Comparator<Device> devicePowerStateAscendingComparator = new DevicePowerStateComparator(powerStateAscendingNullComparator);
      Comparator<Device> devicePowerStateAscendingNullComparator = Comparator.nullsLast(devicePowerStateAscendingComparator);
      devicePowerStateComparators.put(OrderDirection.ASC, devicePowerStateAscendingNullComparator);
      Comparator<String> stringDescendingComparator = Comparator.reverseOrder();
      Comparator<PowerState> powerStateDescendingComparator = new EnumComparator<>(stringDescendingComparator);
      Comparator<PowerState> powerStateDescendingNullComparator = Comparator.nullsLast(powerStateDescendingComparator);
      Comparator<Device> devicePowerStateDescendingComparator = new DevicePowerStateComparator(powerStateDescendingNullComparator);
      Comparator<Device> devicePowerStateDescendingNullComparator = Comparator.nullsLast(devicePowerStateDescendingComparator);
      devicePowerStateComparators.put(OrderDirection.DESC, devicePowerStateDescendingNullComparator);
      return devicePowerStateComparators;
   }

   private static Map<OrderDirection, Comparator<? super Device>> getDeviceInputSourceComparators() {
      Map<OrderDirection, Comparator<? super Device>> deviceInputSourceComparators = new EnumMap<>(OrderDirection.class);
      Comparator<String> stringAscendingComparator = Comparator.naturalOrder();
      Comparator<InputSource.SourceType> sourceTypeAscendingComparator = new EnumComparator<>(stringAscendingComparator);
      Comparator<InputSource.SourceType> sourceTypeAscendingNullComparator = Comparator.nullsLast(sourceTypeAscendingComparator);
      Comparator<InputSource> inputSourceAscendingComparator = new InputSourceComparator(sourceTypeAscendingNullComparator);
      Comparator<InputSource> inputSourceAscendingNullComparator = Comparator.nullsLast(inputSourceAscendingComparator);
      Comparator<Device> deviceInputSourceAscendingComparator = new DeviceInputSourceComparator(inputSourceAscendingNullComparator);
      Comparator<Device> deviceInputSourceAscendingNullComparator = Comparator.nullsLast(deviceInputSourceAscendingComparator);
      deviceInputSourceComparators.put(OrderDirection.ASC, deviceInputSourceAscendingNullComparator);
      Comparator<String> stringDescendingComparator = Comparator.reverseOrder();
      Comparator<InputSource.SourceType> sourceTypeDescendingComparator = new EnumComparator<>(stringDescendingComparator);
      Comparator<InputSource.SourceType> sourceTypeDescendingNullComparator = Comparator.nullsLast(sourceTypeDescendingComparator);
      Comparator<InputSource> inputSourceDescendingComparator = new InputSourceComparator(sourceTypeDescendingNullComparator);
      Comparator<InputSource> inputSourceDescendingNullComparator = Comparator.nullsLast(inputSourceDescendingComparator);
      Comparator<Device> deviceInputSourceDescendingComparator = new DeviceInputSourceComparator(inputSourceDescendingNullComparator);
      Comparator<Device> deviceInputSourceDescendingNullComparator = Comparator.nullsLast(deviceInputSourceDescendingComparator);
      deviceInputSourceComparators.put(OrderDirection.DESC, deviceInputSourceDescendingNullComparator);
      return deviceInputSourceComparators;
   }

   @Override
   public Comparator<? super Device> getComparator(final OrderField<? super Device> orderField, final OrderDirection orderDirection) {
      Assert.notNull(orderField, GetComparatorMessages.ORDER_FIELD_CAN_NOT_BE_NULL);
      Assert.notNull(orderDirection, GetComparatorMessages.ORDER_DIRECTION_CAN_NOT_BE_NULL);
      Map<OrderDirection, Comparator<? super Device>> orderComparators = orderMap.get(orderField);
      if (orderComparators == null) {
         String noComparatorsFoundForOrderFieldMessage = GetComparatorMessages.getNoComparatorsFoundForOrderFieldMessage(orderField);
         throw new UnsupportedOperationException(noComparatorsFoundForOrderFieldMessage);
      } else {
         Comparator<? super Device> orderComparator = orderComparators.get(orderDirection);
         if (orderComparator == null) {
            String orderDirectionIsNotSupportedMessage = GetComparatorMessages.getOrderDirectionIsNotSupportedMessage(orderDirection);
            throw new UnsupportedOperationException(orderDirectionIsNotSupportedMessage);
         } else {
            return orderComparator;
         }
      }
   }

   static {
      Map<OrderDirection, Comparator<? super Device>> deviceIdComparators = getDeviceIdComparators();
      orderMap.put(be.tpvision.smartcontrol.repository.order_fields.device.OrderField.ID, deviceIdComparators);
      Map<OrderDirection, Comparator<? super Device>> deviceNameComparators = getDeviceNameComparators();
      orderMap.put(be.tpvision.smartcontrol.repository.order_fields.device.OrderField.NAME, deviceNameComparators);
      Map<OrderDirection, Comparator<? super Device>> deviceModelNumberComparators = getDeviceModelNumberComparators();
      orderMap.put(be.tpvision.smartcontrol.repository.order_fields.device.OrderField.MODEL_NUMBER, deviceModelNumberComparators);
      Map<OrderDirection, Comparator<? super Device>> deviceAddressComparators = getDeviceAddressComparators();
      orderMap.put(be.tpvision.smartcontrol.repository.order_fields.device.OrderField.IP_DESTINATION, deviceAddressComparators);
      Map<OrderDirection, Comparator<? super Device>> deviceTemperatureComparators = getDeviceTemperatureComparators();
      orderMap.put(be.tpvision.smartcontrol.repository.order_fields.device.OrderField.TEMPERATURE, deviceTemperatureComparators);
      Map<OrderDirection, Comparator<? super Device>> devicePowerStateComparators = getDevicePowerStateComparators();
      orderMap.put(be.tpvision.smartcontrol.repository.order_fields.device.OrderField.POWER_STATE, devicePowerStateComparators);
      Map<OrderDirection, Comparator<? super Device>> deviceInputSourceComparators = getDeviceInputSourceComparators();
      orderMap.put(be.tpvision.smartcontrol.repository.order_fields.device.OrderField.INPUT_SOURCE, deviceInputSourceComparators);
   }
}
