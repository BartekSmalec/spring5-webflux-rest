package com.barteksmalec.spring5webfluxrest.controllers;

import com.barteksmalec.spring5webfluxrest.model.Category;
import com.barteksmalec.spring5webfluxrest.repostitories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class CategoryControllerTest {

    public static final String URI = "/api/v1/categories/";
    WebTestClient webTestClient;
    CategoryRepository categoryRepository;
    CategoryController categoryController;

    @BeforeEach
    void setUp() {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    void list() {
        given(categoryRepository.findAll())
                .willReturn(Flux.just(Category.builder().description("Cat1").build(), Category.builder().description("Cat2").build()));

        webTestClient.get()
                .uri(URI)
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    void getById() {
        given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().description("Cat1").build()));
        webTestClient.get()
                .uri(URI + "someid")
                .exchange()
                .expectBody(Category.class);

    }

    @Test
    void create() {
        given(categoryRepository.saveAll(any(Publisher.class))).willReturn(Flux.just(Category.builder().build()));

        Mono<Category> categoryMono = Mono.just(Category.builder().description("Some Cat").build());

        webTestClient.post()
                .uri(URI)
                .body(categoryMono, Category.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void update() {
        given(categoryRepository.save(any(Category.class))).willReturn(Mono.just(
                Category.builder().description("Some description").build()));

        Mono<Category> categoryMono = Mono.just(Category.builder().description("Some cat").build());

        webTestClient.put()
                .uri(URI + "someid")
                .body(categoryMono, Category.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void patchWithChanges() {

        given(categoryRepository.findById(anyString())).willReturn(Mono.just(
                Category.builder().build()));

        given(categoryRepository.save(any(Category.class))).willReturn(Mono.just(
                Category.builder().build()));

        Mono<Category> categoryMono = Mono.just(Category.builder().description("Some cat").build());

        webTestClient.patch()
                .uri(URI + "someid")
                .body(categoryMono, Category.class)
                .exchange()
                .expectStatus().isOk();
        verify(categoryRepository).save(any());
    }


    @Test
    void patchNoChanges() {

        given(categoryRepository.findById(anyString())).willReturn(Mono.just(
                Category.builder().build()));

        given(categoryRepository.save(any(Category.class))).willReturn(Mono.just(
                Category.builder().build()));

        Mono<Category> categoryMono = Mono.just(Category.builder().build());

        webTestClient.patch()
                .uri(URI + "someid")
                .body(categoryMono, Category.class)
                .exchange()
                .expectStatus().isOk();
        verify(categoryRepository, never()).save(any());
    }
}