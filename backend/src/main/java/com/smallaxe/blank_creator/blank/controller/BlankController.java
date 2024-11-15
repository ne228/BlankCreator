package com.smallaxe.blank_creator.blank.controller;

import com.smallaxe.blank_creator.blank.models.BlankCreateDto;
import com.smallaxe.blank_creator.blank.models.BlankEditDto;
import com.smallaxe.blank_creator.blank.models.ImportTemplateDto;
import com.smallaxe.blank_creator.blank.service.BLankTemplateService;
import com.smallaxe.blank_creator.blank.service.BlankHubService;
import com.smallaxe.blank_creator.blank.service.BlankService;
import com.smallaxe.blank_creator.blank.service.PrintService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.CredentialException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("api/blanks")
public class BlankController {

    private final BlankService blankService;
    private final BlankHubService blankHubService;

    private final PrintService printService;

    public BlankController(BlankService blankService, BlankHubService blankHubService, PrintService printService) {
        this.blankService = blankService;
        this.blankHubService = blankHubService;
        this.printService = printService;
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
