package vn.com.example.chatsystem.repository;

import java.util.List;


import vn.com.example.chatsystem.model.chatroom.InstantMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstantMessageRepository extends MongoRepository<InstantMessage, String> {
	List<InstantMessage> findByUsernameAndChatRoomId(String username, String chatRoomId);
}
