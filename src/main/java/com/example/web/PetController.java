package com.example.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Category;
import com.example.model.Pet;
import com.example.service.CategoryService;
import com.example.service.PetService;

@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;
    private final CategoryService categoryService;

    @Autowired
    public PetController(PetService petService, CategoryService categoryService) {
        this.petService = petService;
        this.categoryService = categoryService;
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody Pet pet) {
        return savePet(pet);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody Pet pet) {
        if (petService.getPetById(pet.getId()) != null) {
            return savePet(pet);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<Page> list(Pageable pageable) {
        return new ResponseEntity<>(this.petService.getAllPet(pageable), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{petId}", method = RequestMethod.GET)
    public ResponseEntity<Pet> getById(@PathVariable Long petId) {
        return  new ResponseEntity<>(petService.getPetById(petId), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{petId}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable Long petId) {
        petService.removePet(petId);
        return ResponseEntity.ok().build();
    }

    private ResponseEntity<?> savePet(Pet pet) {
        // Fetch pet category from id
        Category category = this.categoryService.getCategoryById(pet.getCategory().getId());

        if (category == null)
            return ResponseEntity.noContent().build();

        // Save pet
        pet.setCategory(category);
        return new ResponseEntity<>(petService.savePet(pet), HttpStatus.OK);
    }
}