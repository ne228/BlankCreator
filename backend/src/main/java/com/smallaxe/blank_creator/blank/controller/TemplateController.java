package com.smallaxe.blank_creator.blank.controller;


import com.smallaxe.blank_creator.blank.models.ImportTemplateDto;
import com.smallaxe.blank_creator.blank.service.BLankTemplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.CredentialException;

@Controller
@RequestMapping("api/templates")
public class TemplateController {

    private final BLankTemplateService bLankTemplateService;

    public TemplateController(BLankTemplateService bLankTemplateService) {
        this.bLankTemplateService = bLankTemplateService;
    }

    @GetMapping()
    public ResponseEntity<?> getTemplates() throws CredentialException {
        return ResponseEntity.ok().body(bLankTemplateService.getBlankTemplates());
    }

    @PostMapping("/import")
    public ResponseEntity<?> importTemplateById(@RequestBody ImportTemplateDto dto) throws CredentialException {
        return ResponseEntity.ok().body(bLankTemplateService.addBlankTemplateToHub(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTemplateById(@PathVariable String id) throws CredentialException {
        return ResponseEntity.ok().body(bLankTemplateService.deleteBlankTemplate(id));
    }
}
