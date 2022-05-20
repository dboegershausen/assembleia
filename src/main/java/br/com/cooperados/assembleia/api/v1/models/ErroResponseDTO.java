package br.com.cooperados.assembleia.api.v1.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErroResponseDTO {

    private LocalDateTime timestamp;

    private int status;

    private String error;

    private String message;

}
