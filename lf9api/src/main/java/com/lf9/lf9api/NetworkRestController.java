package com.lf9.lf9api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NetworkRestController {

    private XMLToJSONStringService xmlToJSONStringService = new XMLToJSONStringService();
    @GetMapping("/network-devices")
    public String networkDevices() {
        return xmlToJSONStringService.convert().toString();
    }
}