package net.engineeringdigest.journalApp.Repository;

import net.engineeringdigest.journalApp.Entity.ApplicationConfiguration;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApplicationConfig extends MongoRepository<ApplicationConfiguration, ObjectId> {
}
