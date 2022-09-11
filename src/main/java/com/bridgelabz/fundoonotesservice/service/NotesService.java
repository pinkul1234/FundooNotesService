package com.bridgelabz.fundoonotesservice.service;

import com.bridgelabz.fundoonotesservice.dto.NotesDto;
import com.bridgelabz.fundoonotesservice.exception.NotesNotFoundException;
import com.bridgelabz.fundoonotesservice.model.NotesModel;
import com.bridgelabz.fundoonotesservice.repository.NotesRepository;
import com.bridgelabz.fundoonotesservice.util.Response;
import com.bridgelabz.fundoonotesservice.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotesService implements INotesService{

    @Autowired
    NotesRepository notesRepository;
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    MailService mailService;

    @Override
    public Response createNotes(NotesDto notesDto) {
        NotesModel notesModel = new NotesModel(notesDto);
        notesRepository.save(notesModel);
        String body = "User added: " + notesModel.getId();
        String subject = "User registration successfully";
        mailService.send(notesModel.getEmailId(), body, subject);
        return new Response("Success", 200, notesModel);
    }

    @Override
    public Response updateNotes(long id, String token, NotesDto notesDto) {
        long userId = tokenUtil.decodeToken(token);
        Optional<NotesModel> isNotesPresent = notesRepository.findById(userId);
        if (isNotesPresent.isPresent()) {
            isNotesPresent.get().setUserId(notesDto.getUserId());
            isNotesPresent.get().setTitle(notesDto.getTitle());
            isNotesPresent.get().setDescription(notesDto.getDescription());
            isNotesPresent.get().setRegisterDate(notesDto.getRegisterDate());
            isNotesPresent.get().setUpdateDate(notesDto.getUpdateDate());
            isNotesPresent.get().setEmailId(notesDto.getEmailId());
            isNotesPresent.get().setReminderTime(notesDto.getReminderTime());
            notesRepository.save(isNotesPresent.get());
            return new Response("Success", 200, isNotesPresent.get());
        }
        throw new NotesNotFoundException(400, "Not found");
    }

    @Override
    public List<NotesModel> readAllNotes(String token) {
        List<NotesModel> readAllNotes = notesRepository.findAll();
        if (readAllNotes.size() > 0) {
            return readAllNotes;
        }
        throw new NotesNotFoundException(400, "Notes Not Found");
    }

    @Override
    public Response readNotesById(long id, String token) {
        long userId = tokenUtil.decodeToken(token);
        Optional<NotesModel> isNotesPresent = notesRepository.findById(userId);
        if(isNotesPresent.isPresent()){
            Optional<NotesModel> isId = notesRepository.findById(id);
            if (isId.isPresent()){
                return new Response("success", 200, isId.get());
            }
            throw new NotesNotFoundException(400, "Not found");
        }
        throw new NotesNotFoundException(400, "Token is wrong");
    }
    @Override
    public Response deletePermanently(long id, String token){
        long UserId = tokenUtil.decodeToken(token);
        Optional<NotesModel> isNotes = notesRepository.findById(UserId);
        if (isNotes.isPresent()){
            Optional<NotesModel> isId = notesRepository.findById(id);
            if (isId.isPresent()){
                return new Response("success", 200, isId.get());
            }
            throw new NotesNotFoundException(400, "Not found");
        }
        throw new NotesNotFoundException(400, "Token is wrong");
    }
    @Override
    public Response restoreNotes(long id, String token){
        long userId = tokenUtil.decodeToken(token);
        Optional<NotesModel> isNotes = notesRepository.findById(userId);
        if (isNotes.isPresent()){
            Optional<NotesModel> isId = notesRepository.findById(id);
            if (isId.isPresent()){
                isId.get().setArchieve(true);
                isId.get().setTrash(true);
                notesRepository.save(isId.get());
                return new Response("success", 200, isId.get());
            }
            throw new NotesNotFoundException(400, "Not found");
        }
        throw new NotesNotFoundException(400, "Token is wrong");
    }

    @Override
    public Response addColour(long id, String colour, String token) {
        long userId = tokenUtil.decodeToken(token);
        Optional<NotesModel> isNotes = notesRepository.findById(userId);
        if (isNotes.isPresent()){
            Optional<NotesModel> isId = notesRepository.findById(id);
            if (isId.isPresent()){
                isId.get().setColor(colour);
                notesRepository.save(isId.get());
                return new Response("success", 200, isId.get());
            }
            throw new NotesNotFoundException(400, "Notes not found");
        }
        throw new NotesNotFoundException(400, "Token is wrong");
    }

    @Override
    public Response pin(long id, String token) {
        long userId = tokenUtil.decodeToken(token);
        Optional<NotesModel> isNotes = notesRepository.findById(userId);
        if (isNotes.isPresent()){
            Optional<NotesModel> isId = notesRepository.findById(id);
            if (isId.isPresent()){
                isId.get().setArchieve(false);
                isId.get().setPin(true);
                notesRepository.save(isId.get());
                return new Response("success", 200, isId.get());
            }
            throw new NotesNotFoundException(400, "Notes not found");
        }
        throw new NotesNotFoundException(400, "Token is wrong");
    }

    @Override
    public Response archieveNotes(long id, String token) {
        long userId = tokenUtil.decodeToken(token);
        Optional<NotesModel> isNotes = notesRepository.findById(userId);
        if (isNotes.isPresent()){
            Optional<NotesModel> isId = notesRepository.findById(id);
            if (isId.isPresent()){
                isId.get().setPin(false);
                isId.get().setArchieve(true);
                notesRepository.save(isId.get());
                return new Response("success", 200, isId.get());
            }
            throw new NotesNotFoundException(400, "Notes not found");
        }
        throw new NotesNotFoundException(400, "Token is wrong");
    }

}
