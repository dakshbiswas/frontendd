package in.stack.boot.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import in.stack.boot.model.User;

@Service
public class SecurityTokenGenerator {
	public Map<String, String> generateToken(User user) {
		// TODO Auto-generated method stub
		String jwtToken ="";
		jwtToken = Jwts.builder().setSubject(user.getUserId()).setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256,"secretkey").compact();
		Map<String,String> map = new HashMap();
		map.put("token", jwtToken);
		map.put("message", "Welcome! Successfully logged in");
		map.put("name", user.getFirstName());
		
		return map;
	}
}
