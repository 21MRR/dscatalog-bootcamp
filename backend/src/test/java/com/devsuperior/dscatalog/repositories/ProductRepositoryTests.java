package com.devsuperior.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.tests.Factory;

@DataJpaTest
public class ProductRepositoryTests {

	@Autowired
	private ProductRepository repository;

	private long exintingId;
	private long nonExistingId;
	private long countTotalProduct;
	private long countTotalProduct1;

	@BeforeEach
	void setUp() throws Exception {
		exintingId = 1L;
		nonExistingId = 1000L;
		countTotalProduct = 25L;
		countTotalProduct1 = 26L;

	}

	@Test
	public void saveShouldPersistWhitAutoincrementWhenIsNull() {

		Product product = Factory.createProduct();
		product.setId(null);

		product = repository.save(product);

		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProduct + 1, product.getId());

	}

	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {

		repository.deleteById(exintingId);
		Optional<Product> result = repository.findById(exintingId);
		Assertions.assertFalse(result.isPresent());
	}

	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {

		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(nonExistingId);
		});

	}

	@Test
	public void findShouldObjectWhenIdExists() {

		repository.findById(countTotalProduct);
		Optional<Product> result = repository.findById(countTotalProduct);
		Assertions.assertTrue(result.isPresent());

	}
	
	@Test
	public void findShouldObjectWhenIdDoesNotExists() {
		
		repository.findById(countTotalProduct1);
		Optional<Product> result = repository.findById(countTotalProduct1);
		Assertions.assertFalse(result.isPresent());
		
	}
	

}
