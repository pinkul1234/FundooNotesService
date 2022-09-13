package com.bridgelabz.fundoonotesservice.model;

import com.bridgelabz.fundoonotesservice.dto.NotesDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Notes")
@Data
public class NotesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    private String title;
    private String description;
    private long userId;
    private LocalDateTime registerDate;
    private LocalDateTime updateDate;
    private boolean trash;
    private boolean archieve;
    private boolean pin;
    private String emailId;
    private String color;
    private LocalDateTime reminderTime;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "labelNote", joinColumns = {@JoinColumn(name = "noteId")},inverseJoinColumns = {@JoinColumn(name = "labelId")})
    @JsonBackReference
    @JsonIgnore
    private List<LabelModel> list;


    public NotesModel(NotesDto notesDto){
        this.title = notesDto.getTitle();
        this.description = notesDto.getDescription();
        this.emailId = notesDto.getEmailId();
        this.color = notesDto.getColor();
    }

    public NotesModel() {

    }
}
