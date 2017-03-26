package com.example.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.model.Pet;

public interface PetService {
	public Page<Pet> getAllPet(Pageable pageable);
	public Pet getPetById(long id);
	public Pet savePet(Pet pet);
	public void removePet(Long id);
}
