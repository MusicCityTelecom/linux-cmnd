package com.tpvision.smartinstall.util;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import org.json.JSONArray;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

public class JSONArrayConverter extends AbstractHttpMessageConverter<JSONArray> {
   public JSONArrayConverter() {
      super(new MediaType("application", "json", StandardCharsets.UTF_8), new MediaType("application", "*+json", StandardCharsets.UTF_8));
   }

   protected JSONArray readInternal(Class<? extends JSONArray> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
      return null;
   }

   @Override
   protected boolean supports(Class<?> clazz) {
      return clazz.equals(JSONArray.class);
   }

   protected void writeInternal(JSONArray t, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
      OutputStream out = outputMessage.getBody();
      String text = null;
      if (null != t) {
         text = t.toString();
         byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
         out.write(bytes);
      }
   }
}
