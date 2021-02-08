package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.persistence.CustomerRepository;
import com.udacity.jdnd.course3.critter.persistence.EmployeeRepository;
import com.udacity.jdnd.course3.critter.persistence.PetRepository;
import com.udacity.jdnd.course3.critter.persistence.ScheduleRepository;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final PetRepository petRepository;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;

    public ScheduleService(
        ScheduleRepository scheduleRepository,
        PetRepository petRepository,
        EmployeeRepository employeeRepository,
        CustomerRepository customerRepository
    ) {

        this.scheduleRepository = scheduleRepository;
        this.petRepository = petRepository;
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
    }

    public Schedule save(Schedule schedule, List<Long> employeeIds, List<Long> petIds) {
        List<Pet> pets = petRepository.findAllById(petIds);
        List<Employee> employees = employeeRepository.findAllById(employeeIds);

        schedule.setPets(pets);
        schedule.setEmployees(employees);

        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAll() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getPetSchedules(long petId) {
        Pet pet = petRepository.getOne(petId);

        return scheduleRepository.findAllByPetsContains(pet);
    }

    public List<Schedule> getEmployeeSchedules(long employeeId) {
        Employee employee = employeeRepository.getOne(employeeId);

        return scheduleRepository.findAllByEmployeesContains(employee);
    }

    public List<Schedule> getCustomerSchedules(long customerId) {
        Customer customer = customerRepository.getOne(customerId);

        return scheduleRepository.findAllByPetsIn(customer.getPets());
    }
}
