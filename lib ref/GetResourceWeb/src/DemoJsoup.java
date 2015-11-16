import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DemoJsoup {

	public static void main(String[] args) throws IOException {
		// File htmlFile = new File("Accupril.html");
		String[] linkPages = new String[15];
		int page = 1;
		int count = 0;
		for (int i = 0; i < 8; i++) {
			linkPages[i] = "http://www.thuocbietduoc.com.vn/defaults/drgsearch.aspx?start="
					+ String.valueOf(page);
			page = page + 20;
			Document doc = Jsoup.connect(linkPages[i]).get();
			Elements herf = doc.select("a.textlink01_v");
			ArrayList<String> links = new ArrayList();
			for (int j = 0; j < herf.size(); j = j+ 2) {
				String linkDrg = herf.get(j).attr("href");
				linkDrg = linkDrg.substring(2);
				linkDrg = "http://www.thuocbietduoc.com.vn" + linkDrg;
				System.out.println(linkDrg);
				links.add(linkDrg);
				Document docChild = Jsoup.connect(links.get(j/2)).get();
				String DrgName = docChild.select("h1.drugtitle").text();
				System.out.println(DrgName);
				count++;
				System.out.println(count);
				System.out.println(i);
			}
		}
		
		for (int i = 8; i < 15; i++) {
			linkPages[i] = "http://www.thuocbietduoc.com.vn/defaults/drgsearch.aspx?start="
					+ String.valueOf(page);
			page = page + 20;
			Document doc = Jsoup.connect(linkPages[i]).get();
			Elements herf = doc.select("a.textlink01_v");
			ArrayList<String> links = new ArrayList();
			for (int j = 0; j < herf.size(); j = j+ 2) {
				String linkDrg = herf.get(j).attr("href");
				linkDrg = linkDrg.substring(2);
				linkDrg = "http://www.thuocbietduoc.com.vn" + linkDrg;
				System.out.println(linkDrg);
				links.add(linkDrg);
				Document docChild = Jsoup.connect(links.get(j/2)).get();
				String DrgName = docChild.select("h1.drugtitle").text();
				System.out.println(DrgName);
				count++;
				System.out.println(count);
				System.out.println(i);
			}
		}

		// Element e = doc.select("div.neo-center-drug").first();
		
		
		// Iterator<Element> title = doc.select("td.tbldrg_dt3").iterator();
		// while ((e.hasNext())){// &&(title.hasNext()) ){
		// Element temp1 = e.next();
		// //Element temp2 = title.next();
		// //System.out.println(temp2.text());
		// System.out.println(temp1.text());
		// System.out.println("\n");
		// }

	}

}
