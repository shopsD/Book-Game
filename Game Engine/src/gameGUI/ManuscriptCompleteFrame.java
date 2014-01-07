package gameGUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import gameController.ManuscriptCreator;
import gameController.PointControlClass;
import gameController.Product;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class ManuscriptCompleteFrame extends JFrame{
	//instantiated by DataController
	private JPanel manuscriptPanel = new JPanel();
	private JPanel eduStatsPanel = new JPanel();
	private JPanel entStatsPanel = new JPanel();
	private JPanel statsPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();

	private JButton releaseButton;

	private JTextPane manuscriptNamePane = new JTextPane();
	private JTextPane manuscriptTypePane = new JTextPane();
	private JTextPane manuscriptGenrePane = new JTextPane();
	private JTextPane manuscriptAudiencePane = new JTextPane();
	private JTextPane manuscriptFictionPane = new JTextPane();
	private JTextPane manuscriptTopicPane = new JTextPane();
	private JTextPane manuscriptPrequelPane = new JTextPane();
	private JTextPane manuscriptPurposePane = new JTextPane();

	private JTextPane manuscriptCreativity = new JTextPane();
	private JTextPane manuscriptIntrigue = new JTextPane();
	private JTextPane manuscriptSuspense = new JTextPane();
	private JTextPane manuscriptDesign = new JTextPane();
	private JTextPane manuscriptAccuracy = new JTextPane();
	private JTextPane manuscriptRelevancy = new JTextPane();
	private JTextPane manuscriptGrammar = new JTextPane();

	private ManuscriptCreator mc;
	private Product pdc;
	private PointControlClass pcc;
	public ManuscriptCompleteFrame(MainMenuFrame mmf, Product pdc, PointControlClass pcc){
		this.pcc = pcc;
		this.pdc = pdc;
		this.setSize(320, 240);
		this.setVisible(false);
		//this.setResizable(false);
		this.setAlwaysOnTop(true);
		//this.setLocationRelativeTo(mmf);
		this.setTitle("Manuscript Complete");

		createFrameItems();

		manuscriptPanel.setLayout(new GridLayout(8,2));
		eduStatsPanel.setLayout(new GridLayout(4,2));
		entStatsPanel.setLayout(new GridLayout(4,2));
		entStatsPanel.setOpaque(true);
		this.add(statsPanel, BorderLayout.WEST);
		this.add(manuscriptPanel, BorderLayout.EAST);
		this.add(buttonPanel, BorderLayout.SOUTH);


		this.addWindowListener(new WindowListener(){

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				closeWindow();
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

		});
	}

	public void setManuscript(ManuscriptCreator mc){
		this.mc = mc;
	}

	public void showWindow(){
		// for showing the correct stats panel
		if(mc.getPurpose().equalsIgnoreCase("education")){
			if(statsPanel.getComponentCount()>0){
				statsPanel.remove(entStatsPanel);
			}
			setTextPaneSettings(manuscriptGrammar, eduStatsPanel);
			statsPanel.add(eduStatsPanel);
		}else{
			if(statsPanel.getComponentCount()>0){
				statsPanel.remove(eduStatsPanel);
			}
			setTextPaneSettings(manuscriptGrammar, entStatsPanel);
			statsPanel.add(entStatsPanel);
		}
		updatePaneText();
		releaseButton.setText("Release " + mc.getType());
		this.setVisible(true);
	}

	private void setPaneText(JTextPane textPane, String dataType, String dataText, JPanel thePane){
		try{
			if(dataText == null){
				thePane.remove(textPane);
			}else{
				textPane.setText(dataType + ": "+dataText);
				thePane.add(textPane);
			}
		}catch(NullPointerException npe){
			textPane.setText(dataType + ": "+dataText);
		}
	}

	private void setTextPaneSettings(JTextPane textPane, JPanel thePanel){
		textPane.setEditable(false);
		textPane.setFocusable(false);
		thePanel.add(textPane);
	}

	private void updatePaneText(){
		setPaneText(manuscriptNamePane, "Name", mc.getManuscriptName(), manuscriptPanel);
		setPaneText(manuscriptTypePane, "Type", mc.getType(), manuscriptPanel);
		
		//adds second genre if it is available
		if (mc.getGenreTwo() == null){
			setPaneText(manuscriptGenrePane, "Genre", mc.getGenre(), manuscriptPanel);
		}
		else{
			setPaneText(manuscriptGenrePane, "Genre", mc.getGenre() + "-" + mc.getGenreTwo(), manuscriptPanel);
		}
		
		setPaneText(manuscriptAudiencePane, "Audience",  mc.getAudience(), manuscriptPanel);
		setPaneText(manuscriptFictionPane, "Fiction/Non-Fiction", mc.getFiction(), manuscriptPanel);
		
		//adds second and third topics if they are available
		if(mc.getTopicTwo() == null){
			setPaneText(manuscriptTopicPane, "Topic", mc.getTopic(), manuscriptPanel);
		}
		else if(mc.getTopicThree() == null){
			setPaneText(manuscriptTopicPane, "Topic", mc.getTopic() + "-" + mc.getTopicTwo(), manuscriptPanel);
		}
		else{
			setPaneText(manuscriptTopicPane, "Topic", mc.getTopic() + "-" + mc.getTopicTwo() + "-" + mc.getTopicThree(), manuscriptPanel);
		}
		setPaneText(manuscriptPrequelPane, "Prequel", mc.getPrequelName(), manuscriptPanel);
		setPaneText(manuscriptPurposePane, "Purpose", mc.getPurpose(), manuscriptPanel);
		
		//adds other data and text to the panel
		setPaneText(manuscriptCreativity, "Creativity", "" + mc.getCreativity(), entStatsPanel);
		setPaneText(manuscriptIntrigue, "Intrigue", "" + mc.getIntrigue(), entStatsPanel);
		setPaneText(manuscriptSuspense, "Suspense", "" + mc.getSuspense(), entStatsPanel);
		setPaneText(manuscriptDesign, "Design", "" + mc.getDesign(), eduStatsPanel);
		setPaneText(manuscriptAccuracy, "Accuracy", "" + mc.getAccuracy(), eduStatsPanel);
		setPaneText(manuscriptRelevancy, "Relevancy", "" + mc.getRelevancy(), eduStatsPanel);
		setPaneText(manuscriptGrammar, "Grammar", "" + mc.getGrammar(), null);

	}

	private void createFrameItems(){
		//for general data
		setTextPaneSettings(manuscriptNamePane, manuscriptPanel);
		setTextPaneSettings(manuscriptTypePane, manuscriptPanel);
		setTextPaneSettings(manuscriptGenrePane, manuscriptPanel);
		setTextPaneSettings(manuscriptFictionPane, manuscriptPanel);
		setTextPaneSettings(manuscriptTopicPane, manuscriptPanel);
		setTextPaneSettings(manuscriptPurposePane, manuscriptPanel);
		setTextPaneSettings(manuscriptAudiencePane, manuscriptPanel);
		setTextPaneSettings(manuscriptPrequelPane, manuscriptPanel);

		//for entertainment
		setTextPaneSettings(manuscriptCreativity, entStatsPanel);
		setTextPaneSettings(manuscriptIntrigue, entStatsPanel);
		setTextPaneSettings(manuscriptSuspense, entStatsPanel);

		//for education
		setTextPaneSettings(manuscriptDesign, eduStatsPanel);
		setTextPaneSettings(manuscriptAccuracy, eduStatsPanel);
		setTextPaneSettings(manuscriptRelevancy, eduStatsPanel);

		createButtons("Release", buttonPanel);
		createButtons("Store", buttonPanel);
		createButtons("Trash", buttonPanel);

	}

	private void createButtons(final String buttonName, JPanel thePanel){
		JButton button = new JButton(buttonName);
		button.setText(buttonName);
		if(buttonName.equalsIgnoreCase("release")){
			releaseButton = button;
		}
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(buttonName.equalsIgnoreCase("Release")){
					pdc.releaseManuscript(mc);
					mc.startRelease();
					closeWindow();
					dispose();
				}else if(buttonName.equalsIgnoreCase("Trash")){
					closeWindow();
					dispose();
				}else if(buttonName.equals("Store")){
					pdc.saveManuscript(mc);
					closeWindow();
					dispose();
				}

			}

		});

		thePanel.add(button);
	}

	private void closeWindow(){
		statsPanel.removeAll();
		pcc.resetPoints();
	}
}
