package com.telusko.quiz_service.service;

import com.telusko.quiz_service.dao.QuizDao;
import com.telusko.quiz_service.feign.QuizInterface;
import com.telusko.quiz_service.model.QuestionWrapper;
import com.telusko.quiz_service.model.Quiz;
import com.telusko.quiz_service.model.Response;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class  QuizService
{
    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title)
    {
        //get list of question ids with the help of quizInterface use getBody() because getQuestionsForQuiz returns response entity
        List<Integer> questionIds = quizInterface.getQuestionsForQuiz(category,numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questionIds);
        quizDao.save(quiz);


        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }


    // method for getting questions through passing quiz id
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        //finding quiz with the help of id from quizDao

        Quiz quiz=quizDao.findById(id).get();

        List<Integer> questionIds = quiz.getQuestionIds();
        ResponseEntity<List<QuestionWrapper>> questions=quizInterface.getQuestionsFromId(questionIds);

        //return the response entity
        return questions;
    }

    //calculating score by calling getScore method from QuizInterface which is in Question-service
    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        ResponseEntity<Integer> score = quizInterface.getScore(responses);
        return score;
    }
}
