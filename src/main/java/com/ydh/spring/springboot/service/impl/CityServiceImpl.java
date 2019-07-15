package com.ydh.spring.springboot.service.impl;

import com.ydh.spring.springboot.dao.CityDao;
import com.ydh.spring.springboot.domain.City;
import com.ydh.spring.springboot.service.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
@Service
public class CityServiceImpl implements CityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CityServiceImpl.class);

    @Autowired
    private CityDao cityDao;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据id获得城市，
     * 首先确定key的格式，然后实例化一个操作redis的对象：redisTemplate.opsForValue()
     * 如果缓存中有这个城市，直接从缓存中读取并返回
     * 如果缓存中没有城市，就从数据库中读取这个城市，并且把城市的信息按照键值对的形式存入到redis缓存中
     *
     * @param id
     * @return
     */
    public City findCityById(Long id) {
        String key = "city_" + id;

        ValueOperations<String, City> operations = redisTemplate.opsForValue();


        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            City city = operations.get(key);
            LOGGER.info("CityServiceImpl.findCityById() : 从缓存中获得了城市>>" + city.toString());
            return city;
        }

        City city = cityDao.findById(id);

        //设置了缓存的有效时间
        operations.set(key, city, 10, TimeUnit.SECONDS);
        LOGGER.info("CityServiceImpl.findCityById() : 插入缓存 >>" + city.toString());
        return city;

    }


    /**
     * 保存一个新城市，因为是新的所以和缓存无关
     *
     * @param city
     * @return
     */
    @Override
    public Long saveCity(City city) {
        return cityDao.saveCity(city);
    }

    /**
     * 更新城市信息：首先更新数据库，然后更新缓存
     * 如果缓存中存在旧信息，那么就删除缓存，如果缓存不存在就不操作缓存了
     *
     * @param city
     * @return
     */
    @Override
    public Long updateCity(City city) {
        Long ert = cityDao.updateCity(city);

        String key = "city_" + city.getId();

        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {

            redisTemplate.delete(key);
            LOGGER.info("CityServiceImpl.updateCity() : 从缓存中删除城市>>" + city.toString());
        }

        return ert;
    }

    @Override
    public Long deleteCity(Long id) {
        Long ret = cityDao.deleteCity(id);

        String key = "city_" + id;

        boolean hasKey = redisTemplate.hasKey(key);

        if (hasKey) {
            redisTemplate.delete(key);
            LOGGER.info("CityServiceImpl.deleteCity() : 从缓存中删除城市" + id);
        }
        return ret;
    }
}
