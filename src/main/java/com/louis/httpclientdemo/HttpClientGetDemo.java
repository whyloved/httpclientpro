package com.louis.httpclientdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.louis.dto.TianQiResponseEntity;
import com.louis.pojo.UserInfo;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class HttpClientGetDemo {
    public static void main(String[] args) {
        HttpClientGetDemo httpClientGetDemo = new HttpClientGetDemo();
//        httpClientGetDemo.httpGet();
        httpClientGetDemo.httpPost();

    }

    public void httpGet(){
        // 创建 HttpClient 客户端
        CloseableHttpClient aDefault = HttpClients.createDefault();

        //创建httpget请求
        String url = "http://t.weather.sojson.com/api/weather/city/101280101";
        HttpGet httpGet = new HttpGet(url);
        // 设置长连接
        httpGet.setHeader("Connection","keep-alive");
        // 设置代理（模拟浏览器版本）
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.119 Safari/537.36");

        CloseableHttpResponse response = null;
        try {
            //执行httpget请求
            response = aDefault.execute(httpGet);
            HttpEntity entity = response.getEntity();
            //把获取的实体转为json字符串
            String strjson = EntityUtils.toString(entity);
            //System.out.println(strjson);
            //创建对象映射类,用于转换字符串-->对象
            ObjectMapper objectMapper = new ObjectMapper();
            TianQiResponseEntity tianQiResponseEntity = objectMapper.readValue(strjson, TianQiResponseEntity.class);
            System.out.println(tianQiResponseEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //释放连接。无论执行方法是否成功，都必须释放连接
            if (response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (aDefault != null){
                try {
                    aDefault.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * post参数提交
     */
    public void httpPost(){
        // 创建 HttpClient 客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //创建httpPost请求
        String url = "http://localhost:8080/background_sys2/loginCheck";
        HttpPost httpPost = new HttpPost(url);
        // 设置长连接
        httpPost.setHeader("Connection","keep-alive");
        // 设置代理（模拟浏览器版本）
        httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.119 Safari/537.36");
        httpPost.addHeader("Context-type","application/json; charset=utf-8");
        httpPost.setHeader("Accept", "application/json");

        String json = "{\"username\":\"panghu\",\"password\":\"123456\"}";

        CloseableHttpResponse response = null;
        try {
            StringEntity stringEntity = new StringEntity(json, Charset.forName("UTF-8"));
            //post传参最重要的是声明ContentType为json数据
            stringEntity.setContentType("application/json");

            httpPost.setEntity(stringEntity);
            //执行httPost请求
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            //把获取的实体转为json字符串
            String strjson = EntityUtils.toString(entity);
            System.out.println(strjson);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //释放连接。无论执行方法是否成功，都必须释放连接
            if (response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (httpClient != null){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
