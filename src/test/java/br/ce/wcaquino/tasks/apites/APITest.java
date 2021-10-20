package br.ce.wcaquino.tasks.apites;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
	
	
	@BeforeClass
	public static void setup() {
		
		RestAssured.baseURI = "http://localhost:8081/tasks-backend";
	}
	
	@Test
	public void deveRetornarTarefas() {
		
		RestAssured.given()
		.when()
			.get("/todo")
		.then()
			.statusCode(200);
		
	}
	
	@Test
	public void deveAdicionarTarefaComSucesso() {
		
		 
		RestAssured.given()
			.body("{\"task\": \"Testando a API\",\"dueDate\": \"2022-10-20\"}")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
		.statusCode(201);//Recurso criado
		
	}
	
	@Test
	public void naoDeveAdicionarTarefaComDataPassada() {
		
		
		RestAssured.given()
			.body("{\"task\": \"Testando a API\",\"dueDate\": \"2021-09-10\"}")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"));
		
	}

}
