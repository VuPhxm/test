package com.api.auto.testcase;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.api.auto.utils.PropertiesFileUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class TC_API_Login {
	private Response response; //lưu response của API
	private ResponseBody resBody;//body của response
	private JsonPath bodyJson;//body của response đã được cover sang Json
@BeforeClass
public void unit() {
	//thiết lập End Point
	String baseURL = PropertiesFileUtils.getProperty("baseurl");
	String loginPath = PropertiesFileUtils.getProperty("loginPath");
	String account = PropertiesFileUtils.getProperty("account");
	String password = PropertiesFileUtils.getProperty("password");
	//thiết lập endpoint
	RestAssured.baseURI=baseURL;
	RestAssured.basePath = loginPath;
	
/*
	Map<String, Object> accountbody = new HashMap<String, Object>();
	accountbody.put("account",account);
	accountbody.put("password",password);
*/	
	
	String accountbody = "{\"account\": \"" + account + "\",\"password\": \"" + password + "\"}";
	RequestSpecification request = RestAssured.given()
			.contentType(ContentType.JSON)
			.body(accountbody);
	response = request.post();
		resBody = response.getBody();
		bodyJson = resBody.jsonPath(); 
		System.out.println(" " + resBody.asPrettyString());

}
@Test(priority = 0)
public void TC01_Validate200Ok() {
	//Kiểm tra response HTTP status có = 200 không
		Assert.assertEquals(200,response.getStatusCode(),"Status Check Failed!");
}
@Test(priority = 1)
public void TC02_ValidateMessage() {
	//Kiểm tra response trả về có "message": "Đăng nhập thành công"
	Assert.assertTrue(bodyJson.getString("message").contains("Đăng nhập thành công"),"Message Check Failed!");

}
@Test(priority = 2)
private void TC03_ValidateToken() {
	//Kiểm tra response trả về có "token"
	Assert.assertNotNull(bodyJson.getString("token"),"Check token failed") ;
	//lưu biến token
	String token = bodyJson.getString("token");
	PropertiesFileUtils.saveToken(token);
}
@Test(priority = 4)
public void TC04_ValidateUserAccount() {
	String bodyAccount = PropertiesFileUtils.getProperty("account");
	String ResponseAccount = bodyJson.getString("user.account");
	Assert.assertEquals(bodyAccount,ResponseAccount,"User Account not match!");
}
@Test(priority = 5)
public void TC05_ValidateUserPassword() {
	String bodyPassword = PropertiesFileUtils.getProperty("password");
	String ResponsePassword = bodyJson.getString("user.password");
	Assert.assertEquals(bodyPassword, ResponsePassword,"User Password not match!");
}
@Test(priority = 3)
public void TC06_ValidateUserType() {
	Assert.assertEquals(bodyJson.getString("user.type"),"UNGVIEN", "User Type not match!");
}
}

