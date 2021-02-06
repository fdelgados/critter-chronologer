package com.udacity.jdnd.course3.critter.user;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.Set;

@Entity
public class Employee extends User {
    @ElementCollection
    private Set<EmployeeSkill> skills;

    public Employee() {
    }

    public Employee(String name, Set<EmployeeSkill> skills) {
        super(name);
        this.skills = skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }
}
