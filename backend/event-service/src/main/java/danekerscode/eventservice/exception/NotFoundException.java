package danekerscode.eventservice.exception;

import org.aspectj.weaver.ast.Not;

public class NotFoundException extends RuntimeException{
    public NotFoundException(Class<?> c){
        super(c.getSimpleName().concat(" not found"));
    }
}
