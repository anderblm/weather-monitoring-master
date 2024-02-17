package edu.iu.anderblm.weathermonitoring.controllers;

import edu.iu.anderblm.weathermonitoring.model.CurrentConditionDisplay;
import edu.iu.anderblm.weathermonitoring.model.HeatIndexDisplay;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/displays")
public class DisplayController {
    private CurrentConditionDisplay currentConditionDisplay;
    private HeatIndexDisplay heatIndexDisplay;

    public DisplayController(CurrentConditionDisplay currentConditionDisplay, HeatIndexDisplay heatIndexDisplay) {
        this.currentConditionDisplay = currentConditionDisplay;
        this.heatIndexDisplay = heatIndexDisplay;
    }

    @GetMapping
    public ResponseEntity index() {
        String html =
                String.format("<h1>Available screens:</h1>");
        html += "<ul>";
        html += "<li>";
        html += String.format("<a href=/displays/%s>%s</a>", currentConditionDisplay.id(), currentConditionDisplay.name());
        html += "</li>";
        html += "<li>";
        html += String.format("<a href=/displays/%s>%s</a>", heatIndexDisplay.id(), heatIndexDisplay.name());
        html += "</li>";
        html += "</ul>";
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(html);
    }

    @GetMapping("/{id}")
    public ResponseEntity display(@PathVariable String id) {
        // Implementation for displaying details of each screen
    }

    @GetMapping("/{id}/subscribe")
    public ResponseEntity subscribe(@PathVariable String id) {
        String html = "";
        HttpStatus status = null;
        if (id.equalsIgnoreCase(currentConditionDisplay.id())) {
            currentConditionDisplay.subscribe();
            html = "Subscribed!";
            status = HttpStatus.OK;
        } else if (id.equalsIgnoreCase(heatIndexDisplay.id())) {
            heatIndexDisplay.subscribe();
            html = "Subscribed to Heat Index Display!";
            status = HttpStatus.OK;
        } else {
            html = "The screen id is invalid.";
            status = HttpStatus.NOT_FOUND;
        }
        return ResponseEntity
                .status(status)
                .body(html);
    }

    @GetMapping("/{id}/unsubscribe")
    public ResponseEntity unsubscribe(@PathVariable String id) {
        String html = "";
        HttpStatus status = null;
        if (id.equalsIgnoreCase(currentConditionDisplay.id())) {
            currentConditionDisplay.unsubscribe();
            html = "Unsubscribed!";
            status = HttpStatus.OK;
        } else if (id.equalsIgnoreCase(heatIndexDisplay.id())) {
            heatIndexDisplay.unsubscribe();
            html = "Unsubscribed from Heat Index Display!";
            status = HttpStatus.OK;
        } else {
            html = "The screen id is invalid.";
            status = HttpStatus.NOT_FOUND;
        }
        return ResponseEntity
                .status(status)
                .body(html);
    }

    @GetMapping("/heat-index/subscribe")
    public ResponseEntity subscribeHeatIndex() {
        heatIndexDisplay.subscribe();
        return ResponseEntity.status(HttpStatus.OK).body("Subscribed to Heat Index Display");
    }

    @GetMapping("/heat-index/unsubscribe")
    public ResponseEntity unsubscribeHeatIndex() {
        heatIndexDisplay.unsubscribe();
        return ResponseEntity.status(HttpStatus.OK).body("Unsubscribed from Heat Index Display");
    }
}
