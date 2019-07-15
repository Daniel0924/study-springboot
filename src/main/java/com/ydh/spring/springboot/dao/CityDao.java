package com.ydh.spring.springboot.dao;

import com.ydh.spring.springboot.domain.City;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CityDao {

    /**
     * 获得城市列表
     * @return
     */
    List<City> findAllCity();

    /**
     * 根据城市的id，获取城市的信息
     * @param id
     * @return
     */
    City findById(@Param("id") Long id);

    Long saveCity(City city);

    Long updateCity(City city);

    Long deleteCity(Long id);
}
