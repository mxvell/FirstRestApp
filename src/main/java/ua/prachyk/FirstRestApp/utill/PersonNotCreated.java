package ua.prachyk.FirstRestApp.utill;

public class PersonNotCreated extends RuntimeException{
    public PersonNotCreated(String msg){
        super(msg);
    }
}
