package com.example.restful.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/admin")
public class AdminUserController {



    @Autowired //스프링이 가지고 있는 빈 들 중에서 같은 타입을 가지고 있는 빈을 자동으로 주입
    private MessageSource messageSource;

    private UserDaoService service;

    public AdminUserController(UserDaoService service){
        this.service = service;
    }

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers(){
        List<User> users = service.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate","password");

        FilterProvider userInfo = new SimpleFilterProvider().addFilter("UserInfo", filter);




        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(users);
        mappingJacksonValue.setFilters(userInfo);


        return mappingJacksonValue;
    }

    @GetMapping("/users/{id}")
    public MappingJacksonValue retrieveUser(@PathVariable int id){
        User user = service.findOne(id);

        if (user == null){
            throw  new UserNotFoundException(String.format("ID[%S] not found" , id));
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate","ssn");

        FilterProvider userInfo = new SimpleFilterProvider().addFilter("UserInfo", filter);




        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
        mappingJacksonValue.setFilters(userInfo);

        return mappingJacksonValue;
    }



  /* @GetMapping("/v1/users/{id}")
    public MappingJacksonValue retrieveUserV1(@PathVariable int id){
        User user = service.findOne(id);

        if (user == null){
            throw  new UserNotFoundException(String.format("ID[%S] not found" , id));
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate","ssn");

        FilterProvider userInfo = new SimpleFilterProvider().addFilter("UserInfo", filter);




        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
        mappingJacksonValue.setFilters(userInfo);

        return mappingJacksonValue;
    }*/
     /*  @GetMapping("/v2/users/{id}")
    public MappingJacksonValue retrieveUserV2(@PathVariable int id){
        User user = service.findOne(id);

        if (user == null){
            throw  new UserNotFoundException(String.format("ID[%S] not found" , id));
        }


        // User -> User2

        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user,userV2);
        userV2.setGrade("VIP");


        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate","grade");

        FilterProvider userInfo = new SimpleFilterProvider().addFilter("UserInfoV2", filter);




        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userV2);
        mappingJacksonValue.setFilters(userInfo);

        return mappingJacksonValue;
    }*/

  @GetMapping(value = "/users/{id}/",params = "version=1")
  public MappingJacksonValue retrieveUserV1(@PathVariable int id){
      User user = service.findOne(id);

      if (user == null){
          throw  new UserNotFoundException(String.format("ID[%S] not found" , id));
      }

      SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate","ssn");

      FilterProvider userInfo = new SimpleFilterProvider().addFilter("UserInfo", filter);




      MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
      mappingJacksonValue.setFilters(userInfo);

      return mappingJacksonValue;
  }




    @GetMapping(value = "/v2/users/{id}/",params = "version=2")
    public MappingJacksonValue retrieveUserV2(@PathVariable int id) {
        User user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%S] not found", id));
        }


        // User -> User2

        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user, userV2);
        userV2.setGrade("VIP");


        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "grade");

        FilterProvider userInfo = new SimpleFilterProvider().addFilter("UserInfoV2", filter);


        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userV2);
        mappingJacksonValue.setFilters(userInfo);

        return mappingJacksonValue;
    }
}
