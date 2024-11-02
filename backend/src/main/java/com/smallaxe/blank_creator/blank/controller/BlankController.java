package com.smallaxe.blank_creator.blank.controller;

import com.smallaxe.blank_creator.blank.entity.BlankHub;
import com.smallaxe.blank_creator.blank.models.BlankCreateDto;
import com.smallaxe.blank_creator.blank.models.BlankEditDto;
import com.smallaxe.blank_creator.blank.models.BlankHubCreateDto;
import com.smallaxe.blank_creator.blank.models.BlankHubEditDto;
import com.smallaxe.blank_creator.blank.service.BlankService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("api/hub")
public class BlankController {

    private final BlankService blankService;

    public BlankController(BlankService blankService) {
        this.blankService = blankService;
    }


    @GetMapping("")
    public ResponseEntity<?> getHubs() {
        return ResponseEntity.ok().body(blankService.getHubs());
    }


    @GetMapping("/get")
    public ResponseEntity<?> getBlanksByHubId(@RequestParam String id) {
        BlankHub blankHub = blankService.getHubById(id);
        if (blankHub == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(blankHub);
    }

    @PostMapping("/create")
    public ResponseEntity<?> hubCreate(@Valid @RequestBody BlankHubCreateDto blankHubCreateDto) {
        return ResponseEntity.ok().body(blankService.blankCreate(blankHubCreateDto));
    }

    @PostMapping("/edit")
    public ResponseEntity<?> hubEdit(@Valid @RequestBody BlankHubEditDto blankHubEditDto) {
        return ResponseEntity.ok().body(blankService.blankHubEdit(blankHubEditDto));
    }

    @PostMapping("/blank/create")
    public ResponseEntity<?> createBlank(@Valid @RequestBody BlankCreateDto blankCreateDto) {
        return ResponseEntity.ok().body(blankService.blankCreate(blankCreateDto));
    }


    @PostMapping("/blank/edit")
    public ResponseEntity<?> editBlank(@Valid @RequestBody BlankEditDto blankEditDto) {
        return ResponseEntity.ok().body(blankService.editBlank(blankEditDto));
    }

    @GetMapping("/blank/{id}")
    public ResponseEntity<?> getBlank(@Valid @PathVariable String id) {
        var blank = blankService.getBlankById(id);
        return ResponseEntity.ok().body(blank);
    }


    @PostMapping("/blank/success/{id}")
    public ResponseEntity<?> successBlank(@Valid @PathVariable String id) {
        var blank = blankService.successBlank(id);
        return ResponseEntity.ok().body(blank);
    }

    @PostMapping("/blank/reject/{id}")
    public ResponseEntity<?> rejectBlank(@Valid @PathVariable String id) {
        var blank = blankService.rejectBlank(id);
        return ResponseEntity.ok().body(blank);
    }

    @PostMapping("/blank/print/{hubId}")
    public ResponseEntity<?> printHub(@Valid @PathVariable String hubId, @RequestBody List<String> idsBlank) throws Exception {
        var hub = blankService.getHubById(hubId);
        if (hub == null)
            throw new ObjectNotFoundException(hubId, "Не найден blankHub");

        if (idsBlank.size() == 0)
            throw new Exception("idsBLank size must be more 0 ");

        var blanksToPrint = hub.getBlankList().stream()
                .filter(blank -> idsBlank.contains(blank.getId()))
                .collect(Collectors.toList());
        byte[] bytes = blankService.print(blanksToPrint);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "example.docx");

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }
}
