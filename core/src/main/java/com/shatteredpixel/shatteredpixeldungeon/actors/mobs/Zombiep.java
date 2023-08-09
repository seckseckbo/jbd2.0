/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2023 Evan Debenham
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
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Adrenaline;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.CursedBlow;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Doom;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vitam;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BloodParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.DriedRose;
import com.shatteredpixel.shatteredpixeldungeon.items.food.FrozenCarpaccio;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.ChaosCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfEnchantment;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Zombie01Sprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Zombie02Sprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Zombie03Sprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.Zombie04Sprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BossHealthBar;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public abstract class Zombiep extends Mob {

    {
        HP = HT = 20;
        defenseSkill = 5;
        baseSpeed = 1.5f;

        EXP = 4;
        maxLvl = 9;

        loot = new FrozenCarpaccio();
        lootChance = 0.167f;
        properties.add(Property.UNDEAD);
        properties.add(Property.DEMONIC);
    }

    @Override
    public int attackProc( Char enemy, int damage ) {
        damage = super.attackProc( enemy, damage );
        if (Random.Int( 4 ) == 0) {
            Buff.affect(enemy, CursedBlow.class, 2f);
        }

        return damage;
    }

    private int rockfall = 0;

    @Override
    public boolean attack(Char enemy, float dmgMulti, float dmgBonus, float accMulti) {

        if (rockfall < 4) rockfall++;
        else {
            sprite.showStatus(CharSprite.WARNING, Messages.get(this, "rockfall"));
            ArrayList<Integer> positions = new ArrayList<>();
            for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
                int p = this.pos + PathFinder.NEIGHBOURS8[i];
                positions.add(p);
            }
            for (int i : positions) {
                CellEmitter.get(i).burst(BloodParticle.FACTORY, 9 );
                Char ch = Actor.findChar(i);

                if (ch != null && ch.isAlive()) {
                    Buff.affect(ch, Vitam.class, 4f);
                }
            }
            Camera.main.shake(3, 0.7f);
            Sample.INSTANCE.play(Assets.Sounds.RAY);
            rockfall = 0;
        }

        return super.attack(enemy, dmgMulti, dmgBonus, accMulti);
    }

    private static final String ROCKFALL = "ROCKFALL";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(ROCKFALL, rockfall);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        rockfall = bundle.getInt( ROCKFALL );
    }


    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 1, 8 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 12;
    }

    @Override
    public int drRoll() {
        return super.drRoll() + Random.NormalIntRange(0, 3);
    }

    public static class Zombiep1 extends Zombiep {
        {
            spriteClass = Zombie01Sprite.class;
        }

        private boolean seenBefore = false;


        @Override
        public void notice() {
            super.notice();
            if(!seenBefore) this.yell(Messages.get(this, "notice"));
            seenBefore = true;
        }
    }

    public static class Zombiep2 extends Zombiep {
        {
            spriteClass = Zombie02Sprite.class;
        }

        private boolean seenBefore = false;


        @Override
        public void notice() {
            super.notice();
            if(!seenBefore) this.yell(Messages.get(this, "notice"));
            seenBefore = true;
        }
    }

    public static class Zombiep3 extends Zombiep {
        {
            spriteClass = Zombie03Sprite.class;
        }

        private boolean seenBefore = false;


        @Override
        public void notice() {
            super.notice();
            if(!seenBefore) this.yell(Messages.get(this, "notice"));
            seenBefore = true;
        }
    }

    public static class Zombiep4 extends Zombiep {
        {
            spriteClass = Zombie04Sprite.class;
        }

        private boolean seenBefore = false;


        @Override
        public void notice() {
            super.notice();
            if(!seenBefore) this.yell(Messages.get(this, "notice"));
            seenBefore = true;
        }
    }

    public static Class<? extends Zombiep> random() {
        float roll = Random.Float();
        if (roll < 0.25f) {
            return Zombiep1.class;
        } else if (roll < 0.5f) {
            return Zombiep2.class;
        } else if (roll < 0.75f) {
            return Zombiep3.class;
        } else {
            return Zombiep4.class;
        }
    }

    @Override
    public void die( Object cause ) {

        super.die( cause );

        if (Random.Int( 10 ) == 0) {
            Dungeon.level.drop( new StoneOfEnchantment().identify(), pos ).sprite.drop( pos );
        }

        if (Dungeon.level.heroFOV[pos]) {
            Sample.INSTANCE.play( Assets.Sounds.BONES,  Random.Float(1.2f, 0.9f) );
            Sample.INSTANCE.play(Assets.Sounds.BURNING);
        }

    }
}
