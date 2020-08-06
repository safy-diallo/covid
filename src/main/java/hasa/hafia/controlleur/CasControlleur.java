package hasa.hafia.controlleur;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import hasa.hafia.dao.CasRepository;
import hasa.hafia.entities.CasConfirmer;
import hasa.hafia.entities.CasSuspect;

@Controller

public class CasControlleur {
	 @Autowired
	    private CasRepository casRepository;
	    @RequestMapping(value = "/covid/cas", method = RequestMethod.GET)
	    public String listCas(ModelMap map){
	        map.addAttribute("liste_cas", casRepository.findAll());
	        map.addAttribute("cassuspect", new CasSuspect());
	        map.addAttribute("casconfirme", new CasConfirmer());
	        return "covid/list";
	    }

	    @RequestMapping(value = "/suspect/add", method = RequestMethod.POST)
	    public String addCasSuspect(CasSuspect casSuspect){
	        casRepository.save(casSuspect);
	        return "redirect:/covid/cas";
	    }

	    @RequestMapping(value = "/confirme/add", method = RequestMethod.POST)
	    public String addCasConfrime(CasConfirmer casConfirme, String dateConfirmation){
	        try{
	            casConfirme.setDateConfirmation(new SimpleDateFormat("yyyy-MM-dd").parse(dateConfirmation));
	        }catch (Exception e){
	            e.printStackTrace();
	        }
	        casRepository.save(casConfirme);
	        return "redirect:/covid/cas";
	    }

	    @RequestMapping(value = "/cas/delete", method = RequestMethod.GET)
	    public String deleteCas(Integer id) {
	        casRepository.delete(casRepository.getOne(id));
	        return "redirect:/covid/cas";
	    }

	    @RequestMapping(value = "/cas/edit", method = RequestMethod.GET)
	    public String editSuspect(ModelMap map, Integer id) {

	        map.addAttribute("liste_cas", casRepository.findAll());
	        map.addAttribute("casconfirme", casRepository.getOne(id));
	        map.addAttribute("cassuspect", casRepository.getOne(id));
	        return "covid/list";
	    }

}
