package decker;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
/**
 * Model.java
 * Luokka joka v‰litt‰‰ Controllerista k‰skyt DAO:lle ja palauttaa DAO:sta parametrej‰ kontrollerille.
 * K‰ytt‰‰ myˆs Deck-luokkaa hyv‰ksi. K‰ytt‰‰ Runnable rajapintaa s‰iett‰, joka poistaa virheilmoituksen p‰‰-
 * ikkunasta, varten.
 * @author Risto Salama
 *
 */
/*
 * Model class that connects DTO and DAO with Controller Class. Also contains a thread that sets
 * the error message of the main program to invisible when 10 seconds has passed.
 */
public class Decker implements Runnable{
		//Classes and variables
		private Deck deck;
		private ArrayList<Deck> dekit = new ArrayList<Deck>();
		private DAO a;
		private Controller c;
		private boolean stop = true;
		private long time = 0;
		private Thread t = null;
		/**
		 * Konstruktori.
		 * Asettaa t‰m‰n Controllerin ja luo DAO-olion.
		 * @param c saa parametrinaan Controllerin.
		 */
		// Constructor
		public Decker(Controller c){
			a = new DAO();
			this.c = c;
			
		}
		/**
		 * searchDecks
		 * Metodi joka v‰litt‰‰ Controllerilta saadut formaatit ja v‰rit DAO:lle, 
		 * joka hakee niiden mukaan tietokannasta
		 * pakkojen nimi‰ ja palauttaa ne ArrayListiss‰.
		 * @param f formaatit joita k‰ytet‰‰n etsimisess‰.
		 * @param c v‰rit joita k‰ytet‰‰n etsimisess‰.
		 * @return dekit palauttaa controllerille listan pakkojen nimist‰.
		 */
		// Method that uses DAO to make list of deck-names and returns the list to Controller
		public ArrayList<String> searchDecks(String f, String c){
			dekit = a.searchDecks(f,c);
			ArrayList<String> names = new ArrayList<String>();
			for (Iterator<Deck> it = dekit.iterator (); it.hasNext ();){
				names.add(it.next().getName());
			}
			return names;
		}
		/**
		 * searchDekki
		 * K‰skee DAO:ta hakemaan yhden pakan t‰ydelliset tiedot ja tallentaa saadun Deck-olion itselleen.
		 * Ja palauttaa Deck-olion pakan sis‰llˆn Controllerille joka t‰t‰ metodia alunperin kutsuikin.
		 * @param n saa pakan nimen parametrinaan.
		 * @return palauttaa pakan sis‰llˆn kontrollerille.
		 */
		// Method which returns the decklist of the given deck to controller.
		public String searchDekki(String n){
			for (int i = 0; i<dekit.size(); i++){
				if(dekit.get(i).getName().equals(n)) deck = dekit.get(i);
			}
			String j = deck.getDeck();
			return j;
		}
		/*public String searchDekkifromDatabase(String n){
			*deck = a.searchName(n);
			*String j = deck.getDeck();
			*return j;
		*}
		*/
		/**
		 * returnColor
		 * @return palauttaa pakan v‰rin Controllerille. 
		 */
		// Methods which return Color and Format of the deck from DTO to controller.
		public String returnColor(){
			 return deck.getColor();
		}
		/**
		 * returnFormat
		 * @return palauttaa pakan formaatin Controllerille.
		 */
		public String returnFormat(){
			return deck.getFormat();
		}
		/**
		 * returnSideboard
		 * @return palauttaa pakan apupakan Controllerille.
		 */
		public String returnSideboard(){
			return deck.getSb();
		}
		public String returnDate(){
			return deck.getDate();
		}
		public String returnComments(){
			return deck.getComments();
		}
		public int returnRating(){
			return deck.getRating();
		}
		public int returnAmountRatings(){
			return deck.getAmount();
		}
		/**
		 * removeDeck
		 * V‰litysmetodi pakan tietokannasta poistamista varten.
		 * @param n saa parametrinaan poistettavan pakan nimen Controllerilta, jonka t‰m‰ v‰litt‰‰ DAO:lle.
		 * @return r palauttaa DAO:lta saaman boolean arvon Controllerille. 
		 */
		/* Method that commands DAO to remove deck from database. Boolean is used to check whether the operation
		* was successful.
		*/
		public boolean removeDeck(String n, String f){
			boolean r = a.removeDeck(n, f);
			return r;				
		}
		/**
		 * addDeck
		 * V‰litysmetodi pakan tietokantaan lis‰‰mist‰ varten. Saa attribuutit Controllerilta ja v‰litt‰‰
		 * ne DAO:lle.
		 * @param Name pakan nimi.
		 * @param Format pakan formaatti.
		 * @param Decklist pakan sis‰ltˆ.
		 * @param Color pakan v‰ri.
		 * @param Sideboard pakan apupakka.
		 */
		// Method for passing the attributes of the deck for DAO to create it to database.
		public void addDeck(String Name, String Format, String Decklist, String Color, String Sideboard, String Comments){
			a.addDeck(Name, Format, Decklist, Color, Sideboard, Comments);
		}
		/**
		 * searchCard
		 * V‰litysmetodi kortin etsimist‰ varten.
		 * @param Card kortin nimi jonka se saa Controllerilta ja v‰litt‰‰ DAO:lle.
		 * @return palauttaa DAO:lta saadun kortin hakemistopolun Controllerille.
		 */
		// Method which commands DAO to search the database for image of given card name. Returns the
		// String to controller.
		public String searchCard(String Card, String SetName){
			String image = a.getCard(Card, SetName);
			image = image.replace("`", "'");
			return image;
		}
		public String searchAnyCard(String Card){
			String image = a.getAnyCard(Card);
			image = image.replace("`", "'");
			return image;
		}
		public String[] searchCardbyCatID(String[] ID){
			return a.getCardbyCatID(ID);
		}
		/**
		 * cardslists
		 * 
		 * @return arrange palauttaa Controllerille j‰rjestetyn String taulukon.
		 */
		public String[] cardslists(String setName){
			ArrayList<String> cards = a.cardlist(setName);
			Vector<String> vektori = new Vector<String>();
		for (Iterator<String> it = cards.iterator(); it.hasNext();) {
				vektori.add(it.next());
				if (vektori.lastElement().contains("Foil")) vektori.removeElement(vektori.lastElement());
			}
			String[] arrange = (String[])vektori.toArray(new String[0]);
			Arrays.sort(arrange);
			return arrange;
		}
		public String[] setslist(){
			ArrayList<String> sets = a.setList();
			String[] arrange = new String[sets.size()];
			int i = 0;
			for (Iterator<String> it = sets.iterator (); it.hasNext (); i++) {
				arrange[i] = it.next();
			}
			Arrays.sort(arrange);
			return arrange;
		}
		public void removeSets(){
			a.deleteSets();
		}
		/**
		 * addCards
		 * Metodi joka jakaa Controllerilta saamien tiedostojen hakemistopolut yhteen merkkijonoon ja nimet
		 * toiseen. Lopuksi t‰m‰ l‰hett‰‰ n‰m‰ merkkijonot DAO:lle tietokantaan lis‰‰mist‰ varten.
		 * @param Images tiedostotaulukko joka sis‰lt‰‰ korttikuvien tietoja.
		 */
		/* Method which separates given file table to two string tables which contain the name of the file
		 * and path of the file. Then it passes the string tables to DAO for adding them to database.
		 */
		public void addCards(File[] Images, String[] id, String[] cardnames){
			int h = Images.length*2;
			String[] Imagelist = new String[h];
			String[] Cardlist = new String[h];
			String[] CatIDs = new String[h];
			String[] Names = new String[h];
			boolean empt = false;
			if (cardnames == null){
				empt = true;
				//System.out.println("P‰‰stiin t‰h‰n");
			}
			String parent = Images[0].getParentFile().getName();
			//System.out.println(parent);
			for(int i = 1, j=0; i<= Images.length; i++){
				for(; j < i*2 ; j++){
					Imagelist[j] = Images[i-1].getPath();
					Imagelist[j] = Imagelist[j].replace("'","`");
					Cardlist[j] = Images[i-1].getName();
					Cardlist[j] = Cardlist[j].replace(",", "");
					Cardlist[j] = Cardlist[j].replace("_", " ");
					Cardlist[j] = Cardlist[j].replace("'", "");
					if(j%2==0){
						Cardlist[j] = Cardlist[j].replaceAll(".jpg","");
						if(!empt && (i-1)< cardnames.length ){
							Names[j] = cardnames[i-1];
							Names[j] = Names[j].replace("'", "");
							CatIDs[j] = id[i-1];
						}
					}
					else{
						Cardlist[j] = Cardlist[j].replaceAll(".jpg"," Foil");
						if(!empt && (i-1)< cardnames.length){
							Names[j] = cardnames[i-1] + " Foil";
							Names[j] = Names[j].replace("'", "");
							CatIDs[j] =  "" + (Integer.parseInt(id[i-1])+1);
						}
					}
				//j++;
				//Imagelist[j] = Images[i].getPath();
				//Imagelist[j] = Imagelist[i].replace("'","`");
				//Cardlist[j] = Images[i].getName();
				//Cardlist[j] = Cardlist[i].replace("_", " ");
				//Cardlist[j] = Cardlist[i].replace("'", "`");
				//Cardlist[j] = Cardlist[i].replaceAll(".jpg"," Foil");
				}
			}
			//System.out.println("T‰nne myˆs");
			a.addCards(Imagelist, Cardlist, parent, CatIDs, Names);
		}
		/**
		 * play
		 * Metodi joka lopettaa vanhan s‰ikeen juoksemasta ja aloittaa uuden. Kutsutaan Controllerista.
		 * Viewin virheilmoituksen poistoa varten.
		 */
		// Method which stops old thread from running and starts a new one.
		public void play(){
			stop();
			time = System.currentTimeMillis();
			if (t==null){
	            t = new Thread(this);
	            stop = false;
	            t.start();
	        }

		}
		/**
		 * run
		 * Kutsutaan yksinomaan luokan omasta play-metodista. Kun noin 10 sekunttia on kulunut
		 * K‰skee Controlleria p‰ivitt‰m‰‰n View-luokan virheilmoituksen tyhj‰ksi.
		 */
		// Run method for thread. Tells Controller when 10 seconds has passed.
		public void run(){
		    	while(!stop){
		    		if (time + 10000 < System.currentTimeMillis()){
		    			c.updateUI();
		    		}
		    		try{ 
		    			Thread.sleep(1000);
		    		}
		    		catch(InterruptedException e){
		    	    }
		    	}
		    }
		/**
		 * stop.
		 * Toiminto joka pys‰ytt‰‰ s‰ikeen. Kutsutaan vain luokan omasta play-metodista.
		 */
		 /* Stops the thread from running, used when new error message is set or if the 10 seconds has passed
		  * since the last error message.
		  */
		 public void stop(){
			 stop = true;
			 t = null;
		 }
		 /**
		  * passCheck
		  * V‰lismetodi kirjautumista varten. Saa parametrit controllerilta ja v‰litt‰‰ ne DAO:lle.
		  * @param user k‰ytt‰j‰tunnus.
		  * @param pwd salasana.
		  * @return check palauttaa Controllerille booleanin joka saatiin DAO:lta. Booleanin avulla m‰‰ritell‰‰n
		  *  onnistuiko kirjautuminen vai ei.
		  */
		 // Passes user and password combination to the DAO. And tells the controller whether operation
		 // was successful or not.
		 public boolean passCheck(String user, char[] pwd){
			 boolean check = a.checkUser(user, pwd);
			 return check;
		 }
		 public void setRating(int rating, String name, String forma){
			 int taulu[] = new int[2];
			 taulu = a.setRating(rating, name, forma);
			 deck.setRating(taulu[0]);
			 deck.setAmount(taulu[1]);
		 }
		 public void addAll(){
				this.removeSets();
				File folder = new File(".\\Cardpics");
				File[] listOfFiles = folder.listFiles();
				Vector<File> cards;
				Vector<String> ids;
				Vector<String> cardnames;
				String[] cat = null;
				String[] names = null;
				for (int i=0; i < listOfFiles.length; i++){
					if(listOfFiles[i].isDirectory()) {
						File[] listDir = listOfFiles[i].listFiles();
						cards = new Vector<File>();
						for (int j=0; j< listDir.length; j++){
							String name = listDir[j].getName();
							if(name.contains(".jpg") && listDir[j].isFile()){
								cards.add(listDir[j]);
							}else if(name.contains(".dek") && listDir[j].isFile()){
								try{
									ids = new Vector<String>();
									cardnames = new Vector<String>();
									FileInputStream fr = new FileInputStream(listDir[j].getPath());
									BufferedReader in = new BufferedReader(new InputStreamReader(fr));
									String readline, separator;
									while ((readline = in.readLine()) != null){
										if(readline.toCharArray().length>8){
											separator = readline.substring(0, 8);
											StringTokenizer apu = new StringTokenizer(separator, " <>!`¥%&}][{$£@()?+-_,&*'=abcdefghijklmnopqrstuvwyxz‰ÂˆABCDEFGHIJKLMNOPQRSTUVWYXZYƒ÷≈\t\n\r\f\"");
											separator="";
											while(apu.hasMoreTokens())separator+=apu.nextToken();
											ids.add(separator);
											separator = readline.substring(12, readline.length()-3);
											if(separator.charAt(0)=='_'){
												separator = separator.replaceFirst("_", "");
											}
											separator = separator.replace("_", " ");
											apu = new StringTokenizer(separator, "<>!`¥%&}][{$£@()?+-_,&*=\"");
											separator="";
											while(apu.hasMoreTokens())separator+=apu.nextToken();
											cardnames.add(separator);
										}
									}
									cat = (String[])ids.toArray(new String[0]);
									names = (String[])cardnames.toArray(new String[0]);
									fr.close();
									in.close();
								}catch(Exception e){
									System.err.println(e);
								}
							}
						}
						File[] images = (File[])cards.toArray(new File[0]);
						this.addCards(images, cat, names);
						cards = null;
						cat = null;
						names = null;
						images = null;
						ids = null;
						cardnames = null;
					}
				}
		 }
}