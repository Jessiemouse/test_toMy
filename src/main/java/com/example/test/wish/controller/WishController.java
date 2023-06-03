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
    @Autowired
    private WishService service;

    @GetMapping("/wish")
    public String showWishList(Model model) {
        List<Wish> listWish = service.listAll();
        model.addAttribute("listWish", listWish);

        return "wish";
    }

    @GetMapping("/wish/new")
    public String showNewForm(Model model,HttpSession session,RedirectAttributes ra) {
        String loggedInAccount = (String) session.getAttribute("memAccount");
        if (loggedInAccount != null) {
            model.addAttribute("wish", new Wish());
            model.addAttribute("pageTitle", "新增許願");
            System.out.println("memAccount: " + loggedInAccount);
            return "wish_form";
        } else {
            ra.addFlashAttribute("message", "請先登入後再許願<(￣︶￣)>");
            return "redirect:/wish";
        }
    }

    @PostMapping("/wish/save")
    public String saveWish(@ModelAttribute("wish") Wish wish, HttpSession session, Model model,RedirectAttributes ra) {
        String xx = (String) session.getAttribute("memAccount");
        wish.setMemAccount(xx);
        System.out.println("memAccount: " + wish.getMemAccount());

        service.save(wish);
        ra.addFlashAttribute("message", "許願成功＼(＾▽＾)／");
        return "redirect:/wish?message=success";
    }

    @GetMapping("/wish/edit/{wishId}")
    public String showEditForm(@PathVariable("wishId") Integer wishId, HttpSession session, Model model, RedirectAttributes ra) {
        try {
            String loggedInAccount = (String) session.getAttribute("memAccount");
            Wish wish = service.get(wishId);

            // 檢查登錄的帳戶和許願文章的帳戶是否一致
            if (loggedInAccount != null && loggedInAccount.equals(wish.getMemAccount())) {
                model.addAttribute("wish", wish);
                model.addAttribute("pageTitle", "編輯許願");
                return "wish_form";
            } else {
                ra.addFlashAttribute("message", "您沒有權限編輯這則許願(×﹏×)");
                return "redirect:/wish";
            }
        } catch (WishNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "wish";
        }
    }

    @GetMapping("/wish/delete/{wishId}")
    public String deleteWish(@PathVariable("wishId") Integer wishId, RedirectAttributes ra, HttpSession session) {
        try {
            Wish wish = service.get(wishId);

            // 檢查當前登入的帳號是否與該許願文章的 memAccount 相符
            String memAccount = (String) session.getAttribute("memAccount");
            if (!wish.getMemAccount().equals(memAccount)) {
                ra.addFlashAttribute("message", "您沒有權限刪除這則許願(×﹏×)");
                return "redirect:/wish";
            }

            service.delete(wishId);
            ra.addFlashAttribute("message", "許願已刪除");
        } catch (WishNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/wish";
    }
}
