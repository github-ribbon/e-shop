package com.company.eshop.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.eshop.domain.Client;
import com.company.eshop.domain.Usr;
import com.company.eshop.domain.UsrPK;
import com.company.eshop.service.UserService;
import com.company.eshop.validation.UsrValidator;


@Controller
@RequestMapping(value="/usr")
public class UsrController {
	
//	private static final Logger logger = LoggerFactory.getLogger(UsrController.class);
	
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UsrValidator usrValidator;	
	
	
	/*
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
				
		return "home";
		
	}
	
	*/
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String displayRegistrationForm(Model model) {		
		
		model.addAttribute("usr", new Usr());
		
		return "registration";		
	}
	
	@RequestMapping(value = "/register/completed", method = RequestMethod.GET)
	public String displayRegistrationSummary(Model model) {		
		
		model.addAttribute("completed",true);
		
		return "registration";		
	}
	
	
	
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String registerUser(@Valid Usr usr,BindingResult bindingResult,@RequestParam(value="password2",required=true) String password2,Model model){
				
		usrValidator.validate(usr, bindingResult);
		
		if(!password2.equals(usr.getPassword())){
			bindingResult.rejectValue("password","error.password2");
		}
		
		if(!bindingResult.hasErrors()){
			
			userService.registerUser(usr);
			
			return "redirect:/usr/register/completed";
		}else{
			return "registration";			
		}
		
	}

	
	@PreAuthorize(value="hasAnyRole('ROLE_ADMIN','ROLE_CLIENT')")
	@RequestMapping(value = "/",params="update",method = RequestMethod.POST)
	public String updateUser(@Valid Usr usr,BindingResult bindingResult,@RequestParam(value="password2",required=true) String password2,Model model,Principal principal){
				
		
		if(!principal.getName().equals(usr.getId().getLogin())){
			throw new RuntimeException();
		}
		
		if(!password2.equals(usr.getPassword())){
			bindingResult.rejectValue("password","error.password2");
		}
		
		if(!bindingResult.hasErrors()){
			
			userService.updateUserDetails(usr);
			
			return "redirect:/usr?updated";
		}else{
			return "userView";			
		}
		
	}
	
	@PreAuthorize(value="hasAnyRole('ROLE_ADMIN','ROLE_CLIENT')")
	@RequestMapping(method = RequestMethod.GET)
	public String displayAccountSettings(@RequestParam(value="updated",required=false) String updated,
			@RequestParam(value="client_account_created",required=false) String clientAccountCreated,
			HttpServletRequest request,Model model,Principal principal) {
		
		UsrPK usrPK=new UsrPK();
		usrPK.setLogin(principal.getName());
		
		Usr usr=userService.getUsr(usrPK);
		usr.setPassword("");
		
		model.addAttribute("usr",usr);
		
		return "userView";
	}

	@PreAuthorize(value="hasRole('ROLE_ADMIN')&&(!hasRole('ROLE_CLIENT'))")
	@RequestMapping(value = "/client", method = RequestMethod.GET)
	public String displayCreateClientAccountForm(Model model,Authentication authentication) {		
					
		UsrPK usrPK=new UsrPK();
		usrPK.setLogin(authentication.getName());
		
		if(!userService.isClient(usrPK)){
			Client client=new Client();
			client.setId(usrPK);
			
			model.addAttribute("client",client);
		}else{
			return "redirect:/usr?client_account_created";
		}	
		
		return "newClientAccountForm";		
	}
	
	
	@PreAuthorize(value="hasRole('ROLE_ADMIN')&&(!hasRole('ROLE_CLIENT'))")
	@RequestMapping(value = "/client", method = RequestMethod.POST)
	public String createClientAccountForAdmin(@Valid Client client,BindingResult bindingResult,Model model,Authentication authentication){
				
		if(authentication.getName().equals(client.getId().getLogin())){
			
			if(!bindingResult.hasErrors()){				
				userService.createClientAccountForAdmin(client);				
				return "redirect:/usr?client_account_created";
			}else{
				return "newClientAccountForm";			
			}
			
		}else{
			throw new RuntimeException();
		}	
		
	}
	
	
	@RequestMapping(value = "/reset-password", method = RequestMethod.GET)
	public String displayResetPasswordForm(@RequestParam (value="failure",required=false) String failure,
			@RequestParam (value="success",required=false) String success,
			@RequestParam (value="login",required=false) String login,
			@RequestParam (value="email",required=false) String email,
			HttpServletRequest request,HttpServletResponse response,Model model) {	
		
		if(failure!=null)
			model.addAttribute("failure", true);
		
		if(login!=null)
			model.addAttribute("login", login);
		
		if(email!=null)
			model.addAttribute("email", email);
		
		if(success!=null)
			model.addAttribute("success", true);
		
		return "passwordReset";		
	} 

	@RequestMapping(value="/reset-password",method = RequestMethod.POST)
	public String resetPassword(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
				
		String login=request.getParameter("login");
		String email=request.getParameter("email");
		
		if(userService.isUser(login, email)){				
			userService.resetPassword(login,email);			
			return "redirect:/usr/reset-password?success";			
		}else{
			return "redirect:/usr/reset-password?failure&login="+URLEncoder.encode(login,"utf8")+"&email="+URLEncoder.encode(email,"utf8");	
		}	
			
	}	
}
