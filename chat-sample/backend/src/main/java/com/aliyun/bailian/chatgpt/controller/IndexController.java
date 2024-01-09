package com.aliyun.bailian.chatgpt.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yuanci
 */
public class IndexController {
    @GetMapping(value = {"/"})
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        //TODO may need to set some user info for ftl
        return "index";
    }
}
