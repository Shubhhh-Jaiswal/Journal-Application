package net.engineeringdigest.journalApp.AppCache;

import net.engineeringdigest.journalApp.Entity.ApplicationConfiguration;
import net.engineeringdigest.journalApp.Repository.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppCache {

    public Map<String, String> cache = new HashMap<>();

    @Autowired
    private ApplicationConfig applicationConfig;

    @PostConstruct
    public void getConfigs(){
        List<ApplicationConfiguration> all = applicationConfig.findAll();
        for(ApplicationConfiguration configuration:all){
          cache.put(configuration.getKey(), configuration.getValue());
        }

    }


}
