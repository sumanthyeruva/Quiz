package com.telusko.quiz_service.controller;

import com.telusko.quiz_service.model.QuestionWrapper;
import com.telusko.quiz_service.model.QuizDto;
import com.telusko.quiz_service.model.Response;
import com.telusko.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {


    @Autowired
    QuizService quizService;
    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto)
    {
        return quizService.createQuiz(quizDto.getCategoryName(), quizDto.getNumQuestions(),quizDto.getTitle());
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id)
    {
        return quizService.getQuizQuestions(id);
    }

    // method for handling post with quiz id that takes id and list of responses from user(no.of Questions)
    // i.e responses having same attributes as Response class with id and response(string)
    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses)
    {
        return quizService.calculateResult(id,responses);
    }
}
