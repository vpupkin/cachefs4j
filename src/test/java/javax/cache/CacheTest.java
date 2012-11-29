package javax.cache;

import java.util.ArrayList;
import java.util.Collection; 
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;

import cc.co.llabor.cache.CacheManager;
 
 

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheEntry;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheListener; 
import net.sf.jsr107cache.CacheStatistics;
 

import junit.framework.TestCase;

/** 
 * <b>Description:TODO</b>
 * @author      Gennady<br>
 * <br>
 * <b>Copyright:</b>     Copyright (c) 2006-2008 OXSEED AG <br>
 * <b>Company:</b>       OXSEED AG  <br>
 * 
 * Creation:  28.10.2008::16:40:37<br> 
 */
public class CacheTest extends TestCase {

	private static final String ON_REMOVE = "R:";
	private static final String ON_PUT = "P:";
	private static final String ON_LOAD = "L:";
	private static final String ON_EVICT = "E:";
	private static final String ON_CLEAR = "onClear";
	private static final Logger log = Logger .getLogger(CacheTest.class.getName());

 

	public void testSize() {
		CacheManager cmTmp = CacheManager.getInstance();
		Cache cacheTmp = cmTmp.getCache(this.getName());
		cacheTmp.put("key", "value");
		assertEquals(1, cacheTmp.size());
		cacheTmp.remove("key");
	}
	
	public void testIsEmpty() {
		CacheManager cmTmp = CacheManager.getInstance();
		Cache cacheTmp = cmTmp.getCache(this.getName());
		cacheTmp.put("key", "value");
		assertTrue(!cacheTmp.isEmpty());
		cacheTmp.remove("key");
		assertTrue (cacheTmp.isEmpty());
	}

	public void testClear() {
		CacheManager cmTmp = CacheManager.getInstance();
		Cache cacheTmp = cmTmp.getCache(this.getName());
		cacheTmp.put("key", "value");
		cacheTmp.clear();
		assertTrue(cacheTmp.isEmpty());
	}
		


	public void testContainsKeyObject() {
		CacheManager cmTmp = CacheManager.getInstance();
		Cache cacheTmp = cmTmp.getCache(this.getName());
		cacheTmp.put("key", "value");
		assertTrue ( cacheTmp.containsKey("key") );
		cacheTmp.remove("key");
	}

	public void testContainsValueObject() {
		CacheManager cmTmp = CacheManager.getInstance();
		Cache cacheTmp = cmTmp.getCache(this.getName());
		cacheTmp.put("key", "value");
		assertTrue ( cacheTmp.containsValue("value") );
		cacheTmp.remove("key");
	}

	public void testEntrySet() {
		CacheManager cmTmp = CacheManager.getInstance();
		Cache cacheTmp = cmTmp.getCache(this.getName());
		cacheTmp.put("key", "value");
		Set setTmp = cacheTmp.entrySet();
		assertEquals (1, setTmp.size());
		setTmp.clear();
		assertTrue ( setTmp.isEmpty());
		cacheTmp.remove("key");
		
		
		 
	}

	public void testEqualsObject() {
		CacheManager cmTmp = CacheManager.getInstance();
		 Cache cacheTmp = cmTmp.getCache(this.getName());
		 cacheTmp.put("key", "value");
		 assertEquals(cacheTmp.containsKey("key"),cacheTmp.containsValue("value"));
		 cacheTmp.remove("key");
	}

	public void testKeySet() {
		CacheManager cmTmp = CacheManager.getInstance();
		Cache cacheTmp = cmTmp.getCache(this.getName());
		cacheTmp.put("key", "value");
		Set setTmp = cacheTmp.keySet();
		assertEquals (1, setTmp.size());
		cacheTmp.remove("key");
		
	}

	public void testPutAllMap() {
		CacheManager cmTmp = CacheManager.getInstance();
		Cache cacheTmp = cmTmp.getCache(this.getName());
		Map mapTmp = new TreeMap();
		mapTmp .put("k1", "v1");
		mapTmp .put("k2", "v2");
		cacheTmp.putAll(mapTmp);
		
		assertEquals (2, mapTmp.size());
		mapTmp.clear();
		assertTrue (mapTmp.isEmpty());
		
		cacheTmp.clear();
		assertTrue (cacheTmp.isEmpty());
		
	}

	public void testValues() {
		CacheManager cmTmp = CacheManager.getInstance();
		Cache cacheTmp = cmTmp.getCache(this.getName());
		cacheTmp.put("key", "value");
		assertNotNull (cacheTmp.values());
		cacheTmp.remove("key");
	}

