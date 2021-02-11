package pe.com.bcp.exchangerate.view.dto.response;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BaseWebResponse<T> {
    private ErrorCode errorCode;
    private T data;



    public static <T> BaseWebResponse<T> successWithData(T data) {
        return BaseWebResponse.<T>builder()
                .data(data)
                .build();
    }

    public static BaseWebResponse error(ErrorCode errorCode, String data) {
        return BaseWebResponse.builder()
                .errorCode(errorCode)
                .data(data)
                .build();
    }
}
