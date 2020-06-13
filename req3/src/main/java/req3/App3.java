package req3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import pl.edu.icm.cermine.ContentExtractor;
import pl.edu.icm.cermine.exception.AnalysisException;
import pl.edu.icm.cermine.tools.timeout.TimeoutException;

public class App3 {

	private ContentExtractor extractor;
	private InputStream inputStream;
	private Element result;
	private XMLOutputter outp;
	private List<String> path_names;
	private List<String> file_names;
	private List<ArrayList<Elements>> metadata;
	private String html_start = "<!DOCTYPE html><html><head><style>table {font-family: arial, sans-serif;border-collapse: collapse;width: 100%;}td, th {border: 1px solid #dddddd;text-align: left;padding: 8px;}tr:nth-child(even) {background-color: #dddddd;}</style></head><body><h2>COVID PDF TABLE</h2><table><tr><th>Article title</th><th>Journal name</th><th>Publication year</th><th>Authors</th></tr>";
	private String html_final = "</table></body></html>";
	private String html;

	private String folder_path = "Covid";
	private String wordpress_url = "http://localhost/Covid";

	private List<String> meta;

	private HashMap<String, String> pdf_meta;

	/**
	 * Constructor for the app
	 * 
	 */
	public App3() {
		path_names = new ArrayList<String>();
		file_names = new ArrayList<String>();
		metadata = new ArrayList<ArrayList<Elements>>();
		pdf_meta = new HashMap<String, String>();
		meta = new ArrayList<String>();
	}

	public List<String> getMeta() {
		return meta;
	}

	public HashMap<String, String> getPdf_meta() {
		return pdf_meta;
	}

	/**
	 * Getter of the folder path where the pdf files are
	 * 
	 * @return folder path
	 */
	public String getFolder_path() {
		return folder_path;
	}

	/**
	 * Setter for the folder_path field
	 * 
	 * @param fp folder_path
	 */
	public void setFolder_path(String fp) {
		folder_path = fp;
	}

	/**
	 * Getter of the names of the pdf files related to Covid
	 * 
	 * @return file names
	 */
	public List<String> getFile_names() {
		return file_names;
	}

	/**
	 * Getter of the pdf files paths
	 * 
	 * @return List of Strings pdf file paths
	 */
	public List<String> getPath_names() {
		return path_names;
	}

	/**
	 * Getter of the pdf files metadata
	 * 
	 * @return List inside a List containing metadata of pdf files
	 */
	public List<ArrayList<Elements>> getMetadata() {
		return metadata;
	}

	/**
	 * Getter html String
	 * 
	 * @return string containing the html
	 */
	public String getHtml() {
		return html;
	}

	/**
	 * Getter of the first part of the html string
	 * 
	 * @return start of the html string
	 */
	public String getHtml_start() {
		return html_start;
	}

	/**
	 * Setter for the html_start field
	 * 
	 * @param html_start value
	 */
	public void setHtml_start(String html_start) {
		this.html_start = html_start;
	}

	/**
	 * Getter of the last part of the html string
	 * 
	 * @return end of html string
	 */
	public String getHtml_final() {
		return html_final;
	}

	/**
	 * Setter of the final part of the html string
	 * 
	 * @param html_final value
	 */
	public void setHtml_final(String html_final) {
		this.html_final = html_final;
	}

	/**
	 * Getter of path where pdf files were downloaded from
	 * 
	 * @return iscte folder path
	 */
	public String getWordpressurl() {
		return wordpress_url;
	}

	/**
	 * Setter for the wordpress url field
	 * 
	 * @param url wordpress url
	 */
	public void setWordpressurl(String url) {
		wordpress_url = url;
	}

	/**
	 * Method start that runs the whole app
	 * Populate a list of strings with the pdf path names and file names
	 * Execute methods to get metadata and write the html string
	 * Create txt file empty, after writeMetadata and writeHtml methods put information on the txt file
	 * Populate a hash map if there's already a txt file
	 * @throws IOException expected to throw IOException in case something goes wrong
	 * @throws AnalysisException expected to throw AnalysisException in case something goes wrong
	 * @throws TimeoutException  expected to throw TimeoutException in case something goes wrong
	 * 
	 */

	public void start() throws IOException, TimeoutException, AnalysisException {

		File folder2 = new File(folder_path); //folder where pdf files are

		File pdfN = new File("pdfs.txt"); //txt to save metadata associated with pdf name

		File[] listOfFiles = folder2.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) { //populate path_names and file_names from the shared folder
			if (listOfFiles[i].isFile()) {
				String path = folder_path + "\\" + listOfFiles[i].getName();
				path_names.add(path);
				file_names.add(listOfFiles[i].getName());
			}
		}

