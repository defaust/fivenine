package com.fivenine.fivenine.service;



import com.fivenine.fivenine.entity.User;
import com.fivenine.fivenine.model.UserDto;
import com.fivenine.fivenine.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Transactional
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public UserDto.View create(UserDto.Create req) {
        User u = User.builder()
                .name(req.name())
                .email(req.email())
                .build();
        u = repo.save(u);
        return toView(u);
    }

    @Transactional(readOnly = true)
    public UserDto.View get(Long id) {
        return toView(find(id));
    }

    @Transactional(readOnly = true)
    public java.util.List<UserDto.View> list() {
        return repo.findAll().stream().map(this::toView).toList();
    }

    public UserDto.View update(Long id, UserDto.Update req) {
        User u = find(id);
        if (req.name() != null)  u.setName(req.name());
        if (req.email() != null) u.setEmail(req.email());
        return toView(repo.save(u));
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResponseStatusException(NOT_FOUND, "User not found");
        repo.deleteById(id);
    }

    private User find(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
    }

    private UserDto.View toView(User u) {
        return new UserDto.View(u.getId(), u.getName(), u.getEmail(), u.getCreatedAt());
    }
}

