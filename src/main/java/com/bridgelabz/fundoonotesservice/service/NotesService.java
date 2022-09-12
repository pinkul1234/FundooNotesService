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
        String body = "User added: " + notesModel.getId();
        String subject = "User registration successfully";
        mailService.send(notesModel.getEmailId(), body, subject);
        return new Response("Success", 200, notesModel);
    }

    @Override
    public Response updateNotes(long id, String token, NotesDto notesDto) {
        boolean isNotesPresent = restTemplate.getForObject("http://localhost:8087/user/validate" + token, Boolean.class);
        if (isNotesPresent) {
            Optional<NotesModel> isNotes = notesRepository.findById(id);
            if (isNotes.isPresent()) {
                isNotes.get().setUserId(notesDto.getUserId());
                isNotes.get().setTitle(notesDto.getTitle());
                isNotes.get().setDescription(notesDto.getDescription());
                isNotes.get().setRegisterDate(notesDto.getRegisterDate());
                isNotes.get().setUpdateDate(notesDto.getUpdateDate());
                isNotes.get().setEmailId(notesDto.getEmailId());
                isNotes.get().setReminderTime(notesDto.getReminderTime());
                notesRepository.save(isNotes.get());
                return new Response("Success", 200, isNotes.get());
            }
            throw new NotesNotFoundException(400, "Not found");
        }
        throw new NotesNotFoundException(400, "Token is wrong");
    }


    @Override
    public List<NotesModel> readAllNotes(String token) {
        boolean isNotesPresent = restTemplate.getForObject("http://localhost:8087/user/validate" + token, Boolean.class);
        if(isNotesPresent){
            List<NotesModel> isNotes = notesRepository.findAll();
            return isNotes;
        }
        throw new NotesNotFoundException(400, "Notes Not Available");
    }

    @Override
    public Response readNotesById(long id, String token) {
        boolean isNotesPresent = restTemplate.getForObject("http://localhost:8087/user/validate" + token, Boolean.class);
        if(isNotesPresent){
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
        boolean isNotesPresent = restTemplate.getForObject("http://localhost:8087/user/validate" + token, Boolean.class);
        if (isNotesPresent){
            Optional<NotesModel> isId = notesRepository.findById(id);
            if (isId.isPresent()){
                notesRepository.delete(isId.get());
                return new Response("success", 200, isId.get());
            }
            throw new NotesNotFoundException(400, "Not found");
        }
        throw new NotesNotFoundException(400, "Token is wrong");
    }
    @Override
    public Response restoreNotes(long id, String token){
        boolean isNotesPresent = restTemplate.getForObject("http://localhost:8087/user/validate" + token, Boolean.class);
        if (isNotesPresent){
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
        boolean isNotesPresent = restTemplate.getForObject("http://localhost:8087/user/validate" + token, Boolean.class);
        if (isNotesPresent){
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
        boolean isNotesPresent = restTemplate.getForObject("http://localhost:8087/user/validate" + token, Boolean.class);
        if (isNotesPresent){
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
        boolean isNotesPresent = restTemplate.getForObject("http://localhost:8087/user/validate" + token, Boolean.class);
        if (isNotesPresent){
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
