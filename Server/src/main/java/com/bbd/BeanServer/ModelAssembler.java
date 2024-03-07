package com.bbd.BeanServer;


import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ModelAssembler<T> implements RepresentationModelAssembler<T, EntityModel<T>>{
  @Override
  public EntityModel<T> toModel(T input) {
    return EntityModel.of(input);
  }
}