package com.sample.investorsquo.domain.entities.projections;

import com.sample.investorsquo.domain.entities.User;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "user projection", types = { User.class })
public interface UserProjection {

    String getFirstName();
    String getLastName();
    String getEmail();
}
