package pl.szaran.service;

import pl.szaran.dto.ErrorDTO;
import pl.szaran.model.Error;
import pl.szaran.repository.ErrorRepository;
import pl.szaran.repository.ErrorRepositoryImpl;

import java.time.LocalDateTime;

public class ErrorService implements ModelMapper{
    final ErrorRepository errorRepository = new ErrorRepositoryImpl();

    public void addError(String message) {

        Error error = null;
        error = ModelMapper.fromErrorDTOToError(ErrorDTO.builder().message(message).date(LocalDateTime.now()).build());
        errorRepository.saveOrUpdate(error);


    }

}
