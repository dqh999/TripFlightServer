package com.railgo.domain.station.repository;

import com.railgo.domain.station.model.Station;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface StationRepository {
    void save(Station s);
    Optional<Station> findById(String id);
    Page<Station> search(String keyword, Pageable pageable);
}
