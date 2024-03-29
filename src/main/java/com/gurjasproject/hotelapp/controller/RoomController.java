package com.gurjasproject.hotelapp.controller;

import com.gurjasproject.hotelapp.model.Room;
import com.gurjasproject.hotelapp.response.RoomResponse;
import com.gurjasproject.hotelapp.service.IRoomService;
import com.gurjasproject.hotelapp.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;


// controller class calls the business logic from interface implementation
//(USING SERVICES)

@RestController // to identify our class as a controller
@RequiredArgsConstructor
@RequestMapping("/rooms")   //just for a prefix, no actual use
public class RoomController {
    private final IRoomService roomService;  //declaration of variable of custom type to be initialized later
    private final ImageService imageService; //declaration of variable of custom type to be initialized later

    //endpoint of the POST method is mapped to url: /add/new-room
    @PostMapping("/add/new-room")   //@PostMapping is a composed annotation, @RequestMapping(method = RequestMethod.POST).
    public ResponseEntity<RoomResponse> addNewRoom(     //using ResponseEntity<T> is considered best practice when returning responses.
            //@RequestParam("file") MultipartFile multipartFile,
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("roomType") String roomType,
            @RequestParam("roomPrice") BigDecimal roomPrice)
            throws SQLException, IOException {

        //imageService.upload(multipartFile);       //firebase upload
        Room savedRoom = roomService.addNewRoom(photo, roomType, roomPrice); //Room object creation (logic in service class)
        RoomResponse response = new RoomResponse(                      //RoomResponse object creation
                savedRoom.getId(), savedRoom.getRoomType(), savedRoom.getRoomPrice()
        );
        return ResponseEntity.ok(response); //return OK STATUS and return response object
    }

    @GetMapping("/room/types")
    public List<String> getRoomTypes(){
        return roomService.getAllRoomTypes();
    }

    }



