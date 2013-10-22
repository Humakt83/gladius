package decker;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
/**
 * DAO.java
 * P‰ivitt‰‰, lis‰‰, etsii ja poistaa kentti‰ tietokannasta. Vain Model kutsuu t‰m‰n luokan metodeja ja kaikki
 * paluuarvot annetaan Model-luokalle.
 * @author Risto Salama
 *
 */
/* Data Access Object
 * Updates, inserts, searches and removes fields from database according to parameters given by 
 * model.
 * 
 */
public class DAO {
	// Classes and variables.
	private int taulu[] = new int[2];
	private ResultSet myRs;
	private Statement myStatement;
	private Connection myCon;
	private final String filename = "Decks.mdb";
	private String database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
	private Deck deck;
	private static DAO instance = null;
	private char[] ch = { 't', 'e', 's', 't' };
	private final CharSequence cl = "classic";
	private final CharSequence s = "standard";
	private final CharSequence e = "extended";
	private final CharSequence w = "white";
	private final CharSequence b = "black";
	private final CharSequence g = "green";
	private final CharSequence u = "blue";
	private final CharSequence r = "red";
	private String Nt;
	private String login;
	private DecimalFormat ratedouble = new DecimalFormat("#0.00", new
			DecimalFormatSymbols(Locale.US));
	public static synchronized DAO getInstance(){

		if (instance == null){
	      instance = new DAO();
	    }
	    return instance;

	}
	/**
	 * Konstruktori.
	 * Asettaa ajurin tietokannalle.
	 */
	// Sets up driver for database
	public DAO() {
		try{
	    	Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	    	database += filename.trim() + ";PWD=mypass";
	    }catch(Exception e){
	      e.printStackTrace();
	    }
	    jaa();
	}
	private String jokin(char[] s){
		StringBuffer st = new StringBuffer();
		for(int i = 1; i<=s.length; i++){
			st.append(s[s.length -i]);
		}
		return st.toString();
	}
	/**
	 * searchName.
	 * Metodi jolla etsit‰‰n tietokannasta tietty‰ pakkaa.
	 * @param Name saa parametrinaan Stringin jonka nimist‰ kentt‰‰ se etsii tietokannasta. Jos lˆyt‰‰ kyseisen
	 * kent‰n, tallentaa kent‰n tiedot Deck-olioon.
	 * @return Palauttaa Deck-olion modelille.
	 */
	// Method which searches from Decks table for certain field asked by program's interface.
	// Returns Deck object.
	public Deck searchName(String Name, String forma){
		deck = null;

		try{
			String sqlSelect = "SELECT * FROM "+ forma +" WHERE Name='" + Name + "'";
			myCon = DriverManager.getConnection(database,"J‰rjestelm‰nvalvoja" , Nt);
			myStatement = myCon.createStatement();
			myRs = myStatement.executeQuery(sqlSelect);
			if (myRs.next()) {
				String rs_Name = myRs.getString("Name");
				String rs_Deck = myRs.getString("Decklist");
				String rs_Color = "";
				if(forma != "Prismatic") rs_Color = myRs.getString("Color");
				String rs_Format = forma;
				if(forma == "Pauper" || forma == "Vanguard" || forma == "Tribal Wars") rs_Format += myRs.getString("Format");
				String rs_Sideboard = myRs.getString("Sideboard");
				String rs_Date = myRs.getString("Da");
				String rs_Comments = myRs.getString("Comments");
				String rs_Rating = myRs.getString("Rating");
				String rs_Amount = myRs.getString("AmountRa");
				deck = new Deck(rs_Name, rs_Deck, rs_Format, rs_Color, rs_Sideboard, rs_Date, rs_Comments, rs_Rating, rs_Amount);
	        }
		} catch (SQLException e){
			System.out.println(e);
		}
		finally{
			try{
				if (myRs!=null) myRs.close();
				if (myStatement!=null) myStatement.close();
				if (myCon!=null)myCon.close();
			}catch(Exception e){e.printStackTrace();
			}
		}
	    return deck;
	}
	
