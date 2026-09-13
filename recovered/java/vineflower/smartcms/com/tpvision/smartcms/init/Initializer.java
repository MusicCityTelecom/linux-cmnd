package com.tpvision.smartcms.init;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

public class Initializer implements WebApplicationInitializer {
   @Override
   public void onStartup(ServletContext container) throws ServletException {
      AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
      ctx.register(WebAppConfig.class);
      container.addListener(new ContextLoaderListener(ctx));
      ctx.setServletContext(container);
      Dynamic servlet = container.addServlet("dispatcher", new DispatcherServlet(ctx));
      servlet.addMapping("/");
      servlet.setLoadOnStartup(1);
   }

   private void addFilter(ServletContext container) {
      String filterName = "WhatEverYouWantToNameYourFilter";
      String filterBeanName = "mdcInsertingServletFilter";
      container.addFilter(filterName, new DelegatingFilterProxy(filterBeanName)).addMappingForUrlPatterns(null, false, "/");
   }
}
