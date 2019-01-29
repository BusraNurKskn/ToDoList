package com.busra.todolist.list;

import com.busra.todolist.list.model.ToDoItem;
import com.busra.todolist.list.response.Response;
import com.busra.todolist.list.model.ToDoList;
import com.busra.todolist.list.repository.ItemRepository;
import com.busra.todolist.list.repository.ListRepository;
import com.busra.todolist.list.response.ResponseItem;
import com.busra.todolist.list.response.ResponseList;
import com.busra.todolist.user.model.User;
import com.busra.todolist.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/todolist")
public class ListController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ListRepository listRepository;

    @Autowired
    private ItemRepository itemRepository;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createList(@RequestBody Map<String, String> body) {

        String username = body.get("username");
        User user = userRepository.findByUserName(username);

        ToDoList toDoList = new ToDoList();
        toDoList.setUser(user);
        toDoList.setName(body.get("name"));

        Set<ToDoList> toDoListSet = user.getToDoLists();
        toDoListSet.add(toDoList);

        user.setToDoLists(toDoListSet);
        userRepository.save(user);

        return ResponseEntity.ok("success");
    }

    @PostMapping(value = "/del", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delList(@RequestBody Map<String, String> body) {

        itemRepository.deleteAllList(Long.parseLong(body.get("listid"), 10));
        listRepository.deleteUsingSingleQuery(Long.parseLong(body.get("listid"), 10));

        return ResponseEntity.ok("success");
    }

    @GetMapping(value = "/getall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getList(@RequestParam(value = "username") String username) {
        User user = userRepository.findByUserName(username);
        Response response = new Response();
        Set <ResponseList> emptyList = new HashSet<>();

        for (ToDoList list : user.getToDoLists()){
            ResponseList responseList = new ResponseList();
            responseList.setId(list.getToDoListId());
            responseList.setName(list.getName());

            Set <ResponseItem> emptyItems = new HashSet<>();

            for (ToDoItem item : list.getToDoItems()) {

                ResponseItem responseItem = new ResponseItem();
                responseItem.setDeadline(item.getDeadline());
                responseItem.setId(item.getToDoItemId());
                responseItem.setDescription(item.getDescription());
                responseItem.setName(item.getName());
                responseItem.setStatus(item.getStatus());

                emptyItems.add(responseItem);
            }

            responseList.setToDoItemSet(emptyItems);

            emptyList.add(responseList);
        }
        response.setToDoLists(emptyList);

        return ResponseEntity.ok(response);
    }
}
