package pl.szaran.service;

import pl.szaran.dto.CountryDTO;
import pl.szaran.dto.ShopDTO;
import pl.szaran.exceptions.ExceptionCode;
import pl.szaran.exceptions.MyException;
import pl.szaran.model.Country;
import pl.szaran.model.Shop;
import pl.szaran.model.Stock;
import pl.szaran.repository.CountryRepository;
import pl.szaran.repository.CountryRepositoryImpl;
import pl.szaran.repository.ShopRepository;
import pl.szaran.repository.ShopRepositoryImpl;
import pl.szaran.validators.ShopValidator;

import java.util.*;
import java.util.stream.Collectors;

public class ShopService implements ModelMapper{
    private final ShopRepository shopRepository = new ShopRepositoryImpl();
    private final CountryRepository countryRepository = new CountryRepositoryImpl();
    private final ErrorService errorService = new ErrorService();
    private final String TABLE = "SHOP;";

    public void addShop(ShopDTO shopDTO) {
        ShopValidator shopValidator = new ShopValidator();

        //------WALIDACJA------
        var validate = shopValidator.validateShopData(shopDTO);
        if (!validate.isEmpty()) {
            validate.forEach((k, v) -> System.out.println(k + " " + v));
            validate.forEach((k, v) -> errorService.addError(TABLE + v));
        } else {

            Shop shop = null;
            if (shopDTO.getId() != null) {
                shop = shopRepository.findById(shopDTO.getId()).orElse(null);
            }
            if (shop == null) {

                Country country = null;
                if (shopDTO.getCountryDTO().getId() != null) {
                    country = countryRepository.findById(shopDTO.getCountryDTO().getId()).orElse(null);
                }

                if (country == null && shopDTO.getCountryDTO().getName() != null) {
                    country = countryRepository.findByName(shopDTO.getCountryDTO().getName()).orElse(null);
                }

                if (country == null) {
                    country = countryRepository
                            .saveOrUpdate(Country.builder().name(shopDTO.getCountryDTO().getName()).build())
                            .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "CANNOT ADD NEW COUNTRY"));
                }


                if (shopRepository.isShopWithNameCountry(shopDTO.getName(), shopDTO.getCountryDTO().getName())) {
                    String errorMessage = "SHOP " + shopDTO.getName() + " FROM  " + shopDTO.getCountryDTO().getName() + " ALREADY ADDED";
                    System.out.println(errorMessage);
                    errorService.addError(TABLE + errorMessage);
                } else {

                    shop = ModelMapper.fromShopDTOToShop(shopDTO);
                    shop.setCountry(country);
                    shopRepository.saveOrUpdate(shop);
                }
            }
        }
    }

    public List<ShopDTO> getShops() {
        return
                shopRepository.findAll()
                        .stream()
                        .map(ModelMapper::fromShopToShopDTO)
                        .collect(Collectors.toList());
    }

    public Map<Integer, Shop> getMapOfShops() {
        List<Shop> list = shopRepository.findAll();
        int i = 1;
        Map<Integer, Shop> shopsMap = new HashMap<>();
        for (Shop s : list) {
            shopsMap.put(i, s);
            i++;
        }
        return shopsMap;
    }

    public void showShops() {
        Map<Integer, Shop> map = getMapOfShops();
        for (Map.Entry<Integer, Shop> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue().getName());
        }
    }

    public void deleteShops(){shopRepository.deleteAll();}

    //todo dorobić metodę
    public void showShopsProductsWithDifferentCountries() {}
}
