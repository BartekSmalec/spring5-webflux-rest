package com.barteksmalec.spring5webfluxrest.repostitories;

import com.barteksmalec.spring5webfluxrest.model.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {
}
