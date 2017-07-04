import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;

public class ExamController extends JFrame {
	public ExamController() {
		GridLayout layout = new GridLayout(2, 1);
		setLayout(layout);
		ExamPanel examPanel = new ExamPanel();
		this.add(new JScrollPane(examPanel));
		GhostPanel ghostPanel = new GhostPanel();
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
}