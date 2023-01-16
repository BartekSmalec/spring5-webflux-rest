package com.barteksmalec.spring5webfluxrest.controllers;

import com.barteksmalec.spring5webfluxrest.model.Vendor;
import com.barteksmalec.spring5webfluxrest.repostitories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

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
}