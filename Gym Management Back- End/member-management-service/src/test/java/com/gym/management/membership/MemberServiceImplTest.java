package com.gym.management.membership;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gym.management.membership.exception.ResourceNotFoundException;
import com.gym.management.membership.model.Member;
import com.gym.management.membership.repository.MemberRepository;
import com.gym.management.membership.service.MemberServiceImpl;

@ExtendWith(MockitoExtension.class)
public class MemberServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberServiceImpl memberService;

    private Member member;

    @BeforeEach
    void setUp() {
        member = new Member();
        member.setId(1);
        member.setFirstName("John");
        member.setLastName("Doe");
        member.setEmail("john.doe@example.com");
        member.setPhoneNumber("+1234567890");
        member.setDateOfBirth(new Date());
    }

    @Test
    void testCreateMember() {
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        Member createdMember = memberService.createMember(member);

        assertEquals(member.getId(), createdMember.getId());
        verify(memberRepository, times(1)).save(member);
    }

    @Test
    void testGetMember() {
        when(memberRepository.findById(1)).thenReturn(Optional.of(member));

        Member foundMember = memberService.getMember(1);

        assertEquals(member.getId(), foundMember.getId());
        verify(memberRepository, times(1)).findById(1);
    }

    @Test
    void testGetMemberNotFound() {
        when(memberRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> memberService.getMember(1));
        verify(memberRepository, times(1)).findById(1);
    }

    @Test
    void testGetAllMembers() {
        when(memberRepository.findAll()).thenReturn(Arrays.asList(member));

        assertEquals(1, memberService.getAllMembers().size());
        verify(memberRepository, times(1)).findAll();
    }

    @Test
    void testUpdateMember() {
        Member updatedDetails = new Member();
        updatedDetails.setFirstName("Jane");
        updatedDetails.setLastName("Doe");
        updatedDetails.setEmail("jane.doe@example.com");
        updatedDetails.setPhoneNumber("+0987654321");
        updatedDetails.setDateOfBirth(new Date());

        when(memberRepository.findById(1)).thenReturn(Optional.of(member));
        when(memberRepository.save(any(Member.class))).thenReturn(updatedDetails);

        Member updatedMember = memberService.updateMember(1, updatedDetails);

        assertEquals(updatedDetails.getFirstName(), updatedMember.getFirstName());
        verify(memberRepository, times(1)).findById(1);
        verify(memberRepository, times(1)).save(member);
    }

    @Test
    void testDeleteMember() {
        when(memberRepository.existsById(1)).thenReturn(true);

        memberService.deleteMember(1);

        verify(memberRepository, times(1)).existsById(1);
        verify(memberRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteMemberNotFound() {
        when(memberRepository.existsById(1)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> memberService.deleteMember(1));
        verify(memberRepository, times(1)).existsById(1);
    }
}
