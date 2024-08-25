package com.company.inventory.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.inventory.dao.ICategoryDao;
import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseRest;


@Service
public class CategoryService implements ICategoryServiceImpl {
	
	@Autowired
	private ICategoryDao categoryDao;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoryResponseRest> search() {
		CategoryResponseRest responseRest = new CategoryResponseRest();
		try {
			List<Category> categories = (List<Category>) categoryDao.findAll();
			
			responseRest.getCategoryResponse().setCategory(categories);
			responseRest.setMetadata("Respuesta OK", "00", "Respuesta exitosa");
			
		} catch (Exception e) {

			responseRest.setMetadata("No OK", "-1", "Error en la consulta");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(responseRest, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		return new ResponseEntity<CategoryResponseRest>(responseRest, HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoryResponseRest> searchById(Long id) {
		CategoryResponseRest responseRest = new CategoryResponseRest();
		List<Category> list = new ArrayList<>();
		try {

			Optional<Category> category = categoryDao.findById(id);
			
			if(category.isPresent()) {
				list.add(category.get());
				responseRest.getCategoryResponse().setCategory(list);
				responseRest.setMetadata("Respuesta OK", "01", "Categoria encontrada");
			}else {
				responseRest.setMetadata("No OK", "-2", "Categoria no encontrada");
				return new ResponseEntity<CategoryResponseRest>(responseRest, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {

			responseRest.setMetadata("No OK", "-1", "Error en la consulta");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(responseRest, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		return new ResponseEntity<CategoryResponseRest>(responseRest, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<CategoryResponseRest> save(Category category) {
		CategoryResponseRest responseRest = new CategoryResponseRest();
		List<Category> list = new ArrayList<>();
		try {

			Category categorySaved = categoryDao.save(category);
			
			if(categorySaved != null) {
				list.add(categorySaved);
				responseRest.getCategoryResponse().setCategory(list);
				responseRest.setMetadata("Respuesta OK", "02", "Categoria guardada");

			}else {
				responseRest.setMetadata("No OK", "-3", "Categoria no guardada");
				return new ResponseEntity<CategoryResponseRest>(responseRest, HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {

			responseRest.setMetadata("No OK", "-1", "Error al añadir");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(responseRest, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		return new ResponseEntity<CategoryResponseRest>(responseRest, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<CategoryResponseRest> update(Category category, Long id) {
		CategoryResponseRest responseRest = new CategoryResponseRest();
		List<Category> list = new ArrayList<>();
		try {

			Optional<Category> categorySearch = categoryDao.findById(id);
			
			if(categorySearch.isPresent()) {
				
				categorySearch.get().setName(category.getName());
				categorySearch.get().setDescription(category.getDescription());

				Category categoryUpdate = categoryDao.save(categorySearch.get());
				
				if(categoryUpdate != null) {
					list.add(categoryUpdate);
					responseRest.getCategoryResponse().setCategory(list);
					responseRest.setMetadata("Respuesta OK", "03", "Categoria actualizada");
				}else {
					responseRest.setMetadata("No OK", "-4", "Categoria no se pudo actualizar");
					return new ResponseEntity<CategoryResponseRest>(responseRest, HttpStatus.NOT_FOUND);
				}
			}else {
				responseRest.setMetadata("No OK", "-2", "Categoria no encontrada");
				return new ResponseEntity<CategoryResponseRest>(responseRest, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {

			responseRest.setMetadata("No OK", "-1", "Error al actualizar");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(responseRest, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		return new ResponseEntity<CategoryResponseRest>(responseRest, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<CategoryResponseRest> deleteById(Long id) {
		CategoryResponseRest responseRest = new CategoryResponseRest();
		try {

			categoryDao.deleteById(id);
			responseRest.setMetadata("Respuesta OK", "04", "Categoria eliminada");

		} catch (Exception e) {

			responseRest.setMetadata("No OK", "-1", "Error en la eliminación");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(responseRest, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		return new ResponseEntity<CategoryResponseRest>(responseRest, HttpStatus.OK);
	}

}
