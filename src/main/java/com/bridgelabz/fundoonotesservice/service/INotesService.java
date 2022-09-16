package com.bridgelabz.fundoonotesservice.service;

import com.bridgelabz.fundoonotesservice.dto.NotesDto;
import com.bridgelabz.fundoonotesservice.model.NotesModel;
import com.bridgelabz.fundoonotesservice.util.Response;

import java.time.LocalDateTime;
import java.util.List;

public interface INotesService {
    Response createNotes(NotesDto notesDto);

    Response updateNotes(Long noteId, String token, NotesDto notesDto);

    List<NotesModel> readAllNotes(String token);

    Response readNotesById(Long noteId, String token);

    Response deletePermanently(Long noteId, String token);

    Response restoreNotes(Long noteId, String token);

    Response addColour(Long noteId, String colour, String token);

    Response pin(Long noteId, String token);

    Response archieveNotes(Long noteId, String token);

    Response addCollabrators(Long noteId, String emailId, String collabrators, String token, Long collabratorUserId);

    NotesModel setRemainder(String remainderTime, String token, Long id);
}
