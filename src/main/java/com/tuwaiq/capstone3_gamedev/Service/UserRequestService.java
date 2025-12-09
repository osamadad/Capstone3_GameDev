package com.tuwaiq.capstone3_gamedev.Service;

import com.tuwaiq.capstone3_gamedev.Api.ApiException;
import com.tuwaiq.capstone3_gamedev.Model.UserRequest;
import com.tuwaiq.capstone3_gamedev.Repository.StudioRepository;
import com.tuwaiq.capstone3_gamedev.Repository.UserRepository;
import com.tuwaiq.capstone3_gamedev.Repository.UserRequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class UserRequestService {
    private final UserRequestRepository userRequestRepository;
    private final UserRepository userRepository;
    private final StudioRepository studioRepository;



    public List<UserRequest> getAll() {
        return userRequestRepository.findAll();
    }


    public void add(UserRequest request) {
        Integer userId = request.getUser().getId();

        if (!userRepository.existsById(userId)) {
            throw new ApiException("User not found");
        }

        Integer studioId = request.getStudio().getId();
        if (!studioRepository.existsById(studioId)) {
            throw new ApiException("Studio not found");
        }

        request.setStatus("Pending");

        request.setCreatedAt(LocalDateTime.now());
        userRequestRepository.save(request);
    }


    public void update(Integer id, UserRequest request) {
        UserRequest old = userRequestRepository.findUserRequestById(id);

        if (old == null) {
            throw new ApiException("Request not found");
        }

        old.setMessage(request.getMessage());
        userRequestRepository.save(old);
    }


    public void delete(Integer id) {
        UserRequest old = userRequestRepository.findUserRequestById(id);

        if (old == null) {
            throw new ApiException("Request not found");
        }

        userRequestRepository.delete(old);
    }

   //Endpoints
    public void acceptRequest(Integer id) {
        UserRequest req = userRequestRepository.findUserRequestById(id);

        if (req == null) {
            throw new ApiException("Request not found");
        }

        if (req.getStatus().equalsIgnoreCase("Rejected")) {
            throw new ApiException("Cannot accept a rejected request");
        }

        req.setStatus("Accepted");
        userRequestRepository.save(req);
    }


    public void rejectRequest(Integer id) {
        UserRequest req = userRequestRepository.findUserRequestById(id);

        if (req == null) {
            throw new ApiException("Request not found");
        }

        if (req.getStatus().equalsIgnoreCase("Accepted")) {
            throw new ApiException("Cannot reject an accepted request");
        }

        req.setStatus("Rejected");
        userRequestRepository.save(req);
    }

}
