package com.telusko.controller;

import com.telusko.entity.Customer;
import com.telusko.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.Map;

@Controller
public class CustomerController
{

    private ICustomerService service;

    @Autowired
    public void setService(ICustomerService service) {
        this.service = service;
    }

    @GetMapping("/customerlist")
    public String showCustomers(Model model)
    {
        model.addAttribute("customers", service.getAllCustomers());
        return "customer-list";
    }

    @GetMapping("/customerform")
    public String showCustomerRegisterForm(@ModelAttribute("customer")Customer customer)
    {
        return "customer-register-f";
    }

    @GetMapping("/updateform")
    public String showCustomerUpdateForm(@ModelAttribute("customer")Customer customer, @RequestParam("id")Integer id,Model model)
    {
        model.addAttribute("customer", service.searchCustomerById(id));
        return "customer-update-f";
    }

    @PostMapping("registercustomer")
    public String registerCustomer(@ModelAttribute("customer")Customer customer)
    {
        service.registerCustomer(customer);
        return "redirect:/customerlist";
    }

    @PostMapping("updatecustomer")
    public String updateCustomer(@ModelAttribute("customer")Customer customer)
    {
        service.registerCustomer(customer);
        return "redirect:/customerlist";
    }

    @GetMapping("deletecustomer")
    public String deleteCustomer(@RequestParam("id") Integer id)
    {
        service.deleteCustomer(id);
        return "redirect:/customerlist";
    }

    @PostMapping("searchcustomernameemail")
    public String updateCustomer(@RequestParam("name")String name, @RequestParam("email")String email, Map<String, Object> map)
    {
        map.put("customers", service.searchCustomerByNameAndEmail(name,email));
        return "redirect:/customerlist";
    }
}
