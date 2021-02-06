package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.persistence.CustomerRepository;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = this.customerRepository.save(
            new Customer(customerDTO.getName(), customerDTO.getPhoneNumber())
        );

        return createDTO(customer);
    }

    public List<CustomerDTO> getAll() {
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        List<Customer> customers = this.customerRepository.findAll();

        for (Customer customer: customers) {
            customerDTOS.add(createDTO(customer));
        }

        return customerDTOS;
    }

    private CustomerDTO createDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setNotes(customer.getNotes());

        List<Pet> pets = customer.getPets();

        if (pets != null) {
            customerDTO.setPetIds(
                pets.stream().map(pet -> pet.getId()).collect(Collectors.toList())
            );
        }

        return customerDTO;
    }
}
