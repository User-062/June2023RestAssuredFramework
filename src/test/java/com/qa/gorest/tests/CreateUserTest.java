package com.qa.gorest.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIConstants;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.User;
import com.qa.gorest.utils.ExcelUtil;
import com.qa.gorest.utils.StringUtils;

import static org.hamcrest.Matchers.*;

public class CreateUserTest extends BaseTest{
		
	@BeforeMethod
	public void getUserSetup() {
	restClient = new RestClient(prop, baseURI);
	}
	
	
	@DataProvider
	public Object[][] getUserTestData() {
		return new Object[][] {
			{"Subodh", "male", "active"},
			{"Seema", "female", "inactive"},
			{"Madhuri", "female", "active"}
		};
	}
	
	@DataProvider
	public Object[][] getUserTestSheetData() {
		return ExcelUtil.getTestData(APIConstants.GOREST_USER_SHEET_NAME);
	
	}
	
	
	@Test(dataProvider = "getUserTestSheetData")
	public void createAllUsersTest(String name, String gender, String status) {
		
		//1.post
		User user = new User(name,StringUtils.getRandomEmailId(),gender,status);
		
		Integer userId =restClient.post(GOREST_ENDPOINT, "json", user, true,true)
			.then().log().all()
				.assertThat().statusCode(APIHttpStatus.CREATED_201.getCode())
					.extract().path("id");
		
		System.out.println("User ID: " + userId);
		
		RestClient clientGet   = new RestClient(prop,baseURI);
		
		//2.GET : 
		
		clientGet.get(GOREST_ENDPOINT+"/"+userId, true,true)
		.then().log().all()
			.assertThat().statusCode(APIHttpStatus.OK_200.getCode())
				.assertThat().body("id",equalTo(userId));
		
		System.out.println("End Test");
	}
}
