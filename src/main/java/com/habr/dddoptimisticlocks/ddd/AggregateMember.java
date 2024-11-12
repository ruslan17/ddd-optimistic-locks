package com.habr.dddoptimisticlocks.ddd;

import java.io.Serializable;

public interface AggregateMember<R_PK extends Serializable, T extends AggregateRoot<R_PK>> {

    R_PK getRootId();

//    Class<T> getParentClass();
}

