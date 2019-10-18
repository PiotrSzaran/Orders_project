package pl.szaran.repository;

import pl.szaran.model.Producer;

import java.util.List;
import java.util.Optional;

public interface ProducerRepository extends GenericRepository<Producer> {
    Optional<Producer> findByName(String producerName);
    boolean isProducerWithNameCountryTrade(String name, String countryName, String tradeName);
    Optional<Producer> findByNameCountryTrade(String name, String countryName, String tradeName);
    List<Producer> getProducerFromStock();
}
