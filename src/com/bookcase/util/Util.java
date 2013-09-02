package com.bookcase.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.bookcase.model.Book;

public class Util {
	public static Collection<Book> obtainBooksFromXml(String xml)
			throws SAXException, IOException, ParserConfigurationException {
		Collection<Book> books = new Vector<Book>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(new InputSource(new StringReader(xml)));

		Node bookListNode = doc.getDocumentElement().getFirstChild();
		NodeList booksNodeList = bookListNode.getChildNodes();
		if (booksNodeList.getLength() > 0) {
			for (int i = 0; i < booksNodeList.getLength(); i++) {
				Book book = createBook(booksNodeList.item(i));
				books.add(book);
			}
		}
		return books;
	}

	private static Book createBook(Node bookNode) {
		Book book = new Book();
		book.setIsbn(bookNode.getAttributes().getNamedItem("isbn")
				.getNodeValue());
		NodeList bookDetails = bookNode.getChildNodes();
		for (int j = 0; j < bookDetails.getLength(); j++) {
			Node detail = bookDetails.item(j);
			if (detail.getNodeName().equalsIgnoreCase("Title")) {
				book.setTitle(detail.getFirstChild().getNodeValue());
			} else if (detail.getNodeName().equalsIgnoreCase("AuthorsText")) {
				if (detail.hasChildNodes()) {
					String author = detail.getFirstChild().getNodeValue();
					book.setAuthor(author);
				}
			}
		}
		return book;
	}
}
