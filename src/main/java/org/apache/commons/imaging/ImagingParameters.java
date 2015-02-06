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

import org.apache.commons.imaging.common.BufferedImageFactory;

/**
 * This class is a POJO holding data for parameters as requested in IMAGING-159.
 * It holds data needed for all image formats. For data needed only by one
 * particular format there are inherited classes.
 * <p>
 * There are two kinds of parameters: with and without default value. For
 * parameters without default value it implements lazy initialization:
 * <br>
 * After getting an instance there is no value present until you set one using
 * one the setter methods. Until a value is set, isXPresent() will return
 * {@code false}. Accessing the value with getX() in this state will cause a RuntimeException.
 * After a value is set with setX() isXPresent() will return {@code true} and you may access the value.
 * <p>
 * Other parameters have a default value. This is told in the javadoc for their
 * getX() method. They don't have a isXPresent() method. You may access them any time.
 */
public class ImagingParameters {
    
    private Boolean verbose; //PARAM_KEY_VERBOSE
    
    private Boolean strict; // PARAM_KEY_STRICT
    
    private String fileNameHint; // PARAM_KEY_FILENAME
    
    private String xmpXmlAsString; // PARAM_KEY_XMP_XML
    
    private ImageFormat imageFormat; // PARAM_KEY_FORMAT
    
    private BufferedImageFactory bufferedImageFactory; // BUFFERED_IMAGE_FACTORY
    
    private PixelDensity pixelDensity; // PARAM_KEY_PIXEL_DENSITY
    
    /**
     * This gives you a parameter object with default values.
     */
    public ImagingParameters() {
        this.verbose = Boolean.FALSE;
        this.strict = Boolean.FALSE;
        this.fileNameHint = null;
        this.xmpXmlAsString = null;
        this.imageFormat = null;
        this.bufferedImageFactory = null;
        this.pixelDensity = null;
    }
    
    //****** verbose ******
    /**
     * Turns the verbose mode on/off.
     * <p>
     * Parameter applies to read and write operations.
     * <p>
     * Default value: verbose mode disabled.
     * @return Valid values: {@code Boolean.TRUE} means {@literal "verbose mode enabled"}
     * and {@code Boolean.FALSE} means {@literal "verbose mode disabled"}.
     */
    public Boolean isVerbose() {
        return this.verbose;
    }
    
    /**
     * Turns the verbose mode on.
     * Parameter applies to read and write operations.
     */
    public void enableVerbose() {
        this.verbose = Boolean.TRUE;
    }
    
    /**
     * Turns the verbose mode off.
     * Parameter applies to read and write operations.
     */
    public void disableVerbose() {
        this.verbose = Boolean.FALSE;
    }
    
    //****** strict ******
    
    /**
     * Parameter indicates whether to throw exceptions when parsing invalid
     * files, or whether to tolerate small problems.
     * <p>
     * Default value: tolerate small problems.
     * @return Valid values: {@code Boolean.TRUE} causes it to throw exceptions
     * when parsing invalid files and {@code Boolean.FALSE} let it tolerate small problems.
     * @see org.apache.commons.imaging.formats.tiff.constants.TiffConstants
     */
    public Boolean isStrict() {
        return this.strict;
    }
    
    /**
     * Parameter indicates whether to throw exceptions when parsing invalid
     * files, or whether to tolerate small problems.
     * This method switches the behavior so that it throws exceptions when
     * parsing invalid files.
     */
    public void enableStrict() {
        this.strict = Boolean.TRUE;
    }
    
    /**
     * Parameter indicates whether to throw exceptions when parsing invalid
     * files, or whether to tolerate small problems.
     * This method switches the behavior so that it tolerates small problems.
     * 
     */
    public void disableStrict() {
        this.strict = Boolean.FALSE;
    }
    
    //****** fileNameHint ******
    
    /**
     * Parameter used to hint the filename when reading from a byte array
     * or InputStream. The filename hint can help disambiguate what file the
     * image format.
     * <p>
     * Applies to read operations.
     * @return {@code true} if there is a value present, {@false} else.
     */
    public boolean isFileNameHintPresent() {
        return this.fileNameHint == null;
    }
    
    /**
     * Parameter used to hint the filename when reading from a byte array
     * or InputStream. The filename hint can help disambiguate what file the
     * image format.
     * <p>
     * Applies to read operations.
     * @return Valid values: filename as string
     * @see java.io.InputStream
     */
    public String getFileNameHint() {
        String value = this.fileNameHint;
        checkIfParameterIsPresent(value);
        return value;
    }
    
