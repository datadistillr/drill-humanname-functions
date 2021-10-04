/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.datadistillr.udf;

import io.netty.buffer.DrillBuf;
import org.apache.drill.exec.expr.DrillSimpleFunc;
import org.apache.drill.exec.expr.annotations.FunctionTemplate;
import org.apache.drill.exec.expr.annotations.Output;
import org.apache.drill.exec.expr.annotations.Param;
import org.apache.drill.exec.expr.holders.VarCharHolder;

import javax.inject.Inject;

public class HumanNameUDFs {


  @FunctionTemplate(names = {"getFirstName", "get_first_name"},
    scope = FunctionTemplate.FunctionScope.SIMPLE,
    nulls = FunctionTemplate.NullHandling.NULL_IF_NULL)
  public static class getFirstNameUDF implements DrillSimpleFunc {

    @Param
    VarCharHolder inputNameHolder;

    @Output
    VarCharHolder out;

    @Inject
    DrillBuf buffer;

    @Override
    public void setup() {

    }

    @Override
    public void eval() {
      String inputName = org.apache.drill.exec.expr.fn.impl.StringFunctionHelpers.getStringFromVarCharHolder(inputNameHolder);
      com.tupilabs.human_name_parser.Name name = new com.tupilabs.human_name_parser.Name(inputName);

      com.tupilabs.human_name_parser.HumanNameParserBuilder builder = new com.tupilabs.human_name_parser.HumanNameParserBuilder(name);
      com.tupilabs.human_name_parser.HumanNameParserParser parser = builder.build();

      String result = parser.getFirst();

      byte[] rowStringBytes = result.getBytes(java.nio.charset.StandardCharsets.UTF_8);
      buffer = buffer.reallocIfNeeded(rowStringBytes.length);
      buffer.setBytes(0, rowStringBytes);

      out.start = 0;
      out.end = rowStringBytes.length;
      out.buffer = buffer;
    }
  }

}
