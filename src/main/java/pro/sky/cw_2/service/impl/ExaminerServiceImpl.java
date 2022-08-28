package pro.sky.cw_2.service.impl;

import org.springframework.stereotype.Service;
import pro.sky.cw_2.exception.NotEnoughQuestionsException;
import pro.sky.cw_2.model.Question;
import pro.sky.cw_2.service.ExaminerService;
import pro.sky.cw_2.service.QuestionService;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class ExaminerServiceImpl implements ExaminerService {

    private final QuestionService questionService;

    public ExaminerServiceImpl(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public Collection<Question> getQuestion(int amount) {
        if(amount <= 0 || amount > questionService.getAll().size()){
            throw new NotEnoughQuestionsException();
        }
        Set<Question> result = new HashSet<>(amount);
        while (result.size() < amount){
            result.add(questionService.getRandomQuestion());
        }
        return result;
    }
}
