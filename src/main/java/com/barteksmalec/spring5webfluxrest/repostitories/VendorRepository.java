package com.barteksmalec.spring5webfluxrest.repostitories;

import com.barteksmalec.spring5webfluxrest.model.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface VendorRepository extends ReactiveMongoRepository<Vendor, String> {
}
