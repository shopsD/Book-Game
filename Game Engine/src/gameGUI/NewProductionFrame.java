package gameGUI;

import gameController.AudienceList;
import gameController.GenreList;
import gameController.ManuscriptCreator;
import gameController.DataController;
import gameController.Product;
import gameController.TopicList;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class NewProductionFrame extends JFrame {
	//instantiated by MainGamePanel
	private DataController dc;
	private UpperDisplayPanel udp;
	private MainGamePanel mgp;
	private Product prd;

	private String bookName;
	private String manuscriptType; // magazine/novel/text book e.t.c
	private String genre; // book genre
	private String genreTwo;
	private String audience; //target audience
	private String fictionType; // fiction or non fiction
	private String topic; // book topic
	private String topicTwo;
	private String topicThree;
	private String prequelName; // magazine/website name for articles or previous book name
	private String purpose; // education or entertainment

	private ManuscriptCreator prequel;

	private boolean isSequel = false;

	private Random pointRandom = new Random();//for adding points to sequels
	
	private JTextArea manuscriptNameInput = new JTextArea(); // input manuscript name

	private JPopupMenu manuscriptOptions = new JPopupMenu(); // set book, magazines e.t.c
	private JPopupMenu genreOptions = new JPopupMenu(); // sets genre
	private JPopupMenu audienceOptions = new JPopupMenu();
	private JPopupMenu topicOptions = new JPopupMenu();
	private JPopupMenu prequelOptions = new JPopupMenu();
	//new labels
	private JLabel manuscriptTypeButtonLabel;
	private JLabel genreTypeButtonLabel;
	private JLabel genreTwoTypeButtonLabel;
	private JLabel audienceTypeButtonLabel;
	private JLabel topicTypeButtonLabel;
	private JLabel topicTwoTypeButtonLabel;
	private JLabel topicThreeTypeButtonLabel;
	private JLabel prequelNameButtonLabel;
	// Radio boxes
	private ButtonGroup manuscriptFiction = new ButtonGroup();
	private ButtonGroup manuscriptPurpose = new ButtonGroup();
	//Radio buttons
	private JRadioButton eduButton;
	private JRadioButton entButton;
	private JRadioButton ficButton;
	private JRadioButton nonFButton;

	private JPanel infoPane = new JPanel();
	private JPanel buttonPane = new JPanel();

	private JPanel purposePane = new JPanel();
	private JPanel fictionPane = new JPanel();
	private JPanel topicPane = new JPanel();
	private JPanel genrePane = new JPanel();
	private JPanel prequelTypePane = new JPanel();

	private String labelLB;

	private static final int GRID_ROWS = 8;
	private static final int GRID_COLUMNS = 3;
	private static final int TEXT_AREA_WIDTH = 20;
	private static final int TEXT_AREA_HEIGHT = 5;
	private static final int FRAME_WIDTH = 370;
	private static final int FRAME_HEIGHT = 370;
	private static final int LABEL_WIDTH = 105;
	private static final int LABEL_HEIGHT = 30;
	
	public NewProductionFrame(MainGamePanel mainGamePanel, DataController dc, UpperDisplayPanel udp) {
		this.dc = dc;
		this.udp = udp;
		mgp = mainGamePanel;
		dc.setNewProductionFrame(this);
	}

	public void createGUI(){	

		setPaneSettings(purposePane);
		setPaneSettings(fictionPane);
		setPaneSettings(topicPane);
		setPaneSettings(genrePane);
		setPaneSettings(prequelTypePane);

		//for popup menu's
		createMenuItem("Book", manuscriptOptions, null);
		createMenuItem("Comic", manuscriptOptions, null);
		createMenuItem("Article", manuscriptOptions, null);
		createMenuItem("Blog", manuscriptOptions, null);
		createMenuItem("Magazine", manuscriptOptions, null);
		createMenuItem("Script", manuscriptOptions, null);

		// loop through enums
		for (AudienceList anl : AudienceList.values()){
			createMenuItem(anl.getAudienceName(), audienceOptions, null);
		}

		JTextArea nameText = new JTextArea("Manuscript Name");
		nameText.setEditable(false); // prevents the text from being edited
		nameText.setFocusable(false); // prevents the text from being focused
		nameText.setSize(TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT);
		nameText.setBackground(Color.GRAY);

		//text
		infoPane.add(nameText, BorderLayout.WEST); // add data
		infoPane.add(manuscriptNameInput, BorderLayout.EAST);
		//buttons
		createButtons("Next", buttonPane);
		createButtons("Cancel", buttonPane);
		//labels
		createLabels("Manuscript Type", "Choose Type", prequelTypePane);
		createLabels("Prequel Name", "Prequel Name", prequelTypePane);
		createLabels("Audience Type", "Choose Audience", prequelTypePane);

		createLabels("Topic Type","Choose Topic", topicPane);
		createLabels("Topic Type Two","Choose Topic", topicPane);
		createLabels("Topic Type Three","Choose Topic", topicPane);

		createLabels("Genre Type", "Choose Genre", genrePane);
		createLabels("Genre Type Two", "Choose Genre", genrePane);

		//radio buttons

		createRadioButton("Entertainment", manuscriptPurpose, purposePane);
		createRadioButton("Education", manuscriptPurpose, purposePane);
		createRadioButton("Non-Fiction", manuscriptFiction, fictionPane);
		createRadioButton("Fiction", manuscriptFiction, fictionPane);

		infoPane.add(prequelTypePane);
		infoPane.add(topicPane);
		infoPane.add(genrePane);
		infoPane.add(purposePane);
		infoPane.add(fictionPane);
		infoPane.setLayout(new GridLayout(GRID_ROWS, GRID_COLUMNS));//sets a grid layout

		this.add(infoPane, BorderLayout.NORTH);
		this.add(buttonPane, BorderLayout.SOUTH);

		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setResizable(false);
		this.setLocationRelativeTo(mgp);
		setWindowListenerBehaviour();
	}

	public void showWindow(){
		if(isSequel){
			this.setTitle("New Entry");
		}else{
			this.setTitle("New Manuscript");
		}
		manuscriptNameInput.setText("Manuscript #" + ((prd.getProductVectorSize() + prd.getUnreleasedProductVectorSize())+1));
		this.setVisible(true);
	}

	public void setProduct(Product prd){
		this.prd = prd;
	}

	public void showAudienceOption(){
		//for unlocking audience options
		prequelTypePane.add(audienceTypeButtonLabel);
		this.repaint();
	}

	public void showEducationOption(){
		//for unlocking education options
		purposePane.add(eduButton);
		this.repaint();
	}

	public void showTopicOptions(){
		//for unlocking topic options
		topicPane.add(topicTwoTypeButtonLabel);
		topicPane.add(topicThreeTypeButtonLabel);
		this.repaint();
	}

	public void showGenreOptions(){
		//for unlocking genre options
		genrePane.add(genreTwoTypeButtonLabel);
		this.repaint();
	}

	public void setIsSequel(boolean isSequel){
		this.isSequel = isSequel;
	}

	private void createButtons (final String buttonName, JPanel thePanel){
		JButton button = new JButton (buttonName);
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				checkButtonClicked(buttonName);

			}

		});
		thePanel.add(button);
	}

	private void checkButtonClicked(String buttonName){
		String bn = buttonName.toLowerCase(); //converts to lower case
		if (bn.equals("cancel")){
			closeWindow();
			this.dispose();
		}

		if (bn.equals("next")){
			if(checkEnteredValues()){
				bookName = manuscriptNameInput.getText();
				ManuscriptCreator mc = new ManuscriptCreator(bookName,manuscriptType,genre,genreTwo,audience,fictionType, topic, topicTwo, topicThree,prequelName,purpose, prequel, mgp.getMainMenuFrame(), dc);
				if(isSequel){
					prequel.setIsPrequel(true);
					//add random points to sequel
					mc.setGrammar(10+(pointRandom.nextInt(prequel.getGrammar())+1));
					if(prequel.getPurpose().equalsIgnoreCase("education")){
						mc.setDesign(10+(pointRandom.nextInt(prequel.getDesign())+1));
						mc.setAccuracy(10+(pointRandom.nextInt(prequel.getRelevancy())+1));
						mc.setRelevancy(10+(pointRandom.nextInt(prequel.getRelevancy())+1));
						
					}else{
						mc.setCreativity(10+(pointRandom.nextInt(prequel.getCreativity())+1));
						mc.setSuspense(10+(pointRandom.nextInt(prequel.getSuspense())+1));
						mc.setIntrigue(10+(pointRandom.nextInt(prequel.getIntrigue())+1));
					}
					
				}
				dc.setManuscript(mc); // adds new book to dataController class
				udp.setMenuPaneText(bookName, manuscriptType, false);
				dc.startmanuscript(); // sets book to start writing
				closeWindow();
				this.dispose();
			}
		}

	}

	private void createLabels (final String labelName, String labelText, JPanel thePanel){
		//creating new jlabels
		final JLabel label = new JLabel (labelName, SwingConstants.CENTER);
		label.setOpaque(true); // make background visible
		label.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		label.setBackground(Color.gray); // set default colour
		label.setForeground(Color.black); //set text colour
		label.setText(labelText); // set label text
		label.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
		thePanel.add(label);
		//add new labels here
		//assing main variable names;
		if (labelName.equalsIgnoreCase("manuscript type")){
			manuscriptTypeButtonLabel = label;
		}else if(labelName.equalsIgnoreCase("topic type")){
			topicTypeButtonLabel = label;
		}
		else if(labelName.equalsIgnoreCase("topic type two")){
			topicTwoTypeButtonLabel = label;
			thePanel.remove(label);
		}
		else if(labelName.equalsIgnoreCase("topic type three")){
			topicThreeTypeButtonLabel = label;
			thePanel.remove(label);
		}
		else if(labelName.equalsIgnoreCase("genre type")){
			genreTypeButtonLabel = label;
		}
		else if(labelName.equalsIgnoreCase("genre type two")){
			genreTwoTypeButtonLabel = label;
			thePanel.remove(label);
		}
		else if(labelName.equalsIgnoreCase("audience type")){
			audienceTypeButtonLabel = label;
			thePanel.remove(label);
		}
		else if(labelName.equalsIgnoreCase("prequel name")){
			prequelNameButtonLabel = label;
			thePanel.remove(label);
		}

		/////////////////////------------------------
		label.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				checkLabelClicked(labelName, label);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// on hover change colour
				label.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// return to default colour
				label.setBackground(Color.GRAY);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});

	}

	private void checkLabelClicked(String labelName, JLabel label){
		String lb = labelName.toLowerCase(); //converts to lower case

		//name of JLabel (NOT TEXT)
		if (lb.equals("manuscript type")){
			manuscriptOptions.show(label, label.getHorizontalTextPosition() - 10, label.getVerticalTextPosition()+ 30);
		}
		//creating dynamic topic menus to prevent duplicate options
		if (lb.equals("topic type")){
			topicOptions.removeAll();
			for (TopicList tpl : TopicList.values()){
				// if not topic two and three
				if(!(tpl.getTopic().equals(topicTwo)) && !(tpl.getTopic().equals(topicThree))){
					createMenuItem(tpl.getTopic(), topicOptions, null);
				}
			}
			labelLB = "topic one";// for renaming label
			topicOptions.show(label, label.getHorizontalTextPosition() - 10, label.getVerticalTextPosition()+ 30);
		}
		if (lb.equals("topic type two")){
			if (topic != null){
				topicOptions.removeAll();
				createMenuItem("Choose Topic Two", topicOptions, null);
				for (TopicList tpl : TopicList.values()){
					//if not one and three
					if(!(tpl.getTopic().equals(topic)) && !(tpl.getTopic().equals(topicThree))){
						createMenuItem(tpl.getTopic(), topicOptions, null);
					}
				}
				labelLB = "topic two";// for renaming label
				topicOptions.show(label, label.getHorizontalTextPosition() - 10, label.getVerticalTextPosition()+ 30);
			}
		}
		if (lb.equals("topic type three")){

			if (topic != null && topicTwo != null){
				topicOptions.removeAll();
				createMenuItem("Choose Topic Three", topicOptions, null);
				for (TopicList tpl : TopicList.values()){
					//if not one and two
					if(!(tpl.getTopic().equals(topic)) && !(tpl.getTopic().equals(topicTwo))){
						createMenuItem(tpl.getTopic(), topicOptions, null);
					}
				}
				labelLB = "topic three"; // for renaming label
				topicOptions.show(label, label.getHorizontalTextPosition() - 10, label.getVerticalTextPosition()+ 30);
			}	
		}
		if (lb.equals("audience type")){
			audienceOptions.show(label, label.getHorizontalTextPosition() - 10, label.getVerticalTextPosition()+ 30);
		}
		//creating dynamic menu's to prevent duplicate options
		if (lb.equals("genre type")){
			genreOptions.removeAll();
			for (GenreList gnl : GenreList.values()){
				if(!(gnl.getGenreName().equals(genreTwo))){
					createMenuItem(gnl.getGenreName(), genreOptions, null);
				}
			}
			labelLB = "genre one";
			genreOptions.show(label, label.getHorizontalTextPosition() - 10, label.getVerticalTextPosition()+ 30);
		}
		if (lb.equals("genre type two")){
			if (genre != null){
				genreOptions.removeAll();
				createMenuItem("Choose Genre Two", genreOptions, null);
				for (GenreList gnl : GenreList.values()){
					if(!(gnl.getGenreName().equals(genre))){
						createMenuItem(gnl.getGenreName(), genreOptions, null);
					}
				}
				labelLB = "genre two";
				genreOptions.show(label, label.getHorizontalTextPosition() - 10, label.getVerticalTextPosition()+ 30);
			}
		}
		if (lb.equals("prequel name")){
			prequelOptions.removeAll();
			//dynamically create menu's
			for (int i = 0; i < prd.getProductVectorSize(); i++){
				if(prd.getProductVectorSize()>0){
					if(prd.getReleasedManuscript(i).getIsPrequel() == false){
						//if it is not a prequel, add it to list of new sequels
						createMenuItem(prd.getReleasedManuscript(i).getManuscriptName(), prequelOptions, prd.getReleasedManuscript(i));
					}
				}
			}
			//released products
			for (int i = 0; i < prd.getUnreleasedProductVectorSize(); i++){
				if(prd.getUnreleasedProductVectorSize()>0){		
					if(prd.getUnreleasedManuscript(i).getIsPrequel() == false){
						createMenuItem(prd.getUnreleasedManuscript(i).getManuscriptName(), prequelOptions, prd.getUnreleasedManuscript(i));
					}
				}
			}

			prequelOptions.show(label, label.getHorizontalTextPosition() - 10, label.getVerticalTextPosition()+ 30);
		}
	}

	private void createRadioButton(final String buttonName, ButtonGroup buttonGroupName, JPanel panelName){
		JRadioButton newRadioButton = new JRadioButton (buttonName);
		buttonGroupName.add(newRadioButton);
		panelName.add(newRadioButton);
		if(buttonName.equalsIgnoreCase("education")){
			eduButton = newRadioButton;
			panelName.remove(eduButton);
		}else if(buttonName.equalsIgnoreCase("entertainment")){
			entButton = newRadioButton;
		}
		else if(buttonName.equalsIgnoreCase("fiction")){
			ficButton = newRadioButton;
		}
		else if(buttonName.equalsIgnoreCase("non-fiction")){
			nonFButton = newRadioButton;
		}
		newRadioButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(buttonName.equalsIgnoreCase("fiction") || buttonName.equalsIgnoreCase("non-fiction")){
					fictionType = buttonName;
				}else if (buttonName.equalsIgnoreCase("education") || buttonName.equalsIgnoreCase("entertainment")){
					purpose = buttonName;
				}
			}
		});
	}

	private void hideDefaultMenus(JMenuItem menuItemOption, JPopupMenu newMenu){
		String mio = menuItemOption.getText();
		if(mio.equalsIgnoreCase("book") || mio.equalsIgnoreCase("History") || mio.equalsIgnoreCase("Numbers")
				|| mio.equalsIgnoreCase("mystery") || mio.equalsIgnoreCase("Military") || newMenu == audienceOptions
				|| newMenu == genreOptions || newMenu == prequelOptions || mio.equalsIgnoreCase("choose topic") || 
				mio.equalsIgnoreCase("choose genre")){
			menuItemOption.setVisible(true);
			repaint();
		}else{
			menuItemOption.setVisible(false);
		}
	}

	private JPopupMenu createMenuItem (final String itemLabel, final JPopupMenu newMenu, final ManuscriptCreator prequel){
		JMenuItem menuItemOption = new JMenuItem(itemLabel); // creates new menu item
		if(itemLabel.equalsIgnoreCase("choose topic two") || itemLabel.equalsIgnoreCase("choose topic three") ){
			menuItemOption.setText("Choose Topic");
		}
		if(itemLabel.equalsIgnoreCase("choose genre two")){
			menuItemOption.setText("Choose Genre");
		}
		menuItemOption.addActionListener(new ActionListener (){

			@Override
			public void actionPerformed(ActionEvent e) {
				//determine which menu is selected
				updateSelectedLabel(itemLabel, newMenu, prequel);
			}	
		});
		hideDefaultMenus(menuItemOption, newMenu);
		newMenu.add(menuItemOption); // add option to menu
		return newMenu;
	}

	private void updateSelectedLabel(String itemName, JPopupMenu newMenu, ManuscriptCreator prequel){
		//used to determine which option was selected
		//sets the label text to selected option
		try{
			if(newMenu == manuscriptOptions){
				manuscriptTypeButtonLabel.setText(itemName);
				manuscriptType = itemName;
				manuscriptTypeButtonLabel.repaint();
			}
			else if(newMenu == genreOptions){
				if(labelLB.equalsIgnoreCase("genre one")){
					genreTypeButtonLabel.setText(itemName);
					genre = itemName;

				}
				else{
					genreTwoTypeButtonLabel.setText(itemName);
					if (itemName.equalsIgnoreCase("choose genre two")){
						genreTwo = null;
						genreTwoTypeButtonLabel.setText("Choose Genre");
					}else{
						genreTwo = itemName;
					}

				}
			}
			else if(newMenu == topicOptions){
				if(labelLB.equalsIgnoreCase("topic one")){
					topicTypeButtonLabel.setText(itemName);
					topic = itemName;
				}
				else if (labelLB.equalsIgnoreCase("topic two")){

					topicTwoTypeButtonLabel.setText(itemName);
					if (itemName.equalsIgnoreCase("choose topic two")){
						topicTwo = null;
						topicTwoTypeButtonLabel.setText("Choose Topic");
						topicThree = null;
						topicThreeTypeButtonLabel.setText("Choose Topic");
					}else{
						topicTwo = itemName;
					}

				}else if(labelLB.equalsIgnoreCase("topic three")){

					topicThreeTypeButtonLabel.setText(itemName);
					if (itemName.equalsIgnoreCase("choose topic three")){
						topicThree = null;
						topicThreeTypeButtonLabel.setText("Choose Topic");
					}else{
						topicThree = itemName;
					}
				}
			}
			else if(newMenu == audienceOptions){
				audienceTypeButtonLabel.setText(itemName);
				audience = itemName;
				audienceTypeButtonLabel.repaint();
			}else if(newMenu == prequelOptions){
				this.prequel = prequel;
				prequelNameButtonLabel.setText(itemName);
				prequelName = itemName;
				prequelNameButtonLabel.repaint();
				this.setTitle("New Entry to " + prequelName);
			}

		}catch(NullPointerException npe){

		}
	}

	private void setPaneSettings(JPanel thePane){
		thePane.setPreferredSize(new Dimension(350, 35));
		//thePane.setBackground(Color.gray);
	}

	private boolean checkEnteredValues(){
		//make sure nothing is empty
		if(manuscriptType == null || genre == null ||  fictionType == null || 
				topic == null){
			return false;
		}if(prequelTypePane.isAncestorOf(audienceTypeButtonLabel)){
			if(audience == null){
				return false;
			}
		}if(eduButton.isVisible()){
			if (purpose == null){
				return false;
			}
		}if(isSequel){
			if(prequelName == null){
				return false;
			}
		}if(purposePane.isAncestorOf(eduButton) == false){
			entButton.setSelected(true);
			purpose = "Entertainment";
			return true;
		}
		else{
			return true;
		}
	}

	private void closeWindow(){
		//setting values to nothing
		manuscriptTypeButtonLabel.setText("Choose Type");
		topicTypeButtonLabel.setText("Choose Topic");
		topicTwoTypeButtonLabel.setText("Choose Topic");
		topicThreeTypeButtonLabel.setText("Choose Topic");
		genreTypeButtonLabel.setText("Choose Genre");
		genreTwoTypeButtonLabel.setText("Choose Genre");
		audienceTypeButtonLabel.setText("Choose Audience");
		prequelNameButtonLabel.setText("Prequel Name");
		manuscriptType = null;
		genre = null;
		genre = null;
		audience = null;
		fictionType = null;
		topic = null;
		topicTwo = null;
		topicThree = null;
		prequelName = null;
		purpose  = null;
		manuscriptPurpose.clearSelection();
		manuscriptFiction.clearSelection();
		isSequel = false;
		prequel = null;
		prequelTypePane.remove(prequelNameButtonLabel);
	}

	private void setWindowListenerBehaviour(){
		this.addWindowListener(new WindowListener(){

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				if(isSequel){
					prequelTypePane.add(prequelNameButtonLabel);
					//prequelNameButtonLabel.setVisible(true);
				}
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent arg0) {
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

			}

		});
	}
}
