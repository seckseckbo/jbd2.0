/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2022 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hex;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.CloakOfShadows;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfFuror;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfTenacity;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class Gauntlet extends MeleeWeapon {
	
	{
		image = ItemSpriteSheet.GAUNTLETS;
		hitSound = Assets.Sounds.HIT_CRUSH;
		hitSoundPitch = 1.2f;
		
		tier = 5;
		DLY = 0.5f; //2x speed
	}
	
	@Override
	public int max(int lvl) {
		return  Math.round(2.5f*(tier+1)) +     //15 base, down from 30
				lvl*Math.round(0.5f*(tier+1));  //+3 per level, down from +6
	}


	@Override
	public int proc(Char attacker, Char defender, int damage) {

		if (hero.belongings.getItem(RingOfFuror.class) != null) {
			if (hero.belongings.getItem(RingOfFuror.class).isEquipped(hero) && (Random.Int(10) == 0)) {
				{
					Buff.affect( attacker, Bless.class, 10f);
				}
			}
		}

		return super.proc(attacker, defender, damage);
	}

	@Override
	public String desc() {
		String info = Messages.get(this, "desc");
		if (Dungeon.hero != null && Dungeon.hero.belongings.getItem(RingOfFuror.class) != null) {
			if (Dungeon.hero.belongings.getItem(RingOfFuror.class).isEquipped(Dungeon.hero))
				info += "\n\n" + Messages.get( Gauntlet.class, "setbouns");}

		return info;
	}

	@Override
	public String targetingPrompt() {
		return Messages.get(this, "prompt");
	}

	@Override
	protected void duelistAbility(Hero hero, Integer target) {
		//+(5+lvl) damage, roughly +50% base damage, +50% scaling
		int dmgBoost = augment.damageFactor(5 + buffedLvl());
		Sai.comboStrikeAbility(hero, target, 0, dmgBoost, this);
	}

	@Override
	public String abilityInfo() {
		int dmgBoost = levelKnown ? 5 + buffedLvl() : 5;
		if (levelKnown){
			return Messages.get(this, "ability_desc", augment.damageFactor(dmgBoost));
		} else {
			return Messages.get(this, "typical_ability_desc", augment.damageFactor(dmgBoost));
		}
	}

	public String upgradeAbilityStat(int level){
		return "+" + augment.damageFactor(5 + level);
	}

}
