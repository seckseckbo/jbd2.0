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

package com.shatteredpixel.shatteredpixeldungeon.items.spells;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.huntress.SpiritHawk;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Civil;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Diego;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Diego12;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Diego21;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.SpellSprite;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CivilSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.DiegoSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.DiegonSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndDialogueWithPic;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Sbr9 extends Spell {

    {
        image = ItemSpriteSheet.MAP0;

        unique = true;
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);

        return actions;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return new ItemSprite.Glowing(0x000000, 1f);
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);

    }

    @Override
    public String status() {
        if (this.isIdentified()) return  "9TH" ;
        else return null;}

    @Override
    protected void onCast(Hero hero) {

        ArrayList<Integer> spawnPoints = new ArrayList<>();

        if (Dungeon.depth == 25) {
            Sample.INSTANCE.play(Assets.Sounds.MIMIC);
            Sample.INSTANCE.play(Assets.Sounds.DIEGO);
            GameScene.flash(0x990000);
            Music.INSTANCE.play(Assets.Music.CIV, true);
            GLog.p(Messages.get(Civil.class, "39"));
            WndDialogueWithPic.dialogue(
                    new CharSprite[]{new DiegonSprite()},
                    new String[]{"디에고 브란도"},
                    new String[]{
                            Messages.get(Diego12.class, "hi")
                    },
                    new byte[]{
                            WndDialogueWithPic.IDLE
                    }
            );

            for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
                int p = hero.pos + PathFinder.NEIGHBOURS8[i];
                if (Actor.findChar( p ) == null && Dungeon.level.passable[p]) {
                    spawnPoints.add( p );
                }
            }

            if (!spawnPoints.isEmpty()){

                Diego12 elemental = new Diego12();
                GameScene.add( elemental );
                ScrollOfTeleportation.appear( elemental, Random.element(spawnPoints) );

                Diego21 q = new Diego21();
                GameScene.add( q );
                ScrollOfTeleportation.appear( q, Random.element(spawnPoints) );

                detach(Dungeon.hero.belongings.backpack);

            } else {
                GLog.w(Messages.get(SpiritHawk.class, "no_space"));
            }

            updateQuickslot();

            Dungeon.hero.sprite.emitter().burst(Speck.factory(Speck.RED_LIGHT),12);
        }else
        {
            GLog.h(Messages.get(Civil.class, "now"));
            SpellSprite.show( curUser, SpellSprite.MAP );
            Sample.INSTANCE.play(Assets.Sounds.READ);
        }


        hero.sprite.operate(hero.pos);
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
    }

    @Override
    public int value() {
        return 10 * quantity;
    }

}