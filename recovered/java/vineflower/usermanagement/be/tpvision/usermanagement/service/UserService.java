package be.tpvision.usermanagement.service;

import be.tpvision.usermanagement.domain.OrderDirection;
import be.tpvision.usermanagement.domain.Role;
import be.tpvision.usermanagement.domain.User;
import java.util.Set;
import javax.persistence.metamodel.SingularAttribute;

public interface UserService {
   Set<User> getUsers();

   Set<User> getUsersOrderedBy(SingularAttribute<? super User, ?> singularAttribute, OrderDirection orderDirection);

   Set<User> getUsersByRole(Role role);

   Set<User> getUsersByRoleOrderedBy(Role role, SingularAttribute<? super User, ?> singularAttribute, OrderDirection orderDirection);

   User getUser(long userId);

   User getUser(String username);

   void addUser(User user);

   void updateUser(User user, boolean encodePassword);

   void deleteUser(User user);

   void deleteUser(long userId);
}
