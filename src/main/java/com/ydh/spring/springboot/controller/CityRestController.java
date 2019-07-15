package com.ydh.spring.springboot.controller;

import com.ydh.spring.springboot.domain.City;
import com.ydh.spring.springboot.service.CacheService;
import com.ydh.spring.springboot.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CityRestController {

    @Autowired
    private CityService cityService;

    @Autowired
    private CacheService cacheService;

    // 展示城市的列表 http://localhost:8866/api/city/1
    @RequestMapping(value = "/api/city/{id}", method = RequestMethod.GET)
    public City findOneCity(@PathVariable("id") Long id) {
        return cityService.findCityById(id);
    }

    // 添加一个新的城市
    @RequestMapping(value = "/api/city", method = RequestMethod.POST)
    public void createCity(@RequestBody City city) {
        cityService.saveCity(city);
    }

    @RequestMapping(value = "/api/city", method = RequestMethod.PUT)
    public void modifyCity(@RequestBody City city) {
        cityService.updateCity(city);
    }

    @RequestMapping(value = "/api/city/{id}", method = RequestMethod.DELETE)
    public void modifyCity(@PathVariable("id") Long id) {
        cityService.deleteCity(id);
    }

    @RequestMapping(value = "api/cache", method = RequestMethod.POST)
    public String addHashCache(@RequestParam(value = "key") String key,
                               @RequestParam(value = "value") String value) {
        return cacheService.addCache(key, value);

    }

}
