package com.bbd.shared.assembler;


import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ModelAssembler<T extends Object> implements RepresentationModelAssembler<T, EntityModel<T>>{

  @Override
  public @NonNull EntityModel<T> toModel(@NonNull T entity) {
    return EntityModel.of(entity);
  }

  public T fromModel(EntityModel<T> model) {
    return model.getContent();
}
}