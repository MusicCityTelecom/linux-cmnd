/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.mappers.video;

import be.tpvision.smartcontrol.domain.device_settings.video.PictureInPictureSource;
import be.tpvision.smartcontrol.messages.mappers.video.picture_in_picture_source.ToPictureInPictureSourceMessages;
import be.tpvision.smartcontrol.messages.mappers.video.picture_in_picture_source.ToPictureInPictureSourceViewModelMessages;
import be.tpvision.smartcontrol.rest.view_models.video.PictureInPictureSourceViewModel;
import be.tpvision.smartcontrol.util.ValueUtilities;
import org.springframework.util.Assert;

public class PictureInPictureSourceMapper {
    private PictureInPictureSourceMapper() {
    }

    public static PictureInPictureSourceViewModel toPictureInPictureSourceViewModel(PictureInPictureSource pictureInPictureSource) {
        Assert.notNull((Object)pictureInPictureSource, ToPictureInPictureSourceViewModelMessages.PICTURE_IN_PICTURE_SOURCE_CAN_NOT_BE_NULL);
        PictureInPictureSource.SourceType pictureInPictureSourceSourceType = pictureInPictureSource.getPictureInPictureSourceSourceType();
        String sourceTypeString = pictureInPictureSourceSourceType != null ? String.valueOf((Object)pictureInPictureSourceSourceType) : null;
        PictureInPictureSource.InputSourceSourceType inputSourceSourceTypeQ2 = pictureInPictureSource.getInputSourceSourceTypeQ2();
        String inputSourceSourceTypeQ2String = inputSourceSourceTypeQ2 != null ? String.valueOf((Object)inputSourceSourceTypeQ2) : null;
        PictureInPictureSource.InputSourceSourceType inputSourceSourceTypeQ3 = pictureInPictureSource.getInputSourceSourceTypeQ3();
        String inputSourceSourceTypeQ3String = inputSourceSourceTypeQ3 != null ? String.valueOf((Object)inputSourceSourceTypeQ3) : null;
        PictureInPictureSource.InputSourceSourceType inputSourceSourceTypeQ4 = pictureInPictureSource.getInputSourceSourceTypeQ4();
        String inputSourceSourceTypeQ4String = inputSourceSourceTypeQ4 != null ? String.valueOf((Object)inputSourceSourceTypeQ4) : null;
        return new PictureInPictureSourceViewModel(sourceTypeString, inputSourceSourceTypeQ2String, inputSourceSourceTypeQ3String, inputSourceSourceTypeQ4String);
    }

    public static PictureInPictureSource toPictureInPictureSource(PictureInPictureSourceViewModel pictureInPictureSourceViewModel) {
        Assert.notNull((Object)pictureInPictureSourceViewModel, ToPictureInPictureSourceMessages.PICTURE_IN_PICTURE_SOURCE_VIEW_MODEL_CAN_NOT_BE_NULL);
        String pictureInPictureSourceSourceTypeString = pictureInPictureSourceViewModel.getPictureInPictureSourceSourceType();
        PictureInPictureSource.SourceType sourceType = pictureInPictureSourceSourceTypeString != null ? ValueUtilities.getEnumValue(PictureInPictureSource.SourceType.class, pictureInPictureSourceSourceTypeString) : null;
        String inputSourceSourceTypeQ2String = pictureInPictureSourceViewModel.getInputSourceSourceTypeQ2();
        PictureInPictureSource.InputSourceSourceType inputSourceSourceTypeQ2 = inputSourceSourceTypeQ2String != null ? ValueUtilities.getEnumValue(PictureInPictureSource.InputSourceSourceType.class, inputSourceSourceTypeQ2String) : null;
        String inputSourceSourceTypeQ3String = pictureInPictureSourceViewModel.getInputSourceSourceTypeQ3();
        PictureInPictureSource.InputSourceSourceType inputSourceSourceTypeQ3 = inputSourceSourceTypeQ3String != null ? ValueUtilities.getEnumValue(PictureInPictureSource.InputSourceSourceType.class, inputSourceSourceTypeQ3String) : null;
        String inputSourceSourceTypeQ4String = pictureInPictureSourceViewModel.getInputSourceSourceTypeQ4();
        PictureInPictureSource.InputSourceSourceType inputSourceSourceTypeQ4 = inputSourceSourceTypeQ4String != null ? ValueUtilities.getEnumValue(PictureInPictureSource.InputSourceSourceType.class, inputSourceSourceTypeQ4String) : null;
        return new PictureInPictureSource(sourceType, inputSourceSourceTypeQ2, inputSourceSourceTypeQ3, inputSourceSourceTypeQ4);
    }
}

