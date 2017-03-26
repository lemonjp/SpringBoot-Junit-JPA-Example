package com.example.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.model.Pet;

public interface PetRepository extends PagingAndSortingRepository<Pet, Long> {
    Pet findByname(String name);
}