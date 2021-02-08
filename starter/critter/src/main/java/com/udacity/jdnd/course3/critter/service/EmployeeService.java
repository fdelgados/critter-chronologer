package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.persistence.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getById(long employeeId) {
        return employeeRepository.getOne(employeeId);
    }

    public void addSchedule(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee = getById(employeeId);

        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }

    public List<Employee> getAvailableEmployees(Set<EmployeeSkill> skills, LocalDate date) {
        List<Employee> employees = employeeRepository
                .findAllByDaysAvailableContains(date.getDayOfWeek());

        return employees.stream().filter(
            employee -> employee.getSkills().containsAll(skills)
        ).collect(Collectors.toList());
    }
}
