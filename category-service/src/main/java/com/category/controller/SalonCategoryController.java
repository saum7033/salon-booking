package com.category.controller;

import com.category.dto.SalonDTO;
import com.category.dto.UserDTO;
import com.category.modal.Category;
import com.category.service.CategoryService;
import com.category.service.client.SalonFeignClient;
import com.category.service.client.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories/salon-owner")
public class SalonCategoryController {
    private final CategoryService categoryService;
    private final SalonFeignClient salonFeignClient;
    private final UserFeignClient userFeignClient;

    @PostMapping
    public ResponseEntity<Category> createCategory(
            @RequestBody Category category, @RequestHeader("Authorization") String jwt
    ) throws Exception {
        com.zosh.user.service.model.User user = userFeignClient.getUserProfile(jwt).getBody();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFullName(user.getFullName());
        userDTO.setEmail(user.getEmail());
        SalonDTO salonDTO = salonFeignClient.getSalonByOwnerId(userDTO.getId()).getBody();
        Category savedCategory = categoryService.saveCategory(category,salonDTO);
        return ResponseEntity.ok(savedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(
            @PathVariable Long id, @RequestHeader("Authorization") String jwt
    )throws Exception{
        com.zosh.user.service.model.User user = userFeignClient.getUserProfile(jwt).getBody();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFullName(user.getFullName());
        userDTO.setEmail(user.getEmail());
        SalonDTO salonDTO = salonFeignClient.getSalonByOwnerId(userDTO.getId()).getBody();
        categoryService.deleteCategoryById(id,salonDTO.getId());
        return ResponseEntity.ok("category deleted successfully");
    }
}
