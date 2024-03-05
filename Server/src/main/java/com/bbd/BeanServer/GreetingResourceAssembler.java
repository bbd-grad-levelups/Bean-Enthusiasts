package com.bbd.BeanServer;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class GreetingModelAssembler implements RepresentationModelAssembler<Greeting, EntityModel<Greeting>> {

  @Override
  public EntityModel<Greeting> toModel(Greeting greeting) {

    return EntityModel.of(greeting,
      linkTo(methodOn(TestController.class).getGreeting(greeting.getID())).withSelfRel(),
      linkTo(methodOn(TestController.class).all()).withRel("greetings"));
  }
}