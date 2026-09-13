package be.tpvision.smartcontrol.rest.controllers;

import be.tpvision.smartcontrol.domain.twa.Brightness;
import be.tpvision.smartcontrol.domain.twa.CascadeMode;
import be.tpvision.smartcontrol.domain.twa.DeviceAddress;
import be.tpvision.smartcontrol.domain.twa.DeviceAddressWrapper;
import be.tpvision.smartcontrol.domain.twa.MainboardOriginPosition;
import be.tpvision.smartcontrol.domain.twa.MainboardSignalOutput;
import be.tpvision.smartcontrol.domain.twa.ModuleAddress;
import be.tpvision.smartcontrol.domain.twa.NormalDisplayTestPattern;
import be.tpvision.smartcontrol.domain.twa.TestPatternColor;
import be.tpvision.smartcontrol.io.ip.twa.NettyCommandSender;
import be.tpvision.smartcontrol.messages.controllers.led_device.ConstructorMessages;
import be.tpvision.smartcontrol.messages.controllers.led_device.SetBrightnessMessages;
import be.tpvision.smartcontrol.messages.controllers.led_device.SetCascadeModeMessages;
import be.tpvision.smartcontrol.messages.controllers.led_device.SetDeviceAddressMessages;
import be.tpvision.smartcontrol.messages.controllers.led_device.SetMainboardOriginPositionMessages;
import be.tpvision.smartcontrol.messages.controllers.led_device.SetMainboardSignalOutputMessages;
import be.tpvision.smartcontrol.messages.controllers.led_device.SetNormalDisplayTestPatternMessages;
import be.tpvision.smartcontrol.messages.controllers.led_device.SetTestPatternColorMessages;
import be.tpvision.smartcontrol.protocol.twa.DeviceAddressCommand;
import be.tpvision.smartcontrol.protocol.twa.TwaCommand;
import be.tpvision.smartcontrol.protocol.twa.TwaCommandFactory;
import be.tpvision.smartcontrol.rest.mappers.twa.BrightnessMapper;
import be.tpvision.smartcontrol.rest.mappers.twa.DeviceAddressMapper;
import be.tpvision.smartcontrol.rest.mappers.twa.DeviceAddressWrapperMapper;
import be.tpvision.smartcontrol.rest.mappers.twa.MainboardOriginPositionMapper;
import be.tpvision.smartcontrol.rest.mappers.twa.ModuleAddressMapper;
import be.tpvision.smartcontrol.rest.view_models.twa.DeviceAddressViewModel;
import be.tpvision.smartcontrol.rest.view_models.twa.DeviceAddressWrapperViewModel;
import be.tpvision.smartcontrol.rest.view_models.twa.IntegerViewModel;
import be.tpvision.smartcontrol.rest.view_models.twa.MainboardOriginPositionViewModel;
import be.tpvision.smartcontrol.rest.view_models.twa.ModuleAddressViewModel;
import be.tpvision.smartcontrol.rest.view_models.twa.StringViewModel;
import be.tpvision.smartcontrol.rest.view_models.twa.TwaCommandViewModel;
import be.tpvision.smartcontrol.rest.view_models.twa.TwaDeviceSettingViewModel;
import be.tpvision.smartcontrol.util.ValueUtilities;
import java.net.InetSocketAddress;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ledDevices")
public class LedDeviceController {
   private static final int PORT = 8001;
   private final NettyCommandSender nettyCommandSender;

   @Autowired
   public LedDeviceController(final NettyCommandSender nettyCommandSender) {
      Assert.notNull(nettyCommandSender, ConstructorMessages.NETTY_COMMAND_SENDER_CAN_NOT_BE_NULL);
      this.nettyCommandSender = nettyCommandSender;
   }

