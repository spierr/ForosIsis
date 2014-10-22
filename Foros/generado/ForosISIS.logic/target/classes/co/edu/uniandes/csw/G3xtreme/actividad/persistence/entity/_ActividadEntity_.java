package co.edu.uniandes.csw.G3xtreme.actividad.persistence.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-10-01T10:57:35")
@StaticMetamodel(_ActividadEntity.class)
public abstract class _ActividadEntity_ { 

    public static volatile SingularAttribute<_ActividadEntity, Long> id;
    public static volatile SingularAttribute<_ActividadEntity, Long> hora;
    public static volatile SingularAttribute<_ActividadEntity, String> name;
    public static volatile SingularAttribute<_ActividadEntity, String> descripcion;
    public static volatile SingularAttribute<_ActividadEntity, String> tema;
    public static volatile SingularAttribute<_ActividadEntity, Long> costo;
    public static volatile SingularAttribute<_ActividadEntity, Long> lugar_actividadId;
    public static volatile SingularAttribute<_ActividadEntity, Long> expositor_actividadId;

}