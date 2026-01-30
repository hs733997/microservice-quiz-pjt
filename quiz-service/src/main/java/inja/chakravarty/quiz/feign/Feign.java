package inja.chakravarty.quiz.feign;

import inja.chakravarty.quiz.model.QuestionWrapper;
import inja.chakravarty.quiz.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface Feign {

    @GetMapping("/questions/generate")
    ResponseEntity<List<Integer>> getQuestionsForQuiz
            (@RequestParam String categoryName, @RequestParam Integer numQuestions);
    @PostMapping("/questions/getQuestions")
    ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds);
    @PostMapping("/questions/getScore")
    ResponseEntity<Integer> getScore(@RequestBody List<Response> responses);
}
