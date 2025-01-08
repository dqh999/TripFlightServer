package com.airline.application.airport.service.impl;

import com.airline.application.airport.dataTransferObject.request.AddAirportRequest;
import com.airline.application.airport.dataTransferObject.response.AirportResponse;
import com.airline.application.airport.mapper.AirportMapper;
import com.airline.application.airport.service.IAirportUseCase;
import com.airline.application.utils.PageResponse;
import com.airline.domain.airport.model.Airport;
import com.airline.domain.airport.service.IAirportService;
import com.airline.infrastructure.security.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirportUseCase implements IAirportUseCase {
    private final IAirportService airlineService;
    private final AirportMapper airlineMapper;

    @Autowired
    public AirportUseCase(IAirportService airlineService,
                          AirportMapper airlineMapper) {
        this.airlineService = airlineService;
        this.airlineMapper = airlineMapper;
    }

    @Override
    public AirportResponse add(UserDetail userRequest,
                                      AddAirportRequest request) {

        Airport airlineObject = airlineMapper.toAirport(request);
        Airport newAirport = airlineService.addAirport(airlineObject);

        return airlineMapper.toDTO(newAirport);
    }

    @Override
    public AirportResponse getById(String id) {
        Airport exist = airlineService.getAirport(id);
        return airlineMapper.toDTO(exist);
    }

    @Override
    public PageResponse<AirportResponse>  search(String keyword, int pageNo, int pageSize) {
        Page<Airport> pageResult = airlineService.search(keyword,pageNo-1,pageSize);
        List<AirportResponse> airlineResponses = pageResult.getContent().stream().map(airlineMapper::toDTO).collect(Collectors.toList());

        return new PageResponse<>(
                (int) pageResult.getTotalElements(),
                pageResult.getTotalPages(),
                pageNo,
                pageSize,
                airlineResponses,
                pageResult.hasNext(),
                pageResult.hasPrevious()
        );
    }

}
