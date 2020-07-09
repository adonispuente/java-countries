package com.lambdaadonis.country.controller;

import com.lambdaadonis.country.models.Country;
import com.lambdaadonis.country.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
public class CountryController {

    @Autowired
    CountryRepository countryRepository;


    private List<Country> findCountry(List<Country> myList, CheckCountry tester){
        List<Country> tempList = new ArrayList<>();
        for(Country c: myList)
        {
            if(tester.test(c))
            {
                tempList.add(c);
            }
        }
        return tempList;
    }


//    http://localhost:2019/names/all
    @GetMapping(value = "names/all", produces = {"application/json"})
    public ResponseEntity<?> listAllCountries(){
        List<Country> myList = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(myList::add);

        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

//    http://localhost:2019/names/start/u
    @GetMapping(value = "names/start/{letter}", produces = {"application/json"})
    public ResponseEntity<?> ThatStartWith(@PathVariable char letter)
    {
        List<Country> myList = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(myList::add);

        List<Country> rtnList = findCountry(myList, c-> c.getName().toUpperCase().charAt(0)==letter);
//        for(Country c: rtnList)
//        {
//            System.out.println(c);
//        }
        return new ResponseEntity<>(rtnList,HttpStatus.OK);
    }
//    http://localhost:2019/population/total
    @GetMapping(value = "population/total", produces = {"application/json"})
    public ResponseEntity<?> TotalPop(){
        List<Country> myList = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(myList::add);

         double Total = 0d;
        for(Country c: myList)
        {
           Total= Total + c.getPopulation();
        }
//        DecimalFormat df = new DecimalFormat("#####0.00");

        return new ResponseEntity<>(Total,HttpStatus.OK);
    }

//    http://localhost:2019/population/min
    @GetMapping(value = "/population/min", produces = {"application/json"})
    public ResponseEntity<?> MinPopulation(){
        List<Country> myList = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(myList::add);
       myList.sort((Country c1, Country c2)-> (int) (c1.getPopulation()-c2.getPopulation()));
       Country smallest = myList.get(0);

       return new ResponseEntity<>(smallest,HttpStatus.OK);

    }

    //    http://localhost:2019/population/max
    @GetMapping(value = "/population/max", produces = {"application/json"})
    public ResponseEntity<?> MaxPopulation(){
        List<Country> myList = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(myList::add);
        myList.sort((Country c1, Country c2)-> (int) (c2.getPopulation()-c1.getPopulation()));
        Country Largest = myList.get(0);
        return new ResponseEntity<>(Largest,HttpStatus.OK);

    }


//    http://localhost:2019/population/median
    @GetMapping(value = "/population/median", produces = {"application/json"})
    public ResponseEntity<?> MedianPopulation(){
        List<Country> myList = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(myList::add);
        //sorting from largest to smallest
        myList.sort((Country c1, Country c2)-> (int) (c2.getPopulation()-c1.getPopulation()));

        double median = Math.floor(myList.size()/2);
//        if(myList.size()%2==0) {median = myList.size()%2;}

        return new ResponseEntity<>(myList.get((int) median),HttpStatus.OK);

    }
}
