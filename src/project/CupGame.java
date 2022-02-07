package project;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class CupGame extends GameContainer {

	static ImageIcon backIcon = new ImageIcon("images/background.png");
	static ImageIcon gameBagIcon = new ImageIcon("images/cupgamebackImg.png");
	static ImageIcon cupIcon = new ImageIcon("images/cup.png");
	static ImageIcon ballIcon = new ImageIcon("images/ball.png");
	static ImageIcon pauseIcon = new ImageIcon("images/pause.png");
	static ImageIcon checkIcon = new ImageIcon("images/checked.png");
	static ImageIcon xIcon = new ImageIcon("images/x.png");
	
	static ImageIcon cupBorderIcon = new ImageIcon("images/cup_stroke.png"); // border있는 컵 그림

	static JLabel backLabel;
	static JLabel gameBackLabel;
	static JLabel checkLabel;
	static JLabel xLabel;
	static JLabel manualLabel;

	static JButton playBtn;
	static JButton pauseBtn;

	static Timer timer;
	static javax.swing.Timer otherCupTtimer;

	static int startBtn;

	static int click0 = 0;
	static int click1 = 0;
	static int click2 = 0;

	static JLabel[] balls = new JLabel[3];

	static Cup[] cups = new Cup[3];

	static boolean flag;

	public CupGame() {
		gamePlay();
	}

	@Override
	public void gamePlay() {

		this.setLayout(null);
		this.setBounds(0, 0, 1024, 768);

		checkLabel = new JLabel(checkIcon);
		checkLabel.setBounds(750, 20, 150, 150);
		checkLabel.setVisible(false);

		xLabel = new JLabel(xIcon);
		xLabel.setBounds(750, 20, 150, 150);
		xLabel.setVisible(false);

		manualLabel = new JLabel("공이 들어있는 컵을 선택하세요");
		manualLabel.setBounds(230, 150, 550, 50);
		manualLabel.setHorizontalAlignment(JLabel.CENTER);
		manualLabel.setFont(new Font("맑은고딕", Font.BOLD, 25));

		pauseBtn = new JButton(pauseIcon);
		pauseBtn.setBounds(920, 30, 50, 50);
		pauseBtn.setBorderPainted(false);
		pauseBtn.setContentAreaFilled(false);

		pauseBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		// 시작하기 버튼
		playBtn = new RoundJButton("시작하기");
		playBtn.setBounds(430, 480, 150, 50);
		playBtn.setBackground(Color.ORANGE);
		playBtn.setFont(new Font("맑은고딕", Font.BOLD, 20));
		playBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (startBtn == 1) {
					return;
				}
				playBtn.setVisible(false);
				manualLabel.setVisible(false);
				cupUpDown();

				startBtn++;
			}
		});

		// 컵 생성
		for (int i = 0; i < cups.length; i++) {
			cups[i] = new Cup();
			cups[i].addActionListener(this);
			cups[i].setBorderPainted(false); // 버튼 외곽 없애기
			cups[i].setContentAreaFilled(false); 
			this.add(cups[i]);
		}

		cups[0].x = 230;
		cups[1].x = 430;
		cups[2].x = 630;

		// 컵 위치
		cups[0].setBounds(cups[0].x, cups[0].y, cups[0].w, cups[0].h);
		cups[1].setBounds(cups[1].x, cups[1].y, cups[0].w, cups[0].h);
		cups[2].setBounds(cups[2].x, cups[2].y, cups[0].w, cups[0].h);

		// 공 생성
		for (int i = 0; i < balls.length; i++) {
			balls[i] = new JLabel(ballIcon);
			this.add(balls[i]);
			if (!(i == 1)) {
				balls[i].setVisible(false);
			}
		}

		// 공 위치
		balls[0].setBounds(280, 350, 50, 50);
		balls[1].setBounds(480, 350, 50, 50);
		balls[2].setBounds(680, 350, 50, 50);

		// 흰색 배경
		gameBackLabel = new JLabel(gameBagIcon);
		gameBackLabel.setBounds(50, 35, 900, 620);

		// 초록 배경
		backLabel = new JLabel(backIcon);
		backLabel.setBounds(0, 0, 1024, 768);

		this.add(manualLabel);
		this.add(xLabel);
		this.add(checkLabel);
		this.add(pauseBtn);
		this.add(playBtn);
		this.add(gameBackLabel);
		this.add(backLabel);
	}

	// 컵 내리기
	public void cupUpDown() {

		timer = new Timer();

		// 3초 카운트
		timer.scheduleAtFixedRate(new TimerTask() {

			int i = 8;

			public void run() {

				if (i > 4) {

					cups[1].y -= 25;

					cups[1].setBounds(cups[1].x, cups[1].y, cups[1].w, cups[1].h);

					i--;

				} else if (i <= 4 && i > 0) {

					cups[1].y += 25;

					cups[1].setBounds(cups[1].x, cups[1].y, cups[1].w, cups[1].h);

					i--;

				} else if (i == 0) {

					timer.cancel();

					balls[1].setVisible(false); // 컵이 내려간 후 공 지우기

					changeCup();
				}

			}
		}, 0, 50);
	}

	// 컵 올리기
	public void cupUp(int index) {

		timer = new Timer();

		// 3초 카운트
		timer.scheduleAtFixedRate(new TimerTask() {

			int i = 3;

			public void run() {

				// 들어오는 인덱스에 따른 컵 올리기
				if (index == 0) {
					cups[0].y -= 25;
					cups[0].setBounds(cups[0].x, cups[0].y, cups[0].w, cups[0].h);
				}

				else if (index == 1) {
					cups[1].y -= 25;
					cups[1].setBounds(cups[1].x, cups[1].y, cups[0].w, cups[0].h);
				}

				else if (index == 2) {
					cups[2].y -= 25;
					cups[2].setBounds(cups[2].x, cups[2].y, cups[0].w, cups[0].h);
				}

				i--;

				if (i < 0) {

					timer.cancel();

					if (index == 0) {
						click0++;
					}

					// 1번 컵의 x 위치에 맞는 공 보여주기
					else if (index == 1) {
						click1++;
						if (cups[1].getX() == 230) {
							balls[0].setVisible(true);
						} else if (cups[1].getX() == 430) {
							balls[1].setVisible(true);
						} else if (cups[1].getX() == 630) {
							balls[2].setVisible(true);
						}
					}

					else if (index == 2) {
						click2++;
					}
				}
			}
		}, 0, 50);
	}
	
	public void otherCupUp(int index1, int index2) {
		otherCupTtimer = new javax.swing.Timer(50, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (index1 == 1) {
					if (cups[1].getX() == 230) {
						balls[0].setVisible(true);
					} else if (cups[1].getX() == 430) {
						balls[1].setVisible(true);
					} else if (cups[1].getX() == 630) {
						balls[2].setVisible(true);
					}
				}

				cups[index1].y -= 25;
				cups[index1].setBounds(cups[index1].x, cups[index1].y, cups[index1].w, cups[index1].h);

				cups[index2].y -= 25;
				cups[index2].setBounds(cups[index2].x, cups[index2].y, cups[index2].w, cups[index2].h);

				if (cups[index1].y == 200) {
					otherCupTtimer.stop();
				}

			}
		});
		otherCupTtimer.start();
	}

	// 컵 섞기
	public void changeCup() {

		int r = 100;

		CupRoad cr = new CupRoad();

		cups[0].road = cr.cupRoadArr[0];
		cups[1].road = cr.cupRoadArr[1];
		cups[2].road = cr.cupRoadArr[2];

		CupThread ct = new CupThread(cups[0], cups[1], cups[2], r, manualLabel);

		ct.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		JButton btn = (JButton) e.getSource();

		if (cups[0] == btn) {
			if (click0 == 1) {
				return;
			}
			cupUp(0);
			otherCupUp(1, 2);
			if(flag == true) {
				return;
			}
			flag = true;
			xLabel.setVisible(flag);
		} else if (cups[1].equals(btn)) {
			if (click1 == 1) {
				return;
			}
			cupUp(1);
			otherCupUp(0, 2);
			if(flag == true) {
				return;
			}
			flag = true;
			checkLabel.setVisible(flag);
		} else if (cups[2].equals(btn)) {
			if (click2 == 1) {
				return;
			}
			cupUp(2);
			otherCupUp(1, 0);
			if(flag == true) {
				return;
			}
			flag = true;
			xLabel.setVisible(flag);
		}
	}

}