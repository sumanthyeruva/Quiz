package com.sumanth.quiz_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Quiz
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    //we used manytomany in monolithic app because it is list of questions which has different fields(id, title,options)
    //but here we need only list of integers
    @ElementCollection
    private List<Integer> questionIds;

}
