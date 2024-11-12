package com.habr.dddoptimisticlocks.ddd;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class Entity<PK extends Serializable> {

    @Version
    private Long version;

    public abstract PK getId();

    protected Entity() {

    }
}
