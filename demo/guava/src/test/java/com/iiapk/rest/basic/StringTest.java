package com.iiapk.rest.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;

public class StringTest {
	
	private List<String> list = new ArrayList<String>();
	private Map<String,String> testMap = new HashMap<String,String>();
	
	@Before
	public void setup(){
		list.add("one");
		list.add("two");
		list.add(null);
		list.add("");
		list.add("three");
		list.add("four");
		list.add("five");
		testMap.put("Washington D.C","Redskins");
		testMap.put("New York City","Giants");
		testMap.put("Philadelphia","Eagles");
		testMap.put("Dallas","Cowboys");
	}
	
	@Test
	public void joinerList(){
		String result = Joiner.on("|").skipNulls().join(list);
		Assert.assertEquals(result,"one|two||three|four|five");
	}
	
	@Test
	public void joinerMap(){
		String result = Joiner.on("#").withKeyValueSeparator("=").join(testMap);
		Assert.assertEquals(result,"New York City=Giants#Philadelphia=Eagles#Dallas=Cowboys#Washington D.C=Redskins");
	}
	
	@Test
	public void splitter(){
		String startString = "Washington D.C=Redskins#New York City=Giants#Philadelphia=Eagles#Dallas=Cowboys";
		Splitter.MapSplitter mapSplitter =Splitter.on("#").withKeyValueSeparator("=");
		Map<String,String> splitMap =mapSplitter.split(startString);
		Assert.assertEquals(testMap,splitMap);
	}
	
	@Test
	public void teststring(){
		"foobarbaz".getBytes(Charsets.UTF_8);
		Assert.assertEquals(Strings.padEnd("foo",6,'x'),"fooxxx");
		Assert.assertEquals(Strings.nullToEmpty(null),"");
		Assert.assertEquals(Strings.emptyToNull(""),null);
		Assert.assertTrue(Strings.isNullOrEmpty(""));
		Assert.assertTrue(Strings.isNullOrEmpty(null));
	}
	
	@Test
	public void testRemoveWhiteSpace(){
		String tabsAndSpaces = "\tString	with	spaces	\nand\ttabs";
		String scrubbed = CharMatcher.WHITESPACE.collapseFrom(tabsAndSpaces,' ');
		Assert.assertEquals(scrubbed," String with spaces and tabs");
		String scrubbed2 = CharMatcher.WHITESPACE.trimAndCollapseFrom(tabsAndSpaces,' ');
		Assert.assertEquals(scrubbed2,"String with spaces and tabs");
		
	}
	
	@Test
	public void testRetainFrom(){
		String lettersAndNumbers = "foo989yxbar234";
		String expected = "989234";
		String retained = CharMatcher.JAVA_DIGIT.retainFrom(lettersAndNumbers);
		Assert.assertEquals(retained,expected);
		//System.out.println(retained);
	}
	
	@Test
	public void preconditions(){
		Preconditions.checkNotNull("","someObj must not be null");
		Preconditions.checkArgument(100 <= 100,"Value can't be more than 100");
		Preconditions.checkState(true,"Can't perform operation");
		int[] values = new int[5];
		Preconditions.checkElementIndex(1, values.length,"IndexOutOfBoundsException");
	}
	
	@Test
	public void testobject(){
		Assert.assertEquals(Objects.firstNonNull(null, "ddd"),"ddd");
	}
	
	@Test
	public void optional(){
		Optional<List> list = null;
		Optional possible = Optional.of(list);
		Assert.assertSame(possible.get(),5);
		Assert.assertTrue(possible.isPresent());
		
		Object obj=5;
		Optional<Object> possible1 = Optional.of(obj);
		obj=null;
		Assert.assertSame(possible1.get(),5);
		Assert.assertTrue(possible1.isPresent());
	}
	
	@After
	public void setdown(){
		list.clear();
		testMap.clear();
	}
}
