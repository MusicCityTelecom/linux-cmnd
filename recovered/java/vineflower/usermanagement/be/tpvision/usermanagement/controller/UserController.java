package be.tpvision.usermanagement.controller;

import be.tpvision.usermanagement.domain.OrderDirection;
import be.tpvision.usermanagement.domain.Role;
import be.tpvision.usermanagement.domain.User;
import be.tpvision.usermanagement.domain.User_;
import be.tpvision.usermanagement.mapper.UserMapper;
import be.tpvision.usermanagement.service.UserService;
import be.tpvision.usermanagement.viewModel.CreateOrUpdateUserViewModel;
import be.tpvision.usermanagement.viewModel.UpdateUserPasswordViewModel;
import be.tpvision.usermanagement.viewModel.UserViewModel;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.metamodel.SingularAttribute;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import util.MessageUtilities;

@RestController
@RequestMapping("/users")
public class UserController {
   private static final int MINIMUM_NUMBER_OF_ADMINS = 1;
   private final UserService userService;

   @Autowired
   public UserController(final UserService userService) {
      Assert.notNull(userService, MessageUtilities.USER_SERVICE_NOT_NULL_MESSAGE);
      this.userService = userService;
   }

   private void assertUserServiceStateNotNull() {
      Assert.state(this.userService != null, MessageUtilities.USER_SERVICE_NOT_NULL_MESSAGE);
   }

   private void assertUserWithIdNotNull(final User user, long userId) {
      String noUserFoundMessage = MessageUtilities.getNoUserFoundWithIdMessage(userId);
      Assert.notNull(user, noUserFoundMessage);
   }

   private void assertUserWithUsernameNotNull(final User user, final String username) {
      Assert.notNull(username, MessageUtilities.USER_USERNAME_NOT_NULL_MESSAGE);
      String noUserFoundMessage = MessageUtilities.getNoUserFoundWithUsernameMessage(username);
      Assert.notNull(user, noUserFoundMessage);
   }

   private boolean isNullOrEmpty(final String string) {
      return string == null || string.isEmpty();
   }

   private SingularAttribute<? super User, ?> getSingularAttribute(final String attribute) {
      Assert.notNull(attribute, MessageUtilities.SINGULAR_ATTRIBUTE_NOT_NULL_MESSAGE);
      switch (attribute) {
         case "id":
            return User_.id;
         case "username":
            return User_.username;
         case "role":
            return User_.role;
         default:
            String orderByNotSupportedMessage = String.format("Order by %s not supported.", attribute);
            throw new IllegalArgumentException(orderByNotSupportedMessage);
      }
   }

   private String getCurrentUserUsername() {
      SecurityContext securityContext = SecurityContextHolder.getContext();
      Assert.state(securityContext != null, MessageUtilities.SECURITY_CONTEXT_NOT_NULL_MESSAGE);
      CasAuthenticationToken casAuthenticationToken = (CasAuthenticationToken)securityContext.getAuthentication();
      Assert.state(casAuthenticationToken != null, MessageUtilities.CAS_AUTHENTICATION_TOKEN_NOT_NULL_MESSAGE);
      Assertion assertion = casAuthenticationToken.getAssertion();
      Assert.state(assertion != null, MessageUtilities.ASSERTION_NOT_NULL_MESSAGE);
      AttributePrincipal attributePrincipal = assertion.getPrincipal();
      Assert.state(attributePrincipal != null, MessageUtilities.ATTRIBUTE_PRINCIPAL_NOT_NULL_MESSAGE);
      return attributePrincipal.getName();
   }

   private User getCurrentUser() {
      this.assertUserServiceStateNotNull();
      String username = this.getCurrentUserUsername();
      Assert.state(username != null, MessageUtilities.CURRENT_USER_USERNAME_NOT_NULL_MESSAGE);
      User user = this.userService.getUser(username);
      this.assertUserWithUsernameNotNull(user, username);
      return user;
   }

   private Role getCurrentUserRole() {
      User currentUser = this.getCurrentUser();
      Assert.state(currentUser != null, MessageUtilities.CURRENT_USER_NOT_NULL_MESSAGE);
      Role role = currentUser.getRole();
      Assert.state(role != null, MessageUtilities.USER_ROLE_NOT_NULL_MESSAGE);
      return role;
   }

   private boolean isCurrentUserAdmin() {
      Role currentUserRole = this.getCurrentUserRole();
      Assert.state(currentUserRole != null, MessageUtilities.USER_ROLE_NOT_NULL_MESSAGE);
      return currentUserRole.equals(Role.ADMIN);
   }

