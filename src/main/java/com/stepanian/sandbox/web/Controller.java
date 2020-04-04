package com.stepanian.sandbox.web;

import com.stepanian.sandbox.counterApi.entity.Category;
import com.stepanian.sandbox.counterApi.entity.Exercise;
import com.stepanian.sandbox.counterApi.service.RepositoryService;
import com.stepanian.sandbox.pojo.DebitCard;
import com.stepanian.sandbox.util.StringModifier;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class Controller {

    private final RepositoryService repositoryService;

    @PostMapping(value = "/api/addCategory")
    public HttpEntity<HttpStatus> addCategory(@RequestBody Category category) {
        repositoryService.saveCategory(category);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/api/deleteCategory")
    public HttpEntity<HttpStatus> deleteCategory(@RequestParam Integer id) {
        repositoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/api/allCategories")
    public HttpEntity<List<Category>> allCategories() {
        return new ResponseEntity<>(repositoryService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/api/exercisesByCategory")
    public HttpEntity<List<Exercise>> exercisesByCategory(@RequestParam Integer category) {
        return new ResponseEntity<>(repositoryService.getExercisesByCategory(category), HttpStatus.OK);
    }

    @GetMapping(value = "/api/allExercises")
    public HttpEntity<List<Exercise>> allExercises() {
        return new ResponseEntity<>(repositoryService.getAllExercises(), HttpStatus.OK);
    }

    ////////////////////////////////////////////////////////////

    @GetMapping(value = "/api/getCard")
    public HttpEntity<DebitCard> returnCard() {
        return new ResponseEntity<>(card(), HttpStatus.OK);
    }

    private DebitCard card() {
        String cardNumber = "1563145896354789";
        return new DebitCard(cardNumber, StringModifier.maskCardNumber(cardNumber),
                LocalDate.of(2021, 12, 5), "Kluch", BigDecimal.valueOf(156983.89)
        );
    }
}
