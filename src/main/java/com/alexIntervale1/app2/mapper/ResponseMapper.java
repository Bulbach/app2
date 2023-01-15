package com.alexIntervale1.app2.mapper;

import com.alexIntervale1.app2.dto.ResponseDto;
import com.alexIntervale1.app2.model.ResponseMessage;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ResponseMapper {

    ResponseDto toDto(ResponseMessage message);
    ResponseMessage toModel(ResponseDto dto);
    void updateResponseMessageFromDto(ResponseDto dto, @MappingTarget ResponseMessage message);
}
