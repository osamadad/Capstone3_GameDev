package com.tuwaiq.capstone3_gamedev.Service;

import com.tuwaiq.capstone3_gamedev.Api.ApiException;
import com.tuwaiq.capstone3_gamedev.Model.Genre;
import com.tuwaiq.capstone3_gamedev.Repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public void addGenre(Genre genre){
        genreRepository.save(genre);
    }

    public List<Genre> getGenres(){
        return genreRepository.findAll();
    }

    public void updateGenre(Integer id, Genre genre){
        Genre oldGenre = genreRepository.findGenreById(id);
        if (oldGenre == null){
            throw new ApiException("Genre not found");
        }
        oldGenre.setName(genre.getName());
        genreRepository.save(oldGenre);
    }

    public void deleteGenre(Integer id){
        Genre genre = genreRepository.findGenreById(id);
        if (genre == null){
            throw new ApiException("Genre not found");
        }
        genreRepository.delete(genre);
    }
}
