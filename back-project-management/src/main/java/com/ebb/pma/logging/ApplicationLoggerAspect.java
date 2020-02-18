package com.ebb.pma.logging;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ApplicationLoggerAspect {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**@Pointcut("within(com.jrp.pma.controllers..*)"
			+ " || within(com.jrp.pma.dao..*)")*/
	@Pointcut("within(com.jrp.pma.controllers..*)")
	public void definePackagePointCuts() {
		// empty method just to name the location specified in the pointcut
	}
	
	@Around("definePackagePointCuts()")
	public Object logAround(ProceedingJoinPoint pjp) {
		log.debug(" \n \n \n ");
		log.debug(" ************* Before Method Execution  ************ \n {}.{} () with arguments[s] = {}",
				pjp.getSignature().getDeclaringTypeName(),
				pjp.getSignature().getName(), Arrays.toString(pjp.getArgs()));
		log.debug("____________________________________________________ \n \n \n");
		
		Object o = null;
		try {
			o = pjp.proceed();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.debug(" \n \n \n ");
		log.debug(" ************* After Method Execution  ************ \n {}.{} () with arguments[s] = {}",
				pjp.getSignature().getDeclaringTypeName(),
				pjp.getSignature().getName(), Arrays.toString(pjp.getArgs()));
		log.debug("____________________________________________________ \n \n \n");
		
		return o;
	}
	
	
	
	/**@After("definePackagePointCuts()")*/
	/**@Before("definePackagePointCuts()")
	public void logBefore(JoinPoint jp) {
		log.debug(" \n \n \n ");
		log.debug(" ************* Before Method Execution  ************ \n {}.{} () with arguments[s] = {}",
				jp.getSignature().getDeclaringTypeName(),
				jp.getSignature().getName(), Arrays.toString(jp.getArgs()));
		log.debug("____________________________________________________ \n \n \n");
	}*/
}
