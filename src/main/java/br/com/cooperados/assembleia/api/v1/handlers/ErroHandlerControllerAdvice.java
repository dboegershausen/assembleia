package br.com.cooperados.assembleia.api.v1.handlers;

import br.com.cooperados.assembleia.api.v1.models.ErroResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice
public class ErroHandlerControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErroResponseDTO onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ErroResponseDTO.builder().timestamp(LocalDateTime.now()).status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase()).message(e.getBindingResult().getFieldErrors().get(0).getDefaultMessage())
                .build();
    }

}
