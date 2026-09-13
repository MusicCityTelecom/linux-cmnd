package be.tpvision.smartcontrol.domain;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Group.class)
public abstract class Group_ {
   public static volatile SetAttribute<Group, Device> devices;
   public static volatile SingularAttribute<Group, String> name;
   public static volatile SingularAttribute<Group, Long> id;
   public static final String DEVICES = "devices";
   public static final String NAME = "name";
   public static final String ID = "id";
}
