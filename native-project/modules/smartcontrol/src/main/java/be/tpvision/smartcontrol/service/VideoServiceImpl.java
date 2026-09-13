package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.device_settings.video.ColorParameters;
import be.tpvision.smartcontrol.domain.device_settings.video.ColorTemperature;
import be.tpvision.smartcontrol.domain.device_settings.video.ColorTemperature100K;
import be.tpvision.smartcontrol.domain.device_settings.video.PictureFormat;
import be.tpvision.smartcontrol.domain.device_settings.video.PictureInPicture;
import be.tpvision.smartcontrol.domain.device_settings.video.PictureInPictureSource;
import be.tpvision.smartcontrol.domain.device_settings.video.PictureStyle;
import be.tpvision.smartcontrol.domain.device_settings.video.VGAVideoParameters;
import be.tpvision.smartcontrol.domain.device_settings.video.VideoParameters;
import be.tpvision.smartcontrol.messages.services.video.ConstructorMessages;
import be.tpvision.smartcontrol.messages.services.video.SetColorParametersMessages;
import be.tpvision.smartcontrol.messages.services.video.SetColorTemperature100kMessages;
import be.tpvision.smartcontrol.messages.services.video.SetColorTemperatureMessages;
import be.tpvision.smartcontrol.messages.services.video.SetPictureFormatMessages;
import be.tpvision.smartcontrol.messages.services.video.SetPictureInPictureMessages;
import be.tpvision.smartcontrol.messages.services.video.SetPictureInPictureSourceMessages;
import be.tpvision.smartcontrol.messages.services.video.SetPictureStyleMessages;
import be.tpvision.smartcontrol.messages.services.video.SetVgaVideoParametersMessages;
import be.tpvision.smartcontrol.messages.services.video.SetVideoParametersMessages;
import be.tpvision.smartcontrol.protocol.sicp.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@Profile("production")
public class VideoServiceImpl implements VideoService {
   private final CommandService commandService;

   @Autowired
   VideoServiceImpl(final CommandService commandService) {
      Assert.notNull(commandService, ConstructorMessages.COMMAND_SERVICE_CAN_NOT_BE_NULL);
      this.commandService = commandService;
   }

   @Override
   public VideoParameters getVideoParameters(final Device device) {
      return device == null ? null : this.commandService.send(device, Command.Type.GET, Command.Setting.VIDEO_PARAMETERS, VideoParameters.class);
   }

   @Override
   public void setVideoParameters(final Device device, final VideoParameters videoParameters) {
      Assert.notNull(device, SetVideoParametersMessages.DEVICE_CAN_NOT_BE_NULL);
      Assert.notNull(videoParameters, SetVideoParametersMessages.VIDEO_PARAMETERS_CAN_NOT_BE_NULL);
      this.commandService.send(device, Command.Type.SET, Command.Setting.VIDEO_PARAMETERS, videoParameters);
   }

   @Override
   public ColorTemperature getColorTemperature(final Device device) {
      return device == null ? null : this.commandService.send(device, Command.Type.GET, Command.Setting.COLOR_TEMPERATURE, ColorTemperature.class);
   }

   @Override
   public void setColorTemperature(final Device device, final ColorTemperature colorTemperature) {
      Assert.notNull(device, SetColorTemperatureMessages.DEVICE_CAN_NOT_BE_NULL);
      Assert.notNull(colorTemperature, SetColorTemperatureMessages.COLOR_TEMPERATURE_CAN_NOT_BE_NULL);
      this.commandService.send(device, Command.Type.SET, Command.Setting.COLOR_TEMPERATURE, colorTemperature);
   }

   @Override
   public ColorParameters getColorParameters(final Device device) {
      return device == null ? null : this.commandService.send(device, Command.Type.GET, Command.Setting.COLOR_PARAMETERS, ColorParameters.class);
   }

   @Override
   public void setColorParameters(final Device device, final ColorParameters colorParameters) {
      Assert.notNull(device, SetColorParametersMessages.DEVICE_CAN_NOT_BE_NULL);
      Assert.notNull(colorParameters, SetColorParametersMessages.COLOR_PARAMETERS_CAN_NOT_BE_NULL);
      this.commandService.send(device, Command.Type.SET, Command.Setting.COLOR_PARAMETERS, colorParameters);
   }

   @Override
   public ColorTemperature100K getColorTemperature100K(final Device device) {
      return device == null ? null : this.commandService.send(device, Command.Type.GET, Command.Setting.COLOR_TEMPERATURE_100K, ColorTemperature100K.class);
   }

