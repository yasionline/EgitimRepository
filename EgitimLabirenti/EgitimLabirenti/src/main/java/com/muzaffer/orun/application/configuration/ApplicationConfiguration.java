package com.muzaffer.orun.application.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.faces.webapp.FacesServlet;
import javax.servlet.Filter;

import org.primefaces.webapp.filter.FileUploadFilter;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.sun.faces.config.ConfigureListener;

@Configuration
@ComponentScan(basePackages = "com.muzaffer.orun")
public class ApplicationConfiguration {

	// JSF Configration Başlangıc
	@Bean
	public ServletRegistrationBean<FacesServlet> facesServletRegistraiton() {
		ServletRegistrationBean<FacesServlet> registration = new ServletRegistrationBean<>(new FacesServlet(), "*.xhtml");
		registration.setName("Faces Servlet");
		registration.setLoadOnStartup(1);
		return registration;
	}

	@Bean
	public ServletContextInitializer servletContextInitializer() {
		return servletContext -> {
			servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
			servletContext.setInitParameter("primefaces.THEME", "omega");
			servletContext.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", Boolean.TRUE.toString());
			servletContext.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", Boolean.TRUE.toString());
			servletContext.setInitParameter("primefaces.FONT_AWESOME", Boolean.TRUE.toString());
			servletContext.setInitParameter("primefaces.UPLOADER", "commons");
			servletContext.setInitParameter("javax.faces.STATE_SAVING_METHOD", "server");
			servletContext.setInitParameter("javax.faces.PROJECT_STAGE", "Production");

		};
	}

	@Bean
	public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
		return new ServletListenerRegistrationBean<ConfigureListener>(new ConfigureListener());
	}

	@Bean
	public FilterRegistrationBean<FileUploadFilter> fileUploadFilter() {
		FilterRegistrationBean<FileUploadFilter> registration = new FilterRegistrationBean<>();
		registration.setFilter(new org.primefaces.webapp.filter.FileUploadFilter());
		registration.setName("PrimeFaces FileUpload Filter");
		return registration;
	}

	@Bean
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}

	@Bean
	public FilterRegistrationBean<Filter> characterEncodingFilterRegistration() {
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
		registration.setFilter(characterEncodingFilter());
		registration.addServletNames("FacesServlet");
		registration.setName("Character Encoding Filter");
		return registration;
	}

	public Filter characterEncodingFilter() {
		return new CharacterEncodingFilter();
	}

	@Bean
	public CustomScopeConfigurer viewScope() {
		CustomScopeConfigurer configurer = new CustomScopeConfigurer();
		Map<String, Object> viewScope = new HashMap<>();
		viewScope.put("view", new ViewScope());
		configurer.setScopes(viewScope);
		return configurer;
	}

	@Bean
	public FilterRegistrationBean<Filter> openSessionFilterRegistration() {
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
		registration.setFilter(openSessionFilter());
		registration.addUrlPatterns("/*");
		registration.setName("OpenSessionFilter");
		return registration;
	}

	public Filter openSessionFilter() {
		return new OpenSessionInViewFilter();
	}
}
