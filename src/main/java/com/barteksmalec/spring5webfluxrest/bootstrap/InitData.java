package com.barteksmalec.spring5webfluxrest.bootstrap;

import com.barteksmalec.spring5webfluxrest.model.Category;
import com.barteksmalec.spring5webfluxrest.model.Vendor;
import com.barteksmalec.spring5webfluxrest.repostitories.CategoryRepository;
import com.barteksmalec.spring5webfluxrest.repostitories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InitData implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    public InitData(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (vendorRepository.count().block() == 0) {
            loadVendors();

        }
        if (categoryRepository.count().block() == 0) {
            loadCategories();
        }

    }

    private void loadVendors() {
        vendorRepository.save(Vendor.builder().firstname("Ola").lastname("Kot").build()).block();
        vendorRepository.save(Vendor.builder().firstname("Joe").lastname("Buck").build()).block();
        vendorRepository.save(Vendor.builder().firstname("Aga").lastname("Bicz").build()).block();
        vendorRepository.save(Vendor.builder().firstname("Jimmy").lastname("Buffet").build()).block();
        vendorRepository.save(Vendor.builder().firstname("Brandon").lastname("Sanderson").build()).block();


        log.debug("Count of vendors: " + vendorRepository.count().block());
    }

    private void loadCategories() {
        categoryRepository.save(Category.builder().description("Fruits").build()).block();
        categoryRepository.save(Category.builder().description("Dried").build()).block();
        categoryRepository.save(Category.builder().description("Fresh").build()).block();
        categoryRepository.save(Category.builder().description("Exotic").build()).block();
        categoryRepository.save(Category.builder().description("Nuts").build()).block();


        log.debug("Count of categories: " + categoryRepository.count().block());
    }
}
