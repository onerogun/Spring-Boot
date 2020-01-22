package og.prj.springsecurityjwt;

import og.prj.springsecurityjwt.jpafiles.CustomUserDetailsService;
import og.prj.springsecurityjwt.models.AuthenticationRequest;
import og.prj.springsecurityjwt.models.AuthenticationResponse;
import og.prj.springsecurityjwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class HelloWorldController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@RequestMapping({ "/hello" })
	public String firstPage() {


		System.out.println(Session.Cookie.class.toString());
		return "Hello World" ;
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
		}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}


		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

	@Autowired
	private WebClient.Builder webClientBuilder;

	@GetMapping("/")
	public String home() {
		return "home";
	}

	@GetMapping("/student")
	public String student() {
		return webClientBuilder.build().get().uri("http://student-service/").retrieve().bodyToMono(String.class).block();

	}

	@GetMapping("/teacher")
	public String teacher() {
		return "teacher";
	}

	@GetMapping("/admin")
	public String admin() {
		return webClientBuilder.build().get().uri("http://admin-service").retrieve().bodyToMono(String.class).block();

	}

}
