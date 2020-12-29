package com.staxrt.tutorial;

import com.staxrt.tutorial.entity.UserEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testGetAllUsers() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/users",
				HttpMethod.GET, entity, String.class);

		Assert.assertNotNull(response.getBody());
	}

	@Test
	public void testGetUserById() {
		UserEntity userEntity = restTemplate.getForObject(getRootUrl() + "/users/1", UserEntity.class);
		System.out.println(userEntity.getFirstName());
		Assert.assertNotNull(userEntity);
	}

	@Test
	public void testCreateUser() {
		UserEntity userEntity = new UserEntity();
		userEntity.setEmail("admin@gmail.com");
		userEntity.setFirstName("admin");
		userEntity.setLastName("admin");
		userEntity.setCreatedBy("admin");
		userEntity.setUpdatedBy("admin");

		ResponseEntity<UserEntity> postResponse = restTemplate.postForEntity(getRootUrl() + "/users", userEntity, UserEntity.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
	}

	@Test
	public void testUpdatePost() {
		int id = 1;
		UserEntity userEntity = restTemplate.getForObject(getRootUrl() + "/users/" + id, UserEntity.class);
		userEntity.setFirstName("admin1");
		userEntity.setLastName("admin2");

		restTemplate.put(getRootUrl() + "/users/" + id, userEntity);

		UserEntity updatedUserEntity = restTemplate.getForObject(getRootUrl() + "/users/" + id, UserEntity.class);
		Assert.assertNotNull(updatedUserEntity);
	}

	@Test
	public void testDeletePost() {
		int id = 2;
		UserEntity userEntity = restTemplate.getForObject(getRootUrl() + "/users/" + id, UserEntity.class);
		Assert.assertNotNull(userEntity);

		restTemplate.delete(getRootUrl() + "/users/" + id);

		try {
			userEntity = restTemplate.getForObject(getRootUrl() + "/users/" + id, UserEntity.class);
		} catch (final HttpClientErrorException e) {
			Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}

}
