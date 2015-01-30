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

import java.util.EnumMap;
import org.apache.commons.imaging.common.BinaryConstant;

/**
 * Class to provide parameters as requested at IMAGING-159.
 * Each instance of this class is immutable.
 */
public final class ParameterObject {
    // this is an error message
    private static final String wrongTypeError = 
        "This parameter is present in this parameter object "
        + "but the stored value does not match the expected type.";
    
    // this field maps an image constant to an parameter values
    private final EnumMap<Parameter, Object> parameterValues;
    
    /**
     * Get a new parameter object
     * @param parameterValues 
     */
    private ParameterObject(final EnumMap<Parameter, Object> parameterValues) {
        this.parameterValues = parameterValues;
    }
    
    /**
     * Returns an builder object to build a parameter object
     * @return an builder object
     */
    public static ParameterBuilder build() {
        return new ParameterBuilder(
            new EnumMap<Parameter, Object>(Parameter.class)
        );
    }
    
    /**
     * Get an int value. If there is no value present for this parameter or
     * if there is a value present but it is not an int value an
     * IllegalStateException is thrown.
     * @param parameter
     * @return 
     */
    public int getInt(final Parameter parameter) {
       checkIfParameterPresent(parameter);
        try {
            return (Integer) this.parameterValues.get(parameter);
        }
        catch (ClassCastException ex) {
            throw new IllegalStateException(wrongTypeError);
        }
}
    
    /**
     * Get an BinaryConstant value. 
     * If there is no value present for this parameter or
     * if there is a value present but it is not an BinaryConstant value an
     * IllegalStateException is thrown.
     * @param parameter
     * @return 
     */
    public BinaryConstant getBinaryConstant(final Parameter parameter) {
        checkIfParameterPresent(parameter);
        try {
            return (BinaryConstant) this.parameterValues.get(parameter);
        }
        catch (ClassCastException ex) {
            throw new IllegalStateException(wrongTypeError);
        }
    }
    
    /**
     * Checks if the given parameter is present in this parameter object -
     * if not an IllegalStateException is thrown.
     * @param parameter the parameter to check
     */
    private void checkIfParameterPresent(final Parameter parameter) {
        if (!this.parameterValues.containsKey(parameter)) {
            throw new IllegalStateException(
                "This parameter is not present in this parameter object."
            );
        }
    }
    
    
    /**
     * This class is part of the builder pattern used to set an arbitrary
     * number of parameters easily.
     */
    public static final class ParameterBuilder {
        private final EnumMap<Parameter, Object> parameterValues;
        
        /**
         * Get a new parameter builder object
         * @param parameterValues 
         */
        private ParameterBuilder(final EnumMap<Parameter,
                Object> parameterValues) {
            this.parameterValues = parameterValues;
        }
        
        /**
         * Return the parameter object with the values collected by the builder.
         * @return an parameter object
         */
        public ParameterObject get() {
            return new ParameterObject(this.parameterValues);
        }
        
        /**
         * set an int value - no checks necessary
         * @param parameter
         * @param value 
         * @return  
         */
        public ParameterBuilder setInt(final Parameter parameter, final int value) {
            this.parameterValues.put(parameter,value);
            return this;
        }
        
        /**
         * Set an BinaryConstant as value - checks necessary
         * @param parameter 
         * @param value the BinaryConstant must not {@code null} and must have
         * a size {@literal > 0} otherwise an IllegalArgumentException is thrown
         * @return 
         */
        public ParameterBuilder setBinaryConstant(final Parameter parameter,
                final BinaryConstant value) {
            //check value
            checkIfValueIsNull(value);
            if (value.size() == 0) {
                throw new IllegalArgumentException(
                    "The size of the BinaryConstant must be > 0."
                );
            }
            this.parameterValues.put(parameter,value);
            return this;
        }
        
        /**
         * Checks if the given value is {@code null} - if it is {@code null}
         * then an IllegalStateException is thrown.
         * @param parameter the parameter to check
         */
        private void checkIfValueIsNull(final Object object) {
            if (object == null) {
                throw new IllegalArgumentException(
                    "The value for any parameter must not null."
                );
            }
        }
    }
}
