package com.smallaxe.blank_creator.blank.controller;

import com.smallaxe.blank_creator.blank.service.BlankHubService;
import com.smallaxe.blank_creator.blank.service.PrintService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("api")
public class PrintController {
    private final BlankHubService blankHubService;
    private final PrintService printService;

    public PrintController(BlankHubService blankHubService, PrintService printService) {
        this.blankHubService = blankHubService;
        this.printService = printService;
    }

    @PostMapping("/print/{hubId}")
    public ResponseEntity<?> printHub(@Valid @PathVariable String hubId, @RequestBody List<String> idsBlank) throws Exception {
        var hub = blankHubService.getHubById(hubId);
        if (hub == null) throw new ObjectNotFoundException(hubId, "Не найден blankHub");

        if (idsBlank.size() == 0) throw new Exception("idsBLank size must be more 0 ");

        var blanksToPrint = hub.getBlankList().stream().filter(blank -> idsBlank.contains(blank.getId())).collect(Collectors.toList());

        byte[] bytes = printService.print(blanksToPrint);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "example.docx");

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }
}
