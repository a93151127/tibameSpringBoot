package com.tibame.domain;
//資料模組類別 
public class DataUtility {
	//cities Data Model
	public static String[] getCitites() {
		//五都字串陣列(或者經由資料庫存取產生)
		String[] cities=
				new String[] {"台北市","新北市","桃園市","台中市","台南市","高雄市"};
		return cities;
	}

}
