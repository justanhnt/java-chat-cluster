package vn.com.example.chatsystem.service;

import java.util.List;

import vn.com.example.chatsystem.model.chatroom.ChatRoom;
import vn.com.example.chatsystem.model.chatroom.ChatRoomUser;
import vn.com.example.chatsystem.model.chatroom.InstantMessage;

public interface ChatRoomService {
	
	ChatRoom save(ChatRoom chatRoom);
	ChatRoom findById(String chatRoomId);
	ChatRoom join(ChatRoomUser joiningUser, ChatRoom chatRoom);
	ChatRoom leave(ChatRoomUser leavingUser, ChatRoom chatRoom);
	void sendPublicMessage(InstantMessage instantMessage);
	void sendPrivateMessage(InstantMessage instantMessage);
	List<ChatRoom> findAll();
}
