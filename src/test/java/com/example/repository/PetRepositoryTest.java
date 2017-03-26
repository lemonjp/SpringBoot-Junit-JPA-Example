package com.example.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Optional;
import java.util.stream.StreamSupport;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.ExampleApplication;
import com.example.model.Category;
import com.example.model.Pet;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = ExampleApplication.class,
        properties = {"flyway.enabled=false"}
        )
@DataJpaTest
public class PetRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    PetRepository petRepository;
    @Autowired
    CategoryRepository categoryRepository;

    public static final String DOLPHIN = "dolphin";


    /*
    @Before
    public void setUp() {
        petRepository.save(new Pet("Labrador chocolate", 3, 1));
        petRepository.save(new Pet("Yellow bird", 2, 4));
    }
    */

    @Test
    public void getOnePet() throws Exception {
        Pet one = petRepository.findOne(1L);
        assertNotNull(one);
    }

    @Test
    public void createPet() throws Exception {
        Optional<Category> fish =
                StreamSupport.stream(categoryRepository.findAll().spliterator(), false).
                        filter(category -> category.getName().equals("Fish")).findFirst();
        assertNotNull(fish.get());
        entityManager.persist(new Pet(DOLPHIN, 3, fish.get()));
        assertNotNull(petRepository.findByname(DOLPHIN));
    }

    @Test
    public void updatePet() throws Exception {
        Pet one = petRepository.findOne(1L);
        assertNotNull(one);
        one.setName(one.getName() + "updated");
        petRepository.save(one);
        Pet updated = petRepository.findOne(1L);
        assertEquals(one.getName(), updated.getName());
    }

    @Test
    public void deletePet() throws Exception {
        //petRepository.delete(2L);
        //assertNull(petRepository.findOne(2L));
        petRepository.delete(1L);
        assertNull(petRepository.findOne(1L));
    }

    @Test
    public void getByPage() throws Exception {
        Page<Pet> pageOne = petRepository.findAll(new PageRequest(1, 10));
        assertThat(pageOne.getTotalElements()).isGreaterThan(0);
    }
}