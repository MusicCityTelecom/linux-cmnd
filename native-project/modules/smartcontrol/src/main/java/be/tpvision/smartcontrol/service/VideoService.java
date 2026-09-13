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

public interface VideoService {
   VideoParameters getVideoParameters(Device device);

   void setVideoParameters(Device device, VideoParameters videoParameters);

   ColorTemperature getColorTemperature(Device device);

   void setColorTemperature(Device device, ColorTemperature colorTemperature);

   ColorParameters getColorParameters(Device device);

   void setColorParameters(Device device, ColorParameters colorParameters);

   ColorTemperature100K getColorTemperature100K(Device device);

   void setColorTemperature100K(Device device, ColorTemperature100K colorTemperature100K);

   PictureFormat getPictureFormat(Device device);

   void setPictureFormat(Device device, PictureFormat pictureFormat);

   VGAVideoParameters getVGAVideoParameters(Device device);

   void setVGAVideoParameters(Device device, VGAVideoParameters vgaVideoParameters);

   PictureInPicture getPictureInPicture(Device device);

   void setPictureInPicture(Device device, PictureInPicture pictureInPicture);

   PictureInPictureSource getPictureInPictureSource(Device device);

   void setPictureInPictureSource(Device device, PictureInPictureSource pictureInPictureSource);

   PictureStyle getPictureStyle(Device device);

   void setPictureStyle(Device device, PictureStyle pictureStyle);
}
