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

public class TC_API_CreateWork {
	private String token;
	private Response response;
	private ResponseBody responseBody;
	private JsonPath jsonBody;
	
	//Tạo Data
	private String myWork = "tester";
	private String myExperience = "1 year Exp";
	private String myEducation = "university";
	
	@BeforeClass
	public void init() {
		// Init data
		String baseUrl = PropertiesFileUtils.getProperty("baseurl");
		String createWorkPath = PropertiesFileUtils.getProperty("createWorkPath");
		String token = PropertiesFileUtils.getToken();

		RestAssured.baseURI = baseUrl;
		RestAssured.basePath = createWorkPath;
		// Make body
		Map<String, Object> body = new HashMap<String, Object>();
             body.put("nameWork",myWork);
             body.put("experience",myExperience);
             body.put("education", myEducation);
		RequestSpecification request = RestAssured.given()
				.contentType(ContentType.JSON)
				.header("token",token)
				.body(body);
		response = request.post();
		responseBody = response.body();
		jsonBody = responseBody.jsonPath();
		System.out.println(" " + responseBody.asPrettyString());
	}
	@Test(priority = 0)
	public void TC01_Validate201Created() {
   // kiểm chứng status code
   Assert.assertEquals(201,response.getStatusCode(),"Status Check Failed!");
	}
	@Test(priority = 1)
	public void TC02_ValidateWorkId() {
    // kiểm chứng id
    Assert.assertNotNull(jsonBody.getString("id"),"ID Check Failed!");
	}
	@Test(priority = 2)
	public void TC03_ValidateNameOfWorkMatched() {
    // kiểm chứng tên công việc nhận được có giống lúc tạo
    String ResponseNameWork=jsonBody.getString("nameWork");
    Assert.assertEquals(myWork, ResponseNameWork,"Name of work not matched!");
	}
	@Test(priority = 3)
	public void TC04_ValidateExperienceMatched() {
    // kiểm chứng kinh nghiệm nhận được có giống lúc tạo
    String ResponseExperience = jsonBody.getString("experience");
    Assert.assertEquals(myExperience, ResponseExperience,"Experience not matched!");
	}
	@Test(priority = 4)
	public void TC05_ValidateEducationMatched() {
	// kiểm chứng học vấn nhận được có giống lúc tạo
    String ResponseEducation = jsonBody.getString("education");
    Assert.assertEquals(myEducation,ResponseEducation,"Education not Matched!");
	}
}




