package com.iiapk.module.xml;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlTest {

	Document document;
	XMLEventReader reader;
	Map<String, String> map = new HashMap<String, String>();

	public XmlTest(File file) {
		init(file);
	}

	public void init(File file) {
		try {
			document = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(file);
			reader = XMLInputFactory.newInstance().createXMLEventReader(
					new FileInputStream(file));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}

	public Map<String, String> parseByw3cdom() {
		NodeList list = document.getElementsByTagName("expr");
		for (int i = 0; i < list.getLength(); i++) {
			Element element = (Element) list.item(i);
			if (element.getAttribute("oper").equals("equal")) {
				Element keyNode = (Element) element.getElementsByTagName("leftnode").item(0);
				Element valueNode = (Element) element.getElementsByTagName("righnode").item(0);
				map.put(keyNode.getAttribute("value"),valueNode.getAttribute("value"));
			}
		}
		return map;
	}

	public Map<String, String> parseByStax() {
		String key = null;
		try {
			while (reader.hasNext()) {
				XMLEvent e = reader.nextEvent();
				if (e.isStartElement()&& e.asStartElement().getName().getLocalPart().equals("leftnode")) {
					Attribute attribute1 = e.asStartElement().getAttributeByName(new QName("type"));
					Attribute attribute2 = e.asStartElement().getAttributeByName(new QName("value"));
					if (attribute1 != null&& attribute1.getValue().equals("var")) {
						key = attribute2.getValue();
					}
				}
				if (e.isStartElement()&& e.asStartElement().getName().getLocalPart().equals("righnode")) {
					Attribute attribute1 = e.asStartElement().getAttributeByName(new QName("type"));
					Attribute attribute2 = e.asStartElement().getAttributeByName(new QName("value"));
					if (attribute1 != null&& attribute1.getValue().equals("const")) {
						System.out.println(attribute2.getValue());
						map.put(key, attribute2.getValue());
					}
				}
			}
		} catch (XMLStreamException e) {
		}
		return map;
	}

	public Map<String, String> parseByXpath() {
		String xpathExp="//expr[oper='equal']";;
		XPath xpath = XPathFactory.newInstance().newXPath();
		try {
			NodeList nodes = (NodeList) xpath.evaluate(xpathExp, document,XPathConstants.NODESET);
			System.out.println("size=" + nodes.getLength());
			for (int i = 0, len = nodes.getLength(); i < len; i++) {
				Node node = nodes.item(i);
				String value = node.getTextContent();
				System.out.println(node.getAttributes().item(0).toString());
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return map;
	}

	public static void main(String[] args) {
		System.out.println(new XmlTest(new File("test.xml")).parseByStax());
	}
}
