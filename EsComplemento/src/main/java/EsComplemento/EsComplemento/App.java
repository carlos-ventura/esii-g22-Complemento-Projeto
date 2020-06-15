package EsComplemento.EsComplemento;

import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Hashtable;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.util.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import EsComplemento.EsComplemento.cgi_lib;

/** 
 * @author Rodrigo Vidigal da Silva
 * 
 */
public class App {

	public static String query = "";
	private static final String URI = "https://github.com/vbasto-iscte/ESII1920.git";
	public static final String file = "covid19spreading.rdf";
	/** Strings to help creating rdf file and query string the one that will be concatenated with some methods that concatenate some strings to it and then it is used in xPath to generate querys
	 * @param args Are the arguments passed to the main application.
	 * @throws XPathExpressionException If an XPathExpression exception occurred
	 * @throws InvalidRemoteException If an InvalidRemoteException exception occurred
	 * @throws TransportException If a TransportExceptio exception occurred
	 * @throws GitAPIException If a GitAPIException exception occurred
	 * @throws SAXException If a SAXException exception occurred
	 * @throws IOException If an IOExceptio exception occurred
	 * @throws ParserConfigurationException If a ParserConfigurationException exception occurred
	 */
	public static void main( String[] args ) throws InvalidRemoteException, TransportException, GitAPIException, XPathExpressionException, SAXException, IOException, ParserConfigurationException {

		System.out.println(cgi_lib.Header());
		File localPath = File.createTempFile("JGitTestRepository", "");

		try {
			Files.delete(localPath.toPath());
			Git.cloneRepository().setURI(URI).setDirectory(localPath).call();
		} catch (Exception e) {
			e.printStackTrace();
		}


		File inputFile = new File(localPath+"/"+file);    	      	  
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(inputFile);
		doc.getDocumentElement().normalize();  


		String query = "/RDF/NamedIndividual/@*";
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		XPathExpression expr = xpath.compile(query);         
		NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

		//  Here is a minimalistic CGI program that uses cgi_lib
		//  Print the required CGI header.
		//System.out.println(cgi_lib.Header());
		if(args[0].equals("GET")) {

			System.out.println("<!DOCTYPE html>\r\n" + 
					"<html>\r\n" + 
					"<head>\r\n" + 
					"    <title>COVID QUERY</title>\r\n" + 
					"</head>\r\n" + 
					"<body> \r\n" + 
					"	<h2>COVID QUERY</h2>\r\n" + 
					"     <FORM METHOD=\"POST\" ACTION=\"/cgi-bin/covid-query_post.sh\">\r\n" + 
					"<p>Se quiser pesquisar os valores de cada regiao utilize apenas a query de cima, se deseja obter por regiao utilize a debaixo. A soma apenas funciona para estes dois parametros.</p>"+
					"   <label for=\"regiao\">Regiao a selecionar:</label><br>\r\n" + 
					"       	<select name=\"regiao\" id=\"regiao\"><option value=\"algarve\">Algarve</option><option value=\"alentejo\">Alentejo</option><option value=\"norte\">Norte</option><option value=\"lisboa\">Lisboa</option>\r\n" + 
					"        <option value=\"centro\">Centro</option></select>&nbsp&nbsp<select name=\"criterioalone\" id=\"criterioalone\"><option value=\"infecoes\">Infecoes</option><option value=\"testes\">Testes</option><option value=\"internados\">Internados</option>"+
					"</select>&nbsp&nbsp<input type=\"checkbox\" id=\"alone\" name=\"alone\" value=\"alone\"><label for=\"alones\">Quer obter apenas os valores da regiao selecionada(apenas da que esta em primeiro lugar)?</label>\r\n" + 
					"        <br><br>\r\n"+
					"<select name=\"regiao2\" id=\"regiao2\"><option value=\"algarve\">Algarve</option><option value=\"alentejo\">Alentejo</option><option value=\"norte\">Norte</option><option value=\"lisboa\">Lisboa</option><option value=\"centro\">Centro</option></select>&nbsp&nbsp<select name=\"criterioalone2\" id=\"criterioalone2\"><option value=\"infecoes\">Infecoes</option><option value=\"testes\">Testes</option><option value=\"internados\">Internados</option></select><br><br>" + 
					"<label for=\"criterios\">Criterio a selecionar:</label><br>\r\n" + 
					"\r\n" + 
					"        <select name=\"criterio\" id=\"criterio\"><option value=\"infecoes\">Infecoes</option><option value=\"testes\">Testes</option><option value=\"internados\">Internados</option></select>&nbsp&nbsp\r\n" + 
					"        \r\n" + 
					"        <select name=\"rel\" id=\"rel\"><option value=\"equal\">=</option><option value=\"dif\">!=</option><option value=\"maiequal\">>=</option></option><option value=\"menequal\"><=</option><option value=\"men\"><</option><option value=\"maior\">></option><br></select>&nbsp <input type=\"text\" id=\"num\" name=\"num\" value=\"0\"><br>\r\n" + 
					"        <br>\r\n" + 
					"        \r\n" + 
					"        \r\n" + 
					"  \r\n" + 
					"        \r\n" + 
					"       \r\n" + 
					"        <input type=\"checkbox\" id=\"or\" name=\"or\" value=\"Or\"><label for=\"or\">Or</label>\r\n" + 
					"        <input type=\"checkbox\" id=\"and\" name=\"and\" value=\"And\"><label for=\"and\">And</label><br><br>\r\n" + 
					"        <label for=\"criterio2\">Criterio a selecionar:</label><br>\r\n" + 
					"        \r\n" + 
					"        <select name=\"criterio2\" id=\"criterio2\"><option value=\"infecoes\">Infecoes</option><option value=\"testes\">Testes</option><option value=\"internados\">Internados</option></select>&nbsp&nbsp\r\n" + 
					"        \r\n" + 
					"        <select name=\"rel2\" id=\"rel2\"><option value=\"equal\">=</option><option value=\"dif\">!=</option><option value=\"maiequal\">>=</option></option><option value=\"menequal\"><=</option><option value=\"men\"><</option><option value=\"maior\">></option><br></select>&nbsp <input type=\"text\" id=\"num2\" name=\"num2\" value=\"0\"><br>\r\n" + 
					"        <br>\r\n" + 
					"        <input type=\"checkbox\" id=\"soma\" name=\"soma\" value=\"Soma\"><label for=\"soma\">Soma das duas regioes selecionadas</label>\r\n" + 
					"      \r\n" + 
					"        &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp\r\n" + 
					"       \r\n" + 
					"        <br>\r\n" + 
					"        <br>\r\n" + 
					"         <input type=\"submit\" value=\"Submit\">\r\n" + 
					"        </form>\r\n" + 
					"        </body>\r\n" + 
					"        </html>");
		}

		if(args[0].equals("POST")) {
			System.out.println(cgi_lib.Header());
			//  Parse the form data into a Hashtable.
			Hashtable form_data = cgi_lib.ReadParse(System.in);
			// Create the Top of the returned HTML page
			System.out.println("<p>");

			//  Print the name/value pairs sent from the browser.
			//		 System.out.println(cgi_lib.Variables(form_data));

			principal(form_data,nl,xpath,expr,doc);

			System.out.println(cgi_lib.HtmlBot());
		}

	}
	/** Creates query string so it can be used to create querys and sysout them...
	 * @param fd The hashtable with the information selected by user in the form.
	 * @param nl Nodes representing the XML paths.
	 * @param xpath XPath needed to compile query string.
	 * @param expr is an XPathExpression with the query already compiled.
	 * @param doc RDF document where XPath will search based on the query string.
	 * @throws XPathExpressionException If an XPathExpression exception occurors
	 */
	public static void principal(Hashtable fd,NodeList nl,XPath xpath, XPathExpression expr,Document doc) throws XPathExpressionException {
		if(fd.containsKey("alone")) {
			query="//*[contains(@about,'"+ regiao(fd) +"')]/"+ criterioAlone(fd)  +"/text()";
			expr = xpath.compile(query);
			System.out.println("Estes sao os numeros de "+ criterioAlone(fd)+" na regiao: " + regiao(fd) + " :");
			System.out.println("<p>");
			System.out.println(expr.evaluate(doc, XPathConstants.STRING));
			System.out.println("<p>");
			if(fd.containsKey("soma")) {
				or();
				query = query + "//*[contains(@about,'"+ regiao2(fd) +"')]/"+ criterioAlone2(fd)  +"/text()";
				soma();
				expr = xpath.compile(query);
				System.out.println("Aqui esta a soma dos dois parametros:");
				System.out.println("<p>");
				System.out.println(expr.evaluate(doc, XPathConstants.STRING));
				System.out.println("<p>");
			}

		}else {
			if(fd.containsKey("or")) { 
				inicio();
				bracketOpen();
				criterio(fd);
				relacional(fd);
				numero2(fd);
				bracketClose();
				barra();
				todos();
				or();
				inicio();
				bracketOpen();
				criterio2(fd);
				relacional2(fd);
				numero2(fd);
				bracketClose();
				barra();
				todos();
			}if(fd.containsKey("and")) { 
				inicio();
				bracketOpen();
				criterio(fd);
				relacional(fd);
				numero(fd);		
				and();			
				criterio2(fd);
				relacional2(fd);
				numero2(fd);
				bracketClose();
				barra();
				todos();
				System.out.println(query);
			}else {
				inicio();
				bracketOpen();
				criterio(fd);
				relacional(fd);
				numero(fd);
				bracketClose();
				barra();
				todos();
			}
			System.out.println("Aqui estao os resultados da sua pesquisa:");

			expr = xpath.compile(query);
			nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < nl.getLength(); i++) {
				System.out.println("<p>");
				System.out.println(StringUtils.substringAfter(nl.item(i).getNodeValue(), "#"));
				System.out.println("<p>");

			}
		}
	}
	/** adds to query string the number found on the form...
	 * @param fd The hashtable with the information selected by user in the form.
	 */
	public static void numero(Hashtable fd) {
		query = query + String.valueOf(fd.get("num"));
	}
	public static void numero2(Hashtable fd) {
		query = query + String.valueOf(fd.get("num2"));
	}

	/** adds to query string the first criteria found on the form...
	 * @param fd The hashtable with the information selected by user in the form.
	 */
	public static String criterioAlone2(Hashtable fd) {
		switch((String) fd.get("criterioalone2")) {
		case "infecoes": 
			return "Infecoes";
		case "testes":
			return "Testes";
		case "internados":
			return "Internados";
		}
		return null;
	}
	/** Gets the string in the select area of the form.
	 * @return String representing the criteria selected in the form.
	 */

	/** adds to query string the first criteria found on the form...
	 * @param fd The hashtable with the information selected by user in the form.
	 */
	public static String criterioAlone(Hashtable fd) {
		switch((String) fd.get("criterioalone")) {
		case "infecoes": 
			return "Infecoes";
		case "testes":
			return "Testes";
		case "internados":
			return "Internados";
		}
		return null;
	}
	/** Gets the string in the select area of the form.
	 * @return String representing the criteria selected in the form.
	 */

	/** adds to query string the criteria found on the form where you can use relational operations with...
	 * @param fd The hashtable with the information selected by user in the form.
	 */
	public static void criterio2(Hashtable fd) {
		switch((String) fd.get("criterio2")) {
		case "infecoes": 
			query=query+"Infecoes";
			break;
		case "testes":
			query=query+"Testes";
			break;
		case "internados":
			query=query+"Internados";
			break;
		}
	}



	/** adds to query string the criteria found on the form where you can use relational operations with...
	 * @param fd The hashtable with the information selected by user in the form.
	 */
	public static void criterio(Hashtable fd) {
		switch((String) fd.get("criterio")) {
		case "infecoes": 
			query=query+"Infecoes";
			break;
		case "testes":
			query=query+"Testes";
			break;
		case "internados":
			query=query+"Internados";
			break;
		}
	}
	/** adds to query string the region.
	 * @param fd The hashtable with the information selected by user in the form.
	 */
	public static String regiao2(Hashtable fd) {
		switch((String) fd.get("regiao2")) {
		case "alentejo": 
			return "Alentejo";
		case "norte":
			return "Norte";
		case "centro":
			return "Centro";
		case "lisboa": 
			return "Lisboa";
		case "algarve":
			return "Algarve";
		}
		return null;
	}
	/** Gets the string in the select area of the form.
	 * @return A string representing the region selected in the form.
	 */
	/** adds to query string the region.
	 * @param fd The hashtable with the information selected by user in the form.
	 */
	public static String regiao(Hashtable fd) {
		switch((String) fd.get("regiao")) {
		case "alentejo": 
			return "Alentejo";
		case "norte":
			return "Norte";
		case "centro":
			return "Centro";
		case "lisboa": 
			return "Lisboa";
		case "algarve":
			return "Algarve";
		}
		return null;
	}
	/** Gets the string in the select area of the form.
	 * @return A string representing the region selected in the form.
	 */


	/** adds to query string the relational operations.
	 * @param fd The hashtable with the information selected by user in the form.
	 */
	public static void relacional2(Hashtable fd) {
		switch((String) fd.get("rel2")) {
		case "equal": 
			query=query+"=";
			break;
		case "dif":
			query=query+"!=";
			break;
		case "maiequal":
			query=query+">=";
			break;
		case "menequal":
			query=query+"<=";
			break;
		case "mai":
			query=query+">";
			break;
		case "men":
			query=query+"<";
			break;
		}
	}



	/** adds to query string the relational operations.
	 * @param fd The hashtable with the information selected by user in the form.
	 */
	public static void relacional(Hashtable fd) {
		switch((String) fd.get("rel")) {
		case "equal": 
			query=query+"=";
			break;
		case "dif":
			query=query+"!=";
			break;
		case "maiequal":
			query=query+">=";
			break;
		case "menequal":
			query=query+"<=";
			break;
		case "mai":
			query=query+">";
			break;
		case "men":
			query=query+"<";
			break;
		}
	}
	/** adds to query string a slash.
	 */
	public static void barra() {
		query=query+"/";
	}
	/** adds to query string "@*" conotation.
	 */
	public static void todos() {
		query=query+"@*";
	}
	/** adds to query string an open bracket.
	 */
	public static void bracketOpen() {
		query=query+"[";
	}
	/** adds to query string a closed bracket.
	 */
	public static void bracketClose() {
		query=query+"]";
	}
	/** adds to query the beginning of a new query string to get the region values.
	 */
	public static void inicio() {
		query=query+"/RDF/NamedIndividual";
	}
	/** adds to query string the sum notation.
	 */
	public static void soma() {
		query = "sum(" + query + ")";
	}
	/** adds to query string the and notation.
	 */
	public static void and() {
		query = query+" and ";
	}
	/** adds to query string the or notation.
	 */
	public static void or() {
		query = query + " | ";
	}
	public static void clear() {
		query="";
	}
}
