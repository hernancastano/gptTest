package gptTest.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
public class ResponseDTO<T> {

    public Integer code;
    public String message;
    public T body;

    public ResponseDTO(Integer code, String message, T body) {
        this.code = code;
        this.message = message;
        this.body = body;
    }
}