   @PutMapping("/{ipAddress}/mainboardOriginPosition")
   public void setMainboardOriginPosition(@PathVariable("ipAddress") final String ipAddress, @RequestBody final TwaCommandViewModel twaCommandViewModel) {
      Assert.isTrue(StringUtils.hasText(ipAddress), SetMainboardOriginPositionMessages.IP_ADDRESS_CAN_NOT_BE_EMPTY);
      Assert.notNull(twaCommandViewModel, SetMainboardOriginPositionMessages.TWA_COMMAND_VIEW_MODEL_CAN_NOT_BE_NULL);
      DeviceAddressViewModel deviceAddressViewModel = twaCommandViewModel.getDeviceAddress();
      Assert.state(deviceAddressViewModel != null, SetMainboardOriginPositionMessages.DEVICE_ADDRESS_VIEW_MODEL_CAN_NOT_BE_NULL);
      DeviceAddress deviceAddress = DeviceAddressMapper.toDeviceAddress(deviceAddressViewModel);
      Assert.state(deviceAddress != null, SetMainboardOriginPositionMessages.DEVICE_ADDRESS_CAN_NOT_BE_NULL);
      ModuleAddressViewModel moduleAddressViewModel = twaCommandViewModel.getModuleAddress();
      Assert.state(moduleAddressViewModel != null, SetMainboardOriginPositionMessages.MODULE_ADDRESS_VIEW_MODEL_CAN_NOT_BE_NULL);
      ModuleAddress moduleAddress = ModuleAddressMapper.toModuleAddress(moduleAddressViewModel);
      Assert.state(moduleAddress != null, SetMainboardOriginPositionMessages.MODULE_ADDRESS_CAN_NOT_BE_NULL);
      TwaDeviceSettingViewModel twaDeviceSettingViewModel = twaCommandViewModel.getTwaDeviceSetting();
      Assert.state(twaDeviceSettingViewModel != null, SetMainboardOriginPositionMessages.TWA_DEVICE_SETTING_VIEW_MODEL_CAN_NOT_BE_NULL);
      Assert.isInstanceOf(
         MainboardOriginPositionViewModel.class,
         twaDeviceSettingViewModel,
         "TWA device setting view model has to be an instance of mainboard origin position view model"
      );
      MainboardOriginPositionViewModel mainboardOriginPositionViewModel = (MainboardOriginPositionViewModel)twaDeviceSettingViewModel;
      Assert.notNull(mainboardOriginPositionViewModel, SetMainboardOriginPositionMessages.MAINBOARD_ORIGIN_POSITION_VIEW_MODEL_CAN_NOT_BE_NULL);
      MainboardOriginPosition mainboardOriginPosition = MainboardOriginPositionMapper.toMainboardOriginPosition(mainboardOriginPositionViewModel);
      Assert.state(mainboardOriginPosition != null, SetMainboardOriginPositionMessages.MAINBOARD_ORIGIN_POSITION_CAN_NOT_BE_NULL);
      TwaCommand mainboardOriginPositionCommand = TwaCommandFactory.getTwaCommand(deviceAddress, moduleAddress, mainboardOriginPosition);
      Assert.state(mainboardOriginPositionCommand != null, SetMainboardOriginPositionMessages.MAINBOARD_ORIGIN_POSITION_COMMAND_CAN_NOT_BE_NULL);
      InetSocketAddress inetSocketAddress = new InetSocketAddress(ipAddress, 8001);
      this.nettyCommandSender.send(mainboardOriginPositionCommand, inetSocketAddress);
   }

