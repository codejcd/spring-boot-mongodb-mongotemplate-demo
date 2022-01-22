package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@SpringBootApplication
public class SpringbootMongoDbDemo2Application implements CommandLineRunner {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootMongoDbDemo2Application.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {

		// 최초 실행시 삭제
		mongoTemplate.remove(new Query(), "customer");
		
		// Customer 객체 생성
		Customer Bob = new Customer("Bob", "Smith");
		Customer John = new Customer("John", "Smith");
		Customer Alice = new Customer("Alice", "Smith");
		
		// mongoDB에 insert 수행. insert 메소드로 대체 가능하나 차이가 있음.
		mongoTemplate.save(Bob);
		mongoTemplate.save(John);
		mongoTemplate.save(Alice);
		
		// mongoDB에 Update 수행.
		Alice.setFirstName("Alice");
		Alice.setLastName("Chris");
		// upate 메소드로 대체 가능하나 차이가 있음.
		mongoTemplate.save(Alice); 
		
		// mongoDB에 조회 수행.
		Query query = new Query();
		Criteria criteria = Criteria.where("firstName").is("Bob");
		query.addCriteria(criteria);
		Customer temp = mongoTemplate.findOne(query, Customer.class);
		
		// mongoDB에 삭제 수행.
		mongoTemplate.remove(temp);
		
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (Customer customer : mongoTemplate.findAll(Customer.class)) {
			System.out.println(customer);
		}
		System.out.println();
		
		System.out.println("Customer found with findByFirstName('Alice'):");
		query = new Query(Criteria.where("firstName").is("Alice"));
		System.out.println(mongoTemplate.findOne(query, Customer.class));
		System.out.println("--------------------------------");

		System.out.println("Customers found with findByLastName('Smith'):");
		System.out.println("--------------------------------");
		query = new Query(Criteria.where("lastName").is("Smith"));
		for (Customer customer : mongoTemplate.find(query, Customer.class)) {
			System.out.println(customer);
		}
		
	}
}
