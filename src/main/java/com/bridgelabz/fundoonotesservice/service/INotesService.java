package com.bridgelabz.fundoonotesservice.service;

import com.bridgelabz.fundoonotesservice.dto.NotesDto;
import com.bridgelabz.fundoonotesservice.model.NotesModel;
import com.bridgelabz.fundoonotesservice.util.Response;

import java.time.LocalDateTime;
import java.util.List;

public interface INotesService {
    Response createNotes(NotesDto notesDto);

    Response updateNotes(long noteId, String token, NotesDto notesDto);

    List<NotesModel> readAllNotes(String token);

    Response readNotesById(long noteId, String token);

    Response deletePermanently(long noteId, String token);

    Response restoreNotes(long noteId, String token);

    Response addColour(long noteId, String colour, String token);

    Response pin(long noteId, String token);

    Response archieveNotes(long noteId, String token);

    Response addCollabrators(long noteId, String emailId, List<String> collabrators);

}
