package cc.co.llabor.cache;

import java.io.IOException;
import java.io.OutputStream;   
import net.sf.jsr107cache.Cache; 

/** 
 * <b>Description:TODO</b>
 * @author      vipup<br>
 * <br>
 * <b>Copyright:</b>     Copyright (c) 2006-2008 Monster AG <br>
 * <b>Company:</b>       Monster AG  <br>
 * 
 * Creation:  14.04.2010::10:50:13<br> 
 */
public class MemoryFileCache {
	
	 private String cachename;
	
	public MemoryFileCache(String cachename) {
		this.cachename = cachename;
	}

	public static MemoryFileCache getInstance(String cachename){
		return new MemoryFileCache( cachename );
	}

	public MemoryFileItem get (String name) throws IOException{
			Cache cache = Manager.getCache(this.cachename);
			if (cache instanceof FileCache){
				String baseDir = ((FileCache)cache).getBaseDir();
				name = name.startsWith(baseDir)?name.substring(baseDir.length()):name;
			}
			MemoryFileItem retval = null;
			Object o = cache.get(name);
			if (o instanceof String){
				retval = new MemoryFileItem (name,"text/plain",false,name, 0);
				retval.getOutputStream().write( ((String)o).getBytes());
				retval.flush();
			}
			if (o instanceof MemoryFileItem)
				retval = (MemoryFileItem) o; 
			 if (retval ==null){ // try to restore parts
				  for (int i=0;cache.get(name+"::"+i)!=null;){
					 MemoryFileItem next = (MemoryFileItem)cache.get(name+"::"+i);				 
					 if (i==0 ){
						 retval = new MemoryFileItem (next.fileName,next.contentType,next.isFormField(),next.fileName, 0);
					 }
					 retval.getOutputStream().write(next.get());
					 i++;
				 }
				 retval.flush();
			 }
			 return retval;
		 }	
             
	 public String put (MemoryFileItem  item) throws IOException{
		 Cache cache = Manager.getCache(cachename);
		 String name = item.getName();
		 byte[] bs = item.get();
		 
		if (bs.length < MAX_SIZE){
			 cache.put(name,item);
		 }else{ //SPLIT
			 int done = 0;
			 for (int i=0;done<bs.length ;i++){
				 String nameTmp = item.getName()+"::"+i;
				 MemoryFileItem itemNext =  new MemoryFileItem (item.fileName,item.contentType,item.isFormField(),item.fileName, 0);
				 OutputStream outputStream = itemNext.getOutputStream();
				 outputStream.write(bs, done,Math.min( MAX_BUFF_SIZE, bs.length-done ));
				 itemNext.flush();
				 done += MAX_BUFF_SIZE;
				 try{
					 cache.put(nameTmp,itemNext);
				 }catch(Throwable e){
					 e.printStackTrace();
					 throw new IOException (e.getMessage());
				 }
			 }
		 }
		 return name;
	 }
	
	
	/**
	 * @deprecated use getInstance()
	 * 
	 * 
	 * @author vipup
	 * @param name
	 * @return
	 * @throws IOException
	 */
	 static MemoryFileItem _get (String name) throws IOException{
		Cache cache = Manager.getCache();
		MemoryFileItem retval = (MemoryFileItem) cache.get(name);
		 if (retval ==null){ // try to restore parts
			  for (int i=0;cache.get(name+"::"+i)!=null;){
				 MemoryFileItem next = (MemoryFileItem)cache.get(name+"::"+i);				 
				 if (i==0 ){
					 retval = new MemoryFileItem (next.fileName,next.contentType,next.isFormField(),next.fileName, 0);
				 }
				 retval.getOutputStream().write(next.get());
				 i++;
			 }
			 retval.flush();
		 }
		 return retval;
	 }
	 static int MAX_SIZE = 64*1024;
	 static int MAX_BUFF_SIZE = MAX_SIZE;

	 /**
	  * 
	  * @deprecated
	  * 
	  * @author vipup
	  * @param item
	  * @return
	  * @throws IOException
	  */
	 static String _put (MemoryFileItem  item) throws IOException{
		 Cache cache = Manager.getCache();
		 String name = item.getName();
		 byte[] bs = item.get();
		 
		if (bs.length < MAX_SIZE){
			 cache.put(name,item);
		 }else{ //SPLIT
			 int done = 0;
			 for (int i=0;done<bs.length ;i++){
				 String nameTmp = item.getName()+"::"+i;
				 MemoryFileItem itemNext =  new MemoryFileItem (item.fileName,item.contentType,item.isFormField(),item.fileName, 0);
				 OutputStream outputStream = itemNext.getOutputStream();
				 outputStream.write(bs, done,Math.min( MAX_BUFF_SIZE, bs.length-done ));
				 itemNext.flush();
				 done += MAX_BUFF_SIZE;
				 try{
					 cache.put(nameTmp,itemNext);
				 }catch(Throwable e){
					 e.printStackTrace();
					 throw new IOException (e.getMessage());
				 }
			 }
		 }
		 return name;
	 }


}


 