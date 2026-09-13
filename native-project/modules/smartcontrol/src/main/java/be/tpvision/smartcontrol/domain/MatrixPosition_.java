package be.tpvision.smartcontrol.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(MatrixPosition.class)
public abstract class MatrixPosition_ {
   public static volatile SingularAttribute<MatrixPosition, Integer> sizeX;
   public static volatile SingularAttribute<MatrixPosition, Integer> x;
   public static volatile SingularAttribute<MatrixPosition, Integer> y;
   public static volatile SingularAttribute<MatrixPosition, Integer> sizeY;
   public static final String SIZE_X = "sizeX";
   public static final String X = "x";
   public static final String Y = "y";
   public static final String SIZE_Y = "sizeY";
}
