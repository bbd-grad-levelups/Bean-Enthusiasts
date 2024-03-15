package com.bbd.BeanServer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbd.BeanServer.repository.ReactionTypeRepository;
import com.bbd.shared.models.ReactionType;

@Service
public class ReactionTypeService {

      @Autowired
    private ReactionTypeRepository reactionTypeRepository;

     public Optional<ReactionType> getReactionTypeById(int id) {
      return reactionTypeRepository.findAll().stream()
      .filter(x -> x.getReaction_type_id()==id)
      .findFirst();
}

}
