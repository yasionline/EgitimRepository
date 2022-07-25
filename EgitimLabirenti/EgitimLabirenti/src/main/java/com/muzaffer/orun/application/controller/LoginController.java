package com.muzaffer.orun.application.controller;

import java.io.Serializable;

import org.primefaces.PrimeFaces;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

@Controller
@Scope("request")
public class LoginController implements Serializable{

	private static final long serialVersionUID = 4207695841264438634L;

	private boolean login;

	public void login() {
		PrimeFaces.current().executeScript("giris()");
	}

	public boolean isLogin() {
		if(SecurityContextHolder.getContext().getAuthentication() == null) {
			login = false;
		} else if(!SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			login = false;
		} else {
			login = true;
		}
		return login;
	}

	public void setLogin(boolean login) {
		this.login = login;
	}

}
