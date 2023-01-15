package com.alexIntervale1.app2.mapper;

import com.alexIntervale1.app2.dto.RequestDto;
import com.alexIntervale1.app2.model.RequestMessage;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    RequestDto toDto(RequestMessage message);
    RequestMessage toModel(RequestDto dto);

    default void updateRequestMessageFromDto(RequestDto dto,@MappingTarget RequestMessage message) {
        if (dto != null) {
            message.setName(dto.getName());
            message.setSurname(dto.getSurname());
            message.setPersonalNumber(dto.getPersonalNumber());
        }
    }
}