   private boolean isCurrentUser(final User user) {
      this.assertUserServiceStateNotNull();
      Assert.notNull(user, MessageUtilities.USER_NOT_NULL_MESSAGE);
      User currentUser = this.getCurrentUser();
      Assert.state(currentUser != null, MessageUtilities.CURRENT_USER_NOT_NULL_MESSAGE);
      return currentUser.equals(user);
   }

   private void accessDeniedException() {
      throw new AccessDeniedException("You're not authorized to perform this action.");
   }

   private void validateCurrentUserIsAdmin() {
      if (!this.isCurrentUserAdmin()) {
         this.accessDeniedException();
      }
   }

   private void validateMoreThanMinimumNumberOfAdmins() {
      Set<User> users = this.userService.getUsers();
      Objects.requireNonNull(users, MessageUtilities.USER_SET_NOT_NULL_MESSAGE);
      long numberOfAdmins = users.stream().filter(user -> Role.ADMIN.equals(user.getRole())).count();
      String minimumNumberOfAdminsMessage = MessageUtilities.getMinimumNumberOfAdminsMessage(1);
      Assert.state(numberOfAdmins > 1L, minimumNumberOfAdminsMessage);
   }

   private void validateCurrentUserIsAdminOrUserIsCurrentUser(final User user) {
      Assert.notNull(user, MessageUtilities.USER_NOT_NULL_MESSAGE);
      boolean isAuthorized = this.isCurrentUserAdmin() || this.isCurrentUser(user);
      if (!isAuthorized) {
         this.accessDeniedException();
      }
   }

   @RequestMapping(method = RequestMethod.GET)
   public List<UserViewModel> getUsersOrderedBy(
      @RequestParam(value = "orderBy", required = false) final String orderBy,
      @RequestParam(value = "orderDirection", required = false) final String orderDirection
   ) {
      this.assertUserServiceStateNotNull();
      Role currentUserRole = this.getCurrentUserRole();
      Assert.state(currentUserRole != null, MessageUtilities.USER_ROLE_NOT_NULL_MESSAGE);
      Set<User> userSet;
      if (this.isNullOrEmpty(orderBy)) {
         userSet = this.userService.getUsers();
      } else if (this.isNullOrEmpty(orderDirection)) {
         SingularAttribute<? super User, ?> singularAttribute = this.getSingularAttribute(orderBy);
         Objects.requireNonNull(singularAttribute, MessageUtilities.SINGULAR_ATTRIBUTE_NOT_NULL_MESSAGE);
         userSet = this.userService.getUsersOrderedBy(singularAttribute, OrderDirection.ASC);
      } else {
         String orderDirectionUppercase = orderDirection.toUpperCase();
         OrderDirection orderDirectionEnum = Enum.valueOf(OrderDirection.class, orderDirectionUppercase);
         SingularAttribute<? super User, ?> singularAttribute = this.getSingularAttribute(orderBy);
         Objects.requireNonNull(singularAttribute, MessageUtilities.SINGULAR_ATTRIBUTE_NOT_NULL_MESSAGE);
         userSet = this.userService.getUsersOrderedBy(singularAttribute, orderDirectionEnum);
      }

      Objects.requireNonNull(userSet, MessageUtilities.USER_SET_NOT_NULL_MESSAGE);
      return UserMapper.toUserViewModelList(userSet);
   }

   @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
   public UserViewModel getUser(@PathVariable("userId") final long userId) {
      this.assertUserServiceStateNotNull();
      User user = this.userService.getUser(userId);
      this.assertUserWithIdNotNull(user, userId);
      this.validateCurrentUserIsAdminOrUserIsCurrentUser(user);
      return UserMapper.toUserViewModel(user);
   }

   @RequestMapping(value = "/find/{username}", method = RequestMethod.GET)
   public UserViewModel getUser(@PathVariable("username") final String username) {
      this.assertUserServiceStateNotNull();
      User user = this.userService.getUser(username);
      this.assertUserWithUsernameNotNull(user, username);
      this.validateCurrentUserIsAdminOrUserIsCurrentUser(user);
      return UserMapper.toUserViewModel(user);
   }

   @RequestMapping(method = RequestMethod.POST)
   public void addUser(@RequestBody final CreateOrUpdateUserViewModel createOrUpdateUserViewModel) {
      this.assertUserServiceStateNotNull();
      this.validateCurrentUserIsAdmin();
      User user = UserMapper.toUser(createOrUpdateUserViewModel);
      Objects.requireNonNull(user, MessageUtilities.USER_NOT_NULL_MESSAGE);
      this.userService.addUser(user);
   }

