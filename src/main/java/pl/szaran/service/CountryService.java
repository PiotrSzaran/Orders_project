package pl.szaran.service;

import pl.szaran.dto.CountryDTO;
import pl.szaran.exceptions.ExceptionCode;
import pl.szaran.exceptions.MyException;
import pl.szaran.model.Country;
import pl.szaran.repository.CountryRepository;
import pl.szaran.repository.CountryRepositoryImpl;
import pl.szaran.validators.CountryValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CountryService implements ModelMapper {
    private final CountryRepository countryRepository = new CountryRepositoryImpl();
    private final ErrorService errorService = new ErrorService();
    private final String TABLE = "COUNTRY;";

    public void addCountry(CountryDTO countryDTO) {
        CountryValidator countryValidator = new CountryValidator();
        //------WALIDACJA------
        var validate = countryValidator.validateCountryData(countryDTO);
        if (!validate.isEmpty()) {
            validate.forEach((k, v) -> System.out.println(k + " " + v));
            validate.forEach((k, v) -> errorService.addError(TABLE + v));
        } else {

            Country country = null;
            if (countryDTO.getId() != null) {
                country = countryRepository.findById(countryDTO.getId()).orElse(null);
            } else {
                country = countryRepository.findByName(countryDTO.getName()).orElse(null);

                if (country == null) {
                    country = ModelMapper.fromCountryDTOToCountry(countryDTO);
                    countryRepository.saveOrUpdate(country);
                } else {
                    String message = "COUNTRY " + countryDTO.getName() + " ALREADY ADDED";
                    errorService.addError(TABLE + message);
                    System.out.println(message);
                }
            }

            if (country == null) {
                errorService.addError(TABLE + "PROBLEMS WITH COUNTRY");
                throw new MyException(ExceptionCode.SERVICE, "PROBLEMS WITH COUNTRY");
            }
        }
    }

    public CountryDTO getCountry(Long id) {

        if (id == null) {
            String message = "METHOD getCountry: ARGUMENT OF METHOD IS NULL";
            errorService.addError(TABLE + message);
            throw new MyException(ExceptionCode.SERVICE, "METHOD getCountry: ARGUMENT OF METHOD IS NULL");
        }

        return countryRepository
                .findById(id)
                .map(ModelMapper::fromCountryToCountryDTO)
                .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "NO COUNTRY WITH ID " + id));

    }

    public List<CountryDTO> getCountries() {
        return countryRepository
                .findAll()
                .stream()
                .map(ModelMapper::fromCountryToCountryDTO)
                .map(countryDTO -> CountryDTO.builder().name(countryDTO.getName()).build())
                .collect(Collectors.toList());
    }

    public Map<Integer, Country> getMapOfCountries() {
        List<Country> list = new ArrayList<>(countryRepository.findAll());
        int i = 1;
        Map<Integer, Country> countriesMap = new HashMap<>();
        for (Country c : list) {
            countriesMap.put(i, c);
            i++;
        }
        return countriesMap;
    }

    public void showCountries() {
        Map<Integer, Country> map = getMapOfCountries();
        for (Map.Entry<Integer, Country> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue().getName());
        }
    }

    public void deleteCountries() {
        countryRepository.deleteAll();
    }
}