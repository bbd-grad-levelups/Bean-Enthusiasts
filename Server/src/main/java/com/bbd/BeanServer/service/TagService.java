package com.bbd.BeanServer.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbd.BeanServer.repository.TagRepository;
import com.bbd.shared.models.Tag;

@Service
public class TagService {
  
  @Autowired
  private TagRepository repository;

  public Optional<Tag> getTagByName(String name) {
      return repository.findAll().stream()
      .filter(x -> x.getTag_name().equalsIgnoreCase(name))
      .findFirst();
  }
  
}
