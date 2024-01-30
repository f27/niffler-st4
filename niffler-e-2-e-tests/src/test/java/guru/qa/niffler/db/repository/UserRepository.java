package guru.qa.niffler.db.repository;

import guru.qa.niffler.db.model.UserAuthEntity;
import guru.qa.niffler.db.model.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

  static UserRepository getRepository() {
      if ("jdbc".equals(System.getProperty("repository"))) {
          System.out.println("######## JDBC");
          return new UserRepositoryJdbc();
      }
      if ("sjdbc".equals(System.getProperty("repository"))) {
          System.out.println("######## SPRING-JDBC");
          return new UserRepositorySJdbc();
      }
      return new UserRepositoryJdbc();
  }

  UserAuthEntity createInAuth(UserAuthEntity user);

  Optional<UserAuthEntity> findByIdInAuth(UUID id);

  UserAuthEntity updateInAuth(UserAuthEntity user);

  void deleteInAuthById(UUID id);

  UserEntity createInUserdata(UserEntity user);

  Optional<UserEntity> findByIdInUserdata(UUID id);

  UserEntity updateInUserdata(UserEntity user);

  void deleteInUserdataById(UUID id);
}
