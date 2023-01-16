package com.barteksmalec.spring5webfluxrest.controllers;

import com.barteksmalec.spring5webfluxrest.model.Category;
import com.barteksmalec.spring5webfluxrest.repostitories.CategoryRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(("/api/v1/categories"))
public class CategoryController {
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @GetMapping
    Flux<Category> list(){
      return categoryRepository.findAll();
    }
    @GetMapping("/{id}")
    Mono<Category> getById(@PathVariable String id) {
        return categoryRepository.findById(id);
    }


}
