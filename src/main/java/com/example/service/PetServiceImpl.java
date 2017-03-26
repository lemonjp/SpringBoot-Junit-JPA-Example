package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.model.Pet;
import com.example.repository.PetRepository;

@Service("petService")
public class PetServiceImpl implements PetService{

	@Autowired
	private PetRepository petRepository;

	@Override
    public Page<Pet> getAllPet(Pageable pageable) {
		return petRepository.findAll(pageable);
	}

	@Override
	public Pet getPetById(long id) {
		return petRepository.findOne(id);
	}

	@Override
	public Pet savePet(Pet pet) {
		return petRepository.save(pet);
	}

	@Override
	public void removePet(Long petId) {
		petRepository.delete(petId);
	}


}
