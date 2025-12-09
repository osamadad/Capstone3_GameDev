package com.tuwaiq.capstone3_gamedev.Service;

import com.tuwaiq.capstone3_gamedev.Api.ApiException;
import com.tuwaiq.capstone3_gamedev.Model.Studio;
import com.tuwaiq.capstone3_gamedev.Model.StudioMember;
import com.tuwaiq.capstone3_gamedev.Model.User;
import com.tuwaiq.capstone3_gamedev.Repository.StudioMemberRepository;
import com.tuwaiq.capstone3_gamedev.Repository.StudioRepository;
import com.tuwaiq.capstone3_gamedev.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudioService {

    private final StudioRepository studioRepository;
    private final StudioMemberRepository studioMemberRepository;
    private final UserRepository userRepository;

    private void checkLeader(StudioMember member) {
        if (member == null) throw new ApiException("Studio member not found");

        if (!member.getRole().equalsIgnoreCase("leader")) {
            throw new ApiException("Access denied, only leader can manage the studio");
        }

    }

    public List<Studio> get() {
        return studioRepository.findAll();
    }

    public void add(Integer userId, Studio studio) {
        User user= userRepository.findUserById(userId);
        if (user==null){
            throw new ApiException("User not found");
        }
        StudioMember studioMember=new StudioMember(null,user.getUsername(),"leader", LocalDateTime.now(),user,studio);
        studio.getMembers().add(studioMember);
        studioRepository.save(studio);
    }

    public void update(Integer studioMemberId, Integer id, Studio studio) {
        StudioMember studioMember = studioMemberRepository.findStudioMemberById(studioMemberId);
        checkLeader(studioMember);

        if (studioMember.getStudio() == null || !studioMember.getStudio().getId().equals(id)) {
            throw new ApiException("Access denied, you are not a leader of this studio");
        }

        Studio oldStudio = studioRepository.findStudioById(id);
        if (oldStudio == null) throw new ApiException("Studio not found");


        oldStudio.setName(studio.getName());
        oldStudio.setDescription(studio.getDescription());
        oldStudio.setCreatedAt(studio.getCreatedAt());

        studioRepository.save(oldStudio);
    }

    public void delete(Integer studioMemberId, Integer id) {
        StudioMember studioMember = studioMemberRepository.findStudioMemberById(studioMemberId);
        checkLeader(studioMember);

        if (studioMember.getStudio() == null || !studioMember.getStudio().getId().equals(id)) {
            throw new ApiException("Access denied, you are not a leader of this studio");
        }

        Studio studio = studioRepository.findStudioById(id);
        if (studio == null) throw new ApiException("Studio not found");

        studioRepository.delete(studio);
    }
}
