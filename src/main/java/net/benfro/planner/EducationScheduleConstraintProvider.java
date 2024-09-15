package net.benfro.planner;

import static ai.timefold.solver.core.api.score.stream.Joiners.equal;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintCollectors;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;

public class EducationScheduleConstraintProvider implements ConstraintProvider {
    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[] {
            atMostOneShiftPerDay(constraintFactory),
            notLongerThanEightHours(constraintFactory)
        };
    }

    Constraint grouping(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(WorkShift.class)
            .groupBy(
                WorkShift::toDate,
                WorkShift::getGroup,
                ConstraintCollectors.max())
            .penalize(HardSoftScore.ONE_HARD)
            .asConstraint("Max working time per day");
    }

    Constraint notLongerThanEightHours(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(WorkShift.class)
            .filter(shift -> shift.getShiftLength() > 8.0)
            .penalize(HardSoftScore.ONE_HARD)
            .asConstraint("Max working time per day");
    }

    Constraint atMostOneShiftPerDay(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(WorkShift.class)
            .join(WorkShift.class,
                equal(shift -> shift.getStart().toLocalDate()),
                equal(WorkShift::getTeacher))
            .filter((shift1, shift2) -> shift1 != shift2)
            .penalize(HardSoftScore.ONE_HARD)
            .asConstraint("At most one shift per day");
    }
}
