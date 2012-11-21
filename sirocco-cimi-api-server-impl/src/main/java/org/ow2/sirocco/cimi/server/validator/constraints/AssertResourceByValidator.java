/**
 *
 * SIROCCO
 * Copyright (C) 2011 France Telecom
 * Contact: sirocco@ow2.org
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 *
 * $Id$
 *
 */
package org.ow2.sirocco.cimi.server.validator.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.ow2.sirocco.cimi.server.domain.CimiResource;

/**
 * Implementation of {@link AssertResourceBy} validator.
 **/
public class AssertResourceByValidator implements ConstraintValidator<AssertResourceBy, Object> {

    @Override
    public void initialize(final AssertResourceBy annotation) {
        // Nothing to do
    }

    /**
     * Validate a valid reference.
     * <p>
     * {@inheritDoc}
     * </p>
     * 
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object,
     *      javax.validation.ConstraintValidatorContext)
     */
    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext ctx) {
        boolean valid = true;
        if (value != null) {
            valid = false;
            CimiResource resource = (CimiResource) value;
            if ((true == resource.hasReference()) || (true == resource.hasValues())) {
                valid = true;
            }
        }
        return valid;
    }
}