   @RequestMapping(value = "/{ipAddress}/mainboardSignalOutput", method = RequestMethod.PUT)
   public void setMainboardSignalOutput(@PathVariable("ipAddress") final String ipAddress, @RequestBody final TwaCommandViewModel twaCommandViewModel) {
      Assert.isTrue(StringUtils.hasText(ipAddress), SetMainboardSignalOutputMessages.IP_ADDRESS_CAN_NOT_BE_EMPTY);
      Assert.notNull(twaCommandViewModel, SetMainboardSignalOutputMessages.TWA_COMMAND_VIEW_MODEL_CAN_NOT_BE_NULL);
      DeviceAddressViewModel deviceAddressViewModel = twaCommandViewModel.getDeviceAddress();
      Assert.state(deviceAddressViewModel != null, SetMainboardSignalOutputMessages.DEVICE_ADDRESS_VIEW_MODEL_CAN_NOT_BE_NULL);
      DeviceAddress deviceAddress = DeviceAddressMapper.toDeviceAddress(deviceAddressViewModel);
      Assert.state(deviceAddress != null, SetMainboardSignalOutputMessages.DEVICE_ADDRESS_CAN_NOT_BE_NULL);
      ModuleAddressViewModel moduleAddressViewModel = twaCommandViewModel.getModuleAddress();
      Assert.state(moduleAddressViewModel != null, SetMainboardSignalOutputMessages.MODULE_ADDRESS_VIEW_MODEL_CAN_NOT_BE_NULL);
      ModuleAddress moduleAddress = ModuleAddressMapper.toModuleAddress(moduleAddressViewModel);
      Assert.state(moduleAddress != null, SetMainboardSignalOutputMessages.MODULE_ADDRESS_CAN_NOT_BE_NULL);
      TwaDeviceSettingViewModel twaDeviceSettingViewModel = twaCommandViewModel.getTwaDeviceSetting();
      Assert.state(twaDeviceSettingViewModel != null, SetMainboardSignalOutputMessages.TWA_DEVICE_SETTING_VIEW_MODEL_CAN_NOT_BE_NULL);
      Assert.isInstanceOf(StringViewModel.class, twaDeviceSettingViewModel, "TWA device setting view model has to be an instance of string view model");
      StringViewModel stringViewModel = (StringViewModel)twaDeviceSettingViewModel;
      String viewModelMainboardSignalOutput = stringViewModel.getValue();
      Assert.state(StringUtils.hasText(viewModelMainboardSignalOutput), SetMainboardSignalOutputMessages.VIEW_MODEL_MAINBOARD_SIGNAL_OUTPUT_CAN_NOT_BE_EMPTY);
      MainboardSignalOutput domainMainboardSignalOutput = ValueUtilities.getEnumValue(MainboardSignalOutput.class, viewModelMainboardSignalOutput);
      Assert.state(domainMainboardSignalOutput != null, SetMainboardSignalOutputMessages.DOMAIN_MAINBOARD_SIGNAL_OUTPUT_CAN_NOT_BE_NULL);
      TwaCommand mainboardSignalOutputCommand = TwaCommandFactory.getTwaCommand(deviceAddress, moduleAddress, domainMainboardSignalOutput);
      Assert.state(mainboardSignalOutputCommand != null, SetMainboardSignalOutputMessages.MAINBOARD_SIGNAL_OUTPUT_COMMAND_CAN_NOT_BE_NULL);
      InetSocketAddress inetSocketAddress = new InetSocketAddress(ipAddress, 8001);
      this.nettyCommandSender.send(mainboardSignalOutputCommand, inetSocketAddress);
   }

   @PutMapping("/{ipAddress}/cascadeMode")
   public void setCascadeMode(@PathVariable("ipAddress") final String ipAddress, @RequestBody final TwaCommandViewModel twaCommandViewModel) {
      Assert.isTrue(StringUtils.hasText(ipAddress), SetCascadeModeMessages.IP_ADDRESS_CAN_NOT_BE_EMPTY);
      Assert.notNull(twaCommandViewModel, SetCascadeModeMessages.TWA_COMMAND_VIEW_MODEL_CAN_NOT_BE_NULL);
      DeviceAddressViewModel deviceAddressViewModel = twaCommandViewModel.getDeviceAddress();
      Assert.state(deviceAddressViewModel != null, SetCascadeModeMessages.DEVICE_ADDRESS_VIEW_MODEL_CAN_NOT_BE_NULL);
      DeviceAddress deviceAddress = DeviceAddressMapper.toDeviceAddress(deviceAddressViewModel);
      Assert.state(deviceAddress != null, SetCascadeModeMessages.DEVICE_ADDRESS_CAN_NOT_BE_NULL);
      ModuleAddressViewModel moduleAddressViewModel = twaCommandViewModel.getModuleAddress();
      Assert.state(moduleAddressViewModel != null, SetCascadeModeMessages.MODULE_ADDRESS_VIEW_MODEL_CAN_NOT_BE_NULL);
      ModuleAddress moduleAddress = ModuleAddressMapper.toModuleAddress(moduleAddressViewModel);
      Assert.state(moduleAddress != null, SetCascadeModeMessages.MODULE_ADDRESS_CAN_NOT_BE_NULL);
      TwaDeviceSettingViewModel twaDeviceSettingViewModel = twaCommandViewModel.getTwaDeviceSetting();
      Assert.state(twaDeviceSettingViewModel != null, SetCascadeModeMessages.TWA_DEVICE_SETTING_VIEW_MODEL_CAN_NOT_BE_NULL);
      Assert.isInstanceOf(StringViewModel.class, twaDeviceSettingViewModel, "TWA device setting view model has to be an instance of string view model");
      StringViewModel stringViewModel = (StringViewModel)twaDeviceSettingViewModel;
      String viewModelCascadeMode = stringViewModel.getValue();
      Assert.state(!ObjectUtils.isEmpty(viewModelCascadeMode), SetCascadeModeMessages.VIEW_MODEL_CASCADE_MODE_CAN_NOT_BE_EMPTY);
      CascadeMode domainCascadeMode = ValueUtilities.getEnumValue(CascadeMode.class, viewModelCascadeMode);
      Assert.state(domainCascadeMode != null, SetCascadeModeMessages.DOMAIN_CASCADE_MODE_CAN_NOT_BE_NULL);
      TwaCommand cascadeModeCommand = TwaCommandFactory.getTwaCommand(deviceAddress, moduleAddress, domainCascadeMode);
      Assert.state(cascadeModeCommand != null, SetCascadeModeMessages.CASCADE_MODE_COMMAND_CAN_NOT_BE_NULL);
      InetSocketAddress inetSocketAddress = new InetSocketAddress(ipAddress, 8001);
      this.nettyCommandSender.send(cascadeModeCommand, inetSocketAddress);
   }

