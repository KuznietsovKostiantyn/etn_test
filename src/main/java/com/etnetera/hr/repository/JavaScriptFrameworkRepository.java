package com.etnetera.hr.repository;

import org.springframework.data.repository.CrudRepository;

import com.etnetera.hr.data.JavaScriptFramework;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring data repository interface used for accessing the data in database.
 * 
 * @author Etnetera
 *
 */
public interface JavaScriptFrameworkRepository extends CrudRepository<JavaScriptFramework, Long> {

    JavaScriptFramework findByName(String name);

    Iterable<JavaScriptFramework> findByDeprecationDateBefore (LocalDate before);

}