	/**
	 * searchDecks.
	 * Etsii tietokannasta listan pakkoja annetun formaatin ja v‰rin mukaan. Kokoaa pakkojen nimet
	 * ArrayListiin.
	 * @param f on  merkkijono joka sis‰lt‰‰ annetut formaatit etsi-toiminnolle.
	 * @param c on merkkijono joka sis‰lt‰‰ annetut v‰rit etsi-toiminnolle.
	 * @return ArrayList<String>, palauttaa pakkojen nimet Model:lle k‰ytt‰en merkkijonoksi m‰‰ritelty‰
	 *  ArrayListia. 
	 */
	/* Method for searching Strings of deck names from Decks table of database.
	 * Method is given format(f) and color(c) as required attributes for search.
	 * It stores the deck names into ArrayList and returns it back to model.
	 */
	public ArrayList<Deck> searchDecks(String f, String c){
		ArrayList<Deck> Dekit = new ArrayList<Deck>();
		f = f.replace(" ", "_");
		String forma = f;
		forma = forma.replace("classic", "");
		forma = forma.replace("extended", "");
		forma = forma.replace("standard", "");
    	try{
    		myCon = DriverManager.getConnection(database, "J‰rjestelm‰nvalvoja" , Nt);
		    myStatement = myCon.createStatement();
		    if(forma == "" || forma == null || forma.isEmpty()){    	
		    	myRs = myStatement.executeQuery("SELECT * FROM " + f + "");
		    	while (myRs.next()) {
	    			String rs_Color = myRs.getString("Color");
	    			if((c.contains(b) && rs_Color.contains(b))||(c.contains(u) && rs_Color.contains(u))||(c.contains(w) && rs_Color.contains(w))||(c.contains(g)&& rs_Color.contains(g))||(c.contains(r) && rs_Color.contains(r))){
	    					String rs_Name = myRs.getString("Name");
	    					String rs_Deck = myRs.getString("Decklist");
	    					String rs_Sideboard = myRs.getString("Sideboard");
	    					String rs_Date = myRs.getString("Da");
	    					String rs_Comments = myRs.getString("Comments");
	    					String rs_Rating = myRs.getString("Rating");
	    					String rs_Amount = myRs.getString("AmountRa");
	    					Dekit.add(new Deck(rs_Name, rs_Deck, f, rs_Color, rs_Sideboard, rs_Date, rs_Comments, rs_Rating, rs_Amount));
	    				}
	    			}
	    		}else if(forma.contains("Tribal_Wars") || forma.contains("Pauper") || (forma.contains("Vanguard"))){
	    			myRs = myStatement.executeQuery("SELECT * FROM "+forma+"");
	    			while (myRs.next()) {
	    				String rs_Color = myRs.getString("Color");
	    	    		String rs_Format = myRs.getString("Format");
	    	    		/* If statements to check whether this particular Decks table field(from Resultset) 
	    	    		 * has one of the required formats and colors of the query. Adds the field to ArrayList
	    	    		 * if both if statements has been passed.
	    	    		 * Uses local final CharSequences for comparison. 
	    	    		 */
	    	    		if((f.contains(e) && rs_Format.contains(e))|| (f.contains(s) && rs_Format.contains(s))||(f.contains(cl) && rs_Format.contains(cl))){
	    	    			if((c.contains(b) && rs_Color.contains(b))||(c.contains(u) && rs_Color.contains(u))||(c.contains(w) && rs_Color.contains(w))||(c.contains(g)&& rs_Color.contains(g))||(c.contains(r) && rs_Color.contains(r))){
	    	    				String rs_Name = myRs.getString("Name");
	    	    				String rs_Deck = myRs.getString("Decklist");
	    	    				String rs_Sideboard = myRs.getString("Sideboard");
	    	    				String rs_Date = myRs.getString("Da");
	    	    				String rs_Comments = myRs.getString("Comments");
	    	    				String rs_Rating = myRs.getString("Rating");
	    	    				String rs_Amount = myRs.getString("AmountRa");
	    	    				Dekit.add(new Deck(rs_Name, rs_Deck, (forma+rs_Format), rs_Color, rs_Sideboard, rs_Date, rs_Comments, rs_Rating, rs_Amount));
	    	    			}
	    	    		}
	    	    	}
	    		}else{
	    			myRs = myStatement.executeQuery("SELECT * FROM "+forma+"");
	    			while (myRs.next()) {
	    				String rs_Color = myRs.getString("Color");
	    	    			if( forma == "Prismatic" ||(c.contains(b) && rs_Color.contains(b))||(c.contains(u) && rs_Color.contains(u))||(c.contains(w) && rs_Color.contains(w))||(c.contains(g)&& rs_Color.contains(g))||(c.contains(r) && rs_Color.contains(r))){
	    	    				String rs_Name = myRs.getString("Name");
	    	    				String rs_Deck = myRs.getString("Decklist");
	    	    				String rs_Sideboard = myRs.getString("Sideboard");
	    	    				String rs_Date = myRs.getString("Da");
	    	    				String rs_Comments = myRs.getString("Comments");
	    	    				String rs_Rating = myRs.getString("Rating");
	    	    				String rs_Amount = myRs.getString("AmountRa");
	    	    				Dekit.add(new Deck(rs_Name, rs_Deck, forma, rs_Color, rs_Sideboard, rs_Date, rs_Comments, rs_Rating, rs_Amount));
	    	    			}
	    	    		}
	    	    	}
    			
    		}
    		catch (SQLException e){
			System.out.println(e);
		}
    finally{
	try{
		//if (myRs!=null) myRs.close();
		if (myStatement!=null) myStatement.close();
		if (myCon!=null)myCon.close();
	} catch(Exception e){e.printStackTrace();
    }
    }
    return Dekit;
}
	/**
	 * removeDeck.
	 * Metodi pakkakenttien poistamista varten tietokannasta.
	 * @param name pakan nimi, jonka tietue poistetaan tietokannasta mik‰li sellaista lˆytyy
	 * @return palauttaa arvon tosi jos tietueen poisto onnistui Model:lle. Palauttaa arvon ep‰tosi jos
	 *  pakkaa ei lˆytynyt tietokannasta.
	 */
	/* Method which removes particular deck from Database by using it's unique name field.
	 * Removes true if successful. False if not, in which case field probably doesn't exist in
	 * database.
	 */
	public boolean removeDeck(String name, String forma) {
		forma = forma.replace(" ", "_");
		try{
			myCon = DriverManager.getConnection(database, "J‰rjestelm‰nvalvoja" , Nt);
		    myStatement = myCon.createStatement();
			int i = myStatement.executeUpdate("DELETE FROM "+forma+" WHERE Name='" +
		            name + "'");
			if(i>0) return true;
		}
		catch (SQLException e){
			System.out.println(e);
		}
		finally{
			try{
				if (myStatement!=null) myStatement.close();
				if (myCon!=null)myCon.close();
			} catch(Exception e){e.printStackTrace();}
		}
		return false;
	}
	public int[] setRating(int rating, String name, String forma){	
		ResultSet myRs2 = null;
		forma = forma.replace(" ", "_");
		String f = forma;
		forma = forma.replace("classic", "");
		forma = forma.replace("extended", "");
		forma = forma.replace("standard", "");
		if(forma == "" || forma == null || (forma.isEmpty())) forma = f;
		try{
			String sqlSelect = "SELECT * FROM "+ forma +" WHERE Name='" + name + "'";
			myCon = DriverManager.getConnection(database,"J‰rjestelm‰nvalvoja" , Nt);
			myStatement = myCon.createStatement();	
			myRs2 = myStatement.executeQuery(sqlSelect);
			if (myRs2.next()) {
				String rs_Amount = myRs2.getString("AmountRa");
				double rate = Double.parseDouble(myRs2.getString("Rating"));
				myStatement.executeUpdate("UPDATE " + forma + " SET AmountRa = '"+(Integer.valueOf(rs_Amount).intValue()+1)+"' WHERE Name = '"+name+"'");
				rs_Amount = ""+(Integer.valueOf(rs_Amount).intValue()+1);
				rate = ((rate*(Double.parseDouble(rs_Amount)-1)) + rating)/Double.parseDouble(rs_Amount);
				rate = Double.parseDouble(ratedouble.format(rate));
				myStatement.executeUpdate("UPDATE " + forma + " SET Rating = '"+rate+"' WHERE Name = '"+name+"'");
				taulu[0] = (int)Math.round(rate);
				taulu[1] = Integer.valueOf(rs_Amount).intValue();
	        }
		} catch (SQLException e){
			System.out.println(e);
		}
		finally{
			try{
				//if (myRs2!=null)myRs2.close();
				if (myStatement!=null)myStatement.close();
				if (myCon!=null)myCon.close();
			}catch(Exception e){e.printStackTrace();
			}
		}
		return taulu;
	}
	/**
	 * addDeck.
	 * Metodi joka tallentaa annetut arvot Decks-tietokantataulukkoon. Jos perusavain eli annettu nimi lˆytyy,
	 * t‰m‰ p‰ivitt‰‰ automaattisesti muut kent‰t. Muussa tapauksessa addDeck luo uuden tietueen pakalle.
	 * @param Name pakan nimi.
	 * @param Format pakan formaatti tai formaatit.
	 * @param Decklist pakan sis‰ltˆ.
	 * @param Color pakan v‰ri(t).
	 * @param Sideboard apupakka.
	 */
	/* Method for inserting or updating deck into database.
	 * Checks whether a given deck name already exists in database. If so, it updates the fields for that deck
	 * instead of creating a new one. 
	 */
	public void addDeck(String Name, String Format, String Decklist, String Color, String Sideboard, String Comments) {
		try{
			Format = Format.replace(" ", "_");
			String forma = Format;
			forma = forma.replace("classic", "");
			forma = forma.replace("extended", "");
			forma = forma.replace("standard", "");
			Format = Format.replace(forma, "");
			System.out.println(forma +" ja " + Format);
			DateFormat dateFormat = new SimpleDateFormat("MM.dd.yyyy");
	        Date date = new Date();
	        String today = dateFormat.format(date);
			myCon = DriverManager.getConnection(database, "J‰rjestelm‰nvalvoja" , Nt);
		    myStatement = myCon.createStatement();
		    String sqlSelect = "";
		    if(forma != "" && forma != null && !(forma.isEmpty()))sqlSelect = "SELECT * FROM "+ forma +" WHERE Name='" + Name + "'";
		    else sqlSelect = "SELECT * FROM "+ Format +" WHERE Name='" + Name + "'";
		    System.out.println(sqlSelect);
	    	myRs = myStatement.executeQuery(sqlSelect);
	    	if (!myRs.next()){
	    		if(forma == "Pauper" || forma == "Vanguard" || forma == "Tribal_Wars"){
	    			String apu = "INSERT INTO "+forma+" (Name, Decklist, Color, Sideboard, Da, Comments, Rating, AmountRa, Madeby, Format) ";
	    			apu += "VALUES('"+Name+"','"+Decklist+"','"+Color+"','"+Sideboard+"','"+today+"','"+Comments+"','0','0','"+login+"', '"+Format+"')";
	    			myStatement.executeUpdate(apu);
	    		}else if (forma != "" && !(forma.isEmpty())){
	    			String apu = "INSERT INTO "+forma+" (Name, Decklist, Color, Sideboard, Da, Comments, Rating, AmountRa, Madeby) ";
	    			apu += "VALUES('"+Name+"','"+Decklist+"','"+Color+"','"+Sideboard+"','"+today+"','"+Comments+"','0','0','"+login+"')";
	    			myStatement.executeUpdate(apu);
	    		}else{
	    			String apu = "INSERT INTO "+Format+" (Name, Decklist, Color, Sideboard, Da, Comments, Rating, AmountRa, Madeby) ";
	    			apu += "VALUES('"+Name+"','"+Decklist+"','"+Color+"','"+Sideboard+"','"+today+"','"+Comments+"','0','0','"+login+"')";
	    			myStatement.executeUpdate(apu);
	    		}
	    	}
	    	else{
	    		//if(myRs.getString("Madeby").compareTo(login)==0){
	    			if (forma != ""){
	    				if(forma == "Pauper" || forma == "Vanguard" || forma == "Tribal Wars")myStatement.executeUpdate("UPDATE " + forma + " SET Format = '"+Format+"' WHERE Name = '"+Name+"'");
	    				myStatement.executeUpdate("UPDATE " + forma + " SET Decklist ='"+Decklist+"' WHERE Name = '"+Name+"'");
	    				myStatement.executeUpdate("UPDATE " + forma + " SET Color ='"+Color+"' WHERE Name = '"+Name+"'");
	    				myStatement.executeUpdate("UPDATE " + forma + " SET Sideboard ='"+Sideboard+"' WHERE Name = '"+Name+"'");
	    				myStatement.executeUpdate("UPDATE " + forma + " SET Comments ='"+Comments+"' WHERE Name = '"+Name+"'");
	    				myStatement.executeUpdate("UPDATE " + forma + " SET Da ='"+today+"' WHERE Name = '"+Name+"'");
	    			}else{
	    				myStatement.executeUpdate("UPDATE " + Format + " SET Decklist ='"+Decklist+"' WHERE Name = '"+Name+"'");
	    				myStatement.executeUpdate("UPDATE " + Format + " SET Color ='"+Color+"' WHERE Name = '"+Name+"'");
	    				myStatement.executeUpdate("UPDATE " + Format + " SET Sideboard ='"+Sideboard+"' WHERE Name = '"+Name+"'");
	    				myStatement.executeUpdate("UPDATE " + Format + " SET Comments ='"+Comments+"' WHERE Name = '"+Name+"'");
	    				myStatement.executeUpdate("UPDATE " + Format + " SET Da ='"+today+"' WHERE Name = '"+Name+"'");
	    			}
	    		//}
	    	}
		}
		catch (SQLException e){
			System.out.println(e);
		}
		finally{
			try{
				//if (myRs!=null) myRs.close();
				if (myStatement!=null) myStatement.close();
				if (myCon!=null)myCon.close();
			} catch(Exception e){e.printStackTrace();}
		}
	}
	/**
	 * checkUser.
	 * Metodi jota k‰ytet‰‰n kun k‰ytt‰j‰ kirjautuu sis‰‰n. Tarkistaa k‰ytt‰j‰tunnuksen ja salasanan
	 * oikeellisuuden
	 * @param user k‰ytt‰j‰tunnus. Login tietueen perusavain jonka mukaan checkUser etsii salasana-k‰ytt‰j‰ paria.
	 * @param pwd k‰ytt‰j‰n syˆtt‰m‰ salasana.
	 * @return palauttaa Model:lle arvon tosi jos annettu k‰ytt‰j‰ ja salasana t‰sm‰siv‰t. Muuten palauttaa
	 *  ep‰toden.
	 */
	/*	Method for checking if the user and password are in the same field of Login database.
	 *  User is basic key here. Returns true if password and user matches. otherwise it creates false.
	 */
	public boolean checkUser(String user, char[] pwd){
		try{
			myCon = DriverManager.getConnection(database, "J‰rjestelm‰nvalvoja" , Nt);
			myStatement = myCon.createStatement();
			String sqlSelect = "SELECT * FROM Login WHERE User='" + user + "'";
			ResultSet myRs = myStatement.executeQuery(sqlSelect);
			if (myRs.next()) {
				//If user is found in database this converts char table of password into a string
				//which is then compared to actual password of the said user.
				String pass = "";
				for(int i = 0; i<pwd.length; i++){
					pass = pass + pwd[i];
				}
		    	String rs_pwd = myRs.getString("Password");
		    	if(rs_pwd.compareTo(pass)==0){
		    		login = user;
		    		return true;
		    	}
			}
		}
		catch (SQLException e){
			System.out.println(e);
		}
		finally{
			try{
				//if (myRs!=null) myRs.close();
				if (myStatement!=null) myStatement.close();
				if (myCon!=null)myCon.close();
			} catch(Exception e){e.printStackTrace();}
		}
		return false;
	}
	/**
	 * getCard.
	 * Toiminto jolla palautetaan tietokannasta kortin hakemistopolku jotta sen kuva voidaan n‰ytt‰‰
	 * sovelluksessa.
	 * @param CardName perusavain jolla etsit‰‰n annetun kortin tietuetta.
	 * @return String palauttaa kortin hakemistopolun Model:lle.
	 */
	/* Method for getting given card's location from Cards table.
	 * Returns it to model as a String.
	 */
	public String getCard(String CardName, String SetName){ 
		String path = "";
		try{
	    	  String sqlSelect = "SELECT * FROM "+SetName+" WHERE Card='" + CardName + "'";
	    	  myCon = DriverManager.getConnection(database, "J‰rjestelm‰nvalvoja" , Nt);
		      myStatement = myCon.createStatement();	
		      myRs = myStatement.executeQuery(sqlSelect);
		      if (myRs.next()) {
		    	  path= myRs.getString("File");
	        }

	      } catch (SQLException e){
				System.out.println(e);
			}
	    finally{
	    	try{
	    		if (myRs!=null) myRs.close();
	    		if (myStatement!=null) myStatement.close();
	    		if (myCon!=null)myCon.close();
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    }
	    return path;
	  
	}
	public String getAnyCard(String CardName){ 
		String path = "";
		ArrayList<String> sets = this.setList();
		String[] searchsets = new String[sets.size()];
		int i = 0;
		for (Iterator<String> it = sets.iterator (); it.hasNext (); i++) {
			searchsets[i] = it.next();
		}
		try{
			for (int j=0; j<searchsets.length; j++) {
				String sqlSelect = "SELECT * FROM "+searchsets[j]+" WHERE Card='" + CardName + "'";
				myCon = DriverManager.getConnection(database, "J‰rjestelm‰nvalvoja" , Nt);
				myStatement = myCon.createStatement();	
				myRs = myStatement.executeQuery(sqlSelect);
		      		if (myRs.next()) {
		      			path= myRs.getString("File");
		      			break;
		      	}
			}
	      } catch (SQLException e){
				System.out.println(e);
			}
	    finally{
	    	try{
	    		if (myRs!=null) myRs.close();
	    		if (myStatement!=null) myStatement.close();
	    		if (myCon!=null)myCon.close();
	    	}catch(Exception e){e.printStackTrace();
	    	}
	    }
	    return path;
	}
	public String[] getCardbyCatID(String[] ID){ 
		String[] card = ID;
		ArrayList<String> sets = this.setList();
		String[] searchsets = new String[sets.size()];
		int i = 0;
		for (Iterator<String> it = sets.iterator (); it.hasNext (); i++) {
			searchsets[i] = it.next();
		}
		try{
			myCon = DriverManager.getConnection(database, "J‰rjestelm‰nvalvoja" , Nt);
			myStatement = myCon.createStatement();	
			for (int j=0; j<searchsets.length; j++) {
				for(i=0; i<card.length; i++){
					String sqlSelect = "SELECT * FROM "+searchsets[j]+" WHERE CatID='" + ID[i] + "'";
					myRs = myStatement.executeQuery(sqlSelect);
		      			if (myRs.next()) {
		      				card[i]= myRs.getString("Card");
		      			}
		      	}
			}
	      } catch (SQLException e){
				System.out.println(e);
			}
	    finally{
	    	try{
	    		//if (myRs!=null) myRs.close();
	    		if (myStatement!=null) myStatement.close();
	    		if (myCon!=null)myCon.close();
	    	}catch(Exception e){e.printStackTrace();
	    	}
	    }
	    return card;
	}
	/**
	 * cardlist
	 * Metodi joka etsii tietokannasta korttien nimi‰ ja tallentaa ne ArrayListiin.
	 * @return cards palauttaa Model:lle luettelon kaikista korteista.
	 */
	public ArrayList<String> cardlist(String setName){
		ArrayList<String> cards = new ArrayList<String>();
		try{
	    	  String sqlSelect = "SELECT * FROM "+setName+"";
	    	  myCon = DriverManager.getConnection(database, "J‰rjestelm‰nvalvoja" , Nt);
		      myStatement = myCon.createStatement();	
		      myRs = myStatement.executeQuery(sqlSelect);
		      while (myRs.next()) {
		    	  cards.add(myRs.getString("Card"));
	        }

	      } catch (SQLException e){
				System.out.println(e);
			}
	    finally{
	    	try{
	    		if (myRs!=null) myRs.close();
	    		if (myStatement!=null) myStatement.close();
	    		if (myCon!=null)myCon.close();
	    	}catch(Exception e){e.printStackTrace();
	    	}
	    }
	    return cards;
	  
	}
	/**
	 * addCards.
	 * Metodi jolla tietokantaan lis‰t‰‰n yksi tai useampi kortti ja niiden hakemistopolut.
	 * @param CardPath korttien hakemistopolut taulukossa.
	 * @param CardName korttien nimet taulukossa.
	 */
	/* Method for adding multiple fields to Cards table. Sets up both locations and names of the cards.
	 *
	 */
	public void addCards(String[] CardPath, String[] CardName, String Parent, String[] CatID, String[] Names){
		try{
			myCon = DriverManager.getConnection(database, "J‰rjestelm‰nvalvoja" , Nt);
		    myStatement = myCon.createStatement();
	    	myStatement.executeUpdate("CREATE TABLE "+Parent+" (Card Text, File Text, CatID text, PRIMARY KEY(Card))");
		}catch (SQLException e){
			System.out.println(e);
		}
		finally{
	    	try{
	    		//if (myRs!=null) myRs.close();
	    		if (myStatement!=null) myStatement.close();
	    		if (myCon!=null)myCon.close();
	    	}catch(Exception e){e.printStackTrace();
	    	}
	     }
		try{	
			myCon = DriverManager.getConnection(database, "J‰rjestelm‰nvalvoja" , Nt);
		    myStatement = myCon.createStatement();
		    String sqlSelect = "SELECT * FROM Sets WHERE SetName='" + Parent + "'";
		    myRs = myStatement.executeQuery(sqlSelect);
	    	if(!myRs.next()){
	    		myStatement.executeUpdate("INSERT INTO Sets VALUES('"+Parent+"')");
	    	}
		    for(int i = 0; i<CardName.length;i++){
	    		sqlSelect = "SELECT * FROM "+Parent+" WHERE Card='" + CardName[i] + "'";
		    	myRs = myStatement.executeQuery(sqlSelect);
	    		if (!myRs.next())myStatement.executeUpdate("INSERT INTO "+Parent+" (Card, File) VALUES('"+CardName[i]+"','"+CardPath[i]+"')");
	    		else myStatement.executeUpdate("UPDATE "+Parent+" SET File = '"+CardPath[i]+"' WHERE Card = '"+CardName[i]+"'");
	        }
		    if(!(CatID == null || CatID.length!=CardName.length)){
		    	for(int j = 0; j<CardName.length;j++){
		    		sqlSelect = "SELECT * FROM "+Parent+" WHERE Card='" + Names[j] + "'";
			    	myRs = myStatement.executeQuery(sqlSelect);
		    		if (myRs.next())myStatement.executeUpdate("UPDATE "+Parent+" SET CatID = '"+CatID[j]+"' WHERE Card = '"+Names[j]+"'");
		    	}
		    }
	      }catch (SQLException e){
				System.out.println(e);
	      }
	    finally{
	    	try{
	    		//if (myRs!=null) myRs.close();
	    		if (myStatement!=null) myStatement.close();
	    		if (myCon!=null)myCon.close();
	    	}catch(Exception e){e.printStackTrace();
	    	}
	     }
	  
	}
	public void deleteSets(){
		ArrayList<String> sets = this.setList();
		String[] removesets = new String[sets.size()];
		int i = 0;
		for (Iterator<String> it = sets.iterator (); it.hasNext (); i++) {
			removesets[i] = it.next();
		}
		try{
			myCon = DriverManager.getConnection(database, "J‰rjestelm‰nvalvoja" , Nt);
		    myStatement = myCon.createStatement();
		    for (int j=0; j<removesets.length; j++) {
		    	myStatement.executeUpdate("DROP TABLE "+ removesets[j]+"");
		    	myStatement.executeUpdate("DELETE FROM Sets WHERE SetName='"+removesets[j]+"'");
	        }

	      } catch (SQLException e){
				System.out.println(e);
			}
	    finally{
	    	try{
	    		//if (myRs!=null) myRs.close();
	    		if (myStatement!=null) myStatement.close();
	    		if (myCon!=null)myCon.close();
	    	}catch(Exception e){e.printStackTrace();
	    	}
	    }
	}
	public ArrayList<String> setList(){
		ArrayList<String> sets = new ArrayList<String>();
		try{
	    	  String sqlSelect = "SELECT * FROM Sets";
	    	  myCon = DriverManager.getConnection(database, "J‰rjestelm‰nvalvoja" , Nt);
		      myStatement = myCon.createStatement();	
		      myRs = myStatement.executeQuery(sqlSelect);
		      while (myRs.next()) {
		    	  sets.add(myRs.getString("SetName"));
	        }

	      } catch (SQLException e){
				System.out.println(e);
			}
	    finally{
	    	try{
	    		if (myRs!=null) myRs.close();
	    		if (myStatement!=null) myStatement.close();
	    		if (myCon!=null)myCon.close();
	    	}catch(Exception e){e.printStackTrace();
	    	}
	    }
	    return sets;
	  
	}
	private void jaa(){
		Nt = jokin(ch);
	}
}