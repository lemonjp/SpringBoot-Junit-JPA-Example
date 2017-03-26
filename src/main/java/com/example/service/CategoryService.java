package com.example.service;

import java.util.List;

import com.example.model.Category;

public interface CategoryService {
	public List<Category> getAllCategory();
	public Category getCategoryById(long id);
	public Category saveCategory(Category category);
	public void removeCategory(Category category);
}
