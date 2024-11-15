package com.smallaxe.blank_creator.blank.controller;


import com.smallaxe.blank_creator.blank.models.ImportTemplateDto;
import com.smallaxe.blank_creator.blank.service.BLankTemplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
