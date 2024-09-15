package net.benfro.planner;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Teacher {
    private String name;
    private Group group;
}
