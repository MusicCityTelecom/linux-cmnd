package be.tpvision.smartcontrol.domain.device_settings.input_sources;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(InputSource.class)
public abstract class InputSource_ {
   public static volatile SingularAttribute<InputSource, InputSource.SourceType> sourceType;
   public static volatile SingularAttribute<InputSource, InputSource.SourceLabel> sourceLabel;
   public static volatile SingularAttribute<InputSource, InputSource.Tag> tag;
   public static final String SOURCE_TYPE = "sourceType";
   public static final String SOURCE_LABEL = "sourceLabel";
   public static final String TAG = "tag";
}
