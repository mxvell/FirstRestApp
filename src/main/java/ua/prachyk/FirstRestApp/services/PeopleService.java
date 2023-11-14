package ua.prachyk.FirstRestApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.prachyk.FirstRestApp.models.Person;
import ua.prachyk.FirstRestApp.repositories.PeopleRepository;
import ua.prachyk.FirstRestApp.utill.PersonNotFoundException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService  {

    private final PeopleRepository peopleRepository;
     @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }
    public List<Person> findAll(){
         return peopleRepository.findAll();
    }

    public Person findOne(int id){
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElseThrow(PersonNotFoundException::new);
    }
    @Transactional
    public void save(Person person){
         enrichPerson(person);
         peopleRepository.save(person);
    }
    private void enrichPerson(Person person) {
        person.setCreatedAt(LocalDateTime.now());
        person.setUpdateAt(LocalDateTime.now());
        person.setCreatedWho("Admin");
    }
    @Transactional
    public void update(int id, Person updPerson){
         updPerson.setId(id);
         peopleRepository.save(updPerson);
    }
    @Transactional
    public void delete(int id){
         peopleRepository.deleteById(id);
    }
    public void test(){
        System.out.println("Testing here with debug.Inside Hibernate Transaction");
    }
}
