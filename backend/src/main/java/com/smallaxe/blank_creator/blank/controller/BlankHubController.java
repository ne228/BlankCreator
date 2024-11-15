package com.smallaxe.blank_creator.blank.controller;


import com.smallaxe.blank_creator.blank.entity.BlankHub;
import com.smallaxe.blank_creator.blank.models.BlankHubCreateDto;
import com.smallaxe.blank_creator.blank.models.BlankHubEditDto;
import com.smallaxe.blank_creator.blank.service.BlankHubService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.CredentialException;
import javax.validation.Valid;

@Controller
@RequestMapping("api/hubs")
public class BlankHubController {

    private final BlankHubService blankHubService;


    public BlankHubController(BlankHubService blankHubService) {
        this.blankHubService = blankHubService;
    }

    @GetMapping()
    public ResponseEntity<?> getHubs() throws CredentialException {
        return ResponseEntity.ok().body(blankHubService.getHubs());
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getBlanksByHubId(@PathVariable String id) throws CredentialException {
        BlankHub blankHub = blankHubService.getHubById(id);
        if (blankHub == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(blankHub);
    }

    @PostMapping()
    public ResponseEntity<?> hubCreate(@Valid @RequestBody BlankHubCreateDto blankHubCreateDto) throws CredentialException {
        return ResponseEntity.ok().body(blankHubService.create(blankHubCreateDto));
    }

    @PutMapping()
    public ResponseEntity<?> hubEdit(@Valid @RequestBody BlankHubEditDto blankHubEditDto) throws CredentialException {
        return ResponseEntity.ok().body(blankHubService.edit(blankHubEditDto));
    }

}
