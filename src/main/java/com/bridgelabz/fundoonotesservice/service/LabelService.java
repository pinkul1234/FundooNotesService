package com.bridgelabz.fundoonotesservice.service;

import com.bridgelabz.fundoonotesservice.dto.LabelDto;
import com.bridgelabz.fundoonotesservice.exception.LabelNotFoundException;
import com.bridgelabz.fundoonotesservice.model.LabelModel;
import com.bridgelabz.fundoonotesservice.model.NotesModel;
import com.bridgelabz.fundoonotesservice.repository.LabelRepository;
import com.bridgelabz.fundoonotesservice.repository.NotesRepository;
import com.bridgelabz.fundoonotesservice.util.Response;
import com.bridgelabz.fundoonotesservice.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LabelService implements ILabelService {
    @Autowired
    LabelRepository labelRepository;
    @Autowired
    MailService mailService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    NotesRepository notesRepository;

    @Override
    public Response createLabel(LabelDto labelDto) {
        LabelModel labelModel = new LabelModel(labelDto);
        labelRepository.save(labelModel);
        String body = "Label added: " + labelModel.getId();
        String subject = "Label registration successfully";
        mailService.send(labelModel.getEmailId(), body, subject);
        return new Response("Success", 200, labelModel);
    }

    @Override
    public Response updateLabel(long labelId, String token, LabelDto labelDto) {
        boolean isUserPresent = restTemplate.getForObject("http://User-Service:8087/user/validate" + token, Boolean.class);
        if (isUserPresent) {
            Optional<LabelModel> isLabelPresent = labelRepository.findById(labelId);
            if (isLabelPresent.isPresent()) {
                isLabelPresent.get().setLabelName(labelDto.getLabelName());
                isLabelPresent.get().setUpdateDate(LocalDateTime.now());
                labelRepository.save(isLabelPresent.get());
                String body = "Label details updated with id is: " + isLabelPresent.get().getId();
                String subject = "Label details updated successfully";
                mailService.send(isLabelPresent.get().getEmailId(), body, subject);
                return new Response("Success", 200, isLabelPresent.get());
            }
            throw new LabelNotFoundException(400, "Not found");
        }
        throw new LabelNotFoundException(400, "Token is wrong");
    }

    @Override
    public List<LabelModel> readAllLabel(String token) {
        boolean isUserPresent = restTemplate.getForObject("http://User-Service:8087/user/validate" + token, Boolean.class);
        if (isUserPresent) {
            List<LabelModel> isLabelPresent = labelRepository.findAll();
            return isLabelPresent;
        }
        throw new LabelNotFoundException(400, "Label Not Available");
    }

    @Override
    public Response deleteLabel(long labelId, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://User-Service:8087/user/validate" + token, Boolean.class);
        if (isUserPresent) {
            Optional<LabelModel> isLabelPresent = labelRepository.findById(labelId);
            if (isLabelPresent.isPresent()) {
                labelRepository.delete(isLabelPresent.get());
                return new Response("success", 200, isLabelPresent.get());
            }
            throw new LabelNotFoundException(400, "Not found");
        }
        throw new LabelNotFoundException(400, "Token is wrong");
    }

    @Override
    public Response addLabel(long labelId, String token, List<Long> noteId) {
        boolean isUserPresent = restTemplate.getForObject("http://User-Service:8087/user/validate" + token, Boolean.class);
        if (isUserPresent){
            List<NotesModel> notesModels = new ArrayList<>();
            noteId.stream().forEach(note ->{
                Optional<NotesModel> isNotePresent = notesRepository.findById(note);
                if (isNotePresent.isPresent()){
                    notesModels.add(isNotePresent.get());
                }
            });
            Optional<LabelModel> isLabelPresent = labelRepository.findById(labelId);
            if (isLabelPresent.isPresent()){
                isLabelPresent.get().setList(notesModels);
                labelRepository.save(isLabelPresent.get());
                return new Response("success", 200, isLabelPresent.get());
            }
            throw new LabelNotFoundException(400, "not found");
        }
        throw new LabelNotFoundException(400, "Token is wrong");
    }

}