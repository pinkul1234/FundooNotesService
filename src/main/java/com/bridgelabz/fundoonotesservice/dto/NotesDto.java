package com.bridgelabz.fundoonotesservice.dto;


import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotesDto {

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

}
