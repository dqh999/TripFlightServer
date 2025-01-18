package com.airline.booking.application.airport.service.impl;

import com.airline.booking.application.airport.dataTransferObject.request.AddAirportRequest;
import com.airline.booking.application.airport.dataTransferObject.response.AirportResponse;
import com.airline.booking.application.airport.mapper.AirportMapper;
import com.airline.booking.application.airport.service.IAirportUseCase;
import com.airline.booking.application.utils.PageResponse;
import com.airline.booking.domain.airport.model.Airport;
import com.airline.booking.domain.airport.service.IAirportService;
import com.airline.booking.infrastructure.security.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Airport newAirport = airlineService.create(airlineObject);

        return airlineMapper.toDTO(newAirport);
    }

    @Override
    public AirportResponse getById(String id) {
        Airport exist = airlineService.getById(id);
        return airlineMapper.toDTO(exist);
    }

    @Override
    public PageResponse<AirportResponse>  search(String keyword, int pageNo, int pageSize) {
        Page<Airport> pageResult = airlineService.search(keyword,pageNo,pageSize);
        List<AirportResponse> airlineResponses = airlineMapper.toDTOs(pageResult.getContent());
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
