package req3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.select.Elements;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase{
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public AppTest( String apptest){
		super( apptest );
	}


	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite(){
		return new TestSuite( AppTest.class );
	}


	static App3 app;

	/**
	 * Testing field in the constructor
	 */
	public void testApp(){
		app = new App3();

		List<String> path_names = app.getPath_names();
		assertEquals(path_names, app.getPath_names());

		List<ArrayList<Elements>> metadata = app.getMetadata();
		assertEquals(metadata, app.getMetadata());

		List<String> files_names = app.getFile_names();
		assertEquals(files_names, app.getFile_names());

		HashMap<String, String> pdf = app.getPdf_meta();
		assertEquals(pdf, app.getPdf_meta());

		List<String> meta = app.getMeta();
		assertEquals(meta, app.getMeta());

	}


	/**
	 * Testing the html fields in the app
	 */
	public void testHtml() {

		app = new App3();

		String html = app.getHtml();
		assertEquals(html, app.getHtml());

		String html_start = app.getHtml_start();
		assertEquals(html_start, app.getHtml_start());

		app.setHtml_start("Start test");
		assertEquals(app.getHtml_start(), "Start test");

		String html_final = app.getHtml_final();
		assertEquals(html_final, app.getHtml_final());

		app.setHtml_final("End test");
		assertEquals(app.getHtml_final(), "End test");


	}


	/**
	 * Testing the path(folder) where the app gets the pdf files
	 * Testing wordpress url
	 */
	public void test_folder_path() {

		app = new App3();

		String folder_path = app.getFolder_path();
		assertEquals(folder_path, app.getFolder_path());

		app.setFolder_path("path_test");
		assertEquals (app.getFolder_path(), "path_test");

		String wp = app.getWordpressurl();
		assertEquals(wp, app.getWordpressurl());

		app.setWordpressurl("http");
		assertEquals(app.getWordpressurl(), "http");
	}

}
