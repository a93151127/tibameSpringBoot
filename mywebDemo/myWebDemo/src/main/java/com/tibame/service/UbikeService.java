package com.tibame.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.tibame.entity.UbikeData;
//註冊外部自訂的properties
@RestController
@PropertySource("classpath:services.properties")
public class UbikeService {
	//Attribute
	//注入外部定義properties項目 使用SpringEL
	@Value("${outside.ubike.service}")
	private String ubikeService;
	//回應的資訊
	//produces 決定Response Header Content-Type:application/json
	@GetMapping(path="/api/ubike/qry/{area}/rawdata",
			produces="application/json")
	public List<UbikeData> query(@PathVariable("area") String area) {
		
		//2.採用HttpClient對遠端服務提出請求 透過工廠來生產一個HttpClient介面的物件
		CloseableHttpClient httpClient=HttpClients.createDefault();
		//建立一個HttpGet(採用Request Method-GET)
		HttpGet httpGet=new HttpGet(ubikeService);
		//正式提出請求
		String content=null;
		List<UbikeData> query=null;
		try {
			CloseableHttpResponse response=httpClient.execute(httpGet);
			//取出Http Body
			HttpEntity body=response.getEntity();
			//開始讀取回應字串???
			//使用EntrityUtils
			content=EntityUtils.toString(body);
			//TODO 反序列化 進行查詢 在回應查詢結果json
			Gson gson=new Gson();
			UbikeData[] result=gson.fromJson(content,UbikeData[].class);
			//將陣列轉換成List<UbikeData> 取出Stream物件 進行功能化比對與篩選
			List<UbikeData> listData=Arrays.asList(result);
				query=listData.stream()
					.filter(u->u.sarea.equals(area))
					.collect(Collectors.toList());
			
			
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		//3.取回資訊進行區域查詢
		//4.回應查詢結果Json 資料
		return query;
		
	}
}
