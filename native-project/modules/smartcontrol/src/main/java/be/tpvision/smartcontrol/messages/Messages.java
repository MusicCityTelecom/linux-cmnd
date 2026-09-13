package be.tpvision.smartcontrol.messages;

import org.springframework.util.Assert;

public class Messages {
   private Messages() {
   }

   public static String getCanNotBeNullMessage(final String input) {
      String canNotBeNullMessage = " can not be null.";
      String inputCanNotBeNullMessage = "Input can not be null.";
      Assert.notNull(input, "Input can not be null.");
      Assert.isTrue(!input.isEmpty(), "Input can not be empty.");
      return input + " can not be null.";
   }

   public static String getCanNotBeEmptyMessage(final String input) {
      String inputCanNotBeNullMessage = getCanNotBeNullMessage("Input");
      Assert.notNull(input, inputCanNotBeNullMessage);
      String canNotBeEmptyMessage = " can not be empty.";
      String inputCanNotBeEmptyMessage = "Input can not be empty.";
      Assert.isTrue(!input.isEmpty(), "Input can not be empty.");
      return input + " can not be empty.";
   }

   public static void assertIsNotNullOrEmpty(final String input) {
      String inputCanNotBeNullMessage = getCanNotBeNullMessage("Input");
      Assert.notNull(input, inputCanNotBeNullMessage);
      String inputCanNotBeEmptyMessage = getCanNotBeEmptyMessage(input);
      Assert.isTrue(!input.isEmpty(), inputCanNotBeEmptyMessage);
   }

   public static String getConverterCanNotBeNullMessage(final String input) {
      assertIsNotNullOrEmpty(input);
      String converterCanNotBeNullMessage = getCanNotBeNullMessage(" converter");
      return input + converterCanNotBeNullMessage;
   }

   public static String getRepositoryCanNotBeNullMessage(final String input) {
      assertIsNotNullOrEmpty(input);
      String repositoryCanNotBeNullMessage = getCanNotBeNullMessage(" repository");
      return input + repositoryCanNotBeNullMessage;
   }

   public static String getServiceCanNotBeNullMessage(final String input) {
      assertIsNotNullOrEmpty(input);
      String serviceCanNotBeNullMessage = getCanNotBeNullMessage(" service");
      return input + serviceCanNotBeNullMessage;
   }

   public static String getCodecCanNotBeNullMessage(final String input) {
      assertIsNotNullOrEmpty(input);
      String codecCanNotBeNullMessage = getCanNotBeNullMessage(" codec");
      return input + codecCanNotBeNullMessage;
   }

   public static String getViewModelCanNotBeNullMessage(final String input) {
      assertIsNotNullOrEmpty(input);
      String viewModelNotNullMessage = getCanNotBeNullMessage(" view model");
      return input + viewModelNotNullMessage;
   }

   public static String getHasToBePositiveNumberMessage(final String input) {
      assertIsNotNullOrEmpty(input);
      return input + " has to be a positive number.";
   }

   public static String getCanNotContainNullValuesMessage(final String input) {
      assertIsNotNullOrEmpty(input);
      return input + " can not contain null values.";
   }

   private static String getIsNotSupportedForThisSicpVersionMessage(final String input) {
      assertIsNotNullOrEmpty(input);
      return input + " is not supported for this SICP version.";
   }

   public static String getCodecToProtocolIsNotSupportedForThisSicpVersionMessage(final String input) {
      assertIsNotNullOrEmpty(input);
      String notSupportedForThisSicpVersionMessage = getIsNotSupportedForThisSicpVersionMessage(" codec toProtocol()");
      return input + notSupportedForThisSicpVersionMessage;
   }

   public static String getCodecToDomainIsNotSupportedForThisSicpVersionMessage(final String input) {
      assertIsNotNullOrEmpty(input);
      String notSupportedForThisSicpVersionMessage = getIsNotSupportedForThisSicpVersionMessage(" codec toDomain()");
      return input + notSupportedForThisSicpVersionMessage;
   }

   public static String getHasToBeWithinByteRangeMessage(final String input) {
      assertIsNotNullOrEmpty(input);
      return input.concat(" has to be within byte range.");
   }
}
