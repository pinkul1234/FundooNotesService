package com.bridgelabz.fundoonotesservice.service;

import com.bridgelabz.fundoonotesservice.dto.NotesDto;
import com.bridgelabz.fundoonotesservice.model.NotesModel;
import com.bridgelabz.fundoonotesservice.util.Response;
import com.bridgelabz.fundoonotesservice.util.ResponseToken;

import java.util.List;

public interface INotesService {
    Response createNotes(NotesDto notesDto);

    Response updateNotes(long id, String token, NotesDto notesDto);

    List<NotesModel> readAllNotes(String token);

    Response readNotesById(long id, String token);

    Response deletePermanently(long id, String token);

    Response restoreNotes(long id, String token);

    Response addColour(long id, String colour, String token);

    Response pin(long id, String token);

    Response archieveNotes(long id, String token);
}
