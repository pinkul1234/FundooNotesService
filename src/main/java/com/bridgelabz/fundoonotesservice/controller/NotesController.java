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
    public ResponseEntity<Response> updateNotes(@RequestHeader String token, @RequestBody NotesDto notesDto, @PathVariable Long noteId){
        Response response = notesService.updateNotes(noteId, token, notesDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/readallnotes")
    public ResponseEntity<List<?>> readAllNotes(@RequestParam String token){
        List<NotesModel> response = notesService.readAllNotes(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/readnotesbyid/{id}")
    public ResponseEntity<Response> readNotesById(@RequestHeader String token, @PathVariable Long noteId){
        Response response = notesService.readNotesById(noteId, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/deletepermanently")
    public ResponseEntity<Response> deletePermanently(@PathVariable Long noteId, @RequestHeader String token) {
        Response response = notesService.deletePermanently(noteId, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/restore")
    public ResponseEntity<Response> restoreNotes(@PathVariable Long noteId, @RequestHeader String token) {
        Response response = notesService.restoreNotes(noteId, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/addcolour")
    public ResponseEntity<Response> addColour(@RequestParam Long noteId, @RequestParam String colour, @RequestHeader String token){
        Response response = notesService.addColour(noteId, colour, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/getalltrash")
    public ResponseEntity<List<?>> getAlltrash(@RequestHeader String token) {
        List<NotesModel> response = notesService.getAlltrash(token);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    @PutMapping("/pin{id}")
    public ResponseEntity<Response> pin(@PathVariable Long noteId, @RequestHeader String token){
        Response response = notesService.pin(noteId, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/getallpinned")
    public ResponseEntity<List<?>> getAllPinned(@RequestHeader String token){
        List<NotesModel> response = notesService.getAllPinned(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/archieve{id}")
    public ResponseEntity<Response> archieveNotes(@PathVariable Long noteId, @RequestHeader String token){
        Response response = notesService.archieveNotes(noteId, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/getArchieve")
    public ResponseEntity<List<?>> getAllArchieve(@RequestHeader String token) {
        List<NotesModel> response = notesService.getAllArchieve(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/addcollabrators")
    public ResponseEntity<Response> addCollabrators(@RequestParam Long noteId, @RequestParam String emailId, @RequestHeader String collabrators, String token, Long collabratorUserId ) {
        Response response = notesService.addCollabrators(noteId, emailId, collabrators, token, collabratorUserId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/setremainder/{id}")
    ResponseEntity<Response> setRemainder(@RequestHeader String token, @PathVariable Long id, @RequestParam String remainderTime){
        NotesModel notesModel = notesService.setRemainder(remainderTime, token, id);
        Response response = new Response("Remainder set successfully", 400, notesModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
