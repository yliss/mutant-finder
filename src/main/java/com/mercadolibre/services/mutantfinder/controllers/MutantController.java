package com.mercadolibre.services.mutantfinder.controllers;

import com.mercadolibre.services.mutantfinder.models.Human;
import com.mercadolibre.services.mutantfinder.models.HumanClassificationStatistics;
import com.mercadolibre.services.mutantfinder.models.Message;
import com.mercadolibre.services.mutantfinder.services.DNAFinderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.mercadolibre.services.mutantfinder.utils.StringMessages.*;

@Controller
@RequestMapping(path = "/mutant")
@Validated
public class MutantController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MutantController.class);
    private final DNAFinderService dnaFinderService;

    @Autowired
    public MutantController(DNAFinderService dnaFinderService) {
        this.dnaFinderService = dnaFinderService;
    }

    @PostMapping
    @CrossOrigin
    public ResponseEntity<Message> isMutant(@Valid
                                            @NotNull(message = HUMAN_IS_REQUIRED) @RequestBody Human human) {
        LOGGER.info("Method is mutant from controller started");
        boolean theHumanIsAMutant = dnaFinderService
                .isMutant(human
                        .getDna()
                        .stream()
                        .toArray(String[]::new));

        Message message = new Message();
        HttpStatus status;
        if (theHumanIsAMutant) {
            status = HttpStatus.OK;
            message.setDescription(HUMAN_IS_MUTANT);
        } else {
            status = HttpStatus.FORBIDDEN;
            message.setDescription(HUMAN_IS_NOT_MUTANT);
        }

        LOGGER.info("Method is mutant from controller ends");
        return ResponseEntity.status(status).body(message);
    }

    @GetMapping
    @RequestMapping(path = "/stats")
    @CrossOrigin
    public ResponseEntity<HumanClassificationStatistics> stats() {
        HumanClassificationStatistics humanClassificationStatistics = dnaFinderService.retrieveStatistics();

        if(humanClassificationStatistics != null) {
            return ResponseEntity.status(HttpStatus.OK).body(humanClassificationStatistics);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }

}
