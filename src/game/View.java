package decker;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.TextAction;
import javax.swing.undo.UndoManager;
/**
 * View.java
 * MTG-Decker sovelluksen pääikkuna, joka sisältää sovelluksen keskeiset komponentit.
 * @author Risto Salama
 *
 */
/* Graphical Interface for the MTG-Decker. Main window of the program.
 * Contains search, save and remove JButtons for decks. And view JButton for viewing card images.
 * Also has JMenuBar which contains Login, Add Cards, Save Deck, Remove Deck, View Card,
 * Help and About JMenuItems.
 */
public class View extends JFrame{
	static final long serialVersionUID = 1;
	private int focus = 0;
	private final Date da = new Date();
	private final DateFormat dateFormat = new SimpleDateFormat("MM.dd.yyyy");
    private final String today = dateFormat.format(da);
    private URL urlrate = View.class.getResource("rate.jpg");
    private URL urlnorate = View.class.getResource("norate.jpg");
	//Background icons for main window and JButtons
	private ImageIcon icon = new ImageIcon("MtGBG.jpg");
	private ImageIcon bicon = new ImageIcon("MtGButton.jpg");
	//Default card image for the program
	private ImageIcon cardpic = new ImageIcon("cardback.jpg");
	private ImageIcon w = new ImageIcon("white.png");
	private ImageIcon b = new ImageIcon("black.png");
	private ImageIcon u = new ImageIcon("blue.png");
	private ImageIcon g = new ImageIcon("green.png");
	private ImageIcon r = new ImageIcon("red.png");
	private ImageIcon wp = new ImageIcon("whitep.png");
	private ImageIcon bp = new ImageIcon("blackp.png");
	private ImageIcon up = new ImageIcon("bluep.png");
	private ImageIcon gp = new ImageIcon("greenp.png");
	private ImageIcon rp = new ImageIcon("redp.png");
	// Graphical interface components:
	private Controller kontrolleri;
	private JPanel sailio = new JPanel(){
		static final long serialVersionUID = 1;
		// paints the background for sailio-panel, which contains other panels.
		protected void paintComponent(Graphics g)
		{
			g.drawImage(icon.getImage(), 0, 0, null);
			Dimension d = getSize();
			g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
			super.paintComponent(g);
		}
	};
	private JPanel imagecontainer = new JPanel();
	private JPanel searchpan = new JPanel();
	private JPanel colorlab = new JPanel();
	private JPanel formatlab = new JPanel();
	private JPanel restofbuttons = new JPanel();
	private JPanel decksb = new JPanel();
	private JPanel deckpan = new JPanel();
	private JPanel namepan = new JPanel();
	private JButton search = new JButton("SEARCH", bicon);
	private JButton remove = new JButton("REMOVE", bicon);
	private JButton save = new JButton("SAVE", bicon);
	private JButton viewer = new JButton("VIEW", bicon);
	private JTextField name = new JTextField(10);
	private JLabel namelabel = new JLabel("Name:");
	private JLabel decklabel = new JLabel("Decklist");
	private JLabel sblabel = new JLabel("Sideboard");
	private JLabel describtionlabel = new JLabel("Description");
	private JLabel listofdecks = new JLabel("Decks:");
	private JLabel date = new JLabel("  " + today);
	private JLabel error = new JLabel();
	private JLabel imageholder = new JLabel(cardpic);	
	private JTextArea deck = new JTextArea(14,22);
	private JTextArea sideboard = new JTextArea(8,6);
	private JTextArea description = new JTextArea(8,6);
	private JCheckBox white = new JCheckBox(w);
	private JCheckBox blue = new JCheckBox(u);
	private JCheckBox black = new JCheckBox(b);
	private JCheckBox red = new JCheckBox(r);
	private JCheckBox green= new JCheckBox(g);
	private JCheckBox standard = new JCheckBox("Standard");
	private JCheckBox extended = new JCheckBox("Extended");
	private JCheckBox classic = new JCheckBox("Classic");
	private JComboBox<String> format = new JComboBox<>();
	private JComboBox<String> dekit = new JComboBox<>();
	//Items for deck rating:
	private JLabel ratingholder = new JLabel();
	private JPanel ratingpanel = new JPanel();
	private JButton rate = new JButton("RATE", bicon);
	private SpinnerNumberModel ratings = new SpinnerNumberModel(10,0,10,1);
	private JSpinner ratingspinner = new JSpinner(ratings);
	// Menu and menu items:
	private JMenuBar menubar = new JMenuBar();
	private JMenu filemenu = new JMenu("File");
	private JMenu db = new JMenu("Database");
	private JMenu help = new JMenu("Help");
	private JMenu tools = new JMenu("Tools");
	private JMenu edit = new JMenu("Edit");
	private JMenu cardsmenu = new JMenu("Cards");
	private JMenuItem addcards = new JMenuItem("Add Cards");
	private JMenuItem addallcards = new JMenuItem("Add All Cards");
	private JMenuItem viewcards = new JMenuItem("View Card", 'W');
	private JMenuItem cardlist = new JMenuItem("List of Cards");
	private JMenuItem database = new JMenuItem("Use Database of Server");
	private JMenuItem newaccount = new JMenuItem("Create New Account");
	private JMenuItem login = new JMenuItem("Login");
	private JMenuItem logout = new JMenuItem("Logout");
	private JMenuItem adddeck = new JMenuItem("Open Deck");
	private JMenuItem remover = new JMenuItem("Remove Deck", 'R');
	private JMenuItem saver = new JMenuItem("Save Deck", 'S');
	private JMenuItem savedecks = new JMenuItem("Save All");
	private JMenuItem savedeck = new JMenuItem("Save to Folder");
	private JMenuItem exit = new JMenuItem("Exit");
	private JMenuItem about = new JMenuItem("About");
	private JMenuItem helpitem = new JMenuItem("Help");
	private JMenuItem analysis = new JMenuItem("Analysis");
	private JMenuItem cut = new JMenuItem(new CutAct());
	private JMenuItem copy = new JMenuItem(new CopyAct());
	private JMenuItem paste = new JMenuItem(new PasteAct());
	private JMenuItem select = new JMenuItem("Select All");
	private Document doku = deck.getDocument();
	private Document doku1 = sideboard.getDocument();
	private Document doku2 = description.getDocument();
	private UndoManager peruutus = new UndoManager();
	private JMenuItem undo = new JMenuItem(new UndoAct());
	private JMenuItem redo = new JMenuItem(new RedoAct());
	private ButtonGroup bg = new ButtonGroup();
	//Cardlist components
	private JList<String> ListofCards;
	private JFrame apuframe;
	private JComboBox<String> setit;
	private DefaultListModel<String> Cardslist;
	/**
	 * Konstruktori.
	 * Tässä muokataan käyttöliittymäkomponenttien ominaisuuksia ja ryhmitetään ne.
	 */
	public View(){
		// Customizing JComponents
		deck.setLineWrap(true);
		deck.setSelectedTextColor(Color.WHITE);
		deck.setSelectionColor(Color.BLUE);
		sideboard.setSelectedTextColor(Color.WHITE);
		sideboard.setSelectionColor(Color.BLUE);
		description.setSelectedTextColor(Color.WHITE);
		description.setSelectionColor(Color.BLUE);
		name.setSelectedTextColor(Color.WHITE);
		name.setSelectionColor(Color.BLUE);
		deck.setAutoscrolls(true);
		description.setLineWrap(true);
		sideboard.setLineWrap(true);
		restofbuttons.setOpaque(false);
		sailio.setOpaque(false);
		decksb.setOpaque(false);
		imagecontainer.setOpaque(false);
		searchpan.setOpaque(false);
		formatlab.setOpaque(false);
		colorlab.setOpaque(false);
		deckpan.setOpaque(false);
		namepan.setOpaque(false);
		white.setOpaque(false);
		black.setOpaque(false);
		green.setOpaque(false);
		blue.setOpaque(false);
		red.setOpaque(false);
		ratingholder.setOpaque(false);
		ratingpanel.setOpaque(false);
		extended.setOpaque( false );
		classic.setOpaque( false );
		standard.setOpaque( false );
		format.setOpaque( false );
		setNappi(search, "SEARCH");
		setNappi(remove, "REMOVE");
		setNappi(save, "SAVE");
		setNappi(viewer, "VIEW");
		setNappi(rate, "RATE");
		search.setToolTipText("<html><b>Search</b> the database for decks that have the selected <u>parameters.</u></html>");
		save.setToolTipText("<html><b>Creates</b> a new deck to the database or saves the changes in case deck with the same name already exists.</html>");
		remove.setToolTipText("<html><b>Removes</b> a deck <i>(only name is required field)</i> from the database.</html>");
		viewer.setToolTipText("<html><b>View</b> picture of the card you have selected <i>(painted over with mouse)</i>.</html>");
		savedecks.setToolTipText("<html>Saves all decks from the list to computer</html>");
		rate.setToolTipText("<html>Rate the selected deck.</html>");
		database.setToolTipText("<html>Switch between <b>server</b> and <b>client</b> databases<html>");
		addallcards.setToolTipText("<html>Add all cards and sets from <i>cardpics</i> folder</html>");
		deck.setPreferredSize(new Dimension(300,10000));
		sideboard.setPreferredSize(new Dimension(300,500));
		description.setPreferredSize(new Dimension(300,500));
		dekit.setPreferredSize(new Dimension(100,20));
		Border whiteline = BorderFactory.createLineBorder(Color.white);
		colorlab.setBorder(BorderFactory.createTitledBorder(whiteline, "Color"));
		formatlab.setBorder(BorderFactory.createTitledBorder(whiteline, "Format"));
		Color whiter = new Color(255,255,255);
		colorlab.setForeground(whiter);
		error.setForeground(new Color(255,0,0));
		name.setFont(new Font("Times New Roman",Font.BOLD,12));
		namelabel.setForeground(whiter);
		decklabel.setForeground(whiter);
		sblabel.setForeground(whiter);
		describtionlabel.setForeground(whiter);
		date.setForeground(whiter);
		white.setForeground(whiter);
		black.setForeground(whiter);
		red.setForeground(whiter);
		green.setForeground(whiter);
		blue.setForeground(whiter);
		extended.setForeground(whiter);
		standard.setForeground(whiter);
		classic.setForeground(whiter);
		listofdecks.setForeground(whiter);
		viewer.setFocusable(false);
		ratingholder.setVisible(false);
		ratingpanel.setVisible(false);
		white.setPressedIcon(wp);
		blue.setPressedIcon(up);
		black.setPressedIcon(bp);
		red.setPressedIcon(rp);
		green.setPressedIcon(gp);
	    bg.add(standard);
	    bg.add(extended);
	    bg.add(classic);
		//formats
		format.addItem("");
		format.addItem("Singleton");
		format.addItem("100 Card Singleton");
		format.addItem("Pauper");
		format.addItem("Prismatic");
		format.addItem("Tribal Wars");
		format.addItem("Vanguard");
		format.addItem("Test");
		format.addItem("Alara Block");
		format.addItem("Lorwyn-Shadowmoor Block");
		format.addItem("Time Spiral Block");
		format.addItem("Ravnica Block");
		format.addItem("Kamigawa Block");
		format.addItem("Mirrodin Block");
		format.addItem("Onslaught Block");
		format.addItem("Odyssey Block");
		format.addItem("Invasion Block");
		format.addItem("Tempest Block");
		format.addItem("Mirage Block");
		format.addItem("Ice Age Block");
		// Storing components and containers to containers
		ratingspinner.setSize(new Dimension(10,5));
		ratingpanel.setLayout(new BoxLayout(ratingpanel, BoxLayout.X_AXIS));
		ratingpanel.add(ratingspinner);
		ratingpanel.add(rate);
		colorlab.add(white);
		colorlab.add(blue);
		colorlab.add(black);
		colorlab.add(red);
		colorlab.add(green);
		formatlab.add(format);
		formatlab.add(standard);
		formatlab.add(extended);
		formatlab.add(classic);
		searchpan.add(colorlab);
		searchpan.add(formatlab);
		searchpan.add(search);
		decksb.setLayout(new BoxLayout(decksb, BoxLayout.Y_AXIS));
		decksb.add(decklabel);
		decksb.add(new JScrollPane(deck));
		decksb.add(sblabel);
		decksb.add(new JScrollPane(sideboard));
		decksb.add(describtionlabel);
		decksb.add(new JScrollPane(description));
		namepan.add(namelabel);
		namepan.add(name);
		deckpan.add(listofdecks);
		deckpan.add(dekit);	
		restofbuttons.add(namepan);
		restofbuttons.add(ratingholder);
		restofbuttons.add(ratingpanel);
		restofbuttons.add(date);
		restofbuttons.add(deckpan);	
		restofbuttons.add(save);
		restofbuttons.add(remove);
		restofbuttons.add(viewer);
		restofbuttons.setLayout(new GridLayout(0,1));
		imagecontainer.add(imageholder);
		sailio.add(searchpan);
		sailio.add(imagecontainer);
		sailio.add(decksb);
		sailio.add(restofbuttons);
		sailio.add(error);
		add(sailio);
		// Forming menu group
		cardsmenu.add(viewcards);
		cardsmenu.add(cardlist);
		cardsmenu.addSeparator();
		cardsmenu.add(addcards);
		cardsmenu.add(addallcards);
		filemenu.add(adddeck);
		filemenu.add(saver);
		filemenu.add(savedeck);
		filemenu.add(savedecks);
		filemenu.add(remover);
		filemenu.addSeparator();
		filemenu.add(exit);
		db.add(database);
		db.addSeparator();
		db.add(newaccount);
		db.add(login);
		db.add(logout);
		login.setEnabled(false);
		logout.setEnabled(false);
		newaccount.setEnabled(false);
		tools.add(analysis);
		edit.add(undo);
		edit.add(redo);
		edit.addSeparator();
		edit.add(cut);
		edit.add(copy);
		edit.add(paste);
		edit.addSeparator();
		edit.add(select);
		//help.add(helpdoc);
		help.add(helpitem);
		help.add(about);
		menubar.add(filemenu);
		menubar.add(edit);
		//menubar.add(db);
		menubar.add(cardsmenu);
		menubar.add(tools);
		menubar.add(help);
		setJMenuBar(menubar);
		viewcards.setFocusable(false);
		// Making mnemonics for menu group
		viewcards.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
		cardlist.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
		remover.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
		saver.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
		analysis.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.ALT_MASK));
		adddeck.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
		undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
		redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
		select.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		//Setting attributes for main window
		pack();
		setSize(800,720);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		setResizable(false);
		setTitle( "MTG-Decker" );
		setLayout(new GridLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	/**
	 * setKontrolleri
	 * Tässä metodissa asetetaan tapahtumankuuntelijat komponenteille.
	 * @param k saa parametrinaan Controllerin joka on ohjelman tapahtumankuuntelija luokka.
	 */
	// Sets action listener for the components.
	void setKontrolleri(Controller k){
        kontrolleri = k;
        newaccount.setActionCommand("NEWACCOUNT");
        white.setActionCommand("WHITE");
        blue.setActionCommand("BLUE");
        black.setActionCommand("BLACK");
        red.setActionCommand("RED");
        green.setActionCommand("GREEN");
        savedecks.setActionCommand("SAVEALL");
        savedeck.setActionCommand("SAVEDECK");
        dekit.setActionCommand("dekit");
        login.setActionCommand("LOGIN");
        logout.setActionCommand("LOGOUT");
        saver.setActionCommand("SAVE");
        remover.setActionCommand("REMOVE");
        viewcards.setActionCommand("VIEW2");
        viewer.setActionCommand("VIEW2");
        cardlist.setActionCommand("CARDS");
        adddeck.setActionCommand("ADD");
        select.setActionCommand("SELECT");
        addallcards.setActionCommand("ADDALLCARDS");
        database.setActionCommand("DATABASE");
        format.setActionCommand("FORMAT");
        format.addActionListener(kontrolleri);
        addallcards.addActionListener(kontrolleri);
        database.addActionListener(kontrolleri);
        search.addActionListener(kontrolleri);
        remove.addActionListener(kontrolleri);
        save.addActionListener(kontrolleri);
        viewer.addActionListener(kontrolleri);
        viewcards.addActionListener(kontrolleri);
        dekit.addActionListener(kontrolleri);
        login.addActionListener(kontrolleri);
        logout.addActionListener(kontrolleri);
        exit.addActionListener(kontrolleri);
        addcards.addActionListener(kontrolleri);
        about.addActionListener(kontrolleri);
        helpitem.addActionListener(kontrolleri);
        saver.addActionListener(kontrolleri);
        remover.addActionListener(kontrolleri);
        cardlist.addActionListener(kontrolleri);
        analysis.addActionListener(kontrolleri);
        adddeck.addActionListener(kontrolleri);
        select.addActionListener(kontrolleri);
        savedecks.addActionListener(kontrolleri);
        savedeck.addActionListener(kontrolleri);
        white.addActionListener(kontrolleri);
        blue.addActionListener(kontrolleri);
        black.addActionListener(kontrolleri);
        red.addActionListener(kontrolleri);
        green.addActionListener(kontrolleri);
        rate.addActionListener(kontrolleri);
        newaccount.addActionListener(kontrolleri);
        Undous u = new Undous();
        doku.addUndoableEditListener(u);
        doku1.addUndoableEditListener(u);
        doku2.addUndoableEditListener(u);
        Focuser f = new Focuser();
        deck.addFocusListener(f);
        sideboard.addFocusListener(f);
        description.addFocusListener(f);
    }
	/**
	 * checkColor
	 * Metodi joka asettaa merkkijonoon valitut värit. Kutsutaan Controllerista.
	 * @return colors palauttaa valitut värit merkkijonona Controllerille.
	 */
	// Methods to help identify which Colors and Formats are selected.
	public String checkColor(){
		String colors = "";
		if(white.isSelected()){
			colors = colors +"white";
		}
		if(blue.isSelected()){
			colors = colors +"blue";
		}
		if(black.isSelected()){
			colors = colors +"black";
		}
		if(red.isSelected()){
			colors = colors +"red";
		}
		if(green.isSelected()){
			colors = colors + "green";
		}
		return colors;
	}	
	public void changeIcon(int s, boolean p){
		switch(s){
		case 1: 
			if(p){
				white.setIcon(wp);
				white.setPressedIcon(w);
			}else{
				white.setIcon(w);
				white.setPressedIcon(wp);
			}
			break;
		case 2: 
			if(p){
				blue.setIcon(up);
				blue.setPressedIcon(u);
			}else{
				blue.setIcon(u);
				blue.setPressedIcon(up);
			}
			break;
		case 3:
			if(p){
				black.setIcon(bp);
				black.setPressedIcon(b);
			}else{
				black.setIcon(b);
				black.setPressedIcon(bp);
			}
			break;
		case 4:
			if(p){
				red.setIcon(rp);
				red.setPressedIcon(r);
			}else{
				red.setIcon(r);
				red.setPressedIcon(rp);
			}
			break;
		case 5:
			if(p){
				green.setIcon(gp);
				green.setPressedIcon(g);
			}else{
				green.setIcon(g);
				green.setPressedIcon(gp);
			}
		break;
		}
	}
	/**
	 * checkFormat
	 * Metodi joka asettaa merkkijonoon valitut formaatit. Kutsutaan Controllerista.
	 * @return format palauttaa valitut formaatit merkkijonona Controllerille.
	 */
	public String checkFormat(){
		String forma = "";
		if(standard.isSelected()){
			forma = forma +"standard";
		}
		if(extended.isSelected()){
			forma = forma + "extended";
		}
		if(classic.isSelected()){
			forma = forma +"classic";
		}
		forma = forma + format.getSelectedItem();
		return forma;
	}
	public String getFormat(){
		return (String)format.getSelectedItem();
	}
	public void formatEnableDisable(boolean enable){
		if(enable){
			classic.setEnabled(true);
			extended.setEnabled(true);
			standard.setEnabled(true);
		}else{
			bg.clearSelection();
			//classic.setSelected(false);
			//extended.setSelected(false);
			//standard.setSelected(false);
			classic.setEnabled(false);
			extended.setEnabled(false);
			standard.setEnabled(false);
		}
	}
	/**
	 * setColor
	 * Metodi joka asettaa värin JCheckboxit valituksi tai ei valituksi, saadun parametrin mukaan.
	 * @param c saa asetettavat värit Controllerilta.
	 */
	// Sets the format(s) and color(s) of the deck in question.
	public void setColor(String c){
		if(c.contains("white"))white.setSelected(true);
		else white.setSelected(false);
		if(c.contains("blue"))blue.setSelected(true);
		else blue.setSelected(false);
		if(c.contains("black"))black.setSelected(true);
		else black.setSelected(false);
		if(c.contains("red"))red.setSelected(true);
		else red.setSelected(false);
		if(c.contains("green"))green.setSelected(true);
		else green.setSelected(false);
	}
	public void selectAll(){
		switch(focus){
		case 0: deck.grabFocus();
				deck.selectAll();
				break;
		case 1:
				sideboard.grabFocus();
				sideboard.selectAll();
				break;
		case 2:
				description.grabFocus();
				description.selectAll();
				break;
		}
		
		//sideboard.selectAll();
	}
	/**
	 * setFormat
	 * Metodi joka asettaa formaatin JCheckboxit valituksi tai ei valituksi, saadun parametrin mukaan.
	 * @param f saa asetettavat formaatit Controllerilta.
	 */
	public void setFormat(String f){
		bg.clearSelection();
		if(f.contains("standard"))standard.setSelected(true);
		if(f.contains("extended"))extended.setSelected(true);
		if(f.contains("classic"))classic.setSelected(true);
	}
	/**
	 * getName
	 * Getteri pakan nimeä varten.
	 * return String palauttaa nimen Controllerille.
	 */
	// Get- and Set-methods for deck's name, decklist and sideboard.
	public String getName(){
		String n = name.getText();
		n = n.replace("'", "´");
		return n;
	}
	/**
	 * setName
	 * Setteri pakan nimeä varten.
	 * @param x pakan nimi Controllerilta.
	 */
	public void setName(String x){
		name.setText(x);
	}
	/**
	 * getDeck
	 * Getteri pakan sisältöä varten.
	 * return String palauttaa sisällön Controllerille.
	 */
	public String getDeck(){
		String d = deck.getText();
		d = d.replace("'", "´");
		return d;
	}
	/**
	 * setDeck
	 * Setteri pakan sisältöä varten.
	 * @param x pakan sisältö Controllerilta.
	 */
	public void setDeck(String x){
		deck.setText(x);
	}
	/**
	 * getSideboard
	 * Getteri pakan apupakkaa varten.
	 * return String palauttaa apupakan Controllerille.
	 */
	public String getSideboard(){
		String sb = sideboard.getText();
		sb = sb.replace("'", "´");
		return sb;
	}
	/**
	 * setSideboard
	 * Setteri pakan apupakkaa varten.
	 * @param x pakan apupakka Controllerilta.
	 */
	public void setSideboard(String x){
		sideboard.setText(x);
	}
	public String getComments(){
		String c = description.getText();
		c = c.replace("'", "´");
		return c;
	}
	public void setComments(String x){
		description.setText(x);
	}
	public void setDate(String x){
		date.setText("  " + x);
	}
	public void setCurrentDate(String x){
		date.setText("  " +today);
	}
	/**
	 * AddDekit
	 * Laittaa pakkojen nimiä JComboBoxiin iteraattorin avulla. Tapahtuu kun etsitoiminto on onnistuneesti
	 * suoritettu.
	 * @param j pakkojen nimiä sisältävä ArrayList Controllerilta.
	 */
	// Adds deck names to JCombobox from given ArrayList using Iterator.
	public void AddDekit(ArrayList<String> j){
		dekit.removeAllItems();
		for (Iterator<String> it = j.iterator (); it.hasNext ();) {
			dekit.addItem(it.next());
		}
	}
	public void removefromDecklist(String Nam){
		dekit.removeItem(Nam);
	}
	/**
	 * emptyList
	 * Poistaa JComboBoxista kaikki pakkojen nimet, kutsutaan etsi-toiminnon alussa Controllerista.
	 */
	// Method for emptying JCombobox of all items.
	public void emptyList(){
		dekit.removeAllItems();
	}
	/**
	 * valiName
	 * Metodi jota Controller kutsuu saadakseen mikä pakka JComboBoxista on valittuna
	 * @return palauttaa Controllerille pakan nimen.
	 */
	// Return method for giving the selected deck name from JCombobox (dekit) to Controller class. 
	public String valiName(){
		String paluu = (String)dekit.getSelectedItem();
		return paluu;
	}
	/**
	 * setNappi.
	 * Muokkaa nappien ominaisuuksia. Kutsutaan vain tästä luokasta.
	 * @param s saa JButton objektin parametrinaan.
	 * @param tex saa nappiin asetettan tekstin.
	 */
	// Customizes JButtons.
	public void setNappi(JButton s, String tex){
		s.setText(tex);
		s.setForeground(new Color(255,255,255));
		s.setHorizontalTextPosition(JButton.CENTER);
		s.setVerticalTextPosition(JButton.CENTER);
		s.setContentAreaFilled(false);
		s.setBorderPainted(false);
		s.setSize(bicon.getIconWidth(),bicon.getIconHeight());
	}
	/**
	 * setErrorMessage
	 * Asettaa virheilmoitukset ja muut viestit error-jlabeliin. Kyseisen JLabelin teksti näkyy punaisena
	 * jotta käyttäjä huomaisi virheilmoituksen.
	 * @param s saa parametrinaan viestin sisällön kontrollerilta.
	 */
	// Sets the error and system messages (painted red in the program)
	public void setErrorMessage(String s, boolean virhe){
		if(virhe)error.setForeground(Color.RED);
		else error.setForeground(new Color(0,100,0));
		error.setText(s);
	}
	public void hideRating(){
		ratingholder.setVisible(false);
		ratingpanel.setVisible(false);
	}
	public void setRating(int rating, int amount){
		String sivu = "<html><table style='border-collapse: collapse'><tr>";
		for(int i = 0; i<10; i++){
			if(rating>0){
				sivu += "<td style='padding: 0; margin-top: 0; margin-bottom: 0;'><img src="+urlrate+"></td>";
				rating--;
			}else{
				sivu += "<td style='padding: 0; margin-top: 0; margin-bottom: 0;'><img src="+urlnorate+"></td>";
			}
		}
		sivu+="</tr><tr><td colspan='10'>";
		if(amount>0)sivu += "Amount of ratings: " +amount;
		else sivu+= "Not yet rated.";
		sivu += "</td></tr></table></html>";
		ratingholder.setText(sivu);
		ratingholder.setVisible(true);
		ratingpanel.setVisible(true);
	}
	public int getRating(){
		return ratings.getNumber().intValue();
	}
	/**
	 * getSelectedCard
	 * Metodi kortin kuvan näyttämistä varten. Tarkistaa onko käyttäjä maalannut nimen apupakasta vai
	 * varsinaisesta pakasta.
	 * @return selected palauttaa nimen Controllerille.
	 */
	/* Returns the selected text from JTextArea to controller.
	 * Which is ideally a proper card name.
	 */ 
	public String getSelectedCard(){
		String selected;
		if(sideboard.isFocusOwner())selected = "" + sideboard.getSelectedText();
		else selected = "" + deck.getSelectedText();
		return selected;
	}
	/**
	 * setCardImage
	 * Metodi joka vaihtaa kortin kuvaa.
	 * @param s saa parametrinaan Controllerilta kortin hakemistopolun
	 */
	// Changes the card-image in the imageholder label
	public void setCardImage(String s){
		cardpic = new ImageIcon(s);
		imageholder.setIcon(cardpic);
	}
	/**
	 * cardList
	 * Luo uuden ikkunan korttien listaamista varten.
	 * @param cards saa parametrinaan taulukon korttien nimistä Controllerilta.
	 */
	public void cardList(String[] cards, String[] setteja){
		/* Disposes apuframe if another one exists so two instances of apuframe can't
		 * coexist.
		 */
		if(apuframe != null){
			apuframe.dispose();
		}
		apuframe = new JFrame();
		JPanel apu = new JPanel();
		setit = new JComboBox<>();
		for(int j = 0; j<setteja.length; j++){
			setit.addItem(setteja[j]);
		}
		JScrollPane Cardspanel = new JScrollPane();
		Cardslist = new DefaultListModel<>();
		//Adds the card names to the list
		for(int i = 0; i<cards.length; i++){
			Cardslist.addElement(cards[i]);
		}
		ListofCards = new JList<>(Cardslist);
		ListofCards.setVisibleRowCount(25);
		ListofCards.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ListofCards.addListSelectionListener(kontrolleri);
		ListofCards.addMouseListener(kontrolleri);
		setit.setActionCommand("SETS");
	    setit.addActionListener(kontrolleri);
		Cardspanel.setWheelScrollingEnabled(true);
		Cardspanel.getViewport().setView(ListofCards);
		apu.add(setit);
		apu.add(Cardspanel);
		apuframe.add(apu);
		apuframe.setVisible(true);
		apuframe.setResizable(false);
		apuframe.setSize(setit.getWidth()+20,440);
		apuframe.setTitle("Cardlist");
		apuframe.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	//function to get chosen set from cardlist
	public String getcardlistSelection(){
		return (String)setit.getSelectedItem();
	}
	//function to update selected list
	public void updateCardlist(String[] cards){
		Cardslist.removeAllElements();
		for(int i = 0; i<cards.length; i++){
			Cardslist.addElement(cards[i]);
		}
	}
	//function to changetext of database
	public void databaseChangeText(boolean online){
		if(online){
			database.setText("Use Database in your Computer");
			login.setEnabled(true);
			logout.setEnabled(true);
			newaccount.setEnabled(true);
		}
		else{
			database.setText("Use Database of Server");
			login.setEnabled(false);
			logout.setEnabled(false);
			newaccount.setEnabled(false);
		}
	}
	class Undous implements UndoableEditListener{
		public void undoableEditHappened(UndoableEditEvent e){
			peruutus.addEdit(e.getEdit());
		}
	}
	class CutAct extends TextAction {
		static final long serialVersionUID = 1;
		public CutAct(){
			super("Cut");
		}
		public void actionPerformed(ActionEvent e){
			JTextComponent target = getTextComponent(e);
			if (target != null) target.cut();
		}
	}
	class PasteAct extends TextAction {
		static final long serialVersionUID = 1;
		public PasteAct(){
			super("Paste");
		}
		public void actionPerformed(ActionEvent e){
			JTextComponent target = getTextComponent(e);
			if (target != null) target.paste();
		}
	}
	class CopyAct extends TextAction {
		static final long serialVersionUID = 1;
		public CopyAct(){
			super("Copy");
		}
		public void actionPerformed(ActionEvent e){
			JTextComponent target = getTextComponent(e);
			if (target != null) target.copy();
		}
	}
	class UndoAct extends AbstractAction {
		static final long serialVersionUID = 1;
		public UndoAct(){
			super("Undo Typing");
		}
		public void actionPerformed(ActionEvent e){
			if(peruutus.canUndo())peruutus.undo();
		}
	}
	class RedoAct extends AbstractAction {
		static final long serialVersionUID = 1;
		public RedoAct(){
			super("Redo");
		}
		public void actionPerformed(ActionEvent e){
			if(peruutus.canRedo())peruutus.redo();
		}
	}
	class Focuser implements FocusListener{
		public void focusGained(FocusEvent e){
			if(e.getSource()==deck){
				focus = 0;
			}else
				if(e.getSource()==sideboard){
					focus = 1;
				}else focus = 2;
			
		}
		public void focusLost(FocusEvent e){
			
		}
	}
}
