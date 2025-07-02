package net.engineeringdigest.journalApp.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("/config")
@Getter
@Setter
@NoArgsConstructor
public class ApplicationConfiguration {
    private String key;
    private String value;

}
