package EducacionIt.web.aspectos;

import EducacionIt.web.entities.Persona;
import EducacionIt.web.validator.NotVoid;
import EducacionIt.web.validator.NotVoidValidator;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Log4j2
@Aspect
@Component
public class NotVoidAspect {

    @Around("@annotation(EducacionIt.web.validator.NotVoid) || execution(* *(.., @EducacionIt.web.validator.NotVoid (*), ..))")
    public Object methodValidator(ProceedingJoinPoint pjp) throws Throwable {
        //################### Lógica ANTES de ejecutar el método
        log.info("Antes del método: " + pjp.getSignature());
        Method metodo = ((MethodSignature) pjp.getSignature()).getMethod();
        Object[] args = pjp.getArgs();
        NotVoidValidator.validateParameters(metodo, args);

        //################### ejecuta el método original
        Object resultado = pjp.proceed();


        //################### Lógica DESPUÉS de ejecutar el método
        log.info("Después del método: " + metodo.getName());
        if (metodo.getName().equals("update")) {
            Persona p = (Persona) resultado;
            p.setDireccion("***********************************");
            return p;
        }

        return resultado;
    }


    @PostConstruct
    public void init() {
        log.info(">>> NotVoidAspect registrado <<<");
    }
}
