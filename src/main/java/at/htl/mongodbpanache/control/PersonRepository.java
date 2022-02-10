package at.htl.mongodbpanache.control;

import at.htl.mongodbpanache.entity.Person;
import io.quarkus.mongodb.panache.PanacheMongoRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PersonRepository implements PanacheMongoRepository<Person> {
}
