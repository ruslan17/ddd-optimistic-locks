package com.habr.dddoptimisticlocks.controller.dto;

import java.time.LocalDate;

public record MilestoneUpdateDTO(
    LocalDate startDate,
    LocalDate endDate) {

}
