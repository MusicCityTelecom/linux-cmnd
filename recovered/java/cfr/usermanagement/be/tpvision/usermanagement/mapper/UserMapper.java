/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.usermanagement.mapper;

import be.tpvision.usermanagement.domain.Role;
import be.tpvision.usermanagement.domain.User;
import be.tpvision.usermanagement.viewModel.CreateOrUpdateUserViewModel;
import be.tpvision.usermanagement.viewModel.UserViewModel;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.Assert;
import util.MessageUtilities;

public class UserMapper {
    public static User toUser(CreateOrUpdateUserViewModel createOrUpdateUserViewModel) {
        Assert.notNull((Object)createOrUpdateUserViewModel, MessageUtilities.CREATE_OR_UPDATE_USER_VIEW_MODEL_NOT_NULL_MESSAGE);
        String username = createOrUpdateUserViewModel.getUsername();
        Assert.state(username != null, MessageUtilities.USER_USERNAME_NOT_NULL_MESSAGE);
        String password = createOrUpdateUserViewModel.getPassword();
        Assert.state(password != null, MessageUtilities.USER_PASSWORD_NOT_NULL_MESSAGE);
        String roleString = createOrUpdateUserViewModel.getRole();
        Assert.state(roleString != null, MessageUtilities.USER_ROLE_NOT_NULL_MESSAGE);
        String roleStringUpperCase = roleString.toUpperCase();
        Role role = Enum.valueOf(Role.class, roleStringUpperCase);
        return new User(username, password, role);
    }

    public static List<User> toUserList(Collection<CreateOrUpdateUserViewModel> createOrUpdateUserViewModelCollection) {
        Assert.notNull(createOrUpdateUserViewModelCollection, MessageUtilities.CREATE_OR_UPDATE_USER_VIEW_MODEL_COLLECTION_NOT_NULL_MESSAGE);
        return createOrUpdateUserViewModelCollection.stream().filter(createOrUpdateUserViewModel -> createOrUpdateUserViewModel != null).map(UserMapper::toUser).filter(user -> user != null).collect(Collectors.toList());
    }

    public static UserViewModel toUserViewModel(User user) {
        Assert.notNull((Object)user, MessageUtilities.USER_NOT_NULL_MESSAGE);
        UserViewModel userViewModel = new UserViewModel();
        Long id = user.getId();
        userViewModel.setId(id);
        String username = user.getUsername();
        Assert.state(username != null, MessageUtilities.USER_USERNAME_NOT_NULL_MESSAGE);
        userViewModel.setUsername(username);
        Role role = user.getRole();
        Assert.state(role != null, MessageUtilities.USER_ROLE_NOT_NULL_MESSAGE);
        String roleString = String.valueOf((Object)role);
        userViewModel.setRole(roleString);
        String passwordString = user.getPassword();
        userViewModel.setPassword(passwordString);
        return userViewModel;
    }

    public static List<UserViewModel> toUserViewModelList(Collection<User> userCollection) {
        Assert.notNull(userCollection, MessageUtilities.USER_COLLECTION_NOT_NULL_MESSAGE);
        return userCollection.stream().filter(user -> user != null).map(UserMapper::toUserViewModel).filter(userViewModel -> userViewModel != null).collect(Collectors.toList());
    }
}

