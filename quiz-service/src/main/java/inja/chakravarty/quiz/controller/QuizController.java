package inja.chakravarty.quiz.controller;


import inja.chakravarty.quiz.model.QuestionWrapper;
import inja.chakravarty.quiz.model.Response;
import inja.chakravarty.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/quiz")
public class QuizController {
    @Autowired
    private QuizService quizService;
    @PostMapping("/{category}")
    public ResponseEntity<String> createQuiz(@PathVariable @RequestParam String category, @RequestParam int numQ, @RequestParam String title){
        return quizService.createQuiz(category,numQ,title);
    }
    @GetMapping("/getQuiz/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuiz(@PathVariable int id){
        return quizService.getQuiz(id);
    }
    @PostMapping("getScore/{id}")
    public ResponseEntity<Integer> getScore(@PathVariable int id, @RequestBody List<Response> responses){
        return quizService.getScore(id,responses);
    }
}
