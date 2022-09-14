package com.bridgelabz.fundoonotesservice.service;

import com.bridgelabz.fundoonotesservice.dto.NotesDto;
import com.bridgelabz.fundoonotesservice.exception.NotesNotFoundException;
import com.bridgelabz.fundoonotesservice.model.NotesModel;
import com.bridgelabz.fundoonotesservice.repository.NotesRepository;
import com.bridgelabz.fundoonotesservice.util.Response;
import com.bridgelabz.fundoonotesservice.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotesService implements INotesService {

    @Autowired
    NotesRepository notesRepository;
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    MailService mailService;
    @Autowired
    RestTemplate restTemplate;

    @Override
    public Response createNotes(NotesDto notesDto) {
        NotesModel notesModel = new NotesModel(notesDto);
        notesRepository.save(notesModel);
        String body = "notes added: " + notesModel.getId();
        String subject = "notes registration successfully";
        mailService.send(notesModel.getEmailId(), body, subject);
        return new Response("Success", 200, notesModel);
    }

    @Override
    public Response updateNotes(long noteId, String token, NotesDto notesDto) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8087/user/validate" + token, Boolean.class);
        if (isUserPresent) {
            Optional<NotesModel> isNotesPresent = notesRepository.findById(noteId);
            if (isNotesPresent.isPresent()) {
                isNotesPresent.get().setTitle(notesDto.getTitle());
                isNotesPresent.get().setDescription(notesDto.getDescription());
                isNotesPresent.get().setEmailId(notesDto.getEmailId());
                isNotesPresent.get().setUpdateDate(LocalDateTime.now());
                notesRepository.save(isNotesPresent.get());
                String body = "Notes details updated with id is: " +isNotesPresent.get().getId();
                String subject = "Notes details updated successfully";
                mailService.send(isNotesPresent.get().getEmailId(), body, subject);
                return new Response("Success", 200, isNotesPresent.get());
            }
            throw new NotesNotFoundException(400, "Not found");
        }
        throw new NotesNotFoundException(400, "Token is wrong");
    }


    @Override
    public List<NotesModel> readAllNotes(String token) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8087/user/validate" + token, Boolean.class);
        if(isUserPresent){
            List<NotesModel> isNotesPresent = notesRepository.findAll();
            return isNotesPresent;
        }
        throw new NotesNotFoundException(400, "Notes Not Available");
    }

    @Override
    public Response readNotesById(long noteId, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8087/user/validate" + token, Boolean.class);
        if(isUserPresent){
            Optional<NotesModel> isIdPresent = notesRepository.findById(noteId);
            if (isIdPresent.isPresent()){
                return new Response("success", 200, isIdPresent.get());
            }
            throw new NotesNotFoundException(400, "Not found");
        }
        throw new NotesNotFoundException(400, "Token is wrong");
    }
    @Override
    public Response deletePermanently(long noteId, String token){
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8087/user/validate" + token, Boolean.class);
        if (isUserPresent){
            Optional<NotesModel> isIdPresent = notesRepository.findById(noteId);
            if (isIdPresent.isPresent()){
                notesRepository.delete(isIdPresent.get());
                return new Response("success", 200, isIdPresent.get());
            }
            throw new NotesNotFoundException(400, "Not found");
        }
        throw new NotesNotFoundException(400, "Token is wrong");
    }
    @Override
    public Response restoreNotes(long noteId, String token){
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8087/user/validate" + token, Boolean.class);
        if (isUserPresent){
            Optional<NotesModel> isIdPresent = notesRepository.findById(noteId);
            if (isIdPresent.isPresent()){
                isIdPresent.get().setArchieve(true);
                isIdPresent.get().setTrash(true);
                notesRepository.save(isIdPresent.get());
                return new Response("success", 200, isIdPresent.get());
            }
            throw new NotesNotFoundException(400, "Not found");
        }
        throw new NotesNotFoundException(400, "Token is wrong");
    }

    @Override
    public Response addColour(long noteId, String colour, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8087/user/validate" + token, Boolean.class);
        if (isUserPresent){
            Optional<NotesModel> isIdPresent = notesRepository.findById(noteId);
            if (isIdPresent.isPresent()){
                isIdPresent.get().setColor(colour);
                notesRepository.save(isIdPresent.get());
                return new Response("success", 200, isIdPresent.get());
            }
            throw new NotesNotFoundException(400, "Notes not found");
        }
        throw new NotesNotFoundException(400, "Token is wrong");
    }

    @Override
    public Response pin(long noteId, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8087/user/validate" + token, Boolean.class);
        if (isUserPresent){
            Optional<NotesModel> isIdPresent = notesRepository.findById(noteId);
            if (isIdPresent.isPresent()){
                isIdPresent.get().setArchieve(false);
                isIdPresent.get().setPin(true);
                notesRepository.save(isIdPresent.get());
                return new Response("success", 200, isIdPresent.get());
            }
            throw new NotesNotFoundException(400, "Notes not found");
        }
        throw new NotesNotFoundException(400, "Token is wrong");
    }

    @Override
    public Response archieveNotes(long noteId, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8087/user/validate" + token, Boolean.class);
        if (isUserPresent){
            Optional<NotesModel> isIdPresent = notesRepository.findById(noteId);
            if (isIdPresent.isPresent()){
                isIdPresent.get().setPin(false);
                isIdPresent.get().setArchieve(true);
                notesRepository.save(isIdPresent.get());
                return new Response("success", 200, isIdPresent.get());
            }
            throw new NotesNotFoundException(400, "Notes not found");
        }
        throw new NotesNotFoundException(400, "Token is wrong");
    }

    @Override
    public Response addCollabrators(long noteId, String emailId, List<String> collabrators) {
        boolean isUserPresent = restTemplate.getForObject("http://localhost:8087/user/validateEmail" + emailId, Boolean.class);
        if (isUserPresent){
            Optional<NotesModel> isEmailPresent = notesRepository.findById(noteId);
            isEmailPresent.get().setEmailId(emailId);
            isEmailPresent.get().setCollabrators(collabrators);
                notesRepository.save(isEmailPresent.get());
                return new Response("success", 200, isEmailPresent.get());
            }
            throw new NotesNotFoundException(400, "not found");
        }

}

