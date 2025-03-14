package com.users.dto;

public class UserUpdateRequest {
	private String currentPassword;
    private String newPassword;
    private String email;
    
    
    
	public String getCurrentPassword() {
		return currentPassword;
	}
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public String getEmail() {
		return email;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	

}
