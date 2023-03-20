package de.ghse.forum.service;

import de.ghse.forum.model.User;
import de.ghse.forum.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 *
 *
 * <pre>
 * User Service Class
 * </pre>
 *
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class UserService {
  @Value("${page.size}")
  private int PAGE_SIZE;

  private final UserRepository UserRepository;

  public @NotNull Optional<User> findbyUsername(String username) {
    return UserRepository.findByUsername(username);
  }

  public @NotNull Optional<User> findUserById(UUID id) {
    return UserRepository.findById(id);
  }

  public List<User> search(String username, int page) {
    return UserRepository.search(username, PageRequest.of(page, PAGE_SIZE));
  }

  public void deleteUser(UUID id) {
    UserRepository.deleteById(id);
  }

  public void updateUser(String bio, UUID id) {
    UserRepository.updateUser(bio, id);
  }
}
