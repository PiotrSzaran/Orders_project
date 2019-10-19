package pl.szaran;

import pl.szaran.dto.CountryDTO;
import pl.szaran.service.CountryService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )

            //this is for testing :)
    {
        CountryService countryService = new CountryService();

        countryService.addCountry(CountryDTO.builder().name("PANAMA").build());
    }
}
