package ua.prachyk.FirstRestApp.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ua.prachyk.FirstRestApp.dto.PersonDTO;
import ua.prachyk.FirstRestApp.models.Person;
import ua.prachyk.FirstRestApp.services.PeopleService;
import ua.prachyk.FirstRestApp.utill.PersonErrorResponse;
import ua.prachyk.FirstRestApp.utill.PersonNotCreated;
import ua.prachyk.FirstRestApp.utill.PersonNotFoundException;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final ModelMapper modelMapper;

    @Autowired
    public PeopleController(PeopleService peopleService, ModelMapper modelMapper) {
        this.peopleService = peopleService;
        this.modelMapper = modelMapper;
    }

    public List<PersonDTO> findAll() {
        return peopleService.findAll().stream().map(this::convertToPersonDTO).collect(Collectors.toList());
    }

    @GetMapping()
    public List<Person> getPeople() {
        return peopleService.findAll();
    }

    @GetMapping("/{id}")

    public PersonDTO getPerson(@PathVariable("id") int id) {
        return convertToPersonDTO(peopleService.findOne(id));
    }

    @PostMapping
   @ExceptionHandler
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            }

            throw new PersonNotCreated(errorMsg.toString());
        }
        peopleService.save(convertToPerson(personDTO));
        //  відправляєм HTTP відповідь з тустим тілом зі статусом 200
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Person convertToPerson(PersonDTO personDTO) {


        return modelMapper.map(personDTO, Person.class);
    }



    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handlerException(PersonNotFoundException personNotFoundException) {
        PersonErrorResponse response = new PersonErrorResponse("Person not found", System.currentTimeMillis());
        // In HTTP body response
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handlerException(PersonNotCreated personNotCreated) {
        PersonErrorResponse response = new PersonErrorResponse(personNotCreated.getMessage(), System.currentTimeMillis());
        // In HTTP body response
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private PersonDTO convertToPersonDTO(Person person){
        return modelMapper.map(person, PersonDTO.class);
    }
}
