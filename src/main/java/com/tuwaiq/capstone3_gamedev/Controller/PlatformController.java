package com.tuwaiq.capstone3_gamedev.Controller;

import com.tuwaiq.capstone3_gamedev.Api.ApiResponse;
import com.tuwaiq.capstone3_gamedev.Model.Platform;
import com.tuwaiq.capstone3_gamedev.Service.PlatformService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/platform")
@RequiredArgsConstructor
public class PlatformController {

    private final PlatformService platformService;

    @PostMapping("/add")
    public ResponseEntity<?> addPlatform(@RequestBody Platform platform){
        platformService.addPlatform(platform);
        return ResponseEntity.status(200).body(new ApiResponse("Platform added successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getPlatforms(){
        return ResponseEntity.status(200).body(platformService.getPlatforms());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePlatform(@PathVariable Integer id, @RequestBody Platform platform){
        platformService.updatePlatform(id, platform);
        return ResponseEntity.status(200).body(new ApiResponse("Platform updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePlatform(@PathVariable Integer id){
        platformService.deletePlatform(id);
        return ResponseEntity.status(200).body(new ApiResponse("Platform deleted successfully"));
    }
}
