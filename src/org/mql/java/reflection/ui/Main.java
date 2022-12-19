package org.mql.java.reflection.ui;


import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.mql.java.reflection.ClassParser;


public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel container;
	
	public Main() throws ClassNotFoundException {		
		container = new JPanel();
		container.setLayout(new FlowLayout(FlowLayout.LEFT));
		container.setBorder(new LineBorder(Color.BLUE));
		setContentPane(container);
		exp01();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		//setSize(700, 300);
		pack();
		setLocationRelativeTo(null);
	}
	private void exp01() throws ClassNotFoundException {
		ClassParser cls = new ClassParser("org.mql.java.reflection.ClassParser");
		ClassParserFrame c = new ClassParserFrame(cls);
		JPanel p1 = c.addClass();
		//p1.setSize(new Dimension(50 ,70));
		container.add(p1);
		ClassParser cls1 = new ClassParser("org.mql.java.reflection.ui.ClassParserFrame");
		ClassParserFrame c1 = new ClassParserFrame(cls1);
		container.add(c1.addClass());
		setContentPane(container);
		
		
		

	}
	
	public static void main(String[] args) throws ClassNotFoundException {
		new Main();
	}

}
