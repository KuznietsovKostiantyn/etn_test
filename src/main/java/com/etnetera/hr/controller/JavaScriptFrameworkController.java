package com.etnetera.hr.controller;

import com.etnetera.hr.rest.Errors;
import com.etnetera.hr.rest.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Simple REST controller for accessing application logic.
 * 
 * @author Etnetera
 *
 */
@RestController
public class JavaScriptFrameworkController extends EtnRestController {

	private final JavaScriptFrameworkRepository repository;

	@Autowired
	public JavaScriptFrameworkController(JavaScriptFrameworkRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/frameworks")
	public Iterable<JavaScriptFramework> frameworks() {
		return repository.findAll();
	}

	@PostMapping({"/add", "/update"})
	public ResponseEntity<?> saveJavascriptFramework(@RequestBody @Valid JavaScriptFramework framework, BindingResult result){
		if(!result.hasErrors())
			return ResponseEntity.ok(repository.save(framework));

		return getRespond(result);
	}

	@GetMapping("/findByName")
	public ResponseEntity<?> findJavascriptFrameworkByName(@RequestParam String name) {
		ValidationError error = new ValidationError();
		if(name != null) {
			JavaScriptFramework framework = repository.findByName(name);

			if(framework == null) {
				error.setField("framework");
				error.setMessage("is not finded");
			} else return ResponseEntity.ok(framework);

		} else {
			error.setField("name");
			error.setMessage("is null");
		}

		return getRespond(error);
	}

	@GetMapping("/deprecated")
	public Iterable<JavaScriptFramework> findDeprecatedJavascriptFramework() {
		return repository.findByDeprecationDateBefore(LocalDate.now());
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Errors> delJavascriptFrameworkById(@RequestParam Long id) {
		ValidationError error = new ValidationError();
		if(id != null)
			repository.deleteById(id);
		else {
			error.setField("id");
			error.setMessage("is null");
		}

		return getRespond(error);
	}

	private ResponseEntity<Errors> getRespond(BindingResult result) {
		Errors errors = new Errors();
		List<ValidationError> errorList = result.getFieldErrors()
				.stream()
				.map(e -> new ValidationError(e.getField(), e.getCode()))
				.collect(Collectors.toList());

		errors.setErrors(errorList);
		return errorList.isEmpty() ? ResponseEntity.ok().body(errors) : ResponseEntity.badRequest().body(errors);
	}

	private ResponseEntity<Errors> getRespond(ValidationError error) {
		Errors errors = new Errors();
		errors.setErrors(Collections.singletonList(error));
		return error.getField() == null ? ResponseEntity.ok().body(errors) : ResponseEntity.badRequest().body(errors);
	}

}
