package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.user.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setType(petDTO.getType());
        pet.setName(petDTO.getName());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setNotes(petDTO.getNotes());

        return toPetDTO(petService.save(pet, petDTO.getOwnerId()));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return toPetDTO(petService.getById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getAll();

        return buildPetDTOCollection(pets);
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petService.getByOwner(ownerId);

        return buildPetDTOCollection(pets);
    }

    private List<PetDTO> buildPetDTOCollection(List<Pet> pets) {
        List<PetDTO> petDTOS = new ArrayList<>();

        for (Pet pet: pets) {
            petDTOS.add(toPetDTO(pet));
        }

        return petDTOS;
    }

    private PetDTO toPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        Customer customer = pet.getCustomer();

        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(customer.getId());

        return petDTO;
    }
}
