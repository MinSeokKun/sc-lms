package com.lms.sc.controller;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.lms.sc.entity.SiteUserDetails;

@ControllerAdvice
public class GlobalController {
	
    public void addAttributes(Model model, Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof SiteUserDetails) {
            SiteUserDetails userDetails = (SiteUserDetails) authentication.getPrincipal();
            model.addAttribute("userName", userDetails.getName());
        }
    }
}
