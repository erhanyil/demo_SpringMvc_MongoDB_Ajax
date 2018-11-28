package com.example.demo.web;

import com.example.demo.models.ReturnValue;
import com.example.demo.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.demo.repositories.UsersRepository;
import javax.validation.Valid;

@Controller
public class MainController {

    @Autowired
    private UsersRepository usersRepository;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ResponseBody
    public ReturnValue getAll() {
        return new ReturnValue(usersRepository.findAll());
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public ReturnValue get(@RequestParam(value = "id") String id) {
        if(usersRepository.existsById(id)) {
            return new ReturnValue(usersRepository.findById(id));
        }
        return new ReturnValue(false, "User Not Found");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public ReturnValue delete(@RequestParam(value = "id") String id) {
        ReturnValue rv = new ReturnValue();
        try {
            usersRepository.deleteById(id);
        }catch (Exception _ex) {
            rv.isSuccess = false;
            rv.value = _ex.toString();
        }
        return rv;
    }

    @RequestMapping(value = "/createOrEdit", method = RequestMethod.POST)
    @ResponseBody
    public ReturnValue createOrEdit(@Valid @ModelAttribute("user") Users _user, BindingResult result) {
        ReturnValue validateResult = _user.checkValidation(result);
        if(validateResult.isSuccess) {
            usersRepository.save(_user);
        }
        return validateResult;
    }

}
