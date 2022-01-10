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

import org.apache.drill.common.types.TypeProtos.MinorType;
import org.apache.drill.exec.physical.rowSet.RowSet;
import org.apache.drill.exec.record.metadata.SchemaBuilder;
import org.apache.drill.exec.record.metadata.TupleMetadata;
import org.apache.drill.exec.rpc.RpcException;
import org.apache.drill.test.ClusterFixture;
import org.apache.drill.test.ClusterFixtureBuilder;
import org.apache.drill.test.ClusterTest;
import org.apache.drill.test.QueryBuilder;
import org.apache.drill.test.rowSet.RowSetComparison;
import org.junit.BeforeClass;
import org.junit.Test;


public class HumanNameUDFTests extends ClusterTest {

  @BeforeClass
  public static void setup() throws Exception {
    ClusterFixtureBuilder builder = ClusterFixture.builder(dirTestWatcher);
    startCluster(builder);
  }

  @Test
  public void testFirstName() throws RpcException {
    String sql = "SELECT get_first_name('Abraham Lincoln') as n1, " +
      "get_first_name('de la Cruz, Ana M.') as n2, " +
      "get_first_name('') as n3 " +
      "FROM (VALUES(1))";

    QueryBuilder q = client.queryBuilder().sql(sql);
    RowSet results = q.rowSet();

    TupleMetadata expectedSchema = new SchemaBuilder()
      .add("n1", MinorType.VARCHAR)
      .add("n2", MinorType.VARCHAR)
      .add("n3", MinorType.VARCHAR)
      .build();

    RowSet expected = client.rowSetBuilder(expectedSchema)
      .addRow("Abraham", "Ana", "")
      .build();

    new RowSetComparison(expected).verifyAndClearAll(results);
  }

  @Test
  public void testLastName() throws RpcException {
    String sql = "SELECT get_last_name('Abraham Lincoln') as n1, " +
      "get_last_name('de la Cruz, Ana M.') as n2, " +
      "get_last_name('') as n3 " +
      "FROM (VALUES(1))";

    QueryBuilder q = client.queryBuilder().sql(sql);
    RowSet results = q.rowSet();

    TupleMetadata expectedSchema = new SchemaBuilder()
      .add("n1", MinorType.VARCHAR)
      .add("n2", MinorType.VARCHAR)
      .add("n3", MinorType.VARCHAR)
      .build();

    RowSet expected = client.rowSetBuilder(expectedSchema)
      .addRow("Lincoln", "de la Cruz", "")
      .build();

    new RowSetComparison(expected).verifyAndClearAll(results);
  }


  @Test
  public void testLeadingInitial() throws RpcException {
    String sql = "SELECT get_leading_initial('J. Walter Weatherman') as n1, " +
      "get_leading_initial('Dr. F. Scott Fitzgerald') as n2, " +
      "get_leading_initial('') as n3, " +
      "get_leading_initial(' ') as n4 " +
      "FROM (VALUES(1))";

    QueryBuilder q = client.queryBuilder().sql(sql);
    RowSet results = q.rowSet();

    TupleMetadata expectedSchema = new SchemaBuilder()
      .add("n1", MinorType.VARCHAR)
      .add("n2", MinorType.VARCHAR)
      .add("n3", MinorType.VARCHAR)
      .add("n4", MinorType.VARCHAR)
      .build();

    RowSet expected = client.rowSetBuilder(expectedSchema)
      .addRow("J.", "F.", "", "")
      .build();

    new RowSetComparison(expected).verifyAndClearAll(results);
  }

