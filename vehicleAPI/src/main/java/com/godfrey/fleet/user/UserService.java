package com.godfrey.fleet.user;

import com.godfrey.fleet.common.exception.ResourceNotFoundException;
import com.godfrey.fleet.user.dto.UserCreateDTO;
import com.godfrey.fleet.user.dto.UserPatchDTO;
import com.godfrey.fleet.user.dto.UserResponseDTO;
import com.godfrey.fleet.user.dto.UserUpdateDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
//@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService (UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDTO createUser(UserCreateDTO dto) {
        User user = userMapper.fromCreateDTO(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserUpdateDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
        userMapper.updateFromDTO(dto, user);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponseDTO patchUser(Long id, UserPatchDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
        userMapper.patchFromDTO(dto, user);
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
        userRepository.delete(user);
    }

    @Override
    public UserResponseDTO getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
        return userMapper.toResponse(user);
    }

    @Override
    public List<UserResponseDTO> listUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .toList();
    }
}