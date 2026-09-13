package be.tpvision.usermanagement.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.util.DigestUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@Order(50)
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {
   private static final String API_KEY = "&&&&&FDSf@#R@#!!!#@$@#$";

   @Override
   protected void configure(final HttpSecurity httpSecurity) throws Exception {
      ((HttpSecurity)httpSecurity.antMatcher("/api/**")
            .addFilterAfter(this.apiRequestSignFilter(), HeaderWriterFilter.class)
            .authorizeRequests()
            .anyRequest()
            .permitAll()
            .and())
         .csrf()
         .disable();
   }

   public OncePerRequestFilter apiRequestSignFilter() {
      return new OncePerRequestFilter() {
         @Override
         protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            String sign = request.getParameter("sign");
            if (!StringUtils.isBlank(sign)) {
               List<String> paramNameList = new ArrayList<>(request.getParameterMap().keySet());
               Collections.sort(paramNameList);
               paramNameList.remove("sign");
               StringBuilder signRawStr = new StringBuilder();

               for (String paramName : paramNameList) {
                  String values = StringUtils.join(request.getParameterValues(paramName));
                  signRawStr.append(paramName).append("=").append(values).append("&");
               }

               signRawStr.append("&&&&&FDSf@#R@#!!!#@$@#$");
               String checkSign = DigestUtils.md5DigestAsHex(signRawStr.toString().getBytes());
               if (sign.equalsIgnoreCase(checkSign)) {
                  filterChain.doFilter(request, response);
               }
            }
         }
      };
   }
}
