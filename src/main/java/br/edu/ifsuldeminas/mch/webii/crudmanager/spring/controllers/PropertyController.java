package br.edu.ifsuldeminas.mch.webii.crudmanager.spring.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.edu.ifsuldeminas.mch.webii.crudmanager.spring.model.entities.Property;
import br.edu.ifsuldeminas.mch.webii.crudmanager.spring.model.repositories.PropertyRepository;

@Controller
public class PropertyController {

    @Autowired
    private PropertyRepository propertyRepository;

    @GetMapping("/properties")
    public String properties(Model model) {
        // Envia a lista de imóveis para a tabela (idêntico ao 'usuarios')
        model.addAttribute("imoveis", propertyRepository.findAll());
        return "properties";
    }

    @GetMapping("/properties/form")
    public String propertyForm(Model model) {
        // Inicializa um objeto imóvel vazio para o formulário
        model.addAttribute("property", new Property());
        return "property_form";
    }

    @PostMapping("/properties/save")
    public String propertySave(@Valid @ModelAttribute("property") Property property, 
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "property_form";
        }
        
        propertyRepository.save(property);
        return "redirect:/properties";
    }

    @GetMapping("/properties/{id}")
    public String propertyUpdate(@PathVariable("id") Integer id, Model model) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Imóvel inválido:" + id));
        
        model.addAttribute("property", property);
        return "property_form";
    }

    @GetMapping("/properties/delete/{id}")
    public String propertyDelete(@PathVariable("id") Integer id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Imóvel inválido:" + id));
        
        propertyRepository.delete(property);
        return "redirect:/properties";
    }
}