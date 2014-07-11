package com.iiapk.rest.xml;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

public class XStreamTest {
	
	private XStream xstream = null;
    private ObjectOutputStream  out = null;
    private ObjectInputStream in = null;
    private Book book = null;
    private static String TEST_XML_FILE = "src/main/resources/bookxml.xml";
    private static String CCB_XML_FILE = "src/main/resources/ccb.xml";
	
	@Before
    public void init() {
        try {
            xstream = new XStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        book = new Book();
        book.setTitle("china");
        book.setAuthor("jack@email.com");
        book.setYear("1");
        book.setPrice("jack");
        book.setBookmark(new Bookmark("test1", "test2"));
    }
	
	@After
    public void destory() {
        xstream = null;
        book = null;
        try {
            if (out != null) {
                out.flush();
                out.close();
            }
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.gc();
    }
	
	//@Test
	public void writeBean2XML() {
	    try {
	        //包重命名
	        xstream.aliasPackage("book", "com.iiapk.rest.xml");
	        //属性重命名
	        xstream.aliasField("amount", Book.class, "price");
	        xstream.aliasField("mark", Bookmark.class, "title");
	        //类重命名
	        xstream.alias("book", Book.class);
	        System.out.println(xstream.toXML(book));
	        System.out.println(xstream.fromXML(xstream.toXML(book)));
	        System.out.println(xstream.fromXML(FileUtils.readFileToString(new File(TEST_XML_FILE))));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	//@Test
	public void writeList2XML() {
		xstream.alias("bookList", BookList.class);
		xstream.alias("book", Book.class);
		xstream.setMode(XStream.NO_REFERENCES);
		//xstream.processAnnotations(Classes.class);
        //启用Annotation
        //xstream.autodetectAnnotations(true);
		System.out.println(xstream.toXML(BookList.getList()));
		System.out.println(xstream.fromXML(xstream.toXML(BookList.getList())));
	}
	
	//@Test
	public void writeEntity2JETTSON() {
	    xstream = new XStream(new JettisonMappedXmlDriver());
	    xstream.setMode(XStream.NO_REFERENCES);
	    xstream.autodetectAnnotations(true);
	    //xstream.alias("book", Book.class);
	    //xstream.aliasField("amount", Book.class, "price");
        //xstream.aliasField("mark", Bookmark.class, "title");
	    System.out.println(xstream.toXML(book));
	}
	
	//@Test
	public void writeList2JETTSON() {
		//xstream = new XStream(new JettisonMappedXmlDriver());
		//删除根节点
	    xstream = new XStream(new JsonHierarchicalStreamDriver() {
	        public HierarchicalStreamWriter createWriter(Writer out) {
	            return new JsonWriter(out, JsonWriter.DROP_ROOT_MODE);
	        }
	    });
		//xstream.alias("bookList", BookList.class);
		//xstream.alias("book", Book.class);
		//xstream.setMode(XStream.NO_REFERENCES);
		//xstream.processAnnotations(Classes.class);
        //启用Annotation
        xstream.autodetectAnnotations(true);
		System.out.println(xstream.toXML(BookList.getList()));
		System.out.println(xstream.fromXML(xstream.toXML(BookList.getList())));
	}
	
	@Test
	public void xml2Bean() {
		xstream.processAnnotations(new Class<?>[] {CcbRefundResult.class,CcbRefundResultInfo.class});
		try {
			System.out.println(xstream.fromXML(FileUtils.readFileToString(new File(CCB_XML_FILE))));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
