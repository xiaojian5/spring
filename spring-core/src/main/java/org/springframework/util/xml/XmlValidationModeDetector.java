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

package org.springframework.util.xml;

import java.io.BufferedReader;
import java.io.CharConversionException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

/**
 * 验证模式探测器，检测资源到底使用哪一种验证模式DTD or XSD<br/>
 * Detects whether an XML stream is using DTD- or XSD-based validation.
 *
 * @author Rob Harrop
 * @author Juergen Hoeller
 * @since 2.0
 */
public class XmlValidationModeDetector {

	/**
	 * Indicates that the validation should be disabled.
	 */
	public static final int VALIDATION_NONE = 0;

	/**
	 * Indicates that the validation mode should be auto-guessed, since we cannot find
	 * a clear indication (probably choked on some special characters, or the like).
	 */
	public static final int VALIDATION_AUTO = 1;

	/**
	 * Indicates that DTD validation should be used (we found a "DOCTYPE" declaration).
	 */
	public static final int VALIDATION_DTD = 2;

	/**
	 * Indicates that XSD validation should be used (found no "DOCTYPE" declaration).
	 */
	public static final int VALIDATION_XSD = 3;


	/**
	 * The token in a XML document that declares the DTD to use for validation
	 * and thus that DTD validation is being used.
	 */
	private static final String DOCTYPE = "DOCTYPE";

	/**
	 * 注释开始标志 <br/>
	 * The token that indicates the start of an XML comment.
	 */
	private static final String START_COMMENT = "<!--";

	/**
	 * 注释结束标志"-->" <br/>
	 * The token that indicates the end of an XML comment.
	 */
	private static final String END_COMMENT = "-->";


	/**
	 * 表明当前内容是否为注释<br/>
	 * Indicates whether or not the current parse position is inside an XML comment.
	 */
	private boolean inComment;


	/**
	 * 检测InputStream的验证模式<br/>
	 * Detect the validation mode for the XML document in the supplied {@link InputStream}.
	 * Note that the supplied {@link InputStream} is closed by this method before returning.
	 *
	 * @param inputStream the InputStream to parse
	 * @throws IOException in case of I/O failure
	 * @see #VALIDATION_DTD
	 * @see #VALIDATION_XSD
	 */
	public int detectValidationMode(InputStream inputStream) throws IOException {
		// 将InputStream进行包装，便于读取
		// Peek into the file to look for DOCTYPE.
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		try {
			// 是否为DTD验证模式，默认为false，即不是DTD验证模式，那就是XSD验证模式
			boolean isDtdValidated = false;
			String content;
			// 循环读取xml资源的内容
			while ((content = reader.readLine()) != null) {
				// 消费注释内容，返回有用信息
				content = consumeCommentTokens(content);
				// 如果为注释，或者为空，则继续循环
				if (this.inComment || !StringUtils.hasText(content)) {
					continue;
				}
				// #1.如果包含"DOCTYPE"，则为DTD验证模式
				if (hasDoctype(content)) {
					isDtdValidated = true;
					break;
				}
				// #2.该方法会校验，内容中是否有"<",并且"<"后面还跟着字母，如果是则返回true
				// 如果为true，最终就是XSD模式
				if (hasOpeningTag(content)) {
					// End of meaningful data...
					break;
				}
			}
			// 返回DTD模式或XSD模式
			return (isDtdValidated ? VALIDATION_DTD : VALIDATION_XSD);
		} catch (CharConversionException ex) {
			// Choked on some character encoding...
			// Leave the decision up to the caller.
			// 如果发生异常，则返回自动验证模式
			return VALIDATION_AUTO;
		} finally {
			reader.close();
		}
	}


	/**
	 * 是否包含"DOCTYPE"<br/>
	 * 从这里的函数命名可以看出，表示是否有可以用hasXXXX[会心一笑]<br/>
	 * Does the content contain the DTD DOCTYPE declaration?
	 */
	private boolean hasDoctype(String content) {
		return content.contains(DOCTYPE);
	}

	/**
	 * 是否有打开标志"<" <br/>
	 * Does the supplied content contain an XML opening tag. If the parse state is currently
	 * in an XML comment then this method always returns false. It is expected that all comment
	 * tokens will have consumed for the supplied content before passing the remainder to this method.
	 */
	private boolean hasOpeningTag(String content) {
		// 如果是注释，则直接返回false
		if (this.inComment) {
			return false;
		}
		// "<"标志的位置
		int openTagIndex = content.indexOf('<');
		// 如果"<"存在&&"<"后面还有内容&&"<"后面位置上是字母，则返回ture
		return (openTagIndex > -1 && (content.length() > openTagIndex + 1) &&
				Character.isLetter(content.charAt(openTagIndex + 1)));
	}

	/**
	 * 清除注释信息，返回剩下的有用的信息<br/>
	 * Consumes all the leading comment data in the given String and returns the remaining content, which
	 * may be empty since the supplied content might be all comment data. For our purposes it is only important
	 * to strip leading comment content on a line since the first piece of non comment content will be either
	 * the DOCTYPE declaration or the root element of the document.
	 */
	@Nullable
	private String consumeCommentTokens(String line) {
		// 非注释，即为有用信息
		if (!line.contains(START_COMMENT) && !line.contains(END_COMMENT)) {
			return line;
		}
		String currLine = line;
		// 寻找有用信息
		while ((currLine = consume(currLine)) != null) {
			// 当inComment标志位更新，并且返回信息不是以注释开始标志开始就返回currLine
			if (!this.inComment && !currLine.trim().startsWith(START_COMMENT)) {
				return currLine;
			}
		}
		// 如果没有有用信息，则返回null
		return null;
	}

	/**
	 * 消费注释信息，并更新inComment[是否为注释]，返回剩余信息<br/>，
	 * Consume the next comment token, update the "inComment" flag
	 * and return the remaining content.
	 */
	@Nullable
	private String consume(String line) {
		// 如果inComment-true，则走endComent函数；false-startComment函数，初始时为false
		// 因此这里会走startComment，返回注释位置的index[注释位置+1的index]
		int index = (this.inComment ? endComment(line) : startComment(line));
		// 如果index=-1，则表示没有注释信息，否则返回注释信息
		return (index == -1 ? null : line.substring(index));
	}

	/**
	 * Try to consume the {@link #START_COMMENT} token.
	 *
	 * @see #commentToken(String, String, boolean)
	 */
	private int startComment(String line) {
		// 返回注释开始标志的位置信息
		return commentToken(line, START_COMMENT, true);
	}

	private int endComment(String line) {
		return commentToken(line, END_COMMENT, false);
	}

	/**
	 * 更新inComent，并返回注释标志的位置信息<br/>
	 * Try to consume the supplied token against the supplied content and update the
	 * in comment parse state to the supplied value. Returns the index into the content
	 * which is after the token or -1 if the token is not found.
	 *
	 * @param line               传入信息
	 * @param token              注释开始标志
	 * @param inCommentIfPresent 是否是注释，默认在startComment中传入true
	 */
	private int commentToken(String line, String token, boolean inCommentIfPresent) {
		// 查找注释标志的开始位置[<!--或-->]
		int index = line.indexOf(token);
		// index>-1表示存在注释开始标志,并将inComment更新为inCommentIfPresent
		// [默认在startComment为true，endComment为false]
		if (index > -1) {
			this.inComment = inCommentIfPresent;
		}
		// 如果index=-1，则返回注释标志的后一个位置信息index+token.length()
		return (index == -1 ? index : index + token.length());
	}

}
