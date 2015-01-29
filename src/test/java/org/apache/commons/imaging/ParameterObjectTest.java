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

import org.apache.commons.imaging.common.BinaryConstant;
import org.apache.commons.imaging.formats.jpeg.JpegConstants;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Some tests to see if the class under test works.
 */
public final class ParameterObjectTest {
    /**
     * Test the building on an parameter object using only valid values
     * and the access to the stored parameter values.
     */
    @Test
    public void testBuildWithValidValues() {
        ParameterObject testObject = ParameterObject.build()
            .setInt(Parameter.PARAM_KEY_EXIF, 21)
            .setBinaryConstant(Parameter.PARAM_KEY_STRICT,
                    JpegConstants.JFIF0_SIGNATURE)
            .get();
        assertEquals(21, testObject.getInt(Parameter.PARAM_KEY_EXIF));
        assertEquals(0x4a, testObject.getBinaryConstant(Parameter.PARAM_KEY_STRICT).get(0));
    }
    
    /**
     * Test the building on an parameter object using an valid value.
     * This should cause an IllegalArgumentException
     */
    @Test(expected=IllegalArgumentException.class)
    public void testBuildWithInvalidValues() {
        ParameterObject.build()
            // violation of contract - BinaryConstant::size must > 0
            .setBinaryConstant(Parameter.PARAM_KEY_STRICT,
                    getEmptyBinaryConstant());
    }
    
    
    /**
     * Try to access an parameter which is not present.
     */
    @Test(expected=IllegalStateException.class)
    public void testGetIntWithExceptionForNotPresent() {
        ParameterObject testObject = ParameterObject.build()
            .setInt(Parameter.PARAM_KEY_EXIF, 21)
            .get();
        // throws IllegalStateException because this parameter is not present
        testObject.getInt(Parameter.PARAM_KEY_STRICT);
    }
    
    /**
     * Try to access an parameter which is present but has another type.
     */
    @Test(expected=IllegalStateException.class)
    public void testGetIntWithExceptionForWrongType() {
        ParameterObject testObject = ParameterObject.build()
            .setInt(Parameter.PARAM_KEY_EXIF, 21)
            .setBinaryConstant(Parameter.PARAM_KEY_STRICT,
                    JpegConstants.JFIF0_SIGNATURE)
            .get();
        // throws IllegalStateException because this parameter is not of type int
        testObject.getInt(Parameter.PARAM_KEY_STRICT);
    }
    
    
    /**
     * Try to access an parameter which is not present.
     */
    @Test(expected=IllegalStateException.class)
    public void testGetBinaryConstantWithExceptionForNotPresent() {
        ParameterObject testObject = ParameterObject.build()
            .setBinaryConstant(Parameter.PARAM_KEY_STRICT,
                    JpegConstants.JFIF0_SIGNATURE)
            .get();
        // throws IllegalStateException because this parameter is not present
        testObject.getBinaryConstant(Parameter.PARAM_KEY_EXIF);
    }
    
    /**
     * Try to access an parameter which is present but has another type.
     */
    @Test(expected=IllegalStateException.class)
    public void testGetBinaryConstantWithExceptionForWrongType() {
        ParameterObject testObject = ParameterObject.build()
            .setInt(Parameter.PARAM_KEY_EXIF, 21)
            .setBinaryConstant(Parameter.PARAM_KEY_STRICT,
                    JpegConstants.JFIF0_SIGNATURE)
            .get();
        // throws IllegalStateException because this parameter is not of type
        // BinaryConstant
        testObject.getBinaryConstant(Parameter.PARAM_KEY_EXIF);
    }
    
    /**
     * Get an empty BinaryConstant (BinaryConstant::size == 0)
     * @return 
     */
    private BinaryConstant getEmptyBinaryConstant() {
        return new BinaryConstant(
            new byte[] { });
    }
}