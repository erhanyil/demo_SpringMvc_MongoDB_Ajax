package com.example.demo.models;
import org.springframework.data.annotation.Id;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class Users {

    @Id
    public String id;

    @NotBlank(message = "*Please enter your First Name.")
    public String firstName;

    @NotBlank(message = "*Please enter your Last Name.")
    public String lastName;

    @Pattern(regexp = "(?:^$|^((\\+0?1\\s)?)\\(?\\d{3}\\)?[\\s.\\s]\\d{3}[\\s]\\d{4})", message = "Please enter correct phone number")
    public String phoneNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ReturnValue checkValidation(BindingResult result) {
        ReturnValue rv = new ReturnValue("");
        if (result.hasErrors()) {
            rv.isSuccess = false;
            for (ObjectError error : result.getAllErrors()) {
                rv.value += error.getDefaultMessage() + "<br>";
            }
        }
        return rv;
    }
}
