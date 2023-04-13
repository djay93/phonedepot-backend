package com.amdocs.phonedepot.controller;

import java.time.Instant;
import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.cache.annotation.Cacheable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.amdocs.phonedepot.model.Product;
import com.amdocs.phonedepot.model.Response;
import com.amdocs.phonedepot.service.ProductServiceImp;
import lombok.RequiredArgsConstructor;

/**
 * @author Dhanapal
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private final ProductServiceImp serviceImp;

//	CREATE
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping()
	public ResponseEntity<Response> saveProduct(@RequestParam("request") @Valid String strProduct,
			@RequestParam("productimage") @Nullable MultipartFile file) {
		try {
			Product product = serviceImp.create(new ObjectMapper().readValue(strProduct, Product.class), file);
			if (product != null) {
				return ResponseEntity.ok(Response.builder().timeStamp(Instant.now()).data(Map.of("product", product))
						.message("Create product").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
			} else {
				return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
						.developerMessage("Please check if exist any category").message("Cannot create the product")
						.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
			}

		} catch (JsonProcessingException e) {
			return ResponseEntity.ok(
					Response.builder().timeStamp(Instant.now()).message("Error creating the product: " + e.getMessage())
							.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}

	}

//	UPDATE
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYEE')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response> updateProduct(@PathVariable("id") Long id,
			@RequestParam("request") @Valid String strProduct,
			@RequestParam("productimage") @Nullable MultipartFile file) {
		try {
			if (serviceImp.exist(id)) {
				Product product = serviceImp.update(id, new ObjectMapper().readValue(strProduct, Product.class), file);
				if (product != null) {
					return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
							.data(Map.of("product", product)).message("Update product with id:" + id)
							.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
				} else {
					return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
							.developerMessage("Please check if exist any category").message("Cannot create the product")
							.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
				}

			} else {
				return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
						.message("The product with id:" + id + " does not exist").status(HttpStatus.BAD_REQUEST)
						.statusCode(HttpStatus.BAD_REQUEST.value()).build());
			}

		} catch (JsonProcessingException e) {
			return ResponseEntity.ok(
					Response.builder().timeStamp(Instant.now()).message("Error updating the product: " + e.getMessage())
							.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}
	}

//	DELETE
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response> deleteProduct(@PathVariable("id") Long id) {
		if (serviceImp.exist(id)) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("product", serviceImp.delete(id))).message("Delete product with id: " + id)
					.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
		} else {
			return ResponseEntity
					.ok(Response.builder().timeStamp(Instant.now()).message("The product " + id + " does not exist")
							.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}
	}

//	SEARCH BY CATEGORY
	//@Cacheable(value = "products", key = "#category")
	@GetMapping(value = "/category/{category}")
	public ResponseEntity<Response> getProductByCategoryName(@PathVariable("category") String category) {
		Collection<Product> listProduct = serviceImp.findByNameCategory(category);
		if (listProduct != null) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now()).data(Map.of("products", listProduct))
					.message("Get product with category called: " + category).status(HttpStatus.OK)
					.statusCode(HttpStatus.OK.value()).build());

		} else {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.message("The product with category called " + category + " does not exist")
					.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}

	}

}
