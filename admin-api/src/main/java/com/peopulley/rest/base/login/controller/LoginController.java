package com.peopulley.rest.base.login.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(value = "로그인 API", tags = "로그인 API")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = {"/v1/api/", "/api/"})
public class LoginController {
}
