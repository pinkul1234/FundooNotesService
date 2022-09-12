package com.bridgelabz.fundoonotesservice.dto;


import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotesDto {

    private String title;
    private String description;
    private String emailId;
    private String color;

}
