package com.busra.todolist.list;

import com.busra.todolist.list.model.ToDoItem;
import com.busra.todolist.list.model.ToDoList;
import com.busra.todolist.list.repository.ItemRepository;
import com.busra.todolist.user.model.User;
import com.busra.todolist.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/todoitem")
public class ToDoItemController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addItem(@RequestBody Map<String, String> body) {

        String username = body.get("username");
        User user = userRepository.findByUserName(username);
        Set<ToDoList> toDoListSet = user.getToDoLists();

        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setDescription(body.get("description"));
        toDoItem.setName(body.get("name"));
        toDoItem.setDeadline(body.get("deadline"));
        toDoItem.setStatus(body.get("status"));

        if (!body.get("dependency").equals("")){
            toDoItem.setDependency(Long.parseLong(body.get("dependency"),10));
        }

        Set<ToDoList> newList = new HashSet<>();

        for (Iterator<ToDoList> iterator = toDoListSet.iterator(); iterator.hasNext(); ) {
            ToDoList temp = iterator.next();
            if (temp.getToDoListId().toString().equals(body.get("listid"))) {
                Set<ToDoItem> toDoItemSet = temp.getToDoItems();
                toDoItem.setToDoList(temp);
                toDoItemSet.add(toDoItem);
                temp.setToDoItems(toDoItemSet);
                newList.add(temp);
            } else {
                newList.add(temp);
            }
        }

        user.setToDoLists(newList);
        userRepository.save(user);
        return ResponseEntity.ok("success");


    }

    @PostMapping(value = "/del", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delItem(@RequestBody Map<String, String> body) {

        itemRepository.deleteUsingSingleQuery(Long.parseLong(body.get("itemid"), 10) );

        return ResponseEntity.ok("success");
    }

    @PostMapping(value = "/finish", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> finishItem(@RequestBody Map<String, String> body) {

        ToDoItem toDoItem = itemRepository.findByToDoItemId(Long.parseLong(body.get("itemid"), 10));
        ToDoItem dependentItem = itemRepository.findByToDoItemId(toDoItem.getDependency());

        if (dependentItem != null){
            if ( dependentItem.getStatus().equals("finished")){
                itemRepository.updateUsingSingleQuery(Long.parseLong(body.get("itemid"), 10));
                return ResponseEntity.ok("success");
            } else {
                return ResponseEntity.ok("notsuccess");
            }
        } else {
            itemRepository.updateUsingSingleQuery(Long.parseLong(body.get("itemid"), 10));
            return ResponseEntity.ok("success");
        }

    }
}
