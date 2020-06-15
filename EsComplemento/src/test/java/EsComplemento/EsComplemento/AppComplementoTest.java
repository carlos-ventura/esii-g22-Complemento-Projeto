package EsComplemento.EsComplemento;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.eclipse.jgit.api.Git;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class AppComplementoTest {
	
	Hashtable fd =new Hashtable();
	public static String query = "";
	private static final String URI = "https://github.com/vbasto-iscte/ESII1920.git";
	public static final String file = "covid19spreading.rdf";
	
	@Before
	public void init() throws IOException, ParserConfigurationException, XPathExpressionException, SAXException {
		fd.put("num", "32");
		fd.put("criterioalone", "infecoes");
		fd.put("criterio", "infecoes");
		fd.put("regiao", "algarve");
		fd.put("regiao2", "algarve");
		fd.put("rel", "equal");
		fd.put("rel2", "equal");
		fd.put("num", "32");
		fd.put("num", "32");	
		fd.put("alone","alone");
	}
	@Test
	public void testNumero() {
		App app = new App();
		app.numero(fd);
		assertEquals("32", app.query);	
		app.clear();
	}
	@Test
	public void testNumero2() {
		App app = new App();
		app.numero(fd);
		assertEquals("32", app.query);	
		app.clear();
	}

	@Test
	public void testCriterioAlone() {
		App app = new App();
		assertEquals("Infecoes", app.criterioAlone(fd));
		app.clear();
	}

	@Test
	public void testCriterio() {
		App app = new App();
		app.criterio(fd);
		assertEquals("Infecoes", app.query);
		app.clear();
	}

	@Test
	public void testRegiao() {
		App app = new App();
		assertEquals("Algarve", app.regiao(fd));
		app.clear();
	}
	@Test
	public void testRegiao2() {
		App app = new App();
		assertEquals("Algarve", app.regiao2(fd));
		app.clear();
	}
	@Test
	public void testRelacional() {
		App app = new App();
		app.relacional(fd);
		assertEquals("=", app.query);
		app.clear();
	}
	@Test
	public void testRelacional2() {
		App app = new App();
		app.relacional2(fd);
		assertEquals("=", app.query);
		app.clear();
	}

	@Test
	public void testBarra() {
		App app = new App();
		app.barra();
		assertEquals("/", app.query);
		app.clear();
	}

	@Test
	public void testTodos() {
		App app = new App();
		app.todos();
		assertEquals("@*", app.query);
		app.clear();
	}

	@Test
	public void testBracketOpen() {
		App app = new App();
		app.bracketOpen();
		assertEquals("[", app.query);
		app.clear();
	}

	@Test
	public void testBracketClose() {	
		App app = new App();
		app.bracketClose();
		assertEquals("]", app.query);
		app.clear();
	}

	@Test
	public void testInicio() {
		App app = new App();
		app.inicio();
		assertEquals("/RDF/NamedIndividual", app.query);
		app.clear();
	}


	@Test
	public void testSoma() {
		App app = new App();
		App.soma();
		assertEquals("sum()", App.query);
		app.clear();
	}

	@Test
	public void testAnd() {
		App app = new App();
		App.and();
		assertEquals("AND", app.query);
		app.clear();
	}

	@Test
	public void testOr() {
		App app = new App();
		app.or();
		assertEquals(" | ", app.query);
		app.clear();
	}

}
