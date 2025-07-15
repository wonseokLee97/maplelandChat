package com.mapleland.chatservice.shared.jwt;

import lombok.Getter;

@Getter
public enum MonsterName {
    BLUE_SNAIL("파란 달팽이", "/image/BLUE_SNAIL.png"),
    RED_SNAIL("빨간 달팽이", "/image/RED_SNAIL.png"),
    PIG("돼지", "/image/PIG.png"),
    SPORE("스포아", "/image/SPORE.png"),
    GOTH_STUMP("고스텀프", "/image/GOTH_STUMP.png"),
    JUNIOR_BOOGEY("주니어 부기", "/image/JUNIOR_BOOGEY.png"),
    STUMP("스텀프", "/image/STUMP.png"),
    X_STUMP("엑스텀프", "/image/X_STUMP.png"),
    DARK_STUMP("다크 스텀프", "/image/DARK_STUMP.png"),
    DARK_X_STUMP("다크 엑스텀프", "/image/DARK_X_STUMP.png"),
    WILD_BOAR("와일드보어", "/image/WILD_BOAR.png"),
    IRON_HOG("아이언호그", "/image/IRON_HOG.png"),
    IRON_BOAR("아이언보어", "/image/IRON_BOAR.png"),
    FIRE_BOAR("파이어보어", "/image/FIRE_BOAR.png"),
    RED_DRAKE("레드 드레이크", "/image/RED_DRAKE.png"),
    WOOD_MASK("우드 마스크", "/image/WOOD_MASK.png"),
    STONE_MASK("스톤 마스크", "/image/STONE_MASK.png"),
    SKELDOG("스켈독", "/image/SKELDOG.png"),
    MUMMY_DOG("머미독", "/image/MUMMY_DOG.png"),
    SKELETON_SOLDIER("스켈레톤 사병", "/image/SKELETON_SOLDIER.png"),
    JUNIOR_NECKI("주니어 네키", "/image/JUNIOR_NECKI.png"),
    SLIME("슬라임", "/image/SLIME.png"),
    FAIRY("페어리", "/image/FAIRY.png"),
    ROYAL_FAIRY("로얄 페어리", "/image/ROYAL_FAIRY.png"),
    BUBBLING("버블링", "/image/BUBBLING.png"),
    RUPANG("루팡", "/image/RUPANG.png"),
    CURSE_EYE("커즈아이", "/image/CURSE_EYE.png"),
    OCTOPUS("옥토퍼스", "/image/OCTOPUS.png"),
    MARTIAN("마티안", "/image/MARTIAN.png"),
    STIG("스티지", "/image/STIG.png"),
    JUNIOR_RACE("주니어 레이스", "/image/JUNIOR_RACE.png"),
    RACE("레이스", "/image/RACE.png"),
    CROCO("크로코", "/image/CROCO.png"),
    HORN_MUSHROOM("뿔버섯", "/image/HORN_MUSHROOM.png"),
    ZOMBIE_MUSHROOM("좀비버섯", "/image/ZOMBIE_MUSHROOM.png"),
    EVIL_EYE("이블아이", "/image/EVIL_EYE.png"),
    DRAKE("드레이크", "/image/DRAKE.png"),
    COLD_EYE("콜드아이", "/image/COLD_EYE.png"),
    ICE_DRAKE("아이스 드레이크", "/image/ICE_DRAKE.png"),
    DARK_DRAKE("다크 드레이크", "/image/DARK_DRAKE.png"),
    JUNIOR_BALROG("주니어 발록", "/image/JUNIOR_BALROG.png");

    private final String koreanName;
    private final String path;

    MonsterName(String koreanName, String path) {
        this.koreanName = koreanName;
        this.path = path;
    }
}


