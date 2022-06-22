package com.expertautomationteam;

import base.CommonAPI;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BasiccomponentsTest extends CommonAPI {
    @Test
    public void test1(){
        String title= getPagetitle();
        System.out.println(title);
        Assert.assertEquals("Expert Automation Team | QA Automation", title);

    }
    @Test
    public void test2(){

    }

}

