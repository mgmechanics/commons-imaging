/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Created 2015 by Michael Gross, mgmechanics@mgmechanics.de
 * Changed 2015 by Michael Gross, mgmechanics@mgmechanics.de
 */

package org.apache.commons.imaging;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Some tests to see if the class under test works.
 */
public final class ImagingParametersTest {
    /**
     * Test the building on an parameter object using only valid values
     * and the access to the stored parameter values.
     */
    @Test
    public void testBuildWithValidValues() {
        ImagingParameters testObject = ImagingParameters.build()
            .setValue(Parameter.EXIF, 21)
            .setValue(Parameter.STRICT, "yes")
            .get();
        assertEquals(Integer.valueOf(21), testObject.getValue(Parameter.EXIF, Integer.class));
        assertEquals("yes", testObject.getValue(Parameter.STRICT, String.class));
    }
    
    /**
     * Test the building on an parameter object using {@code null} as value.
     * This should cause an IllegalArgumentException
     */
    @Test(expected=IllegalArgumentException.class)
    public void testBuildWithNullAsValue() {
        ImagingParameters.build()
            // violation of contract - value must not null
            .setValue(Parameter.STRICT, null);
    }
    
    /**
     * Try to access an parameter which is not present.
     */
    @Test(expected=IllegalStateException.class)
    public void testGetValueWithExceptionForNotPresent() {
        ImagingParameters testObject = ImagingParameters.build()
            .setValue(Parameter.EXIF, 21)
            .get();
        // throws IllegalStateException because this parameter is not present
        // class for value doesn't matter in this case
        testObject.getValue(Parameter.STRICT, Object.class);
    }
    
    /**
     * Try to access an parameter which is present but has another type.
     */
    @Test(expected=IllegalStateException.class)
    public void testGetValueWithExceptionForWrongType() {
        ImagingParameters testObject = ImagingParameters.build()
            .setValue(Parameter.EXIF, 21)
            .get();
        // throws IllegalStateException because this parameter is not of type Integer
        testObject.getValue(Parameter.STRICT, String.class);
    }
}