package com.tpvision.smartinstall.filter;

import java.io.IOException;
import java.util.regex.Pattern;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class CrossScriptingFilter implements Filter {
   @Override
   public void init(FilterConfig filterConfig) throws ServletException {
   }

   @Override
   public void destroy() {
   }

   @Override
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      chain.doFilter(new CrossScriptingFilter.RequestWrapper((HttpServletRequest)request), response);
   }

   final class RequestWrapper extends HttpServletRequestWrapper {
      public RequestWrapper(HttpServletRequest servletRequest) {
         super(servletRequest);
      }

      @Override
      public String[] getParameterValues(String parameter) {
         String[] values = super.getParameterValues(parameter);
         if (null == values) {
            return null;
         }

         int count = values.length;
         String[] encodedValues = new String[count];

         for (int i = 0; i < count; i++) {
            encodedValues[i] = this.cleanValue(values[i]);
         }

         return encodedValues;
      }

      @Override
      public String getParameter(String parameter) {
         String value = super.getParameter(parameter);
         return null == value ? null : this.cleanValue(value);
      }

      @Override
      public String getHeader(String name) {
         String value = super.getHeader(name);
         return null == value ? null : this.cleanValue(value);
      }

      private String cleanValue(String value) {
         if (value != null) {
            Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", 2);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\'(.*?)\\'", 42);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", 42);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("</script>", 2);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("<script(.*?)>", 42);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("eval\\((.*?)\\)", 42);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("expression\\((.*?)\\)", 42);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("javascript:", 2);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("vbscript:", 2);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("onload(.*?)=", 42);
            value = scriptPattern.matcher(value).replaceAll("");
         }

         return value;
      }
   }
}
