package co.edu.uniandes.csw.G3xtreme.fase.persistence.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-10-27T16:22:25")
@StaticMetamodel(_FaseEntity.class)
public abstract class _FaseEntity_ { 

    public static volatile SingularAttribute<_FaseEntity, Long> id;
    public static volatile SingularAttribute<_FaseEntity, Boolean> estado;
    public static volatile SingularAttribute<_FaseEntity, String> name;
    public static volatile SingularAttribute<_FaseEntity, Date> fechaFin;
    public static volatile SingularAttribute<_FaseEntity, Date> fechaInicio;

}