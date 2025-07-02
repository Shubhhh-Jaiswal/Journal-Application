//package net.engineeringdigest.journalApp.Controller;
//
//import net.engineeringdigest.journalApp.Entity.JournalEntry;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/_journal")
//public class journalEntryControllers {
//
//    Map<Long , JournalEntry> entries = new HashMap<>();
//
//    @GetMapping
//    public List<JournalEntry> getAll(){
//        return new ArrayList<>(entries.values());
//    }
//
//    @PostMapping()
//    public boolean createEntry(@RequestBody JournalEntry obj){
//        entries.put(obj.getId(), obj );
//        return true;
//    }
//    @GetMapping("id/{myID}")
//    public JournalEntry findById(@PathVariable Long myID){
//        if(entries.containsKey(myID)){
//            return entries.get(myID);
//        }else{
//            return null;
//        }
//    }
//
//    @PutMapping("put/{myID}")
//    public JournalEntry updateById(@PathVariable Long myID, @RequestBody JournalEntry obj){
//        entries.put(obj.getId(), obj);
//        return entries.get(obj.getId());
//    }
//
//    @DeleteMapping("delete/{myID}")
//    public JournalEntry DeleteById(Long myID){
//        JournalEntry obj;
//        obj = entries.get(myID);
//        System.out.println(obj.getId()+" "+obj.getContent());
//        entries.remove(myID);
//
//        return obj;
//    }
//}
