package com.tintachina.service.mapper.base;

import org.mapstruct.BeanMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.MappingTarget;

/**
 * @author seonghyeon.seo
 */
public interface GenericMapper<D, E> {

    D entityToDto(E entity);
    E dtoToEntity(D dto);

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(D dto, @MappingTarget E entity);
}
