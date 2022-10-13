package com.generation.blogpessoal.security;
import org.apache.commons.codec.binary.Base64;
import java.nio.charset.Charset;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import
org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
@Autowired
private UsuarioRepository repository;
@Override
public UserDetails loadUserByUsername(String userName)
throws UsernameNotFoundException {
Optional<Usuario> usuario = repository.findByUsuario(userName);
usuario.orElseThrow(() -> new UsernameNotFoundException(userName +
" não encontrado."));
return usuario.map(UserDetailsImpl::new).get();
}

private static String geradorBasicToken(String email, String senha) {
String estrutura = email + ":" + senha;
byte[] estruturaBase64 =
Base64.encodeBase64(estrutura.getBytes(Charset.forName("US-ASCII")));
return "Basic " + new String(estruturaBase64);
}

}