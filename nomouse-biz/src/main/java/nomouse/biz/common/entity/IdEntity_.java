package nomouse.biz.common.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(IdEntity.class)
public abstract class IdEntity_ {

    public static volatile SingularAttribute<IdEntity, Long> id;

}

