package com.qa.gorest.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;

import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;


public class GetUserTest extends BaseTest{
	
	@BeforeMethod
	public void getUserSetup() {
	restClient = new RestClient(prop, baseURI);
	}
	
	@Test(enabled = true,priority = 3)
	public void getReqResUsersTest() {
		//restClient = new RestClient(prop, baseURI);
		restClient.get(GOREST_ENDPOINT,true, true)
			.then().log().all()
				.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
	}
	
	//public/v2/users/4432364/?name&status
	@Test(priority = 2)
	public void getUserTest() {
		//restClient = new RestClient(prop, baseURI);
		restClient.get(GOREST_ENDPOINT+"/"+4495579, true, true)
			.then().log().all()
				.assertThat().statusCode(APIHttpStatus.OK_200.getCode())
					.and().body("id", equalTo(4495579));
	}
	
	//url?name&status
	
	@Test(priority = 1)
	public void getUserWithQueryParamasTest() {
		//restClient = new RestClient(prop, baseURI);
		
		
		Map<String,Object> queryParams = new HashMap<String,Object>();
		queryParams.put("name", "Sanjay");
		queryParams.put("status", "active");
		
		restClient.get(GOREST_ENDPOINT,queryParams, null, true, true)
				.then().log().all()
						.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
		}
	
}
