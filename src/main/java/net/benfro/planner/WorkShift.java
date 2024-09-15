package net.benfro.planner;

import java.time.LocalDate;
import java.time.LocalDateTime;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.variable.PlanningVariable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@PlanningEntity
@NoArgsConstructor
@AllArgsConstructor
public class WorkShift {

    private LocalDateTime start;
    private LocalDateTime end;

    @PlanningVariable
    private Teacher teacher;

    public double getShiftLength() {
        return end.getHour() - start.getHour() + (end.getMinute() - start.getMinute()) / 60.0;
    }

    public LocalDate toDate() {
        return end.toLocalDate();
    }

    public Group getGroup() {
        return teacher.getGroup();
    }

//    public WorkShift(LocalDateTime start, LocalDateTime end, Teacher teacher) {
//        this.start = start;
//        this.end = end;
//        this.teacher = teacher;
//    }
}
