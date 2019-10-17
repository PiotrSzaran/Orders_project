package pl.szaran.repository;

import pl.szaran.model.Shop;

import java.util.Optional;

public interface ShopRepository extends GenericRepository<Shop> {
    Optional<Shop> findByName(String name);
    boolean isShopWithNameCountry(String name, String countryName);
    Optional<Shop> findByNameCountry(String name, String countryName);
}