package vn.com.example.chatsystem.repository;

import org.springframework.data.repository.CrudRepository;

import vn.com.example.chatsystem.model.chatroom.ChatRoom;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends CrudRepository<ChatRoom, String> {
}
