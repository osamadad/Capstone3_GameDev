package com.tuwaiq.capstone3_gamedev.Controller;

import com.tuwaiq.capstone3_gamedev.Api.ApiResponse;
import com.tuwaiq.capstone3_gamedev.Model.Genre;
import com.tuwaiq.capstone3_gamedev.Service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/genre")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @PostMapping("/add")
    public ResponseEntity<?> addGenre(@RequestBody Genre genre){
        genreService.addGenre(genre);
        return ResponseEntity.status(200).body(new ApiResponse("Genre added successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getGenres(){
        return ResponseEntity.status(200).body(genreService.getGenres());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateGenre(@PathVariable Integer id, @RequestBody Genre genre){
        genreService.updateGenre(id, genre);
        return ResponseEntity.status(200).body(new ApiResponse("Genre updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteGenre(@PathVariable Integer id){
        genreService.deleteGenre(id);
        return ResponseEntity.status(200).body(new ApiResponse("Genre deleted successfully"));
    }
}
