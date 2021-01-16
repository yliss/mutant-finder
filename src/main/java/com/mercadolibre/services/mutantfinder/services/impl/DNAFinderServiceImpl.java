package com.mercadolibre.services.mutantfinder.services.impl;

import com.mercadolibre.services.mutantfinder.exceptions.handlers.InvalidDataException;
import com.mercadolibre.services.mutantfinder.services.DNAFinderService;
import org.springframework.stereotype.Service;

@Service
public class DNAFinderServiceImpl implements DNAFinderService {
    @Override
    public boolean isMutant(String[] dna) {
        if(dna == null) {
            throw new InvalidDataException("The DNA parameter is required");
        }

        /*TODO: search how many vector there are inside the dna array
           remove the vector that not have 4 equals chars
        *  */
        return false;
    }
}
