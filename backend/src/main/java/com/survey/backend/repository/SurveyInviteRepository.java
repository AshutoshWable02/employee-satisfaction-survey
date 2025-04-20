package com.survey.backend.repository;

import com.survey.backend.model.SurveyInvite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SurveyInviteRepository extends JpaRepository<SurveyInvite, Long> {
    Optional<SurveyInvite> findByEmailAndPasswordAndUsedFalse(String email, String password);
}
