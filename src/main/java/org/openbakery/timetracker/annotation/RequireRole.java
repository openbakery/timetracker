package org.openbakery.timetracker.annotation;

import org.openbakery.timetracker.data.Role;

import java.lang.annotation.*;

/**
 * User: rene
 * Date: 04.05.11
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RequireRole {
    public Role value();
}
