/*
 * Decompiled with CFR 0.152.
 */
package util;

public class MessageUtilities {
    public static final String USER_NOT_NULL_MESSAGE = MessageUtilities.getNotNullMessage("User");
    public static final String USER_USERNAME_NOT_NULL_MESSAGE = MessageUtilities.getNotNullMessage("Username");
    public static final String USER_PASSWORD_NOT_NULL_MESSAGE = MessageUtilities.getNotNullMessage("Password");
    public static final String USER_ENCODED_PASSWORD_NOT_NULL_MESSAGE = MessageUtilities.getNotNullMessage("Encoded password");
    public static final String USER_ROLE_NOT_NULL_MESSAGE = MessageUtilities.getNotNullMessage("Role");
    public static final String CREATE_OR_UPDATE_USER_VIEW_MODEL_NOT_NULL_MESSAGE = MessageUtilities.getNotNullMessage("Create or update user view model");
    public static final String USER_VIEW_MODEL_NOT_NULL_MESSAGE = MessageUtilities.getNotNullMessage("User view model");
    public static final String USER_LIST_NOT_NULL_MESSAGE = MessageUtilities.getNotNullMessage("User list");
    public static final String USER_SET_NOT_NULL_MESSAGE = MessageUtilities.getNotNullMessage("User set");
    public static final String USER_COLLECTION_NOT_NULL_MESSAGE = MessageUtilities.getNotNullMessage("User collection");
    public static final String CREATE_OR_UPDATE_USER_VIEW_MODEL_COLLECTION_NOT_NULL_MESSAGE = MessageUtilities.getNotNullMessage("Create or update user view model collection");
    public static final String ABSTRACT_REPOSITORY_ENTITY_CLASS_NOT_NULL_MESSAGE = MessageUtilities.getNotNullMessage("Entity class");
    public static final String ABSTRACT_REPOSITORY_ID_NOT_NULL_MESSAGE = MessageUtilities.getNotNullMessage("Id");
    public static final String ABSTRACT_REPOSITORY_ENTITY_NOT_NULL_MESSAGE = MessageUtilities.getNotNullMessage("Id");
    public static final String QUERY_NOT_NULL_MESSAGE = MessageUtilities.getNotNullMessage("Query");
    public static final String SINGULAR_ATTRIBUTE_NOT_NULL_MESSAGE = MessageUtilities.getNotNullMessage("Singular attribute");
    public static final String ORDER_DIRECTION_NOT_NULL_MESSAGE = MessageUtilities.getNotNullMessage("Order direction");
    public static final String ENTITY_MANAGER_NOT_NULL_MESSAGE = MessageUtilities.getNotNullMessage("Entity manager");
    public static final String CRITERIA_BUILDER_NOT_NULL_MESSAGE = MessageUtilities.getNotNullMessage("Criteria builder");
    public static final String CRITERIA_QUERY_NOT_NULL_MESSAGE = MessageUtilities.getNotNullMessage("Criteria query");
    public static final String ROOT_NOT_NULL_MESSAGE = MessageUtilities.getNotNullMessage("Root");
    public static final String EXPRESSION_NOT_NULL_MESSAGE = MessageUtilities.getNotNullMessage("Expression");
    public static final String ORDER_NOT_NULL_MESSAGE = MessageUtilities.getNotNullMessage("Order");
    public static final String PREDICATE_NOT_NULL_MESSAGE = MessageUtilities.getNotNullMessage("Predicate");
    public static final String USER_REPOSITORY_NOT_NULL_MESSAGE = MessageUtilities.getNotNullMessage("User repository");
    public static final String USER_SERVICE_NOT_NULL_MESSAGE = MessageUtilities.getNotNullMessage("User service");
    public static final String SECURITY_CONTEXT_NOT_NULL_MESSAGE = MessageUtilities.getNotNullMessage("Security context");
    public static final String CAS_AUTHENTICATION_TOKEN_NOT_NULL_MESSAGE = MessageUtilities.getNotNullMessage("CAS authentication token");
    public static final String ASSERTION_NOT_NULL_MESSAGE = MessageUtilities.getNotNullMessage("Assertion");
    public static final String ATTRIBUTE_PRINCIPAL_NOT_NULL_MESSAGE = MessageUtilities.getNotNullMessage("Attribute principal");
    public static final String ENUM_CLASS_NOT_NULL_MESSAGE = MessageUtilities.getNotNullMessage("Enum class");
    public static final String CURRENT_USER_NOT_NULL_MESSAGE = MessageUtilities.getNotNullMessage("Current user");
    public static final String CURRENT_USER_USERNAME_NOT_NULL_MESSAGE = MessageUtilities.getNotNullMessage("Current user username");
    public static final String ONLY_ADMIN_OR_USER_SHOULD_BE_AUTHORIZED = "Only admin or user should be authorized.";
    public static final String NOT_AUTHORIZED_MESSAGE = "You're not authorized to perform this action.";

    public static String getNotNullMessage(String input) {
        return input.concat(" can't be null.");
    }

    public static String getNoNullValuesMessage(String input) {
        return input.concat(" can't contain null values.");
    }

    public static String getNoUserFoundWithIdMessage(long id) {
        return String.format("No user found with id %d.", id);
    }

    public static String getNoUserFoundWithUsernameMessage(String username) {
        return String.format("No user found with username %s.", username);
    }

    public static String getMinimumNumberOfAdminsMessage(int minimumNumberOfAdmins) {
        return String.format("The minimum number of admins is %d. Make someone else admin before changing this admin's role.", minimumNumberOfAdmins);
    }
}

