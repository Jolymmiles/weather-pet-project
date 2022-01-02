package com.ru.weather.core.service.weather;

import com.ru.weather.api.controller.mapper.Mapper;
import com.ru.weather.core.dto.WeatherDto;
import com.ru.weather.core.model.openweatherapi.current.Current;
import com.ru.weather.core.model.openweatherapi.other.AllData;
import com.ru.weather.core.model.openweatherapi.other.Daily;
import com.ru.weather.core.service.city.CityService;
import com.ru.weather.core.service.responseopenweatherapi.GettingDataFromOtherApi;
import com.ru.weather.db.entity.city.CityEntity;
import com.ru.weather.db.entity.city.CityEntityRepository;
import com.ru.weather.db.entity.weather.WeatherEntity;
import com.ru.weather.db.entity.weather.WeatherEntityRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class WeatherServiceImpl implements WeatherService {
    @Autowired
    private WeatherEntityRepository weatherEntityRepository;

    Logger logger = LoggerFactory.getLogger("Логгер сервиса погоды");

    @Autowired
    private Mapper mapper;

    @Autowired
    private CityService cityService;

    @Autowired
    private CityEntityRepository cityEntityRepository;

    @Autowired
    private GettingDataFromOtherApi gettingDataFromOtherApi;

    public WeatherEntity getWeatherByNow(Long id) {
        CityEntity cityEntity = cityEntityRepository.getById(id);
        LocalDate date = LocalDate.now();
        WeatherEntity weatherEntity = weatherEntityRepository.findByCityEntityAndDateOfWeather(cityEntity, date);
        if (!java.util.Objects.isNull(weatherEntity)) {
            return weatherEntity;
        } else {
            Instant epochToLocalDate = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
            Long unixTime = epochToLocalDate.toEpochMilli();
            AllData allData = gettingDataFromOtherApi.getJsonFromOtherApi(cityEntity.getLatitude(), cityEntity.getLongitude(), unixTime);
            Current today = allData.getCurrent();
            WeatherDto weatherDto = mapper.map(today, WeatherDto.class);
            StringBuffer buffer = new StringBuffer("http://openweathermap.org/img/wn/@2x.png");
            buffer.insert(33 ,weatherDto.getIcon());
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
                buffer.insert(33 ,weatherDto.getIcon());
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
            return weatherEntities;
        }
    }

    public List<WeatherEntity> getAllWeatherForCity(Long id){
        CityEntity cityEntity = cityEntityRepository.getById(id);
        return weatherEntityRepository.findAllByCityEntity(cityEntity);
    }

    public List<WeatherEntity> getAllWeatherWithContainsLetter(String letters){
        return weatherEntityRepository.findByCityEntity_NameContains(letters);
    }

    public ResponseEntity<InputStreamResource> getExcelFileForWeatherToday(Long id) throws FileNotFoundException {
        WeatherEntity weatherEntity = getWeatherByNow(id);
        makeExcelFile(weatherEntity, "Today_Weather");
        logger.info("Создание Excel файла");
        File file = new File("src/TempFiles/Today_Weather.xlsx");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment; filename=%s", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.setContentType(
                MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));

        ResponseEntity<InputStreamResource> responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length()).body(resource);
        return responseEntity;
    }

    public ResponseEntity<InputStreamResource> getExcelWeeklyWeather(Long id) throws FileNotFoundException {
        List<WeatherEntity> weatherEntities = getWeeklyWeather(id);
        makeExcelFile(weatherEntities, "WeeklyWeather");
        File file = new File("src/TempFiles/WeeklyWeather.xlsx");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment; filename=%s", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.setContentType(
                MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));

        ResponseEntity<InputStreamResource> responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length()).body(resource);
        return responseEntity;



    }

    private void makeExcelFile(List<WeatherEntity> weatherEntities, String fileName){
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Таблица 1");
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("Id записи");
        row.createCell(1).setCellValue("Дата погоды");
        row.createCell(2).setCellValue("Город");
        row.createCell(3).setCellValue("Температура");
        row.createCell(4).setCellValue("Состояние");
        row.createCell(5).setCellValue("Ссылка на иконку");
        for (int rowNumber = 1; rowNumber < weatherEntities.size(); rowNumber++){
            Row otherRow = sheet.createRow(rowNumber);
            Integer normalCount = rowNumber -1;
            otherRow.createCell(0).setCellValue(weatherEntities.get(normalCount).getId());
            otherRow.createCell(1).setCellValue(weatherEntities.get(normalCount).getDateOfWeather().toString());
            otherRow.createCell(2).setCellValue(weatherEntities.get(normalCount).getCityEntity().getName());
            otherRow.createCell(3).setCellValue(weatherEntities.get(normalCount).getTemperature());
            otherRow.createCell(4).setCellValue(weatherEntities.get(normalCount).getWeatherCondition());
            otherRow.createCell(5).setCellValue(weatherEntities.get(normalCount).getIcon());
        }
        try (OutputStream fileOut = new FileOutputStream(String.format("src/TempFiles/%s.xlsx", fileName))) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void makeExcelFile(WeatherEntity weatherEntity, String fileName){
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Таблица 1");
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("Id записи");
        row.createCell(1).setCellValue("Дата погоды");
        row.createCell(2).setCellValue("Город");
        row.createCell(3).setCellValue("Температура");
        row.createCell(4).setCellValue("Состояние");
        row.createCell(5).setCellValue("Ссылка на иконку");
        Row row2 = sheet.createRow(1);
        row2.createCell(0).setCellValue(weatherEntity.getId());
        row2.createCell(1).setCellValue(weatherEntity.getDateOfWeather());
        row2.createCell(2).setCellValue(weatherEntity.getCityEntity().getName());
        row2.createCell(3).setCellValue(weatherEntity.getTemperature());
        row2.createCell(4).setCellValue(weatherEntity.getWeatherCondition());
        row2.createCell(5).setCellValue(weatherEntity.getIcon());
        try (OutputStream fileOut = new FileOutputStream(String.format("src/TempFiles/%s.xlsx", fileName))) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }





    //Стандартные запросы
    public void removeWeatherById(Long id) {
        weatherEntityRepository.deleteById(id);
    }

    public WeatherEntity addWeather(WeatherDto weatherDto) {
        return weatherEntityRepository.save(mapper.map(weatherDto, WeatherEntity.class));
    }

    public WeatherEntity updateWeatherById(Long id, WeatherEntity weatherEntity) {
        weatherEntity.setId(id);
        return weatherEntityRepository.save(weatherEntity);

    }

    public WeatherEntity getWeatherById(Long id) {
        return weatherEntityRepository.getById(id);
    }

    public List<WeatherEntity> getAllWeather() {
        return weatherEntityRepository.findAll();
    }




}


