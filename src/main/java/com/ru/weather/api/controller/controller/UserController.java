package com.ru.weather.api.controller.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

@RestController
@RequestMapping("/api/weather")
public class UserController {

/*    @Autowired
    private Mapper mapper;

    @Autowired
    private UserServiceImpl userService;

    @ApiOperation(value = "Добавление нового пользователя")
    @PostMapping
    public UserDto addUSer(@RequestBody UserDto user) {
        return mapper.map(userService.addUser(user), UserDto.class);
    }

    @ApiOperation(value = "Получение пользователя по ID")
    @RequestMapping(value = {"{id}"})
    @GetMapping
    public UserDto getUserById(@PathVariable("id") Long id) {
        return mapper.map(userService.getUser(id), UserDto.class);
    }

    @ApiOperation(value = "Удаление пользователя по ID")
    @RequestMapping(value = {"{id}"}, method = RequestMethod.DELETE)
    @DeleteMapping
    public void deleteUserbyId(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
    }*/
}
