package com.tutorial.catalogservice.service;

import com.tutorial.catalogservice.dto.CatalogDto;
import com.tutorial.catalogservice.jpa.CatalogEntity;

public interface CatalogService {
    Iterable<CatalogEntity> getAllCatalogs();
}
