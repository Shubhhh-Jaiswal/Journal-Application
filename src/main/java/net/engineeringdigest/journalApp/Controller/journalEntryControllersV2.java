package net.engineeringdigest.journalApp.Controller;

import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Service.JournalEntryService;
import net.engineeringdigest.journalApp.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class journalEntryControllersV2 {

//    Map<Long , JournalEntry> entries = new HashMap<>();

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username= authentication.getName();
        User user = userService.getByUsername(username);
       List<JournalEntry> all= user.getJournalEntries();
        if(all!=null && !all.equals("")){
            return new ResponseEntity(all, HttpStatus.OK);
        }
       return new ResponseEntity(all, HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry obj){
        try{
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String username= authentication.getName();
            journalEntryService.saveEntry(obj, username);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{myID}")
    public ResponseEntity<JournalEntry> findById(@PathVariable ObjectId myID){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getByUsername(username);
        List<JournalEntry> list = user.getJournalEntries().stream().filter(x->x.getId().equals(myID)).collect(Collectors.toList());
        if(list!=null){
            JournalEntry journalEntry= journalEntryService.findById(myID);
            return new ResponseEntity<>(journalEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("{myID}")
    public ResponseEntity<JournalEntry> updateById(@PathVariable ObjectId myID, @RequestBody JournalEntry journalEntry){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getByUsername(username);
        List<JournalEntry> list = user.getJournalEntries().stream().filter(x->x.getId().equals(myID)).collect(Collectors.toList());
        if(list!=null){
            JournalEntry old_entry = journalEntryService.findById(myID);
            old_entry.setTitle(journalEntry.getTitle()!=null && !journalEntry.getTitle().equals("") ? journalEntry.getTitle() : old_entry.getTitle());
            old_entry.setContent(journalEntry.getContent()!=null && !journalEntry.getContent().equals("") ? journalEntry.getContent() : old_entry.getContent());
            JournalEntry journalEntry1 = journalEntryService.updateById(myID, old_entry);
            return new ResponseEntity<>(journalEntry1,HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{myID}")
    public ResponseEntity<JournalEntry> DeleteById( @PathVariable ObjectId myID){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getByUsername(username);
        List<JournalEntry> list = user.getJournalEntries().stream().filter(x->x.getId().equals(myID)).collect(Collectors.toList());
        if(list!=null){
            journalEntryService.deleteById(myID, username);
            return new  ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }



    }

