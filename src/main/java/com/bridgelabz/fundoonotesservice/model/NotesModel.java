package com.bridgelabz.fundoonotesservice.model;

import com.bridgelabz.fundoonotesservice.dto.NotesDto;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

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

    public NotesModel(NotesDto notesDto){
        this.title = notesDto.getTitle();
        this.description = notesDto.getDescription();
        this.userId = notesDto.getUserId();
        this.registerDate = notesDto.getRegisterDate();
        this.updateDate = notesDto.getUpdateDate();
        this.emailId = notesDto.getEmailId();
        this.color = notesDto.getColor();
        this.reminderTime = notesDto.getReminderTime();
    }

    public NotesModel() {

    }
}
