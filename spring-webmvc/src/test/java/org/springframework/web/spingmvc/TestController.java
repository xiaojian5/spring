package org.springframework.web.spingmvc;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.spingmvc.pojo.TestOutput;

/**
 * @author: dengxin.chen
 * @date: 2019-06-21 10:30
 * @description: spring mvc test controller
 */
@RestController
@RequestMapping(value = "/spring/mvc/test")
public class TestController {

	@PostMapping(value = "/get")
	public String getMsg(String input) {
		return input;
	}

	@PostMapping(value = "/get/bean")
	public TestOutput getBean() {
		TestOutput output = new TestOutput();
		output.setAge(10);
		output.setUserName("testBean");
		return output;
	}
}
