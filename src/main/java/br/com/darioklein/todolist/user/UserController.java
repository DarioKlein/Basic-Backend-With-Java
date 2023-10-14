package br.com.darioklein.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

// Modificadores:
// Public, Private e Protected

@RestController
@RequestMapping("/users")
public class UserController {

   /**
    * String (Texto)
    * Integer (int) Numeros Inteiros
    * Double (double) Números 0.00000
    * Float (float) números 0.000
    * char (A C )
    * Date (data)
    * Void
    */
   @Autowired
   private IUserRepository userRepository;

   @PostMapping("/")
   public ResponseEntity create(@RequestBody UserModel userModel) {
      var user = this.userRepository.findByUsername(userModel.getUsername());

      if (user != null) {
         System.out.println("Usuário já existe");

         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
      }

      var passwordHashred = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

      userModel.setPassword(passwordHashred);

      var userCreated = this.userRepository.save(userModel);
      return ResponseEntity.status(HttpStatus.OK).body(userCreated);
   }
}
