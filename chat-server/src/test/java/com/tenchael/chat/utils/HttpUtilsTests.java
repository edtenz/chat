package com.tenchael.chat.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class HttpUtilsTests {

    @Test
    public void testParameterMap() {
        String uri = "http://localhost:8081/index.html?token=123&uname=teng";
        Map<String, String> params = HttpUtils.parameterMap(uri);
        Assert.assertEquals("123", params.get("token"));
        Assert.assertEquals("teng", params.get("uname"));
        Assert.assertEquals(null, params.get("pass"));
    }

    @Test
    public void testParameterMap_noValue() {
        String uri = "http://localhost:8081/index.html/?token=&uname=teng";
        Map<String, String> params = HttpUtils.parameterMap(uri);
        Assert.assertEquals("", params.get("token"));
        Assert.assertEquals("teng", params.get("uname"));
        Assert.assertEquals(null, params.get("pass"));
    }

}
