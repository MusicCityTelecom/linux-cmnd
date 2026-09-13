/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.usermanagement.service;

import be.tpvision.usermanagement.domain.OrderDirection;
import be.tpvision.usermanagement.domain.Role;
import be.tpvision.usermanagement.domain.User;
import be.tpvision.usermanagement.repository.UserRepository;
import be.tpvision.usermanagement.service.UserService;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.persistence.metamodel.SingularAttribute;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import util.MessageUtilities;

@Service
@Transactional
public class UserServiceImpl
implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        Assert.notNull((Object)userRepository, MessageUtilities.USER_REPOSITORY_NOT_NULL_MESSAGE);
        this.userRepository = userRepository;
    }

    private void assertUserRepositoryStateNotNull() {
        Assert.state(this.userRepository != null, MessageUtilities.USER_REPOSITORY_NOT_NULL_MESSAGE);
    }

    @Override
    public Set<User> getUsers() {
        this.assertUserRepositoryStateNotNull();
        List userList = this.userRepository.getAll();
        Objects.requireNonNull(userList, MessageUtilities.USER_LIST_NOT_NULL_MESSAGE);
        return new HashSet<User>(userList);
    }

    @Override
    public Set<User> getUsersOrderedBy(SingularAttribute<? super User, ?> singularAttribute, OrderDirection orderDirection) {
        this.assertUserRepositoryStateNotNull();
        Assert.notNull(singularAttribute, MessageUtilities.SINGULAR_ATTRIBUTE_NOT_NULL_MESSAGE);
        Assert.notNull((Object)orderDirection, MessageUtilities.ORDER_DIRECTION_NOT_NULL_MESSAGE);
        List<User> userList = this.userRepository.getAllOrderedBy(singularAttribute, orderDirection);
        return new LinkedHashSet<User>(userList);
    }

    @Override
    public Set<User> getUsersByRole(Role role) {
        this.assertUserRepositoryStateNotNull();
        Assert.notNull((Object)role, MessageUtilities.USER_ROLE_NOT_NULL_MESSAGE);
        List<User> userList = this.userRepository.getAllByRole(role);
        return new HashSet<User>(userList);
    }

    @Override
    public Set<User> getUsersByRoleOrderedBy(Role role, SingularAttribute<? super User, ?> singularAttribute, OrderDirection orderDirection) {
        this.assertUserRepositoryStateNotNull();
        Assert.notNull((Object)role, MessageUtilities.USER_ROLE_NOT_NULL_MESSAGE);
        Assert.notNull(singularAttribute, MessageUtilities.SINGULAR_ATTRIBUTE_NOT_NULL_MESSAGE);
        Assert.notNull((Object)orderDirection, MessageUtilities.ORDER_DIRECTION_NOT_NULL_MESSAGE);
        List<User> userList = this.userRepository.getAllByRoleOrderedBy(role, singularAttribute, orderDirection);
        return new LinkedHashSet<User>(userList);
    }

    @Override
    public User getUser(long userId) {
        this.assertUserRepositoryStateNotNull();
        User user = (User)this.userRepository.getById(userId);
        String noUserFoundMessage = MessageUtilities.getNoUserFoundWithIdMessage(userId);
        Assert.notNull((Object)user, noUserFoundMessage);
        return user;
    }

    @Override
    public User getUser(String username) {
        this.assertUserRepositoryStateNotNull();
        Assert.notNull((Object)username, MessageUtilities.USER_USERNAME_NOT_NULL_MESSAGE);
        Optional<User> user = this.userRepository.getByUsername(username);
        String noUserFoundMessage = MessageUtilities.getNoUserFoundWithUsernameMessage(username);
        return user.orElseThrow(() -> new IllegalArgumentException(noUserFoundMessage));
    }

    @Override
    public void addUser(User user) {
        this.assertUserRepositoryStateNotNull();
        Assert.notNull((Object)user, MessageUtilities.USER_NOT_NULL_MESSAGE);
        String password = user.getPassword();
        Assert.state(password != null, MessageUtilities.USER_PASSWORD_NOT_NULL_MESSAGE);
        String encodedPassword = DigestUtils.md5Hex(password);
        Objects.requireNonNull(encodedPassword, MessageUtilities.USER_ENCODED_PASSWORD_NOT_NULL_MESSAGE);
        user.setPassword(encodedPassword);
        this.userRepository.persist(user);
    }

    @Override
    public void updateUser(User user, boolean encodePassword) {
        this.assertUserRepositoryStateNotNull();
        Assert.notNull((Object)user, MessageUtilities.USER_NOT_NULL_MESSAGE);
        if (encodePassword) {
            String password = user.getPassword();
            Assert.state(password != null, MessageUtilities.USER_PASSWORD_NOT_NULL_MESSAGE);
            String encodedPassword = DigestUtils.md5Hex(password);
            Objects.requireNonNull(encodedPassword, MessageUtilities.USER_ENCODED_PASSWORD_NOT_NULL_MESSAGE);
            user.setPassword(encodedPassword);
        }
        this.userRepository.merge(user);
    }

    @Override
    public void deleteUser(User user) {
        this.assertUserRepositoryStateNotNull();
        Assert.notNull((Object)user, MessageUtilities.USER_NOT_NULL_MESSAGE);
        this.userRepository.delete(user);
    }

    @Override
    public void deleteUser(long userId) {
        this.assertUserRepositoryStateNotNull();
        this.userRepository.deleteById(userId);
    }
}

