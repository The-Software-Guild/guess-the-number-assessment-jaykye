package GuessTheNumber.controller;

import GuessTheNumber.service.InvalidGameException;
import GuessTheNumber.service.InvalidRoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice  //  indicates our class will assist all controllers in our project.
@RestController  // indicates our class is able to serve an HTTP response on behalf of a controller.
public class GuessTheNumberExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String CONSTRAINT_MESSAGE = "Could not save your item. "
            + "Please ensure it is valid and try again.";

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    // 간단하게 말해서 비슷한 exception 들을 계속 catch 해야 한단 말이지. 대표적으로 SQL 관련 에러.
    // 이것들을 그냥 일괄적으로 처리하고 싶을 때 사용하는 방법이다.
    // 편리한 대신 exception 하나당 딱 하나의 함수만 사용가능. SQLIntegrity exception 은 모두 아래 처럼 처리된다.

    // 이거는 Http response 사용할 때 쓴다. return값으로 내부에서 작업하는 경우는 안됨.

    public final ResponseEntity<Error> handleSqlException(
            SQLIntegrityConstraintViolationException ex,
            WebRequest request) {

        Error err = new Error();
        err.setMessage(CONSTRAINT_MESSAGE);
        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(InvalidRoundException.class)

    public final ResponseEntity<Error> handleInvalidRoundException(
            InvalidRoundException ex,
            WebRequest request) {

        Error err = new Error();
        err.setMessage(ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
