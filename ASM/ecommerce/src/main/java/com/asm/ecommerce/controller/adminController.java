package com.asm.ecommerce.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.asm.ecommerce.model.Category;
import com.asm.ecommerce.service.CategoryService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class adminController {

    @Autowired
	private CategoryService categoryService;
    
    @GetMapping("/")
    public String index(){
        return"admin/index";
    }

    @GetMapping("/loadAddProduct")
    public String loadAddProduct(){
        return"admin/add-product";
    }

    @GetMapping("/category")
    public String category(Model m){
        m.addAttribute("categorys", categoryService.getAllCategory());
        return"admin/category";
    }

    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute Category category,@RequestParam("file") MultipartFile file, HttpSession session) throws IOException{
        String imageName = file != null ? file.getOriginalFilename() : "default.jpg";
		category.setImageName(imageName);

        Boolean existCategory = categoryService.existCategory(category.getName());

        if (existCategory) {
			session.setAttribute("errorMsg", "Category Name already exists");
		} else {
            Category saveCategory = categoryService.saveCategory(category);
            if (ObjectUtils.isEmpty(saveCategory)) {
				session.setAttribute("errorMsg", "Not saved ! internal server error");
			} else {
                String pathToSave = "src/main/resources/static/img/category_img";
                File imgDir = new File(pathToSave);

                if (!imgDir.exists()) {
                    imgDir.mkdirs();
                }

				Path path = Paths.get(imgDir.getAbsolutePath() + File.separator + file.getOriginalFilename());
                System.out.println("Saving image to: " + path.toString());

				// System.out.println(path);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                session.setAttribute("succMsg", "Saved successfully");
            }
        }

        return"redirect:/admin/category";
    }

    @GetMapping("/deleteCategory/{id}")
	public String deleteCategory(@PathVariable int id, HttpSession session) {
		Boolean deleteCategory = categoryService.deleteCategory(id);

		if (deleteCategory) {
			session.setAttribute("succMsg", "category delete success");
		} else {
			session.setAttribute("errorMsg", "something wrong on server");
		}

		return "redirect:/admin/category";
	}

    @GetMapping("/loadEditCategory/{id}")
	public String loadEditCategory(@PathVariable int id, Model m) {
		m.addAttribute("category", categoryService.getCategoryById(id));
		return "admin/edit_category";
	}

    @PostMapping("/updateCategory")
	public String updateCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file,
			HttpSession session) throws IOException {

		Category oldCategory = categoryService.getCategoryById(category.getId());
		String imageName = file.isEmpty() ? oldCategory.getImageName() : file.getOriginalFilename();

		if (!ObjectUtils.isEmpty(category)) {

			oldCategory.setName(category.getName());
			oldCategory.setIsActive(category.getIsActive());
			oldCategory.setImageName(imageName);
		}

		Category updateCategory = categoryService.saveCategory(oldCategory);

		if (!ObjectUtils.isEmpty(updateCategory)) {

			if (!file.isEmpty()) {
				String pathToSave = "src/main/resources/static/img/category_img";
                File imgDir = new File(pathToSave);

                if (!imgDir.exists()) {
                    imgDir.mkdirs();
                }

				Path path = Paths.get(imgDir.getAbsolutePath() + File.separator + file.getOriginalFilename());
                System.out.println("Saving image to: " + path.toString());

				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			}

			session.setAttribute("succMsg", "Category update success");
		} else {
			session.setAttribute("errorMsg", "something wrong on server");
		}

		return "redirect:/admin/loadEditCategory/" + category.getId();
	}
}
