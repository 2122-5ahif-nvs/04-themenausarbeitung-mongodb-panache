package at.htl.mongodbpanache.resource;

import at.htl.mongodbpanache.control.PersonRepository;
import at.htl.mongodbpanache.entity.Person;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("persons")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {
    @Inject
    PersonRepository personRepository;

    @GET
    public Response count(){
        return Response
                .ok(personRepository.findAll().list())
                .build();
    }

    @POST
    public Response create(Person person) {
        personRepository.persist(person);
        return Response.created(URI.create("/persons/" + person.id)).build();
    }

    @GET
    @Path("/{id}")
    public Person get(@PathParam("id") String id) {
        return personRepository.findById(new ObjectId(id));
    }
}
