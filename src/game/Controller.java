package decker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

//import java.io.*;
/**
 * Kontrolli luokka ohjelmalle. Viw:in ActionListener luokka.
 * Yhdistää View:in Model:in. Luo myös Login, AddCards ja About ikkunoita 
 * tarpeen mukaan. Sisältää myös ohjelman tarkistusrutiinit ja asettaa virheviestit Viewiin.
 * @author Risto Salama
 *
 */
/* ActionListener class for program. Connects View to Model. Also creates Login, AddCards and About
 * windows when certain action is given.
 */
public class Controller implements ActionListener, ListSelectionListener, MouseListener{
	// Classes and variables
	private boolean online = false;
	private View kayttoliittyma;
	private Decker sovelluslogiikka;
	private Login login;
	private AddCards Cardadder;
	private AddDeck Deckadder;
	private About about;
	private Help help;
	private Calculate analysis;
	private boolean loggaus = false;
	private boolean addcardscheck = false;
	private boolean adddeckcheck = false;
	// boolean to help check if coloricon was pressed
	private boolean whitep = false;
	private boolean bluep = false;
	private boolean blackp = false;
	private boolean redp = false;
	private boolean greenp = false;
	private boolean iconlock = false;
	private final File tallennus = new File(".\\Decks");
	private ArrayList<String> lista;
	private String card = null;
	/**
	 * Alustaja Controller luokalle.
	 * @param k joka on ohjelman graafinen käyttöliittymä eli View-luokka.
	 */
	// Constructor. Creates a new Model by setting itself as Model's Controller.
	public Controller(View k) {
        kayttoliittyma = k;
        sovelluslogiikka = new Decker(this);
        kayttoliittyma.setErrorMessage("Welcome to MTG-Decker. For the first time: to properly configure database select Cards -> Add all cards.", false);
        sovelluslogiikka.play();
	}
	/**
	 * updateUI
	 * Metodi joka laittaa View:in virheilmoituksen tyhjäksi.
	 * Pysäyttää myös säikeen Model:ssa.
	 */
	// Method for setting View's error message to empty. 
	// Also stops the thread in model when this method is called.
	public void updateUI(){
		kayttoliittyma.setErrorMessage("", true);
		sovelluslogiikka.stop();
	}
	/**
	 * actionPerformed
	 * Tapahtumakuuntelija metodi, joka sisältää tapahtumankuuntelijat käyttöliittymän kaikille 
	 * toiminnoille. SEARCH, SAVE, VIEW CARD ja REMOVE tapauksissa, tekee tarkistuksen onko toiminto suoritettu
	 * oikein ja sitten välittää käyttöliittymästä tiedon MODEL:lle, joka käsittelee DAO:ta, ja palauttaa tiedot
	 * lopuksi käyttöliittymälle ja käyttäjälle. Kun lisätään kortteja, logataan tai painetaan about nappia,
	 * tämä metodi luo uuden ikkunan tapahtumaa varten.
	 * 
	 */
	public void actionPerformed(ActionEvent ae){
		if(ae.getActionCommand().equals("SELECT"))kayttoliittyma.selectAll();
		if(ae.getActionCommand().equals("FORMAT")){
			String forma = kayttoliittyma.getFormat();
			if(forma=="" || forma=="Pauper" || forma == "Tribal Wars" || forma =="Vanguard") kayttoliittyma.formatEnableDisable(true);
			else kayttoliittyma.formatEnableDisable(false);
		}
		if(ae.getActionCommand().equals("SEARCH")){
			// Empties the JComboBox list in View and also View's JTextfield and JTextArea. 
			kayttoliittyma.emptyList();
			kayttoliittyma.setName("");
			kayttoliittyma.setDeck("");
			kayttoliittyma.setSideboard("");
			kayttoliittyma.setCurrentDate("");
			kayttoliittyma.hideRating();
			// Stores selected formats and colors to strings and sets error message and starts
			// thread in model if either one is empty. 
			String format = kayttoliittyma.checkFormat();
			String color = kayttoliittyma.checkColor();
			if((color.isEmpty() && !(kayttoliittyma.getFormat() == "Prismatic")) || (format.isEmpty() || (format == "Tribal Wars") || (format == "Pauper") || (format == "Vanguard"))){
				kayttoliittyma.setErrorMessage("You must select both color and format for search to work", true);
				sovelluslogiikka.play();
			}else{ 
				//Passes the model the information of both formats and colors and then checks whether
				// ArrayList has anything in it by using iterator. If it has it passes the lista to view
				lista = sovelluslogiikka.searchDecks(format, color);
				Iterator<String> it = lista.iterator();
				if(lista == null||!it.hasNext()){
					kayttoliittyma.setErrorMessage("Search didn't find any decks using selected options", true);
					sovelluslogiikka.play();
				}else{
					kayttoliittyma.AddDekit(lista);
				}
			}
		}
		if(ae.getActionCommand().equals("dekit")){
			/* Sets the decklist JTextArea and also sets the JCheckboxes provided JCombobox's 
			 * selected field isn't null. 
			 */
			String name = kayttoliittyma.valiName();
			if(!(name == null)){
				kayttoliittyma.setName(name);
				String dekki = sovelluslogiikka.searchDekki(name);
				iconlock = true;
				kayttoliittyma.setColor(sovelluslogiikka.returnColor());
				System.out.println(sovelluslogiikka.returnFormat());
				kayttoliittyma.setFormat(sovelluslogiikka.returnFormat());
				kayttoliittyma.setDeck(dekki);
				kayttoliittyma.setSideboard(sovelluslogiikka.returnSideboard());
				kayttoliittyma.setComments(sovelluslogiikka.returnComments());
				kayttoliittyma.setDate("Date added: " + sovelluslogiikka.returnDate());
				kayttoliittyma.setRating(sovelluslogiikka.returnRating(), sovelluslogiikka.returnAmountRatings());
				iconlock = false;
			}
		}
		if(ae.getActionCommand().equals("REMOVE")){
			// Creates a new Login window unless user has already logged.
			if(!loggaus && online){
				if(!(login==null))login.close();
				login = new Login(this);
			}
			if(loggaus || !online){
				String name = kayttoliittyma.getName();
				String format = kayttoliittyma.checkFormat();
				if(!name.isEmpty()){
					boolean r = sovelluslogiikka.removeDeck(name, format);
					if(!r){
						kayttoliittyma.setErrorMessage("Program couldn't find such deck from database", true);
						sovelluslogiikka.play();
					}else{
						kayttoliittyma.setErrorMessage("Deck removed succesfully", false);
						sovelluslogiikka.play();
						kayttoliittyma.removefromDecklist(name);
					}
				}else{
					kayttoliittyma.setErrorMessage("You need to give the deck's name you want to be removed.", true);
					sovelluslogiikka.play();
				}
			}
		}
		//Saves the deck to database
		if(ae.getActionCommand().equals("SAVE")){
			// Creates a new Login window unless user has already logged.
			if(!loggaus && online){
				if(!(login==null))login.close();
				login = new Login(this);
			}
			if(loggaus || !online){
				String Name = kayttoliittyma.getName();
				if(!Name.isEmpty()){
					String Format = kayttoliittyma.checkFormat();
					String Color = kayttoliittyma.checkColor();
					//Checks whether format and color is set
					if((Color.isEmpty()) || Format.isEmpty()){
						kayttoliittyma.setErrorMessage("You must select both color and format for the deck you want to create/save", true);
						sovelluslogiikka.play();
					}else{ 
						String Decklist = kayttoliittyma.getDeck();
						//Counts whether deck in question has 60 cards. Sets error message if not. Uses
						//StringTokenizer to filter non-numerals out in order to count the numerals.
						StringTokenizer apu = new StringTokenizer(Decklist, " !%&}]`´[{$£@()?+-_,&*'=abcdefghijklmnopqrstuvwyxzäåöABCDEFGHIJKLMNOPQRSTUVWYXZYÄÖÅ\t\n\r\f");
						int amount = 0;
						while (apu.hasMoreTokens()) {
							amount = amount + Integer.parseInt(apu.nextToken());
					     }
						if(amount<60){
							kayttoliittyma.setErrorMessage("Constructed decks minimum number of cards is 60. Your deck has " +amount+" cards.", true);
							sovelluslogiikka.play();
						}else{
							//Checks whether sideboard is empty or has exactly 15 cards. Uses StringTokenizer
							//in the same way as above.
							String Sideboard = kayttoliittyma.getSideboard();
							StringTokenizer apusb = new StringTokenizer(Sideboard, " !`´%&}][{$£@()?+-_,&*'=abcdefghijklmnopqrstuvwyxzäåöABCDEFGHIJKLMNOPQRSTUVWYXZYÄÖÅ\t\n\r\f");
							int amountsb = 0;
							while (apusb.hasMoreTokens()) {
								amountsb = amountsb + Integer.parseInt(apusb.nextToken());
						    }
							if (!Sideboard.isEmpty() && amountsb != 15){
								kayttoliittyma.setErrorMessage("Sideboard must either be empty or contain exactly 15 cards.", true);
								sovelluslogiikka.play();
							}else{
								String Comments = kayttoliittyma.getComments();
								sovelluslogiikka.addDeck(Name, Format, Decklist, Color, Sideboard, Comments);
								kayttoliittyma.setErrorMessage("Deck saved successfully.", false);
								sovelluslogiikka.play();
							}
						}
						
					
					}
				}else{
					kayttoliittyma.setErrorMessage("You need to give the deck's name you want to be created/saved.", true);
					sovelluslogiikka.play();
				}
			}
		}
		/* Shows the card user wants to view, provided such name can be found from the database.
		 * 
		 */
		if(ae.getActionCommand().equals("VIEW")){
			String card = kayttoliittyma.getSelectedCard();
			String image = sovelluslogiikka.searchCard(card, kayttoliittyma.getcardlistSelection());
			if(image.isEmpty()){
				kayttoliittyma.setErrorMessage("No such card in database.", true);
				sovelluslogiikka.play();
			}
			else{
				kayttoliittyma.setCardImage(image);
			}
		}
		if(ae.getActionCommand().equals("VIEW2")){
			String card = kayttoliittyma.getSelectedCard();
			String image = sovelluslogiikka.searchAnyCard(card);
			if(image.isEmpty()){
				kayttoliittyma.setErrorMessage("No such card in database.", true);
				sovelluslogiikka.play();
			}
			else{
				kayttoliittyma.setCardImage(image);
			}
		}
		if(ae.getActionCommand().equals("LOGIN")){
			// Creates a new Login window unless user has already logged, in which case it sets error message.
			if(!loggaus){
				if(!(login==null))login.close();
				login = new Login(this);
			}
			else kayttoliittyma.setErrorMessage("Already logged in.", true);
		}
		if(ae.getActionCommand().equals("LOGOUT"))
			if(!loggaus){
				kayttoliittyma.setErrorMessage("Not logged in.", true);
			}else{
				loggaus = false;
				kayttoliittyma.setErrorMessage("Successfully logged out.", false);
			}
		//Closes program.
		if(ae.getActionCommand().equals("Exit")){
			System.exit(0);
		}
		//Creates a new about window of the program.
		if(ae.getActionCommand().equals("About")){
			if(!(about==null))about.close();
			about = new About();
		}
		if(ae.getActionCommand().equals("Help")){
			if(!(help==null))help.close();
			help = new Help();
		}
		if(ae.getActionCommand().equals("Analysis")){
			if(!(analysis==null))analysis.close();
			analysis = new Calculate();
		}
		if(ae.getActionCommand().equals("Add Cards")){
			// Creates a new Login window unless user has already logged. 
			//if(!loggaus){
				//addcardscheck = true;
				//if(!(login==null))login.close();
				//login = new Login(this);
			//}
			// Creates a new AddCards window.
			//if(loggaus){
				if(!(Cardadder==null))Cardadder.close();
				if(!(Deckadder==null))Deckadder.close();
				Cardadder = new AddCards(this);
			//}
		}
		//Opens new cardlist window when user wants list of cards.
		if(ae.getActionCommand().equals("CARDS")){
			String[] sets = sovelluslogiikka.setslist();
			String[] cards = sovelluslogiikka.cardslists(sets[0]);
			if(cards==null){
				kayttoliittyma.setErrorMessage("No card images in database", true);
				sovelluslogiikka.play();
			}else{
				kayttoliittyma.cardList(cards, sets);
				kayttoliittyma.setErrorMessage("To add card to deck mouseclick + Ctrl, to sideboard mouseclick+Alt", false);
				sovelluslogiikka.play();
			}
		}
		if(ae.getActionCommand().equals("SETS")){
			String[] cards = sovelluslogiikka.cardslists(kayttoliittyma.getcardlistSelection());
			if(cards==null){
				kayttoliittyma.setErrorMessage("No card images in selected database", true);
				sovelluslogiikka.play();
			}else{
				kayttoliittyma.updateCardlist(cards);
			}
		}
		// Closes AddCards window when user presses CANCEL-button.
		if(ae.getActionCommand().equals(JFileChooser.CANCEL_SELECTION)){
			if(!(Cardadder==null)){Cardadder.close(); Cardadder = null;
			}else{ Deckadder.close(); Deckadder = null;}
		}
		// Closes AddCards window after sending the file(s) to Model in order to save them
		// to database.
		if(ae.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)){
			if(!(Cardadder==null)){File[] Images = Cardadder.getSelectedImages();
				Cardadder.close();
				Cardadder = null;
				sovelluslogiikka.addCards(Images, null, null);
			}else{
				File deck = Deckadder.getSelectedDeck();
				String deckName = deck.getName();
				deckName = deckName.replace("_", " ");
				deckName = deckName.replace(".txt", "");
				boolean dek = false;
				if(deckName.contains(".dek")){
					dek = true;
					deckName = deckName.replace(".dek", "");
				}
				try{
					FileInputStream fr = new FileInputStream(deck.getPath());
					BufferedReader in = new BufferedReader(new InputStreamReader(fr));
					Vector<String> decklist = new Vector<String>();
					Vector<String> sb = new Vector<String>();
					Vector<String> quantitydl = new Vector<String>();
					Vector<String> quantitysb = new Vector<String>();
					String readline;
					while ((readline = in.readLine()) != null){
						if(!dek)decklist.add(readline + "\n");
						else if(readline.contains("CatID")){
							kayttoliittyma.setErrorMessage("Opening dek-file might take a while.", true);
							sovelluslogiikka.play();
							String card = readline.substring(10, 26);
							StringTokenizer apu = new StringTokenizer(card, " <>!`´%&}][{$£@()?+-_,&*'=abcdefghijklmnopqrstuvwyxzäåöABCDEFGHIJKLMNOPQRSTUVWYXZYÄÖÅ\t\n\r\f\"");
							card = "";
							while(apu.hasMoreTokens())card+=apu.nextToken();
							String quantity = readline.substring(27, 37);
							apu = new StringTokenizer(quantity, " <>!`´%&}][{$£@()?+-_,&*'=abcdefghijklmnopqrstuvwyxzäåöABCDEFGHIJKLMNOPQRSTUVWYXZYÄÖÅ\t\n\r\f\"");
							quantity="";
							while(apu.hasMoreTokens())quantity+=apu.nextToken();
							if(readline.contains("true")){ 
								quantitysb.add(quantity);
								sb.add(card);
								//sb += quantity +" " + sovelluslogiikka.searchCardbyCatID(card) + "\n";
							}
							else{
								quantitydl.add(quantity);
								decklist.add(card);
								//decklist += quantity + " " + sovelluslogiikka.searchCardbyCatID(card) + "\n";
							}
						}
					}
					fr.close();
					in.close();
					String[] deckl = (String[])decklist.toArray(new String[0]);
					String[] quantityd = (String[])quantitydl.toArray(new String[0]);
					String[] side = (String[])sb.toArray(new String[0]);
					String[] quantitys = (String[])quantitysb.toArray(new String[0]);
					if(dek)deckl = sovelluslogiikka.searchCardbyCatID(deckl);
					if(dek)side = sovelluslogiikka.searchCardbyCatID(side);
					String sideb = "";
					String decke = "";
					if(dek){
						for(int k = 0; k<deckl.length; k++)decke += quantityd[k] + " " + deckl[k] + "\n";
						for(int x = 0; x<side.length; x++)sideb += quantitys[x] + " " + side[x] + "\n";
					}else{
						for(int k = 0; k<deckl.length; k++)decke += deckl[k];
					}
					decke = decke.replace("'", "´");
					sideb = sideb.replace("'", "`");
					kayttoliittyma.setName(deckName);
					kayttoliittyma.setDeck(decke);
					kayttoliittyma.setSideboard(sideb);
					Deckadder.close();
					Deckadder = null;
				}catch(Exception e){
					System.err.println(e);
				}
			}
		}
		if(ae.getActionCommand().equals("ADDALLCARDS")){
				kayttoliittyma.setErrorMessage("Adding cards to database, please be patient.", false);
				sovelluslogiikka.play();
				sovelluslogiikka.addAll();
				kayttoliittyma.setErrorMessage("All sets from Cardpics folder are now included in database.", false);
				sovelluslogiikka.play();
		}
		if(ae.getActionCommand().equals("ADD")){
			//if(!loggaus){
				//adddeckcheck = true;
				//if(!(login==null))login.close();
				//login = new Login(this);
			//}
			// Creates a new AddCards window.
			//if(loggaus){
				if(!(Deckadder==null))Deckadder.close();
				if(!(Cardadder==null))Cardadder.close();
				Deckadder = new AddDeck(this);
			//}
		}
		if(ae.getActionCommand().equals("SAVEDECK")){
			if(kayttoliittyma.getName().isEmpty()){
				kayttoliittyma.setErrorMessage("Deck has to have a name in order to save it", true);
				sovelluslogiikka.play();
			}else{
				File tallenna = new File(kayttoliittyma.getName());
				new SaveDeck(tallenna, kayttoliittyma.getDeck(), kayttoliittyma.getSideboard());
			}
				
		}
		if(ae.getActionCommand().equals("SAVEALL")){
			if(kayttoliittyma.getName().isEmpty()){
				kayttoliittyma.setErrorMessage("You have not selected decks to save", true);
				sovelluslogiikka.play();
			}else{
				Iterator<String> it = lista.iterator();
				while(it.hasNext()){
					String name = it.next();
					String dekki = sovelluslogiikka.searchDekki(name);
					String sideboard = sovelluslogiikka.returnSideboard();
					try{
						System.out.println(name);
						FileWriter writer = new FileWriter(".\\Decks\\"+name+".txt");
						PrintWriter printer = new PrintWriter(writer);
						StringTokenizer pakk = new StringTokenizer(dekki, "\n");
						while(pakk.hasMoreTokens())printer.println(pakk.nextToken());
						printer.println();
						pakk = new StringTokenizer(sideboard, "\n");
						while(pakk.hasMoreTokens())printer.println(pakk.nextToken());
						printer.close();
					}
					catch(Exception e){
						
					}
				}
				kayttoliittyma.setErrorMessage("Decks in list were saved to: "+tallennus.getAbsolutePath(), false);
				sovelluslogiikka.play();
			}				
		}
		// Closes AddCards window after sending the file(s) to Model in order to save them
		// to database.
		/* Checks if password and username are correct, sets errormessage to login screen if not.
		 */
		if(ae.getActionCommand().equals("OK")){
			String User = login.getUser();
			char[] Pass = login.getPassword();
			boolean check = sovelluslogiikka.passCheck(User, Pass);
			if(check == true){
				loggaus = true;
				login.close();
				if(addcardscheck){
					addcardscheck = false;
					Cardadder = new AddCards(this);
				}
				if(adddeckcheck){
					adddeckcheck = false;
					Deckadder = new AddDeck(this);
				}
			}else login.error();
		}
		//Closes login screen.
		if(ae.getActionCommand().equals("CANCEL")){
			login.close();
		}
		if(!iconlock){
			if(ae.getActionCommand().equals("WHITE")){
				if(!whitep){
					whitep = true;
					kayttoliittyma.changeIcon(1, true);
				}else{
					whitep = false;
					kayttoliittyma.changeIcon(1, false);
				}
			}
			if(ae.getActionCommand().equals("BLUE")){
				if(!bluep){
					bluep = true;
					kayttoliittyma.changeIcon(2, true);
				}else{
					bluep = false;
					kayttoliittyma.changeIcon(2, false);
				}
			}
			if(ae.getActionCommand().equals("BLACK")){
				if(!blackp){
					blackp = true;
					kayttoliittyma.changeIcon(3, true);
				}else{
					blackp = false;
					kayttoliittyma.changeIcon(3, false);
				}
			}
			if(ae.getActionCommand().equals("RED")){
				if(!redp){
					redp = true;
					kayttoliittyma.changeIcon(4, true);
				}else{
					redp = false;
					kayttoliittyma.changeIcon(4, false);
				}
			}
			if(ae.getActionCommand().equals("GREEN")){
				if(!greenp){
					greenp = true;
					kayttoliittyma.changeIcon(5, true);
				}else{
					greenp = false;
					kayttoliittyma.changeIcon(5, false);
				}
			}
		}
		if(ae.getActionCommand().equals("RATE")){
			sovelluslogiikka.setRating(kayttoliittyma.getRating(), kayttoliittyma.getName(), kayttoliittyma.checkFormat());
			kayttoliittyma.setRating(sovelluslogiikka.returnRating(), sovelluslogiikka.returnAmountRatings());
		}
	}
	/**
	 * valueChanged
	 * Tapahtumakuuntelija metodi JListille, korttien listausta varten.
	 */
	public void valueChanged(ListSelectionEvent e){
		@SuppressWarnings("unchecked")
		JList<String> list = (JList<String>) e.getSource();
		if (e.getValueIsAdjusting() == false) {
			if (list.getSelectedIndex() == -1) {
			} else {
				String card = (String) list.getSelectedValue();
				String image = sovelluslogiikka.searchCard(card, kayttoliittyma.getcardlistSelection());
				if(image.isEmpty()){
					kayttoliittyma.setErrorMessage("No such card in database.", true);
					sovelluslogiikka.play();
				}
				else{
					kayttoliittyma.setCardImage(image);
				}				
			}	
		}
	}
	public void mouseClicked(MouseEvent e) {
		@SuppressWarnings("unchecked")
		JList<String> list = (JList<String>) e.getSource();
		if(e.isControlDown()){
			int amount = 1;
			if(e.isShiftDown()) amount = 4;

			if((String) list.getSelectedValue()!= null) card = (String) list.getSelectedValue();
			String dekki = kayttoliittyma.getDeck();
			if(card!=null){
				if (dekki=="" || dekki.isEmpty() || dekki == null)dekki += amount + " " + card +"\n";
				else{
					if(dekki.contains(card)){
						StringTokenizer apusb = new StringTokenizer(dekki, "\n");
						dekki = "";
						while(apusb.hasMoreTokens()){
							String apu = apusb.nextToken();
							if (apu.contains(card)){
								StringTokenizer laskeb = new StringTokenizer(apu, " !`´%&}][{$£@()?+-_,&*'=abcdefghijklmnopqrstuvwyxzäåöABCDEFGHIJKLMNOPQRSTUVWYXZYÄÖÅ\t\n\r\f");
								int amountexists = Integer.parseInt(laskeb.nextToken());
								amount = amount + amountexists;
								dekki += amount + " " + card + "\n";
							}else dekki += apu + "\n";
						}						
					}else dekki += amount + " " + card + "\n";
				}
				kayttoliittyma.setDeck(dekki);
			}
		}
		if(e.isAltDown()){
			int amount = 1;
			if(e.isShiftDown()) amount = 4;
			if((String) list.getSelectedValue()!= null) card = (String) list.getSelectedValue();
			String dekki = kayttoliittyma.getSideboard();
			if(card!=null){
				if (dekki=="" || dekki.isEmpty() || dekki == null)dekki += amount + " " + card + "\n";
				else{
					if(dekki.contains(card)){
						StringTokenizer apusb = new StringTokenizer(dekki, "\n");
						dekki = "";
						while(apusb.hasMoreTokens()){
							String apu = apusb.nextToken();
							if (apu.contains(card)){
								StringTokenizer laskeb = new StringTokenizer(apu, " !`´%&}][{$£@()?+-_,&*'=abcdefghijklmnopqrstuvwyxzäåöABCDEFGHIJKLMNOPQRSTUVWYXZYÄÖÅ\t\n\r\f");
								int amountexists = Integer.parseInt(laskeb.nextToken());
								amount = amount + amountexists;
								dekki += amount + " " + card + "\n";
							}else dekki += apu + "\n";
						}						
					}else dekki += amount + " " + card + "\n";
				}
				kayttoliittyma.setSideboard(dekki);
			}
		}
	}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
}