package vn.com.example.chatsystem.repository;


import vn.com.example.chatsystem.model.credential.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
}
