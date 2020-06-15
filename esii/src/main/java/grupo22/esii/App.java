package grupo22.esii;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.io.FileDeleteStrategy;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;

/**
 * This application is intended to analyze the commits associated with a GitHub
 * and to transport all that information to an HTML table with the following
 * headers File timestamp, File name, File tag, Tag Description and Spread
 * Visualization Link.
 *
 * @author Guilherme Rodrigues
 * @version 1.0
 * @since 2020-06-15
 */
public class App {
	private String HTML = new String();
	private Git git;
	private static String GITURL = new String(); // "https://github.com/vbasto-iscte/ESII1920";
	private final static String cssStyle = "<!DOCTYPE html><html><head><style>table {font-family: arial, sans-serif;border-collapse: collapse;width: 100%;}td, th {border: 1px solid #dddddd;text-align: left;padding: 8px;}tr:nth-child(even) {background-color: #dddddd;}</style>";
	private final static String folderpath = System.getProperty("user.dir") + "/Git";
	private String filetoAnalyse = new String();// "covid19spreading.rdf";

	/**
	 * Constructor for the app
	 * 
	 * @param filetoAnalyse File to Analyse
	 * @param gitUrl2 Git Url to be accessed
	 * 
	 */
	public App(String gitUrl2, String filetoAnalyse) {
		this.GITURL = gitUrl2;
		this.filetoAnalyse = filetoAnalyse;
	}

	/**
	 * Creates a cloneRepository
	 * 
	 * @throws InvalidRemoteException expected to throw InvalidRemoteException if an
	 *                                error occurs
	 * @throws TransportException     expected to throw TransportException if an
	 *                                error occurs
	 * @throws GitAPIException        expected to throw GitAPIException if an error
	 *                                occurs
	 * 
	 */
	public void createRepository() throws InvalidRemoteException, TransportException, GitAPIException {
		git = Git.cloneRepository().setURI(GITURL).setDirectory(new File(folderpath)).setCloneAllBranches(true).call();
		git.getRepository().close();

	}

	/**
	 * Delete all the files from the last cloneRepository
	 * 
	 * @throws IOException expected to throw IOException if an error occurs
	 * 
	 */
	public void deleteFiles() throws IOException {
		File index = new File(folderpath);
		index.setExecutable(true);
		index.setReadable(true);
		index.setWritable(true);
		if (index.exists() && index.isDirectory()) {
			for (File file : index.listFiles()) {
				file.setExecutable(true);
				file.setReadable(true);
				file.setWritable(true);
				FileDeleteStrategy.FORCE.delete(file);

			}
		}
	}

	/**
	 * Start by running the app, where you will open the Git repository, do a search
	 * directed to the desired file. As the search is done, the relevant information
	 * will be added to a string in HTML format.
	 * 
	 * @throws IOException            expected to throw IOException if an error
	 *                                occurs
	 * @throws InvalidRemoteException expected to throw InvalidRemoteException if an
	 *                                error occurs
	 * @throws TransportException     expected to throw TransportException if an
	 *                                error occurs
	 * @throws GitAPIException        expected to throw GitAPIException if an error
	 *                                occurs
	 */
	public void execute() throws IOException, InvalidRemoteException, TransportException, GitAPIException {
		// Open Repository
		git = Git.open(new File(folderpath));
		Repository repository = git.getRepository();

		// Start Search for covid file
		List<Ref> call = new Git(repository).tagList().call();

		for (Ref ref : call) {
			ObjectId CommitId = repository.resolve(ref.getName());
			Map<ObjectId, String> names = git.nameRev().add(ref.getObjectId()).addPrefix("refs/tags/").call();
			String nameTag = names.get(ref.getObjectId());
			try (RevWalk revWalk = new RevWalk(repository)) {
				RevCommit commit = revWalk.parseCommit(CommitId);
				RevTree tree = commit.getTree();
				try (TreeWalk treeWalk = new TreeWalk(repository)) {
					treeWalk.addTree(tree);
					treeWalk.setRecursive(true);
					treeWalk.setFilter(PathFilter.create(filetoAnalyse/* "covid19spreading.rdf" */));
					if (treeWalk.next()) {
						addtoHTML("<tr><td>" + getAuthorCommitter(commit).getWhen().toString());
						addtoHTML("</td><td>" + treeWalk.getPathString());
						addtoHTML("</td><td>" + nameTag);
						addtoHTML("</td><td>" + commit.getFullMessage());
						addtoHTML("</td><td><a href=" + "http://visualdataweb.de/webvowl/#iri=" + GITURL + "/raw/"
								+ nameTag /* + commit.getName() */ + "/" + filetoAnalyse/* "/covid19spreading.rdf" */
								+ " target=\"_blank\"\" " + ">" + "Link" + "</a></td></td></tr>");
					}
				}
				revWalk.dispose();
			}

		}
		git.getRepository().close();

		endHTML();
		replaceQuestionMark();
	}

	/**
	 * This function allows to obtain the Timestamp of the received commit.
	 * 
	 * @param commit Commit to be analyzed
	 * @return committerIdent Return the Timestamp
	 */
	public PersonIdent getAuthorCommitter(RevCommit commit) {
		PersonIdent authorIdent = commit.getAuthorIdent();
		Date authorDate = authorIdent.getWhen();
		TimeZone authorTimeZone = authorIdent.getTimeZone();
		PersonIdent committerIdent = commit.getCommitterIdent();
		return committerIdent;

	}

	/**
	 * This function replaces in HTML all occurrences of single characters.
	 */
	public void replaceQuestionMark() {
		HTML = HTML.replaceAll("[á|à|â|ã]", "a");
		HTML = HTML.replaceAll("[é|è|ê]", "e");
		HTML = HTML.replaceAll("[í|ì|î]", "i");
		HTML = HTML.replaceAll("[ó|ò|ô|õ]", "o");
		HTML = HTML.replaceAll("[ú|ù|û]", "u");
		HTML = HTML.replaceAll("ç", "c");

	}

	/**
	 * This method creates the initial HTML with the necessary css and the header.
	 */
	public void createHTML() {
		this.HTML += cssStyle;
		this.HTML += "</head><body><h2>Covid Spread Table</h2><table><tr><th>File timestamp</th><th>File name</th><th>File tag</th><th> Tag Description</th><th> Spread Visualization Link</th></tr>";
	}

	/**
	 * This method adds content to the HTML string.
	 * 
	 * @param html String to be added
	 * @return HTML Full string html updated
	 */
	public String addtoHTML(String html) {
		HTML += html;
		return HTML;
	}

	/**
	 * This method ends HTML format String
	 */
	public void endHTML() {
		this.HTML += "</table></body></html>";
	}

	/**
	 * Getter of the HTML string.
	 * 
	 * @return HTML full string html
	 */
	public String getHTMl() {
		return HTML;
	}

	/**
	 * Getter of the Git
	 * 
	 * @return git Git chosen to analyse
	 */
	public Git getGit() {
		return git;
	}

	/**
	 * Getter of the Git Url
	 * 
	 * @return GITURL Site of the Git
	 */
	public String getGiturl() {
		return GITURL;
	}

	public static void main(String[] args) {
		System.out.println("Content-type: text/html\n\n");

		String gitUrl = "https://github.com/vbasto-iscte/ESII1920"; // args[0];
		String filetoAnalyse = "covid19spreading.rdf"; // args[1];

		App a = new App(gitUrl, filetoAnalyse);
		try {
			a.deleteFiles();
			a.createRepository();
			a.createHTML();
			a.execute();

		} catch (IOException | GitAPIException e) {
		}

		System.out.println(a.getHTMl());

	}

}
