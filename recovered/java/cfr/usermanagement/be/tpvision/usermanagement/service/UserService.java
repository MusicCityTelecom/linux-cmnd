/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.usermanagement.service;

import be.tpvision.usermanagement.domain.OrderDirection;
import be.tpvision.usermanagement.domain.Role;
import be.tpvision.usermanagement.domain.User;
import java.util.Set;
import javax.persistence.metamodel.SingularAttribute;

public interface UserService {
    public Set<User> getUsers();

    public Set<User> getUsersOrderedBy(SingularAttribute<? super User, ?> var1, OrderDirection var2);

    public Set<User> getUsersByRole(Role var1);

    public Set<User> getUsersByRoleOrderedBy(Role var1, SingularAttribute<? super User, ?> var2, OrderDirection var3);

    public User getUser(long var1);

    public User getUser(String var1);

    public void addUser(User var1);

    public void updateUser(User var1, boolean var2);

    public void deleteUser(User var1);

    public void deleteUser(long var1);
}

