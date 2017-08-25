package com.suparat.apisit.sccexecutivesummary.model;

import java.util.ArrayList;

/**
 * Created by Aoh on 8/24/2017 AD.
 */

public class TestData {
    float score;
    String name;
    public TestData(String aName,float aScore){
        this.name = aName;
        this.score = aScore;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ArrayList<TestData> getDataTest(int size){
        ArrayList<TestData> testDatas = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            //testDatas.add(new TestData("Android v"+i, (float) Math.random() * 100));
            int iCount = i + 1;
            testDatas.add(new TestData("Android v"+iCount,iCount) );
        }
        return testDatas;
    }
}
