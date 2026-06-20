package Security.Spring.Revision.Config;

import Security.Spring.Revision.Entity.User;
import Security.Spring.Revision.Repository.UserRepo;
import Security.Spring.Revision.Service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthConfigFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtservice;

    @Autowired
    UserRepo userRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        if(authorization == null || !authorization.startsWith("Bearer ")){
            //Pass the requeest onto the next Filter in the
            filterChain.doFilter(request,response);
        }
        final String jwttoken = authorization.substring(7);

        //Get Username from JWT
        String username = jwtservice.getUsername(jwttoken);

        User user = userRepo.findByName(username).get();

        if(username!= null && username.equals(user.getName())){
            boolean validtokenexp = jwtservice.isvalidToken(jwttoken);
            if(validtokenexp){
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,null);
                // Set extra data to the Authentication object like IP-ADDRESS,WHO LOGGED IN,AT WHAT TIME
                //Gives you some extra secuirty.
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(token);
            }
        }

        filterChain.doFilter(request,response);


    }
}