		if (pdfN.createNewFile() || pdfN.length() == 0) { // if txt file doesnt exist or it exists but its empty

			writeMetadata(); // execute method to get metadata
			writeHtml(); // method to write the html string

			for (int i = 0; i < file_names.size(); i++) { // put info of metadata and pdf name in a map
				pdf_meta.put(file_names.get(i), meta.get(i));
			}

			//Write file, remove all \n and put ":::" as a way to separate pdf name from metadata, get info from the map
			List<String> line = new ArrayList<String>();
			BufferedWriter w = new BufferedWriter(new FileWriter(pdfN));
			for (Map.Entry<String, String> entry : pdf_meta.entrySet()) {
				String value = entry.getValue();
				value = value.replaceAll("\\n", "");
				String lll = entry.getKey() + ":::" + value + "\n";
				line.add(lll);
			}
			for (String s : line) {
				w.write(s);
			}
			w.close();

		} else { // in case there's already a txt file, read the file, put the information on the map
			// get the file names that are on the map and remove them from the following lists: path_names, file_names, so it doesnt check the metadata again
			BufferedReader reader = new BufferedReader(new FileReader("pdfs.txt"));
			String line = reader.readLine();
			while (line != null) {
				String aux = folder_path + "\\" + line.split(":::")[0];
				if (path_names.contains(aux)) {
					pdf_meta.put(line.split(":::")[0], line.split(":::")[1]);
					path_names.remove(aux);
					file_names.remove(line.split(":::")[0]);

				}
				line = reader.readLine();
			}
			reader.close();
			writeMetadata();
			writeHtml();

		}

	}

	/**
	 * Method used to get the metadata of the pdf files, specified before with the paths
	 * Gets the article-title, journal-name publication-year and the authors 
	 * Adds the metadata per pdf file in a list inside a list (of Elements, jsoup)
	 * 
	 * @throws TimeoutException expected to throw TimeoutException in case something goes wrong
	 * @throws AnalysisException expected to throw AnalysisException in case something goes wrong
	 * @throws IOException expected to throw IOException in case something goes wrong
	 */
	public void writeMetadata() throws TimeoutException, AnalysisException, IOException {

		if (!path_names.isEmpty()) { // execute this method only if path_names list is not empty which means there's at least one pdf file that's not on the txt file
			for (String path : path_names) {
				inputStream = new FileInputStream(path);
				extractor = new ContentExtractor();
				outp = new XMLOutputter();
				extractor.setPDF(inputStream);
				result = extractor.getMetadataAsNLM();
				String s = outp.outputString(result);

				ArrayList<Elements> aux = new ArrayList<Elements>();

				Document doc = Jsoup.parse(s);

				Elements article_title = doc.getElementsByTag("article-title");
				Elements journal_name = doc.getElementsByTag("journal-title");
				Elements pub_year = doc.getElementsByTag("pub-date");
				Elements authors = doc.getElementsByTag("string-name");

				authors.append("<b>;</b>");

				aux.add(article_title);
				aux.add(journal_name);
				aux.add(pub_year);
				aux.add(authors);

				metadata.add(aux); //list of Arrays, each Array has the 4 Elements needed for each "String" on the path_names list

				inputStream.close();

			}
		}

	}

	/**
	 * Method used to create and write the HTML file containing a table with the
	 * following columns: Article title, Journal name, Publication year and Authors
	 * 
	 * @throws IOException expected to throw IOException in case something goes wrong
	 */
	public void writeHtml() throws IOException {

		//File f = new File("table.html");
		//BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		html = html_start; //start of the html string, includes head style etc

		if (path_names.isEmpty()) { // this list being empty means there's no new pdf file and either there are no pdf files or the info(name and metadata) is on the txt file
			for (String value : pdf_meta.values()) { // pdf_meta is the hash map, value - metadata with table format
				html += value;
			}
		} else {
			File pdfN = new File("pdfs.txt");
			if (pdfN.exists() && pdfN.length() != 0) {
				for (String value : pdf_meta.values()) { // get metadata from hash map and put on html string
					html += value;
				}
				for (int i = 0; i < metadata.size(); i++) {
					String s = new String();
					s = file_names.get(i) + ":::";
					s = auxWriteHtml(s, i);
					s = s.replaceAll("\\n", "");
					s += "</tr>" + "\n";
					meta.add(s); // aux list to put the whole "html" of 1 pdf file
					FileWriter fw = new FileWriter(pdfN, true); //append previous string to the txt file
					fw.write(s);
					fw.close();
				}

			} else {

				for (int i = 0; i < metadata.size(); i++) {
					String s = new String();
					s = auxWriteHtml(s, i);
					s += "</tr>";
					meta.add(s); //aux list to put whole "html" of 1 pdf file, used above in start method after this method is called

				}

			}

		}
		html += html_final;
		//bw.write(html);
		System.out.println(html);
		//bw.close();

	}

	/**
	 * Method to write parts of the html table
	 * @param s String received 
	 * @param i int that comes from a loop
	 * @return s - String received that was modified
	 */
	public String auxWriteHtml(String s, int i) {
		s += "<tr>";
		html += "<tr>";
		for (int j = 0; j < metadata.get(i).size(); j++) {
			if (j == 0) {
				s += "<td>" + "<a href=" + wordpress_url + "/" + file_names.get(i) + ">" + metadata.get(i).get(j)
						+ "</a></td>";
				html += "<td>" + "<a href=" + wordpress_url + "/" + file_names.get(i) + ">"
						+ metadata.get(i).get(j) + "</a></td>"; // URL to computer path
			} else {
				s += "<td>" + metadata.get(i).get(j) + "</td>";
				html += "<td>" + metadata.get(i).get(j) + "</td>";
			}
		}
		html += "</tr>";
		return s;
	}


	/**
	 * Starts the app that generates an HTML file to the req3 location
	 * 
	 * @param args 0 - folder_path 
	 * @param args 1 - wordpressurl
	 * @throws AnalysisException expected to throw AnalysisException in case something goes wrong
	 * @throws TimeoutException  expected to throw TimeoutException in case something goes wrong
	 * @throws IOException IOException expected to throw IOException in case something goes wrong
	 */
	public static void main(String[] args) throws AnalysisException, TimeoutException, IOException {
		System.out.println("Content-type: text/html\n\n");
		App3 app3 = new App3();
		app3.setFolder_path(args[0]);
		app3.setWordpressurl(args[1]);

		app3.start();

	}
}
