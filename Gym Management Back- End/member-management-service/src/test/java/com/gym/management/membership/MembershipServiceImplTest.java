package com.gym.management.membership;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gym.management.membership.exception.MembershipAlreadyExistsException;
import com.gym.management.membership.exception.ResourceNotFoundException;
import com.gym.management.membership.model.Member;
import com.gym.management.membership.model.Membership;
import com.gym.management.membership.repository.MembershipRepository;
import com.gym.management.membership.service.MemberService;
import com.gym.management.membership.service.MembershipServiceImpl;


@ExtendWith(MockitoExtension.class)
class MembershipServiceImplTest {

    @Mock
    private MemberService memberService;

    @Mock
    private MembershipRepository membershipRepository;

    @InjectMocks
    private MembershipServiceImpl membershipService;

    private Member member;
    private Membership membership;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        member = new Member();
        member.setId(1);

        membership = new Membership();
        membership.setId(1);
        membership.setMember(member);
        membership.setMembershipType(Membership.MembershipType.BASIC);
        membership.setStatus(Membership.MembershipStatus.ACTIVE);
        membership.setStartDate(LocalDate.now());
        membership.setEndDate(LocalDate.now().plusMonths(1));
    }

    @Test
    void testCreateMembership() {
        when(memberService.getMember(1)).thenReturn(member);
        when(membershipRepository.save(any(Membership.class))).thenReturn(membership);

        Membership createdMembership = membershipService.createMembership(1, Membership.MembershipType.BASIC);

        assertNotNull(createdMembership);
        assertEquals(Membership.MembershipType.BASIC, createdMembership.getMembershipType());
        verify(memberService, times(1)).getMember(1);
        verify(membershipRepository, times(1)).save(any(Membership.class));
    }

    @Test
    void testCreateMembershipAlreadyExists() {
        member.setMembership(membership);
        when(memberService.getMember(1)).thenReturn(member);

        assertThrows(MembershipAlreadyExistsException.class, () -> {
            membershipService.createMembership(1, Membership.MembershipType.BASIC);
        });

        verify(memberService, times(1)).getMember(1);
    }

    @Test
    void testRenewMembership() {
        when(memberService.getMember(1)).thenReturn(member);
        member.setMembership(membership);

        Membership renewedMembership = membershipService.renewMembership(1);

        assertNotNull(renewedMembership);
        assertEquals(Membership.MembershipStatus.ACTIVE, renewedMembership.getStatus());
        verify(memberService, times(1)).getMember(1);
    }

    @Test
    void testRenewMembershipNotFound() {
        when(memberService.getMember(1)).thenReturn(member);

        assertThrows(ResourceNotFoundException.class, () -> {
            membershipService.renewMembership(1);
        });

        verify(memberService, times(1)).getMember(1);
    }

    @Test
    void testUpgradeMembership() {
        when(memberService.getMember(1)).thenReturn(member);
        member.setMembership(membership);

        Membership upgradedMembership = membershipService.upgradeMembership(1, Membership.MembershipType.PREMIUM);

        assertNotNull(upgradedMembership);
        assertEquals(Membership.MembershipType.PREMIUM, upgradedMembership.getMembershipType());
        verify(memberService, times(1)).getMember(1);
    }

    @Test
    void testUpgradeMembershipNotFound() {
        when(memberService.getMember(1)).thenReturn(member);

        assertThrows(ResourceNotFoundException.class, () -> {
            membershipService.upgradeMembership(1, Membership.MembershipType.PREMIUM);
        });

        verify(memberService, times(1)).getMember(1);
    }

    @Test
    void testDeactivateMembership() {
        when(memberService.getMember(1)).thenReturn(member);
        member.setMembership(membership);

        membershipService.deactivateMembership(1);

        assertEquals(Membership.MembershipStatus.INACTIVE, membership.getStatus());
        verify(memberService, times(1)).getMember(1);
    }

    @Test
    void testDeactivateMembershipNotFound() {
        when(memberService.getMember(1)).thenReturn(member);

        assertThrows(ResourceNotFoundException.class, () -> {
            membershipService.deactivateMembership(1);
        });

        verify(memberService, times(1)).getMember(1);
    }
}