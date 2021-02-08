package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.persistence.CustomerRepository;
import com.udacity.jdnd.course3.critter.persistence.PetRepository;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Customer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PetRepository petRepository;

    public CustomerService(CustomerRepository customerRepository, PetRepository petRepository) {
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
    }

    public Customer save(Customer customer) {
        return this.customerRepository.save(customer);
    }

    public List<Customer> getAll() {
        return this.customerRepository.findAll();
    }

    public Customer getByPet(long petId) {
        Optional<Pet> pet = petRepository.findById(petId);

        return pet.map(Pet::getCustomer).orElse(null);
    }
}
