package com.habr.dddoptimisticlocks.repository;

import com.habr.dddoptimisticlocks.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

}
