/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.comparators.device;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.comparators.EnumComparator;
import be.tpvision.smartcontrol.domain.comparators.InetAddressComparator;
import be.tpvision.smartcontrol.domain.comparators.InetSocketAddressComparator;
import be.tpvision.smartcontrol.domain.comparators.InputSourceComparator;
import be.tpvision.smartcontrol.domain.comparators.IpDestinationComparator;
import be.tpvision.smartcontrol.domain.comparators.StringWrapperComparator;
import be.tpvision.smartcontrol.domain.comparators.TemperatureSensorComparator;
import be.tpvision.smartcontrol.domain.comparators.device.DeviceAddressComparator;
import be.tpvision.smartcontrol.domain.comparators.device.DeviceComparatorFactory;
import be.tpvision.smartcontrol.domain.comparators.device.DeviceIdComparator;
import be.tpvision.smartcontrol.domain.comparators.device.DeviceInputSourceComparator;
import be.tpvision.smartcontrol.domain.comparators.device.DeviceModelNumberComparator;
import be.tpvision.smartcontrol.domain.comparators.device.DeviceNameComparator;
import be.tpvision.smartcontrol.domain.comparators.device.DevicePowerStateComparator;
import be.tpvision.smartcontrol.domain.comparators.device.DeviceTemperatureComparator;
import be.tpvision.smartcontrol.domain.device_settings.StringWrapper;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.InputSource;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.TemperatureSensor;
import be.tpvision.smartcontrol.io.ip.IpDestination;
import be.tpvision.smartcontrol.messages.domain.comparators.device.device_comparator_factory_impl.GetComparatorMessages;
import be.tpvision.smartcontrol.repository.OrderDirection;
import be.tpvision.smartcontrol.repository.order_fields.device.OrderField;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class DeviceComparatorFactoryImpl
implements DeviceComparatorFactory {
    private static final Map<be.tpvision.smartcontrol.repository.order_fields.OrderField<? super Device>, Map<OrderDirection, Comparator<? super Device>>> orderMap = new HashMap<be.tpvision.smartcontrol.repository.order_fields.OrderField<? super Device>, Map<OrderDirection, Comparator<? super Device>>>();

    private static Map<OrderDirection, Comparator<? super Device>> getDeviceIdComparators() {
        EnumMap<OrderDirection, Comparator<? super Device>> deviceIdComparators = new EnumMap<OrderDirection, Comparator<? super Device>>(OrderDirection.class);
        Comparator longAscendingComparator = Comparator.naturalOrder();
        Comparator<Long> longAscendingNullComparator = Comparator.nullsLast(longAscendingComparator);
        DeviceIdComparator deviceIdAscendingComparator = new DeviceIdComparator(longAscendingNullComparator);
        Comparator<Device> deviceIdAscendingNullComparator = Comparator.nullsLast(deviceIdAscendingComparator);
        deviceIdComparators.put(OrderDirection.ASC, deviceIdAscendingNullComparator);
        Comparator longDescendingComparator = Comparator.reverseOrder();
        Comparator<Long> longDescendingNullComparator = Comparator.nullsLast(longDescendingComparator);
        DeviceIdComparator deviceIdDescendingComparator = new DeviceIdComparator(longDescendingNullComparator);
        Comparator<Device> deviceIdDescendingNullComparator = Comparator.nullsLast(deviceIdDescendingComparator);
        deviceIdComparators.put(OrderDirection.DESC, deviceIdDescendingNullComparator);
        return deviceIdComparators;
    }

    private static Comparator<String> getStringAscendingNullComparator() {
        Comparator stringAscendingComparator = Comparator.naturalOrder();
        return Comparator.nullsLast(stringAscendingComparator);
    }

    private static Comparator<String> getStringDescendingNullComparator() {
        Comparator stringDescendingComparator = Comparator.reverseOrder();
        return Comparator.nullsLast(stringDescendingComparator);
    }

    private static Map<OrderDirection, Comparator<? super Device>> getDeviceNameComparators() {
        EnumMap<OrderDirection, Comparator<? super Device>> deviceNameComparators = new EnumMap<OrderDirection, Comparator<? super Device>>(OrderDirection.class);
        Comparator<String> stringAscendingNullComparator = DeviceComparatorFactoryImpl.getStringAscendingNullComparator();
        DeviceNameComparator deviceNameAscendingComparator = new DeviceNameComparator(stringAscendingNullComparator);
        Comparator<Device> deviceNameAscendingNullComparator = Comparator.nullsLast(deviceNameAscendingComparator);
        deviceNameComparators.put(OrderDirection.ASC, deviceNameAscendingNullComparator);
        Comparator<String> stringDescendingNullComparator = DeviceComparatorFactoryImpl.getStringDescendingNullComparator();
        DeviceNameComparator deviceNameDescendingComparator = new DeviceNameComparator(stringDescendingNullComparator);
        Comparator<Device> deviceNameDescendingNullComparator = Comparator.nullsLast(deviceNameDescendingComparator);
        deviceNameComparators.put(OrderDirection.DESC, deviceNameDescendingNullComparator);
        return deviceNameComparators;
    }

    private static Map<OrderDirection, Comparator<? super Device>> getDeviceModelNumberComparators() {
        EnumMap<OrderDirection, Comparator<? super Device>> deviceModelNumberComparators = new EnumMap<OrderDirection, Comparator<? super Device>>(OrderDirection.class);
        Comparator<String> stringAscendingNullComparator = DeviceComparatorFactoryImpl.getStringAscendingNullComparator();
        StringWrapperComparator stringWrapperAscendingComparator = new StringWrapperComparator(stringAscendingNullComparator);
        Comparator<StringWrapper> stringWrapperAscendingNullComparator = Comparator.nullsLast(stringWrapperAscendingComparator);
        DeviceModelNumberComparator deviceModelNumberAscendingComparator = new DeviceModelNumberComparator((Comparator<? super StringWrapper>)stringWrapperAscendingNullComparator);
        Comparator<Device> deviceModelNumberAscendingNullComparator = Comparator.nullsLast(deviceModelNumberAscendingComparator);
        deviceModelNumberComparators.put(OrderDirection.ASC, deviceModelNumberAscendingNullComparator);
        Comparator<String> stringDescendingNullComparator = DeviceComparatorFactoryImpl.getStringDescendingNullComparator();
        StringWrapperComparator stringWrapperDescendingComparator = new StringWrapperComparator(stringDescendingNullComparator);
        Comparator<StringWrapper> stringWrapperDescendingNullComparator = Comparator.nullsLast(stringWrapperDescendingComparator);
        DeviceModelNumberComparator deviceModelNumberDescendingComparator = new DeviceModelNumberComparator((Comparator<? super StringWrapper>)stringWrapperDescendingNullComparator);
        Comparator<Device> deviceModelNumberDescendingNullComparator = Comparator.nullsLast(deviceModelNumberDescendingComparator);
        deviceModelNumberComparators.put(OrderDirection.DESC, deviceModelNumberDescendingNullComparator);
        return deviceModelNumberComparators;
    }

    private static Map<OrderDirection, Comparator<? super Device>> getDeviceAddressComparators() {
        EnumMap<OrderDirection, Comparator<? super Device>> deviceAddressComparators = new EnumMap<OrderDirection, Comparator<? super Device>>(OrderDirection.class);
        Comparator<String> stringAscendingNullComparator = DeviceComparatorFactoryImpl.getStringAscendingNullComparator();
        InetAddressComparator inetAddressAscendingComparator = new InetAddressComparator(stringAscendingNullComparator);
        Comparator<InetAddress> inetAddressAscendingNullComparator = Comparator.nullsLast(inetAddressAscendingComparator);
        Comparator integerAscendingComparator = Comparator.naturalOrder();
        InetSocketAddressComparator inetSocketAddressAscendingComparator = new InetSocketAddressComparator(inetAddressAscendingNullComparator, integerAscendingComparator);
        Comparator<InetSocketAddress> inetSocketAddressAscendingNullComparator = Comparator.nullsLast(inetSocketAddressAscendingComparator);
        IpDestinationComparator ipDestinationAscendingComparator = new IpDestinationComparator(inetSocketAddressAscendingNullComparator, integerAscendingComparator, integerAscendingComparator);
        Comparator<IpDestination> ipDestinationAscendingNullComparator = Comparator.nullsLast(ipDestinationAscendingComparator);
        DeviceAddressComparator deviceAddressAscendingComparator = new DeviceAddressComparator((Comparator<? super IpDestination>)ipDestinationAscendingNullComparator);
        Comparator<Device> deviceAddressAscendingNullComparator = Comparator.nullsLast(deviceAddressAscendingComparator);
        deviceAddressComparators.put(OrderDirection.ASC, deviceAddressAscendingNullComparator);
        Comparator<String> stringDescendingNullComparator = DeviceComparatorFactoryImpl.getStringDescendingNullComparator();
        InetAddressComparator inetAddressDescendingComparator = new InetAddressComparator(stringDescendingNullComparator);
        Comparator<InetAddress> inetAddressDescendingNullComparator = Comparator.nullsLast(inetAddressDescendingComparator);
        Comparator integerDescendingComparator = Comparator.reverseOrder();
        InetSocketAddressComparator inetSocketAddressDescendingComparator = new InetSocketAddressComparator(inetAddressDescendingNullComparator, integerDescendingComparator);
        Comparator<InetSocketAddress> inetSocketAddressDescendingNullComparator = Comparator.nullsLast(inetSocketAddressDescendingComparator);
        IpDestinationComparator ipDestinationDescendingComparator = new IpDestinationComparator(inetSocketAddressDescendingNullComparator, integerDescendingComparator, integerDescendingComparator);
        Comparator<IpDestination> ipDestinationDescendingNullComparator = Comparator.nullsLast(ipDestinationDescendingComparator);
        DeviceAddressComparator deviceAddressDescendingComparator = new DeviceAddressComparator((Comparator<? super IpDestination>)ipDestinationDescendingNullComparator);
        Comparator<Device> deviceAddressDescendingNullComparator = Comparator.nullsLast(deviceAddressDescendingComparator);
        deviceAddressComparators.put(OrderDirection.DESC, deviceAddressDescendingNullComparator);
        return deviceAddressComparators;
    }

    private static Map<OrderDirection, Comparator<? super Device>> getDeviceTemperatureComparators() {
        EnumMap<OrderDirection, Comparator<? super Device>> deviceTemperatureComparators = new EnumMap<OrderDirection, Comparator<? super Device>>(OrderDirection.class);
        TemperatureSensorComparator temperatureSensorAscendingComparator = new TemperatureSensorComparator();
        Comparator<TemperatureSensor> temperatureSensorAscendingNullComparator = Comparator.nullsLast(temperatureSensorAscendingComparator);
        DeviceTemperatureComparator deviceTemperatureAscendingComparator = new DeviceTemperatureComparator((Comparator<? super TemperatureSensor>)temperatureSensorAscendingNullComparator);
        Comparator<Device> deviceTemperatureAscendingNullComparator = Comparator.nullsLast(deviceTemperatureAscendingComparator);
        deviceTemperatureComparators.put(OrderDirection.ASC, deviceTemperatureAscendingNullComparator);
        Comparator temperatureSensorDescendingComparator = new TemperatureSensorComparator().reversed();
        Comparator temperatureSensorDescendingNullComparator = Comparator.nullsLast(temperatureSensorDescendingComparator);
        DeviceTemperatureComparator deviceTemperatureDescendingComparator = new DeviceTemperatureComparator(temperatureSensorDescendingNullComparator);
        Comparator<Device> deviceTemperatureDescendingNullComparator = Comparator.nullsLast(deviceTemperatureDescendingComparator);
        deviceTemperatureComparators.put(OrderDirection.DESC, deviceTemperatureDescendingNullComparator);
        return deviceTemperatureComparators;
    }

    private static Map<OrderDirection, Comparator<? super Device>> getDevicePowerStateComparators() {
        EnumMap<OrderDirection, Comparator<? super Device>> devicePowerStateComparators = new EnumMap<OrderDirection, Comparator<? super Device>>(OrderDirection.class);
        Comparator<String> stringAscendingComparator = Comparator.naturalOrder();
        EnumComparator powerStateAscendingComparator = new EnumComparator(stringAscendingComparator);
        Comparator powerStateAscendingNullComparator = Comparator.nullsLast(powerStateAscendingComparator);
        DevicePowerStateComparator devicePowerStateAscendingComparator = new DevicePowerStateComparator(powerStateAscendingNullComparator);
        Comparator<Device> devicePowerStateAscendingNullComparator = Comparator.nullsLast(devicePowerStateAscendingComparator);
        devicePowerStateComparators.put(OrderDirection.ASC, devicePowerStateAscendingNullComparator);
        Comparator<String> stringDescendingComparator = Comparator.reverseOrder();
        EnumComparator powerStateDescendingComparator = new EnumComparator(stringDescendingComparator);
        Comparator powerStateDescendingNullComparator = Comparator.nullsLast(powerStateDescendingComparator);
        DevicePowerStateComparator devicePowerStateDescendingComparator = new DevicePowerStateComparator(powerStateDescendingNullComparator);
        Comparator<Device> devicePowerStateDescendingNullComparator = Comparator.nullsLast(devicePowerStateDescendingComparator);
        devicePowerStateComparators.put(OrderDirection.DESC, devicePowerStateDescendingNullComparator);
        return devicePowerStateComparators;
    }

    private static Map<OrderDirection, Comparator<? super Device>> getDeviceInputSourceComparators() {
        EnumMap<OrderDirection, Comparator<? super Device>> deviceInputSourceComparators = new EnumMap<OrderDirection, Comparator<? super Device>>(OrderDirection.class);
        Comparator<String> stringAscendingComparator = Comparator.naturalOrder();
        EnumComparator sourceTypeAscendingComparator = new EnumComparator(stringAscendingComparator);
        Comparator sourceTypeAscendingNullComparator = Comparator.nullsLast(sourceTypeAscendingComparator);
        InputSourceComparator inputSourceAscendingComparator = new InputSourceComparator(sourceTypeAscendingNullComparator);
        Comparator<InputSource> inputSourceAscendingNullComparator = Comparator.nullsLast(inputSourceAscendingComparator);
        DeviceInputSourceComparator deviceInputSourceAscendingComparator = new DeviceInputSourceComparator((Comparator<? super InputSource>)inputSourceAscendingNullComparator);
        Comparator<Device> deviceInputSourceAscendingNullComparator = Comparator.nullsLast(deviceInputSourceAscendingComparator);
        deviceInputSourceComparators.put(OrderDirection.ASC, deviceInputSourceAscendingNullComparator);
        Comparator<String> stringDescendingComparator = Comparator.reverseOrder();
        EnumComparator sourceTypeDescendingComparator = new EnumComparator(stringDescendingComparator);
        Comparator sourceTypeDescendingNullComparator = Comparator.nullsLast(sourceTypeDescendingComparator);
        InputSourceComparator inputSourceDescendingComparator = new InputSourceComparator(sourceTypeDescendingNullComparator);
        Comparator<InputSource> inputSourceDescendingNullComparator = Comparator.nullsLast(inputSourceDescendingComparator);
        DeviceInputSourceComparator deviceInputSourceDescendingComparator = new DeviceInputSourceComparator((Comparator<? super InputSource>)inputSourceDescendingNullComparator);
        Comparator<Device> deviceInputSourceDescendingNullComparator = Comparator.nullsLast(deviceInputSourceDescendingComparator);
        deviceInputSourceComparators.put(OrderDirection.DESC, deviceInputSourceDescendingNullComparator);
        return deviceInputSourceComparators;
    }

    @Override
    public Comparator<? super Device> getComparator(be.tpvision.smartcontrol.repository.order_fields.OrderField<? super Device> orderField, OrderDirection orderDirection) {
        Assert.notNull(orderField, GetComparatorMessages.ORDER_FIELD_CAN_NOT_BE_NULL);
        Assert.notNull((Object)orderDirection, GetComparatorMessages.ORDER_DIRECTION_CAN_NOT_BE_NULL);
        Map<OrderDirection, Comparator<? super Device>> orderComparators = orderMap.get(orderField);
        if (orderComparators == null) {
            String noComparatorsFoundForOrderFieldMessage = GetComparatorMessages.getNoComparatorsFoundForOrderFieldMessage(orderField);
            throw new UnsupportedOperationException(noComparatorsFoundForOrderFieldMessage);
        }
        Comparator<? super Device> orderComparator = orderComparators.get((Object)orderDirection);
        if (orderComparator == null) {
            String orderDirectionIsNotSupportedMessage = GetComparatorMessages.getOrderDirectionIsNotSupportedMessage(orderDirection);
            throw new UnsupportedOperationException(orderDirectionIsNotSupportedMessage);
        }
        return orderComparator;
    }

    static {
        Map<OrderDirection, Comparator<? super Device>> deviceIdComparators = DeviceComparatorFactoryImpl.getDeviceIdComparators();
        orderMap.put(OrderField.ID, deviceIdComparators);
        Map<OrderDirection, Comparator<? super Device>> deviceNameComparators = DeviceComparatorFactoryImpl.getDeviceNameComparators();
        orderMap.put(OrderField.NAME, deviceNameComparators);
        Map<OrderDirection, Comparator<? super Device>> deviceModelNumberComparators = DeviceComparatorFactoryImpl.getDeviceModelNumberComparators();
        orderMap.put(OrderField.MODEL_NUMBER, deviceModelNumberComparators);
        Map<OrderDirection, Comparator<? super Device>> deviceAddressComparators = DeviceComparatorFactoryImpl.getDeviceAddressComparators();
        orderMap.put(OrderField.IP_DESTINATION, deviceAddressComparators);
        Map<OrderDirection, Comparator<? super Device>> deviceTemperatureComparators = DeviceComparatorFactoryImpl.getDeviceTemperatureComparators();
        orderMap.put(OrderField.TEMPERATURE, deviceTemperatureComparators);
        Map<OrderDirection, Comparator<? super Device>> devicePowerStateComparators = DeviceComparatorFactoryImpl.getDevicePowerStateComparators();
        orderMap.put(OrderField.POWER_STATE, devicePowerStateComparators);
        Map<OrderDirection, Comparator<? super Device>> deviceInputSourceComparators = DeviceComparatorFactoryImpl.getDeviceInputSourceComparators();
        orderMap.put(OrderField.INPUT_SOURCE, deviceInputSourceComparators);
    }
}

