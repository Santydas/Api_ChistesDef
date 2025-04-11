package com.example.chistesyfrases.Controllers;

import com.example.chistesyfrases.Entities.ChistesYFrasesEntity;
import com.example.chistesyfrases.Services.ChistesYFrasesService;

import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/chistesyfrases")
@Validated
public class ChistesYFrasesController {

    private final ChistesYFrasesService chistesYFrasesService;

    @Autowired
    public ChistesYFrasesController(ChistesYFrasesService chistesYFrasesService) {
        this.chistesYFrasesService = chistesYFrasesService;
    }

    @GetMapping
    public ResponseEntity<?> getAllchistesYFrases(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "5") int size,
    @RequestParam(defaultValue = "chisteOFrase,asc") String[] sort){
        try{
            Pageable pageable = PageRequest.of(page, size, Sort.by(parseSort(sort)));
            return chistesYFrasesService.getAllchistes(pageable);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Orden incorrecto usa 'asc' o 'desc'.");
        }
    }

    private Sort.Order parseSort(String[] sort) {
        if (sort.length < 2) {
            throw new IllegalArgumentException("El parámetro de ordenación debe tener tanto campo como dirección");
        }

        String property = sort[0];
        String direction = sort[1].toLowerCase();

        List<String> validDirections = Arrays.asList("asc", "desc");

        if (!validDirections.contains(direction)) {
            throw new IllegalArgumentException("Orden incorrecto usa 'asc' o 'desc'.");
        }

        return new Sort.Order(Sort.Direction.fromString(direction), property);
    }



    @GetMapping("/{id}")
    public ResponseEntity<?> getChistesOFrasesById(@PathVariable UUID id){
        return chistesYFrasesService.getChistesYFrasesById(id);

    }



    @PostMapping
    public ResponseEntity<?> insertChiste(@Valid @RequestBody ChistesYFrasesEntity chistesYFrasesEntity){
        return chistesYFrasesService.addChiste(chistesYFrasesEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateChiste(@PathVariable UUID id, @Valid @RequestBody ChistesYFrasesEntity chistesYFrasesEntity){
        return chistesYFrasesService.updateChiste(id, chistesYFrasesEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChiste(@PathVariable UUID id){
        return chistesYFrasesService.deleteChiste(id);
    }

}
