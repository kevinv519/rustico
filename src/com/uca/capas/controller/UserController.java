package com.uca.capas.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uca.capas.domain.User;
import com.uca.capas.service.UserService;

@Controller
public class UserController {
	Logger log = Logger.getLogger(UserController.class.getSimpleName());
	
	@Autowired
	UserService uService;
	
	@GetMapping("/")
	String main(Model model, ModelMap map) {
		model.mergeAttributes(map);
		return "login";
	}
	
	@PostMapping("/login")
	String login(@RequestParam String username, @RequestParam String password, RedirectAttributes ra) {
		if (username.trim().isEmpty() || password.trim().isEmpty()) {
			ra.addFlashAttribute("error", "Todos los campos son obligatorios");
		}
		else {
			try {
				User user = uService.logInUser(username, password);
				if (user != null) {
					return "redirect:/stores";
				}
				ra.addFlashAttribute("error", "Usuario o contraseña inválidos");
			} catch(Exception e) {
				log.log(Level.SEVERE, "Could not retrieve user", e);
				ra.addFlashAttribute("error", "No se pudo autenticar el usuario. Intenté más tarde o contacte el administrador del sitio");
			}
		}
		return "redirect:/";
	}
}
