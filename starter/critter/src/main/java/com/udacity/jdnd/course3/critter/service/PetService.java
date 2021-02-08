package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.persistence.CustomerRepository;
import com.udacity.jdnd.course3.critter.persistence.PetRepository;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Customer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PetService {
    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Pet save(Pet pet, Long ownerId) {
        Customer customer = customerRepository.getOne(ownerId);
        pet.setCustomer(customer);

        Pet newPet = petRepository.save(pet);

        customer.addPet(newPet);

        customerRepository.save(customer);

        return newPet;
    }

    public List<Pet> getByOwner(long ownerId) {
        return petRepository.findAllByCustomerId(ownerId);
    }

    public Pet getById(long petId) {
        return petRepository.findById(petId).orElse(null);
    }

    public List<Pet> getAll() {
        return petRepository.findAll();
    }
}
