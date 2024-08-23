package com.company.inventory.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.inventory.dao.ICategoryDao;
import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseRest;


@Service
public class CategoryService implements ICategoryService {
	
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

}
