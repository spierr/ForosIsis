package co.edu.uniandes.csw.G3xtreme.tarea.persistence.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-10-30T11:06:49")
@StaticMetamodel(_TareaEntity.class)
public abstract class _TareaEntity_ { 

    public static volatile SingularAttribute<_TareaEntity, Long> id;
    public static volatile SingularAttribute<_TareaEntity, String> responsable;
    public static volatile SingularAttribute<_TareaEntity, Integer> estado;
    public static volatile SingularAttribute<_TareaEntity, String> name;
    public static volatile SingularAttribute<_TareaEntity, String> descripcion;
    public static volatile SingularAttribute<_TareaEntity, Long> responsable_tareaId;
    public static volatile SingularAttribute<_TareaEntity, Date> fechaFin;
    public static volatile SingularAttribute<_TareaEntity, Date> fechaInicio;

}