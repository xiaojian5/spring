/*
 * Copyright 2002-2017 the original author or authors.
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

package org.springframework.beans.factory.xml;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.Nullable;

/**
 * EntityResolver implementation that tries to resolve entity references
 * through a {@link org.springframework.core.io.ResourceLoader} (usually,
 * relative to the resource base of an ApplicationContext), if applicable.
 * Extends {@link DelegatingEntityResolver} to also provide DTD and XSD lookup.
 *
 * <p>Allows to use standard XML entities to include XML snippets into an
 * application context definition, for example to split a large XML file
 * into various modules. The include paths can be relative to the
 * application context's resource base as usual, instead of relative
 * to the JVM working directory (the XML parser's default).
 *
 * <p>Note: In addition to relative paths, every URL that specifies a
 * file in the current system root, i.e. the JVM working directory,
 * will be interpreted relative to the application context too.
 *
 * @author Juergen Hoeller
 * @see org.springframework.core.io.ResourceLoader
 * @see org.springframework.context.ApplicationContext
 * @since 31.07.2003
 */

/**
 * 继承DelegatingEntityResolver类，通过resourceLoader来解析实体
 */
public class ResourceEntityResolver extends DelegatingEntityResolver {

	private static final Log logger = LogFactory.getLog(ResourceEntityResolver.class);

	private final ResourceLoader resourceLoader;


	/**
	 * Create a ResourceEntityResolver for the specified ResourceLoader
	 * (usually, an ApplicationContext).
	 *
	 * @param resourceLoader the ResourceLoader (or ApplicationContext)
	 *                       to load XML entity includes with
	 */
	public ResourceEntityResolver(ResourceLoader resourceLoader) {
		super(resourceLoader.getClassLoader());
		this.resourceLoader = resourceLoader;
	}

	/**
	 * 解析实体
	 *
	 * @param publicId 被引用的外部实体的公共标识符，如果没有提供，可设置为null
	 * @param systemId 被引用的外部实体的系统标识符 eg:http://www.springframework.org/schema/spring-beans.xsd
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 */
	@Override
	@Nullable
	public InputSource resolveEntity(String publicId, @Nullable String systemId) throws SAXException, IOException {
		// 调用父类[DelegatingEntityResolver]的方法，进行解析
		InputSource source = super.resolveEntity(publicId, systemId);
		// 如果解析失败，才通过resourceLoader进行解析
		if (source == null && systemId != null) {
			String resourcePath = null;
			try {
				// 使用UTF-8解析systemId
				String decodedSystemId = URLDecoder.decode(systemId, "UTF-8");
				// 转换成URL字符串
				String givenUrl = new URL(decodedSystemId).toString();
				// 解析文件资源的相对路径（相对于根目录）
				String systemRootUrl = new File("").toURI().toURL().toString();
				// Try relative to resource base if currently in system root.
				if (givenUrl.startsWith(systemRootUrl)) {
					// 在这里取出相对路径
					resourcePath = givenUrl.substring(systemRootUrl.length());
				}
			} catch (Exception ex) {
				// Typically a MalformedURLException or AccessControlException.
				if (logger.isDebugEnabled()) {
					logger.debug("Could not resolve XML entity [" + systemId + "] against system root URL", ex);
				}
				// No URL (or no resolvable URL) -> try relative to resource base.
				// 如果解析出现异常，则将全路径赋值给相对路径
				resourcePath = systemId;
			}
			// 如果解析出的相对路径不为null
			if (resourcePath != null) {
				if (logger.isTraceEnabled()) {
					logger.trace("Trying to locate XML entity [" + systemId + "] as resource [" + resourcePath + "]");
				}
				// 获取Resource资源
				Resource resource = this.resourceLoader.getResource(resourcePath);
				// 创建InputSource对象
				source = new InputSource(resource.getInputStream());
				// 设置publicId和systemId属性
				source.setPublicId(publicId);
				source.setSystemId(systemId);
				if (logger.isDebugEnabled()) {
					logger.debug("Found XML entity [" + systemId + "]: " + resource);
				}
			}
		}
		// 父类方法解析成功，直接返回
		return source;
	}

}
