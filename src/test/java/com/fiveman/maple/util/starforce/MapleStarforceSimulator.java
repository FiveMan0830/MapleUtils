package com.fiveman.maple.util.starforce;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Random;

public class MapleStarforceSimulator {
    private Integer equipmentBaseCost;
    private Integer equipmentBaseStarForce;
    private Integer currentStarForce;
    private Integer totalCost;
    private Map<Integer, StarforceEnhanceChanceItem> enhanceChanceMap;
    private StarforceEnhanceCostItem costItem;

    @Before
    public void setup() {
        equipmentBaseCost = 0;
        equipmentBaseStarForce = 15;
        currentStarForce = equipmentBaseStarForce;

        // for KR
        enhanceChanceMap.put(16, new StarforceEnhanceChanceItem(3000, 6790, 210));
        enhanceChanceMap.put(17, new StarforceEnhanceChanceItem(3000, 6790, 210));
        enhanceChanceMap.put(18, new StarforceEnhanceChanceItem(1500, 7820, 680));
        enhanceChanceMap.put(19, new StarforceEnhanceChanceItem(1500, 7820, 680));
        enhanceChanceMap.put(20, new StarforceEnhanceChanceItem(1500, 7650, 850));
        enhanceChanceMap.put(21, new StarforceEnhanceChanceItem(3000, 5950, 1050));
        enhanceChanceMap.put(22, new StarforceEnhanceChanceItem(1500, 7225, 1275));
        enhanceChanceMap.put(23, new StarforceEnhanceChanceItem(1500, 6800, 1700));
        enhanceChanceMap.put(24, new StarforceEnhanceChanceItem(1000, 7200, 1800));
        enhanceChanceMap.put(25, new StarforceEnhanceChanceItem(1000, 7200, 1800));
        enhanceChanceMap.put(26, new StarforceEnhanceChanceItem(1000, 7200, 1800));
        enhanceChanceMap.put(27, new StarforceEnhanceChanceItem(700, 7440, 1860));
        enhanceChanceMap.put(28, new StarforceEnhanceChanceItem(500, 7600, 1900));
        enhanceChanceMap.put(29, new StarforceEnhanceChanceItem(300, 7760, 1940));
        enhanceChanceMap.put(30, new StarforceEnhanceChanceItem(100, 7920, 1980));

        costItem = new StarforceEnhanceCostItem(150);
        totalCost = equipmentBaseCost;
    }

    @Test
    public void simulateCost(){
        Integer targetLevel = 22;

        while (currentStarForce < targetLevel) {
            doEnhance(currentStarForce + 1);
        }

        System.out.println(totalCost);
    }

    private void doEnhance(Integer challengeLevel) {
        totalCost += costItem.getCostByChallengeLevel(challengeLevel, true);
        EnhanceResult result = getEnhanceResult(challengeLevel);

        switch (result) {
            case SUCCESS:
                processEnhanceTrue();
                break;
            case FAIL:
                processEnhanceFail();
                break;
            case BREAK:
                processItemBreak();
                break;
        }
    }

    private void processEnhanceTrue() {
        currentStarForce+=1;
    }

    private void processEnhanceFail() {
        return;
    }

    private void processItemBreak() {
        currentStarForce = equipmentBaseStarForce;
        totalCost += equipmentBaseCost;
    }

    private EnhanceResult getEnhanceResult(Integer challengeLevel) {
        StarforceEnhanceChanceItem chanceItem = enhanceChanceMap.get(challengeLevel);

        Random random = new Random();
        int randomNumber = random.nextInt(10000);

        if (randomNumber < chanceItem.getSuccessChance())
            return EnhanceResult.SUCCESS;
        else if (randomNumber < chanceItem.getFailChance())
            return EnhanceResult.FAIL;
        else
            return EnhanceResult.BREAK;
    }


}
