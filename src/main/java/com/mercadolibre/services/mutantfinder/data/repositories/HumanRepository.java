package com.mercadolibre.services.mutantfinder.data.repositories;

import com.mercadolibre.services.mutantfinder.data.entities.HumanEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HumanRepository extends CrudRepository<HumanEntity,Long>,CustomHumanRepository {
}