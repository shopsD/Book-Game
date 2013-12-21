package gameGUI;

import gameController.DataController;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class BottomDisplayPanel extends JPanel{
	private JPanel leftPanel = new JPanel();
	private JPanel rightPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	private JPanel centerLeft = new JPanel();
	private JPanel centerRight = new JPanel();
	private JTextArea playerNameText = new JTextArea();

	private DataController dc;
	
	BottomDisplayPanel (DataController dc){
		this.dc = dc;
		setTextAreaSettings(playerNameText);
		leftPanel.add(playerNameText);

		playerNameText.setVisible(true);
		createCenterPanel();
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(leftPanel, BorderLayout.WEST);
		this.add(rightPanel, BorderLayout.EAST);
		this.setBorder(BorderFactory.createLineBorder(Color.gray, 1));;
	}
	
	public void getPlayerName(String playerName){
		playerNameText.setText(playerName);
	}
	
	private void setTextAreaSettings(JTextArea textArea){
		//editing text area
		textArea.setPreferredSize(new Dimension(100, 30));
		textArea.setAlignmentY(CENTER_ALIGNMENT);
		textArea.setAlignmentX(CENTER_ALIGNMENT);
		textArea.setMargin(new Insets(5, 5, 5, 5));
		textArea.setEditable(false);
		textArea.setFocusable(false);
	}
	
	private void createCenterPanel(){
		
		centerPanel.add(centerLeft, BorderLayout.WEST);
		centerPanel.add(centerRight, BorderLayout.EAST);
	}
}
