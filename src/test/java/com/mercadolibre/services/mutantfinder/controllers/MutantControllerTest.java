package com.mercadolibre.services.mutantfinder.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.services.mutantfinder.data.repositories.HumanRepository;
import com.mercadolibre.services.mutantfinder.exceptions.handlers.RestExceptionHandler;
import com.mercadolibre.services.mutantfinder.mappers.HumanClassificationMapper;
import com.mercadolibre.services.mutantfinder.models.Human;
import com.mercadolibre.services.mutantfinder.models.HumanClassificationStatistics;
import com.mercadolibre.services.mutantfinder.services.DNAFinderService;
import com.mercadolibre.services.mutantfinder.services.impl.DNAFinderServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@RunWith(SpringRunner.class)
@WebMvcTest(MutantController.class)
public class MutantControllerTest {
    //@Autowired
    private MockMvc mvcMock;
    private MockMvc mvc;

    @MockBean
    private DNAFinderService dnaFinderMockService;

    private HumanClassificationMapper classificationMapper;
    private DNAFinderService dnaFinderService;
    private HumanRepository humanRepository;

    @Before
    public void setUp(){
        classificationMapper = mock(HumanClassificationMapper.class);
        humanRepository = mock(HumanRepository.class);
        dnaFinderService = new DNAFinderServiceImpl(humanRepository, classificationMapper);
        mvcMock = MockMvcBuilders.standaloneSetup(new MutantController(dnaFinderMockService))
                .setControllerAdvice(new RestExceptionHandler())
                .build();
        mvc = MockMvcBuilders.standaloneSetup(new MutantController(dnaFinderService))
                .setControllerAdvice(new RestExceptionHandler())
                .build();
    }

    @Test
    public void givenAValidDNAArrayThenShouldReturnStatus200()
            throws Exception {
        final ObjectMapper mapper = new ObjectMapper();

        given(dnaFinderMockService.isMutant(Mockito.any())).willReturn(true);

        List<String> dna = new ArrayList<>();
        dna.add("ATGC");
        dna.add("CGGT");
        dna.add("TTAT");
        dna.add("AGAA");

        Human human = new Human();
        human.setDna(dna);

        mvcMock.perform(post("/mutant/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(human)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verify(dnaFinderMockService,
                VerificationModeFactory.times(1)).isMutant(Mockito.any());
        reset(dnaFinderMockService);
    }

    @Test
    public void givenADNAArrayThatHaveOneRowThenShouldReturnStatus403()
            throws Exception {
        final ObjectMapper mapper = new ObjectMapper();

        List<String> dna = new ArrayList<>();
        dna.add("A");

        Human human = new Human();
        human.setDna(dna);

        mvcMock.perform(post("/mutant/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(human)))
                .andExpect(status().isForbidden())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verify(dnaFinderMockService,
                VerificationModeFactory.times(1)).isMutant(Mockito.any());
        reset(dnaFinderMockService);
    }

    @Test
    public void givenAValidDNAArrayWithNotVectorInsideThenShouldReturnStatus400()
            throws Exception {
        final ObjectMapper mapper = new ObjectMapper();

        given(dnaFinderMockService.isMutant(Mockito.any())).willReturn(false);

        List<String> dna = new ArrayList<>();
        dna.add("ATGC");

        Human human = new Human();
        human.setDna(dna);

        mvc.perform(post("/mutant/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(human)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        reset(dnaFinderMockService);
    }

    @Test
    public void givenAnInvalidDNAArrayThenShouldReturnStatus400()
            throws Exception {
        final ObjectMapper mapper = new ObjectMapper();

        given(dnaFinderMockService.isMutant(Mockito.any())).willReturn(true);

        Human human = new Human();

        mvcMock.perform(post("/mutant/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(human)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        reset(dnaFinderMockService);
    }

    @Test
    public void givenAnEmptyJSONThenShouldReturnStatus400()
            throws Exception {
        given(dnaFinderMockService.isMutant(Mockito.any())).willReturn(true);

        mvcMock.perform(post("/mutant/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        reset(dnaFinderMockService);
    }

    @Test
    public void givenACallToStatsThenShouldReturnStatus200()
            throws Exception {
        final HumanClassificationStatistics humanClassificationStatistics = new HumanClassificationStatistics();

        given(dnaFinderMockService.retrieveStatistics()).willReturn(humanClassificationStatistics);

        mvcMock.perform(get("/mutant/stats")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verify(dnaFinderMockService,
                VerificationModeFactory.times(1)).retrieveStatistics();
        reset(dnaFinderMockService);
    }

    @Test
    public void givenACallToStatsAndThereAreNotStatsThenShouldReturnStatus204()
            throws Exception {
        given(dnaFinderMockService.retrieveStatistics()).willReturn(null);

        mvcMock.perform(get("/mutant/stats")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(dnaFinderMockService,
                VerificationModeFactory.times(1)).retrieveStatistics();
        reset(dnaFinderMockService);
    }

}