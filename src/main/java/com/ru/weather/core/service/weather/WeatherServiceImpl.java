package com.ru.weather.core.service.weather;

import com.ru.weather.api.controller.mapper.Mapper;
import com.ru.weather.core.dto.WeatherDto;
import com.ru.weather.core.model.openweatherapi.current.Current;
import com.ru.weather.core.model.openweatherapi.other.AllData;
import com.ru.weather.core.model.openweatherapi.other.Daily;
import com.ru.weather.core.service.responseopenweatherapi.GettingDataFromOtherApi;
import com.ru.weather.db.entity.city.CityEntity;
import com.ru.weather.db.entity.city.CityEntityRepository;
import com.ru.weather.db.entity.weather.WeatherEntity;
import com.ru.weather.db.entity.weather.WeatherEntityRepository;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class WeatherServiceImpl implements WeatherService {
    Logger logger = LoggerFactory.getLogger("Логгер сервиса погоды");
    @Autowired
    private WeatherEntityRepository weatherEntityRepository;
    @Autowired
    private Mapper mapper;

    @Autowired
    private CityEntityRepository cityEntityRepository;

    @Autowired
    private GettingDataFromOtherApi gettingDataFromOtherApi;

    public WeatherEntity getWeatherByNow(Long id) {
        CityEntity cityEntity = cityEntityRepository.getById(id);
        LocalDate date = LocalDate.now();
        WeatherEntity weatherEntity = weatherEntityRepository.findByCityEntityAndDateOfWeather(cityEntity, date);
        if (Objects.nonNull(weatherEntity)) {
            return weatherEntity;
        } else {
            Instant epochToLocalDate = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
            Long unixTime = epochToLocalDate.toEpochMilli();
            AllData allData = gettingDataFromOtherApi.getJsonFromOtherApi(cityEntity.getLatitude(), cityEntity.getLongitude());
            Current today = allData.getCurrent();
            WeatherDto weatherDto = mapper.map(today, WeatherDto.class);
            StringBuffer buffer = new StringBuffer("http://openweathermap.org/img/wn/@2x.png");
            buffer.insert(33, weatherDto.getIcon());
            weatherDto.setIcon(buffer.toString());
            WeatherEntity weatherToDataBase = mapper.map(weatherDto, WeatherEntity.class);
            weatherToDataBase.setCityEntity(cityEntity);
            weatherToDataBase.setDateOfWeather(Instant.ofEpochSecond(today.getDt()).atZone(ZoneId.systemDefault()).toLocalDate());
            return weatherEntityRepository.save(weatherToDataBase);
        }
    }


    public List<WeatherEntity> getAllCachedWeatherForThisCity(Long id) {
        CityEntity cityEntity = cityEntityRepository.getById(id);
        return weatherEntityRepository.findAllByCityEntity(cityEntity);

    }


    //Получение погоды на неделю
    public List<WeatherEntity> getWeeklyWeather(Long id) {
        CityEntity cityEntity = cityEntityRepository.getById(id);
        LocalDate localDate = LocalDate.now();
        List<WeatherEntity> weatherEntityList = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            WeatherEntity weatherEntityToList = weatherEntityRepository.findByCityEntityAndDateOfWeather(cityEntity, localDate);
            localDate = localDate.plusDays(i);
            if (!Objects.isNull(weatherEntityToList)) {
                weatherEntityList.add(weatherEntityToList);
            }
        }
        if (weatherEntityList.size() == 7) {
            return weatherEntityList;
        } else {
            List<WeatherEntity> weatherEntities = new ArrayList<>();
            AllData allData = gettingDataFromOtherApi.getJsonFromOtherApi(cityEntity.getLatitude(), cityEntity.getLongitude());
            List<Daily> dailyList = allData.getDaily();
            for (Daily oneDay : dailyList) {
                WeatherDto weatherDto = mapper.map(oneDay, WeatherDto.class);
                StringBuffer buffer = new StringBuffer("http://openweathermap.org/img/wn/@2x.png");
                buffer.insert(33, weatherDto.getIcon());
                weatherDto.setIcon(buffer.toString());
                LocalDate date = Instant.ofEpochSecond(oneDay.getDt()).atZone(ZoneId.systemDefault()).toLocalDate();
                if (weatherEntityRepository.findByCityEntityAndDateOfWeather(cityEntity, date) == null) {
                    weatherDto.setDateOfWeather(date);
                    WeatherEntity weatherEntityToDataBase = mapper.map(weatherDto, WeatherEntity.class);
                    weatherEntityToDataBase.setCityEntity(cityEntity);
                    weatherEntities.add(weatherEntityRepository.save(weatherEntityToDataBase));
                } else {
                    weatherEntities.add(weatherEntityRepository.findByCityEntityAndDateOfWeather(cityEntity, date));
                }
            }
            weatherEntities.remove(weatherEntities.size()-1);
            return weatherEntities;
        }
    }

    public List<WeatherEntity> getAllWeatherWithContainsLetter(String letters) {
        return weatherEntityRepository.findByCityEntity_NameContains(letters);
    }

    public ResponseEntity<InputStreamResource> getExcelFileForWeatherToday(Long id) throws IOException {

        WeatherEntity weatherEntity = getWeatherByNow(id);
        List<WeatherEntity> tempList = new ArrayList<>();
        tempList.add(weatherEntity);
        logger.info("Создание Excel файла, погода на день");
        byte[] outputStreamBytes = makeExcelFile(tempList);
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(outputStreamBytes));
        //TODO Перенести в контроллер
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment; filename=%s.xlsx", "TodayWeather"));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.setContentType(
                MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));

        ResponseEntity<InputStreamResource> responseEntity = ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(outputStreamBytes.length)
                .body(resource);
        return responseEntity;
    }

    public ResponseEntity<InputStreamResource> getExcelWeeklyWeather(Long id) throws IOException {
        logger.info("Создание Excel файла");
        List<WeatherEntity> weatherEntities = getWeeklyWeather(id);
        logger.info("Создание Excel файла, погода на неделю");
        byte[] outputStreamBytes = makeExcelFile(weatherEntities);
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(outputStreamBytes));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment; filename=%s.xlsx", "WeeklyWeather"));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.setContentType(
                MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));

        ResponseEntity<InputStreamResource> responseEntity = ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(outputStreamBytes.length)
                .body(resource);
        return responseEntity;
    }

    private byte[] makeExcelFile(List<WeatherEntity> weatherEntities) throws IOException {
        InputStream is = new FileInputStream("src/TempFiles/sample/SampleWeatherExcel.xlsx");
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Context context = new Context();
        context.putVar("weathers", weatherEntities);
        context.putVar("dateOfWeather", 123);
        JxlsHelper.getInstance().processTemplate(is, os, context);
        return os.toByteArray();

    }


    //Стандартные запросы
    public void removeWeatherById(Long id) {
        weatherEntityRepository.deleteById(id);
    }

    public WeatherEntity addWeather(WeatherEntity weatherEntity) {
        return weatherEntityRepository.save(weatherEntity);
    }

    public WeatherEntity updateWeatherById(Long id, WeatherEntity weatherEntity) {
        weatherEntity.setId(id);
        weatherEntity.setCityEntity(getWeatherById(id).getCityEntity());
        return weatherEntityRepository.save(weatherEntity);

    }

    public WeatherEntity getWeatherById(Long id) {
        return weatherEntityRepository.getById(id);
    }

    public List<WeatherEntity> getAllWeather() {
        return weatherEntityRepository.findAll();
    }


}


