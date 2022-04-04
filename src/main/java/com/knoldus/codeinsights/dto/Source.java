package com.knoldus.codeinsights.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Source {

   Repository repository;

   public Source(Repository repository) {
      this.repository = repository;
   }

   public Source() {
   }

   public Repository getRepository() {
      return repository;
   }

   public void setRepository(Repository repository) {
      this.repository = repository;
   }

}
