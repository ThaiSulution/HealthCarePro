package docbaoonline.models;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class RssParser {

	public List<RssItem> parser(String link) {
		if (link.contains("www.24h.com.vn")) {
			try {
				URL url = new URL(link);
				InputSource input = new InputSource(url.openStream());
				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser parser = factory.newSAXParser();
				XMLReader reader = parser.getXMLReader();

				RssHandler handler = new RssHandler();
				reader.setContentHandler(handler);
				reader.parse(input);

				return handler.getItems();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			try {
				// URL url= new URL(link);
				DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpGet httpget = new HttpGet(link);
				HttpResponse response = httpclient.execute(httpget);
				HttpEntity entity = response.getEntity();
				SAXParserFactory factory = SAXParserFactory.newInstance();
				factory.setValidating(false);
				SAXParser parser = factory.newSAXParser();
				XMLReader reader = parser.getXMLReader();
				RssHandler handler = new RssHandler();
				reader.setContentHandler(handler);
				// InputSource source= new InputSource(url.openStream());
				// reader.parse(source);
				InputSource inStream = new InputSource();
				inStream.setCharacterStream(new StringReader(EntityUtils
						.toString(entity)));
				reader.parse(inStream);
				return handler.getItems();
			} catch (MalformedURLException e) {
				e.printStackTrace();
				// result.setText("Cannot connect RSS!");
				return null;
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
				// result.setText("Cannot connect RSS!");
				return null;
			} catch (SAXException e) {
				e.printStackTrace();
				// result.setText("Cannot connect RSS!");
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				// result.setText("Cannot connect RSS!");
				return null;
			}
		}
	}
}
