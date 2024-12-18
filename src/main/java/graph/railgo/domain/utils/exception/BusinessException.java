package graph.railgo.domain.utils.exception;


public class BusinessException extends RuntimeException {
    ExceptionCode responseCode;

    public BusinessException(ExceptionCode responseCode, String message) {
        super(message);
        this.responseCode = responseCode;
    }
    public BusinessException(ExceptionCode responseCode){
        this.responseCode = responseCode;
    }
    public BusinessException(){

    }
    public BusinessException(String message) {
        super(message);
    }

    public ExceptionCode getExceptionCode() {
        return responseCode;
    }
}
