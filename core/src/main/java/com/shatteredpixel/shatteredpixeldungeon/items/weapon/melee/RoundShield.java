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
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfMight;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

public class RoundShield extends MeleeWeapon {

	{
		image = ItemSpriteSheet.ROUND_SHIELD;
		hitSound = Assets.Sounds.HIT;
		hitSoundPitch = 1f;

		tier = 3;
	}

	@Override
	public int max(int lvl) {
		return  Math.round(3f*(tier+1)) +   //12 base, down from 20
				lvl*(tier-1);               //+2 per level, down from +4
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {

		if (Dungeon.hero.belongings.getItem(RingOfMight.class) != null) {
			if (Dungeon.hero.belongings.getItem(RingOfMight.class).isEquipped(Dungeon.hero)) {

				{
					hero.damage( 1, this );
					Buff.affect( defender, Bleeding.class).set(1f);
				}

		}}

		return super.proc(attacker, defender, damage);
	}

	@Override
	public int defenseFactor( Char owner ) {
		return 4+buffedLvl();               //4 extra defence, plus 1 per level
	}

	public String statsInfo(){
		if (isIdentified()){
			return Messages.get(this, "stats_desc", 4+buffedLvl());
		} else {
			return Messages.get(this, "typical_stats_desc", 4);
		}
	}

	@Override
	public String desc() {
		String info = Messages.get(this, "desc");
		if (Dungeon.hero.belongings.getItem(RingOfMight.class) != null) {
			if (Dungeon.hero.belongings.getItem(RingOfMight.class).isEquipped(Dungeon.hero))
				info += "\n\n" + Messages.get( RoundShield.class, "setbouns");}

		return info;
	}

	@Override
	protected void duelistAbility(Hero hero, Integer target) {
		RoundShield.guardAbility(hero, 5+buffedLvl(), this);
	}

	@Override
	public String abilityInfo() {
		if (levelKnown){
			return Messages.get(this, "ability_desc", 5+buffedLvl());
		} else {
			return Messages.get(this, "typical_ability_desc", 5);
		}
	}

	public static void guardAbility(Hero hero, int duration, MeleeWeapon wep){
		wep.beforeAbilityUsed(hero, null);
		Sword.doraclass();
		Buff.prolong(hero, GuardTracker.class, duration).hasBlocked = false;
		hero.sprite.operate(hero.pos);
		hero.spendAndNext(Actor.TICK);
		wep.afterAbilityUsed(hero);
	}

	public static class GuardTracker extends FlavourBuff {

		{
			announced = true;
			type = buffType.POSITIVE;
		}

		public boolean hasBlocked = false;

		@Override
		public int icon() {
			return BuffIndicator.DUEL_GUARD;
		}

		@Override
		public void tintIcon(Image icon) {
			if (hasBlocked){
				icon.tint(0x651f66, 0.5f);
			} else {
				icon.resetColor();
			}
		}

		@Override
		public float iconFadePercent() {
			return Math.max(0, (5 - visualcooldown()) / 5);
		}

		private static final String BLOCKED = "blocked";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			hasBlocked = bundle.getBoolean(BLOCKED);
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			bundle.put(BLOCKED, hasBlocked);
		}
	}
}