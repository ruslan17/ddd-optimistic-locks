package com.habr.dddoptimisticlocks.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@FieldNameConstants
@Getter
@Setter(value = AccessLevel.PROTECTED)
public class Milestone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // some fields

    private Integer index;

    private LocalDate startDate;

    private LocalDate endDate;

    protected Milestone(Integer index, LocalDate startDate, LocalDate endDate) {

        this.index = index;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}


