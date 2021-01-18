package com.mercadolibre.services.mutantfinder.data.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Human")
public class HumanEntity {
    @Id
    private Long hashCode;
    private String dna;
    private Boolean isMutant;
}
