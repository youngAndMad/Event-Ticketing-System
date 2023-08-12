package danekerscode.controller;

import danekerscode.dto.UserDTO;
import danekerscode.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/")
    ResponseEntity<?> save(
            @RequestBody UserDTO dto
    ) {
        return ResponseEntity
                .status(201)
                .body(userService.registration(dto));
    }

    @GetMapping("{id}")
    ResponseEntity<?> find(
            @PathVariable Long id
    ) {
        return ResponseEntity
                .ok(userService.findById(id));
    }

    @ResponseStatus(OK)
    @RequestMapping(
            path = {"{id}", "{id}"},
            method = {PUT, PATCH}
    )
    ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody UserDTO dto
    ) {
        return ResponseEntity
                .ok(userService.update(dto,id));
    }

}
