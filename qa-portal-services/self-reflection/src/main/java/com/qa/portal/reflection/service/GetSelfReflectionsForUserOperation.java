package com.qa.portal.reflection.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.qa.portal.common.exception.QaResourceNotFoundException;
import com.qa.portal.common.persistence.entity.TraineeEntity;
import com.qa.portal.common.persistence.entity.TrainerEntity;
import com.qa.portal.common.persistence.repository.QaTraineeRepository;
import com.qa.portal.common.persistence.repository.QaTrainerRepository;
import com.qa.portal.reflection.dto.ReflectionDto;
import com.qa.portal.reflection.persistence.repository.ReflectionRepository;
import com.qa.portal.reflection.service.mapper.ReflectionMapper;

@Component
public class GetSelfReflectionsForUserOperation {

    private ReflectionMapper reflectionMapper;

    private ReflectionRepository reflectionRepository;

    private QaTraineeRepository traineeRepository;

    private QaTrainerRepository trainerRepository;

    public GetSelfReflectionsForUserOperation(ReflectionMapper reflectionMapper, ReflectionRepository reflectionRepository, QaTraineeRepository traineeRepository, QaTrainerRepository trainerRepository) {
        this.reflectionMapper = reflectionMapper;
        this.reflectionRepository = reflectionRepository;
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
    }

    public Set<ReflectionDto> getSelfReflectionsForTrainee(String userName) {
        TraineeEntity trainee = traineeRepository.findByUserName(userName)
                .orElseThrow(() -> new QaResourceNotFoundException("Trainee does not exist"));
        return reflectionRepository.findAllByResponder(trainee)
                .stream().map(reflectionMapper::mapToReflectionDto)
                .collect(Collectors.toSet());
    }

    public Set<ReflectionDto> getSelfReflectionsForTrainer(String userName) {
        TrainerEntity trainer = trainerRepository.findByUserName(userName)
                .orElseThrow(() -> new QaResourceNotFoundException("Trainer does not exist"));
        return reflectionRepository.findByReviewerId(trainer.getId())
                .stream().map(reflectionMapper::mapToReflectionDto)
                .collect(Collectors.toSet());
    }

    public Set<ReflectionDto> getSelfReflectionsForUser(Integer traineeId) {
        return reflectionRepository.findByResponderId(traineeId)
                .stream().map(reflectionMapper::mapToReflectionDto)
                .collect(Collectors.toSet());
    }
}