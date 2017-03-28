package com.example.web;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.example.BaseTest;
import com.example.ExampleApplication;
import com.example.model.Category;
import com.example.model.Pet;
import com.example.service.CategoryService;
import com.example.service.PetService;

import net.minidev.json.JSONArray;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExampleApplication.class)
@WebAppConfiguration
public class PetControllerTest extends BaseTest {

    private Pet pet;

    @Autowired
    CategoryService categoryService;
    @Autowired
    PetService petService;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(this.webApplicationContext).build();
        Category dogCategory = new Category(1L, "dog");
        categoryService.saveCategory(dogCategory);
        petService.savePet(new Pet("Labrador chocolate", 2, dogCategory));
        petService.savePet(new Pet("Golden retriever", 2, dogCategory));
        pet = petService.getPetById(1L);
    }

    @Test
    public void testCRUD() throws Exception {
        test1GetPetDetail();
        test2AddPet();
        test3UpdatePet();
        test4getAll();
        test5DeletePet();
    }

    private void test1GetPetDetail() throws Exception {
        mockMvc.perform(get("/pet/" + pet.getId())
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(pet.getId().intValue())))
                .andExpect(jsonPath("$.name", is(pet.getName())))
                .andExpect(jsonPath("$.quantity", is(pet.getQuantity())))
                .andExpect(jsonPath("$.category.id", is(pet.getCategory().getId().intValue())));
    }

    private void test2AddPet() throws Exception {
        mockMvc.perform(post("/pet")
                .content(json(new Pet("red dog", 10, new Category(1L))))
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("red dog")))
                .andExpect(jsonPath("$.quantity", is(10)));
    }

    private void test3UpdatePet() throws Exception {
        Pet pet = petService.getPetById(1L);
        Pet petUpd = new Pet(pet.getName() + "updated", pet.getQuantity(), new Category(pet.getId()));
        petUpd.setId(pet.getId());

        mockMvc.perform(put("/pet")
                .content(json(petUpd))
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(pet.getName() + "updated")))
                .andExpect(jsonPath("$.quantity", is(pet.getQuantity())));
    }

    private void test4getAll() throws Exception {
        mockMvc.perform(get("/pet/list?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", isA(JSONArray.class)));
    }

    private void test5DeletePet() throws Exception {
        mockMvc.perform(delete("/pet/" + pet.getId())
                .contentType(contentType))
                .andExpect(status().isOk());
    }

}