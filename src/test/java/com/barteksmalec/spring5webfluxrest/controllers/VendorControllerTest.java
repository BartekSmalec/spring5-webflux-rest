package com.barteksmalec.spring5webfluxrest.controllers;

import com.barteksmalec.spring5webfluxrest.model.Vendor;
import com.barteksmalec.spring5webfluxrest.repostitories.VendorRepository;
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

class VendorControllerTest {
    public static final String URI = "/api/v1/vendors/";
    WebTestClient webTestClient;
    VendorRepository vendorRepository;
    VendorController vendorController;

    @BeforeEach
    void setUp() {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    void list() {
        given(vendorRepository.findAll()).willReturn(Flux.just(
                Vendor.builder().firstname("Ola").lastname("Kot").build(),
                Vendor.builder().firstname("Joe").lastname("Buck").build()));

        webTestClient.get()
                .uri(URI)
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    void getById() {
        given(vendorRepository.findById(anyString())).willReturn(Mono.just(
                Vendor.builder().firstname("Aga").lastname("Bicz").build()));

        webTestClient.get()
                .uri(URI + "someid")
                .exchange()
                .expectBody(Vendor.class);
    }

    @Test
    void create() {
        given(vendorRepository.saveAll(any(Publisher.class))).willReturn(Flux.just(Vendor.builder().build()));

        Mono<Vendor> vendorMono = Mono.just(Vendor.builder().firstname("Ola").lastname("Kot").build());

        webTestClient.post()
                .uri(URI)
                .body(vendorMono, Vendor.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void update() {
        given(vendorRepository.save(any(Vendor.class))).willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorMono = Mono.just(Vendor.builder().firstname("Ola").lastname("Kot").build());

        webTestClient.put()
                .uri(URI + "id")
                .body(vendorMono, Vendor.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void patchWithChanges() {
        given(vendorRepository.findById(anyString())).willReturn(Mono.just(Vendor.builder().build()));

        given(vendorRepository.save(any(Vendor.class))).willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorMono = Mono.just(Vendor.builder().firstname("Ola").lastname("Kot").build());

        webTestClient.patch()
                .uri(URI + "id")
                .body(vendorMono, Vendor.class)
                .exchange()
                .expectStatus().isOk();

        verify(vendorRepository).save(any());
    }


    @Test
    void patchNoChanges() {
        given(vendorRepository.findById(anyString())).willReturn(Mono.just(Vendor.builder().build()));

        given(vendorRepository.save(any(Vendor.class))).willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorMono = Mono.just(Vendor.builder().build());

        webTestClient.patch()
                .uri(URI + "id")
                .body(vendorMono, Vendor.class)
                .exchange()
                .expectStatus().isOk();

        verify(vendorRepository, never()).save(any());
    }
}