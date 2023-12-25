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

package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.ParalyticGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Light;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Pushing;
import com.shatteredpixel.shatteredpixeldungeon.effects.TargetedCell;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.PitfallParticle;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.Fadeleaf;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.DiegoSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BossHealthBar;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.GameMath;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class Pucci2 extends Mob {

    {
        spriteClass = DiegoSprite.class;

        HP = HT = 200;
        viewDistance = Light.DISTANCE;

        EXP = 0;
        maxLvl = -9;

        state = SLEEPING;

        baseSpeed = 4f;

        immunities.add( Paralysis.class );
        immunities.add( Vertigo.class );
        immunities.add( ParalyticGas.class );

        properties.add(Property.BOSS);
    }

    private int charge = 0; // 2이 될경우 강화 사격
    private int barriercooldown = 999;

    @Override
    public float attackDelay() {
        return super.attackDelay()*0.5f;
    }

    @Override
    protected boolean getCloser(int target) {

        target = Dungeon.hero.pos;

        return super.getCloser( target );
    }

    @Override
    public void damage(int dmg, Object src) {
        CellEmitter.get(this.pos).burst(PitfallParticle.FACTORY8, 12);
        new Fadeleaf().activate(this);

        if (dmg >= 50){
            //takes 20/21/22/23/24/25/26/27/28/29/30 dmg
            // at   20/22/25/29/34/40/47/55/64/74/85 incoming dmg
            dmg = 50;
        }
        HUNTING = new Pucci2.Hunting();
        super.damage(dmg, src);
    }

    private static final String LAST_ENEMY_POS = "last_enemy_pos";
    private static final String LEAP_POS = "leap_pos";
    private static final String LEAP_CD = "leap_cd";
    private static final String SKILLCD   = "charge";
    private static final String BARRIER_CD  = "barriercooldown";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(LAST_ENEMY_POS, lastEnemyPos);
        bundle.put(LEAP_POS, leapPos);
        bundle.put( BARRIER_CD, barriercooldown );
        bundle.put(LEAP_CD, leapCooldown);
        bundle.put( SKILLCD, charge );
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        lastEnemyPos = bundle.getInt(LAST_ENEMY_POS);
        leapPos = bundle.getInt(LEAP_POS);
        barriercooldown = bundle.getInt(BARRIER_CD);
        leapCooldown = bundle.getFloat(LEAP_CD);
        charge = bundle.getInt(SKILLCD);
    }

    private int lastEnemyPos = -1;

    @Override
    public void notice() {
        super.notice();
        if (!BossHealthBar.isAssigned()) {
            BossHealthBar.assignBoss(this);
        }
    }

    @Override
    protected boolean act() {

        AiState lastState = state;
        boolean result = super.act();
        if (paralysed <= 0) leapCooldown --;

        //if state changed from wandering to hunting, we haven't acted yet, don't update.
        if (!(lastState == WANDERING && state == HUNTING)) {
            if (enemy != null) {
                lastEnemyPos = enemy.pos;
            } else {
                lastEnemyPos = Dungeon.hero.pos;
            }
        }

        return result;
    }

    private int leapPos = -1;
    private float leapCooldown = 0;

    public class Hunting extends Mob.Hunting {

        @Override
        public boolean act( boolean enemyInFOV, boolean justAlerted ) {

            if (leapPos != -1){
                charge = 0;
                leapCooldown = Random.NormalIntRange(1, 2);

                switch (Random.Int( 2 )) {
                    case 0:
                        Sample.INSTANCE.play( Assets.Sounds.D1);
                        sprite.showStatus(CharSprite.WARNING, Messages.get(Diego.class, "3"));
                        break;
                    case 1:
                        Sample.INSTANCE.play( Assets.Sounds.D2);
                        sprite.showStatus(CharSprite.WARNING, Messages.get(Diego.class, "4"));
                        break;
                }

                Ballistica b = new Ballistica(pos, leapPos, Ballistica.STOP_TARGET | Ballistica.STOP_SOLID);

                //check if leap pos is not obstructed by terrain
                if (rooted || b.collisionPos != leapPos){
                    leapPos = -1;
                    return true;
                }

                final Char leapVictim = Actor.findChar(leapPos);
                final int endPos;

                //ensure there is somewhere to land after leaping
                if (leapVictim != null){
                    int bouncepos = -1;
                    for (int i : PathFinder.NEIGHBOURS8){
                        if ((bouncepos == -1 || Dungeon.level.trueDistance(pos, leapPos+i) < Dungeon.level.trueDistance(pos, bouncepos))
                                && Actor.findChar(leapPos+i) == null && Dungeon.level.passable[leapPos+i]){
                            bouncepos = leapPos+i;
                        }
                    }
                    if (bouncepos == -1) {
                        leapPos = -1;
                        return true;
                    } else {
                        endPos = bouncepos;
                    }
                } else {
                    endPos = leapPos;
                }

                //do leap
                sprite.visible = Dungeon.level.heroFOV[pos] || Dungeon.level.heroFOV[leapPos] || Dungeon.level.heroFOV[endPos];
                sprite.jump(pos, leapPos, new Callback() {
                    @Override
                    public void call() {

                        if (leapVictim != null && alignment != leapVictim.alignment){
                            Buff.affect(leapVictim, Bleeding.class).set(0.75f*damageRoll());
                            leapVictim.sprite.flash();
                            Sample.INSTANCE.play(Assets.Sounds.HIT);
                        }

                        if (endPos != leapPos){
                            Actor.addDelayed(new Pushing(Pucci2.this, leapPos, endPos), -1);
                        }

                        pos = endPos;
                        leapPos = -1;
                        sprite.idle();
                        Dungeon.level.occupyCell(Pucci2.this);
                        next();
                    }
                });
                return false;
            }

            enemySeen = enemyInFOV;
            if (enemyInFOV && !isCharmedBy( enemy ) && canAttack( enemy )) {

                return doAttack( enemy );

            } else {

                if (enemyInFOV) {
                    target = enemy.pos;
                } else if (enemy == null) {
                    state = WANDERING;
                    target = Dungeon.level.randomDestination( Pucci2.this );
                    return true;
                }

                if (leapCooldown <= 0 && enemyInFOV && !rooted
                        && Dungeon.level.distance(pos, enemy.pos) >= 3) {

                    int targetPos = enemy.pos;
                    if (lastEnemyPos != enemy.pos){
                        int closestIdx = 0;
                        for (int i = 1; i < PathFinder.CIRCLE8.length; i++){
                            if (Dungeon.level.trueDistance(lastEnemyPos, enemy.pos)
                                    < Dungeon.level.trueDistance(lastEnemyPos, enemy.pos)){
                                closestIdx = i;
                            }
                        }
                        targetPos = enemy.pos;
                    }

                    Ballistica b = new Ballistica(pos, targetPos, Ballistica.STOP_TARGET | Ballistica.STOP_SOLID);
                    //try aiming directly at hero if aiming near them doesn't work
                    if (b.collisionPos != targetPos && targetPos != enemy.pos){
                        targetPos = enemy.pos;
                        b = new Ballistica(pos, targetPos, Ballistica.STOP_TARGET | Ballistica.STOP_SOLID);
                    }
                    if (b.collisionPos == targetPos){
                        //get ready to leap
                        leapPos = targetPos;
                        //don't want to overly punish players with slow move or attack speed
                        spend(GameMath.gate(TICK, enemy.cooldown(), 3*TICK));
                        if (Dungeon.level.heroFOV[pos] || Dungeon.level.heroFOV[leapPos]){

                            sprite.parent.addToBack(new TargetedCell(leapPos, 0xFF00FF));
                            ((DiegoSprite)sprite).leapPrep( leapPos );
                            Dungeon.hero.interrupt();
                        }
                        return true;
                    }
                }

                int oldPos = pos;
                if (target != -1 && getCloser( target )) {

                    spend( 1 / speed() );
                    return moveSprite( oldPos,  pos );

                } else {
                    spend( TICK );
                    if (!enemyInFOV) {
                        sprite.showLost();
                        state = WANDERING;
                        target = Dungeon.level.randomDestination( Pucci2.this );
                    }
                    return true;
                }
            }
        }

    }

    @Override
    public void die( Object cause ) {

        super.die( cause );

    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( hero.HT/6, hero.HT/4 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 30;
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, 10);
    }


}
