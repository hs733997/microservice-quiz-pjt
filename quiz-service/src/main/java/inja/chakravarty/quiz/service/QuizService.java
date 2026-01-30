package inja.chakravarty.quiz.service;

import inja.chakravarty.quiz.dao.QuizDao;
import inja.chakravarty.quiz.feign.Feign;
import inja.chakravarty.quiz.model.QuestionWrapper;
import inja.chakravarty.quiz.model.Quiz;
import inja.chakravarty.quiz.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    private QuizDao quizDao;
    @Autowired
    private Feign feign;

    /***
     * In this method we need to communicate with other service(question-service);
     * so, in order to achieve it we use RestTemplate/WebClient.[but here in our local it is fine bcuz url is basically
     * http://localhost:8080/questions/generate]
     * But in real time domain and port will not be static. And we dont want to use any IPAddress; and hard code port,
     * so there come in FiegnClient and ServiceDiscover concept, Eureka server.
     * FiegnClient it helps a declarative way of requesting to a service.
     */
    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Integer> questions = feign.getQuestionsForQuiz(category,numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuiz(int id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        if(quiz.isPresent()){
            List<Integer> questionIds = quiz.get().getQuestionIds();
            ResponseEntity<List<QuestionWrapper>> questions = feign.getQuestionsFromId(questionIds);
            return questions;
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Integer> getScore(int id, List<Response> responses) {
        Optional<Quiz> quiz = quizDao.findById(id);
        if(quiz.isPresent()){
            return feign.getScore(responses);
        }
        return new ResponseEntity<>(0,HttpStatus.BAD_REQUEST);
    }
}
