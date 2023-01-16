package com.barteksmalec.spring5webfluxrest.controllers;

import com.barteksmalec.spring5webfluxrest.model.Category;
import com.barteksmalec.spring5webfluxrest.model.Vendor;
import com.barteksmalec.spring5webfluxrest.repostitories.VendorRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/vendors")
public class VendorController {
    private final VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping
    Flux<Vendor> list() {
        return vendorRepository.findAll();
    }

    @GetMapping("/{id}")
    Mono<Vendor> getById(@PathVariable String id) {
        return vendorRepository.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Void> create(@RequestBody Publisher<Vendor> vendorPublisher){
        return vendorRepository.saveAll(vendorPublisher).then();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Mono<Vendor> update(@PathVariable String id, @RequestBody Vendor vendor){
        vendor.setId(id);
        return vendorRepository.save(vendor);
    }


}
