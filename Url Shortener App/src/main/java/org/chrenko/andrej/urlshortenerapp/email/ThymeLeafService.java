package org.chrenko.andrej.urlshortenerapp.email;

import java.util.Map;

public interface ThymeLeafService {
  String createContent(String template, Map<String, Object> variables);
}
