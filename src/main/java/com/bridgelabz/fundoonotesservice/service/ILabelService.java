package com.bridgelabz.fundoonotesservice.service;

import com.bridgelabz.fundoonotesservice.dto.LabelDto;
import com.bridgelabz.fundoonotesservice.dto.NotesDto;
import com.bridgelabz.fundoonotesservice.model.LabelModel;
import com.bridgelabz.fundoonotesservice.model.NotesModel;
import com.bridgelabz.fundoonotesservice.util.Response;

import java.util.List;

public interface ILabelService {
    Response createLabel(LabelDto labelDto);

    Response updateLabel(Long labelId, String token, LabelDto labelDto);

    List<LabelModel> readAllLabel(String token);

    Response deleteLabel(Long labelId, String token);

    Response addLabel(Long labelId, String token, List<Long> noteId);
}
