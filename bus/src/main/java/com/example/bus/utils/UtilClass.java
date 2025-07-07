package com.example.bus.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.example.bus.security.JwtUtil;

@Configuration
public class UtilClass {

	@Autowired
	JwtUtil jwtUtil;
	
	public String checkUserRole(String token)
	{
		return jwtUtil.getRoleFromToken(token);
	}
	
	public boolean isTravellerRoleAuthorized(String token)
	{
		String role = checkUserRole(token);
		if(role.equals("Traveller"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	// Helper method to convert a hexadecimal string to a byte array
    public byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                 + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
	
}
