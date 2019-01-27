package vn.com.example.chatsystem.model.credential;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import vn.com.example.chatsystem.model.credential.Role;

@Document(collection = "user")
public class User {
	
	@Id
	@Size(min = 5, max = 15)
	private String username;
	
	@Size(min = 5)
	private String password;
	
	private String name;
	
	private String email;

	private Set<Role> roles = new HashSet<>();
	
	public User () {
		
	}
	
	public User(String username, String password, String name, String email) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void addRoles(Collection<Role> roles) {
		this.roles.addAll(roles);
	}
}
