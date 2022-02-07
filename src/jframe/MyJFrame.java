package jframe;

import java.awt.Container;

import javax.swing.JFrame;

import project.CardGame;
import project.CupGame;

public class MyJFrame extends JFrame {
	
	Container contentPan;

	public MyJFrame() {
		
		this.setTitle("CardGame");
		this.setSize(1024, 768);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		contentPan = getContentPane();
		
		contentPan.add(new CardGame());
//		contentPan.add(new CupGame());
		
		
	}

	public static void main(String[] args) {
		new MyJFrame().setVisible(true);
	}
}