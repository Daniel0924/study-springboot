package com.ydh.spring.springboot.service;

import com.ydh.spring.springboot.domain.City;

public interface CityService {

    City findCityById(Long id);

    Long saveCity(City city);

    Long updateCity(City city);

    Long deleteCity(Long id);


}
