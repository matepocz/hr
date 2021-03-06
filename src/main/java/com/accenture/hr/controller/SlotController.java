package com.accenture.hr.controller;

import com.accenture.hr.enums.StatusList;
import com.accenture.hr.responses.EntryResponse;
import com.accenture.hr.responses.ExitResponse;
import com.accenture.hr.responses.RegisterResponse;
import com.accenture.hr.responses.StatusResponse;
import com.accenture.hr.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/slots")
public class SlotController {

    private final SlotService slotService;

    @Autowired
    public SlotController(SlotService slotService) {
        this.slotService = slotService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestParam Long userId) {
        RegisterResponse registerResponse = slotService.registerRequest(userId);
        StatusList status = registerResponse.getStatus();
        return status.equals(StatusList.SUCCESS) ?
                new ResponseEntity<>(registerResponse, HttpStatus.OK) :
                new ResponseEntity<>(registerResponse, HttpStatus.FORBIDDEN);
    }

    @GetMapping("/status")
    public ResponseEntity<StatusResponse> status(@RequestParam Long userId) {
        StatusResponse statusResponse = slotService.statusRequest(userId);
        StatusList status = statusResponse.getStatus();
        return status.equals(StatusList.NOT_REGISTERED) ?
                new ResponseEntity<>(statusResponse, HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(statusResponse, HttpStatus.OK);
    }

    @PutMapping("/entry")
    public ResponseEntity<EntryResponse> entry(@RequestParam Long userId) {
        EntryResponse entryResponse = slotService.entryRequest(userId);
        StatusList status = entryResponse.getStatus();
        return status.equals(StatusList.SUCCESS) ?
                new ResponseEntity<>(entryResponse, HttpStatus.OK) :
                new ResponseEntity<>(entryResponse, HttpStatus.FORBIDDEN);
    }

    @PutMapping("/exit")
    public ResponseEntity<ExitResponse> exit(@RequestParam Long userId) {
        ExitResponse exitResponse = slotService.exitRequest(userId);
        StatusList status = exitResponse.getStatus();
        return status.equals(StatusList.NOT_REGISTERED) ?
                new ResponseEntity<>(exitResponse, HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(exitResponse, HttpStatus.OK);
    }
}
