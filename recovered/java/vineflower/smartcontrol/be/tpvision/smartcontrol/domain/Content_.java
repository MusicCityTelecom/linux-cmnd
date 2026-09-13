package be.tpvision.smartcontrol.domain;

import java.time.LocalDateTime;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Content.class)
public abstract class Content_ {
   public static volatile SingularAttribute<Content, Content.Orientation> orientation;
   public static volatile SingularAttribute<Content, String> thumbnail;
   public static volatile SingularAttribute<Content, LocalDateTime> created;
   public static volatile SingularAttribute<Content, LocalDateTime> publishDate;
   public static volatile SingularAttribute<Content, String> id;
   public static volatile SingularAttribute<Content, LocalDateTime> localChanged;
   public static volatile SingularAttribute<Content, String> title;
   public static volatile SingularAttribute<Content, LocalDateTime> changed;
   public static final String ORIENTATION = "orientation";
   public static final String THUMBNAIL = "thumbnail";
   public static final String CREATED = "created";
   public static final String PUBLISH_DATE = "publishDate";
   public static final String ID = "id";
   public static final String LOCAL_CHANGED = "localChanged";
   public static final String TITLE = "title";
   public static final String CHANGED = "changed";
}
