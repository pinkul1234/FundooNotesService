package com.bridgelabz.fundoonotesservice.dto;


import lombok.Data;

@Data
public class NotesDto {

    private String title;
    private String description;
    private String emailId;
    private String color;
    private String reminderTime;

}
