package pl.szaran.repository;

import pl.szaran.model.Trade;

import java.util.Optional;

public interface TradeRepository extends GenericRepository<Trade> {
    Optional<Trade> findByName (String tradeName);
}
