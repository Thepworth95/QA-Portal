package com.qa.portal.user.services;

import com.qa.portal.common.dto.QaUserDto;
import com.qa.portal.common.persistence.repository.QaUserRepository;
import com.qa.portal.common.util.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetUsersOperation {

    private QaUserRepository userRepository;

    private BaseMapper baseMapper;

    public GetUsersOperation(QaUserRepository userRepository,
                             BaseMapper baseMapper) {
        this.userRepository = userRepository;
        this.baseMapper = baseMapper;
    }

    public List<QaUserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(ue -> baseMapper.mapToQaUserDto(ue))
                .collect(Collectors.toList());
    }
}
