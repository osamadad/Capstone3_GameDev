package com.tuwaiq.capstone3_gamedev.Service;

import com.tuwaiq.capstone3_gamedev.Api.ApiException;
import com.tuwaiq.capstone3_gamedev.Model.StudioMember;
import com.tuwaiq.capstone3_gamedev.Model.UserRequest;
import com.tuwaiq.capstone3_gamedev.Repository.*;
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
    private final StudioMemberRepository studioMemberRepository;



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

        boolean alreadyMember = studioMemberRepository.existsByUserIdAndStudioId(userId, studioId);

        if (alreadyMember) {
            throw new ApiException("User is already a member of this studio");
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

        StudioMember member = new StudioMember();
        member.setName(req.getUser().getUsername());
        member.setRole(req.getUser().getRole());
        member.setCreatedAt(LocalDateTime.now());
        member.setUser(req.getUser());
        member.setStudio(req.getStudio());

        studioMemberRepository.save(member);

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

    public List<UserRequest> getUserRequestsByUserId(Integer userId) {
        UserRequest user = userRequestRepository.findUserRequestById(userId);
        if (user==null) {
            throw new ApiException("User not found");
        }

        List<UserRequest> requests = userRequestRepository.findAllByUserId(userId);

        if (requests.isEmpty()) {
            throw new ApiException("No requests found for user with id: " + userId);
        }

        return requests;
    }

    public List<UserRequest> getRequestsByStudioId(Integer studioId) {
        if (!studioRepository.existsById(studioId)) {
            throw new ApiException("Studio not found");
        }

        List<UserRequest> requests = userRequestRepository.findAllByStudioId(studioId);

        if (requests.isEmpty()) {
            throw new ApiException("No requests found for studio with id: "+ studioId);
        }

        return requests;
    }


}
