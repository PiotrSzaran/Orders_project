package pl.szaran.service;

import pl.szaran.dto.ErrorDTO;
import pl.szaran.exceptions.ExceptionCode;
import pl.szaran.exceptions.MyException;
import pl.szaran.model.Error;
import pl.szaran.repository.ErrorRepository;
import pl.szaran.repository.ErrorRepositoryImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ErrorService implements ModelMapper {
    final ErrorRepository errorRepository = new ErrorRepositoryImpl();

    public void addError(String message) {

        Error error = null;
        error = ModelMapper.fromErrorDTOToError(ErrorDTO.builder().message(message).date(LocalDateTime.now()).build());
        errorRepository.saveOrUpdate(error);
    }

    public Map<Integer, Error> getMapOfErrors() {
        List<Error> list = new ArrayList<>(errorRepository.findAll());
        int i = 1;
        Map<Integer, Error> errorsMap = new HashMap<>();
        for (Error e : list
        ) {
            errorsMap.put(i, e);
            i++;
        }
        return errorsMap;
    }

    //@overload
    //ta metoda służy jedynie do importu z json do DB
    public void addError(ErrorDTO errorDTO) {
        //todo rozważyć walidację (czy warto sprawdzać dane z pliku json, skoro został on wygenerowany na podstawie danych z bazy? Moim zdaniem nie ma sensu
        Error error = null;

        if (errorDTO.getId() != null) {
            error = errorRepository.findById(errorDTO.getId()).orElse(null);
        }

        if (error == null) {
            error = ModelMapper.fromErrorDTOToError(errorDTO);
            errorRepository.saveOrUpdate(error);
        }else {
            String message = "ERROR " + errorDTO.getDate() + " " + errorDTO.getMessage() + " ALREADY ADDED";
            System.out.println(message);
        }
        if (error == null) {
            System.out.println("PROBLEMS WITH ERROR");
            throw new MyException(ExceptionCode.SERVICE, "PROBLEMS WITH ERROR");
        }

    }

    public void showErrors() {
        Map<Integer, Error> map = getMapOfErrors();
        for (Map.Entry<Integer, Error> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue().getDate() + ", " + entry.getValue().getMessage());
        }
    }

    public List<ErrorDTO> getErrors() {
        return errorRepository
                .findAll()
                .stream()
                .map(ModelMapper::fromErrorToErrorDTO)
                .map(errorDTO -> ErrorDTO.builder().date(errorDTO.getDate()).message(errorDTO.getMessage()).build())
                .collect(Collectors.toList());
    }

    public void deleteErrors() {
        errorRepository.deleteAll();
    }

}
