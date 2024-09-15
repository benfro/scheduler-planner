package net.benfro.planner;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class WorkShiftTest {

    private static final LocalDate MONDAY = LocalDate.of(2030, 4, 1);

    @Test
    void testGetLength() {
        WorkShift workShift = new WorkShift(MONDAY.atTime(7, 0), MONDAY.atTime(15, 30), null);
        assertEquals(8.5, workShift.getShiftLength());
    }
}