package pl.szaran.service;

import pl.szaran.dto.CountryDTO;
import pl.szaran.dto.ProducerDTO;
import pl.szaran.dto.TradeDTO;
import pl.szaran.exceptions.ExceptionCode;
import pl.szaran.exceptions.MyException;
import pl.szaran.model.Country;
import pl.szaran.model.Producer;
import pl.szaran.model.Trade;
import pl.szaran.repository.*;
import pl.szaran.validators.ProducerValidator;

import java.util.*;
import java.util.stream.Collectors;

public class ProducerService implements ModelMapper {
    private final ProducerRepository producerRepository = new ProducerRepositoryImpl();
    private final CountryRepository countryRepository = new CountryRepositoryImpl();
    private final TradeRepository tradeRepository = new TradeRepositoryImpl();
    private final ErrorService errorService = new ErrorService();
    private final String TABLE = "PRODUCER;";

    public void addProducer(ProducerDTO producerDTO) {
        ProducerValidator producerValidator = new ProducerValidator();
        //------WALIDACJA------
        var validate = producerValidator.validateProducerData(producerDTO);
        if (!validate.isEmpty()) {
            validate.forEach((k, v) -> System.out.println(k + " " + v));
            validate.forEach((k, v) -> errorService.addError(TABLE + v));
        } else {

            Producer producer = null;
            if (producerDTO.getId() != null) {
                producer = producerRepository.findById(producerDTO.getId()).orElse(null);
            }
            if (producer == null) {

                Country country = null;

                if (producerDTO.getCountryDTO().getId() != null) {
                    country = countryRepository.findById(producerDTO.getCountryDTO().getId()).orElse(null);
                }
                if (country == null && producerDTO.getCountryDTO().getName() != null) {
                    country = countryRepository.findByName(producerDTO.getCountryDTO().getName()).orElse(null);
                }
                if (country == null) {
                    country = countryRepository
                            .saveOrUpdate(Country.builder().name(producerDTO.getCountryDTO().getName()).build())
                            .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "CANNOT ADD NEW COUNTRY"));
                }

                Trade trade = null;
                if (producerDTO.getTradeDTO().getId() != null) {
                    trade = tradeRepository.findById(producerDTO.getTradeDTO().getId()).orElse(null);
                }
                if (trade == null && producerDTO.getTradeDTO().getName() != null) {
                    trade = tradeRepository.findByName(producerDTO.getTradeDTO().getName()).orElse(null);
                }

                if (trade == null) {
                    trade = tradeRepository
                            .saveOrUpdate(Trade.builder().name(producerDTO.getTradeDTO().getName()).build())
                            .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "CANNOT ADD NEW TRADE"));
                }

                if (producerRepository.isProducerWithNameCountryTrade(producerDTO.getName(), producerDTO.getCountryDTO().getName(), producerDTO.getTradeDTO().getName())) {
                    String errorMessage = "PRODUCER " + producerDTO.getName() + " FROM COUNTRY "
                            + producerDTO.getCountryDTO().getName() + " AND TRADE "
                            + producerDTO.getTradeDTO().getName() + " ALREADY ADDED";
                    System.out.println(errorMessage);
                    errorService.addError(TABLE + errorMessage);

                } else {
                    producer = ModelMapper.fromProducerDTOToProducer(producerDTO);
                    producer.setTrade(trade);
                    producer.setCountry(country);
                    producerRepository.saveOrUpdate(producer);

                }
            }
        }
    }

    public Producer getProducer(ProducerDTO producerDTO) {
        Producer producer = null;
        if (producerDTO == null) {
            errorService.addError(TABLE + "METHOD getProduct: ARGUMENT OF METHOD IS NULL");
            throw new MyException(ExceptionCode.SERVICE, "METHOD getProduct: ARGUMENT OF METHOD IS NULL");
        } else {
            if (producerDTO.getId() != null) {
                producer = producerRepository.findById(producerDTO.getId()).orElse(null);
            } else {
                producer = producerRepository.findByName(producerDTO.getName()).orElse(null);

                if (producer == null) {
                    producer = ModelMapper.fromProducerDTOToProducer(producerDTO);
                }
            }
        }
        return producer;
    }

    public List<Producer> getProducersFromStock() {
        return producerRepository.getProducerFromStock();
    }

    public Map<Integer, Producer> getMapOfProducers() {
        List<Producer> list = new ArrayList<>(producerRepository.findAll());
        int i = 1;
        Map<Integer, Producer> producersMap = new HashMap<>();
        for (Producer p : list) {
            producersMap.put(i, p);
            i++;
        }
        return producersMap;
    }

    public List<ProducerDTO> getProducers() {
        return producerRepository
                .findAll()
                .stream()
                .map(ModelMapper::fromProducerToProducerDTO)
                .collect(Collectors.toList());
    }

    public void showProducers() {
        Map<Integer, Producer> map = getMapOfProducers();
        for (Map.Entry<Integer, Producer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue().getName()
                    + ", kraj: " + entry.getValue().getCountry().getName()
                    + ", branża: " + entry.getValue().getTrade().getName());
        }
    }

    public void deleteProducers() {
        producerRepository.deleteAll();
    }

    //todo dorobić metodę
    public void showProducersByTradeAndQuantity() {
    }
}
