package com.bridgelabz.fundoonotesservice.model;

import com.bridgelabz.fundoonotesservice.dto.LabelDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Label")
@Data
public class LabelModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String labelName;
    private Long userId;
    private Long noteId;
    private LocalDateTime registerDate;
    private LocalDateTime updateDate;
    private String emailId;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "lableNote", joinColumns = {@JoinColumn(name = "labelId")}, inverseJoinColumns = {@JoinColumn(name = "noteId")})
    @JsonBackReference
    private List<NotesModel> list;

    public LabelModel(LabelDto labelDto){
        this.labelName = labelDto.getLabelName();
    }

    public LabelModel() {

    }
}
