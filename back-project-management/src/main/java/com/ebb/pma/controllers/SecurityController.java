package com.ebb.pma.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.ebb.pma.dao.UserAccountRepository;
import com.ebb.pma.entities.UserAccount;

@Controller
public class SecurityController {
	
	@Autowired
	UserAccountRepository userAccountRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptEncoder;

	@GetMapping("/register")
	public String register(Model model) {
		UserAccount userAccount = new UserAccount();
		model.addAttribute("userAccount", userAccount);
		
		return "security/register";
	}
	
	@PostMapping("/register/save")
	public String saveUser(Model model, UserAccount userAccount) {
		userAccount.setPassword(bCryptEncoder.encode(userAccount.getPassword()));
		userAccountRepository.save(userAccount);
		
		return "redirect:/";
	}
}
