package cc.co.llabor.props;

import java.io.*;
import java.util.*;
/** 
 * <b> 
 * The CommentedProperties class is an extension of java.util.Properties
 * to allow retention of comment lines and blank (whitespace only) lines
 * in the properties file.
 * 
 * </b>
 * @author      vipup<br>
 * <br>
 * <b>Copyright:</b>     Copyright (c) 2006-2008 Monster AG <br>
 * <b>Company:</b>       Monster AG  <br>
 * 
 * Creation:  17.02.2012::10:02:59<br> 
 */
public class CommentedProperties extends java.util.Properties {

	/**
	 * @author vipup
	 */
	private static final long serialVersionUID = 9183011519738049548L;

	private static final String STD_ENCODING = "ISO-8859-1";

	private static final String EOL = "\n";

	/**
	 * parallel comments-properties-array
	 */
	java.util.Properties  comments = new Properties();

	private int linecount;

	private String title;

	public CommentedProperties(String sTitle) {
		this.setTitle(sTitle);
	}
	public CommentedProperties() {
		title = null;
	}
	/**
	 * Load properties from the specified InputStream. 
	 * Overload the load method in Properties so we can keep comment and blank lines.
	 * @param   inStream   The InputStream to read.
	 */
	public void load(InputStream inStream) throws IOException
	{
		// The spec says that the file must be encoded using ISO-8859-1.
		BufferedReader inBuf =
		new BufferedReader(new InputStreamReader(inStream, STD_ENCODING));
		StringWithComments line;
 
		while ((line = readln(inBuf)) != null) {
			String key_val []= line.getLine().replace(" ", "\t").replace("\t\t", "\t").replace("\t\t", "\t").replace("\t\t", "\t").replace("\t\t", "\t").replace("\t\t", "\t").replace("\t\t", "\t").replace("\t\t", "\t").replace("\t", ";").split("=");
			String keyTmp;
			String valTmp;
			try{
				keyTmp = key_val [0];
				valTmp= line.getLine();
				valTmp = valTmp.substring(keyTmp.length() +1).trim();
			}catch(ArrayIndexOutOfBoundsException e){
				continue;
			}catch(StringIndexOutOfBoundsException e){
				continue;
			}
			String comments2 = line.getComments();
			this.comments.put(keyTmp, comments2);
			this.put(keyTmp, valTmp); 
		}

	}
	/**
	 * return trimmed, uncommented, non-empty line of CFG-File
	 * @author vipup
	 * @param inBuf 
	 * @return
	 * @throws IOException
	 */
	private StringWithComments readln(BufferedReader inBuf) throws IOException {
		String retval = inBuf.readLine();
		
		linecount++;
		String skipped = "";
		String prefix = "";
		try{
			retval = retval.trim();
			// #
			while (retval.startsWith("#")||"".equals( retval) ){
				skipped += prefix;
				skipped += retval;
				prefix += EOL;
				retval = inBuf.readLine();
				retval = retval.trim();				
			}
			
		}catch(NullPointerException e){
			//throw new IOException("EOF at line "+linecount);
			return null ;
		} 
		return new StringWithComments(retval, skipped);
	}
	/**
	 * Write the properties to the specified OutputStream.
	 * 
	 * Overloads the store method in Properties so we can put back comment	
	 * and blank lines.													  
	 * 
	 * @param out	The OutputStream to write to.
	 * @param header Ignored, here for compatability w/ Properties.
	 * 
	 * @exception IOException
	 */
	public void store(OutputStream out, String header) throws IOException
	{
		// The spec says that the file must be encoded using ISO-8859-1.
		PrintWriter pw
		= new PrintWriter(new OutputStreamWriter(out, STD_ENCODING));
		if (title!=null){
			String titleTmp = this.title;
			titleTmp = translit(titleTmp);
			pw.println(("#"+titleTmp).replace("##", "#"));
		}
		for(String key:this.keySet().toArray(new String[]{})){
			String commentTmp = comments.getProperty(key);
			if (commentTmp!=null){
				pw.println(translit(commentTmp));
			}
			pw.println(key+"="+this.getProperty(key));
		} 
		pw.flush ();
	}
	public String translit(String titleTmp) {
		titleTmp = titleTmp.replace(("а".toUpperCase()), "a".toUpperCase());
		titleTmp = titleTmp.replace(("б".toUpperCase()), "b".toUpperCase());
		titleTmp = titleTmp.replace(("в".toUpperCase()), "v".toUpperCase());
		titleTmp = titleTmp.replace(("г".toUpperCase()), "g".toUpperCase());
		titleTmp = titleTmp.replace(("д".toUpperCase()), "d".toUpperCase());
		titleTmp = titleTmp.replace(("е".toUpperCase()), "e".toUpperCase());
		titleTmp = titleTmp.replace(("ё".toUpperCase()), "yo".toUpperCase());
		titleTmp = titleTmp.replace(("ж".toUpperCase()), "zh".toUpperCase());
		titleTmp = titleTmp.replace(("з".toUpperCase()), "z".toUpperCase());
		titleTmp = titleTmp.replace(("и".toUpperCase()), "i".toUpperCase());
		titleTmp = titleTmp.replace(("й".toUpperCase()), "j".toUpperCase());
		titleTmp = titleTmp.replace(("к".toUpperCase()), "k".toUpperCase());
		titleTmp = titleTmp.replace(("л".toUpperCase()), "l".toUpperCase());
		titleTmp = titleTmp.replace(("м".toUpperCase()), "m".toUpperCase());
		titleTmp = titleTmp.replace(("н".toUpperCase()), "n".toUpperCase());
		titleTmp = titleTmp.replace(("о".toUpperCase()), "o".toUpperCase());
		titleTmp = titleTmp.replace(("п".toUpperCase()), "p".toUpperCase());
		titleTmp = titleTmp.replace(("р".toUpperCase()), "r".toUpperCase());
		titleTmp = titleTmp.replace(("с".toUpperCase()), "s".toUpperCase());
		titleTmp = titleTmp.replace(("т".toUpperCase()), "t".toUpperCase());
		titleTmp = titleTmp.replace(("у".toUpperCase()), "u".toUpperCase());
		titleTmp = titleTmp.replace(("ф".toUpperCase()), "f".toUpperCase());
		titleTmp = titleTmp.replace(("х".toUpperCase()), "h".toUpperCase());
		titleTmp = titleTmp.replace(("ц".toUpperCase()), "ts".toUpperCase());
		titleTmp = titleTmp.replace(("ч".toUpperCase()), "ch".toUpperCase());
		titleTmp = titleTmp.replace(("ш".toUpperCase()), "sh".toUpperCase());
		titleTmp = titleTmp.replace(("щ".toUpperCase()), "sh'".toUpperCase());
		titleTmp = titleTmp.replace(("ъ".toUpperCase()), "`".toUpperCase());
		titleTmp = titleTmp.replace(("ы".toUpperCase()), "y".toUpperCase());
		titleTmp = titleTmp.replace(("ь".toUpperCase()), "'".toUpperCase());
		titleTmp = titleTmp.replace(("э".toUpperCase()), "e".toUpperCase());
		titleTmp = titleTmp.replace(("ю".toUpperCase()), "yu".toUpperCase());
		titleTmp = titleTmp.replace(("я".toUpperCase()), "ya".toUpperCase()); 
		titleTmp = titleTmp.replace(("а"), "a");
		titleTmp = titleTmp.replace(("б"), "b");
		titleTmp = titleTmp.replace(("в"), "v");
		titleTmp = titleTmp.replace(("г"), "g");
		titleTmp = titleTmp.replace(("д"), "d");
		titleTmp = titleTmp.replace(("е"), "e");
		titleTmp = titleTmp.replace(("ё"), "yo");
		titleTmp = titleTmp.replace(("ж"), "zh");
		titleTmp = titleTmp.replace(("з"), "z");
		titleTmp = titleTmp.replace(("и"), "i");
		titleTmp = titleTmp.replace(("й"), "j");
		titleTmp = titleTmp.replace(("к"), "k");
		titleTmp = titleTmp.replace(("л"), "l");
		titleTmp = titleTmp.replace(("м"), "m");
		titleTmp = titleTmp.replace(("н"), "n");
		titleTmp = titleTmp.replace(("о"), "o");
		titleTmp = titleTmp.replace(("п"), "p");
		titleTmp = titleTmp.replace(("р"), "r");
		titleTmp = titleTmp.replace(("с"), "s");
		titleTmp = titleTmp.replace(("т"), "t");
		titleTmp = titleTmp.replace(("у"), "u");
		titleTmp = titleTmp.replace(("ф"), "f");
		titleTmp = titleTmp.replace(("х"), "h");
		titleTmp = titleTmp.replace(("ц"), "ts");
		titleTmp = titleTmp.replace(("ч"), "ch");
		titleTmp = titleTmp.replace(("ш"), "sh");
		titleTmp = titleTmp.replace(("щ"), "sh'");
		titleTmp = titleTmp.replace(("ъ"), "`");
		titleTmp = titleTmp.replace(("ы"), "y");
		titleTmp = titleTmp.replace(("ь"), "'");
		titleTmp = titleTmp.replace(("э"), "e");
		titleTmp = titleTmp.replace(("ю"), "yu");
		titleTmp = titleTmp.replace(("я"), "ya");
		titleTmp = titleTmp.replace(("«"), "\"");
		titleTmp = titleTmp.replace(("»"), "\"");
		titleTmp = titleTmp.replace(("№"), "No");
		return titleTmp;
	}
  
	@Override
	public Object setProperty(String key, String value){
		return super.setProperty(key, value); 
	}
	
	public Object setProperty(String key, String value, String comment){
		this.comments.put(key, comment.trim());
		return super.setProperty(key, value); 
	}
	
	/**
	 * Return comment, associated with property
	 * @author vipup
	 * @param string
	 * @return
	 */
	public String getPropertyComment(String key) { 
		return comments.getProperty(key); 
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() { 
		return title;
	}
	
}


 