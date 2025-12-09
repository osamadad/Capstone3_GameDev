package com.tuwaiq.capstone3_gamedev.Service;

import com.tuwaiq.capstone3_gamedev.Api.ApiException;
import com.tuwaiq.capstone3_gamedev.Model.StudioMember;
import com.tuwaiq.capstone3_gamedev.Repository.StudioMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudioMemberService {

    private final StudioMemberRepository studioMemberRepository;

    public List<StudioMember> get() {
        return studioMemberRepository.findAll();
    }

    public void add(StudioMember studioMember) {
        studioMemberRepository.save(studioMember);
    }

    public void update(Integer id, StudioMember studioMember) {
        StudioMember old = studioMemberRepository.findStudioMemberById(id);
        if (old == null) throw new ApiException("Studio member not found");

        old.setName(studioMember.getName());
        old.setRole(studioMember.getRole());
        old.setCreatedAt(studioMember.getCreatedAt());
        old.setUser(studioMember.getUser());
        old.setStudio(studioMember.getStudio());

        studioMemberRepository.save(old);
    }

    public void delete(Integer id) {
        StudioMember old = studioMemberRepository.findStudioMemberById(id);
        if (old == null) throw new ApiException("Studio member not found");

        studioMemberRepository.delete(old);
    }
}
