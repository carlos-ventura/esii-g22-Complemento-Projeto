package grupo22.esii;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
	/**
	 * Git Test
	 */
	@Test
	public void gitTest() {
		App app = new App("a","b");
		app.getGit();
		app.getGiturl();
		
		App app2 = new App("a","b");
		Git gtest=app2.getGit();
		
		assertEquals(app.getGit(), gtest);

		String gtest2 = app.getGiturl();
		
		assertEquals(app.getGiturl(), gtest2);
	}

	/**
	 * Html Test
	 */
	@Test
	public void htmlTest() {
		App app = new App("a","b");

		app.createHTML();
		app.addtoHTML("Test");
		app.endHTML();
		String html = app.getHTMl();

		String test = "<!DOCTYPE html><html><head><style>table {font-family: arial, sans-serif;border-collapse: collapse;width: 100%;}td, th {border: 1px solid #dddddd;text-align: left;padding: 8px;}tr:nth-child(even) {background-color: #dddddd;}</style>"
				+ "</head><body><h2>Covid Spread Table</h2><table><tr><th>File timestamp</th><th>File name</th><th>File tag</th><th> Tag Description</th><th> Spread Visualization Link</th></tr>";
		test+="Test";
		test+="</table></body></html>";
		assertEquals(test, app.getHTMl());

	}

	/**
	 * Replace Caracters Test
	 */
	@Test
	public void replaceTest() {
		App app = new App("a","b");
		String test = "âõéìç";
		app.createHTML();
		app.addtoHTML(test);
		app.replaceQuestionMark();
		app.endHTML();

		App app2 = new App("a","b");
		String test2 = "aoeic";
		app2.createHTML();
		app2.addtoHTML(test2);
		app2.endHTML();

		assertEquals(app.getHTMl(), app2.getHTMl());

	}
	
	/**
	 * Replace Caracters Test
	 */
	@Test
	public void createRepositoryTest() {
		App app = new App("https://github.com/vbasto-iscte/ESII1920","covid19spreading.rdf");
		try {
			app.deleteFiles();
			app.createRepository();
		} catch (GitAPIException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean test= false;
		File index = new File(System.getProperty("user.dir") + "/Git");
		if(index.exists())
			test=true;
		assertEquals(test, true);

	}
	
	
}
