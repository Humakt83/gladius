package game;
import java.io.Serializable;
import java.util.*;
public class Spellshop implements ShopInterface, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4680531697500241304L;
	private Vector<Spell> damagespells;
    private Vector<Spell> healingspells;
    public Spellshop(DAO d){
        damagespells = d.searchSpells(true);
        healingspells = d.searchSpells(false);
    }
	public boolean purchase(Team t, String which, Gladiator g, View v){
        int squirrels = t.getSquirrels();
        Iterator<Spell> it = damagespells.iterator();
		while(it.hasNext()){
            Spell apu = it.next();
            if(apu.getName().equals(which)){
                if(squirrels>=apu.getPrice()){
                    if(g.getSpell1()==null)g.setSpell1(apu);
                    else g.setSpell2(apu);
                    t.setSquirrels((squirrels-apu.getPrice()));
                    v.addText("Thanks for purchasing o noble customer.");
                    return true;
                }
            }
        }
        Iterator<Spell> it2 = healingspells.iterator();
		while(it2.hasNext()){
            Spell apu = it2.next();
            if(apu.getName().equals(which)){
                if(squirrels>=apu.getPrice()){
                    if(g.getSpell1()==null)g.setSpell1(apu);
                    else g.setSpell2(apu);
                    t.setSquirrels((squirrels-apu.getPrice()));
                    v.addText("Thanks for purchasing o noble customer.");
                    return true;
                }
            }
        }
		return false;
	}
    public Spell getSpell(String n){
        Iterator<Spell> it = damagespells.iterator();
		while(it.hasNext()){
            Spell apu = it.next();
            if(apu.getName().equals(n))return apu;
        }
        Iterator<Spell> it2 = healingspells.iterator();
		while(it2.hasNext()){
            Spell apu = it2.next();
            if(apu.getName().equals(n))return apu;
        }
		System.out.println("Why here");
        return null;
    }
    public Vector<Spell> getDamageSpells(){
        return damagespells;
    }
    public Vector<Spell> getHealingSpells(){
        return healingspells;
    }
}
