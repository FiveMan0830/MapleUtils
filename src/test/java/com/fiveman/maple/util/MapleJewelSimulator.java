package com.fiveman.maple.util;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class MapleJewelSimulator {
    private static Map<Integer, Map<String, Integer>> randomTable = new HashMap<>();
    private static final Integer intervalCount = 10;
    private static final Integer tryCount = 100000;
    private static final Integer AttendLevel = 7;
    private static final String SUCCESS_STRING = "Success";
    private static final String FAILURE_STRING = "Failure";
    private static final String RESET_STRING = "Reset";


    @Before
    public void setup() {
        randomTable.put(1, createRandomPercentage(100, 0, 0));
        randomTable.put(2, createRandomPercentage(40, 60, 0));
        randomTable.put(3, createRandomPercentage(40, 55, 5));
        randomTable.put(4, createRandomPercentage(30, 60, 10));
        randomTable.put(5, createRandomPercentage(30, 60, 10));
        randomTable.put(6, createRandomPercentage(80, 0, 20));
        randomTable.put(7, createRandomPercentage(40, 40, 20));
        randomTable.put(8, createRandomPercentage(40, 40, 20));
        randomTable.put(9, createRandomPercentage(30, 40, 30));
        randomTable.put(10, createRandomPercentage(30, 40, 30));

    }

    @Test
    public void test() {
        List<Result> resultList = new ArrayList<>();
        double sum = 0;
        int maximum = 0;
        int minimum = 100;
        for (int i = 0; i < tryCount; i++) {
            Result result = getPriceForRound(AttendLevel);
            resultList.add(result);
            sum += result.getPrice();
            if(result.getPrice() > maximum) {
                maximum = result.getPrice();
            }
            if(result.getPrice() < minimum) {
                minimum = result.getPrice();
            }
//            System.out.println("Round " + (i+1));
//            System.out.println(result.getPrice());
        }

        printData(sum, maximum, minimum);
        outputDistribute(resultList.stream().sorted(Comparator.comparing(Result::getPrice)).collect(Collectors.toList()));
    }

    private void printData(double sum, int maximum, int minimum) {
        System.out.println("-----------------------------------------------");
        System.out.println("AttendLevel : " + AttendLevel);
        System.out.println("Average Price: " + (sum / tryCount));
        System.out.println("Maximum Price: " + maximum);
        System.out.println("Minimum Price: " + minimum);
        System.out.println("-----------------------------------------------");
    }

    private void outputDistribute(List<Result> sorted) {
        Integer interval = tryCount/intervalCount;
        Integer percentageInterval = 100/intervalCount;
        for(int i = 0; i < intervalCount; i++){
            Integer start = i * interval;
            Integer end = (i * interval + interval);
            Integer sum = 0;
            Integer max = 0;
            Integer min = 1000000;
            for (int j = start; j < end; j++) {
                sum += sorted.get(j).getPrice();
                if(sorted.get(j).getPrice() > max) {
                    max = sorted.get(j).getPrice();
                }
                if(sorted.get(j).getPrice() < min) {
                    min = sorted.get(j).getPrice();
                }
            }
            System.out.println("-----------------------------------------------");
            System.out.println("From " + i*percentageInterval + "% to " + (i*percentageInterval + percentageInterval) + "%");
            System.out.println("Average Price = " + sum/interval);
//            System.out.println("Min = " + min);
            System.out.println("Max = " + max);

        }
        System.out.println("-----------------------------------------------");
    }

    private Map<String, Integer> createRandomPercentage(int success, int failure, int reset) {
        Map<String, Integer> map = new HashMap<>();

        map.put(SUCCESS_STRING, success);
        map.put(FAILURE_STRING, failure);
        map.put(RESET_STRING, reset);
        return map;
    }

    private Result getPriceForRound(int attendLevel) {
        Result result = new Result(0);
        while(result.getLevel() != attendLevel){
            doLevelUp(result);
        }
        return result;
    }

    private void doLevelUp(Result result) {
        int upgradeLevel = result.getLevel()+1;
        Random random = new Random();
        int randomNumber = random.nextInt(100);
        // Success
        if (randomNumber < randomTable.get(upgradeLevel).get(SUCCESS_STRING)){
            result.increasePrice();
            result.setLevel(result.getLevel()+1);
        }
        // FAILURE
        else if (randomNumber < randomTable.get(upgradeLevel).get(SUCCESS_STRING) + randomTable.get(upgradeLevel).get(FAILURE_STRING)) {
            result.increasePrice();
            result.setLevel(result.getLevel()-1);
        }
        // RESET
        else if (randomNumber < randomTable.get(upgradeLevel).get(SUCCESS_STRING) + randomTable.get(upgradeLevel).get(FAILURE_STRING) + randomTable.get(upgradeLevel).get(RESET_STRING)) {
            result.increasePrice();
            result.setLevel(0);
        }
        // ERROR
        else {
            System.out.println("ERROR");
            System.out.println(result.getLevel() + ":" + randomNumber);
            throw new RuntimeException();
        }
    }

    public class Result {
        private Integer price;
        private int level;
        public Result(int price){
            this.price = price;
            this.level = 0;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public void increasePrice() {
            if(level > 5){
                price+=2;
                return;
            } else if (level > 8) {
                price+=3;
                return;
            }
            price+=1;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }
    }
}
