package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule.setActivities(scheduleDTO.getActivities());
        schedule.setDate(scheduleDTO.getDate());

        return toScheduleDTO(
            scheduleService.save(schedule, scheduleDTO.getEmployeeIds(), scheduleDTO.getPetIds())
        );
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return buildScheduleDTOCollection(scheduleService.getAll());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return buildScheduleDTOCollection(scheduleService.getPetSchedules(petId));
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return buildScheduleDTOCollection(scheduleService.getEmployeeSchedules(employeeId));
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return buildScheduleDTOCollection(scheduleService.getCustomerSchedules(customerId));
    }

    private List<ScheduleDTO> buildScheduleDTOCollection(List<Schedule> schedules) {
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        for (Schedule schedule: schedules) {
            scheduleDTOS.add(toScheduleDTO(schedule));
        }

        return scheduleDTOS;
    }

    private ScheduleDTO toScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        List<Pet> pets = schedule.getPets();
        List<Employee> employees = schedule.getEmployees();

        BeanUtils.copyProperties(schedule, scheduleDTO);

        if (pets != null) {
            scheduleDTO.setPetIds(
                pets.stream().map(Pet::getId).collect(Collectors.toList())
            );
        }

        if (employees != null) {
            scheduleDTO.setEmployeeIds(
                employees.stream().map(Employee::getId).collect(Collectors.toList())
            );
        }

        return scheduleDTO;
    }
}
