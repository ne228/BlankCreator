package com.smallaxe.blank_creator.blank.controller;

import com.smallaxe.blank_creator.blank.models.BlankCreateDto;
import com.smallaxe.blank_creator.blank.models.BlankEditDto;
import com.smallaxe.blank_creator.blank.service.BlankService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.CredentialException;
import javax.validation.Valid;

@Controller
@RequestMapping("api/blanks")
public class BlankController {

    private final BlankService blankService;


    public BlankController(BlankService blankService) {
        this.blankService = blankService;
    }


    @GetMapping()
    public ResponseEntity<?> getAllBlanks() throws CredentialException {
        return ResponseEntity.ok().body(blankService.getAllBlanks());
    }

    @PostMapping()
    public ResponseEntity<?> createBlank(@Valid @RequestBody BlankCreateDto blankCreateDto) throws CredentialException {
        return ResponseEntity.ok().body(blankService.create(blankCreateDto));
    }


    @PutMapping()
    public ResponseEntity<?> editBlank(@Valid @RequestBody BlankEditDto blankEditDto) throws CredentialException {
        return ResponseEntity.ok().body(blankService.edit(blankEditDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBlank(@Valid @PathVariable String id) throws CredentialException {
        var blank = blankService.getBlankById(id);
        return ResponseEntity.ok().body(blank);
    }


    @PostMapping("/{id}/success")
    public ResponseEntity<?> successBlank(@Valid @PathVariable String id) throws CredentialException {
        var blank = blankService.successBlank(id);
        return ResponseEntity.ok().body(blank);
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<?> rejectBlank(@Valid @PathVariable String id) throws CredentialException {
        var blank = blankService.rejectBlank(id);
        return ResponseEntity.ok().body(blank);
    }


}
