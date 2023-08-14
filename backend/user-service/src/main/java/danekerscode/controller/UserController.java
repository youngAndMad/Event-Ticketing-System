package danekerscode.controller;

import danekerscode.dto.UserDTO;
import danekerscode.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static danekerscode.utils.ReturnError.validateRequest;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;


@RestController
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/")
    ResponseEntity<?> save(
            @RequestBody @Valid UserDTO dto,
            BindingResult br
    ) {
        log.info("new registration request {}" , dto);
        validateRequest(br);
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