   @Override
   public void setColorTemperature100K(final Device device, final ColorTemperature100K colorTemperature100K) {
      Assert.notNull(device, SetColorTemperature100kMessages.DEVICE_CAN_NOT_BE_NULL);
      Assert.notNull(colorTemperature100K, SetColorTemperature100kMessages.COLOR_TEMPERATURE_100K_CAN_NOT_BE_NULL);
      this.commandService.send(device, Command.Type.SET, Command.Setting.COLOR_TEMPERATURE_100K, colorTemperature100K);
   }

   @Override
   public PictureFormat getPictureFormat(final Device device) {
      return device == null ? null : this.commandService.send(device, Command.Type.GET, Command.Setting.PICTURE_FORMAT, PictureFormat.class);
   }

   @Override
   public void setPictureFormat(final Device device, final PictureFormat pictureFormat) {
      Assert.notNull(device, SetPictureFormatMessages.DEVICE_CAN_NOT_BE_NULL);
      Assert.notNull(pictureFormat, SetPictureFormatMessages.PICTURE_FORMAT_CAN_NOT_BE_NULL);
      this.commandService.send(device, Command.Type.SET, Command.Setting.PICTURE_FORMAT, pictureFormat);
   }

   @Override
   public VGAVideoParameters getVGAVideoParameters(final Device device) {
      return device == null ? null : this.commandService.send(device, Command.Type.GET, Command.Setting.VGA_VIDEO_PARAMETERS, VGAVideoParameters.class);
   }

   @Override
   public void setVGAVideoParameters(final Device device, final VGAVideoParameters vgaVideoParameters) {
      Assert.notNull(device, SetVgaVideoParametersMessages.DEVICE_CAN_NOT_BE_NULL);
      Assert.notNull(vgaVideoParameters, SetVgaVideoParametersMessages.VGA_VIDEO_PARAMETERS_CAN_NOT_BE_NULL);
      this.commandService.send(device, Command.Type.SET, Command.Setting.VGA_VIDEO_PARAMETERS, vgaVideoParameters);
   }

   @Override
   public PictureInPicture getPictureInPicture(final Device device) {
      return device == null ? null : this.commandService.send(device, Command.Type.GET, Command.Setting.PICTURE_IN_PICTURE, PictureInPicture.class);
   }

   @Override
   public void setPictureInPicture(final Device device, final PictureInPicture pictureInPicture) {
      Assert.notNull(device, SetPictureInPictureMessages.DEVICE_CAN_NOT_BE_NULL);
      Assert.notNull(pictureInPicture, SetPictureInPictureMessages.PICTURE_IN_PICTURE_CAN_NOT_BE_NULL);
      this.commandService.send(device, Command.Type.SET, Command.Setting.PICTURE_IN_PICTURE, pictureInPicture);
   }

   @Override
   public PictureInPictureSource getPictureInPictureSource(final Device device) {
      return device == null
         ? null
         : this.commandService.send(device, Command.Type.GET, Command.Setting.PICTURE_IN_PICTURE_SOURCE, PictureInPictureSource.class);
   }

   @Override
   public void setPictureInPictureSource(final Device device, final PictureInPictureSource pictureInPictureSource) {
      Assert.notNull(device, SetPictureInPictureSourceMessages.DEVICE_CAN_NOT_BE_NULL);
      Assert.notNull(pictureInPictureSource, SetPictureInPictureSourceMessages.PICTURE_IN_PICTURE_SOURCE_CAN_NOT_BE_NULL);
      this.commandService.send(device, Command.Type.SET, Command.Setting.PICTURE_IN_PICTURE_SOURCE, pictureInPictureSource);
   }

   @Override
   public PictureStyle getPictureStyle(final Device device) {
      return device == null ? null : this.commandService.send(device, Command.Type.GET, Command.Setting.PICTURE_STYLE, PictureStyle.class);
   }

   @Override
   public void setPictureStyle(final Device device, final PictureStyle pictureStyle) {
      Assert.notNull(device, SetPictureStyleMessages.DEVICE_CAN_NOT_BE_NULL);
      Assert.notNull(pictureStyle, SetPictureStyleMessages.PICTURE_STYLE_CAN_NOT_BE_NULL);
      this.commandService.send(device, Command.Type.SET, Command.Setting.PICTURE_STYLE, pictureStyle);
   }
}
