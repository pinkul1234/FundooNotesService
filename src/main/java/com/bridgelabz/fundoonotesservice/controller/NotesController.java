package com.bridgelabz.fundoonotesservice.controller;

import com.bridgelabz.fundoonotesservice.dto.NotesDto;
import com.bridgelabz.fundoonotesservice.model.NotesModel;
import com.bridgelabz.fundoonotesservice.service.INotesService;
import com.bridgelabz.fundoonotesservice.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NotesController {
    @Autowired
    INotesService notesService;

    @PostMapping("/createnotes")
    public ResponseEntity<Response> createNotes(@RequestBody NotesDto notesDto){
        Response response = notesService.createNotes(notesDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/updatenotes/{id}")
    public ResponseEntity<Response> updateNotes(@RequestHeader String token, @RequestBody NotesDto notesDto, @PathVariable long noteId){
        Response response = notesService.updateNotes(noteId, token, notesDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/readallnotes")
    public ResponseEntity<List<?>> readAllNotes(@RequestParam String token){
        List<NotesModel> response = notesService.readAllNotes(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/readnotesbyid/{id}")
    public ResponseEntity<Response> readNotesById(@RequestHeader String token, @PathVariable long noteId){
        Response response = notesService.readNotesById(noteId, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/deletepermanently")
    public ResponseEntity<Response> deletePermanently(@PathVariable long noteId, @RequestHeader String token) {
        Response response = notesService.deletePermanently(noteId, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/restore")
    public ResponseEntity<Response> restoreNotes(@PathVariable long noteId, @RequestHeader String token) {
        Response response = notesService.restoreNotes(noteId, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/addcolour")
    public ResponseEntity<Response> addColour(@RequestParam long noteId, @RequestParam String colour, @RequestHeader String token){
        Response response = notesService.addColour(noteId, colour, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/pin{id}")
    public ResponseEntity<Response> pin(@PathVariable long noteId, @RequestHeader String token){
        Response response = notesService.pin(noteId, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/archieve{id}")
    public ResponseEntity<Response> archieveNotes(@PathVariable long noteId, @RequestHeader String token){
        Response response = notesService.archieveNotes(noteId, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
