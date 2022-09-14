package com.bridgelabz.fundoonotesservice.controller;

import com.bridgelabz.fundoonotesservice.dto.LabelDto;
import com.bridgelabz.fundoonotesservice.model.LabelModel;
import com.bridgelabz.fundoonotesservice.service.ILabelService;
import com.bridgelabz.fundoonotesservice.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/label")
public class LabelController {

    @Autowired
    ILabelService labelService;

    @PostMapping("/createlabel")
    public ResponseEntity<Response> createLabel(@RequestBody LabelDto labelDto){
        Response response = labelService.createLabel(labelDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/updatelabel/{id}")
    public ResponseEntity<Response> updateLabel(@RequestHeader String token, @RequestBody LabelDto labelDto, @PathVariable long labelId){
        Response response = labelService.updateLabel(labelId, token, labelDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/readalllabel")
    public ResponseEntity<List<?>> readAllLabel(@RequestParam String token){
        List<LabelModel> response = labelService.readAllLabel(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/deletelabel")
    public ResponseEntity<Response> deleteLabel(@PathVariable long labelId, @RequestHeader String token) {
        Response response = labelService.deleteLabel(labelId, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/addlabel")
    public ResponseEntity<Response> addLabel(@RequestParam long labelId, @RequestHeader String token, List<Long> noteId){
        Response response = labelService.addLabel(labelId, token, noteId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
