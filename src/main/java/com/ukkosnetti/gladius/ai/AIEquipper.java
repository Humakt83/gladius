package com.ukkosnetti.gladius.ai;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.ukkosnetti.gladius.concept.Team;
import com.ukkosnetti.gladius.gladiator.Gladiator;
import com.ukkosnetti.gladius.item.Armor;
import com.ukkosnetti.gladius.item.MeleeWeapon;
import com.ukkosnetti.gladius.item.MeleeWeaponType;
import com.ukkosnetti.gladius.item.Purchasable;
import com.ukkosnetti.gladius.item.RangedWeapon;
import com.ukkosnetti.gladius.item.RangedWeaponType;
import com.ukkosnetti.gladius.item.Spell;

public class AIEquipper {

	private static final int RANGED_THRESHOLD = 30;
	private static final int SPELL_THRESHOLD = 15;
	private List<MeleeWeapon> spears;
	private List<MeleeWeapon> swords;
	private List<MeleeWeapon> axes;
	private List<MeleeWeapon> hammers;
	private List<RangedWeapon> crossbows;
	private List<RangedWeapon> bows;
	private List<Armor> armors;
	private List<Spell> healingSpells;
	private List<Spell> damageSpells;

	public AIEquipper() {
		init();
	}

	private void init() {
		armors = sortCollectionByPrice(Armor.getArmors());
		healingSpells = sortCollectionByPrice(Spell.getHealingSpells());
		damageSpells = sortCollectionByPrice(Spell.getDamageSpells());
		List<RangedWeapon> rangedWeapons = RangedWeapon.getRangedWeapons();
		crossbows = sortCollectionByPrice(filterRangedWeaponsByType(rangedWeapons, RangedWeaponType.CROSSBOW));
		bows = sortCollectionByPrice(filterRangedWeaponsByType(rangedWeapons, RangedWeaponType.BOW));
		List<MeleeWeapon> meleeWeapons = MeleeWeapon.getMeleeWeapons();
		spears = sortCollectionByPrice(filterMeleeWeaponsByType(meleeWeapons, MeleeWeaponType.SPEAR));
		swords = sortCollectionByPrice(filterMeleeWeaponsByType(meleeWeapons, MeleeWeaponType.SWORD));
		axes = sortCollectionByPrice(filterMeleeWeaponsByType(meleeWeapons, MeleeWeaponType.AXE));
		hammers = sortCollectionByPrice(filterMeleeWeaponsByType(meleeWeapons, MeleeWeaponType.HAMMER));
	}

	public void equipTeam(Team team) {
		for (Gladiator gladiator : team.getGladiators()) {
			equipGladiator(gladiator, team.getLeague());
		}
	}

	private void equipGladiator(Gladiator gladiator, final int league) {
		gladiator.setArmor(getItemAccordingToLeague(armors, league));
		// equipSpells(gladiator, league);
		equipRangedWeapon(gladiator, league);
		equipMeleeWeapon(gladiator, league);
	}

	private void equipMeleeWeapon(Gladiator gladiator, final int league) {
		MeleeWeaponType type = determineMeleeWeaponTypeGladiatorIsMostProficientWith(gladiator);
		switch (type) {
		case SPEAR:
			gladiator.setMelee(getItemAccordingToLeague(spears, league));
			break;
		case SWORD:
			gladiator.setMelee(getItemAccordingToLeague(swords, league));
			break;
		case AXE:
			gladiator.setMelee(getItemAccordingToLeague(axes, league));
			break;
		case HAMMER:
			gladiator.setMelee(getItemAccordingToLeague(hammers, league));
			break;
		}
	}

	private MeleeWeaponType determineMeleeWeaponTypeGladiatorIsMostProficientWith(Gladiator gladiator) {
		final int spearSkill = gladiator.getSpearskillAtt() + gladiator.getSpearskillDef();
		final int swordSkill = gladiator.getSwordskillAtt() + gladiator.getSwordskillDef();
		final int axeSkill = gladiator.getAxeskillAtt() + gladiator.getAxeskillDef();
		final int hammerSkill = gladiator.getHammerskillAtt() + gladiator.getHammerskillDef();
		MeleeWeaponType type = spearSkill < swordSkill ? MeleeWeaponType.SWORD : MeleeWeaponType.SPEAR;
		type = spearSkill < axeSkill && swordSkill < axeSkill ? MeleeWeaponType.AXE : type;
		return spearSkill < hammerSkill && swordSkill < hammerSkill && axeSkill < hammerSkill ? MeleeWeaponType.HAMMER : type;
	}

	private void equipRangedWeapon(Gladiator gladiator, int league) {
		if (gladiator.getBowskill() >= RANGED_THRESHOLD && gladiator.getCrossbowskill() <= gladiator.getBowskill()) {
			gladiator.setRanged(getItemAccordingToLeague(bows, league));
		} else if (gladiator.getCrossbowskill() >= RANGED_THRESHOLD) {
			gladiator.setRanged(getItemAccordingToLeague(crossbows, league));
		}

	}

	private void equipSpells(Gladiator gladiator, int league) {
		if (gladiator.getMaxmana() >= SPELL_THRESHOLD) {
			gladiator.setSpell1(getItemAccordingToLeague(damageSpells, league));
			gladiator.setSpell2(getItemAccordingToLeague(healingSpells, league));
		}
	}

	@SuppressWarnings("unchecked")
	private <T> T getItemAccordingToLeague(List<? extends Purchasable> items, final int league) {
		switch (league) {
		case 1:
			return (T) Iterables.getLast(items);
		case 2:
			if (items.size() > 2)
				return (T) items.get(items.size() - 2);
		case 3:
			if (items.size() > 1)
				return (T) items.get(1);
		case 4:
		default:
			return (T) items.get(0);
		}
	}

	private List<RangedWeapon> filterRangedWeaponsByType(List<RangedWeapon> rangeds, final RangedWeaponType type) {
		return new ArrayList<>(Collections2.filter(rangeds, new Predicate<RangedWeapon>() {

			@Override
			public boolean apply(RangedWeapon input) {
				return input.getWeaponType().equals(type);
			}
		}));
	}

	private List<MeleeWeapon> filterMeleeWeaponsByType(List<MeleeWeapon> melees, final MeleeWeaponType type) {
		return new ArrayList<>(Collections2.filter(melees, new Predicate<MeleeWeapon>() {

			@Override
			public boolean apply(MeleeWeapon input) {
				return input.getWeaponType().equals(type);
			}
		}));
	}

	@SuppressWarnings("unchecked")
	private <T> List<T> sortCollectionByPrice(List<? extends Purchasable> items) {
		items.sort(new Comparator<Purchasable>() {

			@Override
			public int compare(Purchasable o1, Purchasable o2) {
				return o1.getPrice() - o2.getPrice();
			}
		});
		return (List<T>) items;
	}
}
