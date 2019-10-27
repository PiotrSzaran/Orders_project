package pl.szaran.service;

import pl.szaran.dto.TradeDTO;
import pl.szaran.exceptions.ExceptionCode;
import pl.szaran.exceptions.MyException;
import pl.szaran.model.Trade;
import pl.szaran.repository.TradeRepository;
import pl.szaran.repository.TradeRepositoryImpl;
import pl.szaran.validators.TradeValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TradeService implements ModelMapper{
    private final TradeRepository tradeRepository = new TradeRepositoryImpl();
    private final ErrorService errorService = new ErrorService();
    private final String TABLE = "TRADE;";

    public void addTrade(TradeDTO tradeDTO) {
        TradeValidator tradeValidator = new TradeValidator();
        //------WALIDACJA------
        var validate = tradeValidator.validateTradeData(tradeDTO);
        if (!validate.isEmpty()) {
            validate.forEach((k, v) -> System.out.println(k + " " + v));
            validate.forEach((k, v) -> errorService.addError(TABLE + v));
        } else {

            Trade trade = null;
            if (tradeDTO.getId() != null) {
                trade = tradeRepository.findById(tradeDTO.getId()).orElse(null);
            } else {
                trade = tradeRepository.findByName(tradeDTO.getName()).orElse(null);

                if (trade == null) {
                    trade = ModelMapper.fromTradeDTOToTrade(tradeDTO);
                    tradeRepository.saveOrUpdate(trade);
                } else {
                    String errorMessage = "TRADE " + tradeDTO.getName() + " ALREADY ADDED";
                    System.out.println(errorMessage);
                    errorService.addError(TABLE + errorMessage);
                }
            }

            if (trade == null) {
                errorService.addError(TABLE + "PROBLEMS WITH TRADE");
                throw new MyException(ExceptionCode.SERVICE, "PROBLEMS WITH TRADE");
            }
        }
    }

    public List<TradeDTO> getTrades() {
        return tradeRepository
                .findAll()
                .stream()
                .map(ModelMapper::fromTradeToTradeDTO)
                .map(tradeDTO -> TradeDTO.builder().name(tradeDTO.getName()).build())
                .collect(Collectors.toList());
    }

    public Map<Integer, Trade> getMapOfTrades() {
        List<Trade> list = tradeRepository.findAll();
        int i = 1;
        Map<Integer, Trade> tradesMap = new HashMap<>();
        for (Trade t : list) {
            tradesMap.put(i, t);
            i++;
        }
        return tradesMap;
    }

    public void showTrades() {
        Map<Integer, Trade> map = getMapOfTrades();
        System.out.println("W bazie znajdują się poniższe branże:");
        for (Map.Entry<Integer, Trade> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue().getName());
        }
    }

    public void deleteTrades() {
        tradeRepository.deleteAll();
    }
}
