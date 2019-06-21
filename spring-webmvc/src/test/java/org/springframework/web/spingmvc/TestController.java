package org.springframework.web.spingmvc;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
