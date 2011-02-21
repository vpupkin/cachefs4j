package cc.co.llabor.cache;

import java.io.InputStream;

/** 
 * <b>Description:TODO</b>
 * @author      gennady<br>
 * <br>
 * <b>Copyright:</b>     Copyright (c) 2006-2008 Monster AG <br>
 * <b>Company:</b>       Monster AG  <br>
 * 
 * Creation:  Feb 16, 2011::9:24:04 PM<br> 
 */
public class BinaryContent {

	private InputStream content;
	private String contentType;
	private String characterEncoding;

	public BinaryContent(InputStream content, String contentType,
			String characterEncoding) {
		this.content = content;
		this.contentType = contentType;
		this.characterEncoding = characterEncoding;
	}

	public InputStream getContent() { 
			return content;
	}

	public String getContentType() {  
			return contentType;
	}

	public String getCharacterEncoding() { 
			return characterEncoding;
	}

}


 