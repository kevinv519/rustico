package com.uca.capas.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.uca.capas.domain.Store;
import com.uca.capas.dto.EmployeeDTO;
import com.uca.capas.service.EmployeeService;
import com.uca.capas.service.StoreService;

@Controller
@SessionAttributes(UserController.ATT_LOG)
@RequestMapping("/employees")
public class EmployeeController {
	Logger log = Logger.getLogger(EmployeeController.class.getSimpleName());
	
	@Autowired
	EmployeeService empService;
	
	@Autowired
	StoreService storeService;
	
	@GetMapping("/edit/{id}")
	String edit(@PathVariable int id, Model model, @ModelAttribute(UserController.ATT_LOG) boolean loggedIn) {
		if (!loggedIn) {
			model.asMap().clear();
			return "redirect:/";
		}
		EmployeeDTO emp = empService.getEmployee(id);
		if (emp == null) {
			return "redirect:/stores/detail/" + id ;
		} else {
			model.addAttribute("empdto", emp);
			return "employees/edit";
		}
	}
	
	@PostMapping("/add")
	String add(@RequestParam int storeId, Model model, @ModelAttribute(UserController.ATT_LOG) boolean loggedIn) {
		if (!loggedIn) {
			model.asMap().clear();
			return "redirect:/";
		}
		EmployeeDTO empdto = new EmployeeDTO();
		empdto.setStoreId(storeId);
		model.addAttribute("empdto", empdto);
		return "employees/edit";
	}
	
	@PostMapping("/save")
	ModelAndView save(@Valid @ModelAttribute("empdto") EmployeeDTO empdto, BindingResult br,
			RedirectAttributes ra, HttpServletRequest req, @ModelAttribute(UserController.ATT_LOG) boolean loggedIn) {
		ModelAndView mav = new ModelAndView();
		if (!loggedIn) {
			mav.clear();
			return new ModelAndView("redirect:/");
		}
		if (br.hasErrors()) {
			mav.setViewName("employees/edit");
		} else {
			try {
				empService.save(empdto);
				ra.addFlashAttribute("success", true);
				ra.addFlashAttribute("message", "Información de empleado guardad con éxito");
			} catch (Exception e) {
				ra.addFlashAttribute("success", false);
				ra.addFlashAttribute("message", "No se pudo guardar la información del usuario.");
			}
			mav.setViewName("redirect:/stores/detail/" + empdto.getStoreId());
		}
		return mav;
	}
	
	@GetMapping("/delete/{id}")
	RedirectView delete(@PathVariable int id, RedirectAttributes ra, HttpServletRequest req,
			ModelMap map, @ModelAttribute(UserController.ATT_LOG) boolean loggedIn) {
		if (!loggedIn) {
			map.clear();
			return new RedirectView(req.getContextPath() + "/");
		}
		int store = empService.getEmployee(id).getStoreId();
		RedirectView rv = new RedirectView(req.getContextPath() + "/stores/detail/" + store);
		rv.setExposeModelAttributes(false);
		try {
			empService.delete(id);
			ra.addFlashAttribute("success", true);
			ra.addFlashAttribute("message", "Empleado eliminado con éxito");
		} catch(Exception e) {
			log.log(Level.SEVERE, "Couldn't delete store. Details: ", e);
			ra.addFlashAttribute("success", false);
			ra.addFlashAttribute("message", "No se pudo eliminar el empleado. Intene nuevamente más tarde");
		}
		return rv;
	}
	
	@ModelAttribute("stores")
	List<Store> registerStores() {
		return storeService.getStoresList();
	}
	
	@ModelAttribute(UserController.ATT_LOG)
	public boolean isLoggedIn(HttpSession session) {
		return session.getAttribute(UserController.ATT_LOG) != null && (boolean) session.getAttribute(UserController.ATT_LOG);
	}
}
