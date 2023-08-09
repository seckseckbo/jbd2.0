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

package com.shatteredpixel.shatteredpixeldungeon.ui.changelist;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.BadgeBanner;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ElementalSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HeroSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SpectralNecromancerSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIcon;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.TalentIcon;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.noosa.Image;

import java.util.ArrayList;

public class v1_X_Changes {

	public static void addAllChanges( ArrayList<ChangeInfo> changeInfos ){
		add_Coming_Soon(changeInfos);
		add_v1_4_5_Changes(changeInfos);
		add_v1_4_3_Changes(changeInfos);
		add_v1_4_1_Changes(changeInfos);
	}

	public static void add_Coming_Soon( ArrayList<ChangeInfo> changeInfos ) {

		ChangeInfo changes = new ChangeInfo("Coming Soon", true, "");
		changes.hardlight(0xCCCCCC);
		changeInfos.add(changes);

		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.TUSK3), "죠니 퀘스트 개편",
				"2.0j 업데이트에서는 죠니 퀘스트가 압둘, 화이트 스네이크 퀘스트처럼 3가지 조건 중 무작위 하나를 수행하는 방식으로 개편됩니다."));
	}

	public static void add_v1_4_5_Changes( ArrayList<ChangeInfo> changeInfos ) {
		ChangeInfo changes = new ChangeInfo("2.0i", true, "");
		changes.hardlight(Window.TITLE_COLOR);
		changeInfos.add(changes);

		changes = new ChangeInfo("신규 시스템", false, null);
		changes.hardlight(CharSprite.POSITIVE);
		changeInfos.add(changes);
		changes.addButton( new ChangeButton( new Image(Assets.Sprites.DIOBRANDO, 0, 0, 12, 15), "신규 던전 : 디오의 성",
				"신규 던전인 _디오의 성_이 추가되었습니다!\n\n디오의 성은 기존 던전과는 별개의 고난이도 던전으로, 디오의 성에 입장하기 위해서는 클리어 포인트를 이용해 _입장권_을 구매해야 합니다.\n\n보상으로 _스킨 교환권_을 얻을 수 있습니다."));
		changes.addButton( new ChangeButton(BadgeBanner.image(Badges.Badge.BRANDOKILL.image), "신규 뱃지",
				"신규 던전 : 디오의 성 관련 뱃지 2종이 추가되었습니다."));
		changes.addButton( new ChangeButton( Icons.get(Icons.TALENT), "클리어 포인트",
				"_클리어 포인트_는 게임을 클리어하면 획득할 수 있는 영구적으로 보존되는 재화입니다.\n\n클리어 포인트는 모든 캐릭터가 공용으로 획득하고 사용할 수 있으며, _신규 던전 : 디오의 성 입장권_을 구매하는데 사용됩니다."));
		changes.addButton( new ChangeButton( new Image(Assets.Sprites.EMPORIO, 0, 0, 10, 14), "신규 NPC",
				"신규 NPC인 엠포리오를 통해서 유령의 방으로 이동할 수 있습니다.\n\n엠포리오는 게임을 한번 이상 클리어했다면, 던전 1층에 스폰됩니다.\n\n엠포리오의 방에 있는 특정 npc에게 신규 접속 기념 _공짜 입장권_을 받을 수 있습니다."));
		changes.addButton( new ChangeButton( new Image(Assets.Sprites.ANNASUI, 0, 0, 11, 16), "스킨",
				"_캐릭터 스킨_이 새로 추가되었습니다!\n\n스킨은 캐릭터별로 적용할 수 있는 고유의 모습이며, 엠포리오의 방에서 디오의 성 클리어 보상인 _스킨 교환권_을 통해 안나수이에게 구입할 수 있습니다."));
		changes.addButton( new ChangeButton( new Image(Assets.Sprites.PUCCI4, 0, 0, 12, 15), "히든 보스",
				"천국의 DISC 획득 후, 일순하는 과정에서 히든 보스가 추가되었습니다.\n\n처치 시, _클리어 포인트 4_를 획득할 수 있습니다."));
		changes.addButton( new ChangeButton( new Image(Assets.Sprites.ZOMBIET, 0, 0, 14, 16), "신규 적",
				"디오의 성에서만 등장하는 고유한 적들이 대거 추가되었습니다!\n\n디오의 성에서는 디오가 만들어낸 _시생인_들이 적으로 등장합니다."));

		changes = new ChangeInfo("변경", false, null);
		changes.hardlight(CharSprite.WARNING);
		changeInfos.add(changes);

		changes.addButton( new ChangeButton( Icons.get(Icons.STAIRS), "편의성 개선",
				"31층에서 천국의 DISC 획득 후\n계단을 올라갈 시, 25층으로 한번에 이동하도록 변경되었습니다."));
		changes.addButton( new ChangeButton( Icons.get(Icons.CHALLENGE_OFF), "편의성 개선 2",
				"특정 보스 처치 및 시련 활성화 시,\n클리어 포인트 획득량이 더 증가되었습니다."));
		changes.addButton( new ChangeButton( new Image(Assets.Sprites.SPEEDWAGON, 0, 0, 12, 15), "편의성 개선 3",
				"이제 스피드왜건과 시야가 공유되며, 스피드왜건의 체력이 더 증가했습니다."));
	}

	public static void add_v1_4_3_Changes( ArrayList<ChangeInfo> changeInfos ) {
		ChangeInfo changes = new ChangeInfo("2.0h", true, "");
		changes.hardlight(Window.TITLE_COLOR);
		changeInfos.add(changes);

		changes = new ChangeInfo("새로운 요소", false, null);
		changes.hardlight(CharSprite.POSITIVE);
		changeInfos.add(changes);

		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.WALL), "벽의 눈",
				"신규 아이템 벽의 눈이 추가되었습니다!\n\n" +
						"벽의 눈은 5층의 보스를 처치하면 획득할 수 있습니다."));
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.JOJO1), "죠죠 1~3부 만화책",
				"소지하기만 해도 강력한 효과를 지닌 신규 아이템 타입이 추가되었습니다!\n\n" +
						"_죠죠 1~3부 만화책은 벽의 눈에서 3% 확률로 얻을 수 있습니다._"));
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.JOJO4), "죠죠 4~6부 만화책",
				"소지하기만 해도 강력한 효과를 지닌 신규 아이템 타입이 추가되었습니다!\n\n" +
						"_죠죠 4~6부 만화책은 벽의 눈에서 3% 확률로 얻을 수 있습니다._"));
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.JOJO7), "죠죠 7~9부 만화책",
				"소지하기만 해도 강력한 효과를 지닌 신규 아이템 타입이 추가되었습니다!\n\n" +
						"_죠죠 7~9부 만화책은 벽의 눈에서 3% 확률로 얻을 수 있습니다._"));
		changes.addButton( new ChangeButton( new Image(Assets.Sprites.ATOM, 0, 0, 12, 15), "신규 적",
				"_아톰 하트 파더_가 희귀한 확률로 타워 오브 그레이 대신 등장합니다.\n\n_푸 파이터즈_가 희귀한 확률로 클래시 대신 등장합니다."));
		changes.addButton( new ChangeButton( new Image(Assets.Sprites.KEEPER, 0, 0, 15, 15), "상점 개편",
				"상점 상호작용이 추가되었습니다!\n\n이제 최근에 판매한 물건을 다시 재구매할 수 있습니다.\n\n이제 상점 주인과 대화할 수 있습니다."));
		changes.addButton( new ChangeButton( new Image(Assets.Sprites.FUGO, 0, 0, 12, 15), "신규 NPC",
				"히로세 야스호와 판나코타 푸고가 일정 확률로 13층, 17층에 등장합니다."));
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.KATANA), "신규 무기",
				"신규 2단계 무기 1종,\n신규 4단계 무기 1종,\n신규 5단계 무기 1종이 추가되었습니다."));
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.CEN), "20th 센츄리 보이",
				"상점에서 판매하는 희귀 아이템이 추가되었습니다."));
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.TUSK3), "미니 던전",
				"미니 던전 : 기둥의 남자들이 잠든 곳이 추가되었습니다!\n\n" +
						"미니 던전은 죠니 근처의 입구를 통해 입장할 수 있으며, 추후 죠니 퀘스트 개편에 사용됩니다."));
		changes = new ChangeInfo("변경", false, null);
		changes.hardlight(CharSprite.WARNING);
		changeInfos.add(changes);
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.AMULET), "천국의 DISC",
						"천국의 DISC 입수 후, 적들의 방어도가 올라가는 대신 최대 체력이 상승하도록 변경되었습니다."));
		changes.addButton(new ChangeButton( new TalentIcon(Talent.PRECISE_ASSAULT), "죠스케 특성 리워크",
				"죠스케의 3단계 특성 중, 최종 결전 특성이 리워크되었습니다!\n\n기존 효과 : 죠스케가 1/2/3단계 근접 무기의 능력을 사용할 때, 소모 충전량 감소\n\n변경 효과 : 무기 능력을 발동한 후, 5턴 이내의 다음 번 근접 공격이 정확성을 얻음"));
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.GRAVE), "드래곤즈 드림 리워크",
				"드래곤즈 드림의 아이템 효과가 리워크되었습니다.\n\n" +
						"50% 확률로 길의 방향, 흉의 방향을 발동하는 효과로 변경되었습니다.\n이제 상점에서 드래곤즈 드림을 구입할 수 있습니다."));

		changes = new ChangeInfo("상향", false, null);
		changes.hardlight(Window.TITLE_COLOR);
		changeInfos.add(changes);

		changes.addButton( new ChangeButton(HeroSprite.avatar(HeroClass.DUELIST, 1), "죠스케 상향",
				"피해량 및 효과 증가:\n" +
						"_-_ _크레이지 D 능력 발현 - 악퉁 베이비_ :\n은신 지속 시간 8/6/4턴에서 10/8/6턴으로 증가.\n" +
						"_-_ _크레이지 D 능력 발현 - 연속 공격_ :\n+40/35/30%에서 +45/40/35%로 피해량 증가.\n" +
						"_-_ _크레이지 D 능력 발현 - '헌팅'하러 가자!_ :\n+40/30%에서 +45/30%로 피해량 증가.\n" +
						"_-_ _크레이지 D 능력 발현 - 죠셉의 가호_ :\n회피율 2배에서 회피율 3배로 증가, 지속시간 6턴에서 5턴으로 감소.\n" +
						"_-_ _크레이지 D 능력 발현 - 혈액 커터_ :\n이제 가장 가까이 있는 적은 무조건 공격에 적중됩니다.\n" +
						"_-_ _크레이지 D 능력 발현 - 방벽 생성_ :\n방벽 지속 시간 5/4턴에서 8/6턴으로 증가.\n" +
						"_-_ _크레이지 D 능력 발현 - 치유 펀치_ :\n속성 성능 +250% 증가에서 +300%로 증가.\n" +
						"\n" +
						" ",
				"패널티 효과 제거:\n" +
						"_-_ _크레이지 D 능력 발현 - 잠재된 폭발력_ :\n-20% 정확도에서 +25% 정확도로 증가, 지속시간 6턴에서 5턴으로 감소.\n" +
						"_-_ _크레이지 D 능력 발현 - 차지 어택_ :\n힘 축적 횟수와 상관 없이 반드시 명중.\n" +
						"_-_ _크레이지 D 능력 발현 - 각오 모드_ :\n+35%에서 +50%로 피해량 증가.\n" +
						"_-_ _크레이지 D 능력 발현 - 가드 브레이커_ :\n_-_ 이제 반드시 명중하는 대신, 기습 공격을 하지 않으면 2의 충전량을 사용합니다.\n_-_ 70-50%에서 50-30%로 추가 피해량 감소\n_-_ 신규 디버프 '방어 해제'를 5턴 동안 부여.\n" +
						"_-_ _크레이지 D 능력 발현 - 비장의 한발_ :\n_-_ 씨앗을 바른 탄환의 경우 2번에서 4번으로 사용 횟수 증가.\n_-_ 3x3 범위에서 5x5 범위로 증가\n_-_ 해로운 효과는 적에게만 적용되고 이로운 효과는 아군에게만 적용."));


		changes.addButton( new ChangeButton(HeroSprite.avatar(HeroClass.DUELIST, 4), "죠스케 보조 직업 상향",
				"_분노의 스탠드사:_\n" +
						"_-_ _분노 폭주 특성_ :\n10/20/30% 미만의 체력을 가진 적을 일격사에서 13/27/40% 미만의 체력을 가진 적을 일격사로 변경.\n\n" +
						"_수복의 스탠드사:_\n" +
						"_-_ _황금의 정신 특성_ :\n33/67/100% 추가 에너지에서 40/80/120% 추가 에너지로 변경, 장비 미착용 조건 삭제.\n\n" +
						"_-_ _결정된 승리 특성_ :\n돌려받는 에너지량 33%에서 50%로 증가.\n\n" +
						"_-_ _개체 변형 특성_ :\n_-_ 하트 히트 어택에 100% 위력의 속성 부여.\n_-_ 스타일리쉬 무브의 범위가 +2에서 +3으로 증가.\n_-_ 그레이트 히트 어택의 피해량이 +33%에서 +50%로 증가."));
		changes.addButton( new ChangeButton(HeroSprite.avatar(HeroClass.DUELIST, 6), "죠스케 레퀴엠 능력 상향",
				"_-_ _Crazy noisy bizzare town_ :\n약점 발견 - 초월 특성의 방어력 저하가 1/2/3/4턴에서 2/4/6/8턴으로 증가.\n\n" +
						 "_-_ _Chase_ :\n명품 교복 - 초월 특성 체력 회복량 증가\n\n" +
						 "_-_ _Great Days_ :\n피해량 증가."));
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.KINGB), "히가시카타 가족 스탠드",
				"상점에서 판매하는 8부 스탠드들의 가격이 35% 감소합니다."));

		changes = new ChangeInfo("하향", false, null);
		changes.hardlight(CharSprite.NEGATIVE);
		changeInfos.add(changes);
		changes.addButton( new ChangeButton(HeroSprite.avatar(HeroClass.DUELIST, 5), "죠스케 하향",
				"고고고 모드 시전 시, 사격/장비DISC 충전의 지속시간이 10턴에서 8턴으로 감소했습니다."));
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.RAM), "마법의 램프",
				"이제 상점에서 1/2 확률로 마법의 램프 대신 SPW 재단의 보급품을 대신 판매합니다."));
	}

	public static void add_v1_4_1_Changes( ArrayList<ChangeInfo> changeInfos ) {
		ChangeInfo changes = new ChangeInfo("2.0g", true, "");
		changes.hardlight(Window.TITLE_COLOR);
		changeInfos.add(changes);

		changes = new ChangeInfo("신규 캐릭터", false, null);
		changes.hardlight(CharSprite.POSITIVE);
		changeInfos.add(changes);

		changes.addButton( new ChangeButton( new Image(Assets.Sprites.RESEARCHER, 0, 0, 12, 15), "죠스케",
				"4부의 주인공 히가시카타 죠스케가 플레이어블 캐릭터로 추가되었습니다!"));
		changes.addButton( new ChangeButton(new BuffIcon(BuffIndicator.MONK_ENERGY, true), "보조 직업 UI 개선",
				"다음 보조 직업들의 시각적 인터페이스가 개선되었습니다 :\n\n용기의 파문전사 각성의 파문전사\n파괴의 스탠드사 속도의 스탠드사\n분노의 스탠드사 수복의 스탠드사\n의식 폭주의 스탠드사"));

		changes = new ChangeInfo("4부 적", false, null);
		changes.hardlight(CharSprite.WARNING);
		changeInfos.add(changes);

		changes.addButton( new ChangeButton( new Image(Assets.Sprites.BCOM, 0, 0, 15, 15), "배드 컴퍼니",
				"6층에 신규 적인 배드 컴퍼니가 등장합니다."));
		changes.addButton( new ChangeButton( new Image(Assets.Sprites.BOYTWO, 0, 0, 13, 18), "보이 투 맨",
				"무작위 계층에 희귀한 확률로 보이 투 맨이 출몰합니다."));
		changes.addButton( new ChangeButton( new Image(Assets.Sprites.STOWER, 0, 0, 17, 22), "슈퍼 플라이",
				"무작위 계층에 희귀한 확률로 슈퍼 플라이가 출몰합니다."));

		changes = new ChangeInfo("4부 NPC", false, null);
		changes.hardlight(Window.TITLE_COLOR);
		changeInfos.add(changes);

		changes.addButton( new ChangeButton( new Image(Assets.Sprites.YUKAKO, 0, 0, 12, 15), "야마기시 유카코",
				"특정 계층에 일정 확률로 유카코 npc가 등장합니다."));
		changes.addButton( new ChangeButton( new Image(Assets.Sprites.RETONIO, 0, 0, 12, 15), "토니오 트루사르디",
				"특정 계층에 일정 확률로 토니오 npc가 등장합니다."));
		changes.addButton( new ChangeButton( new Image(Assets.Sprites.ROHAN, 0, 0, 12, 14), "키시베 로한",
				"특정 계층에 일정 확률로 로한 npc가 등장합니다."));

		changes = new ChangeInfo("밸런스 조정", false, null);
		changes.hardlight(CharSprite.NEGATIVE);
		changeInfos.add(changes);

		changes.addButton( new ChangeButton( Icons.get(Icons.PREFS), "아이템 조정",
				"다양한 무기들이 리워크되었습니다.\n\n" +
						"반발의 상형문자가 원거리 적을 튕겨내지 않습니다.\n\n" +
						"이제 충전의 석가면이 레퀴엠 브로치의 능력도 충전합니다."));
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.SPOH), "26~30층 변경점",
				"30층 상자에 스타 플라티나 오버 헤븐의 DISC가 추가되었습니다.\n\n" +
						"26~29층에 천국에 도달한 DIO를 지원하는 적이 추가되었습니다.\n\n"+
						"죠나단, 죠셉의 패턴이 변경되었습니다.\n\n" +
						"천국에 도달한 DIO의 패턴이 추가/변경되었습니다."));
		changes.addButton( new ChangeButton(HeroSprite.avatar(HeroClass.DUELIST, 6), "죠스케 상향",
				"_-_ _무기 충전 속도_가 12.5% 상승합니다.\n\n_-_ _다이아몬드는 부서지지 않는다_ 특성의 추가 데미지가 2에서 3으로 상승합니다.\n_-_ _원상복귀_ 특성의 재사용 대기시간이 30턴에서 20턴으로 감소합니다.\n_-_ _황금의 정신_ 특성의 에너지 증가량이 25/50/100/150%에서 33/67/100/150로 증가합니다.\n"+
						"_-_ _개체 변형_ 특성의 필요 에너지 조건이 100/85/70%에서 100/80/60%로 감소합니다.\n\n_-_ _크레이지 D 능력 발현 - 악퉁 베이비_ :\n은신 지속 시간 6/5/4턴에서 8/6/4턴으로 증가.\n_-_ _크레이지 D 능력 발현 - '헌팅'하러 가자!_ :\n+15/10%에서 +40/30%로 피해량 증가.\n_-_ _크레이지 D 능력 발현 - 가드 브레이커_ :\n+50/45/40/35%에서 +65/60/55/50%로 피해량 증가.\n"+
						"_-_ _크레이지 D 능력 발현 - 연속 공격_ :\n+30/25/20%에서 +40/35/30%로 피해량 증가.\n_-_ _크레이지 D 능력 발현 - 차지 어택_ :\n힘을 축적할 때마다 +20%에서 +33%로 피해량 증가."
		));
		changes.addButton( new ChangeButton( new Image(Assets.Sprites.KOUSAKU, 0, 0, 12, 15), "카와지리 보상 강화",
				"카와지리 처치 시, 영구히 지속되는 전용 버프가 추가되었습니다."));

	}
}
