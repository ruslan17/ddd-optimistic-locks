package com.habr.dddoptimisticlocks.model;

import com.habr.dddoptimisticlocks.controller.dto.MilestoneDTO;
import com.habr.dddoptimisticlocks.ddd.AggregateRoot;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Entity
@FieldNameConstants
@Getter
@Setter(AccessLevel.PROTECTED)
public class Order extends AggregateRoot<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<Milestone> milestones = new ArrayList<>();

    // работает нормально - обновляет версию агрегейт рута, тк мы меняем лист и хибернейт это понимает
    public void addMilestone(MilestoneDTO milestone) {

        // some validations

        milestones.add(new Milestone(milestone.index(), milestone.startDate(), milestone.endDate()));
    }

    // работает нормально - обновляет версию агрегейт рута, тк мы меняем лист и хибернейт это понимает
    public void removeMilestone(Integer milestoneId) {

        // some validations

        milestones.removeIf(milestone -> milestone.getId().equals(milestoneId));
    }

    // НЕ обновляет версию рута
    public void updateMilestone(Integer milestoneId, LocalDate startDate, LocalDate endDate) {

        validatePeriod(milestoneId, startDate, endDate);

        milestones.stream()
            .filter(milestone -> milestone.getId().equals(milestoneId))
            .findFirst()
            .ifPresent(milestone -> {
                milestone.setStartDate(startDate);
                milestone.setEndDate(endDate);
            });
    }

    // для наглядности мы игнорируем корнер кейсы
    private void validatePeriod(Integer milestoneId, LocalDate startDate, LocalDate endDate) {

        var milestone = milestones
            .stream()
            .filter(m -> m.getId().equals(milestoneId))
            .findFirst()
            .orElseThrow();

        var prevMilestone = milestones
            .stream()
            .filter(m -> m.getIndex().equals(milestone.getIndex() - 1))
            .findFirst()
            .orElseThrow();

        var nextMilestone = milestones
            .stream()
            .filter(m -> m.getIndex().equals(milestone.getIndex() + 1))
            .findFirst()
            .orElseThrow();

        if (startDate.isBefore(prevMilestone.getEndDate())) {
            throw new RuntimeException();
        }

        if (endDate.isAfter(nextMilestone.getStartDate())) {
            throw new RuntimeException();
        }
    }
}