    /**
     * Parameter used to hint the filename when reading from a byte array
     * or InputStream. The filename hint can help disambiguate what file the
     * image format.
     * <p>
     * Applies to read operations.
     * @param value Valid values: filename as string
     * @see java.io.InputStream
     */
    public void setFileNameHint(final String value) {
        checkIfValueIsNull(value);
        this.fileNameHint = value;
    }
        
    //****** xmpXmlAsString ******
    
    /**
     * Parameter key.
     * 
     * Only used when writing images. Valid values: String of XMP XML.
     * @return {@code true} if there is a value present, {@false} else.
     */
    public boolean isXmpXmlAsStringPresent() {
        return this.xmpXmlAsString == null;
    }
    
    /**
     * Parameter key.
     * 
     * Only used when writing images.
     * @return Valid values: String of XMP XML.
     */
    public String getXmpXmlAsString() {
        String value = this.xmpXmlAsString;
        checkIfParameterIsPresent(value);
        return value;
    }
    
    /**
     * Parameter key.
     * 
     * Only used when writing images.
     * @param value Valid values: String of XMP XML.
     */
    public void setXmpXmlAsString(final String value) {
        checkIfValueIsNull(value);
        this.xmpXmlAsString = value;
    }
    
    //****** imageFormat ******
    
    /**
     * Parameter used in write operations to indicate desired image format.
     * @return {@code true} if there is a value present, {@false} else.
     */
    public boolean isImageFormatPresent() {
        return this.imageFormat == null;
    }
    
    /**
     * Parameter used in write operations to indicate desired image format.
     * @return Valid values: Any format defined in ImageFormat, such as
     * ImageFormat.IMAGE_FORMAT_PNG.
     */
    public ImageFormat getImageFormat() {
        ImageFormat value = this.imageFormat;
        checkIfParameterIsPresent(value);
        return value;
    }
    
    /**
     * Parameter used in write operations to indicate desired image format.
     * @param value Valid values: Any format defined in ImageFormat, such as
     * ImageFormat.IMAGE_FORMAT_PNG.
     */
    public void setImageFormat(final ImageFormat value) {
        checkIfValueIsNull(value);
        this.imageFormat = value;
    }
    
    //****** bufferedImageFactory ******
    
    /**
     * 
     * @return {@code true} if there is a value present, {@false} else.
     */
    public boolean isBufferedImageFactoryPresent() {
        return this.bufferedImageFactory == null;
    }
    
    /**
     * 
     * @return A BufferedImageFactory
     */
    public BufferedImageFactory getBufferedImageFactory() {
        BufferedImageFactory value = this.bufferedImageFactory;
        checkIfParameterIsPresent(value);
        return value;
    }
    
    /**
     * 
     * @param value A BufferedImageFactory
     */
    public void setBufferedImageFactory(final BufferedImageFactory value) {
        checkIfValueIsNull(value);
        this.bufferedImageFactory = value;
    }
    
    //****** pixelDensity ******
    
    /**
     * Parameter key. Used in write operations to indicate the desired pixel
     * density (DPI), and/or aspect ratio.
     * @return {@code true} if there is a value present, {@false} else.
     * @see org.apache.commons.imaging.PixelDensity
     */
    public boolean isPixelDensityPresent() {
        return this.pixelDensity == null;
    }
    
    /**
     * Parameter key. Used in write operations to indicate the desired pixel
     * density (DPI), and/or aspect ratio.
     * @return Valid values: PixelDensity
     * @see org.apache.commons.imaging.PixelDensity
     */
    public PixelDensity getPixelDensity() {
        PixelDensity value = this.pixelDensity;
        checkIfParameterIsPresent(value);
        return value;
    }
    
    /**
     * Parameter key. Used in write operations to indicate the desired pixel
     * density (DPI), and/or aspect ratio.
     * @param value Valid values: PixelDensity
     * @see org.apache.commons.imaging.PixelDensity
     */
    public void setPixelDensity(final PixelDensity value) {
        checkIfValueIsNull(value);
        this.pixelDensity = value;
    }
    
    //****** check methods ******
    
    /**
     * Throws a RuntimeException if a given value is {code null}.
     * @param value 
     */
    final void checkIfValueIsNull(final Object value) {
        if (value == null) {
            throw new IllegalArgumentException("The value for any parameter must not null.");
        }
    }
    
    /**
     * Throws a RuntimeException if the value for this parameter isn't set yet.
     * @param value 
     */
    final void checkIfParameterIsPresent(final Object value) {
        if (value == null) {
            throw new IllegalStateException("You tried to get a value which is not present.");
        }
    }
}
