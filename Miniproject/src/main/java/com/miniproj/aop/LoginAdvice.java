package com.miniproj.aop;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.miniproj.domain.LoginDTO;
import com.miniproj.domain.Member;
import com.miniproj.etc.UploadFileProcess;

@Component
@Aspect
public class LoginAdvice {

	Logger logger = LoggerFactory.getLogger(LoginAdvice.class);
	
	@Around("execution(public * com.miniproj.service.member.MemberServiceImpl.login(..))")
	public Object loginLog(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("-----------------------------------------------");
		System.out.println(" 로그인 시도 !! ");	
		System.out.println("-----------------------------------------------");
		
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String logRealPath = req.getSession().getServletContext().getRealPath("resources/logs");
		System.out.println("logRealPath : "+logRealPath);
		
		String realPath = UploadFileProcess.makeCalculcatePath(logRealPath);
		System.out.println("reatPath : "+realPath);
		
		Calendar cal = Calendar.getInstance();
		String fileName = realPath + File.separator + cal.get(Calendar.YEAR) +
				new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1) +
				new DecimalFormat("00").format(cal.get(Calendar.DATE)) + ".log";
		System.out.println("fileName : "+fileName);
		
		Object[] args = pjp.getArgs();
		System.out.println(Arrays.toString(args));
		
		String tryLoginMember = ((LoginDTO)pjp.getArgs()[0]).getUserId();
		
		Object result = pjp.proceed();
		
		System.out.println("로그인 성공여부 (aop) : "+(Member)result);
		
		String resultLogin = ((Member)result != null) ? "로그인 성공" : "로그인 실패";
		
		// 파일에 저장
		BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true));
		bw.newLine();
		bw.write(tryLoginMember + "가" + cal.getTime().toString() + "에 접속을 시도함.." + resultLogin);
		
		bw.close();
		
		return result;
	}
	
}
