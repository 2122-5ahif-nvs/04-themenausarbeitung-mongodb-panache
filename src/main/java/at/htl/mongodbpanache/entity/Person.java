package at.htl.mongodbpanache.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.time.LocalDate;

@MongoEntity(collection="ThePerson")
public class Person extends PanacheMongoEntity {

    public ObjectId id;
    public String first;
    public String last;

    @BsonProperty("birth")
    public LocalDate birthDate;
}
