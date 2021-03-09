package org.zerock.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Aspect // 해당 클래스의 객체가 Aspect를 구현한 것임을 나타내기 위해 사용
@Log4j
@Component // AOP와 관계 없지만 스프링에서 빈으로 인식하기 위해 사용
public class LogAdvice {

	@Before("execution(* org.zerock.service.SampleService*.*(..))")
	public void logBefore() {

		log.info("========================");
	}

	@Before("execution(* org.zerock.service.SampleService*.doAdd(String, String)) && args(str1, str2)")
	public void logBeforeWithParam(String str1, String str2) {

		log.info("str1: " + str1);
		log.info("str2: " + str2);
	}

	@AfterThrowing(pointcut = "execution(* org.zerock.service.SampleService*.*(..))", throwing = "exception")
	public void logException(Exception exception) {

		log.info("Exception....!!!!");
		log.info("exception: " + exception);

	}

	@Around("execution(* org.zerock.service.SampleService*.*(..))") // @Around가 적용되는 메서드는 리턴 타입이 void아 아닌 타입으로 설정하고, 메서드 실행 결과도 직접 반환해야
	public Object logTime(ProceedingJoinPoint pjp) { // ProceedingJoinPoint는 @Around와 결합해서 파라미터나 예외 등을 처리

		long start = System.currentTimeMillis();

		log.info("Target: " + pjp.getTarget());
		log.info("Param: " + Arrays.toString(pjp.getArgs()));

		Object result = null;

		try {
			result = pjp.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}

		long end = System.currentTimeMillis();

		log.info("TIME: " + (end - start));

		return result;
	}

}
