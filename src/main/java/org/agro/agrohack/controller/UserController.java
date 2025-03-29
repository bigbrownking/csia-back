package org.agro.agrohack.controller;

import lombok.RequiredArgsConstructor;
import org.agro.agrohack.service.UserService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

}
