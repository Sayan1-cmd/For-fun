package com.project.project.entity;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

import java.io.Serializable;


public class Sequence extends IdentityGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        Serializable id = session.getEntityPersister(null, object).getClassMetadata().getIdentifier(object, session);
        return id != null ? id : super.generate(session, object);
    }
}
