package com.example.test.wish.controller;

import com.example.test.wish.entity.Wish;
import com.example.test.wish.WishNotFoundException;
import com.example.test.wish.service.WishService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class WishController {
    @Autowired private WishService service;
    
    @GetMapping("/wish")
    public String showWishList(Model model) {
        List<Wish> listWish = service.listAll();
        model.addAttribute("listWish", listWish);

        return "wish";
    }

    @GetMapping("/wish/new")
    public String showNewForm(Model model) {
        model.addAttribute("wish", new Wish());
        model.addAttribute("pageTitle", "新增許願");
        return "wish_form";
    }

    @PostMapping("/wish/save")
    public String saveWish(@ModelAttribute("wish") Wish wish, HttpSession session, Model model) {
        String xx = (String) session.getAttribute("memAccount");
        wish.setMemAccount(xx);
        System.out.println("memAccount: " + wish.getMemAccount());

        service.save(wish);

        return "redirect:/wish?message=success";
    }

    @GetMapping("/wish/edit/{wishId}")
    public String showEditForm(@PathVariable("wishId") Integer wishId, Model model, RedirectAttributes ra) {
        try {
            Wish wish = service.get(wishId);
            model.addAttribute("wish", wish);
            model.addAttribute("pageTitle", "編輯許願");

            return "wish_form";
        } catch (WishNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "wish";
        }
    }

    @GetMapping("/wish/delete/{wishId}")
    public String deleteWish(@PathVariable("wishId") Integer wishId, RedirectAttributes ra,Model model) {
        try {
            service.delete(wishId);

        } catch (WishNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/wish";
    }
}