	public void testGetObject() {
		CacheManager cmTmp = CacheManager.getInstance();
		Cache cacheTmp = cmTmp.getCache(this.getName());
		cacheTmp.put("key", "value");
		assertEquals("value",  cacheTmp.get("key") );  
		cacheTmp.remove("key");
	}

	public void testGetAll() throws CacheException {
		CacheManager cmTmp = CacheManager.getInstance();
		Cache cacheTmp = cmTmp.getCache(this.getName());
		
		Map keysMap = new TreeMap();
		keysMap.put("k1", "v1");
		keysMap.put("k2", "v2");
		assertEquals (2, keysMap.size());
		
		Collection keysTmp = keysMap.keySet();
		assertTrue (!keysTmp.isEmpty());
		cacheTmp.putAll(keysMap);
		
		Map gaTmp = cacheTmp.getAll(keysTmp);
		assertEquals (2, gaTmp.size());
		
	}
    /**
     * The load method provides a means to "pre load" the cache. This method
     * will, asynchronously, load the specified object into the cache using
     * the associated cacheloader. If the object already exists in the cache,
     * no action is taken. If no loader is associated with the object, no object
     * will be loaded into the cache.  If a problem is encountered during the
     * retrieving or loading of the object, an exception should
     * be logged.
     * If the "arg" argument is set, the arg object will be passed to the
     * CacheLoader.load method.  The cache will not dereference the object. If
     * no "arg" value is provided a null will be passed to the load method.
     * The storing of null values in the cache is permitted, however, the get
     * method will not distinguish returning a null stored in the cache and not
     * finding the object in the cache. In both cases a null is returned.
     * @throws CacheException 
     */
	public void testLoad() throws CacheException {
		CacheManager cmTmp = CacheManager.getInstance();
		Cache cacheTmp = cmTmp.getCache(this.getName());
		cacheTmp.put("key", "value");
		cacheTmp.put("key2", "value2");
		assertEquals (2, cacheTmp.size() );
		cacheTmp.load("key");
		assertEquals (2, cacheTmp.size());
		cacheTmp.clear();

	}

	public void testLoadAll() throws CacheException {
		CacheManager cmTmp = CacheManager.getInstance();
		Cache cacheTmp = cmTmp.getCache(this.getName());
		cacheTmp.put("key", "value");
		cacheTmp.put("key2", "value2");
		assertEquals (2, cacheTmp.size() );
		
		Collection<Object> existingKeys = new ArrayList<Object>();
		existingKeys.add("key");
		existingKeys.add("key2");
		cacheTmp.loadAll(existingKeys);
		Collection<Object> notExistingKeys = new ArrayList<Object>();
		notExistingKeys.add("key21");
		notExistingKeys.add("key23");
		cacheTmp.loadAll(notExistingKeys);
		Collection<Object> partiallyExistingKeys = new ArrayList<Object>();
		partiallyExistingKeys.add("key2");
		partiallyExistingKeys.add("key23");
		cacheTmp.loadAll(partiallyExistingKeys);
	}

	public void testPeek() {
		// TODO - check the diff with get(xxx)
		testGetObject();
	
		
	}

	public void testPutObjectObject() {
		CacheManager cmTmp = CacheManager.getInstance();
		Cache cacheTmp = cmTmp.getCache(this.getName());
		cacheTmp.put("key", "value");
		cacheTmp.put("key2", "value2");
		assertEquals (2, cacheTmp.size() );
		cacheTmp.remove("key");
		cacheTmp.remove("key2");
		
	}

	public void _testGetCacheEntry() {
		CacheManager cmTmp = CacheManager.getInstance();
		Cache cacheTmp = cmTmp.getCache(this.getName());
		cacheTmp.put("key", "value");
		cacheTmp.put("key2", "value2");
		assertEquals (2, cacheTmp.size() );
		assertTrue(cacheTmp.getCacheEntry("key")  instanceof CacheEntry ) ;
		
		cacheTmp.remove("key");
		cacheTmp.remove("key2");
		assertEquals (0, cacheTmp.size());
  	}

		
	public void _testGetCacheStatistics() {
		CacheManager cmTmp = CacheManager.getInstance();
		// any test will use its own cache-instance with name-of-test
		Cache cacheTmp = cmTmp.getCache(this.getName());
		cacheTmp.put("key", "value");
		cacheTmp.put("key2", "value2");
		assertEquals (2, cacheTmp.size() );
		assertTrue(cacheTmp.getCacheStatistics().getCacheHits() == 0 ) ;
		// hit to cache
		cacheTmp.get("key");
		assertTrue(cacheTmp.getCacheStatistics().getCacheHits() == 1 ) ;
		// missto cache
		cacheTmp.peek("key3");
		assertTrue(cacheTmp.getCacheStatistics().getCacheHits() == 1 ) ;
		assertTrue(cacheTmp.getCacheStatistics().getCacheMisses() == 1 ) ;
		
		// hit to another
		cacheTmp.get("key2");
		assertTrue(cacheTmp.getCacheStatistics().getCacheHits() == 2 ) ;
		assertTrue(cacheTmp.getCacheStatistics().getCacheMisses() == 1 ) ;
		//crear stat
		cacheTmp.getCacheStatistics().clearStatistics();
		assertTrue(cacheTmp.getCacheStatistics().getCacheHits() == 0 ) ;
		assertTrue(cacheTmp.getCacheStatistics().getCacheMisses() == 0 ) ;
		
		
	}

