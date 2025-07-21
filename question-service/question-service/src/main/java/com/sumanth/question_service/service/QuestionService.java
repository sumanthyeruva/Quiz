package com.telusko.question_service.service;

import com.telusko.question_service.dao.QuestionDao;
import com.telusko.question_service.model.Question;
import com.telusko.question_service.model.QuestionWrapper;
import com.telusko.question_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService
{

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions()
    {
        try
        {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<List<Question>> getQuestionByCategory(String category)
    {

        try
        {
            return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.OK);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

    }


    public ResponseEntity<String> addQuestion(Question question)
    {
        questionDao.save(question);
        return new ResponseEntity<>("success",HttpStatus.CREATED);
    }

    //method that returns list of question id's
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, Integer numQ) {
        List<Integer> questionIds = questionDao.findRandomQuestionsByCategory(category,numQ);
        return new ResponseEntity<>(questionIds,HttpStatus.OK);
    }

    //here we are getting list of questionIds from server and we find the questions from them and will assign to questionwrapper
    //this method will returns list of question wrapper by taking list of questionIds
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {

        //creating wrappers arraylist
        List<QuestionWrapper> wrappers = new ArrayList<>();

        //creating questions arraylist
        List<Question> questions = new ArrayList<>();
        //getting questions from the list odf ids with the help of dao layer
        for(Integer id: questionIds)
        {
            //adding questions to arraylist
            questions.add(questionDao.findById(id).get());
        }

        //getting questionwrapper from questions
        for(Question question : questions)
        {
            //setting questionwrapper required attributes from question
            QuestionWrapper wrapper=new QuestionWrapper();
            wrapper.setId(question.getId());
            wrapper.setQuestionTitle(question.getQuestionTitle());
            wrapper.setOption1(question.getOption1());
            wrapper.setOption2(question.getOption2());
            wrapper.setOption3(question.getOption3());
            wrapper.setOption4(question.getOption4());
            //adding wrapper to wrappers arraylist
            wrappers.add(wrapper);
        }

        return new ResponseEntity<>(wrappers,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {

        int correct=0;//for calculating result

        //iterating Response and comparing them with responses from user
        for(Response response : responses)
        {
            Question question = questionDao.findById(response.getId()).get();
            if(response.getResponse().equals(question.getRightAnswer()))
                correct++;

        }
        return new ResponseEntity<>(correct,HttpStatus.OK);
    }
}
