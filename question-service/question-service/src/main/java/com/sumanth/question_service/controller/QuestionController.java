package com.sumanth.question_service.controller;

import com.sumanth.question_service.model.Question;
import com.sumanth.question_service.model.QuestionWrapper;
import com.sumanth.question_service.model.Response;
import com.sumanth.question_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController
{

    @Autowired
    QuestionService questionService;

    @Autowired
    Environment environment;//used for checking ports

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>>  getAllQuestions()
    {
        return questionService.getAllQuestions();
    }

    @GetMapping("allQuestions/category/{category}")
    public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable String category)
    {
        return questionService.getQuestionByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question)
    {
        //send question in Json format
        return questionService.addQuestion(question);

    }

    // generating questions for quiz that returns list of question id's
    // by taking category and numQ as parameters that will come from quiz service
    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category , @RequestParam Integer numQ)
    {
        return questionService.getQuestionsForQuiz(category,numQ);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds)
    {
        System.out.println(environment.getProperty("local.server.port"));
        return questionService.getQuestionsFromId(questionIds);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses)
    {
        return questionService.getScore(responses);
    }

}
