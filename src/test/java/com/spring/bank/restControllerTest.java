package com.spring.bank;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.spring.bieb.restController;

import domain.Auteur;
import domain.Boek;
import domain.Locatie;
import repository.BoekRepository;

@SpringJUnitWebConfig(com.spring.bieb.BibController.class)
public class restControllerTest {

    @Mock
    private BoekRepository boekRepository;

    @InjectMocks
    private restController controller;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetBoekenVanAuteur() throws Exception {
        // Mock data
        List<Boek> mockBoeken = new ArrayList<>();
        List<Auteur> auteurs8 = List.of(new Auteur("Harper Lee"));
        List<Auteur> auteurs2 = List.of(new Auteur("J.R.R. Tolkien"), new Auteur("Jane Austen"));
        List<Locatie> locaties = List.of(new Locatie("250", "200", "StandaardBoekhandel"),
                new Locatie("100", "245", "BiebAalst"));
        mockBoeken.add(new Boek(Long.parseLong("9578954685897"), "To Kill a Mockingbird", auteurs8, 12.99, 5, locaties,
                "https://encyclopediaofalabama.org/wp-content/uploads/2023/02/m-2908.jpg"));
        mockBoeken.add(new Boek(Long.parseLong("9780618346264"), "The Two Towers", auteurs2, 16.99, 4, locaties,
                "https://images.booksense.com/images/245/380/9780358380245.jpg"));

        // Mock the repository method
        when(boekRepository.findByAuteurs_AuteurNaam("Author 1")).thenReturn(mockBoeken);

        // Perform the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/boek/{auteur}", "Author 1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Book 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Book 2"));
    }

    @Test
    public void testGetBoekOpISBN() throws Exception {
        // Mock data
        Boek mockBoek = new Boek(null, "Book 1", null, 0, 0, null, "Author 1");
        mockBoek.setISBNnummer(123456789L);

        // Mock the repository method
        when(boekRepository.findByISBNnummer(123456789L)).thenReturn(mockBoek);

        // Perform the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/boekOpISBN/{ISBN}", 123456789L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Book 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ISBNnummer").value(123456789));
    }
}
