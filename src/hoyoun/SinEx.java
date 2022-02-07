package hoyoun;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

class CupRoad {

	int[][] cupsArr = new int[6][3];
	int[][] cuproad = new int[3][6];

	public CupRoad() {
		cupsArr[0][0] = 1;
		cupsArr[0][1] = 2;
		cupsArr[0][2] = 3;
		for (int i = 1; i < 6; i++) {
			for (int j = 0; j < 3; j++) {
				int a = (int) (Math.random() * 100000) % 3 + 1;
				cupsArr[i][j] = a;
				for (int k = 0; k < j; k++) {
					if (a == cupsArr[i][k]) {
						j--;
						break;
					}
				}
			}
		}

//      for(int i=0; i<5; i++) {
//         for(int j =0; j<3; j++) {
//            System.out.print(cupsArr[i][j]+" ");
//         }
//         System.out.println();
//      }

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 6; j++) {
				cuproad[i][j] = cupsArr[j][i];
			}
		}

		for (int i = 0; i < 3; i++) {
			System.out.print("cup" + (i + 1) + "'s road : ");
			for (int j = 0; j < 6; j++) {
				System.out.print(cuproad[i][j] + " ");
			}
			System.out.println();
		}

	}

}

class Cup extends JButton {
	int x;
	int y;
	int now;
	int next;
	int[] road;

	public Cup(int x, int y, Color color, int[] road) {
		this.x = x;
		this.y = y;
		this.road = road;
		this.setBackground(color);
	}

}

class CupThread extends Thread {
	Cup cup1;
	Cup cup2;
	Cup cup3;
	int r;

	public CupThread(Cup cup1, Cup cup2, Cup cup3, int r) {
		this.cup1 = cup1;
		this.cup2 = cup2;
		this.cup3 = cup3;
		this.r = r;
	}

	@Override
	public void run() {
		int i = 0;
		int cup1x = cup1.x;
		int cup2x = cup2.x;
		int cup3x = cup3.x;
		int y = cup1.y;

		int roop = 0;

		cup1.now = cup1.road[0];
		cup1.next = cup1.road[1];
		cup2.now = cup2.road[0];
		cup2.next = cup2.road[1];
		cup3.now = cup3.road[0];
		cup3.next = cup3.road[1];

		int a1 = cup1.next - cup1.now;
		int a2 = cup2.next - cup2.now;
		int a3 = cup3.next - cup3.now;

		while (true) {

			if (i < 181) {

				road(cup1, a1, cup1x, y, i);
				road(cup2, a2, cup2x, y, i);
				road(cup3, a3, cup3x, y, i);

				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				cup1.setBounds(cup1.x, cup1.y, 50, 50);
				cup2.setBounds(cup2.x, cup2.y, 50, 50);
				cup3.setBounds(cup3.x, cup3.y, 50, 50);


			}

			if (roop == 4) {
				break;
			}
			if (i < 181) {
				i++;
			} else if (i == 181) {
				roop++;
				cup1x = cup1.x;
				cup2x = cup2.x;
				cup3x = cup3.x;
				cup1.now = cup1.road[roop];
				cup1.next = cup1.road[roop + 1];

				cup2.now = cup2.road[roop];
				cup2.next = cup2.road[roop + 1];

				cup3.now = cup3.road[roop];
				cup3.next = cup3.road[roop + 1];

				a1 = cup1.next - cup1.now;
				a2 = cup2.next - cup2.now;
				a3 = cup3.next - cup3.now;
				System.out.println(cup1.x);
				System.out.println(cup2.x);
				System.out.println(cup3.x);
				i = 0;
			}
		}
	}

	public void road(Cup cup, int a, int x, int y, int i) {
		if (a != 0) {
			cup.x = (x + a * r) - (int) (a * r * Math.cos(i * Math.PI / 180));
			cup.y = (y - (int) (a * r * Math.sin(i * Math.PI / 180)));
		} else {
			cup.y = (y - (int) (r * Math.sin(i * Math.PI / 180)));
		}
	}
}

public class SinEx extends JFrame implements ActionListener {
	Container contentPane;
	JPanel pane = new JPanel();
	JLabel lbl = new JLabel("dddd");
	JButton btn0 = new JButton("시작");

	Timer timer;
	int x1 = 100;
	int y1 = 200;
	int x2 = 300;
	int y2 = 200;
	int x3 = 500;
	int y3 = 200;

	static int r = 100;
	static int x = 200;
	static int y = 200;

	static int w = 50;
	static int h = 50;

	CupRoad cr = new CupRoad();

	Cup cup1 = new Cup(x1, y1, Color.red, cr.cuproad[0]);
	Cup cup2 = new Cup(x2, y2, Color.blue, cr.cuproad[1]);
	Cup cup3 = new Cup(x3, y3, Color.green, cr.cuproad[2]);

	public SinEx() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1024, 768);
		contentPane = getContentPane();
		contentPane.setLayout(null);
		contentPane.add(pane);
		pane.setLayout(null);
		pane.setBounds(0, 0, 1024, 768);

		btn0.setBackground(Color.black);

		pane.add(cup1);
		pane.add(cup2);
		pane.add(cup3);
		pane.add(btn0);
		cup1.setBounds(x1, y1, w, h);
		cup2.setBounds(x2, y2, w, w);
		cup3.setBounds(x3, y3, w, w);
		btn0.setBounds(900, 500, 50, 50);

		this.setVisible(true);

		btn0.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		CupThread ct = new CupThread(cup1, cup2, cup3, r);
		ct.start();

	}

	public static void main(String[] args) {
		new SinEx();
	}

}