package com.habr.dddoptimisticlocks.controller.dto;

import java.time.LocalDate;

public record MilestoneDTO(
    Integer index,
    LocalDate startDate,
    LocalDate endDate) {

}
