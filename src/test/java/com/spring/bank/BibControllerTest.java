package com.spring.bank;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.Principal;
import java.text.MessageFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.spring.bieb.BibController;

import domain.Auteur;
import domain.Boek;
import domain.Favoriet;
import domain.Locatie;
import jakarta.validation.Valid;
import repository.AuteurRepository;
import repository.BoekRepository;
import repository.FavorietenRepository;
import repository.LocatieRepository;
import validator.AddValidation;

@SpringBootTest(classes = BibController.class)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class BibControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BoekRepository boekRepository;
    @Mock
    private AddValidation addValidation;
    @Mock
    private AuteurRepository auteurRepository;
    @Mock
    private LocatieRepository locatieRepository;
    @Mock
    private FavorietenRepository favorietenRepository;
    @Mock
    private MessageSource messageSource;
    @Mock
    private Principal principal;
    @Mock
    private Authentication auth;
    @Mock
    private Model model;
    @Mock
    private BindingResult result;

    private BibController controller;

    @BeforeEach
    public void before() {
        MockitoAnnotations.openMocks(this);
        controller = new BibController();
    }

    @Test
    public void testListBoeken() {
        List<Boek> boekList = List.of(new Boek(), new Boek());
        Mockito.when(principal.getName()).thenReturn("username");
        Mockito.when(boekRepository.findAll()).thenReturn(boekList);

        String viewName = controller.listBoeken(model, principal);

        Mockito.verify(model).addAttribute("userName", "username");
        Mockito.verify(model).addAttribute("boekList", boekList);
        assertEquals("overzicht", viewName);
    }

    @Test
    public void testBoekDetail() {
        Long index = 1L;
        Boek boek = new Boek();
        boek.setISBNnummer(index);
        List<Favoriet> favoriteBoeken = List.of(new Favoriet());
        Optional<Boek> boekOptional = Optional.of(boek);
        Mockito.when(principal.getName()).thenReturn("username");
        Mockito.when(favorietenRepository.findAllBoeksByUserName("username")).thenReturn(favoriteBoeken);
        Mockito.when(boekRepository.findById(index)).thenReturn(boekOptional);

        String viewName = controller.boekDetail(index, model, principal);

        Mockito.verify(model).addAttribute("userName", "username");
        Mockito.verify(model).addAttribute("aantalFavorieten", favoriteBoeken.size());
        Mockito.verify(model).addAttribute("isFavoriet", null);
        Mockito.verify(model).addAttribute("boek", boek);
        assertEquals("boekdetail", viewName);
    }

    @Test
    public void testProcessFavorietToevoegen() {
        Long id = 1L;
        Favoriet favoriet = new Favoriet();
        favoriet.setBoek(new Boek());
        Mockito.when(principal.getName()).thenReturn("username");
        Mockito.when(messageSource.getMessage(Mockito.anyString(), Mockito.isNull(), Mockito.any())).thenReturn("message");
        Mockito.when(boekRepository.findByISBNnummer(id)).thenReturn(new Boek());

        String viewName = controller.processFavorietToevoegen(id, favoriet, result, model, principal);

        Mockito.verify(model).addAttribute(Mockito.eq("textWeergave"), Mockito.anyString());
        Mockito.verify(favorietenRepository).save(Mockito.any(Favoriet.class));
        Mockito.verify(boekRepository).save(Mockito.any(Boek.class));
        assertEquals("redirect:/home", viewName);
    }

    @Test
    public void testProcessFavorietVerwijderen() {
        Long id = 1L;
        Favoriet favoriet = new Favoriet();
        favoriet.setBoek(new Boek());
        Mockito.when(principal.getName()).thenReturn("username");
        Mockito.when(messageSource.getMessage(Mockito.anyString(), Mockito.isNull(), Mockito.any())).thenReturn("message");
        Mockito.when(boekRepository.findByISBNnummer(id)).thenReturn(new Boek());
        Mockito.when(favorietenRepository.findBoekByISBNAndUsername(Mockito.anyLong(), Mockito.anyString()))
                .thenReturn(Optional.of(new Favoriet()));

        String viewName = controller.processFavorietVerwijderen(id, favoriet, result, model, principal);

        Mockito.verify(model).addAttribute(Mockito.eq("textWeergave"), Mockito.anyString());
        Mockito.verify(favorietenRepository).delete(Mockito.any(Favoriet.class));
        assertEquals("redirect:/home", viewName);
    }

    @Test
    public void testPopulaireBoeken() {
        List<Boek> boekList = List.of(new Boek(), new Boek());
        Mockito.when(principal.getName()).thenReturn("username");
        Mockito.when(boekRepository.findAll()).thenReturn(boekList);

        String viewName = controller.populaireBoeken(model, principal);

        Mockito.verify(model).addAttribute("userName", "username");
        Mockito.verify(model).addAttribute("boekList", boekList);
        assertEquals("populaireKeuzes", viewName);
    }

    @Test
    public void testToevoegenBoek() {
        List<String> roles = List.of("ROLE_ADMIN");
        Mockito.when(principal.getName()).thenReturn("username");

        String viewName = controller.toevoegenBoek(model, auth, principal);

        Mockito.verify(model).addAttribute("userName", "username");
        Mockito.verify(model).addAttribute("userListRoles", roles);
        Mockito.verify(model).addAttribute(Mockito.any(Boek.class));
        assertEquals("toevoegenBoek", viewName);
    }

    @Test
    public void testProcessBoekToevoegen() {
        Boek boek = new Boek();
        List<Auteur> auteurs = List.of(new Auteur());
        List<Locatie> locaties = List.of(new Locatie());
        Mockito.when(boek.getAuteurs()).thenReturn(auteurs);
        Mockito.when(boek.getLocaties()).thenReturn(locaties);

        String viewName = controller.processBoekToevoegen(boek, result, model, auth);

        Mockito.verify(addValidation).validate(boek, result);
        Mockito.verify(auteurRepository).saveAll(auteurs);
        Mockito.verify(locatieRepository).saveAll(locaties);
        Mockito.verify(boekRepository).save(boek);
        assertEquals("redirect:/home", viewName);
    }
}
