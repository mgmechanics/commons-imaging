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

/**
 * Class to provide parameters as requested at IMAGING-159.
 * Each instance of this class is immutable.
 */
public final class ImagingParameters {
    // this is an error message
    private static final String msgWrongTypeError = 
        "This parameter is present in this parameter object "
        + "but the stored value does not match the expected type.";
    
    // this field maps an image constant to an parameter values
    private final EnumMap<Parameter, Object> parameterValues;
    
    /**
     * Get a new parameter object
     * @param parameterValues 
     */
    private ImagingParameters(final EnumMap<Parameter, Object> parameterValues) {
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
     * Get the value for any present parameter.
     * If there is no value present for this parameter or if there is a value
     * present but the value can not cast to type T an IllegalStateException is thrown.
     * @param <T> expected type of the parameter value (must not provided, Java will inference it)
     * @param parameter
     * @param clazz expected class of the parameter value like Integer.class
     * @return value of type T
     */
    public <T> T getValue(final Parameter parameter, final Class<T> clazz) {
        checkIfParameterPresent(parameter);
        try {
            Object value = this.parameterValues.get(parameter);
            return clazz.cast(value);
        }
        catch (ClassCastException ex) {
            throw new IllegalStateException(msgWrongTypeError + "; " + ex.getLocalizedMessage());
        }
    }
    
    /**
     * Check if the given parameter is present.
     * @param parameter
     * @return {@code true} if the given parameter is present, {@false} otherwise.
     */
    public boolean isParameterPresent(final Parameter parameter) {
        return this.parameterValues.containsKey(parameter);
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
        public ImagingParameters get() {
            return new ImagingParameters(this.parameterValues);
        }
        
        /**
         * Set the value for any available parameter.
         * @param parameter
         * @param value 
         * @return another ParameterBuilder so that you can chain setValue(...).setValue(...).get()
         */
        public ParameterBuilder setValue(final Parameter parameter, final Object value) {
            checkIfValueIsNull(value);
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
