package com.example.restful.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Locale;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class UserController {



    @Autowired //스프링이 가지고 있는 빈 들 중에서 같은 타입을 가지고 있는 빈을 자동으로 주입
    private MessageSource messageSource;

    private UserDaoService service;

    public UserController(UserDaoService service){
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public   EntityModel<User> retrieveUser(@PathVariable int id){
        User user = service.findOne(id);

        if (user == null){
            throw  new UserNotFoundException(String.format("ID[%S] not found" , id));
        }

        //Hateoas
        EntityModel<User> model = EntityModel.of(user);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        model.add(linkTo.withRel("all-users"));




        return model;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User saveUser = service.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saveUser.getId())
                .toUri();



        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        User user = service.deleteById(id);

        if (user == null){
            throw new UserNotFoundException(String.format("아이디 = [%s] 없다",id));
        }

    }

    @GetMapping("/helloworld-in")
    public String helloInternational(@RequestHeader(name = "Accept-Language" ,required = false)Locale locale){ //헤더가(랭귀지) 설정되지 않으면 디폴트 랭귀지값으로 설정(main china)

        return messageSource.getMessage("greeting.message",null,locale);
    }

}
