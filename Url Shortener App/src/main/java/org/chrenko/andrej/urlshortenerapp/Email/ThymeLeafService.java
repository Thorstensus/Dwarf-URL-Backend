package org.chrenko.andrej.urlshortenerapp.Email;

import java.util.Map;

public interface ThymeLeafService {
  String createContent(String template, Map<String, Object> variables);
}
