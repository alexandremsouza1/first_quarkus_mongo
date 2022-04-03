package org.acme.mongodb.panache.resources;

import org.acme.mongodb.panache.entities.Person;
import org.bson.types.ObjectId;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import java.util.List;

@Path("/pessoa")
public class PersonResource {

    @Transactional
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Person create(Person newPerson) {
        Person.persist(newPerson);
        return newPerson;
    }

    @GET
    @Produces("application/json")
    public List<Person> listAll() {
        return Person.listAll();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    @Consumes("application/json")
    public Person getById(@PathParam("id") String id) {

        ObjectId personId = new ObjectId(id);
        Person person = Person.findById(personId);
        if (person == null) {
            throw new WebApplicationException(404);
        }
        return person;
    }

    @PUT
    @Path("{id}")
    @Produces("application/json")
    @Consumes("application/json")
    public Person update(@PathParam("id") String id, Person personToSave) {

        ObjectId personId = new ObjectId(id);
        Person person = Person.findById(personId);

        if (personId == null) {
            throw new WebApplicationException(404);
        }
        //merge dinamic properties from personToSave to person
        person = person.merge(personToSave);
        person.update();
        
        return person;
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") String id) {

        ObjectId personId = new ObjectId(id);
        boolean deleted = Person.deleteById(personId);
        if (!deleted) {
            throw new WebApplicationException(404);
        }
    }

}