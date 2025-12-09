package com.tuwaiq.capstone3_gamedev.Service;

import com.tuwaiq.capstone3_gamedev.Api.ApiException;
import com.tuwaiq.capstone3_gamedev.Model.Platform;
import com.tuwaiq.capstone3_gamedev.Repository.PlatformRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlatformService {

    private final PlatformRepository platformRepository;

    public void addPlatform(Platform platform){
        platformRepository.save(platform);
    }

    public List<Platform> getPlatforms(){
        return platformRepository.findAll();
    }

    public void updatePlatform(Integer id, Platform platform){
        Platform oldPlatform = platformRepository.findPlatformById(id);
        if (oldPlatform == null){
            throw new ApiException("Platform not found");
        }
        oldPlatform.setName(platform.getName());
        platformRepository.save(oldPlatform);
    }

    public void deletePlatform(Integer id){
        Platform platform = platformRepository.findPlatformById(id);
        if (platform == null){
            throw new ApiException("Platform not found");
        }
        platformRepository.delete(platform);
    }
}
