package com.uca.capas.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.uca.capas.domain.Store;
import com.uca.capas.service.StoreService;

@Controller
@RequestMapping("/stores")
public class StoreController {
	Logger log = Logger.getLogger(StoreController.class.getSimpleName());
	
	@Autowired
	StoreService storeService;
	
	@GetMapping()
	ModelAndView initMain(ModelMap model) {
		ModelAndView mav = new ModelAndView("stores/main");
		mav.getModelMap().mergeAttributes(model);
		List<Store> stores;
		try {
			stores = storeService.getStoresList();
			mav.addObject("stores", stores);
		} catch (Exception e) {
			mav.addObject("failLoading", true);
			mav.addObject("messageFailLoad", "No se pudo cargar las sucursales");
		}
		return mav;
	}
	
	@GetMapping("/{id}/detail")
	String showStoreDetail(@PathVariable int id, Model model, RedirectAttributes ra) {
		Store store = storeService.getStore(id);
		if (store != null) {
			model.addAttribute("store", store);
			return "stores/show";
		} else {
			model.asMap().clear();
			ra.addFlashAttribute("error", "Ocurrió un problema en la búsqueda de la sucursal. Intente más tarde.");
			return "redirect:/stores";
		}
	}
	
	@GetMapping("/{id}/edit")
	ModelAndView edit(@PathVariable int id) {
		ModelAndView mav = new ModelAndView();
		Store store = storeService.getStore(id);
		if (store != null) {
			mav.addObject("store", store);
			mav.setViewName("stores/edit");
		} else {
			mav.setViewName("redirect:/stores");
		}
		return mav;
	}
	
	@GetMapping("/add")
	String add(Model model) {
		model.addAttribute("store", new Store());
		return "stores/edit";
	}

	
	@PostMapping("/save")
	ModelAndView save(@Valid @ModelAttribute Store store, BindingResult br,
			RedirectAttributes ra, HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		if (br.hasErrors()) {
			mav.setViewName("stores/edit");
		} else {
			RedirectView rv = new RedirectView(req.getContextPath() + "/stores");
			rv.setExposeModelAttributes(false);
			try {
				storeService.save(store);
				ra.addFlashAttribute("success", true);
				ra.addFlashAttribute("message", "Información de sucursal guardada con éxito");
			} catch(Exception e) {
				log.log(Level.SEVERE, "No se pudo guardar", e);
				ra.addFlashAttribute("success", false);
				ra.addFlashAttribute("message", "Ocurrió un error y no se pudo guardar la información");
			}
			mav.setView(rv);
		}
		return mav;
	}
}
