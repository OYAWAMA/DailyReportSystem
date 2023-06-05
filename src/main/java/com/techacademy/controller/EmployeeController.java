package com.techacademy.controller;

import java.time.LocalDateTime;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.techacademy.entity.Employee;
import com.techacademy.service.EmployeeService;

import javax.validation.Valid;

@Controller
@RequestMapping("employee")
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;

    }

    /** 一覧画面を表示 */
    @GetMapping("/list")
    public String getList(Model model) {
        // 全件検索結果をModelに登録
        model.addAttribute("employeelist", service.getEmployeeList());
        // Employee/list.htmlに画面遷移
        return "employee/list";
    }

    /** User登録画面を表示 */
    @GetMapping("/register")
    public String getRegister(@ModelAttribute Employee employee) {
        // employee登録画面に遷移
        return "employee/register";
    }

    /** User登録処理 */
    @PostMapping("/register")

    public String postRegister(@Valid Employee employee, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "入力が不適切です");
            return "employee/register";
        }
        // createdAtに日時を
        try {
            employee.setCreatedAt(LocalDateTime.now());
            employee.setUpdatedAt(LocalDateTime.now());
            employee.getAuthentication().setEmployee(employee);
            // employee登録
            service.saveEmployee(employee);
        } catch (Exception e) {
            // TODO 自動生成された catch ブロック
            model.addAttribute("errorMessage", "登録できませんでした: " + e.getMessage());
            return "employee/register";
        }
        // 一覧画面にリダイレクト
        return "redirect:/employee/list";
    }

    @GetMapping("/update/{id}")
    public String getEmployee(@PathVariable("id") Integer id, Model model) {
        // Modelに登録
        model.addAttribute("employee", service.getEmployee(id));
        // User更新画面に遷移
        return "employee/update";
    }

    /** User更新処理 */
    @PostMapping("/update/{id}/")
    public String postUser(@PathVariable("id") Integer id, Model model, @Valid Employee employee,
            BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "入力が不適切です");
            return "employee/update";
        }
        try {
            Employee emp = service.getEmployee(id);
            emp.setName(employee.getName());
            emp.getAuthentication().setPassword(employee.getAuthentication().getPassword());
            emp.getAuthentication().setRole(employee.getAuthentication().getRole());
            emp.setUpdatedAt(LocalDateTime.now());
            // User登録

            service.saveEmployee(emp);
        } catch (Exception e) {
            // TODO 自動生成された catch ブロック
            model.addAttribute("errorMessage", "更新できませんでした: " + e.getMessage());
            return "employee/update";
        }
        // 一覧画面にリダイレクト
        return "redirect:/employee/list";
    }

    // ----- 追加:ここから -----
    /** User削除処理 */
    @PostMapping(path = "/delete/{id}") // postmappingがポイント"
    public String delUser(@PathVariable("id") Integer id, Model model, Employee employee) {
        try {
            Employee delemp = service.getEmployee(id);
            // Userを論理削除
            delemp.setDeleteFlag(1);
            delemp.setUpdatedAt(LocalDateTime.now());

            service.saveEmployee(delemp);
        } catch (Exception e) {
            // TODO 自動生成された catch ブロック
            model.addAttribute("errorMessage", "ユーザーを削除できませんでした: " + e.getMessage());
            return "employee/list";
        }
        // 一覧画面にリダイレクト
        return "redirect:/employee/list";
    }

    /** 詳細表示 */
    @GetMapping("/detail/{id}")
    public String getDetail(@PathVariable("id") Integer id, Model model) {

        // 詳細情報をModelに登録
        model.addAttribute("employeedetail", service.getEmployee(id));
        // Employee/detail.htmlに画面遷移
        return "employee/detail";
    }

}