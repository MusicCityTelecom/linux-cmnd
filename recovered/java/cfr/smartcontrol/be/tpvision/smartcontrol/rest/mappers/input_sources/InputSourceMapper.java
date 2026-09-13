/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.mappers.input_sources;

import be.tpvision.smartcontrol.domain.device_settings.input_sources.InputSource;
import be.tpvision.smartcontrol.messages.mappers.input_sources.input_source.ToInputSourceMessages;
import be.tpvision.smartcontrol.messages.mappers.input_sources.input_source.ToInputSourceViewModelMessages;
import be.tpvision.smartcontrol.rest.view_models.input_sources.InputSourceViewModel;
import be.tpvision.smartcontrol.util.ValueUtilities;
import org.springframework.util.Assert;

public class InputSourceMapper {
    private InputSourceMapper() {
    }

    public static InputSourceViewModel toInputSourceViewModel(InputSource inputSource) {
        Assert.notNull((Object)inputSource, ToInputSourceViewModelMessages.INPUT_SOURCE_CAN_NOT_BE_NULL);
        InputSource.SourceType sourceType = inputSource.getSourceType();
        String sourceTypeString = String.valueOf((Object)sourceType);
        InputSource.Tag tag = inputSource.getTag();
        String tagString = null;
        if (tag != null) {
            tagString = String.valueOf((Object)tag);
        }
        InputSource.SourceLabel sourceLabel = inputSource.getSourceLabel();
        String sourceLabelString = String.valueOf((Object)sourceLabel);
        return new InputSourceViewModel(sourceTypeString, tagString, sourceLabelString);
    }

    public static InputSource toInputSource(InputSourceViewModel inputSourceViewModel) {
        Assert.notNull((Object)inputSourceViewModel, ToInputSourceMessages.INPUT_SOURCE_VIEW_MODEL_CAN_NOT_BE_NULL);
        String sourceTypeString = inputSourceViewModel.getSourceType();
        InputSource.SourceType sourceType = ValueUtilities.getEnumValue(InputSource.SourceType.class, sourceTypeString);
        String tagString = inputSourceViewModel.getTag();
        InputSource.Tag tag = null;
        if (tagString != null) {
            tag = ValueUtilities.getEnumValue(InputSource.Tag.class, tagString);
        }
        String sourceLabelString = inputSourceViewModel.getSourceLabel();
        InputSource.SourceLabel sourceLabel = ValueUtilities.getEnumValue(InputSource.SourceLabel.class, sourceLabelString);
        return new InputSource(sourceType, tag, sourceLabel);
    }
}

