package org.zerock.service;

import org.springframework.stereotype.Service;

@Service // 스프링에서 빈으로 사용될 수 있도록 설정
public class SampleServiceImpl implements SampleService {

	@Override
	public Integer doAdd(String str1, String str2) throws Exception {

		return Integer.parseInt(str1) + Integer.parseInt(str2);

	}

}