	public void testRemoveObject() {
		CacheManager cmTmp = CacheManager.getInstance();
		Cache cacheTmp = cmTmp.getCache(this.getName());
		cacheTmp.put("key", "value");
		cacheTmp.put("key2", "value2");
		cacheTmp.remove("key");
		cacheTmp.remove("key2");
		assertTrue(cacheTmp.isEmpty());
	}

//	public void testEvict() {
//		 CacheManager cmTmp = CacheManager.getInstance();
//		 Cache cacheTmp = cmTmp.getCache(this.getName());
//		 final int MAX_ITEMS = 22;
//		for(int i=0;i<MAX_ITEMS;i++)cacheTmp.put("key"+i, "value"+i);
//		 // at the default impl capacity willbe adjusted already with put
//		 cacheTmp.evict();
//		 // @see testEvict.properties
//		 // generally the testEvict() is not implementable bikoz of different EvictStrategies
//		 // and should be tested separately for any strategy.
//		 // here only the test for "no-exceptions are throwed"  is possible .
//		 assertEquals(12,cacheTmp.size());
//		 
//		 for(int i=0;i<cacheTmp.size();i++)
//			 assertNotNull("IS NULL :((( "+ (MAX_ITEMS-i-1) ,cacheTmp.get("key"+(MAX_ITEMS-i-1)));
//	}

	public void _testAddListener() throws CacheException {
		 CacheManager cmTmp = CacheManager.getInstance();
		 Cache cacheTmp = cmTmp.getCache(this.getName());
		 final StringBuffer  testVal = new StringBuffer();
		 CacheListener clTmp = new CacheListener(){
			private void log(String s){
				testVal .append( s ).append(" ");
				log.fine(""+testVal);
			}
			public void onClear() {
				log( ON_CLEAR );
			}

			public void onEvict(Object key) {
				log( ON_EVICT+key );
			}

			public void onLoad(Object key) {
				log( ON_LOAD+key );
			}

			public void onPut(Object key) {
				log( ON_PUT+key );
			}

			public void onRemove(Object key) {
				log( ON_REMOVE+key );
			}};
		cacheTmp.addListener(clTmp);
		
		cacheTmp.put("key", "value");
		assertTrue(testVal.indexOf(ON_PUT)>=0);
		
		testVal.setLength(0);
		


		cacheTmp.load("key");
		assertTrue(testVal.indexOf(ON_LOAD)>=0);
		testVal.setLength(0);

		// for evict more the MAX_Size entries shouldbe created
		for (int i=0;i<130;i++)cacheTmp.put("k"+i, "v"+i);
		//cacheTmp.evict();
		assertTrue(testVal.indexOf(ON_EVICT)>=0);
		testVal.setLength(0);

		
		cacheTmp.remove("k129");
		assertTrue(testVal.indexOf(ON_REMOVE)>=0);
		testVal.setLength(0);
		
		cacheTmp.clear();
		assertTrue(testVal.indexOf(ON_CLEAR)>=0);
		testVal.setLength(0);
  
		 
			
	}

