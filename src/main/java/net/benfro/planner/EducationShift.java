package net.benfro.planner;


import java.util.List;

import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty;
import ai.timefold.solver.core.api.domain.solution.PlanningScore;
import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@PlanningSolution
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EducationShift {

    @ValueRangeProvider
    private List<Teacher> teachers;
    @PlanningEntityCollectionProperty
    private List<WorkShift> shifts;

    @PlanningScore
    private HardSoftScore score;

    public EducationShift(List<Teacher> teachers, List<WorkShift> shifts) {
        this.teachers = teachers;
        this.shifts = shifts;
    }
}
