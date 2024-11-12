package com.habr.dddoptimisticlocks.ddd;

import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class AggregateRoot<PK extends Serializable> extends Entity<PK> {


}
