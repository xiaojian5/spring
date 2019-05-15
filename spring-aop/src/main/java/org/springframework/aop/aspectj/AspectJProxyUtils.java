/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.aop.aspectj;

import java.util.List;

import org.springframework.aop.Advisor;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.interceptor.ExposeInvocationInterceptor;

/**
 * Utility methods for working with AspectJ proxies.
 *
 * @author Rod Johnson
 * @author Ramnivas Laddad
 * @author Juergen Hoeller
 * @since 2.0
 */
public abstract class AspectJProxyUtils {

	/**
	 * Add special advisors if necessary to work with a proxy chain that contains AspectJ advisors:
	 * concretely, {@link ExposeInvocationInterceptor} at the beginning of the list.
	 * <p>This will expose the current Spring AOP invocation (necessary for some AspectJ pointcut
	 * matching) and make available the current AspectJ JoinPoint. The call will have no effect
	 * if there are no AspectJ advisors in the advisor chain.
	 * @param advisors the advisors available
	 * @return {@code true} if an {@link ExposeInvocationInterceptor} was added to the list,
	 * otherwise {@code false}
	 */
	public static boolean makeAdvisorChainAspectJCapableIfNecessary(List<Advisor> advisors) {
		// Don't add advisors to an empty list; may indicate that proxying is just not required
		// 如果advisor为空，则不做处理
		if (!advisors.isEmpty()) {
			boolean foundAspectJAdvice = false;
			// 遍历advisor，判断advisor列表中是否存在AspectJ advice
			for (Advisor advisor : advisors) {
				// Be careful not to get the Advice without a guard, as this might eagerly
				// instantiate a non-singleton AspectJ aspect...
				if (isAspectJAdvice(advisor)) {
					foundAspectJAdvice = true;
					break;
				}
			}
			// 向advisor列表头添加ExposeInvocationInterceptor
			// 加入ExposeInvocationInterceptor的作用是暴露MethodInvocation对象到ThreadLocal中，如果其他地方需要当前的
			// MethodInvocation则可以通过调用currenInvocation方法获取。
			if (foundAspectJAdvice && !advisors.contains(ExposeInvocationInterceptor.ADVISOR)) {
				advisors.add(0, ExposeInvocationInterceptor.ADVISOR);
				return true;
			}
		}
		return false;
	}

	/**
	 * Determine whether the given Advisor contains an AspectJ advice.
	 * @param advisor the Advisor to check
	 */
	private static boolean isAspectJAdvice(Advisor advisor) {
		return (advisor instanceof InstantiationModelAwarePointcutAdvisor ||
				advisor.getAdvice() instanceof AbstractAspectJAdvice ||
				(advisor instanceof PointcutAdvisor &&
						((PointcutAdvisor) advisor).getPointcut() instanceof AspectJExpressionPointcut));
	}

}
