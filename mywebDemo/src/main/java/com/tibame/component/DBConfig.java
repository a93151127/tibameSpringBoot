package com.tibame.component;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.tibame.domain.Company;
import com.tibame.domain.Customers;
import com.tibame.domain.Employees;
import com.tibame.domain.Engine;
import com.tibame.domain.Transmission;

//資料庫有關的配置檔(class)
@Configuration
public class DBConfig {
	//Attribute 注入環境物件 可獲取到application.properties設定
	@Autowired
	private Environment env;
	//寫上一個方法 產生一個DataSource物件
	//產生的DataSource可以應用系統共用 生產個別使用Connection連接物件
	@Bean  //定義成一個Spring Bean 進入Spring Container
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) //singletion 獨一模式 只有一個物件形成共用
	public SQLServerDataSource dataSource() {
		System.out.println("建立起一個共用的DataSource物件");
		//建構一個DataSource物件
		SQLServerDataSource datasource=new SQLServerDataSource();
		//設定屬性 連接字串 登入帳號與密碼
		
		datasource.setURL(env.getProperty("spring.datasource.url"));
		datasource.setUser(env.getProperty("spring.datasource.username"));
		datasource.setPassword(env.getProperty("spring.datasource.password"));
		System.out.println(datasource.getURL());
		return datasource;
	}

	@Bean(name="tibame")
	@Order(1)
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public Company company() {
		System.out.println("1. 公司物件建構...");
		Company com=new Company();
		com.setCompanyName("Tibame");
		com.setAddress("台北市");
		com.setPhone("02-12345678");
		return com;
	}
	
	
	//產生一個個體員工物件
	@Bean
	@Order(2)
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public Employees employees(Company com) {
		System.out.println("2. 員工物件建構...");
		Employees emp=new Employees();
		emp.setCompany(com);
		return emp;
	}
	
	//配置汽缸與變數箱Spring Bean
	//ConfigurableBeanFactory.SCOPE_PROTOTYPE 每次請求產生一個個體的Instance物件
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public Engine engine() {
		return new Engine("V8",5);
	}
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public Transmission transmission() {
		return new Transmission("Sliding");
	}
	
	//使用name定義一個Bean
	@Bean(name="cust")
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public Customers customers() {
		Customers customers=new Customers();
		//隨機產生編號
		int id=(int)(Math.random()*1000)+1;
		customers.setCustomerId("ID:"+id);
		return customers;
		
	}
	
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public Customers customers2() {
		Customers customers=new Customers();
		customers.setCustomerId("0001");
		return customers;
		
	}
	
	
}
