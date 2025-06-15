package com.fiveman.maple.util.starforce;

import java.util.HashMap;
import java.util.Map;

public class StarforceEnhanceCostItem {
    private Integer itemLevel;
    private Map<Integer, CostPair> enhanceCostMap;

    public StarforceEnhanceCostItem(Integer itemLevel) {
        this.itemLevel = itemLevel;
//        enhanceCostMap = calculateEachLevelCost();
        enhanceCostMap = new HashMap<>();
    }

    private Map<Integer, CostPair> calculateEachLevelCost() {
        return null;
    }

    public Integer getItemLevel() {
        return itemLevel;
    }

    public void setItemLevel(Integer itemLevel) {
        this.itemLevel = itemLevel;
    }

    public void addCost(Integer challengeLevel, Integer price, Integer protectPrice) {
        enhanceCostMap.put(challengeLevel, new CostPair(price, protectPrice));
    }

    public Integer getCostByChallengeLevel(Integer challengeLevel, boolean isProtect) {
        CostPair costPair = enhanceCostMap.get(challengeLevel);
        return isProtect ? costPair.getEnhanceProtectPrice() : costPair.getEnhancePrice();
    }

    private class CostPair {
        private Integer enhancePrice;
        private Integer enhanceProtectPrice;

        public CostPair(Integer enhancePrice, Integer enhanceProtectPrice) {
            this.enhancePrice = enhancePrice;
            this.enhanceProtectPrice = enhanceProtectPrice;
        }

        public Integer getEnhancePrice() {
            return enhancePrice;
        }

        public void setEnhancePrice(Integer enhancePrice) {
            this.enhancePrice = enhancePrice;
        }

        public Integer getEnhanceProtectPrice() {
            return enhanceProtectPrice;
        }

        public void setEnhanceProtectPrice(Integer enhanceProtectPrice) {
            this.enhanceProtectPrice = enhanceProtectPrice;
        }
    }
}
