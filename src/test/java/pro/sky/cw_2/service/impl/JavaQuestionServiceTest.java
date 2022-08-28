package pro.sky.cw_2.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pro.sky.cw_2.exception.QuestionAlreadyExistException;
import pro.sky.cw_2.exception.QuestionNotFoundException;
import pro.sky.cw_2.model.Question;
import pro.sky.cw_2.service.QuestionService;

import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


public class JavaQuestionServiceTest {

    private final QuestionService questionService = new JavaQuestionService();

    @ParameterizedTest
    @MethodSource("question1")
    public void add1Test(Question question){
        questionService.add(question);
        assertThatExceptionOfType(QuestionAlreadyExistException.class)
                .isThrownBy(()-> questionService.add(question));
        assertThat(questionService.getAll()).containsExactlyInAnyOrder(question);
    }

    @ParameterizedTest
    @MethodSource("question2")
    public void add2Test(String question, String answer){
        questionService.add(question, answer);
        assertThatExceptionOfType(QuestionAlreadyExistException.class)
                .isThrownBy(()-> questionService.add(question, answer));
        assertThat(questionService.getAll()).containsExactlyInAnyOrder(new Question(question, answer));
    }

    @ParameterizedTest
    @MethodSource("question1")
    public void removeTest(Question question){
        questionService.add(question);
        assertThatExceptionOfType(QuestionNotFoundException.class)
                .isThrownBy(()-> questionService.remove(new Question(question.getQuestion() + "1", question.getAnswer())));
        questionService.remove(question);
        assertThat(questionService.getAll()).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("questions")
    public void getRandomQuestionTest(Set<Question> questions){
        questions.forEach(questionService::add);
        assertThat(questionService.getAll()).hasSize(questions.size());
        assertThat(questionService.getRandomQuestion()).isIn(questionService.getAll());

    }

    public static Stream<Arguments> question1(){
        return Stream.of(
                Arguments.of(new Question("question", "answer"))
        );
    }

    public static Stream<Arguments> question2(){
        return Stream.of(
                Arguments.of("Question", "Answer")
        );
    }

    public static Stream<Arguments> questions() {
        return Stream.of(
            Arguments.of(
                    Set.of(
                            new Question("Question1", "Answer 1"),
                            new Question("Question2", "Answer 2"),
                            new Question("Question3", "Answer 3")
                    )
            )
        );
    }
}
