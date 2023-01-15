package cog.caseStudy.qnaportal.userservice.exceptions;

public class SpringQnAException extends RuntimeException {
    public SpringQnAException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public SpringQnAException(String exMessage) {
        super(exMessage);
    }
}