   @PutMapping("/{ipAddress}/normalDisplayTestPattern")
   public void setNormalDisplayTestPattern(@PathVariable("ipAddress") final String ipAddress, @RequestBody final TwaCommandViewModel twaCommandViewModel) {
      Assert.isTrue(StringUtils.hasText(ipAddress), SetNormalDisplayTestPatternMessages.IP_ADDRESS_CAN_NOT_BE_EMPTY);
      Assert.notNull(twaCommandViewModel, SetNormalDisplayTestPatternMessages.TWA_COMMAND_VIEW_MODEL_CAN_NOT_BE_NULL);
      DeviceAddressViewModel deviceAddressViewModel = twaCommandViewModel.getDeviceAddress();
      Assert.state(deviceAddressViewModel != null, SetNormalDisplayTestPatternMessages.DEVICE_ADDRESS_VIEW_MODEL_CAN_NOT_BE_NULL);
      DeviceAddress deviceAddress = DeviceAddressMapper.toDeviceAddress(deviceAddressViewModel);
      Assert.state(deviceAddress != null, SetNormalDisplayTestPatternMessages.DEVICE_ADDRESS_CAN_NOT_BE_NULL);
      ModuleAddressViewModel moduleAddressViewModel = twaCommandViewModel.getModuleAddress();
      Assert.state(moduleAddressViewModel != null, SetNormalDisplayTestPatternMessages.MODULE_ADDRESS_VIEW_MODEL_CAN_NOT_BE_NULL);
      ModuleAddress moduleAddress = ModuleAddressMapper.toModuleAddress(moduleAddressViewModel);
      Assert.state(moduleAddress != null, SetNormalDisplayTestPatternMessages.MODULE_ADDRESS_CAN_NOT_BE_NULL);
      TwaDeviceSettingViewModel twaDeviceSettingViewModel = twaCommandViewModel.getTwaDeviceSetting();
      Assert.state(twaDeviceSettingViewModel != null, SetNormalDisplayTestPatternMessages.TWA_DEVICE_SETTING_VIEW_MODEL_CAN_NOT_BE_NULL);
      Assert.isInstanceOf(StringViewModel.class, twaDeviceSettingViewModel, "TWA device setting view model has to be an instance of string view model");
      StringViewModel stringViewModel = (StringViewModel)twaDeviceSettingViewModel;
      String viewModelNormalDisplayTestPattern = stringViewModel.getValue();
      Assert.state(
         StringUtils.hasText(viewModelNormalDisplayTestPattern), SetNormalDisplayTestPatternMessages.VIEW_MODEL_NORMAL_DISPLAY_TEST_PATTERN_CAN_NOT_BE_EMPTY
      );
      NormalDisplayTestPattern domainNormalDisplayTestPattern = ValueUtilities.getEnumValue(NormalDisplayTestPattern.class, viewModelNormalDisplayTestPattern);
      Assert.state(domainNormalDisplayTestPattern != null, SetNormalDisplayTestPatternMessages.DOMAIN_NORMAL_DISPLAY_TEST_PATTERN_CAN_NOT_BE_NULL);
      TwaCommand normalDisplayTestPatternCommand = TwaCommandFactory.getTwaCommand(deviceAddress, moduleAddress, domainNormalDisplayTestPattern);
      Assert.state(normalDisplayTestPatternCommand != null, SetNormalDisplayTestPatternMessages.NORMAL_DISPLAY_TEST_PATTERN_COMMAND_CAN_NOT_BE_NULL);
      InetSocketAddress inetSocketAddress = new InetSocketAddress(ipAddress, 8001);
      this.nettyCommandSender.send(normalDisplayTestPatternCommand, inetSocketAddress);
   }