	public void _testRemoveListener() throws CacheException {
		CacheManager cmTmp = CacheManager.getInstance();
		 Cache cacheTmp = cmTmp.getCache(this.getName());
		 final StringBuffer  testVal = new StringBuffer();
		 CacheListener clTmp = new CacheListener(){
			private void log(String s){
				testVal .append( s ).append(" ");
				log.fine(""+testVal);
			}
			public void onClear() {
				log( ON_CLEAR );
			}

			public void onEvict(Object key) {
				log( ON_EVICT+key );
			}

			public void onLoad(Object key) {
				log( ON_LOAD+key );
			}

			public void onPut(Object key) {
				log( ON_PUT+key );
			}

			public void onRemove(Object key) {
				log( ON_REMOVE+key );
			}};
		cacheTmp.addListener(clTmp);
		
		cacheTmp.put("key", "value");
		assertTrue(testVal.indexOf(ON_PUT)>=0);
		
		testVal.setLength(0);
		


		cacheTmp.load("key");
		assertTrue(testVal.indexOf(ON_LOAD)>=0);
		testVal.setLength(0);

		// for evict more the MAX_Size entries shouldbe created
		for (int i=0;i<130;i++)cacheTmp.put("k"+i, "v"+i);
		//cacheTmp.evict();
		assertTrue(testVal.indexOf(ON_EVICT)>=0);
		testVal.setLength(0);

		
		cacheTmp.remove("k129");
		assertTrue(testVal.indexOf(ON_REMOVE)>=0);
		testVal.setLength(0);
		
		cacheTmp.clear();
		assertTrue(testVal.indexOf(ON_CLEAR)>=0);
		testVal.setLength(0);
		
		cacheTmp.removeListener(clTmp);
		
		cacheTmp.load("key");
		assertTrue (("".equals(testVal.toString())));
		testVal.setLength(0);
	
		
	}


	
	
	public void testCacheEntry() { 
		Cache cacheTmp = CacheManager.getCache(this.getName());
		cacheTmp.put("key", "value");
		cacheTmp.put("key2", "value2");
		assertEquals (2, cacheTmp.size() );

		CacheEntry entry1 = cacheTmp.getCacheEntry("key");
		entry1.getValue().equals("value");

		CacheEntry entry2 = cacheTmp.getCacheEntry("key2");
		entry2.getValue().equals("value2");
		
		// TODO - this part is still not implemented
		assertEquals (0, entry1.getHits() );
		assertEquals (0, entry1.getCreationTime() );
		assertEquals (0, entry1.getLastAccessTime()  );
		assertEquals (0, entry1.getVersion()   );
		assertEquals (0, entry1.getExpirationTime() );
		
		
		cacheTmp.remove("key");
		cacheTmp.remove("key2");
		assertEquals (0, cacheTmp.size() );
		
	}

	
	public void testCacheStatistics() { 
		Cache cacheTmp = CacheManager.getCache(this.getName());
		cacheTmp.put("key", "value");
		cacheTmp.put("key2", "value2");
		assertEquals (2, cacheTmp.size() );

		CacheStatistics stat1 = cacheTmp.getCacheStatistics();
		assertEquals (2, stat1.getObjectCount() );
		
		assertEquals (0, stat1.getCacheHits() );
		assertEquals (0, stat1.getCacheMisses());
		cacheTmp.get("key");
		cacheTmp.get("keyssss");
		assertEquals (1, stat1.getCacheHits() );
		assertEquals (1, stat1.getCacheMisses() );
 		
		cacheTmp.remove("key");
		cacheTmp.remove("key2");
		assertEquals (0, cacheTmp.size() );
		assertEquals (0, stat1.getObjectCount() );
		
	}	
	
	
	public void testListener() { 
		Cache cacheTmp = CacheManager.getCache(this.getName());
		
		CacheListener listener = new CacheListener(){
			private int load;
			private int put;
			private int evict;
			private int remove;
			private int clear;
			public String toString(){
				return 
				" load="+load+
				" put="+put+
				" evict="+evict+
				" remove="+remove+
				" clear="+clear 
				;
			}
			
			public void onLoad(Object key) { 
				load++;
			}

			public void onPut(Object key) {
				put++;
			}

			public void onEvict(Object key) {
				evict++;
			}

			public void onRemove(Object key) {
				remove++;
			}

			public void onClear() {
				clear++;
			}};
		cacheTmp.addListener(listener );
		cacheTmp.put("key", "value");
		cacheTmp.put("key2", "value2");
		assertEquals (" load=0 put=2 evict=0 remove=0 clear=0", listener.toString() );

		cacheTmp.get("key");
		cacheTmp.get("keyssss");
		assertEquals (" load=2 put=2 evict=0 remove=0 clear=0", listener.toString() );

		cacheTmp.evict();
		assertEquals (" load=2 put=2 evict=1 remove=0 clear=0", listener.toString() );
		
		cacheTmp.remove("key2");
		assertEquals (" load=3 put=2 evict=1 remove=1 clear=0", listener.toString() );		
		assertEquals (1, cacheTmp.size() );
		
		cacheTmp.clear();
		assertEquals (0, cacheTmp.size() );
		assertEquals (" load=4 put=2 evict=1 remove=2 clear=1", listener.toString() );
		 
		
	}	
}


 