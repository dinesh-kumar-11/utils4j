/*
 * utils4j - Mandatory.java, Aug 1, 2013 12:26:44 PM
 * 
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
 */
package com.varra.classification;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;

/**
 * Indicates that a method/field should be set to a valid value. If a
 * method/filed is annotated with this annotation type but does not have a valid
 * value API will throw an error message.
 * 
 * @author <a href="mailto:varra@outlook.com">Rajakrishna V.
 *         Reddy</a>
 * @version 1.0
 * 
 */
@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Mandatory
{
	
}