   @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
   public void updateUser(@PathVariable("userId") final long userId, @RequestBody final CreateOrUpdateUserViewModel createOrUpdateUserViewModel) {
      this.assertUserServiceStateNotNull();
      this.validateCurrentUserIsAdmin();
      User userToEdit = this.userService.getUser(userId);
      this.assertUserWithIdNotNull(userToEdit, userId);
      String createOrUpdateUserViewModelRoleString = createOrUpdateUserViewModel.getRole();
      Assert.state(createOrUpdateUserViewModelRoleString != null, MessageUtilities.USER_ROLE_NOT_NULL_MESSAGE);
      Role userToEditRole = userToEdit.getRole();
      Role createOrUpdateUserViewModelRole = Enum.valueOf(Role.class, createOrUpdateUserViewModelRoleString);
      if (userToEditRole.equals(Role.ADMIN) && !createOrUpdateUserViewModelRole.equals(Role.ADMIN)) {
         this.validateMoreThanMinimumNumberOfAdmins();
      }

      User user = UserMapper.toUser(createOrUpdateUserViewModel);
      Objects.requireNonNull(user, MessageUtilities.USER_NOT_NULL_MESSAGE);
      user.setId(userId);
      this.userService.updateUser(user, true);
   }

   @RequestMapping(value = "/{userId}/username", method = RequestMethod.GET)
   public ResponseWrapper<String> getUserUsername(@PathVariable("userId") final long userId) {
      this.assertUserServiceStateNotNull();
      User user = this.userService.getUser(userId);
      this.assertUserWithIdNotNull(user, userId);
      this.validateCurrentUserIsAdminOrUserIsCurrentUser(user);
      String username = user.getUsername();
      return new ResponseWrapper<>(username);
   }

   @RequestMapping(value = "/{userId}/username/{username}", method = RequestMethod.PUT)
   public void setUserUsername(@PathVariable("userId") final long userId, @PathVariable("username") final String username) {
      this.assertUserServiceStateNotNull();
      this.validateCurrentUserIsAdmin();
      User user = this.userService.getUser(userId);
      this.assertUserWithIdNotNull(user, userId);
      user.setUsername(username);
      this.userService.updateUser(user, false);
   }

   @RequestMapping(value = "/{userId}/password", method = RequestMethod.PUT)
   public void updateUserPassword(@PathVariable("userId") final long userId, @RequestBody final UpdateUserPasswordViewModel updateUserPasswordViewModel) {
      this.assertUserServiceStateNotNull();
      User user = this.userService.getUser(userId);
      this.assertUserWithIdNotNull(user, userId);
      this.validateCurrentUserIsAdminOrUserIsCurrentUser(user);
      String password = updateUserPasswordViewModel.getPassword();
      Objects.requireNonNull(password, MessageUtilities.USER_PASSWORD_NOT_NULL_MESSAGE);
      user.setPassword(password);
      this.userService.updateUser(user, true);
   }

   @RequestMapping(value = "/{userId}/role", method = RequestMethod.GET)
   public ResponseWrapper<String> getUserRole(@PathVariable("userId") final long userId) {
      this.assertUserServiceStateNotNull();
      User user = this.userService.getUser(userId);
      this.assertUserWithIdNotNull(user, userId);
      this.validateCurrentUserIsAdminOrUserIsCurrentUser(user);
      Role role = user.getRole();
      String roleString = String.valueOf(role);
      return new ResponseWrapper<>(roleString);
   }

   @RequestMapping(value = "/{userId}/role/{role}", method = RequestMethod.PUT)
   public void updateUserRole(@PathVariable("userId") final long userId, @PathVariable("role") final String role) {
      this.assertUserServiceStateNotNull();
      this.validateCurrentUserIsAdmin();
      User user = this.userService.getUser(userId);
      this.assertUserWithIdNotNull(user, userId);
      Role currentRole = user.getRole();
      Assert.state(currentRole != null, MessageUtilities.USER_ROLE_NOT_NULL_MESSAGE);
      if (currentRole.equals(Role.ADMIN)) {
         this.validateMoreThanMinimumNumberOfAdmins();
      }

      String roleUpperCase = role.toUpperCase();
      Role newRole = Enum.valueOf(Role.class, roleUpperCase);
      user.setRole(newRole);
      this.userService.updateUser(user, false);
   }

   @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
   public void deleteUser(@PathVariable("userId") final long userId) {
      this.assertUserServiceStateNotNull();
      this.validateCurrentUserIsAdmin();
      this.userService.deleteUser(userId);
   }
}
