package graph.railgo.infrastructure.mapper;

import java.util.List;

public interface MapperEntity<E,D>{
    E toEntity(D d);
    D toDTO(E e);

    List<E> toEntities(List<D> ds);
    List<D> toDTOs(List<E> es);
}