   @PutMapping("/{ipAddress}/testPatternColor")
   public void setTestPatternColor(@PathVariable("ipAddress") final String ipAddress, @RequestBody final TwaCommandViewModel twaCommandViewModel) {
      Assert.isTrue(StringUtils.hasText(ipAddress), SetTestPatternColorMessages.IP_ADDRESS_CAN_NOT_BE_EMPTY);
      Assert.notNull(twaCommandViewModel, SetTestPatternColorMessages.TWA_COMMAND_VIEW_MODEL_CAN_NOT_BE_NULL);
      DeviceAddressViewModel deviceAddressViewModel = twaCommandViewModel.getDeviceAddress();
      Assert.state(deviceAddressViewModel != null, SetTestPatternColorMessages.DEVICE_ADDRESS_VIEW_MODEL_CAN_NOT_BE_NULL);
      DeviceAddress deviceAddress = DeviceAddressMapper.toDeviceAddress(deviceAddressViewModel);
      Assert.state(deviceAddress != null, SetTestPatternColorMessages.DEVICE_ADDRESS_CAN_NOT_BE_NULL);
      ModuleAddressViewModel moduleAddressViewModel = twaCommandViewModel.getModuleAddress();
      Assert.state(moduleAddressViewModel != null, SetTestPatternColorMessages.MODULE_ADDRESS_VIEW_MODEL_CAN_NOT_BE_NULL);
      ModuleAddress moduleAddress = ModuleAddressMapper.toModuleAddress(moduleAddressViewModel);
      Assert.state(moduleAddress != null, SetTestPatternColorMessages.MODULE_ADDRESS_CAN_NOT_BE_NULL);
      TwaDeviceSettingViewModel twaDeviceSettingViewModel = twaCommandViewModel.getTwaDeviceSetting();
      Assert.state(twaDeviceSettingViewModel != null, SetTestPatternColorMessages.TWA_DEVICE_SETTING_VIEW_MODEL_CAN_NOT_BE_NULL);
      Assert.isInstanceOf(StringViewModel.class, twaDeviceSettingViewModel, "TWA device setting view model has to be an instance of string view model");
      StringViewModel stringViewModel = (StringViewModel)twaDeviceSettingViewModel;
      String viewModelTestPatternColor = stringViewModel.getValue();
      Assert.state(StringUtils.hasText(viewModelTestPatternColor), SetTestPatternColorMessages.VIEW_MODEL_TEST_PATTERN_COLOR_CAN_NOT_BE_EMPTY);
      TestPatternColor domainTestPatternColor = ValueUtilities.getEnumValue(TestPatternColor.class, viewModelTestPatternColor);
      Assert.state(domainTestPatternColor != null, SetTestPatternColorMessages.DOMAIN_TEST_PATTERN_COLOR_CAN_NOT_BE_NULL);
      TwaCommand testPatternColorCommand = TwaCommandFactory.getTwaCommand(deviceAddress, moduleAddress, domainTestPatternColor);
      Assert.state(testPatternColorCommand != null, SetTestPatternColorMessages.TEST_PATTERN_COLOR_COMMAND_CAN_NOT_BE_NULL);
      InetSocketAddress inetSocketAddress = new InetSocketAddress(ipAddress, 8001);
      this.nettyCommandSender.send(testPatternColorCommand, inetSocketAddress);
   }

