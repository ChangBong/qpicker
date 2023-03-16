package com.peopulley.core.base.repository;

import com.peopulley.core.base.domain.Member;
import com.peopulley.core.base.repository.custom.CustomMemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, CustomMemberRepository {

    Optional<Member> findByEmailAndMemberStatus(String email, int status);

}
