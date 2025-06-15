package com.fiveman.maple.util.starforce;

public class StarforceEnhanceChanceItem {
    private Integer successChance;
    private Integer failChance;
    private Integer breakChance;

    public StarforceEnhanceChanceItem(Integer successChance, Integer failChance, Integer breakChance) {
        if (successChance + failChance + breakChance != 10000)
            throw new RuntimeException();
        this.successChance = successChance;
        this.failChance = failChance;
        this.breakChance = breakChance;
    }

    public Integer getSuccessChance() {
        return successChance;
    }

    public Integer getFailChance() {
        return failChance + successChance;
    }

    public Integer getBreakChance() {
        return breakChance + failChance + successChance;
    }
}
