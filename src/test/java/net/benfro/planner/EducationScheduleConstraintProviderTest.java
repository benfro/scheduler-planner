package net.benfro.planner;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import ai.timefold.solver.test.api.score.stream.ConstraintVerifier;

class EducationScheduleConstraintProviderTest {

    private static final LocalDate MONDAY = LocalDate.of(2030, 4, 1);
    private static final LocalDate TUESDAY = LocalDate.of(2030, 4, 2);

    ConstraintVerifier<EducationScheduleConstraintProvider, EducationShift> constraintVerifier
        = ConstraintVerifier.build(new EducationScheduleConstraintProvider(), EducationShift.class, WorkShift.class);

    @Test
    void whenTwoShiftsOnOneDay_thenPenalize() {
        Teacher ann = new Teacher("Ann", Group.OLDER_GROUP);
        constraintVerifier.verifyThat(EducationScheduleConstraintProvider::atMostOneShiftPerDay)
            .given(
                new WorkShift(MONDAY.atTime(6, 0), MONDAY.atTime(14, 0), ann),
                new WorkShift(MONDAY.atTime(14, 0), MONDAY.atTime(22, 0), ann))
            // Penalizes by 2 because both {shiftA, shiftB} and {shiftB, shiftA} match.
            // To avoid that, use forEachUniquePair() in the constraint instead of forEach().join() in the implementation.
            .penalizesBy(2);
    }

    @Test
    void whenTwoShiftsOnDifferentDays_thenDoNotPenalize() {
        Teacher ann = new Teacher("Ann", Group.OLDER_GROUP);
        Teacher ann2 = new Teacher("Ann", Group.YOUNGER_GROUP);
        constraintVerifier.verifyThat(EducationScheduleConstraintProvider::atMostOneShiftPerDay)
            .given(
                new WorkShift(MONDAY.atTime(6, 0), MONDAY.atTime(14, 0), ann),
                new WorkShift(MONDAY.atTime(6, 0), MONDAY.atTime(14, 0), ann2),
                new WorkShift(TUESDAY.atTime(14, 0), TUESDAY.atTime(22, 0), ann))
            .penalizesBy(0);
    }

    @Test
    void whenLongerWorkDayThanEightHours_thenDoPenalize() {
        Teacher ann = new Teacher("Ann", Group.OLDER_GROUP);
        constraintVerifier.verifyThat(EducationScheduleConstraintProvider::notLongerThanEightHours)
            .given(
                new WorkShift(MONDAY.atTime(6, 0), MONDAY.atTime(14, 10), ann),
                new WorkShift(TUESDAY.atTime(14, 0), TUESDAY.atTime(22, 0), ann)
                )
            .penalizesBy(1);;
    }

}