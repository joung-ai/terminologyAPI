package com.terminology.terminologyapi.controller;

import com.terminology.terminologyapi.entity.Icd10Code;
import com.terminology.terminologyapi.repository.Icd10Repository;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/icd10")
@CrossOrigin // allow frontend access
public class Icd10Controller {

    private final Icd10Repository repo;

    public Icd10Controller(Icd10Repository repo) {
        this.repo = repo;
    }

    // Search ICD-10
    @GetMapping("/search")
    public List<Icd10Code> search(@RequestParam String q) {
        List<Icd10Code> result = repo.search(q);
        System.out.println("RESULT SIZE = " + result.size());
        return result;
    }


    // Autocomplete
    @GetMapping("/autocomplete")
    public List<Icd10Code> autocomplete(@RequestParam String q) {
        return repo.autocomplete(q + "%");
    }

    // Get by code
    @GetMapping("/code/{code}")
    public Icd10Code getByCode(@PathVariable String code) {
        return repo.findByCode(code)
                .orElseThrow(() -> new RuntimeException("ICD-10 code not found"));
    }


}

