import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class ExamController extends JFrame {
	static ExamView examPanel;
	public final static String FILE_PATH = "resources/";
	public final static String QA_FILE_NAME = FILE_PATH + "question_n_answer";
	public final static String HAPPY1_FILE_NAME = FILE_PATH + "happy1image.gif";
	public final static String HAPPY_FILE_NAME = FILE_PATH + "happyimage.gif";
	public final static String INDIF_FILE_NAME = FILE_PATH + "indiffimage.gif";
	public final static String SAD_FILE_NAME = FILE_PATH + "sadimage.gif";
	public final static String SAD1_FILE_NAME = FILE_PATH + "sad1image.gif";
	public static final String WELCOME_FILE_NAME = FILE_PATH + "welcome.gif";

	public ExamController() {
		GridLayout layout = new GridLayout(2, 1);
		setLayout(layout);
		examPanel = new ExamView(this);
		BlackBoard blackBoard = BlackBoard.getInstance();
		examPanel.addObserver(blackBoard);
		this.add(new JScrollPane(examPanel.viewPanel));
		Companion ghostPanel = new Companion(new GhostBrain());
		this.add(ghostPanel);
		Thread t = new Thread(ghostPanel);
		t.start();
	}

	public static void main(String args[]) {
		ExamController u = new ExamController();
		u.setTitle("Universe Frame");
		u.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		u.setSize(500, 500);
		u.pack();
		u.setVisible(true);
	}

	public static void examOver(int score) {
		examPanel.viewPanel.removeAll();
		examPanel.viewPanel.add(new JLabel("Exam is over and you scored - "
				+ score + "/" + 10));
		examPanel.viewPanel.revalidate();
		examPanel.viewPanel.repaint();
	}
}
