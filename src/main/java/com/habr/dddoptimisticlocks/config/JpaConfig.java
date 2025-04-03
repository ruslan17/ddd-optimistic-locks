package com.habr.dddoptimisticlocks.config;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JpaConfig {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @PostConstruct
    public void init() {

        var sessionFactory = emf.unwrap(SessionFactoryImpl.class);
        var registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
        registry.appendListeners(EventType.POST_UPDATE, new OptimisticLockPostUpdateListener());
    }
}
