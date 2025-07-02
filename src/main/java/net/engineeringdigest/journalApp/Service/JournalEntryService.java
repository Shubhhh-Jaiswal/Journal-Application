package net.engineeringdigest.journalApp.Service;

import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;

    public void saveEntry(JournalEntry journalEntry){
       journalEntry.setDate(LocalDateTime.now());
        journalEntryRepository.save(journalEntry);
    }

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username){
        User user = userService.getByUsername(username);
        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(journalEntry);
        userService.saveExistingEntry(user);
    }

    public List<JournalEntry> getAll(){
       return journalEntryRepository.findAll();
    }

    public JournalEntry findById(ObjectId Id){
        return journalEntryRepository.findById(Id).orElse(null);
    }

    public JournalEntry updateById(ObjectId Id, JournalEntry journalEntry){
        return journalEntryRepository.save(journalEntry);
    }

    @Transactional
    public void deleteById(ObjectId Id, String username){
        try {
            User user = userService.getByUsername(username);
            boolean removed = user.getJournalEntries().removeIf(x -> x.getId().equals(Id));
            if (removed) {
                journalEntryRepository.deleteById(Id);
                userService.saveExistingEntry(user);
            }
        }catch(Exception e){
            throw new RuntimeException("An error occured while deleting the entry");
        }

    }

}
