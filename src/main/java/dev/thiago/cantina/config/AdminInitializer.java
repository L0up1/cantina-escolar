//package dev.thiago.cantina.config;
//
//import dev.thiago.cantina.entity.Usuario;
//import dev.thiago.cantina.enums.Cargo;
//import dev.thiago.cantina.repository.UsuarioRepository;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class AdminInitializer implements CommandLineRunner {
//
//    private final UsuarioRepository usuarioRepository;
//    private final PasswordEncoder encoder;
//
//    @Value("${ADMIN_USERNAME}")
//    private String adminUsername;
//    @Value("${ADMIN_PASSWORD}")
//    private String adminPassword;
//
//    public AdminInitializer(UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
//        this.usuarioRepository = usuarioRepository;
//        this.encoder = encoder;
//    }
//
//    @Override
//    public void run(String... args){
//        if (usuarioRepository.findByLogin(adminUsername).isEmpty()) {
//            Usuario admin = new Usuario();
//
//            admin.setNome("Administrador");
//            admin.setLogin(adminUsername);
//            admin.setSenha(encoder.encode(adminPassword));
//            admin.setCargo(Cargo.ADMIN);
//
//            usuarioRepository.save(admin);
//        }
//
//    }
//}
