package com.petstore.mapper;

import java.util.List;
import org.mapstruct.Mapper;

import com.petstore.dto.PetDTO;
import com.petstore.model.Pet;

@Mapper
public interface PetMapper {
    PetDTO toPetDTO(Pet pet);

    List<PetDTO> toPetDTOs(List<Pet> pets);

    Pet toPet(PetDTO petDTO);
}
