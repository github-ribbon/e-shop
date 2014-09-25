package com.company.eshop.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import com.company.eshop.domain.Product;
import com.company.eshop.domain.Usr;
import com.company.eshop.domain.UsrPK;




@Service
public class AuthService extends SavedRequestAwareAuthenticationSuccessHandler implements UserDetailsService{
	
	@Autowired
	private UserService userService;
	
	
	@Override
	public UserDetails loadUserByUsername(String username){
						
		
		try {
			
			UsrPK usrPK=new UsrPK();
			usrPK.setLogin(username);
			
			Usr domainUser = userService.getUsr(usrPK);
			
			
			@SuppressWarnings("unused")
			boolean enabled = true;
			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;
			
			List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
			
			if(domainUser.getAdmin()!=null){
				grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			}
			
			if(domainUser.getClient()!=null){				
				grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
			}	
			
			return new User(
					domainUser.getId().getLogin(), 
					domainUser.getPassword().toLowerCase(),
					domainUser.getIsEnabled(),
					accountNonExpired,
					credentialsNonExpired,
					accountNonLocked,
					grantedAuthorities);
			
		} catch (UsernameNotFoundException e) {			
			return new User(null, null, null);
		}

	}
	
	
	@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
   
		UsrPK usrPK=new UsrPK();
		usrPK.setLogin(authentication.getName());
		
		userService.saveSessionUserDetails(request.getSession(),userService.getUsr(usrPK));
		
		if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CLIENT"))){			
			request.getSession().setAttribute("cart",new ArrayList<Product>());			
		}
				
		response.sendRedirect(request.getContextPath());	   
   }
	
}
