package com.airline.booking.infrastructure.mapper;

import java.util.List;

public interface MapperEntity<E,D>{
    E toEntity(D d);
    D toDTO(E e);

    List<D> toDTOs(List<E> es);
}
