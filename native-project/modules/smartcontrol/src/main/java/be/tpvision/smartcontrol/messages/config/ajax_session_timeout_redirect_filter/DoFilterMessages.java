package be.tpvision.smartcontrol.messages.config.ajax_session_timeout_redirect_filter;

import be.tpvision.smartcontrol.messages.Messages;

public class DoFilterMessages {
   public static final String SERVLET_REQUEST_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Servlet request");
   public static final String SERVLET_RESPONSE_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Servlet response");
   public static final String FILTER_CHAIN_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Filter chain");
   public static final String SECURITY_CONTEXT_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Security context");
   public static final String AUTHENTICATION_CAN_NOT_BE_NULL = Messages.getCanNotBeNullMessage("Authentication");

   private DoFilterMessages() {
   }
}
