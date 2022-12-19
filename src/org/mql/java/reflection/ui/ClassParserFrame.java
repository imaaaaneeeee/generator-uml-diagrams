package org.mql.java.reflection.ui;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import org.mql.java.reflection.ClassParser;

public class ClassParserFrame extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel container;
	private ClassParser c ;
	

	public ClassParserFrame(ClassParser c ) {
		this.c=c;
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBorder(new LineBorder(Color.BLUE));
		container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		container.setBorder(new LineBorder(Color.BLUE));
		//container.setSize(new Dimension(10, 10));
		add(container);
	}
	
	public JPanel addClass() throws ClassNotFoundException {
		JTextArea calssName = new JTextArea();
		calssName.setEditable(false);
		calssName.setBorder(BorderFactory.createEtchedBorder());
		calssName.setOpaque(false);
		calssName.setText(c.getSimpleName());
		container.add(calssName);
		
		JTextArea attribus = new JTextArea();
		attribus.setEditable(false);
		attribus.setBorder(BorderFactory.createEtchedBorder());
		attribus.setOpaque(false);
		attribus.setText(c.detailClass());
		container.add(attribus);
		return container;
		
	}

}
