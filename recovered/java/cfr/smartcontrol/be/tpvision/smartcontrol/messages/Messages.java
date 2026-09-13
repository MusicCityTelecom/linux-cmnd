/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.messages;

import org.springframework.util.Assert;

public class Messages {
    private Messages() {
    }

    public static String getCanNotBeNullMessage(String input) {
        String canNotBeNullMessage = " can not be null.";
        String inputCanNotBeNullMessage = "Input can not be null.";
        Assert.notNull((Object)input, "Input can not be null.");
        Assert.isTrue(!input.isEmpty(), "Input can not be empty.");
        return input + " can not be null.";
    }

    public static String getCanNotBeEmptyMessage(String input) {
        String inputCanNotBeNullMessage = Messages.getCanNotBeNullMessage("Input");
        Assert.notNull((Object)input, inputCanNotBeNullMessage);
        String canNotBeEmptyMessage = " can not be empty.";
        String inputCanNotBeEmptyMessage = "Input can not be empty.";
        Assert.isTrue(!input.isEmpty(), "Input can not be empty.");
        return input + " can not be empty.";
    }

    public static void assertIsNotNullOrEmpty(String input) {
        String inputCanNotBeNullMessage = Messages.getCanNotBeNullMessage("Input");
        Assert.notNull((Object)input, inputCanNotBeNullMessage);
        String inputCanNotBeEmptyMessage = Messages.getCanNotBeEmptyMessage(input);
        Assert.isTrue(!input.isEmpty(), inputCanNotBeEmptyMessage);
    }

    public static String getConverterCanNotBeNullMessage(String input) {
        Messages.assertIsNotNullOrEmpty(input);
        String converterCanNotBeNullMessage = Messages.getCanNotBeNullMessage(" converter");
        return input + converterCanNotBeNullMessage;
    }

    public static String getRepositoryCanNotBeNullMessage(String input) {
        Messages.assertIsNotNullOrEmpty(input);
        String repositoryCanNotBeNullMessage = Messages.getCanNotBeNullMessage(" repository");
        return input + repositoryCanNotBeNullMessage;
    }

    public static String getServiceCanNotBeNullMessage(String input) {
        Messages.assertIsNotNullOrEmpty(input);
        String serviceCanNotBeNullMessage = Messages.getCanNotBeNullMessage(" service");
        return input + serviceCanNotBeNullMessage;
    }

    public static String getCodecCanNotBeNullMessage(String input) {
        Messages.assertIsNotNullOrEmpty(input);
        String codecCanNotBeNullMessage = Messages.getCanNotBeNullMessage(" codec");
        return input + codecCanNotBeNullMessage;
    }

    public static String getViewModelCanNotBeNullMessage(String input) {
        Messages.assertIsNotNullOrEmpty(input);
        String viewModelNotNullMessage = Messages.getCanNotBeNullMessage(" view model");
        return input + viewModelNotNullMessage;
    }

    public static String getHasToBePositiveNumberMessage(String input) {
        Messages.assertIsNotNullOrEmpty(input);
        return input + " has to be a positive number.";
    }

    public static String getCanNotContainNullValuesMessage(String input) {
        Messages.assertIsNotNullOrEmpty(input);
        return input + " can not contain null values.";
    }

    private static String getIsNotSupportedForThisSicpVersionMessage(String input) {
        Messages.assertIsNotNullOrEmpty(input);
        return input + " is not supported for this SICP version.";
    }

    public static String getCodecToProtocolIsNotSupportedForThisSicpVersionMessage(String input) {
        Messages.assertIsNotNullOrEmpty(input);
        String notSupportedForThisSicpVersionMessage = Messages.getIsNotSupportedForThisSicpVersionMessage(" codec toProtocol()");
        return input + notSupportedForThisSicpVersionMessage;
    }

    public static String getCodecToDomainIsNotSupportedForThisSicpVersionMessage(String input) {
        Messages.assertIsNotNullOrEmpty(input);
        String notSupportedForThisSicpVersionMessage = Messages.getIsNotSupportedForThisSicpVersionMessage(" codec toDomain()");
        return input + notSupportedForThisSicpVersionMessage;
    }

    public static String getHasToBeWithinByteRangeMessage(String input) {
        Messages.assertIsNotNullOrEmpty(input);
        return input.concat(" has to be within byte range.");
    }
}

