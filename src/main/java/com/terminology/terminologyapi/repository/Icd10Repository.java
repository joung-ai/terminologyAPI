package com.terminology.terminologyapi.repository;

import com.terminology.terminologyapi.entity.Icd10Code;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface Icd10Repository extends JpaRepository<Icd10Code, Long> {

    Optional<Icd10Code> findByCode(String code);

    // Full-text search
    @Query(value = """
        SELECT * FROM icd10_codes
        WHERE to_tsvector('english', description)
              @@ plainto_tsquery(:q)
        ORDER BY ts_rank(
            to_tsvector('english', description),
            plainto_tsquery(:q)
        ) DESC
        LIMIT 10
        """, nativeQuery = true)
    List<Icd10Code> search(@Param("q") String q);

    // Autocomplete
    @Query(value = """
        SELECT * FROM icd10_codes
        WHERE description ILIKE :q
        ORDER BY description
        LIMIT 10
        """, nativeQuery = true)
    List<Icd10Code> autocomplete(@Param("q") String q);
}


