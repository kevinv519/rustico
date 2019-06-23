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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.uca.capas.domain.Store;
import com.uca.capas.service.StoreService;

@Controller
@RequestMapping("/stores")
@SessionAttributes(UserController.ATT_LOG)
public class StoreController {
	Logger log = Logger.getLogger(StoreController.class.getSimpleName());
	
	@Autowired
	StoreService storeService;
	
	@GetMapping()
	ModelAndView initMain(ModelMap map, @ModelAttribute(UserController.ATT_LOG) boolean loggedIn) {
		if (!loggedIn) {
			map.clear();
			return new ModelAndView("redirect:/");
		}
		ModelAndView mav = new ModelAndView("stores/main");
		mav.getModelMap().mergeAttributes(map);
		List<Store> stores;
		try {
			stores = storeService.getStoresList();
			mav.addObject("stores", stores);
		} catch (Exception e) {
			log.log(Level.SEVERE, "Could not retrieve stores. Details: ", e);
			mav.addObject("messageFailLoad", "No se pudo cargar las sucursales");
		}
		return mav;
	}
	
	@GetMapping("/detail/{id}")
	ModelAndView showStoreDetail(@PathVariable int id, ModelMap map, RedirectAttributes ra, @ModelAttribute(UserController.ATT_LOG) boolean loggedIn) {
		if (!loggedIn) {
			map.clear();
			return new ModelAndView("redirect:/");
		}
		ModelAndView mav = new ModelAndView();
		Store store = storeService.getStoreWithEmployees(id);
		if (store != null) {
			mav.getModelMap().mergeAttributes(map);
			mav.addObject("store", store);
			mav.setViewName("stores/show");
		} else {
			mav.getModelMap().clear();
			ra.addFlashAttribute("success", false);
			ra.addFlashAttribute("message", "Ocurrió un problema en la búsqueda de la sucursal. Intente más tarde.");
			mav.setViewName("redirect:/stores");
		}
		return mav;
	}
	
	@GetMapping("/edit/{id}")
	ModelAndView edit(@PathVariable int id, ModelMap map, @ModelAttribute(UserController.ATT_LOG) boolean loggedIn) {
		if (!loggedIn) {
			map.clear();
			return new ModelAndView("redirect:/");
		}
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
	String add(Model model, @ModelAttribute(UserController.ATT_LOG) boolean loggedIn) {
		if (!loggedIn) {
			model.asMap().clear();
			return "redirect:/";
		}
		model.addAttribute("store", new Store());
		return "stores/edit";
	}

	
	@PostMapping("/save")
	ModelAndView save(@Valid @ModelAttribute Store store, BindingResult br,
			RedirectAttributes ra, HttpServletRequest req, @ModelAttribute(UserController.ATT_LOG) boolean loggedIn) {
		ModelAndView mav = new ModelAndView();
		if (!loggedIn) {
			mav.clear();
			return new ModelAndView("redirect:/");
		}
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
	
	@GetMapping("/delete/{id}")
	RedirectView delete(@PathVariable int id, RedirectAttributes ra, HttpServletRequest req,
			ModelMap map, @ModelAttribute(UserController.ATT_LOG) boolean loggedIn) {
		if (!loggedIn) {
			map.clear();
			return new RedirectView(req.getContextPath() + "/");
		}
		RedirectView rv = new RedirectView(req.getContextPath() + "/stores");
		rv.setExposeModelAttributes(false);
		Store store = storeService.getStore(id);
		if (store == null) {
			ra.addFlashAttribute("success", false);
			ra.addFlashAttribute("message", "El registro que se desea eliminar no existe");
		} else {
			try {
				storeService.delete(store);
				ra.addFlashAttribute("success", true);
				ra.addFlashAttribute("message", "Sucursal eliminada éxitosamente");
			} catch(Exception e) {
				ra.addFlashAttribute("success", false);
				ra.addFlashAttribute("message", "Ocurrió un error y no se pudo borrar la sucursal");
			}
		}
		return rv;
	}
	
	@ModelAttribute(UserController.ATT_LOG)
	public boolean isLoggedIn(HttpSession session) {
		return session.getAttribute(UserController.ATT_LOG) != null && (boolean) session.getAttribute(UserController.ATT_LOG);
	}
}