   @PutMapping("/{ipAddress}/brightness")
   public void setBrightness(@PathVariable("ipAddress") final String ipAddress, @RequestBody final TwaCommandViewModel twaCommandViewModel) {
      Assert.isTrue(StringUtils.hasText(ipAddress), SetBrightnessMessages.IP_ADDRESS_CAN_NOT_BE_EMPTY);
      Assert.notNull(twaCommandViewModel, SetBrightnessMessages.TWA_COMMAND_VIEW_MODEL_CAN_NOT_BE_NULL);
      DeviceAddressViewModel deviceAddressViewModel = twaCommandViewModel.getDeviceAddress();
      Assert.state(deviceAddressViewModel != null, SetBrightnessMessages.DEVICE_ADDRESS_VIEW_MODEL_CAN_NOT_BE_NULL);
      DeviceAddress deviceAddress = DeviceAddressMapper.toDeviceAddress(deviceAddressViewModel);
      Assert.state(deviceAddress != null, SetBrightnessMessages.DEVICE_ADDRESS_CAN_NOT_BE_NULL);
      ModuleAddressViewModel moduleAddressViewModel = twaCommandViewModel.getModuleAddress();
      Assert.state(moduleAddressViewModel != null, SetBrightnessMessages.MODULE_ADDRESS_VIEW_MODEL_CAN_NOT_BE_NULL);
      ModuleAddress moduleAddress = ModuleAddressMapper.toModuleAddress(moduleAddressViewModel);
      Assert.state(moduleAddress != null, SetBrightnessMessages.MODULE_ADDRESS_CAN_NOT_BE_NULL);
      TwaDeviceSettingViewModel twaDeviceSettingViewModel = twaCommandViewModel.getTwaDeviceSetting();
      Assert.state(twaDeviceSettingViewModel != null, SetBrightnessMessages.TWA_DEVICE_SETTING_VIEW_MODEL_CAN_NOT_BE_NULL);
      Assert.isInstanceOf(IntegerViewModel.class, twaDeviceSettingViewModel, "TWA device setting view model has to be an instance of integer view model");
      IntegerViewModel integerViewModel = (IntegerViewModel)twaDeviceSettingViewModel;
      Brightness brightness = BrightnessMapper.toBrightness(integerViewModel);
      Assert.state(brightness != null, SetBrightnessMessages.BRIGHTNESS_CAN_NOT_BE_NULL);
      TwaCommand brightnessCommand = TwaCommandFactory.getTwaCommand(deviceAddress, moduleAddress, brightness);
      Assert.state(brightnessCommand != null, SetBrightnessMessages.BRIGHTNESS_COMMAND_CAN_NOT_BE_NULL);
      InetSocketAddress inetSocketAddress = new InetSocketAddress(ipAddress, 8001);
      this.nettyCommandSender.send(brightnessCommand, inetSocketAddress);
   }

   @PutMapping("/{ipAddress}/deviceAddress")
   public void setDeviceAddress(
      @PathVariable("ipAddress") final String ipAddress, @RequestBody final DeviceAddressWrapperViewModel deviceAddressWrapperViewModel
   ) {
      Assert.isTrue(StringUtils.hasText(ipAddress), SetDeviceAddressMessages.IP_ADDRESS_CAN_NOT_BE_EMPTY);
      Assert.notNull(deviceAddressWrapperViewModel, SetDeviceAddressMessages.DEVICE_ADDRESS_WRAPPER_VIEW_MODEL_CAN_NOT_BE_NULL);
      DeviceAddressWrapper deviceAddressWrapper = DeviceAddressWrapperMapper.toDeviceAddressWrapper(deviceAddressWrapperViewModel);
      Assert.state(deviceAddressWrapper != null, SetDeviceAddressMessages.DEVICE_ADDRESS_WRAPPER_CAN_NOT_BE_NULL);
      DeviceAddressCommand deviceAddressCommand = TwaCommandFactory.getDeviceAddressCommand(deviceAddressWrapper);
      Assert.state(deviceAddressCommand != null, SetDeviceAddressMessages.DEVICE_ADDRESS_COMMAND_CAN_NOT_BE_NULL);
      InetSocketAddress inetSocketAddress = new InetSocketAddress(ipAddress, 8001);
      this.nettyCommandSender.send(deviceAddressCommand, inetSocketAddress);
   }
}
