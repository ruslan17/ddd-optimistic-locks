package com.habr.dddoptimisticlocks.controller;

import com.habr.dddoptimisticlocks.controller.dto.OrderDTO;
import com.habr.dddoptimisticlocks.model.Order;
import com.habr.dddoptimisticlocks.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository repository;

    @PostMapping(value = "/orders")
    @Transactional
    public void save(@RequestBody OrderDTO orderDTO) {

        repository.save(new Order(orderDTO.name()));
    }

}
