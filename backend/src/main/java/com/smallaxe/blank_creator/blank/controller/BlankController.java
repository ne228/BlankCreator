package com.smallaxe.blank_creator.blank.controller;

import com.smallaxe.blank_creator.blank.entity.BlankHub;
import com.smallaxe.blank_creator.blank.models.*;
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
@RequestMapping("api/hub")
public class BlankController {

    private final BlankService blankService;
    private final BlankHubService blankHubService;

    private final PrintService printService;
    private final BLankTemplateService bLankTemplateService;

    public BlankController(BlankService blankService, BlankHubService blankHubService, PrintService printService, BLankTemplateService bLankTemplateService) {
        this.blankService = blankService;
        this.blankHubService = blankHubService;
        this.printService = printService;
        this.bLankTemplateService = bLankTemplateService;
    }


    @GetMapping("")
    public ResponseEntity<?> getHubs() throws CredentialException {
        return ResponseEntity.ok().body(blankHubService.getHubs());
    }


    @GetMapping("/get")
    public ResponseEntity<?> getBlanksByHubId(@RequestParam String id) throws CredentialException {
        BlankHub blankHub = blankHubService.getHubById(id);
        if (blankHub == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(blankHub);
    }

    @GetMapping("/blanks")
    public ResponseEntity<?> getAllBlanks() throws CredentialException {
        return ResponseEntity.ok().body(blankService.getAllBlanks());
    }

    @PostMapping("/create")
    public ResponseEntity<?> hubCreate(@Valid @RequestBody BlankHubCreateDto blankHubCreateDto) throws CredentialException {
        return ResponseEntity.ok().body(blankHubService.create(blankHubCreateDto));
    }

    @PostMapping("/edit")
    public ResponseEntity<?> hubEdit(@Valid @RequestBody BlankHubEditDto blankHubEditDto) throws CredentialException {
        return ResponseEntity.ok().body(blankHubService.edit(blankHubEditDto));
    }

    @PostMapping("/blank/create")
    public ResponseEntity<?> createBlank(@Valid @RequestBody BlankCreateDto blankCreateDto) throws CredentialException {
        return ResponseEntity.ok().body(blankService.create(blankCreateDto));
    }


    @PostMapping("/blank/edit")
    public ResponseEntity<?> editBlank(@Valid @RequestBody BlankEditDto blankEditDto) throws CredentialException {
        return ResponseEntity.ok().body(blankService.edit(blankEditDto));
    }

    @GetMapping("/blank/{id}")
    public ResponseEntity<?> getBlank(@Valid @PathVariable String id) throws CredentialException {
        var blank = blankService.getBlankById(id);
        return ResponseEntity.ok().body(blank);
    }


    @PostMapping("/blank/success/{id}")
    public ResponseEntity<?> successBlank(@Valid @PathVariable String id) throws CredentialException {
        var blank = blankService.successBlank(id);
        return ResponseEntity.ok().body(blank);
    }

    @PostMapping("/blank/reject/{id}")
    public ResponseEntity<?> rejectBlank(@Valid @PathVariable String id) throws CredentialException {
        var blank = blankService.rejectBlank(id);
        return ResponseEntity.ok().body(blank);
    }

    @GetMapping("templates")
    public ResponseEntity<?> getTemplates() throws CredentialException {
        return ResponseEntity.ok().body(bLankTemplateService.getBlankTemplates());
    }

    @PostMapping("templates/import")
    public ResponseEntity<?> importTemplateById(@RequestBody ImportTemplateDto dto) throws CredentialException {
        return ResponseEntity.ok().body(bLankTemplateService.addBlankTemplateToHub(dto));
    }

    @PostMapping("/blank/print/{hubId}")
    public ResponseEntity<?> printHub(@Valid @PathVariable String hubId, @RequestBody List<String> idsBlank) throws Exception {
        var hub = blankHubService.getHubById(hubId);
        if (hub == null)
            throw new ObjectNotFoundException(hubId, "Не найден blankHub");

        if (idsBlank.size() == 0)
            throw new Exception("idsBLank size must be more 0 ");

        var blanksToPrint = hub.getBlankList().stream()
                .filter(blank -> idsBlank.contains(blank.getId()))
                .collect(Collectors.toList());

        byte[] bytes = printService.print(blanksToPrint);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "example.docx");

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }
}
