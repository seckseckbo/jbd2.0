/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;


import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barkskin;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Light;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Weza;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BloodParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SparkParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.DriedRose;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.ReclaimTrap;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.levels.HallsLevel;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.DoppioDialogSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.NmoonSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.TenguSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BossHealthBar;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndDialogueWithPic;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class Greenbaby extends Mob {

    {
        spriteClass = NmoonSprite.class;

        HP = HT = 175;
        defenseSkill = 20;
        viewDistance = Light.DISTANCE;

        EXP = 15;
        maxLvl = 28;

        loot = Generator.Category.POTION;
        lootChance = 0.5f;

        properties.add(Property.BOSS);
    }

    private boolean seenBefore = false;

    @Override
    public void notice() {
        super.notice();

        if (hero.heroClass == HeroClass.CLERIC) {
            WndDialogueWithPic.dialogue(
                    new CharSprite[]{new NmoonSprite()},
                    new String[]{"C-MOON"},
                    new String[]{
                            Messages.get(Greenbaby.class, "2")
                    },
                    new byte[]{
                            WndDialogueWithPic.IDLE
                    }
            );
        } else {
            this.yell(Messages.get(this, "1"));
        }

        seenBefore = true;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(4, 6);
    }

    @Override
    public int attackSkill(Char target) {
        return 35;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 10);
    }

    @Override
    public int attackProc(Char hero, int damage) {
        damage = super.attackProc(enemy, damage);


        if (this.buff(Barkskin.class) == null) {

            damage = Math.max(damage, hero.HP / 2);

            Sample.INSTANCE.play(Assets.Sounds.BLAST);
            CellEmitter.center(Dungeon.hero.pos).burst(BloodParticle.BURST, 31);
            GLog.w(Messages.get(this, "torment"));

            return damage;

        }
        return damage;
    }

    @Override
    public void die(Object cause) {

        super.die(cause);

        this.yell(Messages.get(this, "3"));

        Dungeon.level.drop(new ReclaimTrap().identify(), pos).sprite.drop(pos);

    }

    public static void spawn(HallsLevel level) {

        if (Random.Int(2) == 0) {
            if (Dungeon.depth == 24 && !Dungeon.bossLevel()) {

                Greenbaby centinel = new Greenbaby();
                do {
                    centinel.pos = level.randomRespawnCell(centinel);
                } while (centinel.pos == -1);
                level.mobs.add(centinel);
            }
        }

    }


}
