package com.habr.dddoptimisticlocks.config;

import static com.habr.dddoptimisticlocks.utils.OptimisticLockingUtils.resolveClass;

import com.habr.dddoptimisticlocks.ddd.AggregateMember;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.LockMode;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;

@Slf4j
public class OptimisticLockPostUpdateListener implements PostUpdateEventListener {

    @Override
    public void onPostUpdate(PostUpdateEvent event) {

        var entity = event.getEntity();

        if (entity instanceof AggregateMember<?, ?> aggregateMember) {

            Object root = event.getSession().get(resolveClass(aggregateMember), aggregateMember.getRootId());

            event.getSession().lock(root, LockMode.OPTIMISTIC_FORCE_INCREMENT);
        }
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister persister) {

        return false;
    }
}
