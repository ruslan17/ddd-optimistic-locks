package com.habr.dddoptimisticlocks.controller;

import com.habr.dddoptimisticlocks.controller.dto.MilestoneDTO;
import com.habr.dddoptimisticlocks.controller.dto.MilestoneUpdateDTO;
import com.habr.dddoptimisticlocks.repository.OrderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MilestoneController {

    private final OrderRepository repository;

    @PostMapping(value = "/orders/{id}/milestones")
    @Transactional
    public void addMilestones(@PathVariable Integer id, @RequestBody List<MilestoneDTO> milestones) {

        var order = repository.findById(id)
            .orElseThrow();

        milestones.forEach(order::addMilestone);
    }

    @SneakyThrows
    @PatchMapping(value = "/orders/{orderId}/milestones/{milestoneId}")
    @Transactional
    public void update(
        @PathVariable Integer orderId,
        @PathVariable Integer milestoneId,
        @RequestBody MilestoneUpdateDTO milestoneDTO) {

        var order = repository.findById(orderId)
            .orElseThrow();

        order.updateMilestone(milestoneId, milestoneDTO.startDate(), milestoneDTO.endDate());

        Thread.sleep(5000);
    }

}
