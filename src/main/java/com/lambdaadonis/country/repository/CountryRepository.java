package com.lambdaadonis.country.repository;

import com.lambdaadonis.country.models.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository <Country,Long> {
}