  @Test
  public void testMiddleName() throws RpcException {
    String sql = "SELECT get_middle_names('Menachem Mendel Schneerson') as n1, " +
      "get_middle_names('Dr. Shalom Dov Ber Grossman') as n2, " +
      "get_middle_names('  ') as n3, " +
      "get_middle_names('   Dr. Shalom Dov Ber Grossman  ') as n4 " +
      "FROM (VALUES(1))";

    QueryBuilder q = client.queryBuilder().sql(sql);
    RowSet results = q.rowSet();

    TupleMetadata expectedSchema = new SchemaBuilder()
      .add("n1", MinorType.VARCHAR)
      .add("n2", MinorType.VARCHAR)
      .add("n3", MinorType.VARCHAR)
      .add("n4", MinorType.VARCHAR)
      .build();

    RowSet expected = client.rowSetBuilder(expectedSchema)
      .addRow("Mendel", "Dov Ber", "", "Dov Ber")
      .build();

    new RowSetComparison(expected).verifyAndClearAll(results);
  }

  @Test
  public void testNickName() throws RpcException {
    String sql = "SELECT get_nickname('Menachem Mendel \"The Rebbe\" Schneerson') as n1, " +
      "get_nickname('Dr. Shalom Dov Ber Grossman') as n2, " +
      "get_nickname('') as n3 " +
      "FROM (VALUES(1))";

    QueryBuilder q = client.queryBuilder().sql(sql);
    RowSet results = q.rowSet();

    TupleMetadata expectedSchema = new SchemaBuilder()
      .add("n1", MinorType.VARCHAR)
      .add("n2", MinorType.VARCHAR)
      .add("n3", MinorType.VARCHAR)
      .build();

    RowSet expected = client.rowSetBuilder(expectedSchema)
      .addRow("The Rebbe", "", "")
      .build();

    new RowSetComparison(expected).verifyAndClearAll(results);
  }

  @Test
  public void testGetPostnominal() throws RpcException {
    String sql = "SELECT get_postnominal('Charles Givre Ph.D') as n1, " +
      "get_postnominal('Dr. Shalom Dov Ber Grossman MD') as n2, " +
      "get_postnominal(' ') as n3 " +
      "FROM (VALUES(1))";

    QueryBuilder q = client.queryBuilder().sql(sql);
    RowSet results = q.rowSet();

    TupleMetadata expectedSchema = new SchemaBuilder()
      .add("n1", MinorType.VARCHAR)
      .add("n2", MinorType.VARCHAR)
      .add("n3", MinorType.VARCHAR)
      .build();

    RowSet expected = client.rowSetBuilder(expectedSchema)
      .addRow("Ph.D", "MD", "")
      .build();

    new RowSetComparison(expected).verifyAndClearAll(results);
  }


  @Test
  public void testGetSalutation() throws RpcException {
    String sql = "SELECT get_salutation('Mr. Charles Givre') as n1, " +
      "get_salutation('Dr. Shalom Dov Ber Grossman MD') as n2, " +
      "get_salutation('') as n3 " +
      "FROM (VALUES(1))";

    QueryBuilder q = client.queryBuilder().sql(sql);
    RowSet results = q.rowSet();

    TupleMetadata expectedSchema = new SchemaBuilder()
      .add("n1", MinorType.VARCHAR)
      .add("n2", MinorType.VARCHAR)
      .add("n3", MinorType.VARCHAR)
      .build();

    RowSet expected = client.rowSetBuilder(expectedSchema)
      .addRow("Mr.", "Dr.", "")
      .build();

    new RowSetComparison(expected).verifyAndClearAll(results);
  }
  
  @Test
  public void testGetSuffix() throws RpcException {
    String sql = "SELECT get_name_suffix('Mr. Charles Givre Jr. Ph.D') as n1, " +
      "get_name_suffix('Dr. Shalom Dov Ber Grossman IV MD') as n2, " +
      "get_name_suffix('12345') as n3 " +
      "FROM (VALUES(1))";

    QueryBuilder q = client.queryBuilder().sql(sql);
    RowSet results = q.rowSet();

    TupleMetadata expectedSchema = new SchemaBuilder()
      .add("n1", MinorType.VARCHAR)
      .add("n2", MinorType.VARCHAR)
      .add("n3", MinorType.VARCHAR)
      .build();

    RowSet expected = client.rowSetBuilder(expectedSchema)
      .addRow("Jr.", "IV", "")
      .build();

    new RowSetComparison(expected).verifyAndClearAll(results);
  }
}
